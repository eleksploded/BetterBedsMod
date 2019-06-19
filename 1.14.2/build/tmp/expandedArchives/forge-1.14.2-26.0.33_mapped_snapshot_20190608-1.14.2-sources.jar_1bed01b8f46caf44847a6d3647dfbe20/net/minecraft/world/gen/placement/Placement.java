package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public abstract class Placement<DC extends IPlacementConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<Placement<?>> {
   public static final Placement<FrequencyConfig> field_215015_a = func_214999_a("count_heightmap", new AtSurface(FrequencyConfig::func_214721_a));
   public static final Placement<FrequencyConfig> field_215016_b = func_214999_a("count_top_solid", new TopSolid(FrequencyConfig::func_214721_a));
   public static final Placement<FrequencyConfig> field_215017_c = func_214999_a("count_heightmap_32", new SurfacePlus32(FrequencyConfig::func_214721_a));
   public static final Placement<FrequencyConfig> field_215018_d = func_214999_a("count_heightmap_double", new TwiceSurface(FrequencyConfig::func_214721_a));
   public static final Placement<FrequencyConfig> field_215019_e = func_214999_a("count_height_64", new AtHeight64(FrequencyConfig::func_214721_a));
   public static final Placement<NoiseDependant> field_215020_f = func_214999_a("noise_heightmap_32", new SurfacePlus32WithNoise(NoiseDependant::func_214734_a));
   public static final Placement<NoiseDependant> field_215021_g = func_214999_a("noise_heightmap_double", new TwiceSurfaceWithNoise(NoiseDependant::func_214734_a));
   public static final Placement<NoPlacementConfig> field_215022_h = func_214999_a("nope", new Passthrough(NoPlacementConfig::func_214735_a));
   public static final Placement<ChanceConfig> field_215023_i = func_214999_a("chance_heightmap", new AtSurfaceWithChance(ChanceConfig::func_214722_a));
   public static final Placement<ChanceConfig> field_215024_j = func_214999_a("chance_heightmap_double", new TwiceSurfaceWithChance(ChanceConfig::func_214722_a));
   public static final Placement<ChanceConfig> field_215025_k = func_214999_a("chance_passthrough", new WithChance(ChanceConfig::func_214722_a));
   public static final Placement<ChanceConfig> field_215026_l = func_214999_a("chance_top_solid_heightmap", new TopSolidWithChance(ChanceConfig::func_214722_a));
   public static final Placement<AtSurfaceWithExtraConfig> field_215027_m = func_214999_a("count_extra_heightmap", new AtSurfaceWithExtra(AtSurfaceWithExtraConfig::func_214723_a));
   public static final Placement<CountRangeConfig> field_215028_n = func_214999_a("count_range", new CountRange(CountRangeConfig::func_214733_a));
   public static final Placement<CountRangeConfig> field_215029_o = func_214999_a("count_biased_range", new HeightBiasedRange(CountRangeConfig::func_214733_a));
   public static final Placement<CountRangeConfig> field_215030_p = func_214999_a("count_very_biased_range", new HeightVeryBiasedRange(CountRangeConfig::func_214733_a));
   public static final Placement<CountRangeConfig> field_215031_q = func_214999_a("random_count_range", new RandomCountWithRange(CountRangeConfig::func_214733_a));
   public static final Placement<ChanceRangeConfig> field_215032_r = func_214999_a("chance_range", new ChanceRange(ChanceRangeConfig::func_214732_a));
   public static final Placement<HeightWithChanceConfig> field_215033_s = func_214999_a("count_chance_heightmap", new AtSurfaceWithChanceMultiple(HeightWithChanceConfig::func_214724_a));
   public static final Placement<HeightWithChanceConfig> field_215034_t = func_214999_a("count_chance_heightmap_double", new TwiceSurfaceWithChanceMultiple(HeightWithChanceConfig::func_214724_a));
   public static final Placement<DepthAverageConfig> field_215035_u = func_214999_a("count_depth_average", new DepthAverage(DepthAverageConfig::func_214729_a));
   public static final Placement<NoPlacementConfig> field_215036_v = func_214999_a("top_solid_heightmap", new TopSolidOnce(NoPlacementConfig::func_214735_a));
   public static final Placement<TopSolidRangeConfig> field_215037_w = func_214999_a("top_solid_heightmap_range", new TopSolidRange(TopSolidRangeConfig::func_214725_a));
   public static final Placement<TopSolidWithNoiseConfig> field_215038_x = func_214999_a("top_solid_heightmap_noise_biased", new TopSolidWithNoise(TopSolidWithNoiseConfig::func_214726_a));
   public static final Placement<CaveEdgeConfig> field_215039_y = func_214999_a("carving_mask", new CaveEdge(CaveEdgeConfig::func_214720_a));
   public static final Placement<FrequencyConfig> field_215040_z = func_214999_a("forest_rock", new AtSurfaceRandomCount(FrequencyConfig::func_214721_a));
   public static final Placement<FrequencyConfig> field_215002_A = func_214999_a("hell_fire", new NetherFire(FrequencyConfig::func_214721_a));
   public static final Placement<FrequencyConfig> field_215003_B = func_214999_a("magma", new NetherMagma(FrequencyConfig::func_214721_a));
   public static final Placement<NoPlacementConfig> field_215004_C = func_214999_a("emerald_ore", new Height4To32(NoPlacementConfig::func_214735_a));
   public static final Placement<LakeChanceConfig> field_215005_D = func_214999_a("lava_lake", new LakeLava(LakeChanceConfig::func_214730_a));
   public static final Placement<LakeChanceConfig> field_215006_E = func_214999_a("water_lake", new LakeWater(LakeChanceConfig::func_214730_a));
   public static final Placement<DungeonRoomConfig> field_215007_F = func_214999_a("dungeons", new DungeonRoom(DungeonRoomConfig::func_214731_a));
   public static final Placement<NoPlacementConfig> field_215008_G = func_214999_a("dark_oak_tree", new DarkOakTreePlacement(NoPlacementConfig::func_214735_a));
   public static final Placement<ChanceConfig> field_215009_H = func_214999_a("iceberg", new IcebergPlacement(ChanceConfig::func_214722_a));
   public static final Placement<FrequencyConfig> field_215010_I = func_214999_a("light_gem_chance", new NetherGlowstone(FrequencyConfig::func_214721_a));
   public static final Placement<NoPlacementConfig> field_215011_J = func_214999_a("end_island", new EndIsland(NoPlacementConfig::func_214735_a));
   public static final Placement<NoPlacementConfig> field_215012_K = func_214999_a("chorus_plant", new ChorusPlant(NoPlacementConfig::func_214735_a));
   public static final Placement<NoPlacementConfig> field_215013_L = func_214999_a("end_gateway", new EndGateway(NoPlacementConfig::func_214735_a));
   private final Function<Dynamic<?>, ? extends DC> configFactory;

   private static <T extends IPlacementConfig, G extends Placement<T>> G func_214999_a(String p_214999_0_, G p_214999_1_) {
      return (G)(Registry.<Placement<?>>register(Registry.field_218380_r, p_214999_0_, p_214999_1_));
   }

   public Placement(Function<Dynamic<?>, ? extends DC> p_i51371_1_) {
      this.configFactory = p_i51371_1_;
   }

   public DC createConfig(Dynamic<?> p_215001_1_) {
      return (DC)(this.configFactory.apply(p_215001_1_));
   }

   protected <FC extends IFeatureConfig> boolean place(IWorld p_214998_1_, ChunkGenerator<? extends GenerationSettings> p_214998_2_, Random p_214998_3_, BlockPos p_214998_4_, DC p_214998_5_, ConfiguredFeature<FC> p_214998_6_) {
      AtomicBoolean atomicboolean = new AtomicBoolean(false);
      this.getPositions(p_214998_1_, p_214998_2_, p_214998_3_, p_214998_5_, p_214998_4_).forEach((p_215000_5_) -> {
         boolean flag = p_214998_6_.place(p_214998_1_, p_214998_2_, p_214998_3_, p_215000_5_);
         atomicboolean.set(atomicboolean.get() || flag);
      });
      return atomicboolean.get();
   }

   public abstract Stream<BlockPos> getPositions(IWorld p_212848_1_, ChunkGenerator<? extends GenerationSettings> p_212848_2_, Random p_212848_3_, DC p_212848_4_, BlockPos p_212848_5_);

   public String toString() {
      return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
   }
}