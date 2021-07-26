package net.tropicraftFabric.client.data;

import net.tropicraftFabric.Constants;
import net.tropicraftFabric.common.Util;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public enum TropicraftLangKeys {
    
    NA("general", "na", "N/A"),

    SCUBA_AIR_TIME("scuba", "air_time", "Air Remaining: %s"),
    SCUBA_DIVE_TIME("scuba", "dive_time", "Dive Time: %s"),
    SCUBA_DEPTH("scuba", "depth", "Current Depth: %s"),
    SCUBA_MAX_DEPTH("scuba", "max_depth", "Max Depth: %s"),
    SCUBA_VISIBILITY_STAT("scuba", "scuba.visibility", "Underwater Fog Reduction")
    ;

    protected final String key, value;
    private final TranslatableText component;

    private TropicraftLangKeys(String type, String key) {
        this(type, key, Util.toEnglishName(key));
    }

    private TropicraftLangKeys(String type, String key, String value) {
        this.key = net.minecraft.util.Util.createTranslationKey(type, new Identifier(Constants.MODID, key));
        this.value = value;
        this.component = new TranslatableText(this.key);
    }
    
    public String getKey() {
        return key;
    }

    public TranslatableText getComponent() {
        return component;
    }

    public TranslatableText format(Object... args) {
        return new TranslatableText(getComponent().getKey(), args);
    }

    public String getLocalizedText() {
        return getComponent().getString();
    }
    
    /*protected void register(TropicraftLangProvider prov) {
        prov.add(key, value);
    }

    public static void generate(TropicraftLangProvider prov) {
        for (TropicraftLangKeys lang : values()) {
            lang.register(prov);
        }
    }

     */
}
