package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.MerchantInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class NPCMerchant implements IMerchant {
   private final MerchantInventory field_70937_a;
   private final PlayerEntity field_70935_b;
   private MerchantOffers field_213709_c = new MerchantOffers();
   private int field_213710_d;

   public NPCMerchant(PlayerEntity p_i50184_1_) {
      this.field_70935_b = p_i50184_1_;
      this.field_70937_a = new MerchantInventory(this);
   }

   @Nullable
   public PlayerEntity getCustomer() {
      return this.field_70935_b;
   }

   public void setCustomer(@Nullable PlayerEntity player) {
   }

   public MerchantOffers func_213706_dY() {
      return this.field_213709_c;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_213703_a(@Nullable MerchantOffers p_213703_1_) {
      this.field_213709_c = p_213703_1_;
   }

   public void func_213704_a(MerchantOffer p_213704_1_) {
      p_213704_1_.func_222219_j();
   }

   /**
    * Notifies the merchant of a possible merchantrecipe being fulfilled or not. Usually, this is just a sound byte
    * being played depending if the suggested itemstack is not null.
    */
   public void verifySellingItem(ItemStack stack) {
   }

   public World getWorld() {
      return this.field_70935_b.world;
   }

   public int func_213708_dV() {
      return this.field_213710_d;
   }

   public void func_213702_q(int p_213702_1_) {
      this.field_213710_d = p_213702_1_;
   }

   public boolean func_213705_dZ() {
      return true;
   }

   public SoundEvent func_213714_ea() {
      return SoundEvents.ENTITY_VILLAGER_YES;
   }
}