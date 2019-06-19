package net.minecraft.world.storage.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.LootParameter;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class LootingEnchantBonus extends LootFunction {
   private final RandomValueRange count;
   private final int limit;

   private LootingEnchantBonus(ILootCondition[] conditions, RandomValueRange countIn, int limitIn) {
      super(conditions);
      this.count = countIn;
      this.limit = limitIn;
   }

   public Set<LootParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootParameters.field_216284_d);
   }

   private boolean func_215917_b() {
      return this.limit > 0;
   }

   public ItemStack doApply(ItemStack p_215859_1_, LootContext p_215859_2_) {
      Entity entity = p_215859_2_.get(LootParameters.field_216284_d);
      if (entity instanceof LivingEntity) {
         int i = p_215859_2_.getLootingModifier();
         if (i == 0) {
            return p_215859_1_;
         }

         float f = (float)i * this.count.generateFloat(p_215859_2_.getRandom());
         p_215859_1_.grow(Math.round(f));
         if (this.func_215917_b() && p_215859_1_.getCount() > this.limit) {
            p_215859_1_.setCount(this.limit);
         }
      }

      return p_215859_1_;
   }

   public static LootingEnchantBonus.Builder func_215915_a(RandomValueRange p_215915_0_) {
      return new LootingEnchantBonus.Builder(p_215915_0_);
   }

   public static class Builder extends LootFunction.Builder<LootingEnchantBonus.Builder> {
      private final RandomValueRange field_216073_a;
      private int field_216074_b = 0;

      public Builder(RandomValueRange p_i50932_1_) {
         this.field_216073_a = p_i50932_1_;
      }

      protected LootingEnchantBonus.Builder func_212826_d_() {
         return this;
      }

      public LootingEnchantBonus.Builder func_216072_a(int p_216072_1_) {
         this.field_216074_b = p_216072_1_;
         return this;
      }

      public ILootFunction build() {
         return new LootingEnchantBonus(this.func_216053_g(), this.field_216073_a, this.field_216074_b);
      }
   }

   public static class Serializer extends LootFunction.Serializer<LootingEnchantBonus> {
      protected Serializer() {
         super(new ResourceLocation("looting_enchant"), LootingEnchantBonus.class);
      }

      public void serialize(JsonObject object, LootingEnchantBonus functionClazz, JsonSerializationContext serializationContext) {
         super.serialize(object, functionClazz, serializationContext);
         object.add("count", serializationContext.serialize(functionClazz.count));
         if (functionClazz.func_215917_b()) {
            object.add("limit", serializationContext.serialize(functionClazz.limit));
         }

      }

      public LootingEnchantBonus deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         int i = JSONUtils.getInt(object, "limit", 0);
         return new LootingEnchantBonus(conditionsIn, JSONUtils.deserializeClass(object, "count", deserializationContext, RandomValueRange.class), i);
      }
   }
}