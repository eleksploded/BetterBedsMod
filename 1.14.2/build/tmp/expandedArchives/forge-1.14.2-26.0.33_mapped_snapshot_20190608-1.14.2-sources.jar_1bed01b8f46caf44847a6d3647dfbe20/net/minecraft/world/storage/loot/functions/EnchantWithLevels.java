package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.RandomRanges;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class EnchantWithLevels extends LootFunction {
   private final IRandomRange field_186577_a;
   private final boolean isTreasure;

   private EnchantWithLevels(ILootCondition[] p_i51236_1_, IRandomRange p_i51236_2_, boolean p_i51236_3_) {
      super(p_i51236_1_);
      this.field_186577_a = p_i51236_2_;
      this.isTreasure = p_i51236_3_;
   }

   public ItemStack doApply(ItemStack p_215859_1_, LootContext p_215859_2_) {
      Random random = p_215859_2_.getRandom();
      return EnchantmentHelper.addRandomEnchantment(random, p_215859_1_, this.field_186577_a.generateInt(random), this.isTreasure);
   }

   public static EnchantWithLevels.Builder func_215895_a(IRandomRange p_215895_0_) {
      return new EnchantWithLevels.Builder(p_215895_0_);
   }

   public static class Builder extends LootFunction.Builder<EnchantWithLevels.Builder> {
      private final IRandomRange field_216060_a;
      private boolean field_216061_b;

      public Builder(IRandomRange p_i51494_1_) {
         this.field_216060_a = p_i51494_1_;
      }

      protected EnchantWithLevels.Builder func_212826_d_() {
         return this;
      }

      public EnchantWithLevels.Builder func_216059_e() {
         this.field_216061_b = true;
         return this;
      }

      public ILootFunction build() {
         return new EnchantWithLevels(this.func_216053_g(), this.field_216060_a, this.field_216061_b);
      }
   }

   public static class Serializer extends LootFunction.Serializer<EnchantWithLevels> {
      public Serializer() {
         super(new ResourceLocation("enchant_with_levels"), EnchantWithLevels.class);
      }

      public void serialize(JsonObject object, EnchantWithLevels functionClazz, JsonSerializationContext serializationContext) {
         super.serialize(object, functionClazz, serializationContext);
         object.add("levels", RandomRanges.func_216131_a(functionClazz.field_186577_a, serializationContext));
         object.addProperty("treasure", functionClazz.isTreasure);
      }

      public EnchantWithLevels deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         IRandomRange irandomrange = RandomRanges.func_216130_a(object.get("levels"), deserializationContext);
         boolean flag = JSONUtils.getBoolean(object, "treasure", false);
         return new EnchantWithLevels(conditionsIn, irandomrange, flag);
      }
   }
}