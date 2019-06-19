package net.minecraft.world.gen.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CountConfig implements IFeatureConfig {
   public final int count;

   public CountConfig(int countIn) {
      this.count = countIn;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("count"), p_214634_1_.createInt(this.count))));
   }

   public static <T> CountConfig func_214687_a(Dynamic<T> p_214687_0_) {
      int i = p_214687_0_.get("count").asInt(0);
      return new CountConfig(i);
   }
}