package com.perkelle.dev.aacdiscordnotifications;

import org.json.JSONObject;

public class DiscordEmbedBuilder {

    public JSONObject json = new JSONObject("{\"embeds\":[{}]}");

    public JSONObject getEmbed() {
        return (JSONObject) json.getJSONArray("embedss").get(0);
    }

    public DiscordEmbedBuilder setColour(int colour) {
        getEmbed().put("color", colour);
        return this;
    }

    public DiscordEmbedBuilder addField(String name, String value, boolean inline) {
        getEmbed().put("fields", getEmbed().getJSONArray("fields").put(new JSONObject()
                .put("name", name)
                .put("value", value)
                .put("inline", inline)));
        return this;
    }
}
