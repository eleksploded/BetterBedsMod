package net.minecraft.world.gen.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class TopSolidRangeConfig implements IPlacementConfig {
   public final int minCount;
   public final int maxCount;

   public TopSolidRangeConfig(int p_i51375_1_, int p_i51375_2_) {
      this.minCount = p_i51375_1_;
      this.maxCount = p_i51375_2_;
   }

   public <T> Dynamic<T> func_214719_a(DynamicOps<T> p_214719_1_) {
      return new Dynamic<>(p_214719_1_, p_214719_1_.createMap(ImmutableMap.of(p_214719_1_.createString("min"), p_214719_1_.createInt(this.minCount), p_214719_1_.createString("max"), p_214719_1_.createInt(this.maxCount))));
   }

   public static TopSolidRangeConfig func_214725_a(Dynamic<?> p_214725_0_) {
      int i = p_214725_0_.get("min").asInt(0);
      int j = p_214725_0_.get("max").asInt(0);
      return new TopSolidRangeConfig(i, j);
   }
}