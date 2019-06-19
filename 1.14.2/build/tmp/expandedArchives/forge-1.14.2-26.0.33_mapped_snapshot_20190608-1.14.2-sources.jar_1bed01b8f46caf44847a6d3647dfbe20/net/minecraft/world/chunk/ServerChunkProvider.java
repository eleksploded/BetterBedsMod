package net.minecraft.world.chunk;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.listener.IChunkStatusListener;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ServerChunkProvider extends AbstractChunkProvider {
   private static final int field_217238_b = (int)Math.pow(17.0D, 2.0D);
   private static final List<ChunkStatus> field_217239_c = ChunkStatus.getAll();
   private final TicketManager ticketManager;
   public final ChunkGenerator<?> generator;
   public final ServerWorld world;
   private final Thread mainThread;
   private final ServerWorldLightManager lightManager;
   private final ServerChunkProvider.ChunkExecutor executor;
   public final ChunkManager field_217237_a;
   private final DimensionSavedDataManager field_217244_j;
   private long field_217245_k;
   private boolean spawnHostiles = true;
   private boolean spawnPassives = true;
   private final long[] recentPositions = new long[4];
   private final ChunkStatus[] recentStatuses = new ChunkStatus[4];
   private final IChunk[] recentChunks = new IChunk[4];

   public ServerChunkProvider(ServerWorld p_i50705_1_, File p_i50705_2_, DataFixer p_i50705_3_, TemplateManager p_i50705_4_, Executor p_i50705_5_, ChunkGenerator<?> p_i50705_6_, int p_i50705_7_, int p_i50705_8_, IChunkStatusListener p_i50705_9_, Supplier<DimensionSavedDataManager> p_i50705_10_) {
      this.world = p_i50705_1_;
      this.executor = new ServerChunkProvider.ChunkExecutor(p_i50705_1_);
      this.generator = p_i50705_6_;
      this.mainThread = Thread.currentThread();
      File file1 = p_i50705_1_.getDimension().getType().getDirectory(p_i50705_2_);
      File file2 = new File(file1, "data");
      file2.mkdirs();
      this.field_217244_j = new DimensionSavedDataManager(file2, p_i50705_3_);
      this.field_217237_a = new ChunkManager(p_i50705_1_, p_i50705_2_, p_i50705_3_, p_i50705_4_, p_i50705_5_, this.executor, this, this.getChunkGenerator(), p_i50705_9_, p_i50705_10_, p_i50705_7_, p_i50705_8_);
      this.lightManager = this.field_217237_a.func_219207_a();
      this.ticketManager = this.field_217237_a.func_219246_e();
      this.invalidateCaches();
   }

   public ServerWorldLightManager getLightManager() {
      return this.lightManager;
   }

   @Nullable
   private ChunkHolder func_217213_a(long p_217213_1_) {
      return this.field_217237_a.func_219219_b(p_217213_1_);
   }

   public int func_217229_b() {
      return this.field_217237_a.func_219174_c();
   }

   @Nullable
   public IChunk getChunk(int p_212849_1_, int p_212849_2_, ChunkStatus p_212849_3_, boolean p_212849_4_) {
      if (Thread.currentThread() != this.mainThread) {
         return CompletableFuture.supplyAsync(() -> {
            return this.getChunk(p_212849_1_, p_212849_2_, p_212849_3_, p_212849_4_);
         }, this.executor).join();
      } else {
         long i = ChunkPos.asLong(p_212849_1_, p_212849_2_);

         for(int j = 0; j < 4; ++j) {
            if (i == this.recentPositions[j] && p_212849_3_ == this.recentStatuses[j]) {
               IChunk ichunk = this.recentChunks[j];
               if (ichunk != null || !p_212849_4_) {
                  return ichunk;
               }
            }
         }

         CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture = this.func_217233_c(p_212849_1_, p_212849_2_, p_212849_3_, p_212849_4_);
         this.executor.driveUntil(completablefuture::isDone);
         IChunk ichunk1 = completablefuture.join().map((p_222874_0_) -> {
            return p_222874_0_;
         }, (p_222870_1_) -> {
            if (p_212849_4_) {
               throw new IllegalStateException("Chunk not there when requested: " + p_222870_1_);
            } else {
               return null;
            }
         });

         for(int k = 3; k > 0; --k) {
            this.recentPositions[k] = this.recentPositions[k - 1];
            this.recentStatuses[k] = this.recentStatuses[k - 1];
            this.recentChunks[k] = this.recentChunks[k - 1];
         }

         this.recentPositions[0] = i;
         this.recentStatuses[0] = p_212849_3_;
         this.recentChunks[0] = ichunk1;
         return ichunk1;
      }
   }

   private void invalidateCaches() {
      Arrays.fill(this.recentPositions, ChunkPos.field_222244_a);
      Arrays.fill(this.recentStatuses, (Object)null);
      Arrays.fill(this.recentChunks, (Object)null);
   }

   @OnlyIn(Dist.CLIENT)
   public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_217232_b(int p_217232_1_, int p_217232_2_, ChunkStatus p_217232_3_, boolean p_217232_4_) {
      boolean flag = Thread.currentThread() == this.mainThread;
      CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> completablefuture;
      if (flag) {
         completablefuture = this.func_217233_c(p_217232_1_, p_217232_2_, p_217232_3_, p_217232_4_);
         this.executor.driveUntil(completablefuture::isDone);
      } else {
         completablefuture = CompletableFuture.supplyAsync(() -> {
            return this.func_217233_c(p_217232_1_, p_217232_2_, p_217232_3_, p_217232_4_);
         }, this.executor).thenCompose((p_217211_0_) -> {
            return p_217211_0_;
         });
      }

      return completablefuture;
   }

   private CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_217233_c(int p_217233_1_, int p_217233_2_, ChunkStatus p_217233_3_, boolean p_217233_4_) {
      ChunkPos chunkpos = new ChunkPos(p_217233_1_, p_217233_2_);
      long i = chunkpos.asLong();
      int j = 33 + ChunkStatus.func_222599_a(p_217233_3_);
      ChunkHolder chunkholder = this.func_217213_a(i);
      if (p_217233_4_) {
         this.ticketManager.func_219356_a(TicketType.field_219494_g, chunkpos, j, chunkpos);
         if (this.func_217224_a(chunkholder, j)) {
            IProfiler iprofiler = this.world.getProfiler();
            iprofiler.startSection("chunkLoad");
            this.func_217235_l();
            chunkholder = this.func_217213_a(i);
            iprofiler.endSection();
            if (this.func_217224_a(chunkholder, j)) {
               throw new IllegalStateException("No chunk holder after ticket has been added");
            }
         }
      }

      return this.func_217224_a(chunkholder, j) ? ChunkHolder.field_219307_b : chunkholder.func_219276_a(p_217233_3_, this.field_217237_a);
   }

   private boolean func_217224_a(@Nullable ChunkHolder p_217224_1_, int p_217224_2_) {
      return p_217224_1_ == null || p_217224_1_.func_219299_i() > p_217224_2_;
   }

   /**
    * Checks to see if a chunk exists at x, z
    */
   public boolean chunkExists(int x, int z) {
      ChunkHolder chunkholder = this.func_217213_a((new ChunkPos(x, z)).asLong());
      int i = 33 + ChunkStatus.func_222599_a(ChunkStatus.FULL);
      return !this.func_217224_a(chunkholder, i);
   }

   public IBlockReader func_217202_b(int p_217202_1_, int p_217202_2_) {
      long i = ChunkPos.asLong(p_217202_1_, p_217202_2_);
      ChunkHolder chunkholder = this.func_217213_a(i);
      if (chunkholder == null) {
         return null;
      } else {
         int j = field_217239_c.size() - 1;

         while(true) {
            ChunkStatus chunkstatus = field_217239_c.get(j);
            Optional<IChunk> optional = chunkholder.func_219301_a(chunkstatus).getNow(ChunkHolder.field_219306_a).left();
            if (optional.isPresent()) {
               return optional.get();
            }

            if (chunkstatus == ChunkStatus.LIGHT.func_222593_e()) {
               return null;
            }

            --j;
         }
      }
   }

   public World func_212864_k_() {
      return this.world;
   }

   public boolean func_217234_d() {
      return this.executor.driveOne();
   }

   private boolean func_217235_l() {
      boolean flag = this.ticketManager.func_219353_a(this.field_217237_a);
      boolean flag1 = this.field_217237_a.func_219245_b();
      if (!flag && !flag1) {
         return false;
      } else {
         this.invalidateCaches();
         return true;
      }
   }

   public boolean func_217204_a(Entity p_217204_1_) {
      long i = ChunkPos.asLong(MathHelper.floor(p_217204_1_.posX) >> 4, MathHelper.floor(p_217204_1_.posZ) >> 4);
      return this.func_222872_a(i, ChunkHolder::func_219297_b);
   }

   public boolean func_222865_a(ChunkPos p_222865_1_) {
      return this.func_222872_a(p_222865_1_.asLong(), ChunkHolder::func_219297_b);
   }

   public boolean canTick(BlockPos p_222866_1_) {
      long i = ChunkPos.asLong(p_222866_1_.getX() >> 4, p_222866_1_.getZ() >> 4);
      return this.func_222872_a(i, ChunkHolder::func_219296_a);
   }

   private boolean func_222872_a(long p_222872_1_, Function<ChunkHolder, CompletableFuture<Either<Chunk, ChunkHolder.IChunkLoadingError>>> p_222872_3_) {
      ChunkHolder chunkholder = this.func_217213_a(p_222872_1_);
      if (chunkholder == null) {
         return false;
      } else {
         Either<Chunk, ChunkHolder.IChunkLoadingError> either = p_222872_3_.apply(chunkholder).getNow(ChunkHolder.field_219308_c);
         return either.left().isPresent();
      }
   }

   public void func_217210_a(boolean p_217210_1_) {
      this.func_217235_l();
      this.field_217237_a.func_219177_a(p_217210_1_);
   }

   public void close() throws IOException {
      this.func_217210_a(true);
      this.lightManager.close();
      this.field_217237_a.close();
   }

   public void tick(BooleanSupplier hasTimeLeft) {
      this.world.getProfiler().startSection("purge");
      this.ticketManager.tick();
      this.func_217235_l();
      this.world.getProfiler().endStartSection("chunks");
      this.func_217220_m();
      this.world.getProfiler().endStartSection("unload");
      this.field_217237_a.func_219204_a(hasTimeLeft);
      this.world.getProfiler().endSection();
      this.invalidateCaches();
   }

   private void func_217220_m() {
      long i = this.world.getGameTime();
      long j = i - this.field_217245_k;
      this.field_217245_k = i;
      WorldInfo worldinfo = this.world.getWorldInfo();
      boolean flag = worldinfo.getGenerator() == WorldType.DEBUG_ALL_BLOCK_STATES;
      boolean flag1 = this.world.getGameRules().getBoolean("doMobSpawning");
      if (!flag) {
         this.world.getProfiler().startSection("pollingChunks");
         int k = this.world.getGameRules().getInt("randomTickSpeed");
         BlockPos blockpos = this.world.getSpawnPoint();
         boolean flag2 = worldinfo.getGameTime() % 400L == 0L;
         this.world.getProfiler().startSection("naturalSpawnCount");
         int l = this.ticketManager.func_219358_b();
         EntityClassification[] aentityclassification = EntityClassification.values();
         Object2IntMap<EntityClassification> object2intmap = this.world.countEntities();
         this.world.getProfiler().endSection();
         ObjectBidirectionalIterator<Entry<ChunkHolder>> objectbidirectionaliterator = this.field_217237_a.func_219197_f();

         while(objectbidirectionaliterator.hasNext()) {
            Entry<ChunkHolder> entry = objectbidirectionaliterator.next();
            ChunkHolder chunkholder = entry.getValue();
            Optional<Chunk> optional = chunkholder.func_219297_b().getNow(ChunkHolder.field_219308_c).left();
            if (optional.isPresent()) {
               Chunk chunk = optional.get();
               this.world.getProfiler().startSection("broadcast");
               chunkholder.func_219274_a(chunk);
               this.world.getProfiler().endSection();
               ChunkPos chunkpos = chunkholder.func_219277_h();
               if (!this.field_217237_a.func_219243_d(chunkpos)) {
                  chunk.setInhabitedTime(chunk.getInhabitedTime() + j);
                  if (flag1 && (this.spawnHostiles || this.spawnPassives) && this.world.getWorldBorder().contains(chunk.getPos())) {
                     this.world.getProfiler().startSection("spawner");

                     for(EntityClassification entityclassification : aentityclassification) {
                        if (entityclassification != EntityClassification.MISC && (!entityclassification.getPeacefulCreature() || this.spawnPassives) && (entityclassification.getPeacefulCreature() || this.spawnHostiles) && (!entityclassification.getAnimal() || flag2)) {
                           int i1 = entityclassification.getMaxNumberOfCreature() * l / field_217238_b;
                           if (object2intmap.getInt(entityclassification) <= i1) {
                              WorldEntitySpawner.performNaturalSpawning(entityclassification, this.world, chunk, blockpos);
                           }
                        }
                     }

                     this.world.getProfiler().endSection();
                  }

                  this.world.func_217441_a(chunk, k);
               }
            }
         }

         this.world.getProfiler().startSection("customSpawners");
         if (flag1) {
            this.generator.spawnMobs(this.world, this.spawnHostiles, this.spawnPassives);
         }

         this.world.getProfiler().endSection();
         this.world.getProfiler().endSection();
      }

      this.field_217237_a.tickEntityTracker();
   }

   /**
    * Converts the instance data to a readable string.
    */
   public String makeString() {
      return "ServerChunkCache: " + this.getLoadedChunkCount();
   }

   public ChunkGenerator<?> getChunkGenerator() {
      return this.generator;
   }

   public int getLoadedChunkCount() {
      return this.field_217237_a.func_219194_d();
   }

   public void func_217217_a(BlockPos p_217217_1_) {
      int i = p_217217_1_.getX() >> 4;
      int j = p_217217_1_.getZ() >> 4;
      ChunkHolder chunkholder = this.func_217213_a(ChunkPos.asLong(i, j));
      if (chunkholder != null) {
         chunkholder.func_219279_a(p_217217_1_.getX() & 15, p_217217_1_.getY(), p_217217_1_.getZ() & 15);
      }

   }

   public void func_217201_a(LightType p_217201_1_, SectionPos p_217201_2_) {
      this.executor.execute(() -> {
         ChunkHolder chunkholder = this.func_217213_a(p_217201_2_.asChunkPos().asLong());
         if (chunkholder != null) {
            chunkholder.func_219280_a(p_217201_1_, p_217201_2_.func_218163_b());
         }

      });
   }

   public <T> void func_217228_a(TicketType<T> p_217228_1_, ChunkPos p_217228_2_, int p_217228_3_, T p_217228_4_) {
      this.ticketManager.func_219331_c(p_217228_1_, p_217228_2_, p_217228_3_, p_217228_4_);
   }

   public <T> void func_217222_b(TicketType<T> p_217222_1_, ChunkPos p_217222_2_, int p_217222_3_, T p_217222_4_) {
      this.ticketManager.func_219362_d(p_217222_1_, p_217222_2_, p_217222_3_, p_217222_4_);
   }

   public void forceChunk(ChunkPos p_217206_1_, boolean p_217206_2_) {
      this.ticketManager.forceChunk(p_217206_1_, p_217206_2_);
   }

   public void func_217221_a(ServerPlayerEntity p_217221_1_) {
      this.field_217237_a.func_219183_a(p_217221_1_);
   }

   public void func_217226_b(Entity p_217226_1_) {
      this.field_217237_a.untrack(p_217226_1_);
   }

   public void func_217230_c(Entity p_217230_1_) {
      this.field_217237_a.track(p_217230_1_);
   }

   public void func_217216_a(Entity p_217216_1_, IPacket<?> p_217216_2_) {
      this.field_217237_a.func_219225_b(p_217216_1_, p_217216_2_);
   }

   public void func_217218_b(Entity p_217218_1_, IPacket<?> p_217218_2_) {
      this.field_217237_a.func_219222_a(p_217218_1_, p_217218_2_);
   }

   public void func_217219_c(int p_217219_1_, int p_217219_2_) {
      this.field_217237_a.setViewDistance(p_217219_1_, p_217219_2_);
   }

   public void func_217203_a(boolean p_217203_1_, boolean p_217203_2_) {
      this.spawnHostiles = p_217203_1_;
      this.spawnPassives = p_217203_2_;
   }

   @OnlyIn(Dist.CLIENT)
   public String func_217208_a(ChunkPos p_217208_1_) {
      return this.field_217237_a.func_219170_a(p_217208_1_);
   }

   public DimensionSavedDataManager func_217227_h() {
      return this.field_217244_j;
   }

   public PointOfInterestManager func_217231_i() {
      return this.field_217237_a.func_219189_h();
   }

   final class ChunkExecutor extends ThreadTaskExecutor<Runnable> {
      private ChunkExecutor(World p_i50985_2_) {
         super("Chunk source main thread executor for " + Registry.field_212622_k.getKey(p_i50985_2_.getDimension().getType()));
      }

      protected Runnable wrapTask(Runnable p_212875_1_) {
         return p_212875_1_;
      }

      protected boolean canRun(Runnable p_212874_1_) {
         return true;
      }

      protected boolean shouldDeferTasks() {
         return true;
      }

      protected Thread getExecutionThread() {
         return ServerChunkProvider.this.mainThread;
      }

      protected boolean driveOne() {
         if (ServerChunkProvider.this.func_217235_l()) {
            return true;
         } else {
            ServerChunkProvider.this.lightManager.func_215588_z_();
            return super.driveOne();
         }
      }
   }
}