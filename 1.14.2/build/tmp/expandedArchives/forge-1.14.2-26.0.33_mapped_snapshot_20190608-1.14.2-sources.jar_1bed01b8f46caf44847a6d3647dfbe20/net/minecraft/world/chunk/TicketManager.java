package net.minecraft.world.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectAVLTreeSet;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SUpdateChunkPositionPacket;
import net.minecraft.util.SectionDistanceGraph;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TicketManager {
   private static final Logger field_219373_a = LogManager.getLogger();
   private static final int field_219374_b = 33 + ChunkStatus.func_222599_a(ChunkStatus.FULL) - 2;
   private final Long2ObjectMap<ObjectSet<ServerPlayerEntity>> field_219375_c = new Long2ObjectOpenHashMap<>();
   private final Long2ObjectMap<ObjectSet<ServerPlayerEntity>> field_219376_d = new Long2ObjectOpenHashMap<>();
   private final Long2ObjectOpenHashMap<ObjectSortedSet<Ticket<?>>> tickets = new Long2ObjectOpenHashMap<>();
   private final TicketManager.ChunkTicketTracker field_219378_f = new TicketManager.ChunkTicketTracker();
   private final TicketManager.PlayerSectionTracker field_219379_g = new TicketManager.PlayerSectionTracker();
   private int field_219380_h;
   private final TicketManager.PlayerChunkTracker field_219381_i = new TicketManager.PlayerChunkTracker(8);
   private final TicketManager.PlayerTicketTracker field_219382_j = new TicketManager.PlayerTicketTracker(33);
   private final Set<ChunkHolder> field_219383_k = Sets.newHashSet();
   private final ChunkHolder.IListener field_219384_l;
   private final ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> field_219385_m;
   private final ITaskExecutor<ChunkTaskPriorityQueueSorter.RunnableEntry> field_219386_n;
   private final LongSet field_219387_o = new LongOpenHashSet();
   private final Executor field_219388_p;
   private long currentTime;

   protected TicketManager(Executor p_i50707_1_, Executor p_i50707_2_) {
      DelegatedTaskExecutor<Runnable> delegatedtaskexecutor = DelegatedTaskExecutor.func_213144_a(p_i50707_2_, "player ticket throttler");
      ChunkTaskPriorityQueueSorter chunktaskpriorityqueuesorter = new ChunkTaskPriorityQueueSorter(ImmutableList.of(delegatedtaskexecutor), p_i50707_1_, 15);
      this.field_219384_l = chunktaskpriorityqueuesorter;
      this.field_219385_m = chunktaskpriorityqueuesorter.func_219087_a(delegatedtaskexecutor, true);
      this.field_219386_n = chunktaskpriorityqueuesorter.func_219091_a(delegatedtaskexecutor);
      this.field_219388_p = p_i50707_2_;
   }

   protected void func_219351_a(int p_219351_1_) {
      int i = this.func_219333_e();
      this.field_219380_h = p_219351_1_;
      int j = this.func_219333_e();

      for(Entry<ObjectSet<ServerPlayerEntity>> entry : this.field_219376_d.long2ObjectEntrySet()) {
         this.field_219379_g.func_215515_b(entry.getLongKey(), j, j < i);
      }

   }

   protected void tick() {
      ++this.currentTime;
      ObjectIterator<Entry<ObjectSortedSet<Ticket<?>>>> objectiterator = this.tickets.long2ObjectEntrySet().fastIterator();

      while(objectiterator.hasNext()) {
         Entry<ObjectSortedSet<Ticket<?>>> entry = objectiterator.next();
         if (entry.getValue().removeIf((p_219370_1_) -> {
            return p_219370_1_.isExpired(this.currentTime);
         })) {
            this.field_219378_f.func_215491_b(entry.getLongKey(), this.func_219344_a(entry.getValue()), false);
         }

         if (entry.getValue().isEmpty()) {
            objectiterator.remove();
         }
      }

   }

   private int func_219344_a(ObjectSortedSet<Ticket<?>> p_219344_1_) {
      ObjectBidirectionalIterator<Ticket<?>> objectbidirectionaliterator = p_219344_1_.iterator();
      return objectbidirectionaliterator.hasNext() ? objectbidirectionaliterator.next().func_219477_b() : ChunkManager.field_219249_a + 1;
   }

   protected abstract boolean func_219371_a(long p_219371_1_);

   @Nullable
   protected abstract ChunkHolder func_219335_b(long p_219335_1_);

   @Nullable
   protected abstract ChunkHolder func_219372_a(long p_219372_1_, int p_219372_3_, @Nullable ChunkHolder p_219372_4_, int p_219372_5_);

   public boolean func_219353_a(ChunkManager p_219353_1_) {
      this.field_219381_i.func_215497_a();
      this.field_219382_j.func_215497_a();
      this.field_219379_g.func_215560_a();
      int i = Integer.MAX_VALUE - this.field_219378_f.func_215493_a(Integer.MAX_VALUE);
      boolean flag = i != 0;
      if (flag) {
         ;
      }

      if (!this.field_219383_k.isEmpty()) {
         this.field_219383_k.forEach((p_219343_1_) -> {
            p_219343_1_.func_219291_a(p_219353_1_);
         });
         this.field_219383_k.clear();
         return true;
      } else {
         if (!this.field_219387_o.isEmpty()) {
            LongIterator longiterator = this.field_219387_o.iterator();

            while(longiterator.hasNext()) {
               long j = longiterator.nextLong();
               if (this.getTickets(j).stream().anyMatch((p_219369_0_) -> {
                  return p_219369_0_.func_219479_a() == TicketType.field_219490_c;
               })) {
                  ChunkHolder chunkholder = p_219353_1_.func_219220_a(j);
                  if (chunkholder == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> completablefuture = chunkholder.func_219297_b();
                  completablefuture.thenAccept((p_219363_3_) -> {
                     this.field_219388_p.execute(() -> {
                        this.field_219386_n.enqueue(ChunkTaskPriorityQueueSorter.func_219073_a(() -> {
                        }, j, false));
                     });
                  });
               }
            }

            this.field_219387_o.clear();
         }

         return flag;
      }
   }

   private void register(long chunkPosIn, Ticket<?> ticketIn) {
      ObjectSortedSet<Ticket<?>> objectsortedset = this.getTickets(chunkPosIn);
      ObjectBidirectionalIterator<Ticket<?>> objectbidirectionaliterator = objectsortedset.iterator();
      int i;
      if (objectbidirectionaliterator.hasNext()) {
         i = objectbidirectionaliterator.next().func_219477_b();
      } else {
         i = ChunkManager.field_219249_a + 1;
      }

      if (objectsortedset.add(ticketIn)) {
         ;
      }

      if (ticketIn.func_219477_b() < i) {
         this.field_219378_f.func_215491_b(chunkPosIn, ticketIn.func_219477_b(), true);
      }

   }

   private void release(long p_219349_1_, Ticket<?> p_219349_3_) {
      ObjectSortedSet<Ticket<?>> objectsortedset = this.getTickets(p_219349_1_);
      if (objectsortedset.remove(p_219349_3_)) {
         ;
      }

      if (objectsortedset.isEmpty()) {
         this.tickets.remove(p_219349_1_);
      }

      this.field_219378_f.func_215491_b(p_219349_1_, this.func_219344_a(objectsortedset), false);
   }

   public <T> void func_219356_a(TicketType<T> p_219356_1_, ChunkPos p_219356_2_, int p_219356_3_, T p_219356_4_) {
      this.register(p_219356_2_.asLong(), new Ticket<>(p_219356_1_, p_219356_3_, p_219356_4_, this.currentTime));
   }

   public <T> void func_219345_b(TicketType<T> p_219345_1_, ChunkPos p_219345_2_, int p_219345_3_, T p_219345_4_) {
      Ticket<T> ticket = new Ticket<>(p_219345_1_, p_219345_3_, p_219345_4_, this.currentTime);
      this.release(p_219345_2_.asLong(), ticket);
   }

   public <T> void func_219331_c(TicketType<T> p_219331_1_, ChunkPos p_219331_2_, int p_219331_3_, T p_219331_4_) {
      this.register(p_219331_2_.asLong(), new Ticket<>(p_219331_1_, 33 - p_219331_3_, p_219331_4_, this.currentTime));
   }

   public <T> void func_219362_d(TicketType<T> p_219362_1_, ChunkPos p_219362_2_, int p_219362_3_, T p_219362_4_) {
      Ticket<T> ticket = new Ticket<>(p_219362_1_, 33 - p_219362_3_, p_219362_4_, this.currentTime);
      this.release(p_219362_2_.asLong(), ticket);
   }

   private ObjectSortedSet<Ticket<?>> getTickets(long chunkPosIn) {
      return this.tickets.computeIfAbsent(chunkPosIn, (p_219365_0_) -> {
         return new ObjectAVLTreeSet<>();
      });
   }

   protected void forceChunk(ChunkPos p_219364_1_, boolean p_219364_2_) {
      Ticket<ChunkPos> ticket = new Ticket<>(TicketType.field_219491_d, 32, p_219364_1_, this.currentTime);
      if (p_219364_2_) {
         this.register(p_219364_1_.asLong(), ticket);
      } else {
         this.release(p_219364_1_.asLong(), ticket);
      }

   }

   private int func_219333_e() {
      return 16 - this.field_219380_h;
   }

   public void func_219341_a(SectionPos p_219341_1_, ServerPlayerEntity p_219341_2_) {
      long i = p_219341_1_.asChunkPos().asLong();
      p_219341_2_.func_213850_a(p_219341_1_);
      p_219341_2_.connection.sendPacket(new SUpdateChunkPositionPacket(p_219341_1_.func_218149_a(), p_219341_1_.func_218148_c()));
      this.field_219376_d.computeIfAbsent(p_219341_1_.asLong(), (p_219339_0_) -> {
         return new ObjectOpenHashSet<>();
      }).add(p_219341_2_);
      this.field_219375_c.computeIfAbsent(i, (p_219361_0_) -> {
         return new ObjectOpenHashSet<>();
      }).add(p_219341_2_);
      this.field_219381_i.func_215491_b(i, 0, true);
      this.field_219382_j.func_215491_b(i, 0, true);
      this.field_219379_g.func_215515_b(p_219341_1_.asLong(), this.func_219333_e(), true);
   }

   public void func_219367_b(SectionPos p_219367_1_, ServerPlayerEntity p_219367_2_) {
      long i = p_219367_1_.asChunkPos().asLong();
      ObjectSet<ServerPlayerEntity> objectset = this.field_219376_d.get(p_219367_1_.asLong());
      if (objectset != null) {
         objectset.remove(p_219367_2_);
         if (objectset.isEmpty()) {
            this.field_219376_d.remove(p_219367_1_.asLong());
            this.field_219379_g.func_215515_b(p_219367_1_.asLong(), Integer.MAX_VALUE, false);
         }

         ObjectSet<ServerPlayerEntity> objectset1 = this.field_219375_c.get(i);
         objectset1.remove(p_219367_2_);
         if (objectset1.isEmpty()) {
            this.field_219375_c.remove(i);
            this.field_219381_i.func_215491_b(i, Integer.MAX_VALUE, false);
            this.field_219382_j.func_215491_b(i, Integer.MAX_VALUE, false);
         }

      }
   }

   protected void func_219354_b(int p_219354_1_) {
      this.field_219382_j.func_215508_a(p_219354_1_);
   }

   public int func_219358_b() {
      this.field_219381_i.func_215497_a();
      return this.field_219381_i.field_215498_a.size();
   }

   class ChunkTicketTracker extends ChunkDistanceGraph {
      public ChunkTicketTracker() {
         super(ChunkManager.field_219249_a + 2, 16, 256);
      }

      protected int func_215492_b(long p_215492_1_) {
         ObjectSortedSet<Ticket<?>> objectsortedset = TicketManager.this.tickets.get(p_215492_1_);
         if (objectsortedset == null) {
            return Integer.MAX_VALUE;
         } else {
            ObjectBidirectionalIterator<Ticket<?>> objectbidirectionaliterator = objectsortedset.iterator();
            return !objectbidirectionaliterator.hasNext() ? Integer.MAX_VALUE : objectbidirectionaliterator.next().func_219477_b();
         }
      }

      protected int func_215471_c(long p_215471_1_) {
         if (!TicketManager.this.func_219371_a(p_215471_1_)) {
            ChunkHolder chunkholder = TicketManager.this.func_219335_b(p_215471_1_);
            if (chunkholder != null) {
               return chunkholder.func_219299_i();
            }
         }

         return ChunkManager.field_219249_a + 1;
      }

      protected void func_215476_a(long p_215476_1_, int p_215476_3_) {
         ChunkHolder chunkholder = TicketManager.this.func_219335_b(p_215476_1_);
         int i = chunkholder == null ? ChunkManager.field_219249_a + 1 : chunkholder.func_219299_i();
         if (i != p_215476_3_) {
            chunkholder = TicketManager.this.func_219372_a(p_215476_1_, p_215476_3_, chunkholder, i);
            if (chunkholder != null) {
               TicketManager.this.field_219383_k.add(chunkholder);
            }

         }
      }

      public int func_215493_a(int p_215493_1_) {
         return this.func_215483_b(p_215493_1_);
      }
   }

   class PlayerChunkTracker extends ChunkDistanceGraph {
      protected final Long2ByteMap field_215498_a = new Long2ByteOpenHashMap();
      protected final int field_215499_b;

      protected PlayerChunkTracker(int p_i50684_2_) {
         super(p_i50684_2_ + 2, 16, 256);
         this.field_215499_b = p_i50684_2_;
         this.field_215498_a.defaultReturnValue((byte)(p_i50684_2_ + 2));
      }

      protected int func_215471_c(long p_215471_1_) {
         return this.field_215498_a.get(p_215471_1_);
      }

      protected void func_215476_a(long p_215476_1_, int p_215476_3_) {
         byte b0;
         if (p_215476_3_ > this.field_215499_b) {
            b0 = this.field_215498_a.remove(p_215476_1_);
         } else {
            b0 = this.field_215498_a.put(p_215476_1_, (byte)p_215476_3_);
         }

         this.func_215495_a(p_215476_1_, b0, p_215476_3_);
      }

      protected void func_215495_a(long p_215495_1_, int p_215495_3_, int p_215495_4_) {
      }

      protected int func_215492_b(long p_215492_1_) {
         return this.func_215496_d(p_215492_1_) ? 0 : Integer.MAX_VALUE;
      }

      private boolean func_215496_d(long p_215496_1_) {
         ObjectSet<ServerPlayerEntity> objectset = TicketManager.this.field_219375_c.get(p_215496_1_);
         return objectset != null && !objectset.isEmpty();
      }

      public void func_215497_a() {
         this.func_215483_b(Integer.MAX_VALUE);
      }
   }

   class PlayerSectionTracker extends SectionDistanceGraph {
      protected final Long2ByteMap field_215561_a = new Long2ByteOpenHashMap();

      protected PlayerSectionTracker() {
         super(18, 16, 256);
         this.field_215561_a.defaultReturnValue((byte)18);
      }

      protected int func_215471_c(long p_215471_1_) {
         return this.field_215561_a.get(p_215471_1_);
      }

      protected void func_215476_a(long p_215476_1_, int p_215476_3_) {
         if (p_215476_3_ > 16) {
            this.field_215561_a.remove(p_215476_1_);
         } else {
            this.field_215561_a.put(p_215476_1_, (byte)p_215476_3_);
         }

      }

      protected int func_215516_b(long p_215516_1_) {
         return this.func_215559_d(p_215516_1_) ? TicketManager.this.func_219333_e() : Integer.MAX_VALUE;
      }

      private boolean func_215559_d(long p_215559_1_) {
         ObjectSet<ServerPlayerEntity> objectset = TicketManager.this.field_219376_d.get(p_215559_1_);
         return objectset != null && !objectset.isEmpty();
      }

      public void func_215560_a() {
         this.func_215483_b(Integer.MAX_VALUE);
      }
   }

   class PlayerTicketTracker extends TicketManager.PlayerChunkTracker {
      private int field_215512_e;
      private final Long2IntMap field_215513_f = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet field_215514_g = new LongOpenHashSet();

      protected PlayerTicketTracker(int p_i50682_2_) {
         super(p_i50682_2_);
         this.field_215512_e = 0;
         this.field_215513_f.defaultReturnValue(p_i50682_2_ + 2);
      }

      protected void func_215495_a(long p_215495_1_, int p_215495_3_, int p_215495_4_) {
         this.field_215514_g.add(p_215495_1_);
      }

      public void func_215508_a(int p_215508_1_) {
         for(it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry entry : this.field_215498_a.long2ByteEntrySet()) {
            byte b0 = entry.getByteValue();
            long i = entry.getLongKey();
            this.func_215504_a(i, b0, this.func_215505_c(b0), b0 <= p_215508_1_ - 2);
         }

         this.field_215512_e = p_215508_1_;
      }

      private void func_215504_a(long p_215504_1_, int p_215504_3_, boolean p_215504_4_, boolean p_215504_5_) {
         if (p_215504_4_ != p_215504_5_) {
            Ticket<?> ticket = new Ticket<>(TicketType.field_219490_c, TicketManager.field_219374_b, new ChunkPos(p_215504_1_), TicketManager.this.currentTime);
            if (p_215504_5_) {
               TicketManager.this.field_219385_m.enqueue(ChunkTaskPriorityQueueSorter.func_219069_a(() -> {
                  TicketManager.this.field_219388_p.execute(() -> {
                     TicketManager.this.register(p_215504_1_, ticket);
                     TicketManager.this.field_219387_o.add(p_215504_1_);
                  });
               }, p_215504_1_, () -> {
                  return p_215504_3_;
               }));
            } else {
               TicketManager.this.field_219386_n.enqueue(ChunkTaskPriorityQueueSorter.func_219073_a(() -> {
                  TicketManager.this.field_219388_p.execute(() -> {
                     TicketManager.this.release(p_215504_1_, ticket);
                  });
               }, p_215504_1_, true));
            }
         }

      }

      public void func_215497_a() {
         super.func_215497_a();
         if (!this.field_215514_g.isEmpty()) {
            LongIterator longiterator = this.field_215514_g.iterator();

            while(longiterator.hasNext()) {
               long i = longiterator.nextLong();
               int j = this.field_215513_f.get(i);
               int k = this.func_215471_c(i);
               if (j != k) {
                  TicketManager.this.field_219384_l.func_219066_a(new ChunkPos(i), () -> {
                     return this.field_215513_f.get(i);
                  }, k, (p_215506_3_) -> {
                     this.field_215513_f.put(i, p_215506_3_);
                  });
                  this.func_215504_a(i, k, this.func_215505_c(j), this.func_215505_c(k));
               }
            }

            this.field_215514_g.clear();
         }

      }

      private boolean func_215505_c(int p_215505_1_) {
         return p_215505_1_ <= this.field_215512_e - 2;
      }
   }
}