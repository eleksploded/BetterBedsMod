package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BegGoal extends Goal {
   private final WolfEntity field_75387_a;
   private PlayerEntity field_75385_b;
   private final World field_75386_c;
   private final float minPlayerDistance;
   private int timeoutCounter;
   private final EntityPredicate field_220688_f;

   public BegGoal(WolfEntity wolf, float minDistance) {
      this.field_75387_a = wolf;
      this.field_75386_c = wolf.world;
      this.minPlayerDistance = minDistance;
      this.field_220688_f = (new EntityPredicate()).func_221013_a((double)minDistance).func_221008_a().func_221011_b().func_221009_d();
      this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      this.field_75385_b = this.field_75386_c.func_217370_a(this.field_220688_f, this.field_75387_a);
      return this.field_75385_b == null ? false : this.hasTemptationItemInHand(this.field_75385_b);
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      if (!this.field_75385_b.isAlive()) {
         return false;
      } else if (this.field_75387_a.getDistanceSq(this.field_75385_b) > (double)(this.minPlayerDistance * this.minPlayerDistance)) {
         return false;
      } else {
         return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.field_75385_b);
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_75387_a.setBegging(true);
      this.timeoutCounter = 40 + this.field_75387_a.getRNG().nextInt(40);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75387_a.setBegging(false);
      this.field_75385_b = null;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.field_75387_a.getLookHelper().setLookPosition(this.field_75385_b.posX, this.field_75385_b.posY + (double)this.field_75385_b.getEyeHeight(), this.field_75385_b.posZ, 10.0F, (float)this.field_75387_a.getVerticalFaceSpeed());
      --this.timeoutCounter;
   }

   /**
    * Gets if the Player has the Bone in the hand.
    */
   private boolean hasTemptationItemInHand(PlayerEntity player) {
      for(Hand hand : Hand.values()) {
         ItemStack itemstack = player.getHeldItem(hand);
         if (this.field_75387_a.isTamed() && itemstack.getItem() == Items.BONE) {
            return true;
         }

         if (this.field_75387_a.isBreedingItem(itemstack)) {
            return true;
         }
      }

      return false;
   }
}