package com.perkelle.dev.aacdiscordnotifications;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import me.konsolas.aac.api.PlayerViolationEvent;
import me.konsolas.aac.api.PlayerViolationCommandEvent;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ViolationListener implements Listener {

    private final FileConfiguration config;
    private final Plugin pl;

    public ViolationListener(AACDiscordNotifications pl) {
        this.config = pl.getConfig();
        this.pl = pl;
    }

    @EventHandler
    public void onViolation(PlayerViolationEvent e) {
        if (config.getBoolean("on-violation.should-notify", true)) {
            if (e.getViolations() >= config.getInt("on-violation.minimum-vl", 30)) {
                String body = new DiscordEmbedBuilder()
                        .setColour(config.getInt("on-violation.colour", 16562691))
                        .addField("Player", e.getPlayer().getName(), true)
                        .addField("Hack Type", e.getHackType().toString(), true)
                        .addField("Violations", "" + e.getViolations(), true)
                        .addField("Description", e.getMessage(), true)
                        .addField("Server", config.getString("server-identifier", ""), true)
                        .json
                        .toString();

                submitWebhook(body);
            }
        }
    }

    @EventHandler
    public void onKick(PlayerViolationCommandEvent e) {
        if (config.getBoolean("on-kick.should-notify", true)) {
            String body = new DiscordEmbedBuilder()
                    .setColour(config.getInt("on-kick.colour", 14226185))
                    .addField("Player", e.getPlayer().getName(), true)
                    .addField("Hack Type", e.getHackType().toString(), true)
                    .addField("Server", config.getString("server-identifier", ""), true)
                    .json
                    .toString();

            submitWebhook(body);
        }
    }

    private void submitWebhook(String body) {
        pl.getServer().getScheduler().runTaskAsynchronously(pl, () -> {
            try {
                Request
                        .Post(config.getString("webhook-url"))
                        .addHeader("Content-Type", "application/json")
                        .bodyString(body, ContentType.APPLICATION_JSON)
                        .execute()
                        .discardContent();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
