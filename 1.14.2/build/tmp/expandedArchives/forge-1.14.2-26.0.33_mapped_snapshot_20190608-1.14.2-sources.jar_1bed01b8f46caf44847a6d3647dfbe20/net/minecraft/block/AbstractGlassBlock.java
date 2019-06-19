package net.minecraft.block;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractGlassBlock extends BreakableBlock {
   protected AbstractGlassBlock(Block.Properties p_i49999_1_) {
      super(p_i49999_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public float func_220080_a(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
      return 1.0F;
   }

   public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
      return true;
   }

   public boolean func_220060_c(BlockState p_220060_1_, IBlockReader p_220060_2_, BlockPos p_220060_3_) {
      return false;
   }

   public boolean func_220081_d(BlockState p_220081_1_, IBlockReader p_220081_2_, BlockPos p_220081_3_) {
      return false;
   }

   public boolean canEntitySpawn(BlockState p_220067_1_, IBlockReader p_220067_2_, BlockPos p_220067_3_, EntityType<?> p_220067_4_) {
      return false;
   }
}