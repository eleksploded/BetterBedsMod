package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class BigMushroomFeatureConfig implements IFeatureConfig {
   public final boolean field_222854_a;

   public BigMushroomFeatureConfig(boolean p_i51513_1_) {
      this.field_222854_a = p_i51513_1_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("planted"), p_214634_1_.createBoolean(this.field_222854_a))));
   }

   public static <T> BigMushroomFeatureConfig func_222853_a(Dynamic<T> p_222853_0_) {
      boolean flag = p_222853_0_.get("planted").asBoolean(false);
      return new BigMushroomFeatureConfig(flag);
   }
}