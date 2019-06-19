package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;

public interface IChunkLightProvider {
   @Nullable
   IBlockReader func_217202_b(int p_217202_1_, int p_217202_2_);

   default void func_217201_a(LightType p_217201_1_, SectionPos p_217201_2_) {
   }

   IBlockReader func_212864_k_();
}