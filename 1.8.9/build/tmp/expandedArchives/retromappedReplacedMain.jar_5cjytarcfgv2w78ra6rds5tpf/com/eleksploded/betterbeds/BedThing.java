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
		IBlockState bed = Blocks.field_150324_C.func_176223_P();
				
		if(event.placedBlock != Blocks.field_150378_br.func_176223_P().func_177226_a(BlockEndPortalFrame.field_176507_b, true)) {
			world.func_175656_a(event.pos, bed);
			world.func_180501_a(getHeadPos(event.pos, bed), bed.func_177226_a(BlockBed.field_176472_a, BlockBed.EnumPartType.HEAD), 3);
		}
	}
	
	private static BlockPos getHeadPos(BlockPos pos, IBlockState bed) {
	    BlockBed.EnumPartType part = bed.func_177229_b(BlockBed.field_176472_a);
	    if(part == BlockBed.EnumPartType.FOOT) {
	        EnumFacing facing = bed.func_177229_b(BlockBed.field_176387_N);
	        return pos.func_177972_a(facing);
	    }
	    return pos;
	}
}
