package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.potion.EffectInstance;
import org.apache.commons.lang3.tuple.Pair;

public class Food {
   private final int field_221470_a;
   private final float field_221471_b;
   private final boolean field_221472_c;
   private final boolean field_221473_d;
   private final boolean field_221474_e;
   private final List<Pair<EffectInstance, Float>> field_221475_f;

   private Food(int p_i50106_1_, float p_i50106_2_, boolean p_i50106_3_, boolean p_i50106_4_, boolean p_i50106_5_, List<Pair<EffectInstance, Float>> p_i50106_6_) {
      this.field_221470_a = p_i50106_1_;
      this.field_221471_b = p_i50106_2_;
      this.field_221472_c = p_i50106_3_;
      this.field_221473_d = p_i50106_4_;
      this.field_221474_e = p_i50106_5_;
      this.field_221475_f = p_i50106_6_;
   }

   public int func_221466_a() {
      return this.field_221470_a;
   }

   public float func_221469_b() {
      return this.field_221471_b;
   }

   public boolean func_221467_c() {
      return this.field_221472_c;
   }

   public boolean func_221468_d() {
      return this.field_221473_d;
   }

   public boolean func_221465_e() {
      return this.field_221474_e;
   }

   public List<Pair<EffectInstance, Float>> func_221464_f() {
      return this.field_221475_f;
   }

   public static class Builder {
      private int field_221458_a;
      private float field_221459_b;
      private boolean field_221460_c;
      private boolean field_221461_d;
      private boolean field_221462_e;
      private final List<Pair<EffectInstance, Float>> field_221463_f = Lists.newArrayList();

      public Food.Builder func_221456_a(int p_221456_1_) {
         this.field_221458_a = p_221456_1_;
         return this;
      }

      public Food.Builder func_221454_a(float p_221454_1_) {
         this.field_221459_b = p_221454_1_;
         return this;
      }

      public Food.Builder func_221451_a() {
         this.field_221460_c = true;
         return this;
      }

      public Food.Builder func_221455_b() {
         this.field_221461_d = true;
         return this;
      }

      public Food.Builder func_221457_c() {
         this.field_221462_e = true;
         return this;
      }

      public Food.Builder func_221452_a(EffectInstance p_221452_1_, float p_221452_2_) {
         this.field_221463_f.add(Pair.of(p_221452_1_, p_221452_2_));
         return this;
      }

      public Food func_221453_d() {
         return new Food(this.field_221458_a, this.field_221459_b, this.field_221460_c, this.field_221461_d, this.field_221462_e, this.field_221463_f);
      }
   }
}