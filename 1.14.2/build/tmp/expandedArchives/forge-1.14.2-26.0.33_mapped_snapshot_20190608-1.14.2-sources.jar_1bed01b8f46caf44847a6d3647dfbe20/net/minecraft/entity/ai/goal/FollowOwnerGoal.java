package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class FollowOwnerGoal extends Goal {
   protected final TameableEntity field_75338_d;
   private LivingEntity field_75339_e;
   protected final IWorldReader field_75342_a;
   private final double followSpeed;
   private final PathNavigator field_75337_g;
   private int timeToRecalcPath;
   private final float maxDist;
   private final float minDist;
   private float oldWaterCost;

   public FollowOwnerGoal(TameableEntity tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
      this.field_75338_d = tameableIn;
      this.field_75342_a = tameableIn.world;
      this.followSpeed = followSpeedIn;
      this.field_75337_g = tameableIn.getNavigator();
      this.minDist = minDistIn;
      this.maxDist = maxDistIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(tameableIn.getNavigator() instanceof GroundPathNavigator) && !(tameableIn.getNavigator() instanceof FlyingPathNavigator)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      LivingEntity livingentity = this.field_75338_d.getOwner();
      if (livingentity == null) {
         return false;
      } else if (livingentity instanceof PlayerEntity && ((PlayerEntity)livingentity).isSpectator()) {
         return false;
      } else if (this.field_75338_d.isSitting()) {
         return false;
      } else if (this.field_75338_d.getDistanceSq(livingentity) < (double)(this.minDist * this.minDist)) {
         return false;
      } else {
         this.field_75339_e = livingentity;
         return true;
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return !this.field_75337_g.noPath() && this.field_75338_d.getDistanceSq(this.field_75339_e) > (double)(this.maxDist * this.maxDist) && !this.field_75338_d.isSitting();
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.field_75338_d.getPathPriority(PathNodeType.WATER);
      this.field_75338_d.setPathPriority(PathNodeType.WATER, 0.0F);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75339_e = null;
      this.field_75337_g.clearPath();
      this.field_75338_d.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.field_75338_d.getLookHelper().setLookPositionWithEntity(this.field_75339_e, 10.0F, (float)this.field_75338_d.getVerticalFaceSpeed());
      if (!this.field_75338_d.isSitting()) {
         if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            if (!this.field_75337_g.tryMoveToEntityLiving(this.field_75339_e, this.followSpeed)) {
               if (!this.field_75338_d.getLeashed() && !this.field_75338_d.isPassenger()) {
                  if (!(this.field_75338_d.getDistanceSq(this.field_75339_e) < 144.0D)) {
                     int i = MathHelper.floor(this.field_75339_e.posX) - 2;
                     int j = MathHelper.floor(this.field_75339_e.posZ) - 2;
                     int k = MathHelper.floor(this.field_75339_e.getBoundingBox().minY);

                     for(int l = 0; l <= 4; ++l) {
                        for(int i1 = 0; i1 <= 4; ++i1) {
                           if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.func_220707_a(new BlockPos(i + l, k - 1, j + i1))) {
                              this.field_75338_d.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.field_75338_d.rotationYaw, this.field_75338_d.rotationPitch);
                              this.field_75337_g.clearPath();
                              return;
                           }
                        }
                     }

                  }
               }
            }
         }
      }
   }

   protected boolean func_220707_a(BlockPos p_220707_1_) {
      BlockState blockstate = this.field_75342_a.getBlockState(p_220707_1_);
      return blockstate.canEntitySpawn(this.field_75342_a, p_220707_1_, this.field_75338_d.getType()) && this.field_75342_a.isAirBlock(p_220707_1_.up()) && this.field_75342_a.isAirBlock(p_220707_1_.up(2));
   }
}