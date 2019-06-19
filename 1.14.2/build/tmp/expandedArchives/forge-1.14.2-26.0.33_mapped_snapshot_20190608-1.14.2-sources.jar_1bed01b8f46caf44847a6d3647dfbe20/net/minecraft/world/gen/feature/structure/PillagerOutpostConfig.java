package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class PillagerOutpostConfig implements IFeatureConfig {
   public final double field_214643_a;

   public PillagerOutpostConfig(double p_i51471_1_) {
      this.field_214643_a = p_i51471_1_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("probability"), p_214634_1_.createDouble(this.field_214643_a))));
   }

   public static <T> PillagerOutpostConfig func_214642_a(Dynamic<T> p_214642_0_) {
      float f = p_214642_0_.get("probability").asFloat(0.0F);
      return new PillagerOutpostConfig((double)f);
   }
}