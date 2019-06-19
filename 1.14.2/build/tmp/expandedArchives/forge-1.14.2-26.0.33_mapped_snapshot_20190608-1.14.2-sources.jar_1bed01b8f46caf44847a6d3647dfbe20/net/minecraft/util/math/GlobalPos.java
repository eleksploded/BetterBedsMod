package net.minecraft.util.math;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Objects;
import net.minecraft.util.IDynamicSerializable;
import net.minecraft.world.dimension.DimensionType;

public final class GlobalPos implements IDynamicSerializable {
   private final DimensionType field_218183_a;
   private final BlockPos field_218184_b;

   private GlobalPos(DimensionType p_i50796_1_, BlockPos p_i50796_2_) {
      this.field_218183_a = p_i50796_1_;
      this.field_218184_b = p_i50796_2_;
   }

   public static GlobalPos of(DimensionType p_218179_0_, BlockPos p_218179_1_) {
      return new GlobalPos(p_218179_0_, p_218179_1_);
   }

   public static GlobalPos deserialize(Dynamic<?> p_218176_0_) {
      return p_218176_0_.get("dimension").map(DimensionType::func_218271_a).flatMap((p_218181_1_) -> {
         return p_218176_0_.get("pos").map(BlockPos::func_218286_a).map((p_218182_1_) -> {
            return new GlobalPos(p_218181_1_, p_218182_1_);
         });
      }).orElseThrow(() -> {
         return new IllegalArgumentException("Could not parse GlobalPos");
      });
   }

   public DimensionType func_218177_a() {
      return this.field_218183_a;
   }

   public BlockPos func_218180_b() {
      return this.field_218184_b;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         GlobalPos globalpos = (GlobalPos)p_equals_1_;
         return Objects.equals(this.field_218183_a, globalpos.field_218183_a) && Objects.equals(this.field_218184_b, globalpos.field_218184_b);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.field_218183_a, this.field_218184_b);
   }

   public <T> T serializeDynamic(DynamicOps<T> p_218175_1_) {
      return p_218175_1_.createMap(ImmutableMap.of(p_218175_1_.createString("dimension"), this.field_218183_a.serializeDynamic(p_218175_1_), p_218175_1_.createString("pos"), this.field_218184_b.serializeDynamic(p_218175_1_)));
   }

   public String toString() {
      return this.field_218183_a.toString() + " " + this.field_218184_b;
   }
}