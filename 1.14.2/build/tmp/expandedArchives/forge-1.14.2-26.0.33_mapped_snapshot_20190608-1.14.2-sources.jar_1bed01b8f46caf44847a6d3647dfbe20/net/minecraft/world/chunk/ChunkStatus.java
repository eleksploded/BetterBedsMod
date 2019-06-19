package net.minecraft.world.chunk;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ChunkStatus extends net.minecraftforge.registries.ForgeRegistryEntry<ChunkStatus> {
   private static final EnumSet<Heightmap.Type> field_222618_n = EnumSet.of(Heightmap.Type.OCEAN_FLOOR_WG, Heightmap.Type.WORLD_SURFACE_WG);
   private static final EnumSet<Heightmap.Type> field_222619_o = EnumSet.of(Heightmap.Type.OCEAN_FLOOR, Heightmap.Type.WORLD_SURFACE, Heightmap.Type.MOTION_BLOCKING, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES);
   private static final ChunkStatus.ILoadingWorker NOOP_LOADING_WORKER = (p_222588_0_, p_222588_1_, p_222588_2_, p_222588_3_, p_222588_4_, p_222588_5_) -> {
      if (p_222588_5_ instanceof ChunkPrimer && !p_222588_5_.getStatus().isAtLeast(p_222588_0_)) {
         ((ChunkPrimer)p_222588_5_).setStatus(p_222588_0_);
      }

      return CompletableFuture.completedFuture(Either.left(p_222588_5_));
   };
   public static final ChunkStatus EMPTY = func_223203_a("empty", (ChunkStatus)null, -1, field_222618_n, ChunkStatus.Type.PROTOCHUNK, (p_223194_0_, p_223194_1_, p_223194_2_, p_223194_3_) -> {
   });
   public static final ChunkStatus STRUCTURE_STARTS = func_223207_a("structure_starts", EMPTY, 0, field_222618_n, ChunkStatus.Type.PROTOCHUNK, (p_222586_0_, p_222586_1_, p_222586_2_, p_222586_3_, p_222586_4_, p_222586_5_, p_222586_6_, p_222586_7_) -> {
      if (!p_222586_7_.getStatus().isAtLeast(p_222586_0_)) {
         if (p_222586_1_.getWorldInfo().isMapFeaturesEnabled()) {
            p_222586_2_.initStructureStarts(p_222586_7_, p_222586_2_, p_222586_3_);
         }

         if (p_222586_7_ instanceof ChunkPrimer) {
            ((ChunkPrimer)p_222586_7_).setStatus(p_222586_0_);
         }
      }

      return CompletableFuture.completedFuture(Either.left(p_222586_7_));
   });
   public static final ChunkStatus STRUCTURE_REFERENCES = func_223203_a("structure_references", STRUCTURE_STARTS, 8, field_222618_n, ChunkStatus.Type.PROTOCHUNK, (p_222587_0_, p_222587_1_, p_222587_2_, p_222587_3_) -> {
      p_222587_1_.func_222528_a(new WorldGenRegion(p_222587_0_, p_222587_2_), p_222587_3_);
   });
   public static final ChunkStatus BIOMES = func_223203_a("biomes", STRUCTURE_REFERENCES, 0, field_222618_n, ChunkStatus.Type.PROTOCHUNK, (p_222594_0_, p_222594_1_, p_222594_2_, p_222594_3_) -> {
      p_222594_1_.generateBiomes(p_222594_3_);
   });
   public static final ChunkStatus NOISE = func_223203_a("noise", BIOMES, 8, field_222618_n, ChunkStatus.Type.PROTOCHUNK, (p_222592_0_, p_222592_1_, p_222592_2_, p_222592_3_) -> {
      p_222592_1_.func_222537_b(new WorldGenRegion(p_222592_0_, p_222592_2_), p_222592_3_);
   });
   public static final ChunkStatus SURFACE = func_223203_a("surface", NOISE, 0, field_222618_n, ChunkStatus.Type.PROTOCHUNK, (p_222589_0_, p_222589_1_, p_222589_2_, p_222589_3_) -> {
      p_222589_1_.generateSurface(p_222589_3_);
   });
   public static final ChunkStatus CARVERS = func_223203_a("carvers", SURFACE, 0, field_222618_n, ChunkStatus.Type.PROTOCHUNK, (p_222590_0_, p_222590_1_, p_222590_2_, p_222590_3_) -> {
      p_222590_1_.carve(p_222590_3_, GenerationStage.Carving.AIR);
   });
   public static final ChunkStatus LIQUID_CARVERS = func_223203_a("liquid_carvers", CARVERS, 0, field_222619_o, ChunkStatus.Type.PROTOCHUNK, (p_222601_0_, p_222601_1_, p_222601_2_, p_222601_3_) -> {
      p_222601_1_.carve(p_222601_3_, GenerationStage.Carving.LIQUID);
   });
   public static final ChunkStatus FEATURES = func_223207_a("features", LIQUID_CARVERS, 8, field_222619_o, ChunkStatus.Type.PROTOCHUNK, (p_222605_0_, p_222605_1_, p_222605_2_, p_222605_3_, p_222605_4_, p_222605_5_, p_222605_6_, p_222605_7_) -> {
      p_222605_7_.setLightManager(p_222605_4_);
      if (!p_222605_7_.getStatus().isAtLeast(p_222605_0_)) {
         Heightmap.func_222690_a(p_222605_7_, EnumSet.of(Heightmap.Type.MOTION_BLOCKING, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, Heightmap.Type.OCEAN_FLOOR, Heightmap.Type.WORLD_SURFACE));
         p_222605_2_.decorate(new WorldGenRegion(p_222605_1_, p_222605_6_));
         if (p_222605_7_ instanceof ChunkPrimer) {
            ((ChunkPrimer)p_222605_7_).setStatus(p_222605_0_);
         }
      }

      return CompletableFuture.completedFuture(Either.left(p_222605_7_));
   });
   public static final ChunkStatus LIGHT = register("light", FEATURES, 1, field_222619_o, ChunkStatus.Type.PROTOCHUNK, (p_222604_0_, p_222604_1_, p_222604_2_, p_222604_3_, p_222604_4_, p_222604_5_, p_222604_6_, p_222604_7_) -> {
      return func_223206_a(p_222604_0_, p_222604_4_, p_222604_7_);
   }, (p_223195_0_, p_223195_1_, p_223195_2_, p_223195_3_, p_223195_4_, p_223195_5_) -> {
      return func_223206_a(p_223195_0_, p_223195_3_, p_223195_5_);
   });
   public static final ChunkStatus SPAWN = func_223203_a("spawn", LIGHT, 0, field_222619_o, ChunkStatus.Type.PROTOCHUNK, (p_222602_0_, p_222602_1_, p_222602_2_, p_222602_3_) -> {
      p_222602_1_.spawnMobs(new WorldGenRegion(p_222602_0_, p_222602_2_));
   });
   public static final ChunkStatus HEIGHTMAPS = func_223203_a("heightmaps", SPAWN, 0, field_222619_o, ChunkStatus.Type.PROTOCHUNK, (p_222603_0_, p_222603_1_, p_222603_2_, p_222603_3_) -> {
   });
   public static final ChunkStatus FULL = register("full", HEIGHTMAPS, 0, field_222619_o, ChunkStatus.Type.LEVELCHUNK, (p_222598_0_, p_222598_1_, p_222598_2_, p_222598_3_, p_222598_4_, p_222598_5_, p_222598_6_, p_222598_7_) -> {
      return (CompletableFuture)p_222598_5_.apply(p_222598_7_);
   }, (p_223205_0_, p_223205_1_, p_223205_2_, p_223205_3_, p_223205_4_, p_223205_5_) -> {
      return (CompletableFuture)p_223205_4_.apply(p_223205_5_);
   });
   private static final List<ChunkStatus> field_222620_p = ImmutableList.of(FULL, FEATURES, LIQUID_CARVERS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS, STRUCTURE_STARTS);
   private static final IntList field_222621_q = Util.make(new IntArrayList(getAll().size()), (p_223202_0_) -> {
      int i = 0;

      for(int j = getAll().size() - 1; j >= 0; --j) {
         while(i + 1 < field_222620_p.size() && j <= field_222620_p.get(i + 1).func_222584_c()) {
            ++i;
         }

         p_223202_0_.add(0, i);
      }

   });
   private final String name;
   private final int ordinal;
   private final ChunkStatus parent;
   private final ChunkStatus.IGenerationWorker generationWorker;
   private final ChunkStatus.ILoadingWorker loadingWorker;
   private final int taskRange;
   private final ChunkStatus.Type type;
   private final EnumSet<Heightmap.Type> heightmaps;

   private static CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_223206_a(ChunkStatus p_223206_0_, ServerWorldLightManager p_223206_1_, IChunk p_223206_2_) {
      boolean flag = func_223197_a(p_223206_0_, p_223206_2_);
      if (!p_223206_2_.getStatus().isAtLeast(p_223206_0_)) {
         ((ChunkPrimer)p_223206_2_).setStatus(p_223206_0_);
      }

      return p_223206_1_.func_215593_a(p_223206_2_, flag).thenApply(Either::left);
   }

   private static ChunkStatus func_223203_a(String p_223203_0_, @Nullable ChunkStatus p_223203_1_, int p_223203_2_, EnumSet<Heightmap.Type> p_223203_3_, ChunkStatus.Type p_223203_4_, ChunkStatus.ISelectiveWorker p_223203_5_) {
      return func_223207_a(p_223203_0_, p_223203_1_, p_223203_2_, p_223203_3_, p_223203_4_, p_223203_5_);
   }

   private static ChunkStatus func_223207_a(String p_223207_0_, @Nullable ChunkStatus p_223207_1_, int p_223207_2_, EnumSet<Heightmap.Type> p_223207_3_, ChunkStatus.Type p_223207_4_, ChunkStatus.IGenerationWorker p_223207_5_) {
      return register(p_223207_0_, p_223207_1_, p_223207_2_, p_223207_3_, p_223207_4_, p_223207_5_, NOOP_LOADING_WORKER);
   }

   private static ChunkStatus register(String p_223196_0_, @Nullable ChunkStatus p_223196_1_, int p_223196_2_, EnumSet<Heightmap.Type> p_223196_3_, ChunkStatus.Type p_223196_4_, ChunkStatus.IGenerationWorker p_223196_5_, ChunkStatus.ILoadingWorker p_223196_6_) {
      return Registry.register(Registry.field_218360_A, p_223196_0_, new ChunkStatus(p_223196_0_, p_223196_1_, p_223196_2_, p_223196_3_, p_223196_4_, p_223196_5_, p_223196_6_));
   }

   public static List<ChunkStatus> getAll() {
      List<ChunkStatus> list = Lists.newArrayList();

      ChunkStatus chunkstatus;
      for(chunkstatus = FULL; chunkstatus.func_222593_e() != chunkstatus; chunkstatus = chunkstatus.func_222593_e()) {
         list.add(chunkstatus);
      }

      list.add(chunkstatus);
      Collections.reverse(list);
      return list;
   }

   private static boolean func_223197_a(ChunkStatus p_223197_0_, IChunk p_223197_1_) {
      return p_223197_1_.getStatus().isAtLeast(p_223197_0_) && p_223197_1_.hasLight();
   }

   public static ChunkStatus func_222581_a(int p_222581_0_) {
      if (p_222581_0_ >= field_222620_p.size()) {
         return EMPTY;
      } else {
         return p_222581_0_ < 0 ? FULL : field_222620_p.get(p_222581_0_);
      }
   }

   public static int func_222600_b() {
      return field_222620_p.size();
   }

   public static int func_222599_a(ChunkStatus p_222599_0_) {
      return field_222621_q.getInt(p_222599_0_.func_222584_c());
   }

   public ChunkStatus(String p_i51520_1_, @Nullable ChunkStatus p_i51520_2_, int p_i51520_3_, EnumSet<Heightmap.Type> p_i51520_4_, ChunkStatus.Type p_i51520_5_, ChunkStatus.IGenerationWorker p_i51520_6_, ChunkStatus.ILoadingWorker p_i51520_7_) {
      this.name = p_i51520_1_;
      this.parent = p_i51520_2_ == null ? this : p_i51520_2_;
      this.generationWorker = p_i51520_6_;
      this.loadingWorker = p_i51520_7_;
      this.taskRange = p_i51520_3_;
      this.type = p_i51520_5_;
      this.heightmaps = p_i51520_4_;
      this.ordinal = p_i51520_2_ == null ? 0 : p_i51520_2_.func_222584_c() + 1;
   }

   public int func_222584_c() {
      return this.ordinal;
   }

   public String getName() {
      return this.name;
   }

   public ChunkStatus func_222593_e() {
      return this.parent;
   }

   public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_223198_a(ServerWorld p_223198_1_, ChunkGenerator<?> p_223198_2_, TemplateManager p_223198_3_, ServerWorldLightManager p_223198_4_, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> p_223198_5_, List<IChunk> p_223198_6_) {
      return this.generationWorker.doWork(this, p_223198_1_, p_223198_2_, p_223198_3_, p_223198_4_, p_223198_5_, p_223198_6_, p_223198_6_.get(p_223198_6_.size() / 2));
   }

   public CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> func_223201_a(ServerWorld p_223201_1_, TemplateManager p_223201_2_, ServerWorldLightManager p_223201_3_, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> p_223201_4_, IChunk p_223201_5_) {
      return this.loadingWorker.doWork(this, p_223201_1_, p_223201_2_, p_223201_3_, p_223201_4_, p_223201_5_);
   }

   /**
    * Distance in chunks between the edge of the center chunk and the edge of the chunk region needed for the task. The
    * task will only affect the center chunk, only reading from the chunks in the margin.
    */
   public int getTaskRange() {
      return this.taskRange;
   }

   public ChunkStatus.Type getType() {
      return this.type;
   }

   public static ChunkStatus byName(String p_222591_0_) {
      return Registry.field_218360_A.getOrDefault(ResourceLocation.tryCreate(p_222591_0_));
   }

   public EnumSet<Heightmap.Type> func_222595_h() {
      return this.heightmaps;
   }

   public boolean isAtLeast(ChunkStatus status) {
      return this.func_222584_c() >= status.func_222584_c();
   }

   public String toString() {
      return Registry.field_218360_A.getKey(this).toString();
   }

   interface IGenerationWorker {
      CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> doWork(ChunkStatus p_doWork_1_, ServerWorld p_doWork_2_, ChunkGenerator<?> p_doWork_3_, TemplateManager p_doWork_4_, ServerWorldLightManager p_doWork_5_, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> p_doWork_6_, List<IChunk> p_doWork_7_, IChunk p_doWork_8_);
   }

   interface ILoadingWorker {
      CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> doWork(ChunkStatus p_doWork_1_, ServerWorld p_doWork_2_, TemplateManager p_doWork_3_, ServerWorldLightManager p_doWork_4_, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> p_doWork_5_, IChunk p_doWork_6_);
   }

   interface ISelectiveWorker extends ChunkStatus.IGenerationWorker {
      default CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>> doWork(ChunkStatus p_doWork_1_, ServerWorld p_doWork_2_, ChunkGenerator<?> p_doWork_3_, TemplateManager p_doWork_4_, ServerWorldLightManager p_doWork_5_, Function<IChunk, CompletableFuture<Either<IChunk, ChunkHolder.IChunkLoadingError>>> p_doWork_6_, List<IChunk> p_doWork_7_, IChunk p_doWork_8_) {
         if (!p_doWork_8_.getStatus().isAtLeast(p_doWork_1_)) {
            this.doWork(p_doWork_2_, p_doWork_3_, p_doWork_7_, p_doWork_8_);
            if (p_doWork_8_ instanceof ChunkPrimer) {
               ((ChunkPrimer)p_doWork_8_).setStatus(p_doWork_1_);
            }
         }

         return CompletableFuture.completedFuture(Either.left(p_doWork_8_));
      }

      void doWork(ServerWorld p_doWork_1_, ChunkGenerator<?> p_doWork_2_, List<IChunk> p_doWork_3_, IChunk p_doWork_4_);
   }

   public static enum Type {
      PROTOCHUNK,
      LEVELCHUNK;
   }
}