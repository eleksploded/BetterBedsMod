package net.minecraft.world.storage.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.functions.ILootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import org.apache.commons.lang3.ArrayUtils;

public abstract class StandaloneLootEntry extends LootEntry {
   protected final int field_216158_e;
   protected final int field_216159_f;
   protected final ILootFunction[] field_216160_g;
   private final BiFunction<ItemStack, LootContext, ItemStack> field_216157_c;
   private final ILootGenerator field_216161_h = new StandaloneLootEntry.Generator() {
      public void func_216188_a(Consumer<ItemStack> p_216188_1_, LootContext p_216188_2_) {
         StandaloneLootEntry.this.func_216154_a(ILootFunction.func_215858_a(StandaloneLootEntry.this.field_216157_c, p_216188_1_, p_216188_2_), p_216188_2_);
      }
   };

   protected StandaloneLootEntry(int p_i51253_1_, int p_i51253_2_, ILootCondition[] p_i51253_3_, ILootFunction[] p_i51253_4_) {
      super(p_i51253_3_);
      this.field_216158_e = p_i51253_1_;
      this.field_216159_f = p_i51253_2_;
      this.field_216160_g = p_i51253_4_;
      this.field_216157_c = LootFunctionManager.combine(p_i51253_4_);
   }

   public void func_216142_a(ValidationResults p_216142_1_, Function<ResourceLocation, LootTable> p_216142_2_, Set<ResourceLocation> p_216142_3_, LootParameterSet p_216142_4_) {
      super.func_216142_a(p_216142_1_, p_216142_2_, p_216142_3_, p_216142_4_);

      for(int i = 0; i < this.field_216160_g.length; ++i) {
         this.field_216160_g[i].func_215856_a(p_216142_1_.func_216108_b(".functions[" + i + "]"), p_216142_2_, p_216142_3_, p_216142_4_);
      }

   }

   protected abstract void func_216154_a(Consumer<ItemStack> p_216154_1_, LootContext p_216154_2_);

   public boolean expand(LootContext p_expand_1_, Consumer<ILootGenerator> p_expand_2_) {
      if (this.func_216141_a(p_expand_1_)) {
         p_expand_2_.accept(this.field_216161_h);
         return true;
      } else {
         return false;
      }
   }

   public static StandaloneLootEntry.Builder<?> func_216156_a(StandaloneLootEntry.ILootEntryBuilder p_216156_0_) {
      return new StandaloneLootEntry.BuilderImpl(p_216156_0_);
   }

   public abstract static class Builder<T extends StandaloneLootEntry.Builder<T>> extends LootEntry.Builder<T> implements ILootFunctionConsumer<T> {
      protected int field_216087_a = 1;
      protected int field_216088_b = 0;
      private final List<ILootFunction> field_216089_c = Lists.newArrayList();

      public T func_212841_b_(ILootFunction.IBuilder p_212841_1_) {
         this.field_216089_c.add(p_212841_1_.build());
         return (T)(this.func_212845_d_());
      }

      protected ILootFunction[] func_216084_a() {
         return this.field_216089_c.toArray(new ILootFunction[0]);
      }

      public T func_216086_a(int p_216086_1_) {
         this.field_216087_a = p_216086_1_;
         return (T)(this.func_212845_d_());
      }

      public T func_216085_b(int p_216085_1_) {
         this.field_216088_b = p_216085_1_;
         return (T)(this.func_212845_d_());
      }
   }

   static class BuilderImpl extends StandaloneLootEntry.Builder<StandaloneLootEntry.BuilderImpl> {
      private final StandaloneLootEntry.ILootEntryBuilder field_216090_c;

      public BuilderImpl(StandaloneLootEntry.ILootEntryBuilder p_i50485_1_) {
         this.field_216090_c = p_i50485_1_;
      }

      protected StandaloneLootEntry.BuilderImpl func_212845_d_() {
         return this;
      }

      public LootEntry func_216081_b() {
         return this.field_216090_c.build(this.field_216087_a, this.field_216088_b, this.func_216079_f(), this.func_216084_a());
      }
   }

   public abstract class Generator implements ILootGenerator {
      /**
       * Gets the effective weight based on the loot entry's weight and quality multiplied by looter's luck.
       */
      public int getEffectiveWeight(float luck) {
         return Math.max(MathHelper.floor((float)StandaloneLootEntry.this.field_216158_e + (float)StandaloneLootEntry.this.field_216159_f * luck), 0);
      }
   }

   @FunctionalInterface
   public interface ILootEntryBuilder {
      StandaloneLootEntry build(int p_build_1_, int p_build_2_, ILootCondition[] p_build_3_, ILootFunction[] p_build_4_);
   }

   public abstract static class Serializer<T extends StandaloneLootEntry> extends LootEntry.Serializer<T> {
      public Serializer(ResourceLocation p_i50483_1_, Class<T> p_i50483_2_) {
         super(p_i50483_1_, p_i50483_2_);
      }

      public void func_212830_a_(JsonObject p_212830_1_, T p_212830_2_, JsonSerializationContext p_212830_3_) {
         if (p_212830_2_.field_216158_e != 1) {
            p_212830_1_.addProperty("weight", p_212830_2_.field_216158_e);
         }

         if (p_212830_2_.field_216159_f != 0) {
            p_212830_1_.addProperty("quality", p_212830_2_.field_216159_f);
         }

         if (!ArrayUtils.isEmpty((Object[])p_212830_2_.field_216160_g)) {
            p_212830_1_.add("functions", p_212830_3_.serialize(p_212830_2_.field_216160_g));
         }

      }

      public final T func_212865_b_(JsonObject p_212865_1_, JsonDeserializationContext p_212865_2_, ILootCondition[] p_212865_3_) {
         int i = JSONUtils.getInt(p_212865_1_, "weight", 1);
         int j = JSONUtils.getInt(p_212865_1_, "quality", 0);
         ILootFunction[] ailootfunction = JSONUtils.deserializeClass(p_212865_1_, "functions", new ILootFunction[0], p_212865_2_, ILootFunction[].class);
         return (T)this.func_212829_b_(p_212865_1_, p_212865_2_, i, j, p_212865_3_, ailootfunction);
      }

      protected abstract T func_212829_b_(JsonObject p_212829_1_, JsonDeserializationContext p_212829_2_, int p_212829_3_, int p_212829_4_, ILootCondition[] p_212829_5_, ILootFunction[] p_212829_6_);
   }
}