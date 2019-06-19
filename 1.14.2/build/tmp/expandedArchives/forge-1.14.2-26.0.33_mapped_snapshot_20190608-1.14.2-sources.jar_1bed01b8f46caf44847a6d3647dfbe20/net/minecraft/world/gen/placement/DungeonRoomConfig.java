package net.minecraft.world.gen.placement;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class DungeonRoomConfig implements IPlacementConfig {
   public final int count;

   public DungeonRoomConfig(int countIn) {
      this.count = countIn;
   }

   public <T> Dynamic<T> func_214719_a(DynamicOps<T> p_214719_1_) {
      return new Dynamic<>(p_214719_1_, p_214719_1_.createMap(ImmutableMap.of(p_214719_1_.createString("chance"), p_214719_1_.createInt(this.count))));
   }

   public static DungeonRoomConfig func_214731_a(Dynamic<?> p_214731_0_) {
      int i = p_214731_0_.get("chance").asInt(0);
      return new DungeonRoomConfig(i);
   }
}