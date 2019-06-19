package net.minecraft.world.gen.layer.traits;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;

public interface IAreaTransformer0 {
   default <R extends IArea> IAreaFactory<R> apply(IExtendedNoiseRandom<R> context) {
      return () -> {
         return context.func_212861_a_((p_202820_2_, p_202820_3_) -> {
            context.setPosition((long)p_202820_2_, (long)p_202820_3_);
            return this.func_215735_a(context, p_202820_2_, p_202820_3_);
         });
      };
   }

   int func_215735_a(INoiseRandom p_215735_1_, int p_215735_2_, int p_215735_3_);
}