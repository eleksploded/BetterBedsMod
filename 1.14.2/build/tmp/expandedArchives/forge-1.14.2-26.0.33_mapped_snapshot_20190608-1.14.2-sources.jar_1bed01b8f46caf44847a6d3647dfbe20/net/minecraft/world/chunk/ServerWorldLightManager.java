package net.minecraft.world.chunk;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.lighting.WorldLightManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerWorldLightManager extends WorldLightManager implements AutoCloseable {
   private static final Logger field_215604_a = LogManager.getLogger();
   private final DelegatedTaskExecutor<Runnable> field_215605_b;
   private final ObjectList<Pair<ServerWorldLightManager.Phase, Runnable>> field_215606_c = new ObjectArrayList<>();
   private final ChunkManager field_215607_d;
   private final ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> field_215608_e;
   private volatile int field_215609_f = 5;
   private final AtomicBoolean field_215610_g = new AtomicBoolean();

   public ServerWorldLightManager(IChunkLightProvider p_i50701_1_, ChunkManager p_i50701_2_, boolean p_i50701_3_, DelegatedTaskExecutor<Runnable> p_i50701_4_, ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> p_i50701_5_) {
      super(p_i50701_1_, true, p_i50701_3_);
      this.field_215607_d = p_i50701_2_;
      this.field_215608_e = p_i50701_5_;
      this.field_215605_b = p_i50701_4_;
   }

   public void close() {
   }

   public int func_215575_a(int p_215575_1_, boolean p_215575_2_, boolean p_215575_3_) {
      throw new UnsupportedOperationException("Ran authomatically on a different thread!");
   }

   public void func_215573_a(BlockPos p_215573_1_, int p_215573_2_) {
      throw new UnsupportedOperationException("Ran authomatically on a different thread!");
   }

   public void func_215568_a(BlockPos p_215568_1_) {
      BlockPos blockpos = p_215568_1_.toImmutable();
      this.func_215586_a(p_215568_1_.getX() >> 4, p_215568_1_.getZ() >> 4, ServerWorldLightManager.Phase.POST_UPDATE, Util.namedRunnable(() -> {
         super.func_215568_a(blockpos);
      }, () -> {
         return "checkBlock " + blockpos;
      }));
   }

   protected void func_215581_a(ChunkPos p_215581_1_) {
      this.func_215600_a(p_215581_1_.x, p_215581_1_.z, () -> {
         return 0;
      }, ServerWorldLightManager.Phase.PRE_UPDATE, Util.namedRunnable(() -> {
         super.func_223115_b(p_215581_1_, false);

         for(int i = -1; i < 17; ++i) {
            super.func_215574_a(LightType.BLOCK, SectionPos.from(p_215581_1_, i), (NibbleArray)null);
            super.func_215574_a(LightType.SKY, SectionPos.from(p_215581_1_, i), (NibbleArray)null);
         }

         for(int j = 0; j < 16; ++j) {
            super.func_215566_a(SectionPos.from(p_215581_1_, j), true);
         }

      }, () -> {
         return "updateChunkStatus " + p_215581_1_ + " " + true;
      }));
   }

   public void func_215566_a(SectionPos p_215566_1_, boolean p_215566_2_) {
      this.func_215600_a(p_215566_1_.func_218149_a(), p_215566_1_.func_218148_c(), () -> {
         return 0;
      }, ServerWorldLightManager.Phase.PRE_UPDATE, Util.namedRunnable(() -> {
         super.func_215566_a(p_215566_1_, p_215566_2_);
      }, () -> {
         return "updateSectionStatus " + p_215566_1_ + " " + p_215566_2_;
      }));
   }

   public void func_215571_a(ChunkPos p_215571_1_, boolean p_215571_2_) {
      this.func_215586_a(p_215571_1_.x, p_215571_1_.z, ServerWorldLightManager.Phase.PRE_UPDATE, Util.namedRunnable(() -> {
         super.func_215571_a(p_215571_1_, p_215571_2_);
      }, () -> {
         return "enableLight " + p_215571_1_ + " " + p_215571_2_;
      }));
   }

   public void func_215574_a(LightType p_215574_1_, SectionPos p_215574_2_, @Nullable NibbleArray p_215574_3_) {
      this.func_215600_a(p_215574_2_.func_218149_a(), p_215574_2_.func_218148_c(), () -> {
         return 0;
      }, ServerWorldLightManager.Phase.PRE_UPDATE, Util.namedRunnable(() -> {
         super.func_215574_a(p_215574_1_, p_215574_2_, p_215574_3_);
      }, () -> {
         return "queueData " + p_215574_2_;
      }));
   }

   private void func_215586_a(int p_215586_1_, int p_215586_2_, ServerWorldLightManager.Phase p_215586_3_, Runnable p_215586_4_) {
      this.func_215600_a(p_215586_1_, p_215586_2_, this.field_215607_d.func_219191_c(ChunkPos.asLong(p_215586_1_, p_215586_2_)), p_215586_3_, p_215586_4_);
   }

   private void func_215600_a(int p_215600_1_, int p_215600_2_, IntSupplier p_215600_3_, ServerWorldLightManager.Phase p_215600_4_, Runnable p_215600_5_) {
      this.field_215608_e.enqueue(ChunkTaskPriorityQueueSorter.func_219069_a(() -> {
         this.field_215606_c.add(Pair.of(p_215600_4_, p_215600_5_));
         if (this.field_215606_c.size() >= this.field_215609_f) {
            this.func_215603_b();
         }

      }, ChunkPos.asLong(p_215600_1_, p_215600_2_), p_215600_3_));
   }

   public void func_223115_b(ChunkPos p_223115_1_, boolean p_223115_2_) {
      this.func_215600_a(p_223115_1_.x, p_223115_1_.z, () -> {
         return 0;
      }, ServerWorldLightManager.Phase.PRE_UPDATE, Util.namedRunnable(() -> {
         super.func_223115_b(p_223115_1_, p_223115_2_);
      }, () -> {
         return "retainData " + p_223115_1_;
      }));
   }

   public CompletableFuture<IChunk> func_215593_a(IChunk p_215593_1_, boolean p_215593_2_) {
      ChunkPos chunkpos = p_215593_1_.getPos();
      p_215593_1_.setLight(false);
      this.func_215586_a(chunkpos.x, chunkpos.z, ServerWorldLightManager.Phase.PRE_UPDATE, Util.namedRunnable(() -> {
         ChunkSection[] achunksection = p_215593_1_.getSections();

         for(int i = 0; i < 16; ++i) {
            ChunkSection chunksection = achunksection[i];
            if (!ChunkSection.isEmpty(chunksection)) {
               super.func_215566_a(SectionPos.from(chunkpos, i), false);
            }
         }

         super.func_215571_a(chunkpos, true);
         if (!p_215593_2_) {
            p_215593_1_.func_217304_m().forEach((p_215579_2_) -> {
               super.func_215573_a(p_215579_2_, p_215593_1_.func_217298_h(p_215579_2_));
            });
         }

         this.field_215607_d.func_219209_c(chunkpos);
      }, () -> {
         return "lightChunk " + chunkpos + " " + p_215593_2_;
      }));
      return CompletableFuture.supplyAsync(() -> {
         p_215593_1_.setLight(true);
         super.func_223115_b(chunkpos, false);
         return p_215593_1_;
      }, (p_215597_2_) -> {
         this.func_215586_a(chunkpos.x, chunkpos.z, ServerWorldLightManager.Phase.POST_UPDATE, p_215597_2_);
      });
   }

   public void func_215588_z_() {
      if ((!this.field_215606_c.isEmpty() || super.func_215570_a()) && this.field_215610_g.compareAndSet(false, true)) {
         this.field_215605_b.enqueue(() -> {
            this.func_215603_b();
            this.field_215610_g.set(false);
         });
      }

   }

   private void func_215603_b() {
      int i = Math.min(this.field_215606_c.size(), this.field_215609_f);
      ObjectListIterator<Pair<ServerWorldLightManager.Phase, Runnable>> objectlistiterator = this.field_215606_c.iterator();

      int j;
      for(j = 0; objectlistiterator.hasNext() && j < i; ++j) {
         Pair<ServerWorldLightManager.Phase, Runnable> pair = objectlistiterator.next();
         if (pair.getFirst() == ServerWorldLightManager.Phase.PRE_UPDATE) {
            pair.getSecond().run();
         }
      }

      objectlistiterator.back(j);
      super.func_215575_a(Integer.MAX_VALUE, true, true);

      for(int k = 0; objectlistiterator.hasNext() && k < i; ++k) {
         Pair<ServerWorldLightManager.Phase, Runnable> pair1 = objectlistiterator.next();
         if (pair1.getFirst() == ServerWorldLightManager.Phase.POST_UPDATE) {
            pair1.getSecond().run();
         }

         objectlistiterator.remove();
      }

   }

   public void func_215598_a(int p_215598_1_) {
      this.field_215609_f = p_215598_1_;
   }

   static enum Phase {
      PRE_UPDATE,
      POST_UPDATE;
   }
}