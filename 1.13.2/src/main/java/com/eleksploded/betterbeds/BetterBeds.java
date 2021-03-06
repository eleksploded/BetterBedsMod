package com.eleksploded.betterbeds;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("betterbeds")
public class BetterBeds
{
    public BetterBeds() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.spec);
        
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(Bedification.class);
    }

    public void setup(final FMLCommonSetupEvent event)
    {
    	//System.out.println("Setup REEEEE");
    }
}