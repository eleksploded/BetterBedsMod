package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.BlockWithContextConfig;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.DoublePlantConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.GrassFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.IcebergConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.ReplaceBlockConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TwoFeatureChoiceConfig;
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.CaveEdgeConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.HeightWithChanceConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.LakeChanceConfig;
import net.minecraft.world.gen.placement.NoiseDependant;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;

public class DefaultBiomeFeatures {
   public static void func_222300_a(Biome p_222300_0_) {
      p_222300_0_.addCarver(GenerationStage.Carving.AIR, Biome.createWorldCarverWrapper(WorldCarver.field_222709_a, new ProbabilityConfig(0.14285715F)));
      p_222300_0_.addCarver(GenerationStage.Carving.AIR, Biome.createWorldCarverWrapper(WorldCarver.field_222711_c, new ProbabilityConfig(0.02F)));
   }

   public static void func_222346_b(Biome p_222346_0_) {
      p_222346_0_.addCarver(GenerationStage.Carving.AIR, Biome.createWorldCarverWrapper(WorldCarver.field_222709_a, new ProbabilityConfig(0.06666667F)));
      p_222346_0_.addCarver(GenerationStage.Carving.AIR, Biome.createWorldCarverWrapper(WorldCarver.field_222711_c, new ProbabilityConfig(0.02F)));
      p_222346_0_.addCarver(GenerationStage.Carving.LIQUID, Biome.createWorldCarverWrapper(WorldCarver.field_222712_d, new ProbabilityConfig(0.02F)));
      p_222346_0_.addCarver(GenerationStage.Carving.LIQUID, Biome.createWorldCarverWrapper(WorldCarver.field_222713_e, new ProbabilityConfig(0.06666667F)));
   }

   public static void func_222295_c(Biome p_222295_0_) {
      p_222295_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.func_222280_a(Feature.MINESHAFT, new MineshaftConfig((double)0.004F, MineshaftStructure.Type.NORMAL), Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.field_214536_b, new PillagerOutpostConfig(0.004D), Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.func_222280_a(Feature.STRONGHOLD, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.field_202334_l, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.DESERT_PYRAMID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.JUNGLE_PYRAMID, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.IGLOO, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.SHIPWRECK, new ShipwreckConfig(false), Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.OCEAN_MONUMENT, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.WOODLAND_MANSION, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.OCEAN_RUIN, new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F), Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.func_222280_a(Feature.field_214549_o, new BuriedTreasureConfig(0.01F), Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      p_222295_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.field_214550_p, new VillageConfig("village/plains/town_centers", 6), Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
   }

   public static void func_222333_d(Biome p_222333_0_) {
      p_222333_0_.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.func_222280_a(Feature.LAKES, new LakesConfig(Blocks.WATER.getDefaultState()), Placement.field_215006_E, new LakeChanceConfig(4)));
      p_222333_0_.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.func_222280_a(Feature.LAKES, new LakesConfig(Blocks.LAVA.getDefaultState()), Placement.field_215005_D, new LakeChanceConfig(80)));
   }

   public static void func_222301_e(Biome p_222301_0_) {
      p_222301_0_.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.func_222280_a(Feature.LAKES, new LakesConfig(Blocks.LAVA.getDefaultState()), Placement.field_215005_D, new LakeChanceConfig(80)));
   }

   public static void func_222335_f(Biome p_222335_0_) {
      p_222335_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.func_222280_a(Feature.DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215007_F, new DungeonRoomConfig(8)));
   }

   public static void func_222326_g(Biome p_222326_0_) {
      p_222326_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33), Placement.field_215028_n, new CountRangeConfig(10, 0, 0, 256)));
      p_222326_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33), Placement.field_215028_n, new CountRangeConfig(8, 0, 0, 256)));
      p_222326_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33), Placement.field_215028_n, new CountRangeConfig(10, 0, 0, 80)));
      p_222326_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33), Placement.field_215028_n, new CountRangeConfig(10, 0, 0, 80)));
      p_222326_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33), Placement.field_215028_n, new CountRangeConfig(10, 0, 0, 80)));
   }

   public static void func_222288_h(Biome p_222288_0_) {
      p_222288_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17), Placement.field_215028_n, new CountRangeConfig(20, 0, 0, 128)));
      p_222288_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9), Placement.field_215028_n, new CountRangeConfig(20, 0, 0, 64)));
      p_222288_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9), Placement.field_215028_n, new CountRangeConfig(2, 0, 0, 32)));
      p_222288_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8), Placement.field_215028_n, new CountRangeConfig(8, 0, 0, 16)));
      p_222288_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8), Placement.field_215028_n, new CountRangeConfig(1, 0, 0, 16)));
      p_222288_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7), Placement.field_215035_u, new DepthAverageConfig(1, 16, 16)));
   }

   public static void func_222328_i(Biome p_222328_0_) {
      p_222328_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9), Placement.field_215028_n, new CountRangeConfig(20, 32, 32, 80)));
   }

   public static void func_222291_j(Biome p_222291_0_) {
      p_222291_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.REPLACE_BLOCK, new ReplaceBlockConfig(Blocks.STONE.getDefaultState(), Blocks.EMERALD_ORE.getDefaultState()), Placement.field_215004_C, IPlacementConfig.NO_PLACEMENT_CONFIG));
   }

   public static void func_222322_k(Biome p_222322_0_) {
      p_222322_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.INFESTED_STONE.getDefaultState(), 9), Placement.field_215028_n, new CountRangeConfig(7, 0, 0, 64)));
   }

   public static void func_222282_l(Biome p_222282_0_) {
      p_222282_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.SAND.getDefaultState(), 7, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState())), Placement.field_215016_b, new FrequencyConfig(3)));
      p_222282_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), 4, 1, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState())), Placement.field_215016_b, new FrequencyConfig(1)));
      p_222282_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState())), Placement.field_215016_b, new FrequencyConfig(1)));
   }

   public static void func_222318_m(Biome p_222318_0_) {
      p_222318_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.func_222280_a(Feature.SPHERE_REPLACE, new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), 4, 1, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.CLAY.getDefaultState())), Placement.field_215016_b, new FrequencyConfig(1)));
   }

   public static void func_222313_n(Biome p_222313_0_) {
      p_222313_0_.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.func_222280_a(Feature.BLOCK_BLOB, new BlockBlobConfig(Blocks.MOSSY_COBBLESTONE.getDefaultState(), 0), Placement.field_215040_z, new FrequencyConfig(3)));
   }

   public static void func_222345_o(Biome p_222345_0_) {
      p_222345_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.DOUBLE_PLANT, new DoublePlantConfig(Blocks.LARGE_FERN.getDefaultState()), Placement.field_215017_c, new FrequencyConfig(7)));
   }

   public static void func_222307_p(Biome p_222307_0_) {
      p_222307_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214485_aM, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215024_j, new ChanceConfig(12)));
   }

   public static void func_222341_q(Biome p_222341_0_) {
      p_222341_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214485_aM, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(1)));
   }

   public static void func_222289_r(Biome p_222289_0_) {
      p_222289_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214482_aJ, new ProbabilityConfig(0.0F), Placement.field_215018_d, new FrequencyConfig(16)));
   }

   public static void func_222325_s(Biome p_222325_0_) {
      p_222325_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214482_aJ, new ProbabilityConfig(0.2F), Placement.field_215038_x, new TopSolidWithNoiseConfig(160, 80.0D, 0.3D, Heightmap.Type.WORLD_SURFACE_WG)));
      p_222325_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202339_q, Feature.field_202342_t, Feature.field_202302_B}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.05F, 0.15F, 0.7F}, Feature.JUNGLE_GRASS, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(30, 0.1F, 1)));
   }

   public static void func_222293_t(Biome p_222293_0_) {
      p_222293_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202344_v}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.33333334F}, Feature.field_202347_y, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
   }

   public static void func_222296_u(Biome p_222296_0_) {
      p_222296_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202339_q}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.1F}, Feature.field_202301_A, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
   }

   public static void func_222330_v(Biome p_222330_0_) {
      p_222330_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_202340_r, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215027_m, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
   }

   public static void func_222302_w(Biome p_222302_0_) {
      p_222302_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202340_r, Feature.field_202339_q}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.2F, 0.1F}, Feature.field_202301_A, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
   }

   public static void func_222336_x(Biome p_222336_0_) {
      p_222336_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202341_s}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.5F}, Feature.field_202340_r, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
   }

   public static void func_222310_y(Biome p_222310_0_) {
      p_222310_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202346_x}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.8F}, Feature.field_202301_A, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
   }

   public static void func_222347_z(Biome p_222347_0_) {
      p_222347_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202346_x}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.8F}, Feature.field_202301_A, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(2, 0.1F, 1)));
   }

   public static void func_222343_A(Biome p_222343_0_) {
      p_222343_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202347_y, Feature.field_202339_q}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.666F, 0.1F}, Feature.field_202301_A, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
   }

   public static void func_222304_B(Biome p_222304_0_) {
      p_222304_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202347_y, Feature.field_202339_q}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.666F, 0.1F}, Feature.field_202301_A, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(3, 0.1F, 1)));
   }

   public static void func_222323_C(Biome p_222323_0_) {
      p_222323_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202339_q, Feature.field_202342_t, Feature.field_202302_B}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.1F, 0.5F, 0.33333334F}, Feature.field_202343_u, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(50, 0.1F, 1)));
   }

   public static void func_222290_D(Biome p_222290_0_) {
      p_222290_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202339_q, Feature.field_202342_t}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.1F, 0.5F}, Feature.field_202343_u, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(2, 0.1F, 1)));
   }

   public static void func_222327_E(Biome p_222327_0_) {
      p_222327_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_202301_A, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215027_m, new AtSurfaceWithExtraConfig(5, 0.1F, 1)));
   }

   public static void func_222284_F(Biome p_222284_0_) {
      p_222284_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_202347_y, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215027_m, new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
   }

   public static void func_222316_G(Biome p_222316_0_) {
      p_222316_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202304_D, Feature.field_202344_v}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.33333334F, 0.33333334F}, Feature.field_202347_y, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
   }

   public static void func_222285_H(Biome p_222285_0_) {
      p_222285_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202304_D, Feature.field_202303_C, Feature.field_202344_v}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.025641026F, 0.30769232F, 0.33333334F}, Feature.field_202347_y, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(10, 0.1F, 1)));
   }

   public static void func_222321_I(Biome p_222321_0_) {
      p_222321_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.JUNGLE_GRASS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(25)));
   }

   public static void func_222344_J(Biome p_222344_0_) {
      p_222344_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.DOUBLE_PLANT, new DoublePlantConfig(Blocks.TALL_GRASS.getDefaultState()), Placement.field_215017_c, new FrequencyConfig(7)));
   }

   public static void func_222314_K(Biome p_222314_0_) {
      p_222314_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214520_L, new GrassFeatureConfig(Blocks.GRASS.getDefaultState()), Placement.field_215018_d, new FrequencyConfig(5)));
   }

   public static void func_222339_L(Biome p_222339_0_) {
      p_222339_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214520_L, new GrassFeatureConfig(Blocks.GRASS.getDefaultState()), Placement.field_215018_d, new FrequencyConfig(20)));
   }

   public static void func_222308_M(Biome p_222308_0_) {
      p_222308_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214520_L, new GrassFeatureConfig(Blocks.GRASS.getDefaultState()), Placement.field_215018_d, new FrequencyConfig(1)));
      p_222308_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.DEAD_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(20)));
   }

   public static void func_222338_N(Biome p_222338_0_) {
      p_222338_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.DEFAULT_RANDOM_FEATURE_LIST, new MultipleWithChanceRandomFeatureConfig(new Feature[]{Feature.DOUBLE_PLANT, Feature.DOUBLE_PLANT, Feature.DOUBLE_PLANT, Feature.field_214519_I}, new IFeatureConfig[]{new DoublePlantConfig(Blocks.LILAC.getDefaultState()), new DoublePlantConfig(Blocks.ROSE_BUSH.getDefaultState()), new DoublePlantConfig(Blocks.PEONY.getDefaultState()), IFeatureConfig.NO_FEATURE_CONFIG}, 0), Placement.field_215017_c, new FrequencyConfig(5)));
   }

   public static void func_222298_O(Biome p_222298_0_) {
      p_222298_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214520_L, new GrassFeatureConfig(Blocks.GRASS.getDefaultState()), Placement.field_215018_d, new FrequencyConfig(2)));
   }

   public static void func_222331_P(Biome p_222331_0_) {
      p_222331_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_202348_z, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215027_m, new AtSurfaceWithExtraConfig(2, 0.1F, 1)));
      p_222331_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_202308_H, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215017_c, new FrequencyConfig(1)));
      p_222331_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214520_L, new GrassFeatureConfig(Blocks.GRASS.getDefaultState()), Placement.field_215018_d, new FrequencyConfig(5)));
      p_222331_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.DEAD_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(1)));
      p_222331_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.WATERLILY, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(4)));
      p_222331_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), Placement.field_215033_s, new HeightWithChanceConfig(8, 0.25F)));
      p_222331_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()), Placement.field_215034_t, new HeightWithChanceConfig(8, 0.125F)));
   }

   public static void func_222294_Q(Biome p_222294_0_) {
      p_222294_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.TWO_FEATURE_CHOICE, new TwoFeatureChoiceConfig(Feature.BIG_RED_MUSHROOM, new BigMushroomFeatureConfig(false), Feature.BIG_BROWN_MUSHROOM, new BigMushroomFeatureConfig(false)), Placement.field_215015_a, new FrequencyConfig(1)));
      p_222294_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), Placement.field_215033_s, new HeightWithChanceConfig(1, 0.25F)));
      p_222294_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()), Placement.field_215034_t, new HeightWithChanceConfig(1, 0.125F)));
   }

   public static void func_222299_R(Biome p_222299_0_) {
      p_222299_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.RANDOM_FEATURE_LIST, new MultipleRandomFeatureConfig(new Feature[]{Feature.field_202339_q}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.33333334F}, Feature.field_202301_A, IFeatureConfig.NO_FEATURE_CONFIG), Placement.field_215027_m, new AtSurfaceWithExtraConfig(0, 0.05F, 1)));
      p_222299_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_202307_G, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215020_f, new NoiseDependant(-0.8D, 15, 4)));
      p_222299_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214520_L, new GrassFeatureConfig(Blocks.GRASS.getDefaultState()), Placement.field_215021_g, new NoiseDependant(-0.8D, 5, 10)));
   }

   public static void func_222334_S(Biome p_222334_0_) {
      p_222334_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.DEAD_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(2)));
   }

   public static void func_222303_T(Biome p_222303_0_) {
      p_222303_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.TAIGA_GRASS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(7)));
      p_222303_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.DEAD_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(1)));
      p_222303_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), Placement.field_215033_s, new HeightWithChanceConfig(3, 0.25F)));
      p_222303_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()), Placement.field_215034_t, new HeightWithChanceConfig(3, 0.125F)));
   }

   public static void func_222342_U(Biome p_222342_0_) {
      p_222342_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_202305_E, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215017_c, new FrequencyConfig(2)));
   }

   public static void func_222306_V(Biome p_222306_0_) {
      p_222306_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_202305_E, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215017_c, new FrequencyConfig(4)));
   }

   public static void func_222348_W(Biome p_222348_0_) {
      p_222348_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.field_214520_L, new GrassFeatureConfig(Blocks.GRASS.getDefaultState()), Placement.field_215018_d, new FrequencyConfig(1)));
   }

   public static void func_222319_X(Biome p_222319_0_) {
      p_222319_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.TAIGA_GRASS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(1)));
      p_222319_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), Placement.field_215033_s, new HeightWithChanceConfig(1, 0.25F)));
      p_222319_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()), Placement.field_215034_t, new HeightWithChanceConfig(1, 0.125F)));
   }

   public static void func_222283_Y(Biome p_222283_0_) {
      p_222283_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.DOUBLE_PLANT, new DoublePlantConfig(Blocks.TALL_GRASS.getDefaultState()), Placement.field_215020_f, new NoiseDependant(-0.8D, 0, 7)));
   }

   public static void func_222315_Z(Biome p_222315_0_) {
      p_222315_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), Placement.field_215024_j, new ChanceConfig(4)));
      p_222315_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()), Placement.field_215024_j, new ChanceConfig(8)));
   }

   public static void func_222311_aa(Biome p_222311_0_) {
      p_222311_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(10)));
      p_222311_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215024_j, new ChanceConfig(32)));
   }

   public static void func_222286_ab(Biome p_222286_0_) {
      p_222286_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(13)));
      p_222286_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215024_j, new ChanceConfig(32)));
      p_222286_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.CACTUS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(5)));
   }

   public static void func_222324_ac(Biome p_222324_0_) {
      p_222324_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.MELON, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(1)));
      p_222324_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.VINES, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215019_e, new FrequencyConfig(50)));
   }

   public static void func_222292_ad(Biome p_222292_0_) {
      p_222292_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(60)));
      p_222292_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215024_j, new ChanceConfig(32)));
      p_222292_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.CACTUS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(10)));
   }

   public static void func_222329_ae(Biome p_222329_0_) {
      p_222329_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215018_d, new FrequencyConfig(20)));
      p_222329_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215024_j, new ChanceConfig(32)));
   }

   public static void func_222281_af(Biome p_222281_0_) {
      p_222281_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.DESERT_WELLS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215023_i, new ChanceConfig(1000)));
      p_222281_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.func_222280_a(Feature.FOSSILS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215025_k, new ChanceConfig(64)));
   }

   public static void func_222317_ag(Biome p_222317_0_) {
      p_222317_0_.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.func_222280_a(Feature.FOSSILS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215025_k, new ChanceConfig(64)));
   }

   public static void func_222287_ah(Biome p_222287_0_) {
      p_222287_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.KELP, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215038_x, new TopSolidWithNoiseConfig(120, 80.0D, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG)));
   }

   public static void func_222320_ai(Biome p_222320_0_) {
      p_222320_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.BLOCK_WITH_CONTEXT, new BlockWithContextConfig(Blocks.SEAGRASS.getDefaultState(), new BlockState[]{Blocks.STONE.getDefaultState()}, new BlockState[]{Blocks.WATER.getDefaultState()}, new BlockState[]{Blocks.WATER.getDefaultState()}), Placement.field_215039_y, new CaveEdgeConfig(GenerationStage.Carving.LIQUID, 0.1F)));
   }

   public static void func_222309_aj(Biome p_222309_0_) {
      p_222309_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.SEA_GRASS, new SeaGrassConfig(80, 0.3D), Placement.field_215036_v, IPlacementConfig.NO_PLACEMENT_CONFIG));
   }

   public static void func_222340_ak(Biome p_222340_0_) {
      p_222340_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.SEA_GRASS, new SeaGrassConfig(80, 0.8D), Placement.field_215036_v, IPlacementConfig.NO_PLACEMENT_CONFIG));
   }

   public static void func_222312_al(Biome p_222312_0_) {
      p_222312_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.KELP, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215038_x, new TopSolidWithNoiseConfig(80, 80.0D, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG)));
   }

   public static void func_222337_am(Biome p_222337_0_) {
      p_222337_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.LIQUIDS, new LiquidsConfig(Fluids.WATER.getDefaultState()), Placement.field_215029_o, new CountRangeConfig(50, 8, 8, 256)));
      p_222337_0_.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.func_222280_a(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA.getDefaultState()), Placement.field_215030_p, new CountRangeConfig(20, 8, 16, 256)));
   }

   public static void func_222305_an(Biome p_222305_0_) {
      p_222305_0_.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.func_222280_a(Feature.ICEBERG, new IcebergConfig(Blocks.PACKED_ICE.getDefaultState()), Placement.field_215009_H, new ChanceConfig(16)));
      p_222305_0_.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.func_222280_a(Feature.ICEBERG, new IcebergConfig(Blocks.BLUE_ICE.getDefaultState()), Placement.field_215009_H, new ChanceConfig(200)));
   }

   public static void func_222332_ao(Biome p_222332_0_) {
      p_222332_0_.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.func_222280_a(Feature.BLUE_ICE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215031_q, new CountRangeConfig(20, 30, 32, 64)));
   }

   public static void func_222297_ap(Biome p_222297_0_) {
      p_222297_0_.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.func_222280_a(Feature.ICE_AND_SNOW, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
   }
}