package net.minecraft.tags;

import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class NetworkTagManager implements IFutureReloadListener {
   private final NetworkTagCollection<Block> blocks = new NetworkTagCollection<>(Registry.field_212618_g, "tags/blocks", "block");
   private final NetworkTagCollection<Item> items = new NetworkTagCollection<>(Registry.field_212630_s, "tags/items", "item");
   private final NetworkTagCollection<Fluid> fluids = new NetworkTagCollection<>(Registry.field_212619_h, "tags/fluids", "fluid");
   private final NetworkTagCollection<EntityType<?>> field_215299_d = new NetworkTagCollection<>(Registry.field_212629_r, "tags/entity_types", "entity_type");

   public NetworkTagCollection<Block> getBlocks() {
      return this.blocks;
   }

   public NetworkTagCollection<Item> getItems() {
      return this.items;
   }

   public NetworkTagCollection<Fluid> getFluids() {
      return this.fluids;
   }

   public NetworkTagCollection<EntityType<?>> func_215297_d() {
      return this.field_215299_d;
   }

   public void clear() {
      this.blocks.clear();
      this.items.clear();
      this.fluids.clear();
      this.field_215299_d.clear();
   }

   public void write(PacketBuffer buffer) {
      this.blocks.write(buffer);
      this.items.write(buffer);
      this.fluids.write(buffer);
      this.field_215299_d.write(buffer);
   }

   public static NetworkTagManager read(PacketBuffer buffer) {
      NetworkTagManager networktagmanager = new NetworkTagManager();
      networktagmanager.getBlocks().read(buffer);
      networktagmanager.getItems().read(buffer);
      networktagmanager.getFluids().read(buffer);
      networktagmanager.func_215297_d().read(buffer);
      return networktagmanager;
   }

   public CompletableFuture<Void> func_215226_a(IFutureReloadListener.IStage p_215226_1_, IResourceManager p_215226_2_, IProfiler p_215226_3_, IProfiler p_215226_4_, Executor p_215226_5_, Executor p_215226_6_) {
      CompletableFuture<Map<ResourceLocation, Tag.Builder<Block>>> completablefuture = this.blocks.func_219781_a(p_215226_2_, p_215226_5_);
      CompletableFuture<Map<ResourceLocation, Tag.Builder<Item>>> completablefuture1 = this.items.func_219781_a(p_215226_2_, p_215226_5_);
      CompletableFuture<Map<ResourceLocation, Tag.Builder<Fluid>>> completablefuture2 = this.fluids.func_219781_a(p_215226_2_, p_215226_5_);
      CompletableFuture<Map<ResourceLocation, Tag.Builder<EntityType<?>>>> completablefuture3 = this.field_215299_d.func_219781_a(p_215226_2_, p_215226_5_);
      return completablefuture.thenCombine(completablefuture1, Pair::of).thenCombine(completablefuture2.thenCombine(completablefuture3, Pair::of), (p_215296_0_, p_215296_1_) -> {
         return new NetworkTagManager.ReloadResults(p_215296_0_.getFirst(), p_215296_0_.getSecond(), p_215296_1_.getFirst(), p_215296_1_.getSecond());
      }).thenCompose(p_215226_1_::func_216872_a).thenAcceptAsync((p_215298_1_) -> {
         this.clear();
         this.blocks.func_219779_a(p_215298_1_.field_219785_a);
         this.items.func_219779_a(p_215298_1_.field_219786_b);
         this.fluids.func_219779_a(p_215298_1_.field_219787_c);
         this.field_215299_d.func_219779_a(p_215298_1_.field_219788_d);
         BlockTags.setCollection(this.blocks);
         ItemTags.setCollection(this.items);
         FluidTags.setCollection(this.fluids);
         EntityTypeTags.func_219759_a(this.field_215299_d);
      }, p_215226_6_);
   }

   public static class ReloadResults {
      final Map<ResourceLocation, Tag.Builder<Block>> field_219785_a;
      final Map<ResourceLocation, Tag.Builder<Item>> field_219786_b;
      final Map<ResourceLocation, Tag.Builder<Fluid>> field_219787_c;
      final Map<ResourceLocation, Tag.Builder<EntityType<?>>> field_219788_d;

      public ReloadResults(Map<ResourceLocation, Tag.Builder<Block>> p_i50480_1_, Map<ResourceLocation, Tag.Builder<Item>> p_i50480_2_, Map<ResourceLocation, Tag.Builder<Fluid>> p_i50480_3_, Map<ResourceLocation, Tag.Builder<EntityType<?>>> p_i50480_4_) {
         this.field_219785_a = p_i50480_1_;
         this.field_219786_b = p_i50480_2_;
         this.field_219787_c = p_i50480_3_;
         this.field_219788_d = p_i50480_4_;
      }
   }
}