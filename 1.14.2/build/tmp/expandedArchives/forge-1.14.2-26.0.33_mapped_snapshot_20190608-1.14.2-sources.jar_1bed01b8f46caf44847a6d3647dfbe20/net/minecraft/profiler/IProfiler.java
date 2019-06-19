package net.minecraft.profiler;

import java.util.function.Supplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IProfiler {
   void func_219894_a();

   void func_219897_b();

   /**
    * Start section
    */
   void startSection(String name);

   void startSection(Supplier<String> nameSupplier);

   /**
    * End section
    */
   void endSection();

   void endStartSection(String p_219895_1_);

   @OnlyIn(Dist.CLIENT)
   void endStartSection(Supplier<String> nameSupplier);
}