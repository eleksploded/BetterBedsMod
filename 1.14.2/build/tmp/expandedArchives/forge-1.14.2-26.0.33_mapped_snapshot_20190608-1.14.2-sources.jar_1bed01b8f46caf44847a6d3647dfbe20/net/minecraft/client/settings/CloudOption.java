package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum CloudOption {
   OFF(0, "options.off"),
   FAST(1, "options.clouds.fast"),
   FANCY(2, "options.clouds.fancy");

   private static final CloudOption[] field_216807_d = Arrays.stream(values()).sorted(Comparator.comparingInt(CloudOption::func_216806_a)).toArray((p_216805_0_) -> {
      return new CloudOption[p_216805_0_];
   });
   private final int field_216808_e;
   private final String field_216809_f;

   private CloudOption(int p_i51166_3_, String p_i51166_4_) {
      this.field_216808_e = p_i51166_3_;
      this.field_216809_f = p_i51166_4_;
   }

   public int func_216806_a() {
      return this.field_216808_e;
   }

   public String func_216803_b() {
      return this.field_216809_f;
   }

   public static CloudOption func_216804_a(int p_216804_0_) {
      return field_216807_d[MathHelper.normalizeAngle(p_216804_0_, field_216807_d.length)];
   }
}