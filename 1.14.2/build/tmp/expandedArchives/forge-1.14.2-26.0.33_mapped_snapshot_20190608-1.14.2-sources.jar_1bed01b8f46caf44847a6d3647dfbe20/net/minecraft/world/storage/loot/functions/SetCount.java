package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.RandomRanges;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class SetCount extends LootFunction {
   private final IRandomRange field_186568_a;

   private SetCount(ILootCondition[] p_i51222_1_, IRandomRange p_i51222_2_) {
      super(p_i51222_1_);
      this.field_186568_a = p_i51222_2_;
   }

   public ItemStack doApply(ItemStack p_215859_1_, LootContext p_215859_2_) {
      p_215859_1_.setCount(this.field_186568_a.generateInt(p_215859_2_.getRandom()));
      return p_215859_1_;
   }

   public static LootFunction.Builder<?> func_215932_a(IRandomRange p_215932_0_) {
      return func_215860_a((p_215934_1_) -> {
         return new SetCount(p_215934_1_, p_215932_0_);
      });
   }

   public static class Serializer extends LootFunction.Serializer<SetCount> {
      protected Serializer() {
         super(new ResourceLocation("set_count"), SetCount.class);
      }

      public void serialize(JsonObject object, SetCount functionClazz, JsonSerializationContext serializationContext) {
         super.serialize(object, functionClazz, serializationContext);
         object.add("count", RandomRanges.func_216131_a(functionClazz.field_186568_a, serializationContext));
      }

      public SetCount deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         IRandomRange irandomrange = RandomRanges.func_216130_a(object.get("count"), deserializationContext);
         return new SetCount(conditionsIn, irandomrange);
      }
   }
}