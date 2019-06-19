package net.minecraft.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorld;

@net.minecraftforge.registries.ObjectHolder("minecraft")
public class Effects {
   public static final Effect field_76424_c = func_220308_a(1, "speed", (new Effect(EffectType.BENEFICIAL, 8171462)).func_220304_a(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));
   public static final Effect field_76421_d = func_220308_a(2, "slowness", (new Effect(EffectType.HARMFUL, 5926017)).func_220304_a(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));
   public static final Effect field_76422_e = func_220308_a(3, "haste", (new Effect(EffectType.BENEFICIAL, 14270531)).func_220304_a(SharedMonsterAttributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", (double)0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));
   public static final Effect field_76419_f = func_220308_a(4, "mining_fatigue", (new Effect(EffectType.HARMFUL, 4866583)).func_220304_a(SharedMonsterAttributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));
   public static final Effect field_76420_g = func_220308_a(5, "strength", (new AttackDamageEffect(EffectType.BENEFICIAL, 9643043, 3.0D)).func_220304_a(SharedMonsterAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, AttributeModifier.Operation.ADDITION));
   public static final Effect field_76432_h = func_220308_a(6, "instant_health", new InstantEffect(EffectType.BENEFICIAL, 16262179));
   public static final Effect field_76433_i = func_220308_a(7, "instant_damage", new InstantEffect(EffectType.HARMFUL, 4393481));
   public static final Effect field_76430_j = func_220308_a(8, "jump_boost", new Effect(EffectType.BENEFICIAL, 2293580));
   public static final Effect field_76431_k = func_220308_a(9, "nausea", new Effect(EffectType.HARMFUL, 5578058));
   public static final Effect field_76428_l = func_220308_a(10, "regeneration", new Effect(EffectType.BENEFICIAL, 13458603));
   public static final Effect field_76429_m = func_220308_a(11, "resistance", new Effect(EffectType.BENEFICIAL, 10044730));
   public static final Effect field_76426_n = func_220308_a(12, "fire_resistance", new Effect(EffectType.BENEFICIAL, 14981690));
   public static final Effect field_76427_o = func_220308_a(13, "water_breathing", new Effect(EffectType.BENEFICIAL, 3035801));
   public static final Effect field_76441_p = func_220308_a(14, "invisibility", new Effect(EffectType.BENEFICIAL, 8356754));
   public static final Effect field_76440_q = func_220308_a(15, "blindness", new Effect(EffectType.HARMFUL, 2039587));
   public static final Effect field_76439_r = func_220308_a(16, "night_vision", new Effect(EffectType.BENEFICIAL, 2039713));
   public static final Effect field_76438_s = func_220308_a(17, "hunger", new Effect(EffectType.HARMFUL, 5797459));
   public static final Effect field_76437_t = func_220308_a(18, "weakness", (new AttackDamageEffect(EffectType.HARMFUL, 4738376, -4.0D)).func_220304_a(SharedMonsterAttributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION));
   public static final Effect field_76436_u = func_220308_a(19, "poison", new Effect(EffectType.HARMFUL, 5149489));
   public static final Effect field_82731_v = func_220308_a(20, "wither", new Effect(EffectType.HARMFUL, 3484199));
   public static final Effect field_180152_w = func_220308_a(21, "health_boost", (new HealthBoostEffect(EffectType.BENEFICIAL, 16284963)).func_220304_a(SharedMonsterAttributes.MAX_HEALTH, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, AttributeModifier.Operation.ADDITION));
   public static final Effect field_76444_x = func_220308_a(22, "absorption", new AbsorptionEffect(EffectType.BENEFICIAL, 2445989));
   public static final Effect field_76443_y = func_220308_a(23, "saturation", new InstantEffect(EffectType.BENEFICIAL, 16262179));
   public static final Effect field_188423_x = func_220308_a(24, "glowing", new Effect(EffectType.NEUTRAL, 9740385));
   public static final Effect field_188424_y = func_220308_a(25, "levitation", new Effect(EffectType.HARMFUL, 13565951));
   public static final Effect field_188425_z = func_220308_a(26, "luck", (new Effect(EffectType.BENEFICIAL, 3381504)).func_220304_a(SharedMonsterAttributes.LUCK, "03C3C89D-7037-4B42-869F-B146BCB64D2E", 1.0D, AttributeModifier.Operation.ADDITION));
   public static final Effect field_189112_A = func_220308_a(27, "unluck", (new Effect(EffectType.HARMFUL, 12624973)).func_220304_a(SharedMonsterAttributes.LUCK, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0D, AttributeModifier.Operation.ADDITION));
   public static final Effect field_204839_B = func_220308_a(28, "slow_falling", new Effect(EffectType.BENEFICIAL, 16773073));
   public static final Effect field_205136_C = func_220308_a(29, "conduit_power", new Effect(EffectType.BENEFICIAL, 1950417));
   public static final Effect field_206827_D = func_220308_a(30, "dolphins_grace", new Effect(EffectType.BENEFICIAL, 8954814));
   public static final Effect field_220309_E = func_220308_a(31, "bad_omen", new Effect(EffectType.NEUTRAL, 745784) {
      /**
       * checks if Potion effect is ready to be applied this tick.
       */
      public boolean isReady(int duration, int amplifier) {
         return true;
      }

      public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
         if (entityLivingBaseIn instanceof ServerPlayerEntity && !entityLivingBaseIn.isSpectator()) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entityLivingBaseIn;
            ServerWorld serverworld = serverplayerentity.getServerWorld();
            if (serverworld.getDifficulty() == Difficulty.PEACEFUL) {
               return;
            }

            if (serverworld.func_217483_b_(new BlockPos(entityLivingBaseIn))) {
               serverworld.func_217452_C().badOmenTick(serverplayerentity);
            }
         }

      }
   });
   public static final Effect field_220310_F = func_220308_a(32, "hero_of_the_village", new Effect(EffectType.BENEFICIAL, 4521796));

   private static Effect func_220308_a(int p_220308_0_, String p_220308_1_, Effect p_220308_2_) {
      return Registry.register(Registry.field_212631_t, p_220308_0_, p_220308_1_, p_220308_2_);
   }
}