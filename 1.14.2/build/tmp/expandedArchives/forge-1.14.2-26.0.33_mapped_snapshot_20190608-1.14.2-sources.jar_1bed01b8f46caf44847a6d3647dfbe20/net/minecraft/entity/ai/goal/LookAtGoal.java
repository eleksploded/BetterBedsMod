package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

public class LookAtGoal extends Goal {
   protected final MobEntity field_75332_b;
   protected Entity closestEntity;
   protected final float maxDistance;
   private int lookTime;
   private final float chance;
   protected final Class<? extends LivingEntity> watchedClass;
   protected final EntityPredicate field_220716_e;

   public LookAtGoal(MobEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
      this(entityIn, watchTargetClass, maxDistance, 0.02F);
   }

   public LookAtGoal(MobEntity entityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance, float chanceIn) {
      this.field_75332_b = entityIn;
      this.watchedClass = watchTargetClass;
      this.maxDistance = maxDistance;
      this.chance = chanceIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
      if (watchTargetClass == PlayerEntity.class) {
         this.field_220716_e = (new EntityPredicate()).func_221013_a((double)maxDistance).func_221011_b().func_221008_a().func_221009_d().func_221012_a((p_220715_1_) -> {
            return EntityPredicates.notRiding(entityIn).test(p_220715_1_);
         });
      } else {
         this.field_220716_e = (new EntityPredicate()).func_221013_a((double)maxDistance).func_221011_b().func_221008_a().func_221009_d();
      }

   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (this.field_75332_b.getRNG().nextFloat() >= this.chance) {
         return false;
      } else {
         if (this.field_75332_b.getAttackTarget() != null) {
            this.closestEntity = this.field_75332_b.getAttackTarget();
         }

         if (this.watchedClass == PlayerEntity.class) {
            this.closestEntity = this.field_75332_b.world.func_217372_a(this.field_220716_e, this.field_75332_b, this.field_75332_b.posX, this.field_75332_b.posY + (double)this.field_75332_b.getEyeHeight(), this.field_75332_b.posZ);
         } else {
            this.closestEntity = this.field_75332_b.world.func_217360_a(this.watchedClass, this.field_220716_e, this.field_75332_b, this.field_75332_b.posX, this.field_75332_b.posY + (double)this.field_75332_b.getEyeHeight(), this.field_75332_b.posZ, this.field_75332_b.getBoundingBox().grow((double)this.maxDistance, 3.0D, (double)this.maxDistance));
         }

         return this.closestEntity != null;
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      if (!this.closestEntity.isAlive()) {
         return false;
      } else if (this.field_75332_b.getDistanceSq(this.closestEntity) > (double)(this.maxDistance * this.maxDistance)) {
         return false;
      } else {
         return this.lookTime > 0;
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.lookTime = 40 + this.field_75332_b.getRNG().nextInt(40);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.closestEntity = null;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.field_75332_b.getLookHelper().func_220679_a(this.closestEntity.posX, this.closestEntity.posY + (double)this.closestEntity.getEyeHeight(), this.closestEntity.posZ);
      --this.lookTime;
   }
}