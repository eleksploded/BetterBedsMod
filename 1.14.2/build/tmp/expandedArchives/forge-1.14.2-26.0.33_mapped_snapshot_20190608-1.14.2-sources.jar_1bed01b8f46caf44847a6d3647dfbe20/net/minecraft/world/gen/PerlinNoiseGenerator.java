package net.minecraft.world.gen;

import java.util.Random;

public class PerlinNoiseGenerator implements INoiseGenerator {
   private final SimplexNoiseGenerator[] field_151603_a;
   private final int levels;

   public PerlinNoiseGenerator(Random seed, int levelsIn) {
      this.levels = levelsIn;
      this.field_151603_a = new SimplexNoiseGenerator[levelsIn];

      for(int i = 0; i < levelsIn; ++i) {
         this.field_151603_a[i] = new SimplexNoiseGenerator(seed);
      }

   }

   public double getValue(double x, double z) {
      return this.func_215464_a(x, z, false);
   }

   public double func_215464_a(double p_215464_1_, double p_215464_3_, boolean p_215464_5_) {
      double d0 = 0.0D;
      double d1 = 1.0D;

      for(int i = 0; i < this.levels; ++i) {
         d0 += this.field_151603_a[i].getValue(p_215464_1_ * d1 + (p_215464_5_ ? this.field_151603_a[i].xo : 0.0D), p_215464_3_ * d1 + (p_215464_5_ ? this.field_151603_a[i].yo : 0.0D)) / d1;
         d1 /= 2.0D;
      }

      return d0;
   }

   public double func_215460_a(double p_215460_1_, double p_215460_3_, double p_215460_5_, double p_215460_7_) {
      return this.func_215464_a(p_215460_1_, p_215460_3_, true) * 0.55D;
   }
}