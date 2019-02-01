package com.eleksploded.betterbeds;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.ModId, name = Reference.Name, version = Reference.Version)
public class betterbeds
{

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {    	
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(new BedThing());
    }
}
