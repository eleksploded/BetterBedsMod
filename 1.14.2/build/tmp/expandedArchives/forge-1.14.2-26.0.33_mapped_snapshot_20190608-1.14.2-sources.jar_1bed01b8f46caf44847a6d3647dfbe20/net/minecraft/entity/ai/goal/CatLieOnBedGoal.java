package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CatLieOnBedGoal extends MoveToBlockGoal {
   private final CatEntity field_220727_g;

   public CatLieOnBedGoal(CatEntity p_i50331_1_, double p_i50331_2_, int p_i50331_4_) {
      super(p_i50331_1_, p_i50331_2_, p_i50331_4_, 6);
      this.field_220727_g = p_i50331_1_;
      this.field_203112_e = -2;
      this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      return this.field_220727_g.isTamed() && !this.field_220727_g.isSitting() && !this.field_220727_g.func_213416_eg() && super.shouldExecute();
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      super.startExecuting();
      this.field_220727_g.getAISit().setSitting(false);
   }

   protected int getRunDelay(CreatureEntity p_203109_1_) {
      return 40;
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      super.resetTask();
      this.field_220727_g.func_213419_u(false);
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      super.tick();
      this.field_220727_g.getAISit().setSitting(false);
      if (!this.getIsAboveDestination()) {
         this.field_220727_g.func_213419_u(false);
      } else if (!this.field_220727_g.func_213416_eg()) {
         this.field_220727_g.func_213419_u(true);
      }

   }

   /**
    * Return true to set given position as destination
    */
   protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
      return worldIn.isAirBlock(pos.up()) && worldIn.getBlockState(pos).getBlock().isIn(BlockTags.field_219747_F);
   }
}