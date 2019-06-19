package net.minecraft.item;

import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;

public abstract class ShootableItem extends Item {
   public static final Predicate<ItemStack> field_220007_a = (p_220002_0_) -> {
      return p_220002_0_.getItem().isIn(ItemTags.field_219776_M);
   };
   public static final Predicate<ItemStack> field_220008_b = field_220007_a.or((p_220003_0_) -> {
      return p_220003_0_.getItem() == Items.FIREWORK_ROCKET;
   });

   public ShootableItem(Item.Properties p_i50040_1_) {
      super(p_i50040_1_);
   }

   public Predicate<ItemStack> func_220006_d() {
      return this.func_220004_b();
   }

   public abstract Predicate<ItemStack> func_220004_b();

   public static ItemStack func_220005_a(LivingEntity p_220005_0_, Predicate<ItemStack> p_220005_1_) {
      if (p_220005_1_.test(p_220005_0_.getHeldItem(Hand.OFF_HAND))) {
         return p_220005_0_.getHeldItem(Hand.OFF_HAND);
      } else {
         return p_220005_1_.test(p_220005_0_.getHeldItem(Hand.MAIN_HAND)) ? p_220005_0_.getHeldItem(Hand.MAIN_HAND) : ItemStack.EMPTY;
      }
   }

   /**
    * Return the enchantability factor of the item, most of the time is based on material.
    */
   public int getItemEnchantability() {
      return 1;
   }
}