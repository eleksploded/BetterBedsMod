package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class ReplaceBlockConfig implements IFeatureConfig {
   public final BlockState field_202457_a;
   public final BlockState field_202458_b;

   public ReplaceBlockConfig(BlockState p_i51445_1_, BlockState p_i51445_2_) {
      this.field_202457_a = p_i51445_1_;
      this.field_202458_b = p_i51445_2_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("target"), BlockState.func_215689_a(p_214634_1_, this.field_202457_a).getValue(), p_214634_1_.createString("state"), BlockState.func_215689_a(p_214634_1_, this.field_202458_b).getValue())));
   }

   public static <T> ReplaceBlockConfig func_214657_a(Dynamic<T> p_214657_0_) {
      BlockState blockstate = p_214657_0_.get("target").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      BlockState blockstate1 = p_214657_0_.get("state").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      return new ReplaceBlockConfig(blockstate, blockstate1);
   }
}