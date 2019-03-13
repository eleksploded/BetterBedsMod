package com.eleksploded.betterbeds;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BedThing {
	
	private static List<String> blacklist = new ArrayList<String>();
	
	@SubscribeEvent
	public static void place(PlaceEvent event) {
		World world = event.world;
		
		IBlockState bed = Blocks.bed.getDefaultState();
		
		if(!blacklist.contains(event.placedBlock.getBlock().getRegistryName().toString())) {
			if(Config.hardcore) {
				world.setBlockState(event.pos, Blocks.bedrock.getDefaultState());
			} else {
				world.setBlockState(event.pos, bed);
				world.setBlockState(getHeadPos(event.pos, bed), bed.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 3);
			}
		}
	}
	
	private static BlockPos getHeadPos(BlockPos pos, IBlockState bed) {
	    BlockBed.EnumPartType part = bed.getValue(BlockBed.PART);
	    if(part == BlockBed.EnumPartType.FOOT) {
	        EnumFacing facing = bed.getValue(BlockBed.FACING);
	        return pos.offset(facing);
	    }
	    return pos;
	}
	
	@SubscribeEvent
	public static void load(WorldEvent.Load event) {
		for(String string : Config.blacklist) {
			System.out.println(string);
			blacklist.add(string);
		}
	}
}
