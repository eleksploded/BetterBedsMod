package net.minecraft.world.biome;

import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public final class TheVoidBiome extends Biome {
   public TheVoidBiome() {
      super((new Biome.Builder()).func_222351_a(SurfaceBuilder.field_215407_R, SurfaceBuilder.field_215427_x).precipitation(Biome.RainType.NONE).category(Biome.Category.NONE).depth(0.1F).scale(0.2F).temperature(0.5F).downfall(0.5F).waterColor(4159204).waterFogColor(329011).parent((String)null));
      this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, func_222280_a(Feature.VOID_START_PLATFORM, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
   }
}