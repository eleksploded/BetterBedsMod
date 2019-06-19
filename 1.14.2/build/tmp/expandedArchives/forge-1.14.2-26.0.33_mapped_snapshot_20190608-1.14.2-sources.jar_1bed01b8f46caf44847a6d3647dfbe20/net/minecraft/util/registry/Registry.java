package net.minecraft.util.registry;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.IVillagerType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.jigsaw.IJigsawDeserializer;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.Structures;
import net.minecraft.world.gen.feature.template.IRuleTestType;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Attention Modders: This SHOULD NOT be used, you should use ForgeRegistries instead. As it has a cleaner modder facing API.
 * We will be wrapping all of these in our API as nessasary for syncing and management.
 */
public abstract class Registry<T> implements IObjectIntIterable<T> {
   protected static final Logger LOGGER = LogManager.getLogger();
   private static final Map<ResourceLocation, Supplier<?>> field_218376_a = Maps.newLinkedHashMap();
   public static final MutableRegistry<MutableRegistry<?>> field_212617_f = new SimpleRegistry<>();
   @Deprecated public static final Registry<SoundEvent> field_212633_v = forge("sound_event", SoundEvent.class, () -> {
      return SoundEvents.ENTITY_ITEM_PICKUP;
   });
   @Deprecated public static final DefaultedRegistry<Fluid> field_212619_h = forgeDefaulted("fluid", Fluid.class, () -> {
      return Fluids.EMPTY;
   });
   @Deprecated public static final Registry<Effect> field_212631_t = forge("mob_effect", Effect.class, () -> {
      return Effects.field_188425_z;
   });
   @Deprecated public static final DefaultedRegistry<Block> field_212618_g = forgeDefaulted("block", Block.class, () -> {
      return Blocks.AIR;
   });
   @Deprecated public static final Registry<Enchantment> field_212628_q = forge("enchantment", Enchantment.class, () -> {
      return Enchantments.FORTUNE;
   });
   @Deprecated public static final DefaultedRegistry<EntityType<?>> field_212629_r = forgeDefaulted("entity_type", EntityType.class, () -> {
      return EntityType.PIG;
   });
   @Deprecated public static final DefaultedRegistry<Item> field_212630_s = forgeDefaulted("item", Item.class, () -> {
      return Items.AIR;
   });
   @Deprecated public static final DefaultedRegistry<Potion> field_212621_j = forgeDefaulted("potion", Potion.class, ()  -> {
      return Potions.field_185229_a;
   });
   @Deprecated public static final Registry<WorldCarver<?>> field_218377_o = forge("carver", WorldCarver.class, () -> {
      return WorldCarver.field_222709_a;
   });
   @Deprecated public static final Registry<SurfaceBuilder<?>> field_218378_p = forge("surface_builder", SurfaceBuilder.class, () -> {
      return SurfaceBuilder.field_215396_G;
   });
   @Deprecated public static final Registry<Feature<?>> field_218379_q = forge("feature", Feature.class, () -> {
      return Feature.MINABLE;
   });
   @Deprecated public static final Registry<Placement<?>> field_218380_r = forge("decorator", Placement.class, () -> {
      return Placement.field_215022_h;
   });
   @Deprecated public static final Registry<Biome> field_212624_m = forge("biome", Biome.class, () -> {
      return Biomes.DEFAULT;
   });
   @Deprecated public static final Registry<ParticleType<? extends IParticleData>> field_212632_u = forge("particle_type", ParticleType.class, () -> {
      return ParticleTypes.BLOCK;
   });
   @Deprecated public static final Registry<BiomeProviderType<?, ?>> field_212625_n = forge("biome_source_type", BiomeProviderType.class, () -> {
      return BiomeProviderType.VANILLA_LAYERED;
   });
   @Deprecated public static final Registry<TileEntityType<?>> field_212626_o = forge("block_entity_type", TileEntityType.class, () -> {
      return TileEntityType.FURNACE;
   });
   @Deprecated public static final Registry<ChunkGeneratorType<?, ?>> field_212627_p = forge("chunk_generator_type", ChunkGeneratorType.class, () -> {
      return ChunkGeneratorType.FLAT;
   });
   public static final Registry<DimensionType> field_212622_k = func_222939_a("dimension_type", net.minecraftforge.common.DimensionManager.getRegistry(), () -> {
      return DimensionType.OVERWORLD;
   });
   @Deprecated public static final DefaultedRegistry<PaintingType> field_212620_i = forgeDefaulted("motive", PaintingType.class, () -> {
      return PaintingType.KEBAB;
   });
   public static final Registry<ResourceLocation> field_212623_l = func_222935_a("custom_stat", () -> {
      return Stats.JUMP;
   });
   @Deprecated public static final DefaultedRegistry<ChunkStatus> field_218360_A = forgeDefaulted("chunk_status", ChunkStatus.class, () -> {
      return ChunkStatus.EMPTY;
   });
   public static final Registry<Structure<?>> field_218361_B = func_222935_a("structure_feature", () -> {
      return Structures.field_215143_a;
   });
   public static final Registry<IStructurePieceType> field_218362_C = func_222935_a("structure_piece", () -> {
      return IStructurePieceType.field_214782_c;
   });
   public static final Registry<IRuleTestType> field_218363_D = func_222935_a("rule_test", () -> {
      return IRuleTestType.field_214911_b;
   });
   public static final Registry<IStructureProcessorType> field_218364_E = func_222935_a("structure_processor", () -> {
      return IStructureProcessorType.field_214920_b;
   });
   public static final Registry<IJigsawDeserializer> field_218365_F = func_222935_a("structure_pool_element", () -> {
      return IJigsawDeserializer.field_214931_e;
   });
   @Deprecated public static final Registry<ContainerType<?>> field_218366_G = forge("menu", ContainerType.class, () -> {
      return ContainerType.field_221514_h;
   });
   public static final Registry<IRecipeType<?>> field_218367_H = func_222935_a("recipe_type", () -> {
      return IRecipeType.field_222149_a;
   });
   @Deprecated public static final Registry<IRecipeSerializer<?>> field_218368_I = forge("recipe_serializer", IRecipeSerializer.class, () -> {
      return IRecipeSerializer.field_222158_b;
   });
   @Deprecated public static final Registry<StatType<?>> field_212634_w = forge("stat_type", StatType.class, () -> {
      return Stats.ITEM_USED;
   });
   public static final DefaultedRegistry<IVillagerType> field_218369_K = func_222933_a("villager_type", "plains", () -> {
      return IVillagerType.field_221175_c;
   });
   @Deprecated public static final DefaultedRegistry<VillagerProfession> field_218370_L = forgeDefaulted("villager_profession", VillagerProfession.class, () -> {
      return VillagerProfession.field_221151_a;
   });
   @Deprecated public static final DefaultedRegistry<PointOfInterestType> field_218371_M = forgeDefaulted("point_of_interest_type", PointOfInterestType.class, () -> {
      return PointOfInterestType.field_221054_b;
   });
   @Deprecated public static final DefaultedRegistry<MemoryModuleType<?>> field_218372_N = forgeDefaulted("memory_module_type", MemoryModuleType.class, () -> {
      return MemoryModuleType.field_220940_a;
   });
   @Deprecated public static final DefaultedRegistry<SensorType<?>> field_218373_O = forgeDefaulted("sensor_type", SensorType.class, () -> {
      return SensorType.field_220997_a;
   });
   @Deprecated public static final Registry<Schedule> field_218374_P = forge("schedule", Schedule.class, () -> {
      return Schedule.field_221383_a;
   });
   @Deprecated public static final Registry<Activity> field_218375_Q = forge("activity", Activity.class, () -> {
      return Activity.IDLE;
   });

   private static <T> Registry<T> func_222935_a(String p_222935_0_, Supplier<T> p_222935_1_) {
      return func_222939_a(p_222935_0_, new SimpleRegistry<>(), p_222935_1_);
   }

   private static <T> DefaultedRegistry<T> func_222933_a(String p_222933_0_, String p_222933_1_, Supplier<T> p_222933_2_) {
      return func_222939_a(p_222933_0_, new DefaultedRegistry<>(p_222933_1_), p_222933_2_);
   }

   private static <T, R extends MutableRegistry<T>> R func_222939_a(String p_222939_0_, R p_222939_1_, Supplier<T> p_222939_2_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_222939_0_);
      field_218376_a.put(resourcelocation, p_222939_2_);
      return (R)(field_212617_f.register(resourcelocation, p_222939_1_));
   }

   /**
    * Gets the name we use to identify the given object.
    */
   @Nullable
   public abstract ResourceLocation getKey(T value);

   /**
    * Gets the integer ID we use to identify the given object.
    */
   public abstract int getId(@Nullable T value);

   @Nullable
   public abstract T getOrDefault(@Nullable ResourceLocation name);

   public abstract Optional<T> func_218349_b(@Nullable ResourceLocation p_218349_1_);

   /**
    * Gets all the keys recognized by this registry.
    */
   public abstract Set<ResourceLocation> keySet();

   @Nullable
   public abstract T getRandom(Random random);

   public Stream<T> stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   @OnlyIn(Dist.CLIENT)
   public abstract boolean containsKey(ResourceLocation name);

   public static <T> T register(Registry<? super T> p_218325_0_, String p_218325_1_, T p_218325_2_) {
      return register(p_218325_0_, new ResourceLocation(p_218325_1_), p_218325_2_);
   }

   public static <T> T register(Registry<? super T> p_218322_0_, ResourceLocation p_218322_1_, T p_218322_2_) {
      return ((MutableRegistry<T>)p_218322_0_).register(p_218322_1_, p_218322_2_);
   }

   public static <T> T register(Registry<? super T> p_218343_0_, int p_218343_1_, String p_218343_2_, T p_218343_3_) {
      return ((MutableRegistry<T>)p_218343_0_).register(p_218343_1_, new ResourceLocation(p_218343_2_), p_218343_3_);
   }

   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> Registry<T> forge(String name, Class<? super T> cls, Supplier<T> def) {
      return func_222939_a(name, net.minecraftforge.registries.GameData.<T>getWrapper(cls), def);
   }
   
   private static <T extends net.minecraftforge.registries.IForgeRegistryEntry<T>> DefaultedRegistry<T> forgeDefaulted(String name, Class<? super T> cls, Supplier<T> def) {
      return Registry.<T, DefaultedRegistry<T>>func_222939_a(name, net.minecraftforge.registries.GameData.<T>getWrapperDefaulted(cls), def);
   }

   static {
      field_218376_a.entrySet().forEach((p_218326_0_) -> {
         if (p_218326_0_.getValue().get() == null) {
            LOGGER.error("Unable to bootstrap registry '{}'", p_218326_0_.getKey());
         }

      });
      field_212617_f.forEach((p_218324_0_) -> {
         if (p_218324_0_.isEmpty()) {
            LOGGER.error("Registry '{}' was empty after loading", (Object)field_212617_f.getKey(p_218324_0_));
            if (SharedConstants.developmentMode) {
               throw new IllegalStateException("Registry: '" + field_212617_f.getKey(p_218324_0_) + "' is empty, not allowed, fix me!");
            }
         }

         if (p_218324_0_ instanceof DefaultedRegistry) {
            ResourceLocation resourcelocation = ((DefaultedRegistry)p_218324_0_).getDefaultKey();
            Validate.notNull(p_218324_0_.getOrDefault(resourcelocation), "Missing default of DefaultedMappedRegistry: " + resourcelocation);
         }

      });
   }
}