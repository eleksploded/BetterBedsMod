package net.minecraft.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class EffectUtils {
   @OnlyIn(Dist.CLIENT)
   public static String getPotionDurationString(EffectInstance effect, float durationFactor) {
      if (effect.getIsPotionDurationMax()) {
         return "**:**";
      } else {
         int i = MathHelper.floor((float)effect.getDuration() * durationFactor);
         return StringUtils.ticksToElapsedTime(i);
      }
   }

   public static boolean hasMiningSpeedup(LivingEntity p_205135_0_) {
      return p_205135_0_.isPotionActive(Effects.field_76422_e) || p_205135_0_.isPotionActive(Effects.field_205136_C);
   }

   public static int getMiningSpeedup(LivingEntity p_205134_0_) {
      int i = 0;
      int j = 0;
      if (p_205134_0_.isPotionActive(Effects.field_76422_e)) {
         i = p_205134_0_.getActivePotionEffect(Effects.field_76422_e).getAmplifier();
      }

      if (p_205134_0_.isPotionActive(Effects.field_205136_C)) {
         j = p_205134_0_.getActivePotionEffect(Effects.field_205136_C).getAmplifier();
      }

      return Math.max(i, j);
   }

   public static boolean canBreatheUnderwater(LivingEntity p_205133_0_) {
      return p_205133_0_.isPotionActive(Effects.field_76427_o) || p_205133_0_.isPotionActive(Effects.field_205136_C);
   }
}