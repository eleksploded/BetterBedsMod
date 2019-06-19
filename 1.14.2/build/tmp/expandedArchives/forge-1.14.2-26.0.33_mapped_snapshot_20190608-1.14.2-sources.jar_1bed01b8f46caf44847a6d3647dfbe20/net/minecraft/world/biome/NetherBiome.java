package net.minecraft.world.biome;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.BushConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HellLavaConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public final class NetherBiome extends Biome {
   protected NetherBiome() {
      super((new Biome.Builder()).func_222351_a(SurfaceBuilder.field_215406_Q, SurfaceBuilder.field_215394_E).precipitation(Biome.RainType.NONE).category(Biome.Category.NETHER).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).waterColor(4159204).waterFogColor(329011).parent((String)null));
      this.addStructure(Feature.FORTRESS, IFeatureConfig.NO_FEATURE_CONFIG);
      this.addCarver(GenerationStage.Carving.AIR, createWorldCarverWrapper(WorldCarver.field_222710_b, new ProbabilityConfig(0.2F)));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, func_222280_a(Feature.LIQUIDS, new LiquidsConfig(Fluids.LAVA.getDefaultState()), Placement.field_215030_p, new CountRangeConfig(20, 8, 16, 256)));
      DefaultBiomeFeatures.func_222315_Z(this);
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.FORTRESS, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.field_214503_ao, new HellLavaConfig(false), Placement.field_215028_n, new CountRangeConfig(8, 4, 8, 128)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.FIRE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215002_A, new FrequencyConfig(10)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.GLOWSTONE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215010_I, new FrequencyConfig(10)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.GLOWSTONE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215028_n, new CountRangeConfig(10, 0, 0, 128)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.BUSH, new BushConfig(Blocks.BROWN_MUSHROOM.getDefaultState()), Placement.field_215032_r, new ChanceRangeConfig(0.5F, 0, 0, 128)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.BUSH, new BushConfig(Blocks.RED_MUSHROOM.getDefaultState()), Placement.field_215032_r, new ChanceRangeConfig(0.5F, 0, 0, 128)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.NETHER_QUARTZ_ORE.getDefaultState(), 14), Placement.field_215028_n, new CountRangeConfig(16, 10, 20, 128)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.MINABLE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.MAGMA_BLOCK.getDefaultState(), 33), Placement.field_215003_B, new FrequencyConfig(4)));
      this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, func_222280_a(Feature.field_214503_ao, new HellLavaConfig(true), Placement.field_215028_n, new CountRangeConfig(16, 10, 20, 128)));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.GHAST, 50, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_PIGMAN, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 2, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 1, 4, 4));
   }
}