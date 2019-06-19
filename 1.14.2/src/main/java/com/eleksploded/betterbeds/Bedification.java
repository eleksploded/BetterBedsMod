package com.eleksploded.betterbeds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.Direction;
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
		BlockState bed;
		if(Config.GENERAL.randBed.get()) {
			bed = getRandBed();
		} else {
			bed = Blocks.WHITE_BED.getDefaultState();
		}
		
		if(!blacklist.contains(event.getPlacedBlock().getBlock().getRegistryName().toString())) {
			if(Config.GENERAL.Hardcore.get()) {
				world.setBlockState(event.getPos(), Blocks.BEDROCK.getDefaultState(), 0);
			} else {
				world.setBlockState(event.getPos(), bed, 0);
				world.setBlockState(getHeadPos(event.getPos(), bed), bed.with(BedBlock.PART, BedPart.HEAD), 3);
			}
		}
	}
	
	private static BlockState getRandBed() {
		Random rand = new Random();
		int i = rand.nextInt(16);
		switch(i){
		case 0:
			return Blocks.WHITE_BED.getDefaultState();
		case 1:
			return Blocks.ORANGE_BED.getDefaultState();
		case 2:
			return Blocks.MAGENTA_BED.getDefaultState();
		case 3:
			return Blocks.LIGHT_BLUE_BED.getDefaultState();
		case 4:
			return Blocks.YELLOW_BED.getDefaultState();
		case 5:
			return Blocks.LIME_BED.getDefaultState();
		case 6:
			return Blocks.PINK_BED.getDefaultState();
		case 7:
			return Blocks.GRAY_BED.getDefaultState();
		case 8:
			return Blocks.LIGHT_GRAY_BED.getDefaultState();
		case 9:
			return Blocks.CYAN_BED.getDefaultState();
		case 10:
			return Blocks.PURPLE_BED.getDefaultState();
		case 11:
			return Blocks.BLUE_BED.getDefaultState();
		case 12:
			return Blocks.BROWN_BED.getDefaultState();
		case 13:
			return Blocks.GREEN_BED.getDefaultState();
		case 14:
			return Blocks.RED_BED.getDefaultState();
		case 15:
			return Blocks.BLACK_BED.getDefaultState();
		default:
			return Blocks.WHITE_BED.getDefaultState();
		}
			
	}

	private static BlockPos getHeadPos(BlockPos pos, BlockState bed) {
	    BedPart part = bed.get(BedBlock.PART);
	    if(part == BedPart.FOOT) {
	        Direction facing = bed.get(BedBlock.HORIZONTAL_FACING);
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
