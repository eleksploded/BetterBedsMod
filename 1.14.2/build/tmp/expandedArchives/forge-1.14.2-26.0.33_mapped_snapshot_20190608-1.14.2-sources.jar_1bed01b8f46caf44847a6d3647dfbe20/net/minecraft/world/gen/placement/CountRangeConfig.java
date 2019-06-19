package net.minecraft.world.gen.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class CountRangeConfig implements IPlacementConfig {
   public final int count;
   public final int minHeight;
   public final int maxHeightBase;
   public final int maxHeight;

   public CountRangeConfig(int countIn, int minHeightIn, int maxHeightBaseIn, int maxHeightIn) {
      this.count = countIn;
      this.minHeight = minHeightIn;
      this.maxHeightBase = maxHeightBaseIn;
      this.maxHeight = maxHeightIn;
   }

   public <T> Dynamic<T> func_214719_a(DynamicOps<T> p_214719_1_) {
      return new Dynamic<>(p_214719_1_, p_214719_1_.createMap(ImmutableMap.of(p_214719_1_.createString("count"), p_214719_1_.createInt(this.count), p_214719_1_.createString("bottom_offset"), p_214719_1_.createInt(this.minHeight), p_214719_1_.createString("top_offset"), p_214719_1_.createInt(this.maxHeightBase), p_214719_1_.createString("maximum"), p_214719_1_.createInt(this.maxHeight))));
   }

   public static CountRangeConfig func_214733_a(Dynamic<?> p_214733_0_) {
      int i = p_214733_0_.get("count").asInt(0);
      int j = p_214733_0_.get("bottom_offset").asInt(0);
      int k = p_214733_0_.get("top_offset").asInt(0);
      int l = p_214733_0_.get("maximum").asInt(0);
      return new CountRangeConfig(i, j, k, l);
   }
}