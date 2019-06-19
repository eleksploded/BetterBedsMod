package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class FillLayerConfig implements IFeatureConfig {
   public final int field_214636_a;
   public final BlockState field_214637_b;

   public FillLayerConfig(int p_i51484_1_, BlockState p_i51484_2_) {
      this.field_214636_a = p_i51484_1_;
      this.field_214637_b = p_i51484_2_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("height"), p_214634_1_.createInt(this.field_214636_a), p_214634_1_.createString("state"), BlockState.func_215689_a(p_214634_1_, this.field_214637_b).getValue())));
   }

   public static <T> FillLayerConfig func_214635_a(Dynamic<T> p_214635_0_) {
      int i = p_214635_0_.get("height").asInt(0);
      BlockState blockstate = p_214635_0_.get("state").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      return new FillLayerConfig(i, blockstate);
   }
}