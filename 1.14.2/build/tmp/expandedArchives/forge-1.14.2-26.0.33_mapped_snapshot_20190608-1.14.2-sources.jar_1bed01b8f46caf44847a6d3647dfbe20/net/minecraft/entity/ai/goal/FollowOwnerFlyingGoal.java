package net.minecraft.entity.ai.goal;

import net.minecraft.block.BlockState;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;

public class FollowOwnerFlyingGoal extends FollowOwnerGoal {
   public FollowOwnerFlyingGoal(TameableEntity tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
      super(tameableIn, followSpeedIn, minDistIn, maxDistIn);
   }

   protected boolean func_220707_a(BlockPos p_220707_1_) {
      BlockState blockstate = this.field_75342_a.getBlockState(p_220707_1_);
      return (blockstate.func_215682_a(this.field_75342_a, p_220707_1_, this.field_75338_d) || blockstate.isIn(BlockTags.LEAVES)) && this.field_75342_a.isAirBlock(p_220707_1_.up()) && this.field_75342_a.isAirBlock(p_220707_1_.up(2));
   }
}