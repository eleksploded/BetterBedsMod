package net.minecraft.profiler;

import java.io.File;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IProfileResult {
   @OnlyIn(Dist.CLIENT)
   List<DataPoint> func_219917_a(String p_219917_1_);

   boolean func_219919_a(File p_219919_1_);

   long func_219918_a();

   int func_219922_b();

   long func_219923_c();

   int func_219921_d();

   default long func_219924_f() {
      return this.func_219923_c() - this.func_219918_a();
   }

   default int func_219925_g() {
      return this.func_219921_d() - this.func_219922_b();
   }

   String func_219920_e();
}