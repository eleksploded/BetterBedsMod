package net.minecraft.block;

import net.minecraft.potion.Effect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class FlowerBlock extends BushBlock {
   protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
   private final Effect field_220096_b;
   private final int field_220097_c;

   public FlowerBlock(Effect p_i49984_1_, int p_i49984_2_, Block.Properties p_i49984_3_) {
      super(p_i49984_3_);
      this.field_220096_b = p_i49984_1_;
      if (p_i49984_1_.isInstant()) {
         this.field_220097_c = p_i49984_2_;
      } else {
         this.field_220097_c = p_i49984_2_ * 20;
      }

   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      Vec3d vec3d = p_220053_1_.getOffset(p_220053_2_, p_220053_3_);
      return SHAPE.withOffset(vec3d.x, vec3d.y, vec3d.z);
   }

   /**
    * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
    */
   public Block.OffsetType getOffsetType() {
      return Block.OffsetType.XZ;
   }

   public Effect func_220094_d() {
      return this.field_220096_b;
   }

   public int func_220095_e() {
      return this.field_220097_c;
   }
}