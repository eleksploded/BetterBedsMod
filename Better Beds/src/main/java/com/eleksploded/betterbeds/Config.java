package com.eleksploded.betterbeds;

import net.minecraftforge.common.config.Configuration;

public class Config {

    private static final String CATEGORY_GENERAL = "general";

    // This values below you can access elsewhere in your mod:
    public static boolean hardcore = false;
    public static String[] blacklist = new String[1];
    
    //public static String[] blacklist = new ArrayList<String>();

    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = betterbeds.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            System.out.println("Problem loading config file! "+ e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
    	blacklist[0] = "minecraft:end_portal_frame";
    	
        hardcore = cfg.getBoolean("Hardcore Mode", CATEGORY_GENERAL, hardcore, "Hardcore mode: Everything is bedrock, not beds");
        blacklist = cfg.getStringList("Blacklist", CATEGORY_GENERAL, blacklist, "List of blocks to blacklist bedding");
    }
}
