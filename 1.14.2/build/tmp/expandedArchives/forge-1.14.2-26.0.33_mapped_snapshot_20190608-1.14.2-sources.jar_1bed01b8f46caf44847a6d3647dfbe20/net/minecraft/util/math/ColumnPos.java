package net.minecraft.util.math;

public class ColumnPos {
   public final int field_219439_a;
   public final int field_219440_b;

   public ColumnPos(int p_i50710_1_, int p_i50710_2_) {
      this.field_219439_a = p_i50710_1_;
      this.field_219440_b = p_i50710_2_;
   }

   public ColumnPos(BlockPos p_i50711_1_) {
      this.field_219439_a = p_i50711_1_.getX();
      this.field_219440_b = p_i50711_1_.getZ();
   }

   public long func_219438_b() {
      return func_219437_a(this.field_219439_a, this.field_219440_b);
   }

   public static long func_219437_a(int p_219437_0_, int p_219437_1_) {
      return (long)p_219437_0_ & 4294967295L | ((long)p_219437_1_ & 4294967295L) << 32;
   }

   public String toString() {
      return "[" + this.field_219439_a + ", " + this.field_219440_b + "]";
   }

   public int hashCode() {
      int i = 1664525 * this.field_219439_a + 1013904223;
      int j = 1664525 * (this.field_219440_b ^ -559038737) + 1013904223;
      return i ^ j;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof ColumnPos)) {
         return false;
      } else {
         ColumnPos columnpos = (ColumnPos)p_equals_1_;
         return this.field_219439_a == columnpos.field_219439_a && this.field_219440_b == columnpos.field_219440_b;
      }
   }
}