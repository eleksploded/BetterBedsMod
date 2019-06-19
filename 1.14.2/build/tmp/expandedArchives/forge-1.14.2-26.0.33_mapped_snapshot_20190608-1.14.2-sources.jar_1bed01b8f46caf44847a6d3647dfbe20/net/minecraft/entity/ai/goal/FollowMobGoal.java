package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;

public class FollowMobGoal extends Goal {
   private final MobEntity field_192372_a;
   private final Predicate<MobEntity> followPredicate;
   private MobEntity field_192374_c;
   private final double speedModifier;
   private final PathNavigator field_192376_e;
   private int timeToRecalcPath;
   private final float stopDistance;
   private float oldWaterCost;
   private final float areaSize;

   public FollowMobGoal(MobEntity p_i47417_1_, double p_i47417_2_, float p_i47417_4_, float p_i47417_5_) {
      this.field_192372_a = p_i47417_1_;
      this.followPredicate = (p_210291_1_) -> {
         return p_210291_1_ != null && p_i47417_1_.getClass() != p_210291_1_.getClass();
      };
      this.speedModifier = p_i47417_2_;
      this.field_192376_e = p_i47417_1_.getNavigator();
      this.stopDistance = p_i47417_4_;
      this.areaSize = p_i47417_5_;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(p_i47417_1_.getNavigator() instanceof GroundPathNavigator) && !(p_i47417_1_.getNavigator() instanceof FlyingPathNavigator)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
      }
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      List<MobEntity> list = this.field_192372_a.world.getEntitiesWithinAABB(MobEntity.class, this.field_192372_a.getBoundingBox().grow((double)this.areaSize), this.followPredicate);
      if (!list.isEmpty()) {
         for(MobEntity mobentity : list) {
            if (!mobentity.isInvisible()) {
               this.field_192374_c = mobentity;
               return true;
            }
         }
      }

      return false;
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return this.field_192374_c != null && !this.field_192376_e.noPath() && this.field_192372_a.getDistanceSq(this.field_192374_c) > (double)(this.stopDistance * this.stopDistance);
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.field_192372_a.getPathPriority(PathNodeType.WATER);
      this.field_192372_a.setPathPriority(PathNodeType.WATER, 0.0F);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_192374_c = null;
      this.field_192376_e.clearPath();
      this.field_192372_a.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (this.field_192374_c != null && !this.field_192372_a.getLeashed()) {
         this.field_192372_a.getLookHelper().setLookPositionWithEntity(this.field_192374_c, 10.0F, (float)this.field_192372_a.getVerticalFaceSpeed());
         if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = 10;
            double d0 = this.field_192372_a.posX - this.field_192374_c.posX;
            double d1 = this.field_192372_a.posY - this.field_192374_c.posY;
            double d2 = this.field_192372_a.posZ - this.field_192374_c.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (!(d3 <= (double)(this.stopDistance * this.stopDistance))) {
               this.field_192376_e.tryMoveToEntityLiving(this.field_192374_c, this.speedModifier);
            } else {
               this.field_192376_e.clearPath();
               LookController lookcontroller = this.field_192374_c.getLookHelper();
               if (d3 <= (double)this.stopDistance || lookcontroller.getLookPosX() == this.field_192372_a.posX && lookcontroller.getLookPosY() == this.field_192372_a.posY && lookcontroller.getLookPosZ() == this.field_192372_a.posZ) {
                  double d4 = this.field_192374_c.posX - this.field_192372_a.posX;
                  double d5 = this.field_192374_c.posZ - this.field_192372_a.posZ;
                  this.field_192376_e.tryMoveToXYZ(this.field_192372_a.posX - d4, this.field_192372_a.posY, this.field_192372_a.posZ - d5, this.speedModifier);
               }

            }
         }
      }
   }
}