package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class GrassFeatureConfig implements IFeatureConfig {
   public final BlockState field_214708_a;

   public GrassFeatureConfig(BlockState p_i49870_1_) {
      this.field_214708_a = p_i49870_1_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("state"), BlockState.func_215689_a(p_214634_1_, this.field_214708_a).getValue())));
   }

   public static <T> GrassFeatureConfig func_214707_a(Dynamic<T> p_214707_0_) {
      BlockState blockstate = p_214707_0_.get("state").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      return new GrassFeatureConfig(blockstate);
   }
}