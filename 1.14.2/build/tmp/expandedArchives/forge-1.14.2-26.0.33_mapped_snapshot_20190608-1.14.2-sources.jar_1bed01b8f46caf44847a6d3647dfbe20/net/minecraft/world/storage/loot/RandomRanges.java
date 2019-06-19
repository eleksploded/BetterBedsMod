package net.minecraft.world.storage.loot;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class RandomRanges {
   private static final Map<ResourceLocation, Class<? extends IRandomRange>> field_216132_a = Maps.newHashMap();

   public static IRandomRange func_216130_a(JsonElement p_216130_0_, JsonDeserializationContext p_216130_1_) throws JsonParseException {
      if (p_216130_0_.isJsonPrimitive()) {
         return p_216130_1_.deserialize(p_216130_0_, ConstantRange.class);
      } else {
         JsonObject jsonobject = p_216130_0_.getAsJsonObject();
         String s = JSONUtils.getString(jsonobject, "type", IRandomRange.field_215832_b.toString());
         Class<? extends IRandomRange> oclass = field_216132_a.get(new ResourceLocation(s));
         if (oclass == null) {
            throw new JsonParseException("Unknown generator: " + s);
         } else {
            return p_216130_1_.deserialize(jsonobject, oclass);
         }
      }
   }

   public static JsonElement func_216131_a(IRandomRange p_216131_0_, JsonSerializationContext p_216131_1_) {
      JsonElement jsonelement = p_216131_1_.serialize(p_216131_0_);
      if (jsonelement.isJsonObject()) {
         jsonelement.getAsJsonObject().addProperty("type", p_216131_0_.func_215830_a().toString());
      }

      return jsonelement;
   }

   static {
      field_216132_a.put(IRandomRange.field_215832_b, RandomValueRange.class);
      field_216132_a.put(IRandomRange.field_215833_c, BinomialRange.class);
      field_216132_a.put(IRandomRange.field_215831_a, ConstantRange.class);
   }
}