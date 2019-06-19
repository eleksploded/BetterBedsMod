package net.minecraft.resources;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.profiler.EmptyProfiler;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AsyncReloader<S> implements IAsyncReloader {
   protected final IResourceManager field_219565_a;
   protected final CompletableFuture<Unit> field_219566_b = new CompletableFuture<>();
   protected final CompletableFuture<List<S>> field_219567_c;
   private final Set<IFutureReloadListener> field_219568_d;
   private final int field_219569_e;
   private int field_219570_f;
   private int field_219571_g;
   private final AtomicInteger field_219572_h = new AtomicInteger();
   private final AtomicInteger field_219573_i = new AtomicInteger();

   public static AsyncReloader<Void> func_219562_a(IResourceManager p_219562_0_, List<IFutureReloadListener> p_219562_1_, Executor p_219562_2_, Executor p_219562_3_, CompletableFuture<Unit> p_219562_4_) {
      return new AsyncReloader<>(p_219562_2_, p_219562_3_, p_219562_0_, p_219562_1_, (p_219561_1_, p_219561_2_, p_219561_3_, p_219561_4_, p_219561_5_) -> {
         return p_219561_3_.func_215226_a(p_219561_1_, p_219561_2_, EmptyProfiler.INSTANCE, EmptyProfiler.INSTANCE, p_219562_2_, p_219561_5_);
      }, p_219562_4_);
   }

   protected AsyncReloader(Executor p_i50690_1_, final Executor p_i50690_2_, IResourceManager p_i50690_3_, List<IFutureReloadListener> p_i50690_4_, AsyncReloader.IStateFactory<S> p_i50690_5_, CompletableFuture<Unit> p_i50690_6_) {
      this.field_219565_a = p_i50690_3_;
      this.field_219569_e = p_i50690_4_.size();
      this.field_219572_h.incrementAndGet();
      p_i50690_6_.thenRun(this.field_219573_i::incrementAndGet);
      List<CompletableFuture<S>> list = new ArrayList<>();
      CompletableFuture<?> completablefuture = p_i50690_6_;
      this.field_219568_d = Sets.newHashSet(p_i50690_4_);

      for(final IFutureReloadListener ifuturereloadlistener : p_i50690_4_) {
         CompletableFuture<?> completablefuture_f = completablefuture;
         CompletableFuture<S> completablefuture1 = p_i50690_5_.create(new IFutureReloadListener.IStage() {
            public <T> CompletableFuture<T> func_216872_a(T p_216872_1_) {
               p_i50690_2_.execute(() -> {
                  AsyncReloader.this.field_219568_d.remove(ifuturereloadlistener);
                  if (AsyncReloader.this.field_219568_d.isEmpty()) {
                     AsyncReloader.this.field_219566_b.complete(Unit.INSTANCE);
                  }

               });
               return AsyncReloader.this.field_219566_b.thenCombine(completablefuture_f, (p_216874_1_, p_216874_2_) -> {
                  return p_216872_1_;
               });
            }
         }, p_i50690_3_, ifuturereloadlistener, (p_219564_2_) -> {
            this.field_219572_h.incrementAndGet();
            p_i50690_1_.execute(() -> {
               p_219564_2_.run();
               this.field_219573_i.incrementAndGet();
            });
         }, (p_219560_2_) -> {
            ++this.field_219570_f;
            p_i50690_2_.execute(() -> {
               p_219560_2_.run();
               ++this.field_219571_g;
            });
         });
         list.add(completablefuture1);
         completablefuture = completablefuture1;
      }

      this.field_219567_c = Util.gather(list);
   }

   public CompletableFuture<Unit> func_219552_a() {
      return this.field_219567_c.thenApply((p_219558_0_) -> {
         return Unit.INSTANCE;
      });
   }

   @OnlyIn(Dist.CLIENT)
   public float func_219555_b() {
      int i = this.field_219569_e - this.field_219568_d.size();
      float f = (float)(this.field_219573_i.get() * 2 + this.field_219571_g * 2 + i * 1);
      float f1 = (float)(this.field_219572_h.get() * 2 + this.field_219570_f * 2 + this.field_219569_e * 1);
      return f / f1;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_219553_c() {
      return this.field_219566_b.isDone();
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_219554_d() {
      return this.field_219567_c.isDone();
   }

   @OnlyIn(Dist.CLIENT)
   public void func_219556_e() {
      if (this.field_219567_c.isCompletedExceptionally()) {
         this.field_219567_c.join();
      }

   }

   public interface IStateFactory<S> {
      CompletableFuture<S> create(IFutureReloadListener.IStage p_create_1_, IResourceManager p_create_2_, IFutureReloadListener p_create_3_, Executor p_create_4_, Executor p_create_5_);
   }
}