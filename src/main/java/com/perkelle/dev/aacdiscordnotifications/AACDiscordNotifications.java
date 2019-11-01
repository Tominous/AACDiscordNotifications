package com.perkelle.dev.aacdiscordnotifications;

import org.bukkit.plugin.java.JavaPlugin;

public class AACDiscordNotifications extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new ViolationListener(this), this);
    }
}
