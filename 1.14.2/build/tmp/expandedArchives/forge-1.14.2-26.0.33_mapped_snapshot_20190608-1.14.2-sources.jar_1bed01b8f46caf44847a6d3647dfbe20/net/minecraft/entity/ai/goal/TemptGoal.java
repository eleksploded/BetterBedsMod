package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.GroundPathNavigator;

public class TemptGoal extends Goal {
   private static final EntityPredicate field_220762_c = (new EntityPredicate()).func_221013_a(10.0D).func_221008_a().func_221011_b().func_221009_d().func_221014_c();
   protected final CreatureEntity field_75284_a;
   private final double speed;
   private double targetX;
   private double targetY;
   private double targetZ;
   private double pitch;
   private double yaw;
   protected PlayerEntity field_75289_h;
   private int delayTemptCounter;
   private boolean isRunning;
   private final Ingredient temptItem;
   private final boolean scaredByPlayerMovement;

   public TemptGoal(CreatureEntity p_i47822_1_, double p_i47822_2_, Ingredient p_i47822_4_, boolean p_i47822_5_) {
      this(p_i47822_1_, p_i47822_2_, p_i47822_5_, p_i47822_4_);
   }

   public TemptGoal(CreatureEntity p_i47823_1_, double p_i47823_2_, boolean p_i47823_4_, Ingredient p_i47823_5_) {
      this.field_75284_a = p_i47823_1_;
      this.speed = p_i47823_2_;
      this.temptItem = p_i47823_5_;
      this.scaredByPlayerMovement = p_i47823_4_;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(p_i47823_1_.getNavigator() instanceof GroundPathNavigator)) {
         throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
      }
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (this.delayTemptCounter > 0) {
         --this.delayTemptCounter;
         return false;
      } else {
         this.field_75289_h = this.field_75284_a.world.func_217370_a(field_220762_c, this.field_75284_a);
         if (this.field_75289_h == null) {
            return false;
         } else {
            return this.isTempting(this.field_75289_h.getHeldItemMainhand()) || this.isTempting(this.field_75289_h.getHeldItemOffhand());
         }
      }
   }

   protected boolean isTempting(ItemStack stack) {
      return this.temptItem.test(stack);
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      if (this.func_220761_g()) {
         if (this.field_75284_a.getDistanceSq(this.field_75289_h) < 36.0D) {
            if (this.field_75289_h.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D) {
               return false;
            }

            if (Math.abs((double)this.field_75289_h.rotationPitch - this.pitch) > 5.0D || Math.abs((double)this.field_75289_h.rotationYaw - this.yaw) > 5.0D) {
               return false;
            }
         } else {
            this.targetX = this.field_75289_h.posX;
            this.targetY = this.field_75289_h.posY;
            this.targetZ = this.field_75289_h.posZ;
         }

         this.pitch = (double)this.field_75289_h.rotationPitch;
         this.yaw = (double)this.field_75289_h.rotationYaw;
      }

      return this.shouldExecute();
   }

   protected boolean func_220761_g() {
      return this.scaredByPlayerMovement;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.targetX = this.field_75289_h.posX;
      this.targetY = this.field_75289_h.posY;
      this.targetZ = this.field_75289_h.posZ;
      this.isRunning = true;
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75289_h = null;
      this.field_75284_a.getNavigator().clearPath();
      this.delayTemptCounter = 100;
      this.isRunning = false;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.field_75284_a.getLookHelper().setLookPositionWithEntity(this.field_75289_h, (float)(this.field_75284_a.getHorizontalFaceSpeed() + 20), (float)this.field_75284_a.getVerticalFaceSpeed());
      if (this.field_75284_a.getDistanceSq(this.field_75289_h) < 6.25D) {
         this.field_75284_a.getNavigator().clearPath();
      } else {
         this.field_75284_a.getNavigator().tryMoveToEntityLiving(this.field_75289_h, this.speed);
      }

   }

   /**
    * @see #isRunning
    */
   public boolean isRunning() {
      return this.isRunning;
   }
}