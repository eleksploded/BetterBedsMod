package net.minecraft.profiler;

import java.util.function.Supplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EmptyProfiler implements IResultableProfiler {
   public static final EmptyProfiler INSTANCE = new EmptyProfiler();

   public void func_219894_a() {
   }

   public void func_219897_b() {
   }

   /**
    * Start section
    */
   public void startSection(String name) {
   }

   public void startSection(Supplier<String> nameSupplier) {
   }

   /**
    * End section
    */
   public void endSection() {
   }

   public void endStartSection(String p_219895_1_) {
   }

   @OnlyIn(Dist.CLIENT)
   public void endStartSection(Supplier<String> nameSupplier) {
   }

   public IProfileResult func_219905_d() {
      return EmptyProfileResult.field_219926_a;
   }
}