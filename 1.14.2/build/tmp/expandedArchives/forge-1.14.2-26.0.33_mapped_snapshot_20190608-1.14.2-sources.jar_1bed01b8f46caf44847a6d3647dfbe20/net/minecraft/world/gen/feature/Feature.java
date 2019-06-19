package net.minecraft.world.gen.feature;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig;
import net.minecraft.world.gen.feature.structure.BuriedTreasureStructure;
import net.minecraft.world.gen.feature.structure.DesertPyramidStructure;
import net.minecraft.world.gen.feature.structure.EndCityStructure;
import net.minecraft.world.gen.feature.structure.FortressStructure;
import net.minecraft.world.gen.feature.structure.IglooStructure;
import net.minecraft.world.gen.feature.structure.JunglePyramidStructure;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.OceanMonumentStructure;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.feature.structure.PillagerOutpostStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckStructure;
import net.minecraft.world.gen.feature.structure.StrongholdStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.SwampHutStructure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillageStructure;
import net.minecraft.world.gen.feature.structure.WoodlandMansionStructure;
import net.minecraft.world.gen.placement.CountConfig;

public abstract class Feature<FC extends IFeatureConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<Feature<?>> {
   public static final Structure<PillagerOutpostConfig> field_214536_b = register("pillager_outpost", new PillagerOutpostStructure(PillagerOutpostConfig::func_214642_a));
   public static final Structure<MineshaftConfig> MINESHAFT = register("mineshaft", new MineshaftStructure(MineshaftConfig::func_214638_a));
   public static final Structure<NoFeatureConfig> WOODLAND_MANSION = register("woodland_mansion", new WoodlandMansionStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<NoFeatureConfig> JUNGLE_PYRAMID = register("jungle_temple", new JunglePyramidStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<NoFeatureConfig> DESERT_PYRAMID = register("desert_pyramid", new DesertPyramidStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<NoFeatureConfig> IGLOO = register("igloo", new IglooStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<ShipwreckConfig> SHIPWRECK = register("shipwreck", new ShipwreckStructure(ShipwreckConfig::func_214658_a));
   public static final SwampHutStructure field_202334_l = register("swamp_hut", new SwampHutStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<NoFeatureConfig> STRONGHOLD = register("stronghold", new StrongholdStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<NoFeatureConfig> OCEAN_MONUMENT = register("ocean_monument", new OceanMonumentStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<OceanRuinConfig> OCEAN_RUIN = register("ocean_ruin", new OceanRuinStructure(OceanRuinConfig::func_214640_a));
   public static final Structure<NoFeatureConfig> FORTRESS = register("nether_bridge", new FortressStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<NoFeatureConfig> BURIED_TREASURE = register("end_city", new EndCityStructure(NoFeatureConfig::func_214639_a));
   public static final Structure<BuriedTreasureConfig> field_214549_o = register("buried_treasure", new BuriedTreasureStructure(BuriedTreasureConfig::func_214684_a));
   public static final Structure<VillageConfig> field_214550_p = register("village", new VillageStructure(VillageConfig::func_214679_a));
   public static final Feature<NoFeatureConfig> field_202339_q = register("fancy_tree", new BigTreeFeature(NoFeatureConfig::func_214639_a, false));
   public static final Feature<NoFeatureConfig> field_202340_r = register("birch_tree", new BirchTreeFeature(NoFeatureConfig::func_214639_a, false, false));
   public static final Feature<NoFeatureConfig> field_202341_s = register("super_birch_tree", new BirchTreeFeature(NoFeatureConfig::func_214639_a, false, true));
   public static final Feature<NoFeatureConfig> field_202342_t = register("jungle_ground_bush", new ShrubFeature(NoFeatureConfig::func_214639_a, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState()));
   public static final Feature<NoFeatureConfig> field_202343_u = register("jungle_tree", new JungleTreeFeature(NoFeatureConfig::func_214639_a, false, 4, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.JUNGLE_LEAVES.getDefaultState(), true));
   public static final Feature<NoFeatureConfig> field_202344_v = register("pine_tree", new PointyTaigaTreeFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> field_214551_w = register("dark_oak_tree", new DarkOakTreeFeature(NoFeatureConfig::func_214639_a, false));
   public static final Feature<NoFeatureConfig> field_202346_x = register("savanna_tree", new SavannaTreeFeature(NoFeatureConfig::func_214639_a, false));
   public static final Feature<NoFeatureConfig> field_202347_y = register("spruce_tree", new TallTaigaTreeFeature(NoFeatureConfig::func_214639_a, false));
   public static final Feature<NoFeatureConfig> field_202348_z = register("swamp_tree", new SwampTreeFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> field_202301_A = register("normal_tree", new TreeFeature(NoFeatureConfig::func_214639_a, false));
   public static final Feature<NoFeatureConfig> field_202302_B = register("mega_jungle_tree", new MegaJungleFeature(NoFeatureConfig::func_214639_a, false, 10, 20, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.JUNGLE_LEAVES.getDefaultState()));
   public static final Feature<NoFeatureConfig> field_202303_C = register("mega_pine_tree", new MegaPineTree(NoFeatureConfig::func_214639_a, false, false));
   public static final Feature<NoFeatureConfig> field_202304_D = register("mega_spruce_tree", new MegaPineTree(NoFeatureConfig::func_214639_a, false, true));
   public static final FlowersFeature field_202305_E = register("default_flower", new DefaultFlowersFeature(NoFeatureConfig::func_214639_a));
   public static final FlowersFeature field_202306_F = register("forest_flower", new ForestFlowersFeature(NoFeatureConfig::func_214639_a));
   public static final FlowersFeature field_202307_G = register("plain_flower", new PlainsFlowersFeature(NoFeatureConfig::func_214639_a));
   public static final FlowersFeature field_202308_H = register("swamp_flower", new SwampFlowersFeature(NoFeatureConfig::func_214639_a));
   public static final FlowersFeature field_214519_I = register("general_forest_flower", new GeneralForestFlowerFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> JUNGLE_GRASS = register("jungle_grass", new JungleGrassFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> TAIGA_GRASS = register("taiga_grass", new TaigaGrassFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<GrassFeatureConfig> field_214520_L = register("grass", new GrassFeature(GrassFeatureConfig::func_214707_a));
   public static final Feature<NoFeatureConfig> VOID_START_PLATFORM = register("void_start_platform", new VoidStartPlatformFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> CACTUS = register("cactus", new CactusFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> DEAD_BUSH = register("dead_bush", new DeadBushFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> DESERT_WELLS = register("desert_well", new DesertWellsFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> FOSSILS = register("fossil", new FossilsFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> FIRE = register("hell_fire", new FireFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<BigMushroomFeatureConfig> BIG_RED_MUSHROOM = register("huge_red_mushroom", new BigRedMushroomFeature(BigMushroomFeatureConfig::func_222853_a));
   public static final Feature<BigMushroomFeatureConfig> BIG_BROWN_MUSHROOM = register("huge_brown_mushroom", new BigBrownMushroomFeature(BigMushroomFeatureConfig::func_222853_a));
   public static final Feature<NoFeatureConfig> ICE_SPIKE = register("ice_spike", new IceSpikeFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> GLOWSTONE = register("glowstone_blob", new GlowstoneBlobFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> MELON = register("melon", new MelonFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> PUMPKIN = register("pumpkin", new ScatteredPlantFeature(NoFeatureConfig::func_214639_a, Blocks.PUMPKIN.getDefaultState()));
   public static final Feature<NoFeatureConfig> REED = register("reed", new ReedFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> ICE_AND_SNOW = register("freeze_top_layer", new IceAndSnowFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> VINES = register("vines", new VinesFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> WATERLILY = register("waterlily", new WaterlilyFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> DUNGEONS = register("monster_room", new DungeonsFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> BLUE_ICE = register("blue_ice", new BlueIceFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<IcebergConfig> ICEBERG = register("iceberg", new IcebergFeature(IcebergConfig::func_214710_a));
   public static final Feature<BlockBlobConfig> BLOCK_BLOB = register("forest_rock", new BlockBlobFeature(BlockBlobConfig::func_214682_a));
   public static final Feature<NoFeatureConfig> field_214495_ag = register("hay_pile", new HayBlockPileFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> field_214496_ah = register("snow_pile", new SnowBlockPileFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> field_214497_ai = register("ice_pile", new IceBlockPileFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> field_214498_aj = register("melon_pile", new MelonBlockPileFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> field_214499_ak = register("pumpkin_pile", new PumpkinBlockPileFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<BushConfig> BUSH = register("bush", new BushFeature(BushConfig::func_214685_a));
   public static final Feature<SphereReplaceConfig> SPHERE_REPLACE = register("disk", new SphereReplaceFeature(SphereReplaceConfig::func_214691_a));
   public static final Feature<DoublePlantConfig> DOUBLE_PLANT = register("double_plant", new DoublePlantFeature(DoublePlantConfig::func_214694_a));
   public static final Feature<HellLavaConfig> field_214503_ao = register("nether_spring", new NetherSpringFeature(HellLavaConfig::func_214709_a));
   public static final Feature<FeatureRadiusConfig> ICE_PATH = register("ice_patch", new IcePathFeature(FeatureRadiusConfig::func_214706_a));
   public static final Feature<LakesConfig> LAKES = register("lake", new LakesFeature(LakesConfig::func_214712_a));
   public static final Feature<OreFeatureConfig> MINABLE = register("ore", new OreFeature(OreFeatureConfig::func_214641_a));
   public static final Feature<MultipleWithChanceRandomFeatureConfig> DEFAULT_RANDOM_FEATURE_LIST = register("random_random_selector", new MultipleRandomFeature(MultipleWithChanceRandomFeatureConfig::func_214653_a));
   public static final Feature<MultipleRandomFeatureConfig> RANDOM_FEATURE_LIST = register("random_selector", new MultipleWithChanceRandomFeature(MultipleRandomFeatureConfig::func_214648_a));
   public static final Feature<SingleRandomFeature> RANDOM_FEATURE_WITH_CONFIG = register("simple_random_selector", new SingleRandomFeatureConfig(SingleRandomFeature::func_214664_a));
   public static final Feature<TwoFeatureChoiceConfig> TWO_FEATURE_CHOICE = register("random_boolean_selector", new TwoFeatureChoiceFeature(TwoFeatureChoiceConfig::func_214647_a));
   public static final Feature<ReplaceBlockConfig> REPLACE_BLOCK = register("emerald_ore", new ReplaceBlockFeature(ReplaceBlockConfig::func_214657_a));
   public static final Feature<LiquidsConfig> LIQUIDS = register("spring_feature", new SpringFeature(LiquidsConfig::func_214677_a));
   public static final Feature<EndSpikeFeatureConfig> END_CRYSTAL_TOWER = register("end_spike", new EndSpikeFeature(EndSpikeFeatureConfig::func_214673_a));
   public static final Feature<NoFeatureConfig> END_ISLAND = register("end_island", new EndIslandFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> CHORUS_PLANT = register("chorus_plant", new ChorusPlantFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<EndGatewayConfig> END_GATEWAY = register("end_gateway", new EndGatewayFeature(EndGatewayConfig::func_214697_a));
   public static final Feature<SeaGrassConfig> SEA_GRASS = register("seagrass", new SeaGrassFeature(SeaGrassConfig::func_214659_a));
   public static final Feature<NoFeatureConfig> KELP = register("kelp", new KelpFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> CORAL_TREE = register("coral_tree", new CoralTreeFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> CORAL_MUSHROOM = register("coral_mushroom", new CoralMushroomFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<NoFeatureConfig> CORAL_CLAW = register("coral_claw", new CoralClawFeature(NoFeatureConfig::func_214639_a));
   public static final Feature<CountConfig> SEA_PICKLE = register("sea_pickle", new SeaPickleFeature(CountConfig::func_214687_a));
   public static final Feature<BlockWithContextConfig> BLOCK_WITH_CONTEXT = register("simple_block", new BlockWithContextFeature(BlockWithContextConfig::func_214663_a));
   public static final Feature<ProbabilityConfig> field_214482_aJ = register("bamboo", new BambooFeature(ProbabilityConfig::func_214645_a));
   public static final Feature<DecoratedFeatureConfig> field_214483_aK = register("decorated", new DecoratedFeature(DecoratedFeatureConfig::func_214688_a));
   public static final Feature<DecoratedFeatureConfig> field_214484_aL = register("decorated_flower", new DecoratedFlowerFeature(DecoratedFeatureConfig::func_214688_a));
   public static final Feature<NoFeatureConfig> field_214485_aM = register("sweet_berry_bush", new ScatteredPlantFeature(NoFeatureConfig::func_214639_a, Blocks.field_222434_lW.getDefaultState().with(SweetBerryBushBlock.field_220125_a, Integer.valueOf(3))));
   public static final Feature<FillLayerConfig> field_214486_aN = register("fill_layer", new FillLayerFeature(FillLayerConfig::func_214635_a));
   public static final BonusChestFeature field_214487_aO = register("bonus_chest", new BonusChestFeature(NoFeatureConfig::func_214639_a));
   public static final BiMap<String, Structure<?>> field_202300_at = Util.make(HashBiMap.create(), (p_205170_0_) -> {
      p_205170_0_.put("Pillager_Outpost".toLowerCase(Locale.ROOT), field_214536_b);
      p_205170_0_.put("Mineshaft".toLowerCase(Locale.ROOT), MINESHAFT);
      p_205170_0_.put("Mansion".toLowerCase(Locale.ROOT), WOODLAND_MANSION);
      p_205170_0_.put("Jungle_Pyramid".toLowerCase(Locale.ROOT), JUNGLE_PYRAMID);
      p_205170_0_.put("Desert_Pyramid".toLowerCase(Locale.ROOT), DESERT_PYRAMID);
      p_205170_0_.put("Igloo".toLowerCase(Locale.ROOT), IGLOO);
      p_205170_0_.put("Shipwreck".toLowerCase(Locale.ROOT), SHIPWRECK);
      p_205170_0_.put("Swamp_Hut".toLowerCase(Locale.ROOT), field_202334_l);
      p_205170_0_.put("Stronghold".toLowerCase(Locale.ROOT), STRONGHOLD);
      p_205170_0_.put("Monument".toLowerCase(Locale.ROOT), OCEAN_MONUMENT);
      p_205170_0_.put("Ocean_Ruin".toLowerCase(Locale.ROOT), OCEAN_RUIN);
      p_205170_0_.put("Fortress".toLowerCase(Locale.ROOT), FORTRESS);
      p_205170_0_.put("EndCity".toLowerCase(Locale.ROOT), BURIED_TREASURE);
      p_205170_0_.put("Buried_Treasure".toLowerCase(Locale.ROOT), field_214549_o);
      p_205170_0_.put("Village".toLowerCase(Locale.ROOT), field_214550_p);
   });
   public static final List<Structure<?>> field_214488_aQ = ImmutableList.of(field_214536_b, field_214550_p);
   private final Function<Dynamic<?>, ? extends FC> configFactory;
   protected final boolean doBlockNotify;

   private static <C extends IFeatureConfig, F extends Feature<C>> F register(String p_214468_0_, F p_214468_1_) {
      return (F)(Registry.<Feature<?>>register(Registry.field_218379_q, p_214468_0_, p_214468_1_));
   }

   public Feature(Function<Dynamic<?>, ? extends FC> p_i49878_1_) {
      this.configFactory = p_i49878_1_;
      this.doBlockNotify = false;
   }

   public Feature(Function<Dynamic<?>, ? extends FC> p_i49879_1_, boolean p_i49879_2_) {
      this.configFactory = p_i49879_1_;
      this.doBlockNotify = p_i49879_2_;
   }

   public FC createConfig(Dynamic<?> p_214470_1_) {
      return (FC)(this.configFactory.apply(p_214470_1_));
   }

   protected void setBlockState(IWorldWriter worldIn, BlockPos pos, BlockState state) {
      if (this.doBlockNotify) {
         worldIn.setBlockState(pos, state, 3);
      } else {
         worldIn.setBlockState(pos, state, 2);
      }

   }

   public abstract boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, FC config);

   public List<Biome.SpawnListEntry> getSpawnList() {
      return Collections.emptyList();
   }

   public List<Biome.SpawnListEntry> getCreatureSpawnList() {
      return Collections.emptyList();
   }
}