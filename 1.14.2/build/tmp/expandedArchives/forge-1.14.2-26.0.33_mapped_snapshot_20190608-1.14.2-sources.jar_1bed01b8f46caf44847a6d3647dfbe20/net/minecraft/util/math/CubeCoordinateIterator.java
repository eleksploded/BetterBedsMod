package net.minecraft.util.math;

public class CubeCoordinateIterator {
   private final int field_218305_a;
   private final int field_218306_b;
   private final int field_218307_c;
   private final int field_218308_d;
   private final int field_218309_e;
   private final int field_218310_f;
   private int field_218311_g;
   private int field_218312_h;
   private int field_218313_i;
   private boolean field_218314_j;

   public CubeCoordinateIterator(int p_i50798_1_, int p_i50798_2_, int p_i50798_3_, int p_i50798_4_, int p_i50798_5_, int p_i50798_6_) {
      this.field_218305_a = p_i50798_1_;
      this.field_218306_b = p_i50798_2_;
      this.field_218307_c = p_i50798_3_;
      this.field_218308_d = p_i50798_4_;
      this.field_218309_e = p_i50798_5_;
      this.field_218310_f = p_i50798_6_;
   }

   public boolean func_218301_a() {
      if (!this.field_218314_j) {
         this.field_218311_g = this.field_218305_a;
         this.field_218312_h = this.field_218306_b;
         this.field_218313_i = this.field_218307_c;
         this.field_218314_j = true;
         return true;
      } else if (this.field_218311_g == this.field_218308_d && this.field_218312_h == this.field_218309_e && this.field_218313_i == this.field_218310_f) {
         return false;
      } else {
         if (this.field_218311_g < this.field_218308_d) {
            ++this.field_218311_g;
         } else if (this.field_218312_h < this.field_218309_e) {
            this.field_218311_g = this.field_218305_a;
            ++this.field_218312_h;
         } else if (this.field_218313_i < this.field_218310_f) {
            this.field_218311_g = this.field_218305_a;
            this.field_218312_h = this.field_218306_b;
            ++this.field_218313_i;
         }

         return true;
      }
   }

   public int func_218304_b() {
      return this.field_218311_g;
   }

   public int func_218302_c() {
      return this.field_218312_h;
   }

   public int func_218303_d() {
      return this.field_218313_i;
   }
}