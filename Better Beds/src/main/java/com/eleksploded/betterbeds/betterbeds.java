package com.eleksploded.betterbeds;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.ModId, name = Reference.Name, version = Reference.Version)
public class betterbeds
{

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(BedThing.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
 
    }
}
