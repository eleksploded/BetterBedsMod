package net.minecraft.profiler;

import java.io.File;
import java.util.Collections;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EmptyProfileResult implements IProfileResult {
   public static final EmptyProfileResult field_219926_a = new EmptyProfileResult();

   @OnlyIn(Dist.CLIENT)
   public List<DataPoint> func_219917_a(String p_219917_1_) {
      return Collections.emptyList();
   }

   public boolean func_219919_a(File p_219919_1_) {
      return false;
   }

   public long func_219918_a() {
      return 0L;
   }

   public int func_219922_b() {
      return 0;
   }

   public long func_219923_c() {
      return 0L;
   }

   public int func_219921_d() {
      return 0;
   }

   public String func_219920_e() {
      return "";
   }
}