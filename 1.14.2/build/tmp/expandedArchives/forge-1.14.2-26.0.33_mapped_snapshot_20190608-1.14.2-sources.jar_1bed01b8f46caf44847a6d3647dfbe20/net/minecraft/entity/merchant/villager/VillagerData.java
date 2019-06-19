package net.minecraft.entity.merchant.villager;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.entity.villager.IVillagerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VillagerData {
   private static final int[] field_221136_a = new int[]{0, 10, 50, 100, 150};
   private final IVillagerType field_221137_b;
   private final VillagerProfession field_221138_c;
   private final int field_221139_d;

   public VillagerData(IVillagerType p_i50180_1_, VillagerProfession p_i50180_2_, int p_i50180_3_) {
      this.field_221137_b = p_i50180_1_;
      this.field_221138_c = p_i50180_2_;
      this.field_221139_d = Math.max(1, p_i50180_3_);
   }

   public VillagerData(Dynamic<?> p_i50181_1_) {
      this(Registry.field_218369_K.getOrDefault(ResourceLocation.tryCreate(p_i50181_1_.get("type").asString(""))), Registry.field_218370_L.getOrDefault(ResourceLocation.tryCreate(p_i50181_1_.get("profession").asString(""))), p_i50181_1_.get("level").asInt(1));
   }

   public IVillagerType getType() {
      return this.field_221137_b;
   }

   public VillagerProfession getProfession() {
      return this.field_221138_c;
   }

   public int getLevel() {
      return this.field_221139_d;
   }

   public VillagerData func_221134_a(IVillagerType p_221134_1_) {
      return new VillagerData(p_221134_1_, this.field_221138_c, this.field_221139_d);
   }

   public VillagerData func_221126_a(VillagerProfession p_221126_1_) {
      return new VillagerData(this.field_221137_b, p_221126_1_, this.field_221139_d);
   }

   public VillagerData func_221135_a(int p_221135_1_) {
      return new VillagerData(this.field_221137_b, this.field_221138_c, p_221135_1_);
   }

   public <T> T func_221131_a(DynamicOps<T> p_221131_1_) {
      return p_221131_1_.createMap(ImmutableMap.of(p_221131_1_.createString("type"), p_221131_1_.createString(Registry.field_218369_K.getKey(this.field_221137_b).toString()), p_221131_1_.createString("profession"), p_221131_1_.createString(Registry.field_218370_L.getKey(this.field_221138_c).toString()), p_221131_1_.createString("level"), p_221131_1_.createInt(this.field_221139_d)));
   }

   @OnlyIn(Dist.CLIENT)
   public static int func_221133_b(int p_221133_0_) {
      return func_221128_d(p_221133_0_) ? field_221136_a[p_221133_0_ - 1] : 0;
   }

   public static int func_221127_c(int p_221127_0_) {
      return func_221128_d(p_221127_0_) ? field_221136_a[p_221127_0_] : 0;
   }

   public static boolean func_221128_d(int p_221128_0_) {
      return p_221128_0_ >= 1 && p_221128_0_ < 5;
   }
}