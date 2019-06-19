package net.minecraft.entity.monster;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class StrayEntity extends AbstractSkeletonEntity {
   public StrayEntity(EntityType<? extends StrayEntity> p_i50191_1_, World p_i50191_2_) {
      super(p_i50191_1_, p_i50191_2_);
   }

   public boolean canSpawn(IWorld p_213380_1_, SpawnReason p_213380_2_) {
      return super.canSpawn(p_213380_1_, p_213380_2_) && (p_213380_2_ == SpawnReason.SPAWNER || p_213380_1_.func_217337_f(new BlockPos(this)));
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_STRAY_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_STRAY_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_STRAY_DEATH;
   }

   protected SoundEvent getStepSound() {
      return SoundEvents.ENTITY_STRAY_STEP;
   }

   protected AbstractArrowEntity func_213624_b(ItemStack p_213624_1_, float p_213624_2_) {
      AbstractArrowEntity abstractarrowentity = super.func_213624_b(p_213624_1_, p_213624_2_);
      if (abstractarrowentity instanceof ArrowEntity) {
         ((ArrowEntity)abstractarrowentity).addEffect(new EffectInstance(Effects.field_76421_d, 600));
      }

      return abstractarrowentity;
   }
}