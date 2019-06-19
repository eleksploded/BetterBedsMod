package net.minecraft.item;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.MathHelper;

public class MerchantOffer {
   private final ItemStack field_222223_a;
   private final ItemStack field_222224_b;
   private final ItemStack field_222225_c;
   private int field_222226_d;
   private final int field_222227_e;
   private boolean field_222228_f = true;
   private int field_222229_g;
   private int field_222230_h;
   private float field_222231_i;
   private int field_222232_j = 1;

   public MerchantOffer(CompoundNBT p_i50012_1_) {
      this.field_222223_a = ItemStack.read(p_i50012_1_.getCompound("buy"));
      this.field_222224_b = ItemStack.read(p_i50012_1_.getCompound("buyB"));
      this.field_222225_c = ItemStack.read(p_i50012_1_.getCompound("sell"));
      this.field_222226_d = p_i50012_1_.getInt("uses");
      if (p_i50012_1_.contains("maxUses", 99)) {
         this.field_222227_e = p_i50012_1_.getInt("maxUses");
      } else {
         this.field_222227_e = 4;
      }

      if (p_i50012_1_.contains("rewardExp", 1)) {
         this.field_222228_f = p_i50012_1_.getBoolean("rewardExp");
      }

      if (p_i50012_1_.contains("xp", 3)) {
         this.field_222232_j = p_i50012_1_.getInt("xp");
      }

      if (p_i50012_1_.contains("priceMultiplier", 5)) {
         this.field_222231_i = p_i50012_1_.getFloat("priceMultiplier");
      }

      this.field_222229_g = p_i50012_1_.getInt("specialPrice");
      this.field_222230_h = p_i50012_1_.getInt("demand");
   }

   public MerchantOffer(ItemStack p_i50013_1_, ItemStack p_i50013_2_, int p_i50013_3_, int p_i50013_4_, float p_i50013_5_) {
      this(p_i50013_1_, ItemStack.EMPTY, p_i50013_2_, p_i50013_3_, p_i50013_4_, p_i50013_5_);
   }

   public MerchantOffer(ItemStack p_i50014_1_, ItemStack p_i50014_2_, ItemStack p_i50014_3_, int p_i50014_4_, int p_i50014_5_, float p_i50014_6_) {
      this(p_i50014_1_, p_i50014_2_, p_i50014_3_, 0, p_i50014_4_, p_i50014_5_, p_i50014_6_);
   }

   public MerchantOffer(ItemStack p_i50015_1_, ItemStack p_i50015_2_, ItemStack p_i50015_3_, int p_i50015_4_, int p_i50015_5_, int p_i50015_6_, float p_i50015_7_) {
      this.field_222223_a = p_i50015_1_;
      this.field_222224_b = p_i50015_2_;
      this.field_222225_c = p_i50015_3_;
      this.field_222226_d = p_i50015_4_;
      this.field_222227_e = p_i50015_5_;
      this.field_222232_j = p_i50015_6_;
      this.field_222231_i = p_i50015_7_;
   }

   public ItemStack func_222218_a() {
      return this.field_222223_a;
   }

   public ItemStack func_222205_b() {
      int i = this.field_222223_a.getCount();
      ItemStack itemstack = this.field_222223_a.copy();
      int j = Math.max(0, MathHelper.floor((float)(i * this.field_222230_h) * this.field_222231_i));
      itemstack.setCount(MathHelper.clamp(i + j + this.field_222229_g, 1, this.field_222223_a.getItem().getMaxStackSize()));
      return itemstack;
   }

   public ItemStack func_222202_c() {
      return this.field_222224_b;
   }

   public ItemStack func_222200_d() {
      return this.field_222225_c;
   }

   public void func_222222_e() {
      this.field_222230_h = this.field_222230_h + this.field_222226_d - (this.field_222227_e - this.field_222226_d);
   }

   public ItemStack func_222206_f() {
      return this.field_222225_c.copy();
   }

   public int func_222213_g() {
      return this.field_222226_d;
   }

   public void func_222203_h() {
      this.field_222226_d = 0;
   }

   public int func_222214_i() {
      return this.field_222227_e;
   }

   public void func_222219_j() {
      ++this.field_222226_d;
   }

   public void func_222207_a(int p_222207_1_) {
      this.field_222229_g += p_222207_1_;
   }

   public void func_222220_k() {
      this.field_222229_g = 0;
   }

   public int func_222212_l() {
      return this.field_222229_g;
   }

   public void func_222209_b(int p_222209_1_) {
      this.field_222229_g = p_222209_1_;
   }

   public float func_222211_m() {
      return this.field_222231_i;
   }

   public int func_222210_n() {
      return this.field_222232_j;
   }

   public boolean func_222217_o() {
      return this.field_222226_d >= this.field_222227_e;
   }

   public void func_222216_p() {
      this.field_222226_d = this.field_222227_e;
   }

   public boolean func_222221_q() {
      return this.field_222228_f;
   }

   public CompoundNBT func_222208_r() {
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.put("buy", this.field_222223_a.write(new CompoundNBT()));
      compoundnbt.put("sell", this.field_222225_c.write(new CompoundNBT()));
      compoundnbt.put("buyB", this.field_222224_b.write(new CompoundNBT()));
      compoundnbt.putInt("uses", this.field_222226_d);
      compoundnbt.putInt("maxUses", this.field_222227_e);
      compoundnbt.putBoolean("rewardExp", this.field_222228_f);
      compoundnbt.putInt("xp", this.field_222232_j);
      compoundnbt.putFloat("priceMultiplier", this.field_222231_i);
      compoundnbt.putInt("specialPrice", this.field_222229_g);
      compoundnbt.putInt("demand", this.field_222230_h);
      return compoundnbt;
   }

   public boolean func_222204_a(ItemStack p_222204_1_, ItemStack p_222204_2_) {
      return this.func_222201_c(p_222204_1_, this.func_222205_b()) && p_222204_1_.getCount() >= this.func_222205_b().getCount() && this.func_222201_c(p_222204_2_, this.field_222224_b) && p_222204_2_.getCount() >= this.field_222224_b.getCount();
   }

   private boolean func_222201_c(ItemStack p_222201_1_, ItemStack p_222201_2_) {
      if (p_222201_2_.isEmpty() && p_222201_1_.isEmpty()) {
         return true;
      } else {
         ItemStack itemstack = p_222201_1_.copy();
         if (itemstack.getItem().isDamageable()) {
            itemstack.setDamage(itemstack.getDamage());
         }

         return ItemStack.areItemsEqual(itemstack, p_222201_2_) && (!p_222201_2_.hasTag() || itemstack.hasTag() && NBTUtil.areNBTEquals(p_222201_2_.getTag(), itemstack.getTag(), false));
      }
   }

   public boolean func_222215_b(ItemStack p_222215_1_, ItemStack p_222215_2_) {
      if (!this.func_222204_a(p_222215_1_, p_222215_2_)) {
         return false;
      } else {
         p_222215_1_.shrink(this.func_222205_b().getCount());
         if (!this.func_222202_c().isEmpty()) {
            p_222215_2_.shrink(this.func_222202_c().getCount());
         }

         return true;
      }
   }
}