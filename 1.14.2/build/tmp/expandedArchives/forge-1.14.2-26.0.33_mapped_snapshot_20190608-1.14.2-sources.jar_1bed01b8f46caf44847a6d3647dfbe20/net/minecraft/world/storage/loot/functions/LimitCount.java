package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.IntClamper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class LimitCount extends LootFunction {
   private final IntClamper field_215914_a;

   private LimitCount(ILootCondition[] p_i51232_1_, IntClamper p_i51232_2_) {
      super(p_i51232_1_);
      this.field_215914_a = p_i51232_2_;
   }

   public ItemStack doApply(ItemStack p_215859_1_, LootContext p_215859_2_) {
      int i = this.field_215914_a.applyAsInt(p_215859_1_.getCount());
      p_215859_1_.setCount(i);
      return p_215859_1_;
   }

   public static LootFunction.Builder<?> func_215911_a(IntClamper p_215911_0_) {
      return func_215860_a((p_215912_1_) -> {
         return new LimitCount(p_215912_1_, p_215911_0_);
      });
   }

   public static class Serializer extends LootFunction.Serializer<LimitCount> {
      protected Serializer() {
         super(new ResourceLocation("limit_count"), LimitCount.class);
      }

      public void serialize(JsonObject object, LimitCount functionClazz, JsonSerializationContext serializationContext) {
         super.serialize(object, functionClazz, serializationContext);
         object.add("limit", serializationContext.serialize(functionClazz.field_215914_a));
      }

      public LimitCount deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         IntClamper intclamper = JSONUtils.deserializeClass(object, "limit", deserializationContext, IntClamper.class);
         return new LimitCount(conditionsIn, intclamper);
      }
   }
}