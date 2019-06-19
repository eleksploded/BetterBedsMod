package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum NarratorStatus {
   OFF(0, "options.narrator.off"),
   ALL(1, "options.narrator.all"),
   CHAT(2, "options.narrator.chat"),
   SYSTEM(3, "options.narrator.system");

   private static final NarratorStatus[] field_216828_e = Arrays.stream(values()).sorted(Comparator.comparingInt(NarratorStatus::func_216827_a)).toArray((p_216826_0_) -> {
      return new NarratorStatus[p_216826_0_];
   });
   private final int field_216829_f;
   private final String field_216830_g;

   private NarratorStatus(int p_i51160_3_, String p_i51160_4_) {
      this.field_216829_f = p_i51160_3_;
      this.field_216830_g = p_i51160_4_;
   }

   public int func_216827_a() {
      return this.field_216829_f;
   }

   public String func_216824_b() {
      return this.field_216830_g;
   }

   public static NarratorStatus func_216825_a(int p_216825_0_) {
      return field_216828_e[MathHelper.normalizeAngle(p_216825_0_, field_216828_e.length)];
   }
}