package com.eleksploded.betterbeds;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public class BedThing {
	@SubscribeEvent
	public void place(PlaceEvent event) {
		World world = event.world;

		int x = event.x;
		int y = event.y;
		int z = event.z;

		if(!BlockEndPortalFrame.isEnderEyeInserted(world.getBlockMetadata(x, y, z))) {
			world.setBlock(x ,y ,z, Blocks.bed);
			world.setBlock(x, y, z+1, Blocks.bed, 12,3);
		}
	}
}
