package com.libus.skycommand.event;

import com.libus.skycommand.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class skyClick implements Listener {

    private Main plugin;

    public skyClick(Main plugin) {
        this.plugin = plugin;
    }

    private Set<UUID> attackedEntity = new HashSet<UUID>();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Entity attacker = event.getDamager();
        if (attacker instanceof Player) {
            Player player = (Player) attacker;
            attackedEntity.add(player.getUniqueId());
        }
    }

    @EventHandler
    public void onInteract(PlayerAnimationEvent event) {
        Player player = event.getPlayer();
        PlayerAnimationType anim = event.getAnimationType();
        if (anim == PlayerAnimationType.ARM_SWING) {
            BukkitScheduler scheduler = plugin.getServer().getScheduler();
            scheduler.scheduleSyncDelayedTask(this.plugin, new Runnable() {
                @Override
                public void run() {

                    if (attackedEntity.contains(player.getUniqueId())) {
                        attackedEntity.remove(player.getUniqueId());
                        return;
                    }

                    float pitch = player.getLocation().getPitch();
                    float minAngle = plugin.getConfig().getInt("minimum_angle");
                    if (pitch < (minAngle * -1)) {
                        for (String commandName : plugin.getConfig().getConfigurationSection("commands").getKeys(false)) {
                            String permission = plugin.getConfig().getString("commands." + commandName + ".permission");
                            String command = plugin.getConfig().getString("commands." + commandName + ".command");
                            command = command.replace("{player}", player.getName());
                            boolean runAsOp = plugin.getConfig().getBoolean("commands." + commandName + ".run_as_op");
                            if (player.hasPermission(permission)) {
                                if (!runAsOp) {
                                    player.performCommand(command);
                                } else {
                                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
                                }
                            }
                        }
                    }
                }
            }, 1L);

        }
    }
}
