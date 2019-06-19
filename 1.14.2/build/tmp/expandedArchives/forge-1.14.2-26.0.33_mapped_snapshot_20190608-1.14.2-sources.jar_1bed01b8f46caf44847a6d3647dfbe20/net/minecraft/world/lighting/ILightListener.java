package net.minecraft.world.lighting;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;

public interface ILightListener {
   default void func_215567_a(BlockPos p_215567_1_, boolean p_215567_2_) {
      this.func_215566_a(SectionPos.from(p_215567_1_), p_215567_2_);
   }

   void func_215566_a(SectionPos p_215566_1_, boolean p_215566_2_);
}