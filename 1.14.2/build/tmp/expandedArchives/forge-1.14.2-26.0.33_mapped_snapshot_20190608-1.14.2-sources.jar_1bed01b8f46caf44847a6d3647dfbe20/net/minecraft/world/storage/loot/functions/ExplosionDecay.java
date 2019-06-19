package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class ExplosionDecay extends LootFunction {
   private ExplosionDecay(ILootCondition[] p_i51244_1_) {
      super(p_i51244_1_);
   }

   public ItemStack doApply(ItemStack p_215859_1_, LootContext p_215859_2_) {
      Float f = p_215859_2_.get(LootParameters.field_216290_j);
      if (f != null) {
         Random random = p_215859_2_.getRandom();
         float f1 = 1.0F / f;
         int i = p_215859_1_.getCount();
         int j = 0;

         for(int k = 0; k < i; ++k) {
            if (random.nextFloat() <= f1) {
               ++j;
            }
         }

         p_215859_1_.setCount(j);
      }

      return p_215859_1_;
   }

   public static LootFunction.Builder<?> func_215863_b() {
      return func_215860_a(ExplosionDecay::new);
   }

   public static class Serializer extends LootFunction.Serializer<ExplosionDecay> {
      protected Serializer() {
         super(new ResourceLocation("explosion_decay"), ExplosionDecay.class);
      }

      public ExplosionDecay deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         return new ExplosionDecay(conditionsIn);
      }
   }
}