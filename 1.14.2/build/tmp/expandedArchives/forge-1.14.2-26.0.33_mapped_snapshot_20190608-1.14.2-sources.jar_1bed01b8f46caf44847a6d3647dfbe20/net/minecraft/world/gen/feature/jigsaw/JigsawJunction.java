package net.minecraft.world.gen.feature.jigsaw;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class JigsawJunction {
   private final int field_214898_a;
   private final int field_214899_b;
   private final int field_214900_c;
   private final int field_214901_d;
   private final JigsawPattern.PlacementBehaviour field_214902_e;

   public JigsawJunction(int p_i51408_1_, int p_i51408_2_, int p_i51408_3_, int p_i51408_4_, JigsawPattern.PlacementBehaviour p_i51408_5_) {
      this.field_214898_a = p_i51408_1_;
      this.field_214899_b = p_i51408_2_;
      this.field_214900_c = p_i51408_3_;
      this.field_214901_d = p_i51408_4_;
      this.field_214902_e = p_i51408_5_;
   }

   public int func_214895_a() {
      return this.field_214898_a;
   }

   public int func_214896_b() {
      return this.field_214899_b;
   }

   public int func_214893_c() {
      return this.field_214900_c;
   }

   public <T> Dynamic<T> func_214897_a(DynamicOps<T> p_214897_1_) {
      Builder<T, T> builder = ImmutableMap.builder();
      builder.put(p_214897_1_.createString("source_x"), p_214897_1_.createInt(this.field_214898_a)).put(p_214897_1_.createString("source_ground_y"), p_214897_1_.createInt(this.field_214899_b)).put(p_214897_1_.createString("source_z"), p_214897_1_.createInt(this.field_214900_c)).put(p_214897_1_.createString("delta_y"), p_214897_1_.createInt(this.field_214901_d)).put(p_214897_1_.createString("dest_proj"), p_214897_1_.createString(this.field_214902_e.func_214936_a()));
      return new Dynamic<>(p_214897_1_, p_214897_1_.createMap(builder.build()));
   }

   public static <T> JigsawJunction func_214894_a(Dynamic<T> p_214894_0_) {
      return new JigsawJunction(p_214894_0_.get("source_x").asInt(0), p_214894_0_.get("source_ground_y").asInt(0), p_214894_0_.get("source_z").asInt(0), p_214894_0_.get("delta_y").asInt(0), JigsawPattern.PlacementBehaviour.func_214938_a(p_214894_0_.get("dest_proj").asString("")));
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         JigsawJunction jigsawjunction = (JigsawJunction)p_equals_1_;
         if (this.field_214898_a != jigsawjunction.field_214898_a) {
            return false;
         } else if (this.field_214900_c != jigsawjunction.field_214900_c) {
            return false;
         } else if (this.field_214901_d != jigsawjunction.field_214901_d) {
            return false;
         } else {
            return this.field_214902_e == jigsawjunction.field_214902_e;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = this.field_214898_a;
      i = 31 * i + this.field_214899_b;
      i = 31 * i + this.field_214900_c;
      i = 31 * i + this.field_214901_d;
      i = 31 * i + this.field_214902_e.hashCode();
      return i;
   }

   public String toString() {
      return "JigsawJunction{sourceX=" + this.field_214898_a + ", sourceGroundY=" + this.field_214899_b + ", sourceZ=" + this.field_214900_c + ", deltaY=" + this.field_214901_d + ", destProjection=" + this.field_214902_e + '}';
   }
}