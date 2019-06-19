package net.minecraft.client.settings;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum ParticleStatus {
   ALL(0, "options.particles.all"),
   DECREASED(1, "options.particles.decreased"),
   MINIMAL(2, "options.particles.minimal");

   private static final ParticleStatus[] field_216835_d = Arrays.stream(values()).sorted(Comparator.comparingInt(ParticleStatus::func_216832_b)).toArray((p_216834_0_) -> {
      return new ParticleStatus[p_216834_0_];
   });
   private final int field_216836_e;
   private final String field_216837_f;

   private ParticleStatus(int p_i51156_3_, String p_i51156_4_) {
      this.field_216836_e = p_i51156_3_;
      this.field_216837_f = p_i51156_4_;
   }

   public String func_216831_a() {
      return this.field_216837_f;
   }

   public int func_216832_b() {
      return this.field_216836_e;
   }

   public static ParticleStatus func_216833_a(int p_216833_0_) {
      return field_216835_d[MathHelper.normalizeAngle(p_216833_0_, field_216835_d.length)];
   }
}