package net.minecraft.potion;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;

public class PotionBrewing {
   private static final List<PotionBrewing.MixPredicate<Potion>> POTION_TYPE_CONVERSIONS = Lists.newArrayList();
   private static final List<PotionBrewing.MixPredicate<Item>> POTION_ITEM_CONVERSIONS = Lists.newArrayList();
   private static final List<Ingredient> POTION_ITEMS = Lists.newArrayList();
   private static final Predicate<ItemStack> IS_POTION_ITEM = (p_210319_0_) -> {
      for(Ingredient ingredient : POTION_ITEMS) {
         if (ingredient.test(p_210319_0_)) {
            return true;
         }
      }

      return false;
   };

   public static boolean isReagent(ItemStack stack) {
      return isItemConversionReagent(stack) || isTypeConversionReagent(stack);
   }

   protected static boolean isItemConversionReagent(ItemStack stack) {
      int i = 0;

      for(int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i) {
         if ((POTION_ITEM_CONVERSIONS.get(i)).reagent.test(stack)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean isTypeConversionReagent(ItemStack stack) {
      int i = 0;

      for(int j = POTION_TYPE_CONVERSIONS.size(); i < j; ++i) {
         if ((POTION_TYPE_CONVERSIONS.get(i)).reagent.test(stack)) {
            return true;
         }
      }

      return false;
   }

   public static boolean func_222124_a(Potion p_222124_0_) {
      int i = 0;

      for(int j = POTION_TYPE_CONVERSIONS.size(); i < j; ++i) {
         if ((POTION_TYPE_CONVERSIONS.get(i)).output.get() == p_222124_0_) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasConversions(ItemStack input, ItemStack reagent) {
      if (!IS_POTION_ITEM.test(input)) {
         return false;
      } else {
         return hasItemConversions(input, reagent) || hasTypeConversions(input, reagent);
      }
   }

   protected static boolean hasItemConversions(ItemStack input, ItemStack reagent) {
      Item item = input.getItem();
      int i = 0;

      for(int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i) {
         PotionBrewing.MixPredicate<Item> mixpredicate = POTION_ITEM_CONVERSIONS.get(i);
         if (mixpredicate.input.get() == item && mixpredicate.reagent.test(reagent)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean hasTypeConversions(ItemStack input, ItemStack reagent) {
      Potion potion = PotionUtils.getPotionFromItem(input);
      int i = 0;

      for(int j = POTION_TYPE_CONVERSIONS.size(); i < j; ++i) {
         PotionBrewing.MixPredicate<Potion> mixpredicate = POTION_TYPE_CONVERSIONS.get(i);
         if (mixpredicate.input.get() == potion && mixpredicate.reagent.test(reagent)) {
            return true;
         }
      }

      return false;
   }

   public static ItemStack doReaction(ItemStack reagent, ItemStack potionIn) {
      if (!potionIn.isEmpty()) {
         Potion potion = PotionUtils.getPotionFromItem(potionIn);
         Item item = potionIn.getItem();
         int i = 0;

         for(int j = POTION_ITEM_CONVERSIONS.size(); i < j; ++i) {
            PotionBrewing.MixPredicate<Item> mixpredicate = POTION_ITEM_CONVERSIONS.get(i);
            if (mixpredicate.input.get() == item && mixpredicate.reagent.test(reagent)) {
               return PotionUtils.addPotionToItemStack(new ItemStack((IItemProvider)mixpredicate.output.get()), potion);
            }
         }

         i = 0;

         for(int k = POTION_TYPE_CONVERSIONS.size(); i < k; ++i) {
            PotionBrewing.MixPredicate<Potion> mixpredicate1 = POTION_TYPE_CONVERSIONS.get(i);
            if (mixpredicate1.input.get() == potion && mixpredicate1.reagent.test(reagent)) {
               return PotionUtils.addPotionToItemStack(new ItemStack(item), (Potion)mixpredicate1.output.get());
            }
         }
      }

      return potionIn;
   }

   public static void init() {
      addContainer(Items.POTION);
      addContainer(Items.SPLASH_POTION);
      addContainer(Items.LINGERING_POTION);
      addContainerRecipe(Items.POTION, Items.GUNPOWDER, Items.SPLASH_POTION);
      addContainerRecipe(Items.SPLASH_POTION, Items.DRAGON_BREATH, Items.LINGERING_POTION);
      addMix(Potions.field_185230_b, Items.GLISTERING_MELON_SLICE, Potions.field_185231_c);
      addMix(Potions.field_185230_b, Items.GHAST_TEAR, Potions.field_185231_c);
      addMix(Potions.field_185230_b, Items.RABBIT_FOOT, Potions.field_185231_c);
      addMix(Potions.field_185230_b, Items.BLAZE_POWDER, Potions.field_185231_c);
      addMix(Potions.field_185230_b, Items.SPIDER_EYE, Potions.field_185231_c);
      addMix(Potions.field_185230_b, Items.SUGAR, Potions.field_185231_c);
      addMix(Potions.field_185230_b, Items.MAGMA_CREAM, Potions.field_185231_c);
      addMix(Potions.field_185230_b, Items.GLOWSTONE_DUST, Potions.field_185232_d);
      addMix(Potions.field_185230_b, Items.REDSTONE, Potions.field_185231_c);
      addMix(Potions.field_185230_b, Items.NETHER_WART, Potions.field_185233_e);
      addMix(Potions.field_185233_e, Items.GOLDEN_CARROT, Potions.field_185234_f);
      addMix(Potions.field_185234_f, Items.REDSTONE, Potions.field_185235_g);
      addMix(Potions.field_185234_f, Items.FERMENTED_SPIDER_EYE, Potions.field_185236_h);
      addMix(Potions.field_185235_g, Items.FERMENTED_SPIDER_EYE, Potions.field_185237_i);
      addMix(Potions.field_185236_h, Items.REDSTONE, Potions.field_185237_i);
      addMix(Potions.field_185233_e, Items.MAGMA_CREAM, Potions.field_185241_m);
      addMix(Potions.field_185241_m, Items.REDSTONE, Potions.field_185242_n);
      addMix(Potions.field_185233_e, Items.RABBIT_FOOT, Potions.field_185238_j);
      addMix(Potions.field_185238_j, Items.REDSTONE, Potions.field_185239_k);
      addMix(Potions.field_185238_j, Items.GLOWSTONE_DUST, Potions.field_185240_l);
      addMix(Potions.field_185238_j, Items.FERMENTED_SPIDER_EYE, Potions.field_185246_r);
      addMix(Potions.field_185239_k, Items.FERMENTED_SPIDER_EYE, Potions.field_185247_s);
      addMix(Potions.field_185246_r, Items.REDSTONE, Potions.field_185247_s);
      addMix(Potions.field_185246_r, Items.GLOWSTONE_DUST, Potions.field_203185_t);
      addMix(Potions.field_185233_e, Items.TURTLE_HELMET, Potions.field_203186_u);
      addMix(Potions.field_203186_u, Items.REDSTONE, Potions.field_203187_v);
      addMix(Potions.field_203186_u, Items.GLOWSTONE_DUST, Potions.field_203188_w);
      addMix(Potions.field_185243_o, Items.FERMENTED_SPIDER_EYE, Potions.field_185246_r);
      addMix(Potions.field_185244_p, Items.FERMENTED_SPIDER_EYE, Potions.field_185247_s);
      addMix(Potions.field_185233_e, Items.SUGAR, Potions.field_185243_o);
      addMix(Potions.field_185243_o, Items.REDSTONE, Potions.field_185244_p);
      addMix(Potions.field_185243_o, Items.GLOWSTONE_DUST, Potions.field_185245_q);
      addMix(Potions.field_185233_e, Items.PUFFERFISH, Potions.field_185248_t);
      addMix(Potions.field_185248_t, Items.REDSTONE, Potions.field_185249_u);
      addMix(Potions.field_185233_e, Items.GLISTERING_MELON_SLICE, Potions.field_185250_v);
      addMix(Potions.field_185250_v, Items.GLOWSTONE_DUST, Potions.field_185251_w);
      addMix(Potions.field_185250_v, Items.FERMENTED_SPIDER_EYE, Potions.field_185252_x);
      addMix(Potions.field_185251_w, Items.FERMENTED_SPIDER_EYE, Potions.field_185253_y);
      addMix(Potions.field_185252_x, Items.GLOWSTONE_DUST, Potions.field_185253_y);
      addMix(Potions.field_185254_z, Items.FERMENTED_SPIDER_EYE, Potions.field_185252_x);
      addMix(Potions.field_185218_A, Items.FERMENTED_SPIDER_EYE, Potions.field_185252_x);
      addMix(Potions.field_185219_B, Items.FERMENTED_SPIDER_EYE, Potions.field_185253_y);
      addMix(Potions.field_185233_e, Items.SPIDER_EYE, Potions.field_185254_z);
      addMix(Potions.field_185254_z, Items.REDSTONE, Potions.field_185218_A);
      addMix(Potions.field_185254_z, Items.GLOWSTONE_DUST, Potions.field_185219_B);
      addMix(Potions.field_185233_e, Items.GHAST_TEAR, Potions.field_185220_C);
      addMix(Potions.field_185220_C, Items.REDSTONE, Potions.field_185221_D);
      addMix(Potions.field_185220_C, Items.GLOWSTONE_DUST, Potions.field_185222_E);
      addMix(Potions.field_185233_e, Items.BLAZE_POWDER, Potions.field_185223_F);
      addMix(Potions.field_185223_F, Items.REDSTONE, Potions.field_185224_G);
      addMix(Potions.field_185223_F, Items.GLOWSTONE_DUST, Potions.field_185225_H);
      addMix(Potions.field_185230_b, Items.FERMENTED_SPIDER_EYE, Potions.field_185226_I);
      addMix(Potions.field_185226_I, Items.REDSTONE, Potions.field_185227_J);
      addMix(Potions.field_185233_e, Items.PHANTOM_MEMBRANE, Potions.field_204841_O);
      addMix(Potions.field_204841_O, Items.REDSTONE, Potions.field_204842_P);
   }

   private static void addContainerRecipe(Item p_196207_0_, Item p_196207_1_, Item p_196207_2_) {
      if (!(p_196207_0_ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.field_212630_s.getKey(p_196207_0_));
      } else if (!(p_196207_2_ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.field_212630_s.getKey(p_196207_2_));
      } else {
         POTION_ITEM_CONVERSIONS.add(new PotionBrewing.MixPredicate<>(p_196207_0_, Ingredient.fromItems(p_196207_1_), p_196207_2_));
      }
   }

   private static void addContainer(Item p_196208_0_) {
      if (!(p_196208_0_ instanceof PotionItem)) {
         throw new IllegalArgumentException("Expected a potion, got: " + Registry.field_212630_s.getKey(p_196208_0_));
      } else {
         POTION_ITEMS.add(Ingredient.fromItems(p_196208_0_));
      }
   }

   private static void addMix(Potion p_193357_0_, Item p_193357_1_, Potion p_193357_2_) {
      POTION_TYPE_CONVERSIONS.add(new PotionBrewing.MixPredicate<>(p_193357_0_, Ingredient.fromItems(p_193357_1_), p_193357_2_));
   }

   static class MixPredicate<T extends net.minecraftforge.registries.ForgeRegistryEntry<T>> {
      private final net.minecraftforge.registries.IRegistryDelegate<T> input;
      private final Ingredient reagent;
      private final net.minecraftforge.registries.IRegistryDelegate<T> output;

      public MixPredicate(T inputIn, Ingredient reagentIn, T outputIn) {
         this.input = inputIn.delegate;
         this.reagent = reagentIn;
         this.output = outputIn.delegate;
      }
   }
}