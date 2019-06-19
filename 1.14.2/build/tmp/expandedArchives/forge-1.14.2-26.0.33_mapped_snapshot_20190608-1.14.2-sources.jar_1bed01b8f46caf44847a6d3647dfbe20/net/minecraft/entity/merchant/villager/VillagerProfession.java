package net.minecraft.entity.merchant.villager;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestType;

public class VillagerProfession extends net.minecraftforge.registries.ForgeRegistryEntry<VillagerProfession> {
   public static final VillagerProfession field_221151_a = func_221147_a("none", PointOfInterestType.field_221054_b);
   public static final VillagerProfession field_221152_b = func_221147_a("armorer", PointOfInterestType.field_221055_c);
   public static final VillagerProfession field_221153_c = func_221147_a("butcher", PointOfInterestType.field_221056_d);
   public static final VillagerProfession field_221154_d = func_221147_a("cartographer", PointOfInterestType.field_221057_e);
   public static final VillagerProfession field_221155_e = func_221147_a("cleric", PointOfInterestType.field_221058_f);
   public static final VillagerProfession field_221156_f = func_221148_a("farmer", PointOfInterestType.field_221059_g, ImmutableSet.of(Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS), ImmutableSet.of(Blocks.FARMLAND));
   public static final VillagerProfession field_221157_g = func_221147_a("fisherman", PointOfInterestType.field_221060_h);
   public static final VillagerProfession field_221158_h = func_221147_a("fletcher", PointOfInterestType.field_221061_i);
   public static final VillagerProfession field_221159_i = func_221147_a("leatherworker", PointOfInterestType.field_221062_j);
   public static final VillagerProfession field_221160_j = func_221147_a("librarian", PointOfInterestType.field_221063_k);
   public static final VillagerProfession field_221161_k = func_221147_a("mason", PointOfInterestType.field_221064_l);
   public static final VillagerProfession field_221162_l = func_221147_a("nitwit", PointOfInterestType.field_221065_m);
   public static final VillagerProfession field_221163_m = func_221147_a("shepherd", PointOfInterestType.field_221066_n);
   public static final VillagerProfession field_221164_n = func_221147_a("toolsmith", PointOfInterestType.field_221067_o);
   public static final VillagerProfession field_221165_o = func_221147_a("weaponsmith", PointOfInterestType.field_221068_p);
   private final String field_221166_p;
   private final PointOfInterestType field_221167_q;
   private final ImmutableSet<Item> field_221168_r;
   private final ImmutableSet<Block> field_221169_s;

   public VillagerProfession(String p_i50179_1_, PointOfInterestType p_i50179_2_, ImmutableSet<Item> p_i50179_3_, ImmutableSet<Block> p_i50179_4_) {
      this.field_221166_p = p_i50179_1_;
      this.field_221167_q = p_i50179_2_;
      this.field_221168_r = p_i50179_3_;
      this.field_221169_s = p_i50179_4_;
   }

   public PointOfInterestType func_221149_b() {
      return this.field_221167_q;
   }

   public ImmutableSet<Item> func_221146_c() {
      return this.field_221168_r;
   }

   public ImmutableSet<Block> func_221150_d() {
      return this.field_221169_s;
   }

   public String toString() {
      return this.field_221166_p;
   }

   static VillagerProfession func_221147_a(String p_221147_0_, PointOfInterestType p_221147_1_) {
      return func_221148_a(p_221147_0_, p_221147_1_, ImmutableSet.of(), ImmutableSet.of());
   }

   static VillagerProfession func_221148_a(String p_221148_0_, PointOfInterestType p_221148_1_, ImmutableSet<Item> p_221148_2_, ImmutableSet<Block> p_221148_3_) {
      return Registry.register(Registry.field_218370_L, new ResourceLocation(p_221148_0_), new VillagerProfession(p_221148_0_, p_221148_1_, p_221148_2_, p_221148_3_));
   }
}