package net.minecraft.fluid;

import net.minecraft.util.registry.Registry;

public class Fluids {
   public static final Fluid EMPTY = func_215710_a("empty", new EmptyFluid());
   public static final FlowingFluid FLOWING_WATER = func_215710_a("flowing_water", new WaterFluid.Flowing());
   public static final FlowingFluid WATER = func_215710_a("water", new WaterFluid.Source());
   public static final FlowingFluid FLOWING_LAVA = func_215710_a("flowing_lava", new LavaFluid.Flowing());
   public static final FlowingFluid LAVA = func_215710_a("lava", new LavaFluid.Source());

   private static <T extends Fluid> T func_215710_a(String p_215710_0_, T p_215710_1_) {
      return (T)(Registry.register(Registry.field_212619_h, p_215710_0_, p_215710_1_));
   }

   static {
      for(Fluid fluid : Registry.field_212619_h) {
         for(IFluidState ifluidstate : fluid.getStateContainer().getValidStates()) {
            Fluid.STATE_REGISTRY.add(ifluidstate);
         }
      }

   }
}