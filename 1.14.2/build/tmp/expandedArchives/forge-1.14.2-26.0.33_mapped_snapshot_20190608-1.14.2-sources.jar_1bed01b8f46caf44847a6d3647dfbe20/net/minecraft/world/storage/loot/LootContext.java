package net.minecraft.world.storage.loot;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ServerWorld;

public class LootContext {
   private final Random field_216035_a;
   private final float luck;
   private final ServerWorld field_186499_b;
   private final LootTableManager lootTableManager;
   private final Set<LootTable> lootTables = Sets.newLinkedHashSet();
   private final Map<LootParameter<?>, Object> field_216036_f;
   private final Map<ResourceLocation, LootContext.IDynamicDropProvider> field_216037_g;

   private LootContext(Random p_i51271_1_, float p_i51271_2_, ServerWorld p_i51271_3_, LootTableManager p_i51271_4_, Map<LootParameter<?>, Object> p_i51271_5_, Map<ResourceLocation, LootContext.IDynamicDropProvider> p_i51271_6_) {
      this.field_216035_a = p_i51271_1_;
      this.luck = p_i51271_2_;
      this.field_186499_b = p_i51271_3_;
      this.lootTableManager = p_i51271_4_;
      this.field_216036_f = ImmutableMap.copyOf(p_i51271_5_);
      this.field_216037_g = ImmutableMap.copyOf(p_i51271_6_);
   }

   public boolean has(LootParameter<?> p_216033_1_) {
      return this.field_216036_f.containsKey(p_216033_1_);
   }

   public void func_216034_a(ResourceLocation p_216034_1_, Consumer<ItemStack> p_216034_2_) {
      LootContext.IDynamicDropProvider lootcontext$idynamicdropprovider = this.field_216037_g.get(p_216034_1_);
      if (lootcontext$idynamicdropprovider != null) {
         lootcontext$idynamicdropprovider.add(this, p_216034_2_);
      }

   }

   @Nullable
   public <T> T get(LootParameter<T> p_216031_1_) {
      return (T)this.field_216036_f.get(p_216031_1_);
   }

   public boolean addLootTable(LootTable lootTableIn) {
      return this.lootTables.add(lootTableIn);
   }

   public void removeLootTable(LootTable lootTableIn) {
      this.lootTables.remove(lootTableIn);
   }

   public LootTableManager getLootTableManager() {
      return this.lootTableManager;
   }

   public Random getRandom() {
      return this.field_216035_a;
   }

   public float getLuck() {
      return this.luck;
   }

   public ServerWorld getWorld() {
      return this.field_186499_b;
   }

   public int getLootingModifier() {
      return net.minecraftforge.common.ForgeHooks.getLootingLevel(get(LootParameters.field_216281_a), get(LootParameters.field_216284_d), get(LootParameters.field_216283_c));
   }

   public static class Builder {
      private final ServerWorld field_186474_a;
      private final Map<LootParameter<?>, Object> field_216025_b = Maps.newIdentityHashMap();
      private final Map<ResourceLocation, LootContext.IDynamicDropProvider> field_216026_c = Maps.newHashMap();
      private Random field_216027_d;
      private float luck;

      public Builder(ServerWorld worldIn) {
         this.field_186474_a = worldIn;
      }

      public LootContext.Builder withRandom(Random p_216023_1_) {
         this.field_216027_d = p_216023_1_;
         return this;
      }

      public LootContext.Builder withSeed(long p_216016_1_) {
         if (p_216016_1_ != 0L) {
            this.field_216027_d = new Random(p_216016_1_);
         }

         return this;
      }

      public LootContext.Builder withSeededRandom(long p_216020_1_, Random p_216020_3_) {
         if (p_216020_1_ == 0L) {
            this.field_216027_d = p_216020_3_;
         } else {
            this.field_216027_d = new Random(p_216020_1_);
         }

         return this;
      }

      public LootContext.Builder withLuck(float luckIn) {
         this.luck = luckIn;
         return this;
      }

      public <T> LootContext.Builder withParameter(LootParameter<T> p_216015_1_, T p_216015_2_) {
         this.field_216025_b.put(p_216015_1_, p_216015_2_);
         return this;
      }

      public <T> LootContext.Builder withNullableParameter(LootParameter<T> p_216021_1_, @Nullable T p_216021_2_) {
         if (p_216021_2_ == null) {
            this.field_216025_b.remove(p_216021_1_);
         } else {
            this.field_216025_b.put(p_216021_1_, p_216021_2_);
         }

         return this;
      }

      public LootContext.Builder withDynamicDrop(ResourceLocation p_216017_1_, LootContext.IDynamicDropProvider p_216017_2_) {
         LootContext.IDynamicDropProvider lootcontext$idynamicdropprovider = this.field_216026_c.put(p_216017_1_, p_216017_2_);
         if (lootcontext$idynamicdropprovider != null) {
            throw new IllegalStateException("Duplicated dynamic drop '" + this.field_216026_c + "'");
         } else {
            return this;
         }
      }

      public ServerWorld func_216018_a() {
         return this.field_186474_a;
      }

      public <T> T assertPresent(LootParameter<T> p_216024_1_) {
         T t = (T)this.field_216025_b.get(p_216024_1_);
         if (t == null) {
            throw new IllegalArgumentException("No parameter " + p_216024_1_);
         } else {
            return t;
         }
      }

      @Nullable
      public <T> T get(LootParameter<T> p_216019_1_) {
         return (T)this.field_216025_b.get(p_216019_1_);
      }

      public LootContext build(LootParameterSet p_216022_1_) {
         Set<LootParameter<?>> set = Sets.difference(this.field_216025_b.keySet(), p_216022_1_.func_216276_b());
         if (!set.isEmpty()) {
            throw new IllegalArgumentException("Parameters not allowed in this parameter set: " + set);
         } else {
            Set<LootParameter<?>> set1 = Sets.difference(p_216022_1_.func_216277_a(), this.field_216025_b.keySet());
            if (!set1.isEmpty()) {
               throw new IllegalArgumentException("Missing required parameters: " + set1);
            } else {
               Random random = this.field_216027_d;
               if (random == null) {
                  random = new Random();
               }

               return new LootContext(random, this.luck, this.field_186474_a, this.field_186474_a.getServer().getLootTableManager(), this.field_216025_b, this.field_216026_c);
            }
         }
      }
   }

   public static enum EntityTarget {
      THIS("this", LootParameters.field_216281_a),
      KILLER("killer", LootParameters.field_216284_d),
      DIRECT_KILLER("direct_killer", LootParameters.field_216285_e),
      KILLER_PLAYER("killer_player", LootParameters.field_216282_b);

      private final String targetType;
      private final LootParameter<? extends Entity> field_216030_f;

      private EntityTarget(String p_i50476_3_, LootParameter<? extends Entity> p_i50476_4_) {
         this.targetType = p_i50476_3_;
         this.field_216030_f = p_i50476_4_;
      }

      public LootParameter<? extends Entity> func_216029_a() {
         return this.field_216030_f;
      }

      public static LootContext.EntityTarget fromString(String type) {
         for(LootContext.EntityTarget lootcontext$entitytarget : values()) {
            if (lootcontext$entitytarget.targetType.equals(type)) {
               return lootcontext$entitytarget;
            }
         }

         throw new IllegalArgumentException("Invalid entity target " + type);
      }

      public static class Serializer extends TypeAdapter<LootContext.EntityTarget> {
         public void write(JsonWriter p_write_1_, LootContext.EntityTarget p_write_2_) throws IOException {
            p_write_1_.value(p_write_2_.targetType);
         }

         public LootContext.EntityTarget read(JsonReader p_read_1_) throws IOException {
            return LootContext.EntityTarget.fromString(p_read_1_.nextString());
         }
      }
   }

   @FunctionalInterface
   public interface IDynamicDropProvider {
      void add(LootContext p_add_1_, Consumer<ItemStack> p_add_2_);
   }
}