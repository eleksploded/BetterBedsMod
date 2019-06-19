package net.minecraft.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.EndGatewayConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EndHighlandsBiome extends Biome {
   public EndHighlandsBiome() {
      super((new Biome.Builder()).func_222351_a(SurfaceBuilder.field_215396_G, SurfaceBuilder.field_215395_F).precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F).temperature(0.5F).downfall(0.5F).waterColor(4159204).waterFogColor(329011).parent((String)null));
      this.addStructure(Feature.BURIED_TREASURE, IFeatureConfig.NO_FEATURE_CONFIG);
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, func_222280_a(Feature.END_GATEWAY, EndGatewayConfig.func_214702_a(EndDimension.SPAWN, true), Placement.field_215013_L, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, func_222280_a(Feature.BURIED_TREASURE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215022_h, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, func_222280_a(Feature.CHORUS_PLANT, IFeatureConfig.NO_FEATURE_CONFIG, Placement.field_215012_K, IPlacementConfig.NO_PLACEMENT_CONFIG));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 4, 4));
   }

   /**
    * takes temperature, returns color
    */
   @OnlyIn(Dist.CLIENT)
   public int getSkyColorByTemp(float currentTemperature) {
      return 0;
   }
}