package net.minecraft.world.chunk;

import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeBlockPacket;
import net.minecraft.network.play.server.SChunkDataPacket;
import net.minecraft.network.play.server.SMultiBlockChangePacket;
import net.minecraft.network.play.server.SUpdateLightPacket;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChunkHolder {
   public static final Either<IChunk, ChunkHolder.IChunkLoadingError> field_219306_a = Either.right(ChunkHolder.IChunkLoadingError.field_219055_b);
   public static final CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> field_219307_b = CompletableFuture.completedFuture(field_219306_a);
   public static final Either<Chunk, ChunkHolder.IChunkLoadingError> field_219308_c = Either.right(ChunkHolder.IChunkLoadingError.field_219055_b);
   private static final CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> field_219309_d = CompletableFuture.completedFuture(field_219308_c);
   private static final List<ChunkStatus> field_219310_e = ChunkStatus.getAll();
   private static final ChunkHolder.LocationType[] field_219311_f = ChunkHolder.LocationType.values();
   private final AtomicReferenceArray<CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> field_219312_g = new AtomicReferenceArray<>(field_219310_e.size());
   private volatile CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> field_222983_h = field_219309_d;
   private volatile CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> field_219313_h = field_219309_d;
   private volatile CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> field_219314_i = field_219309_d;
   private CompletableFuture<IChunk> field_219315_j = CompletableFuture.completedFuture((IChunk)null);
   private int field_219316_k;
   private int field_219317_l;
   private int field_219318_m;
   private final ChunkPos pos;
   private short[] field_219320_o = new short[64];
   private int field_219321_p;
   private int field_219322_q;
   private int field_219323_r;
   private int field_219324_s;
   private int field_219325_t;
   private final WorldLightManager lightManager;
   private final ChunkHolder.IListener field_219327_v;
   private final ChunkHolder.IPlayerProvider field_219328_w;
   private boolean field_219329_x;

   public ChunkHolder(ChunkPos p_i50716_1_, int p_i50716_2_, WorldLightManager p_i50716_3_, ChunkHolder.IListener p_i50716_4_, ChunkHolder.IPlayerProvider p_i50716_5_) {
      this.pos = p_i50716_1_;
      this.lightManager = p_i50716_3_;
      this.field_219327_v = p_i50716_4_;
      this.field_219328_w = p_i50716_5_;
      this.field_219316_k = ChunkManager.field_219249_a + 1;
      this.field_219317_l = this.field_219316_k;
      this.field_219318_m = this.field_219316_k;
      this.func_219292_a(p_i50716_2_);
   }

   public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_219301_a(ChunkStatus p_219301_1_) {
      CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.field_219312_g.get(p_219301_1_.func_222584_c());
      return completablefuture == null ? field_219307_b : completablefuture;
   }

   public CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> func_219296_a() {
      return this.field_219313_h;
   }

   public CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> func_219297_b() {
      return this.field_219314_i;
   }

   @Nullable
   public Chunk func_219298_c() {
      CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.func_219296_a();
      Either<Chunk, ChunkHolder.IChunkLoadingError> either = completablefuture.getNow((Either<Chunk, ChunkHolder.IChunkLoadingError>)null);
      return either == null ? null : either.left().orElse((Chunk)null);
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public ChunkStatus func_219285_d() {
      for(int i = field_219310_e.size() - 1; i >= 0; --i) {
         ChunkStatus chunkstatus = field_219310_e.get(i);
         CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.func_219301_a(chunkstatus);
         if (completablefuture.getNow(field_219306_a).left().isPresent()) {
            return chunkstatus;
         }
      }

      return null;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public IChunk func_219287_e() {
      for(int i = field_219310_e.size() - 1; i >= 0; --i) {
         ChunkStatus chunkstatus = field_219310_e.get(i);
         CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.func_219301_a(chunkstatus);
         if (!completablefuture.isCompletedExceptionally()) {
            Optional<IChunk> optional = completablefuture.getNow(field_219306_a).left();
            if (optional.isPresent()) {
               return optional.get();
            }
         }
      }

      return null;
   }

   public CompletableFuture<IChunk> func_219302_f() {
      return this.field_219315_j;
   }

   public void func_219279_a(int p_219279_1_, int p_219279_2_, int p_219279_3_) {
      Chunk chunk = this.func_219298_c();
      if (chunk != null) {
         this.field_219322_q |= 1 << (p_219279_2_ >> 4);
         { //Forge; Cache everything, so always run
            short short1 = (short)(p_219279_1_ << 12 | p_219279_3_ << 8 | p_219279_2_);

            for(int i = 0; i < this.field_219321_p; ++i) {
               if (this.field_219320_o[i] == short1) {
                  return;
               }
            }

            if (this.field_219321_p == this.field_219320_o.length)
               this.field_219320_o = java.util.Arrays.copyOf(this.field_219320_o, this.field_219320_o.length << 1);
            this.field_219320_o[this.field_219321_p++] = short1;
         }

      }
   }

   public void func_219280_a(LightType p_219280_1_, int p_219280_2_) {
      Chunk chunk = this.func_219298_c();
      if (chunk != null) {
         chunk.setModified(true);
         if (p_219280_1_ == LightType.SKY) {
            this.field_219325_t |= 1 << p_219280_2_ - -1;
         } else {
            this.field_219324_s |= 1 << p_219280_2_ - -1;
         }

      }
   }

   public void func_219274_a(Chunk p_219274_1_) {
      if (this.field_219321_p != 0 || this.field_219325_t != 0 || this.field_219324_s != 0) {
         World world = p_219274_1_.getWorld();
         if (this.field_219321_p == 64) {
            this.field_219323_r = -1;
         }

         if (this.field_219325_t != 0 || this.field_219324_s != 0) {
            this.func_219293_a(new SUpdateLightPacket(p_219274_1_.getPos(), this.lightManager, this.field_219325_t & ~this.field_219323_r, this.field_219324_s & ~this.field_219323_r), true);
            int i = this.field_219325_t & this.field_219323_r;
            int j = this.field_219324_s & this.field_219323_r;
            if (i != 0 || j != 0) {
               this.func_219293_a(new SUpdateLightPacket(p_219274_1_.getPos(), this.lightManager, i, j), false);
            }

            this.field_219325_t = 0;
            this.field_219324_s = 0;
            this.field_219323_r &= ~(this.field_219325_t & this.field_219324_s);
         }

         if (this.field_219321_p == 1) {
            int l = (this.field_219320_o[0] >> 12 & 15) + this.pos.x * 16;
            int j1 = this.field_219320_o[0] & 255;
            int k = (this.field_219320_o[0] >> 8 & 15) + this.pos.z * 16;
            BlockPos blockpos = new BlockPos(l, j1, k);
            this.func_219293_a(new SChangeBlockPacket(world, blockpos), false);
            if (world.getBlockState(blockpos).hasTileEntity()) {
               this.func_219305_a(world, blockpos);
            }
         } else if (this.field_219321_p >= net.minecraftforge.common.ForgeConfig.SERVER.clumpingThreshold.get()) {
            this.func_219293_a(new SChunkDataPacket(p_219274_1_, this.field_219322_q), false);
            //TODO: Fix Mojang's fuckup to modded by combining all TE data into the chunk data packet... seriously... packet size explosion!
         } else if (this.field_219321_p != 0) {
            this.func_219293_a(new SMultiBlockChangePacket(this.field_219321_p, this.field_219320_o, p_219274_1_), false);
        //} Keep this in the else until we figure out a fix for mojang's derpitude on the data packet so we don't double send crap.
        //{// Forge: Send only the tile entities that are updated, Adding this brace lets us keep the indent and the patch small
            for(int i1 = 0; i1 < this.field_219321_p; ++i1) {
               int k1 = (this.field_219320_o[i1] >> 12 & 15) + this.pos.x * 16;
               int l1 = this.field_219320_o[i1] & 255;
               int i2 = (this.field_219320_o[i1] >> 8 & 15) + this.pos.z * 16;
               BlockPos blockpos1 = new BlockPos(k1, l1, i2);
               if (world.getBlockState(blockpos1).hasTileEntity()) {
                  this.func_219305_a(world, blockpos1);
               }
            }
         }

         this.field_219321_p = 0;
         this.field_219322_q = 0;
      }
   }

   private void func_219305_a(World p_219305_1_, BlockPos p_219305_2_) {
      TileEntity tileentity = p_219305_1_.getTileEntity(p_219305_2_);
      if (tileentity != null) {
         SUpdateTileEntityPacket supdatetileentitypacket = tileentity.getUpdatePacket();
         if (supdatetileentitypacket != null) {
            this.func_219293_a(supdatetileentitypacket, false);
         }
      }

   }

   private void func_219293_a(IPacket<?> p_219293_1_, boolean p_219293_2_) {
      this.field_219328_w.func_219097_a(this.pos, p_219293_2_).forEach((p_219304_1_) -> {
         p_219304_1_.connection.sendPacket(p_219293_1_);
      });
   }

   public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_219276_a(ChunkStatus p_219276_1_, ChunkManager p_219276_2_) {
      int i = p_219276_1_.func_222584_c();
      CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.field_219312_g.get(i);
      if (completablefuture != null) {
         Either<IChunk, ChunkHolder.IChunkLoadingError> either = completablefuture.getNow((Either<IChunk, ChunkHolder.IChunkLoadingError>)null);
         if (either == null || either.left().isPresent()) {
            return completablefuture;
         }
      }

      if (func_219278_b(this.field_219317_l).isAtLeast(p_219276_1_)) {
         CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture1 = p_219276_2_.func_219244_a(this, p_219276_1_);
         this.func_219284_a(completablefuture1);
         this.field_219312_g.set(i, completablefuture1);
         return completablefuture1;
      } else {
         return completablefuture == null ? field_219307_b : completablefuture;
      }
   }

   private void func_219284_a(CompletableFuture<? extends Either<? extends IChunk, ChunkHolder.IChunkLoadingError>> p_219284_1_) {
      this.field_219315_j = this.field_219315_j.thenCombine(p_219284_1_, (p_219295_0_, p_219295_1_) -> {
         return p_219295_1_.map((p_219283_0_) -> {
            return p_219283_0_;
         }, (p_219288_1_) -> {
            return p_219295_0_;
         });
      });
   }

   @OnlyIn(Dist.CLIENT)
   public ChunkHolder.LocationType func_219300_g() {
      return func_219286_c(this.field_219317_l);
   }

   public ChunkPos func_219277_h() {
      return this.pos;
   }

   public int func_219299_i() {
      return this.field_219317_l;
   }

   public int func_219281_j() {
      return this.field_219318_m;
   }

   private void func_219275_d(int p_219275_1_) {
      this.field_219318_m = p_219275_1_;
   }

   public void func_219292_a(int p_219292_1_) {
      this.field_219317_l = p_219292_1_;
   }

   protected void func_219291_a(ChunkManager p_219291_1_) {
      ChunkStatus chunkstatus = func_219278_b(this.field_219316_k);
      ChunkStatus chunkstatus1 = func_219278_b(this.field_219317_l);
      boolean flag = this.field_219316_k <= ChunkManager.field_219249_a;
      boolean flag1 = this.field_219317_l <= ChunkManager.field_219249_a;
      ChunkHolder.LocationType chunkholder$locationtype = func_219286_c(this.field_219316_k);
      ChunkHolder.LocationType chunkholder$locationtype1 = func_219286_c(this.field_219317_l);
      if (flag) {
         Either<IChunk, ChunkHolder.IChunkLoadingError> either = Either.right(new ChunkHolder.IChunkLoadingError() {
            public String toString() {
               return "Unloaded ticket level " + ChunkHolder.this.pos.toString();
            }
         });

         for(int i = flag1 ? chunkstatus1.func_222584_c() + 1 : 0; i <= chunkstatus.func_222584_c(); ++i) {
            CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.field_219312_g.get(i);
            if (completablefuture != null) {
               completablefuture.complete(either);
            } else {
               this.field_219312_g.set(i, CompletableFuture.completedFuture(either));
            }
         }
      }

      boolean flag5 = chunkholder$locationtype.func_219065_a(ChunkHolder.LocationType.BORDER);
      boolean flag6 = chunkholder$locationtype1.func_219065_a(ChunkHolder.LocationType.BORDER);
      this.field_219329_x |= flag6;
      if (!flag5 && flag6) {
         this.field_222983_h = p_219291_1_.func_222961_b(this);
         this.func_219284_a(this.field_222983_h);
      }

      if (flag5 && !flag6) {
         CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>> completablefuture1 = this.field_222983_h;
         this.field_222983_h = field_219309_d;
         this.func_219284_a(completablefuture1.thenApply((p_222982_1_) -> {
            return p_222982_1_.ifLeft(p_219291_1_::func_222973_a);
         }));
      }

      boolean flag7 = chunkholder$locationtype.func_219065_a(ChunkHolder.LocationType.TICKING);
      boolean flag2 = chunkholder$locationtype1.func_219065_a(ChunkHolder.LocationType.TICKING);
      if (!flag7 && flag2) {
         this.field_219313_h = p_219291_1_.func_219179_a(this);
         this.func_219284_a(this.field_219313_h);
      }

      if (flag7 && !flag2) {
         this.field_219313_h.complete(field_219308_c);
         this.field_219313_h = field_219309_d;
      }

      boolean flag3 = chunkholder$locationtype.func_219065_a(ChunkHolder.LocationType.ENTITY_TICKING);
      boolean flag4 = chunkholder$locationtype1.func_219065_a(ChunkHolder.LocationType.ENTITY_TICKING);
      if (!flag3 && flag4) {
         if (this.field_219314_i != field_219309_d) {
            throw new IllegalStateException();
         }

         this.field_219314_i = p_219291_1_.func_219188_b(this.pos);
         this.func_219284_a(this.field_219314_i);
      }

      if (flag3 && !flag4) {
         this.field_219314_i.complete(field_219308_c);
         this.field_219314_i = field_219309_d;
      }

      this.field_219327_v.func_219066_a(this.pos, this::func_219281_j, this.field_219317_l, this::func_219275_d);
      this.field_219316_k = this.field_219317_l;
   }

   public static ChunkStatus func_219278_b(int p_219278_0_) {
      return p_219278_0_ < 33 ? ChunkStatus.FULL : ChunkStatus.func_222581_a(p_219278_0_ - 33);
   }

   public static ChunkHolder.LocationType func_219286_c(int p_219286_0_) {
      return field_219311_f[MathHelper.clamp(33 - p_219286_0_ + 1, 0, field_219311_f.length - 1)];
   }

   public boolean func_219289_k() {
      return this.field_219329_x;
   }

   public void func_219303_l() {
      this.field_219329_x = func_219286_c(this.field_219317_l).func_219065_a(ChunkHolder.LocationType.BORDER);
   }

   public void func_219294_a(ChunkPrimerWrapper p_219294_1_) {
      for(int i = 0; i < this.field_219312_g.length(); ++i) {
         CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.field_219312_g.get(i);
         if (completablefuture != null) {
            Optional<IChunk> optional = completablefuture.getNow(field_219306_a).left();
            if (optional.isPresent() && optional.get() instanceof ChunkPrimer) {
               this.field_219312_g.set(i, CompletableFuture.completedFuture(Either.left(p_219294_1_)));
            }
         }
      }

      this.func_219284_a(CompletableFuture.completedFuture(Either.left(p_219294_1_.func_217336_u())));
   }

   public interface IChunkLoadingError {
      ChunkHolder.IChunkLoadingError field_219055_b = new ChunkHolder.IChunkLoadingError() {
         public String toString() {
            return "UNLOADED";
         }
      };
   }

   public interface IListener {
      void func_219066_a(ChunkPos p_219066_1_, IntSupplier p_219066_2_, int p_219066_3_, IntConsumer p_219066_4_);
   }

   public interface IPlayerProvider {
      Stream<ServerPlayerEntity> func_219097_a(ChunkPos p_219097_1_, boolean p_219097_2_);
   }

   public static enum LocationType {
      INACCESSIBLE,
      BORDER,
      TICKING,
      ENTITY_TICKING;

      public boolean func_219065_a(ChunkHolder.LocationType p_219065_1_) {
         return this.ordinal() >= p_219065_1_.ordinal();
      }
   }
}