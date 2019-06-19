package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;

public class LiquidsConfig implements IFeatureConfig {
   public final IFluidState field_214678_a;

   public LiquidsConfig(IFluidState p_i51431_1_) {
      this.field_214678_a = p_i51431_1_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("state"), IFluidState.func_215680_a(p_214634_1_, this.field_214678_a).getValue())));
   }

   public static <T> LiquidsConfig func_214677_a(Dynamic<T> p_214677_0_) {
      IFluidState ifluidstate = p_214677_0_.get("state").map(IFluidState::func_215681_a).orElse(Fluids.EMPTY.getDefaultState());
      return new LiquidsConfig(ifluidstate);
   }
}