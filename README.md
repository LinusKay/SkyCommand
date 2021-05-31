# Sky Command
A Spigot plugin that allows server owners to configure commands to be run when the player clicks the sky.
Commands are run when the player left-clicks the sky above the configured angle.
```YAML
# minimum vertical angle of player head for commands to be run
# value can be any number between -90 and 90
# horizon is 0, directly above is 90, directly below is -90
minimum_angle: -45
# all configured commands
# multiple commands may be configured to be run depending on the player's permissions
commands:
  # command name 
  # REQUIRED
  help:
    # permission node required by player for command to be run 
    # OPTIONAL
    permission: "skycommand.help"
    # command to be run
    # REQUIRED
    command: "help"
    # whether the command should be run by the player or via console
    # OPTIONAL
    run_as_op: false
   ```