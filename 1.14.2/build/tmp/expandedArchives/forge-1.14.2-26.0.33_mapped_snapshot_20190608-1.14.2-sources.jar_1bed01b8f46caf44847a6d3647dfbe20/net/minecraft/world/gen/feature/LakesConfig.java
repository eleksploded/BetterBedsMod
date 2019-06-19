package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class LakesConfig implements IFeatureConfig {
   public final BlockState field_214713_a;

   public LakesConfig(BlockState p_i51486_1_) {
      this.field_214713_a = p_i51486_1_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("state"), BlockState.func_215689_a(p_214634_1_, this.field_214713_a).getValue())));
   }

   public static <T> LakesConfig func_214712_a(Dynamic<T> p_214712_0_) {
      BlockState blockstate = p_214712_0_.get("state").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      return new LakesConfig(blockstate);
   }
}