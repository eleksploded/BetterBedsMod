package com.eleksploded.betterbeds;

import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BedThing {
	@SubscribeEvent
	public void place(PlaceEvent event) {
		World world = event.world;
		IBlockState bed = Blocks.bed.getDefaultState();
				
		if(event.placedBlock != Blocks.end_portal_frame.getDefaultState().withProperty(BlockEndPortalFrame.EYE, true)) {
			world.setBlockState(event.pos, bed);
			world.setBlockState(getHeadPos(event.pos, bed), bed.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 3);
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
}
