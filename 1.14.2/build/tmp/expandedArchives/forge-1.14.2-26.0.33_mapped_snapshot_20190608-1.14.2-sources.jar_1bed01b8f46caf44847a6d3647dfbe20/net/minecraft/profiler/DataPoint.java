package net.minecraft.profiler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class DataPoint implements Comparable<DataPoint> {
   public final double field_219943_a;
   public final double field_219944_b;
   public final String field_219945_c;

   public DataPoint(String p_i50404_1_, double p_i50404_2_, double p_i50404_4_) {
      this.field_219945_c = p_i50404_1_;
      this.field_219943_a = p_i50404_2_;
      this.field_219944_b = p_i50404_4_;
   }

   public int compareTo(DataPoint p_compareTo_1_) {
      if (p_compareTo_1_.field_219943_a < this.field_219943_a) {
         return -1;
      } else {
         return p_compareTo_1_.field_219943_a > this.field_219943_a ? 1 : p_compareTo_1_.field_219945_c.compareTo(this.field_219945_c);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public int func_219942_a() {
      return (this.field_219945_c.hashCode() & 11184810) + 4473924;
   }
}