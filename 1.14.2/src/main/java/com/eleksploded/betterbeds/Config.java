package com.eleksploded.betterbeds;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Boolean> Hardcore;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> Blacklist;
        private static List<String> DefaultValue = new ArrayList<String>();
        public final ForgeConfigSpec.ConfigValue<Boolean> randBed;

        public General(ForgeConfigSpec.Builder builder) {
        	DefaultValue.add("minecraft:end_portal_frame");
        	
            builder.push("General");
            Hardcore = builder
                    .comment("Enables/Disables hardcore mode, bedrock instead of beds")
                    .translation("config.hardcore")
                    .define("hardcore", false);
            Blacklist = builder
            		.comment("Block Blacklist for Bedification [\"mod:block\",\"mod:block\"]")
            		.comment("Just requires a world reload, I think")
            		.translation("config.blacklist")
            		.define("blacklist", DefaultValue);
            randBed = builder
            		.comment("Should we use random colored beds?")
            		.translation("config.randbed")
            		.define("randomBed", true);
            builder.pop();
        }
    }
    
    public static final ForgeConfigSpec spec = BUILDER.build();
}
