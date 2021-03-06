package com.eleksploded.betterbeds;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BedThing {
	
	private static List<String> blacklist = new ArrayList<String>();
	
	@SubscribeEvent
	public static void place(PlaceEvent event) {
		World world = event.getWorld();
		if(world.isRemote) { return; }
		
		IBlockState bed = Blocks.BED.getDefaultState();
		
		if(!blacklist.contains(event.getPlacedBlock().getBlock().getRegistryName().toString())) {
			if(Config.hardcore) {
				world.setBlockState(event.getPos(), Blocks.BEDROCK.getDefaultState());
			} else {
				world.setBlockState(event.getPos(), bed);
				world.setBlockState(getHeadPos(event.getPos(), bed), bed.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 3);
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
		if(event.getWorld().isRemote) { return; }
		for(String string : Config.blacklist) {
			System.out.println(string);
			blacklist.add(string);
		}
	}
}
