package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class GolemEntity extends CreatureEntity {
   protected GolemEntity(EntityType<? extends GolemEntity> p_i48569_1_, World p_i48569_2_) {
      super(p_i48569_1_, p_i48569_2_);
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return null;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return null;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return null;
   }

   /**
    * Get number of ticks, at least during which the living entity will be silent.
    */
   public int getTalkInterval() {
      return 120;
   }

   public boolean func_213397_c(double p_213397_1_) {
      return false;
   }
}