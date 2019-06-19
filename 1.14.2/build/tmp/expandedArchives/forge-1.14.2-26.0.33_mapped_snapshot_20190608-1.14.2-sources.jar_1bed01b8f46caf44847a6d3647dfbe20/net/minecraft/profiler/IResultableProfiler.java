package net.minecraft.profiler;

import java.util.function.Supplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IResultableProfiler extends IProfiler {
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

   IProfileResult func_219905_d();
}