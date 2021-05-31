package com.libus.skycommand;

import com.libus.skycommand.event.skyClick;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new skyClick(this), this);
        saveDefaultConfig();
    }
}
