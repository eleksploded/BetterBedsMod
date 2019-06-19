package net.minecraft.world.storage.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameter;
import net.minecraft.world.storage.loot.LootParameters;

public class KilledByPlayer implements ILootCondition {
   private static final KilledByPlayer INSTANCE = new KilledByPlayer();

   public Set<LootParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootParameters.field_216282_b);
   }

   public boolean test(LootContext p_test_1_) {
      return p_test_1_.has(LootParameters.field_216282_b);
   }

   public static ILootCondition.IBuilder builder() {
      return () -> {
         return INSTANCE;
      };
   }

   public static class Serializer extends ILootCondition.AbstractSerializer<KilledByPlayer> {
      protected Serializer() {
         super(new ResourceLocation("killed_by_player"), KilledByPlayer.class);
      }

      public void serialize(JsonObject json, KilledByPlayer value, JsonSerializationContext context) {
      }

      public KilledByPlayer deserialize(JsonObject json, JsonDeserializationContext context) {
         return KilledByPlayer.INSTANCE;
      }
   }
}