package net.minecraft.village;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Objects;
import net.minecraft.util.IDynamicSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class PointOfInterest implements IDynamicSerializable {
   private final BlockPos field_218266_a;
   private final PointOfInterestType field_218267_b;
   private int field_218268_c;
   private final Runnable field_218269_d;

   private PointOfInterest(BlockPos p_i50295_1_, PointOfInterestType p_i50295_2_, int p_i50295_3_, Runnable p_i50295_4_) {
      this.field_218266_a = p_i50295_1_.toImmutable();
      this.field_218267_b = p_i50295_2_;
      this.field_218268_c = p_i50295_3_;
      this.field_218269_d = p_i50295_4_;
   }

   public PointOfInterest(BlockPos p_i50296_1_, PointOfInterestType p_i50296_2_, Runnable p_i50296_3_) {
      this(p_i50296_1_, p_i50296_2_, p_i50296_2_.func_221044_b(), p_i50296_3_);
   }

   public <T> PointOfInterest(Dynamic<T> p_i50297_1_, Runnable p_i50297_2_) {
      this(p_i50297_1_.get("pos").map(BlockPos::func_218286_a).orElse(new BlockPos(0, 0, 0)), Registry.field_218371_M.getOrDefault(new ResourceLocation(p_i50297_1_.get("type").asString(""))), p_i50297_1_.get("free_tickets").asInt(0), p_i50297_2_);
   }

   public <T> T serializeDynamic(DynamicOps<T> p_218175_1_) {
      return p_218175_1_.createMap(ImmutableMap.of(p_218175_1_.createString("pos"), this.field_218266_a.serializeDynamic(p_218175_1_), p_218175_1_.createString("type"), p_218175_1_.createString(Registry.field_218371_M.getKey(this.field_218267_b).toString()), p_218175_1_.createString("free_tickets"), p_218175_1_.createInt(this.field_218268_c)));
   }

   protected boolean func_218262_b() {
      if (this.field_218268_c <= 0) {
         return false;
      } else {
         --this.field_218268_c;
         this.field_218269_d.run();
         return true;
      }
   }

   protected boolean func_218264_c() {
      if (this.field_218268_c >= this.field_218267_b.func_221044_b()) {
         return false;
      } else {
         ++this.field_218268_c;
         this.field_218269_d.run();
         return true;
      }
   }

   public boolean func_218265_d() {
      return this.field_218268_c > 0;
   }

   public boolean func_218263_e() {
      return this.field_218268_c != this.field_218267_b.func_221044_b();
   }

   public BlockPos func_218261_f() {
      return this.field_218266_a;
   }

   public PointOfInterestType func_218260_g() {
      return this.field_218267_b;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return p_equals_1_ != null && this.getClass() == p_equals_1_.getClass() ? Objects.equals(this.field_218266_a, ((PointOfInterest)p_equals_1_).field_218266_a) : false;
      }
   }

   public int hashCode() {
      return this.field_218266_a.hashCode();
   }
}