package net.minecraft.world.gen.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class NoiseDependant implements IPlacementConfig {
   public final double noiseThreshold;
   public final int lowNoiseCount;
   public final int highNoiseCount;

   public NoiseDependant(double noiseThresholdIn, int lowNoiseCountIn, int highNoiseCountIn) {
      this.noiseThreshold = noiseThresholdIn;
      this.lowNoiseCount = lowNoiseCountIn;
      this.highNoiseCount = highNoiseCountIn;
   }

   public <T> Dynamic<T> func_214719_a(DynamicOps<T> p_214719_1_) {
      return new Dynamic<>(p_214719_1_, p_214719_1_.createMap(ImmutableMap.of(p_214719_1_.createString("noise_level"), p_214719_1_.createDouble(this.noiseThreshold), p_214719_1_.createString("below_noise"), p_214719_1_.createInt(this.lowNoiseCount), p_214719_1_.createString("above_noise"), p_214719_1_.createInt(this.highNoiseCount))));
   }

   public static NoiseDependant func_214734_a(Dynamic<?> p_214734_0_) {
      double d0 = p_214734_0_.get("noise_level").asDouble(0.0D);
      int i = p_214734_0_.get("below_noise").asInt(0);
      int j = p_214734_0_.get("above_noise").asInt(0);
      return new NoiseDependant(d0, i, j);
   }
}