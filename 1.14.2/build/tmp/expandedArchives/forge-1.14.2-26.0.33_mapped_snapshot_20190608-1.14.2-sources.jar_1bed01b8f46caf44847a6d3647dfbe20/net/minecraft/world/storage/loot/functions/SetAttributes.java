package net.minecraft.world.storage.loot.functions;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class SetAttributes extends LootFunction {
   private final List<SetAttributes.Modifier> field_186561_b;

   private SetAttributes(ILootCondition[] p_i51228_1_, List<SetAttributes.Modifier> p_i51228_2_) {
      super(p_i51228_1_);
      this.field_186561_b = ImmutableList.copyOf(p_i51228_2_);
   }

   public ItemStack doApply(ItemStack p_215859_1_, LootContext p_215859_2_) {
      Random random = p_215859_2_.getRandom();

      for(SetAttributes.Modifier setattributes$modifier : this.field_186561_b) {
         UUID uuid = setattributes$modifier.uuid;
         if (uuid == null) {
            uuid = UUID.randomUUID();
         }

         EquipmentSlotType equipmentslottype = setattributes$modifier.field_186601_f[random.nextInt(setattributes$modifier.field_186601_f.length)];
         p_215859_1_.addAttributeModifier(setattributes$modifier.attributeName, new AttributeModifier(uuid, setattributes$modifier.modifierName, (double)setattributes$modifier.amount.generateFloat(random), setattributes$modifier.field_186598_c), equipmentslottype);
      }

      return p_215859_1_;
   }

   static class Modifier {
      private final String modifierName;
      private final String attributeName;
      private final AttributeModifier.Operation field_186598_c;
      private final RandomValueRange amount;
      @Nullable
      private final UUID uuid;
      private final EquipmentSlotType[] field_186601_f;

      private Modifier(String p_i50835_1_, String p_i50835_2_, AttributeModifier.Operation p_i50835_3_, RandomValueRange p_i50835_4_, EquipmentSlotType[] p_i50835_5_, @Nullable UUID p_i50835_6_) {
         this.modifierName = p_i50835_1_;
         this.attributeName = p_i50835_2_;
         this.field_186598_c = p_i50835_3_;
         this.amount = p_i50835_4_;
         this.uuid = p_i50835_6_;
         this.field_186601_f = p_i50835_5_;
      }

      public JsonObject serialize(JsonSerializationContext context) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("name", this.modifierName);
         jsonobject.addProperty("attribute", this.attributeName);
         jsonobject.addProperty("operation", func_216244_a(this.field_186598_c));
         jsonobject.add("amount", context.serialize(this.amount));
         if (this.uuid != null) {
            jsonobject.addProperty("id", this.uuid.toString());
         }

         if (this.field_186601_f.length == 1) {
            jsonobject.addProperty("slot", this.field_186601_f[0].getName());
         } else {
            JsonArray jsonarray = new JsonArray();

            for(EquipmentSlotType equipmentslottype : this.field_186601_f) {
               jsonarray.add(new JsonPrimitive(equipmentslottype.getName()));
            }

            jsonobject.add("slot", jsonarray);
         }

         return jsonobject;
      }

      public static SetAttributes.Modifier deserialize(JsonObject jsonObj, JsonDeserializationContext context) {
         String s = JSONUtils.getString(jsonObj, "name");
         String s1 = JSONUtils.getString(jsonObj, "attribute");
         AttributeModifier.Operation attributemodifier$operation = func_216246_a(JSONUtils.getString(jsonObj, "operation"));
         RandomValueRange randomvaluerange = JSONUtils.deserializeClass(jsonObj, "amount", context, RandomValueRange.class);
         UUID uuid = null;
         EquipmentSlotType[] aequipmentslottype;
         if (JSONUtils.isString(jsonObj, "slot")) {
            aequipmentslottype = new EquipmentSlotType[]{EquipmentSlotType.fromString(JSONUtils.getString(jsonObj, "slot"))};
         } else {
            if (!JSONUtils.isJsonArray(jsonObj, "slot")) {
               throw new JsonSyntaxException("Invalid or missing attribute modifier slot; must be either string or array of strings.");
            }

            JsonArray jsonarray = JSONUtils.getJsonArray(jsonObj, "slot");
            aequipmentslottype = new EquipmentSlotType[jsonarray.size()];
            int i = 0;

            for(JsonElement jsonelement : jsonarray) {
               aequipmentslottype[i++] = EquipmentSlotType.fromString(JSONUtils.getString(jsonelement, "slot"));
            }

            if (aequipmentslottype.length == 0) {
               throw new JsonSyntaxException("Invalid attribute modifier slot; must contain at least one entry.");
            }
         }

         if (jsonObj.has("id")) {
            String s2 = JSONUtils.getString(jsonObj, "id");

            try {
               uuid = UUID.fromString(s2);
            } catch (IllegalArgumentException var12) {
               throw new JsonSyntaxException("Invalid attribute modifier id '" + s2 + "' (must be UUID format, with dashes)");
            }
         }

         return new SetAttributes.Modifier(s, s1, attributemodifier$operation, randomvaluerange, aequipmentslottype, uuid);
      }

      private static String func_216244_a(AttributeModifier.Operation p_216244_0_) {
         switch(p_216244_0_) {
         case ADDITION:
            return "addition";
         case MULTIPLY_BASE:
            return "multiply_base";
         case MULTIPLY_TOTAL:
            return "multiply_total";
         default:
            throw new IllegalArgumentException("Unknown operation " + p_216244_0_);
         }
      }

      private static AttributeModifier.Operation func_216246_a(String p_216246_0_) {
         byte b0 = -1;
         switch(p_216246_0_.hashCode()) {
         case -1226589444:
            if (p_216246_0_.equals("addition")) {
               b0 = 0;
            }
            break;
         case -78229492:
            if (p_216246_0_.equals("multiply_base")) {
               b0 = 1;
            }
            break;
         case 1886894441:
            if (p_216246_0_.equals("multiply_total")) {
               b0 = 2;
            }
         }

         switch(b0) {
         case 0:
            return AttributeModifier.Operation.ADDITION;
         case 1:
            return AttributeModifier.Operation.MULTIPLY_BASE;
         case 2:
            return AttributeModifier.Operation.MULTIPLY_TOTAL;
         default:
            throw new JsonSyntaxException("Unknown attribute modifier operation " + p_216246_0_);
         }
      }
   }

   public static class Serializer extends LootFunction.Serializer<SetAttributes> {
      public Serializer() {
         super(new ResourceLocation("set_attributes"), SetAttributes.class);
      }

      public void serialize(JsonObject object, SetAttributes functionClazz, JsonSerializationContext serializationContext) {
         super.serialize(object, functionClazz, serializationContext);
         JsonArray jsonarray = new JsonArray();

         for(SetAttributes.Modifier setattributes$modifier : functionClazz.field_186561_b) {
            jsonarray.add(setattributes$modifier.serialize(serializationContext));
         }

         object.add("modifiers", jsonarray);
      }

      public SetAttributes deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn) {
         JsonArray jsonarray = JSONUtils.getJsonArray(object, "modifiers");
         List<SetAttributes.Modifier> list = Lists.newArrayListWithExpectedSize(jsonarray.size());

         for(JsonElement jsonelement : jsonarray) {
            list.add(SetAttributes.Modifier.deserialize(JSONUtils.getJsonObject(jsonelement, "modifier"), deserializationContext));
         }

         if (list.isEmpty()) {
            throw new JsonSyntaxException("Invalid attribute modifiers array; cannot be empty");
         } else {
            return new SetAttributes(conditionsIn, list);
         }
      }
   }
}