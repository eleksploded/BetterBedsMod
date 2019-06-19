package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Random;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public final class BinomialRange implements IRandomRange {
   private final int field_215841_d;
   private final float field_215842_e;

   public BinomialRange(int p_i51276_1_, float p_i51276_2_) {
      this.field_215841_d = p_i51276_1_;
      this.field_215842_e = p_i51276_2_;
   }

   public int generateInt(Random rand) {
      int i = 0;

      for(int j = 0; j < this.field_215841_d; ++j) {
         if (rand.nextFloat() < this.field_215842_e) {
            ++i;
         }
      }

      return i;
   }

   public static BinomialRange func_215838_a(int p_215838_0_, float p_215838_1_) {
      return new BinomialRange(p_215838_0_, p_215838_1_);
   }

   public ResourceLocation func_215830_a() {
      return field_215833_c;
   }

   public static class Serializer implements JsonDeserializer<BinomialRange>, JsonSerializer<BinomialRange> {
      public BinomialRange deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
         JsonObject jsonobject = JSONUtils.getJsonObject(p_deserialize_1_, "value");
         int i = JSONUtils.getInt(jsonobject, "n");
         float f = JSONUtils.getFloat(jsonobject, "p");
         return new BinomialRange(i, f);
      }

      public JsonElement serialize(BinomialRange p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("n", p_serialize_1_.field_215841_d);
         jsonobject.addProperty("p", p_serialize_1_.field_215842_e);
         return jsonobject;
      }
   }
}