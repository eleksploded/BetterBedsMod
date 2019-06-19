package net.minecraft.world.gen.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class FrequencyConfig implements IPlacementConfig {
   public final int frequency;

   public FrequencyConfig(int frequencyIn) {
      this.frequency = frequencyIn;
   }

   public <T> Dynamic<T> func_214719_a(DynamicOps<T> p_214719_1_) {
      return new Dynamic<>(p_214719_1_, p_214719_1_.createMap(ImmutableMap.of(p_214719_1_.createString("count"), p_214719_1_.createInt(this.frequency))));
   }

   public static FrequencyConfig func_214721_a(Dynamic<?> p_214721_0_) {
      int i = p_214721_0_.get("count").asInt(0);
      return new FrequencyConfig(i);
   }
}