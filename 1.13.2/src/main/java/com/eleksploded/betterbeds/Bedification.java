package com.eleksploded.betterbeds;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Bedification {
	
private static List<String> blacklist = new ArrayList<String>();
	
	@SubscribeEvent
	public static void place(BlockEvent.EntityPlaceEvent event) {
		IWorld world = event.getWorld();
		
		IBlockState bed = Blocks.RED_BED.getDefaultState();
		
		if(!blacklist.contains(event.getPlacedBlock().getBlock().getRegistryName().toString())) {
			if(Config.GENERAL.Hardcore.get()) {
				world.setBlockState(event.getPos(), Blocks.BEDROCK.getDefaultState(), 0);
			} else {
				world.setBlockState(event.getPos(), bed, 0);
				world.setBlockState(getHeadPos(event.getPos(), bed), bed.with(BlockBed.PART, BedPart.HEAD), 3);
			}
		}
	}
	
	private static BlockPos getHeadPos(BlockPos pos, IBlockState bed) {
	    BedPart part = bed.get(BlockBed.PART);
	    if(part == BedPart.FOOT) {
	        EnumFacing facing = bed.get(BlockBed.HORIZONTAL_FACING);
	        return pos.offset(facing);
	    }
	    return pos;
	}
	
	@SubscribeEvent
	public static void load(WorldEvent.Load event) {
		for(String string : Config.GENERAL.Blacklist.get()) {
			System.out.println(string);
			blacklist.add(string);
		}
	}
}