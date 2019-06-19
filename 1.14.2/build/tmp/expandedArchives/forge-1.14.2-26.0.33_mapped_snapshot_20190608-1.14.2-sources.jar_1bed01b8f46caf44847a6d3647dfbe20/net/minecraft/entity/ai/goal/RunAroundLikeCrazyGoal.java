package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class RunAroundLikeCrazyGoal extends Goal {
   private final AbstractHorseEntity field_111180_a;
   private final double speed;
   private double targetX;
   private double targetY;
   private double targetZ;

   public RunAroundLikeCrazyGoal(AbstractHorseEntity horse, double speedIn) {
      this.field_111180_a = horse;
      this.speed = speedIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (!this.field_111180_a.isTame() && this.field_111180_a.isBeingRidden()) {
         Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.field_111180_a, 5, 4);
         if (vec3d == null) {
            return false;
         } else {
            this.targetX = vec3d.x;
            this.targetY = vec3d.y;
            this.targetZ = vec3d.z;
            return true;
         }
      } else {
         return false;
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_111180_a.getNavigator().tryMoveToXYZ(this.targetX, this.targetY, this.targetZ, this.speed);
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return !this.field_111180_a.isTame() && !this.field_111180_a.getNavigator().noPath() && this.field_111180_a.isBeingRidden();
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (!this.field_111180_a.isTame() && this.field_111180_a.getRNG().nextInt(50) == 0) {
         Entity entity = this.field_111180_a.getPassengers().get(0);
         if (entity == null) {
            return;
         }

         if (entity instanceof PlayerEntity) {
            int i = this.field_111180_a.getTemper();
            int j = this.field_111180_a.getMaxTemper();
            if (j > 0 && this.field_111180_a.getRNG().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(field_111180_a, (PlayerEntity)entity)) {
               this.field_111180_a.setTamedBy((PlayerEntity)entity);
               return;
            }

            this.field_111180_a.increaseTemper(5);
         }

         this.field_111180_a.removePassengers();
         this.field_111180_a.makeMad();
         this.field_111180_a.world.setEntityState(this.field_111180_a, (byte)6);
      }

   }
}