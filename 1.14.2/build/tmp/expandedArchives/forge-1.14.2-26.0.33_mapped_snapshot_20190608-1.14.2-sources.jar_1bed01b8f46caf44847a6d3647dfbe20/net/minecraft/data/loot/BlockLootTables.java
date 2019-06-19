package net.minecraft.data.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BeetrootBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarrotBlock;
import net.minecraft.block.CocoaBlock;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.block.PotatoBlock;
import net.minecraft.block.SeaPickleBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.TNTBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.BedPart;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.storage.loot.AlternativesLootEntry;
import net.minecraft.world.storage.loot.BinomialRange;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.DynamicLootEntry;
import net.minecraft.world.storage.loot.ILootConditionConsumer;
import net.minecraft.world.storage.loot.ILootFunctionConsumer;
import net.minecraft.world.storage.loot.IRandomRange;
import net.minecraft.world.storage.loot.IntClamper;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.StandaloneLootEntry;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.EntityHasProperty;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.conditions.TableBonus;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.CopyName;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.LimitCount;
import net.minecraft.world.storage.loot.functions.SetContents;
import net.minecraft.world.storage.loot.functions.SetCount;

public class BlockLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   private static final ILootCondition.IBuilder field_218573_a = MatchTool.builder(ItemPredicate.Builder.create().func_218003_a(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
   private static final ILootCondition.IBuilder field_218574_b = field_218573_a.inverted();
   private static final ILootCondition.IBuilder field_218575_c = MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS));
   private static final ILootCondition.IBuilder field_218576_d = field_218575_c.alternative(field_218573_a);
   private static final ILootCondition.IBuilder field_218577_e = field_218576_d.inverted();
   private static final Set<Item> field_218578_f = Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(IItemProvider::asItem).collect(ImmutableSet.toImmutableSet());
   private static final float[] field_218579_g = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
   private static final float[] field_218580_h = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
   private final Map<ResourceLocation, LootTable.Builder> field_218581_i = Maps.newHashMap();

   private static <T> T func_218552_a(IItemProvider p_218552_0_, ILootFunctionConsumer<T> p_218552_1_) {
      return (T)(!field_218578_f.contains(p_218552_0_.asItem()) ? p_218552_1_.func_212841_b_(ExplosionDecay.func_215863_b()) : p_218552_1_.cast());
   }

   private static <T> T func_218560_a(IItemProvider p_218560_0_, ILootConditionConsumer<T> p_218560_1_) {
      return (T)(!field_218578_f.contains(p_218560_0_.asItem()) ? p_218560_1_.acceptCondition(SurvivesExplosion.builder()) : p_218560_1_.cast());
   }

   private static LootTable.Builder func_218546_a(IItemProvider p_218546_0_) {
      return LootTable.func_216119_b().func_216040_a(func_218560_a(p_218546_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218546_0_))));
   }

   private static LootTable.Builder func_218494_a(Block p_218494_0_, ILootCondition.IBuilder p_218494_1_, LootEntry.Builder<?> p_218494_2_) {
      return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(p_218494_0_).acceptCondition(p_218494_1_)).func_216080_a(p_218494_2_)));
   }

   private static LootTable.Builder func_218519_a(Block p_218519_0_, LootEntry.Builder<?> p_218519_1_) {
      return func_218494_a(p_218519_0_, field_218573_a, p_218519_1_);
   }

   private static LootTable.Builder func_218511_b(Block p_218511_0_, LootEntry.Builder<?> p_218511_1_) {
      return func_218494_a(p_218511_0_, field_218575_c, p_218511_1_);
   }

   private static LootTable.Builder func_218535_c(Block p_218535_0_, LootEntry.Builder<?> p_218535_1_) {
      return func_218494_a(p_218535_0_, field_218576_d, p_218535_1_);
   }

   private static LootTable.Builder func_218515_b(Block p_218515_0_, IItemProvider p_218515_1_) {
      return func_218519_a(p_218515_0_, func_218560_a(p_218515_0_, ItemLootEntry.func_216168_a(p_218515_1_)));
   }

   private static LootTable.Builder func_218463_a(IItemProvider p_218463_0_, IRandomRange p_218463_1_) {
      return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(func_218552_a(p_218463_0_, ItemLootEntry.func_216168_a(p_218463_0_).func_212841_b_(SetCount.func_215932_a(p_218463_1_)))));
   }

   private static LootTable.Builder func_218530_a(Block p_218530_0_, IItemProvider p_218530_1_, IRandomRange p_218530_2_) {
      return func_218519_a(p_218530_0_, func_218552_a(p_218530_0_, ItemLootEntry.func_216168_a(p_218530_1_).func_212841_b_(SetCount.func_215932_a(p_218530_2_))));
   }

   private static LootTable.Builder func_218561_b(IItemProvider p_218561_0_) {
      return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().acceptCondition(field_218573_a).func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218561_0_)));
   }

   private static LootTable.Builder func_218523_c(IItemProvider p_218523_0_) {
      return LootTable.func_216119_b().func_216040_a(func_218560_a(Blocks.FLOWER_POT, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Blocks.FLOWER_POT)))).func_216040_a(func_218560_a(p_218523_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218523_0_))));
   }

   private static LootTable.Builder func_218513_d(Block p_218513_0_) {
      return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(func_218552_a(p_218513_0_, ItemLootEntry.func_216168_a(p_218513_0_).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(2)).acceptCondition(BlockStateProperty.builder(p_218513_0_).with(SlabBlock.TYPE, SlabType.DOUBLE))))));
   }

   private static <T extends Comparable<T>> LootTable.Builder func_218562_a(Block p_218562_0_, IProperty<T> p_218562_1_, T p_218562_2_) {
      return LootTable.func_216119_b().func_216040_a(func_218560_a(p_218562_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218562_0_).acceptCondition(BlockStateProperty.builder(p_218562_0_).with(p_218562_1_, p_218562_2_)))));
   }

   private static LootTable.Builder func_218481_e(Block p_218481_0_) {
      return LootTable.func_216119_b().func_216040_a(func_218560_a(p_218481_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218481_0_).func_212841_b_(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)))));
   }

   private static LootTable.Builder func_218544_f(Block p_218544_0_) {
      return LootTable.func_216119_b().func_216040_a(func_218560_a(p_218544_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218544_0_).func_212841_b_(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)).func_212841_b_(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Lock", "BlockEntityTag.Lock").func_216056_a("LootTable", "BlockEntityTag.LootTable").func_216056_a("LootTableSeed", "BlockEntityTag.LootTableSeed")).func_212841_b_(SetContents.func_215920_b().func_216075_a(DynamicLootEntry.func_216162_a(ShulkerBoxBlock.field_220169_b))))));
   }

   private static LootTable.Builder func_218559_g(Block p_218559_0_) {
      return LootTable.func_216119_b().func_216040_a(func_218560_a(p_218559_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218559_0_).func_212841_b_(CopyName.func_215893_a(CopyName.Source.BLOCK_ENTITY)).func_212841_b_(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Patterns", "BlockEntityTag.Patterns")))));
   }

   private static LootTable.Builder func_218476_a(Block p_218476_0_, Item p_218476_1_) {
      return func_218519_a(p_218476_0_, func_218552_a(p_218476_0_, ItemLootEntry.func_216168_a(p_218476_1_).func_212841_b_(ApplyBonus.func_215869_a(Enchantments.FORTUNE))));
   }

   private static LootTable.Builder func_218491_c(Block p_218491_0_, IItemProvider p_218491_1_) {
      return func_218519_a(p_218491_0_, func_218552_a(p_218491_0_, ItemLootEntry.func_216168_a(p_218491_1_).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(-6.0F, 2.0F))).func_212841_b_(LimitCount.func_215911_a(IntClamper.func_215848_a(0)))));
   }

   private static LootTable.Builder func_218570_h(Block p_218570_0_) {
      return func_218511_b(p_218570_0_, func_218552_a(p_218570_0_, (ItemLootEntry.func_216168_a(Items.WHEAT_SEEDS).acceptCondition(RandomChance.builder(0.125F))).func_212841_b_(ApplyBonus.func_215865_a(Enchantments.FORTUNE, 2))));
   }

   private static LootTable.Builder func_218475_b(Block p_218475_0_, Item p_218475_1_) {
      return LootTable.func_216119_b().func_216040_a(func_218552_a(p_218475_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218475_1_).func_212841_b_(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.06666667F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).with(StemBlock.AGE, 0))).func_212841_b_(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.13333334F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).with(StemBlock.AGE, 1))).func_212841_b_(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.2F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).with(StemBlock.AGE, 2))).func_212841_b_(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.26666668F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).with(StemBlock.AGE, 3))).func_212841_b_(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.33333334F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).with(StemBlock.AGE, 4))).func_212841_b_(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.4F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).with(StemBlock.AGE, 5))).func_212841_b_(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.46666667F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).with(StemBlock.AGE, 6))).func_212841_b_(SetCount.func_215932_a(BinomialRange.func_215838_a(3, 0.53333336F)).acceptCondition(BlockStateProperty.builder(p_218475_0_).with(StemBlock.AGE, 7))))));
   }

   private static LootTable.Builder func_218486_d(IItemProvider p_218486_0_) {
      return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).acceptCondition(field_218575_c).func_216045_a(ItemLootEntry.func_216168_a(p_218486_0_)));
   }

   private static LootTable.Builder func_218540_a(Block p_218540_0_, Block p_218540_1_, float... p_218540_2_) {
      return func_218535_c(p_218540_0_, func_218560_a(p_218540_0_, ItemLootEntry.func_216168_a(p_218540_1_)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, p_218540_2_))).func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).acceptCondition(field_218577_e).func_216045_a(func_218552_a(p_218540_0_, ItemLootEntry.func_216168_a(Items.STICK).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F)))).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F))));
   }

   private static LootTable.Builder func_218526_b(Block p_218526_0_, Block p_218526_1_, float... p_218526_2_) {
      return func_218540_a(p_218526_0_, p_218526_1_, p_218526_2_).func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).acceptCondition(field_218577_e).func_216045_a(func_218560_a(p_218526_0_, ItemLootEntry.func_216168_a(Items.APPLE)).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
   }

   private static LootTable.Builder func_218541_a(Block p_218541_0_, Item p_218541_1_, Item p_218541_2_, ILootCondition.IBuilder p_218541_3_) {
      return func_218552_a(p_218541_0_, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216045_a(((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(p_218541_1_).acceptCondition(p_218541_3_)).func_216080_a(ItemLootEntry.func_216168_a(p_218541_2_)))).func_216040_a(LootPool.func_216096_a().acceptCondition(p_218541_3_).func_216045_a(ItemLootEntry.func_216168_a(p_218541_2_).func_212841_b_(ApplyBonus.func_215870_a(Enchantments.FORTUNE, 0.5714286F, 3)))));
   }

   public static LootTable.Builder func_218482_a() {
      return LootTable.func_216119_b();
   }

   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
      this.func_218492_c(Blocks.GRANITE);
      this.func_218492_c(Blocks.POLISHED_GRANITE);
      this.func_218492_c(Blocks.DIORITE);
      this.func_218492_c(Blocks.POLISHED_DIORITE);
      this.func_218492_c(Blocks.ANDESITE);
      this.func_218492_c(Blocks.POLISHED_ANDESITE);
      this.func_218492_c(Blocks.DIRT);
      this.func_218492_c(Blocks.COARSE_DIRT);
      this.func_218492_c(Blocks.COBBLESTONE);
      this.func_218492_c(Blocks.OAK_PLANKS);
      this.func_218492_c(Blocks.SPRUCE_PLANKS);
      this.func_218492_c(Blocks.BIRCH_PLANKS);
      this.func_218492_c(Blocks.JUNGLE_PLANKS);
      this.func_218492_c(Blocks.ACACIA_PLANKS);
      this.func_218492_c(Blocks.DARK_OAK_PLANKS);
      this.func_218492_c(Blocks.OAK_SAPLING);
      this.func_218492_c(Blocks.SPRUCE_SAPLING);
      this.func_218492_c(Blocks.BIRCH_SAPLING);
      this.func_218492_c(Blocks.JUNGLE_SAPLING);
      this.func_218492_c(Blocks.ACACIA_SAPLING);
      this.func_218492_c(Blocks.DARK_OAK_SAPLING);
      this.func_218492_c(Blocks.SAND);
      this.func_218492_c(Blocks.RED_SAND);
      this.func_218492_c(Blocks.GOLD_ORE);
      this.func_218492_c(Blocks.IRON_ORE);
      this.func_218492_c(Blocks.OAK_LOG);
      this.func_218492_c(Blocks.SPRUCE_LOG);
      this.func_218492_c(Blocks.BIRCH_LOG);
      this.func_218492_c(Blocks.JUNGLE_LOG);
      this.func_218492_c(Blocks.ACACIA_LOG);
      this.func_218492_c(Blocks.DARK_OAK_LOG);
      this.func_218492_c(Blocks.STRIPPED_SPRUCE_LOG);
      this.func_218492_c(Blocks.STRIPPED_BIRCH_LOG);
      this.func_218492_c(Blocks.STRIPPED_JUNGLE_LOG);
      this.func_218492_c(Blocks.STRIPPED_ACACIA_LOG);
      this.func_218492_c(Blocks.STRIPPED_DARK_OAK_LOG);
      this.func_218492_c(Blocks.STRIPPED_OAK_LOG);
      this.func_218492_c(Blocks.OAK_WOOD);
      this.func_218492_c(Blocks.SPRUCE_WOOD);
      this.func_218492_c(Blocks.BIRCH_WOOD);
      this.func_218492_c(Blocks.JUNGLE_WOOD);
      this.func_218492_c(Blocks.ACACIA_WOOD);
      this.func_218492_c(Blocks.DARK_OAK_WOOD);
      this.func_218492_c(Blocks.STRIPPED_OAK_WOOD);
      this.func_218492_c(Blocks.STRIPPED_SPRUCE_WOOD);
      this.func_218492_c(Blocks.STRIPPED_BIRCH_WOOD);
      this.func_218492_c(Blocks.STRIPPED_JUNGLE_WOOD);
      this.func_218492_c(Blocks.STRIPPED_ACACIA_WOOD);
      this.func_218492_c(Blocks.STRIPPED_DARK_OAK_WOOD);
      this.func_218492_c(Blocks.SPONGE);
      this.func_218492_c(Blocks.WET_SPONGE);
      this.func_218492_c(Blocks.LAPIS_BLOCK);
      this.func_218492_c(Blocks.SANDSTONE);
      this.func_218492_c(Blocks.CHISELED_SANDSTONE);
      this.func_218492_c(Blocks.CUT_SANDSTONE);
      this.func_218492_c(Blocks.NOTE_BLOCK);
      this.func_218492_c(Blocks.POWERED_RAIL);
      this.func_218492_c(Blocks.DETECTOR_RAIL);
      this.func_218492_c(Blocks.STICKY_PISTON);
      this.func_218492_c(Blocks.PISTON);
      this.func_218492_c(Blocks.WHITE_WOOL);
      this.func_218492_c(Blocks.ORANGE_WOOL);
      this.func_218492_c(Blocks.MAGENTA_WOOL);
      this.func_218492_c(Blocks.LIGHT_BLUE_WOOL);
      this.func_218492_c(Blocks.YELLOW_WOOL);
      this.func_218492_c(Blocks.LIME_WOOL);
      this.func_218492_c(Blocks.PINK_WOOL);
      this.func_218492_c(Blocks.GRAY_WOOL);
      this.func_218492_c(Blocks.LIGHT_GRAY_WOOL);
      this.func_218492_c(Blocks.CYAN_WOOL);
      this.func_218492_c(Blocks.PURPLE_WOOL);
      this.func_218492_c(Blocks.BLUE_WOOL);
      this.func_218492_c(Blocks.BROWN_WOOL);
      this.func_218492_c(Blocks.GREEN_WOOL);
      this.func_218492_c(Blocks.RED_WOOL);
      this.func_218492_c(Blocks.BLACK_WOOL);
      this.func_218492_c(Blocks.DANDELION);
      this.func_218492_c(Blocks.POPPY);
      this.func_218492_c(Blocks.BLUE_ORCHID);
      this.func_218492_c(Blocks.ALLIUM);
      this.func_218492_c(Blocks.AZURE_BLUET);
      this.func_218492_c(Blocks.RED_TULIP);
      this.func_218492_c(Blocks.ORANGE_TULIP);
      this.func_218492_c(Blocks.WHITE_TULIP);
      this.func_218492_c(Blocks.PINK_TULIP);
      this.func_218492_c(Blocks.OXEYE_DAISY);
      this.func_218492_c(Blocks.field_222387_by);
      this.func_218492_c(Blocks.field_222388_bz);
      this.func_218492_c(Blocks.field_222383_bA);
      this.func_218492_c(Blocks.BROWN_MUSHROOM);
      this.func_218492_c(Blocks.RED_MUSHROOM);
      this.func_218492_c(Blocks.GOLD_BLOCK);
      this.func_218492_c(Blocks.IRON_BLOCK);
      this.func_218492_c(Blocks.BRICKS);
      this.func_218492_c(Blocks.MOSSY_COBBLESTONE);
      this.func_218492_c(Blocks.OBSIDIAN);
      this.func_218492_c(Blocks.TORCH);
      this.func_218492_c(Blocks.OAK_STAIRS);
      this.func_218492_c(Blocks.REDSTONE_WIRE);
      this.func_218492_c(Blocks.DIAMOND_BLOCK);
      this.func_218492_c(Blocks.CRAFTING_TABLE);
      this.func_218492_c(Blocks.field_222384_bX);
      this.func_218492_c(Blocks.field_222385_bY);
      this.func_218492_c(Blocks.field_222386_bZ);
      this.func_218492_c(Blocks.field_222389_ca);
      this.func_218492_c(Blocks.field_222390_cb);
      this.func_218492_c(Blocks.field_222391_cc);
      this.func_218492_c(Blocks.LADDER);
      this.func_218492_c(Blocks.RAIL);
      this.func_218492_c(Blocks.COBBLESTONE_STAIRS);
      this.func_218492_c(Blocks.LEVER);
      this.func_218492_c(Blocks.STONE_PRESSURE_PLATE);
      this.func_218492_c(Blocks.OAK_PRESSURE_PLATE);
      this.func_218492_c(Blocks.SPRUCE_PRESSURE_PLATE);
      this.func_218492_c(Blocks.BIRCH_PRESSURE_PLATE);
      this.func_218492_c(Blocks.JUNGLE_PRESSURE_PLATE);
      this.func_218492_c(Blocks.ACACIA_PRESSURE_PLATE);
      this.func_218492_c(Blocks.DARK_OAK_PRESSURE_PLATE);
      this.func_218492_c(Blocks.REDSTONE_TORCH);
      this.func_218492_c(Blocks.STONE_BUTTON);
      this.func_218492_c(Blocks.CACTUS);
      this.func_218492_c(Blocks.SUGAR_CANE);
      this.func_218492_c(Blocks.JUKEBOX);
      this.func_218492_c(Blocks.OAK_FENCE);
      this.func_218492_c(Blocks.PUMPKIN);
      this.func_218492_c(Blocks.NETHERRACK);
      this.func_218492_c(Blocks.SOUL_SAND);
      this.func_218492_c(Blocks.CARVED_PUMPKIN);
      this.func_218492_c(Blocks.JACK_O_LANTERN);
      this.func_218492_c(Blocks.REPEATER);
      this.func_218492_c(Blocks.OAK_TRAPDOOR);
      this.func_218492_c(Blocks.SPRUCE_TRAPDOOR);
      this.func_218492_c(Blocks.BIRCH_TRAPDOOR);
      this.func_218492_c(Blocks.JUNGLE_TRAPDOOR);
      this.func_218492_c(Blocks.ACACIA_TRAPDOOR);
      this.func_218492_c(Blocks.DARK_OAK_TRAPDOOR);
      this.func_218492_c(Blocks.STONE_BRICKS);
      this.func_218492_c(Blocks.MOSSY_STONE_BRICKS);
      this.func_218492_c(Blocks.CRACKED_STONE_BRICKS);
      this.func_218492_c(Blocks.CHISELED_STONE_BRICKS);
      this.func_218492_c(Blocks.IRON_BARS);
      this.func_218492_c(Blocks.OAK_FENCE_GATE);
      this.func_218492_c(Blocks.BRICK_STAIRS);
      this.func_218492_c(Blocks.STONE_BRICK_STAIRS);
      this.func_218492_c(Blocks.LILY_PAD);
      this.func_218492_c(Blocks.NETHER_BRICKS);
      this.func_218492_c(Blocks.NETHER_BRICK_FENCE);
      this.func_218492_c(Blocks.NETHER_BRICK_STAIRS);
      this.func_218492_c(Blocks.CAULDRON);
      this.func_218492_c(Blocks.END_STONE);
      this.func_218492_c(Blocks.REDSTONE_LAMP);
      this.func_218492_c(Blocks.SANDSTONE_STAIRS);
      this.func_218492_c(Blocks.TRIPWIRE_HOOK);
      this.func_218492_c(Blocks.EMERALD_BLOCK);
      this.func_218492_c(Blocks.SPRUCE_STAIRS);
      this.func_218492_c(Blocks.BIRCH_STAIRS);
      this.func_218492_c(Blocks.JUNGLE_STAIRS);
      this.func_218492_c(Blocks.COBBLESTONE_WALL);
      this.func_218492_c(Blocks.MOSSY_COBBLESTONE_WALL);
      this.func_218492_c(Blocks.FLOWER_POT);
      this.func_218492_c(Blocks.OAK_BUTTON);
      this.func_218492_c(Blocks.SPRUCE_BUTTON);
      this.func_218492_c(Blocks.BIRCH_BUTTON);
      this.func_218492_c(Blocks.JUNGLE_BUTTON);
      this.func_218492_c(Blocks.ACACIA_BUTTON);
      this.func_218492_c(Blocks.DARK_OAK_BUTTON);
      this.func_218492_c(Blocks.SKELETON_SKULL);
      this.func_218492_c(Blocks.WITHER_SKELETON_SKULL);
      this.func_218492_c(Blocks.ZOMBIE_HEAD);
      this.func_218492_c(Blocks.CREEPER_HEAD);
      this.func_218492_c(Blocks.DRAGON_HEAD);
      this.func_218492_c(Blocks.ANVIL);
      this.func_218492_c(Blocks.CHIPPED_ANVIL);
      this.func_218492_c(Blocks.DAMAGED_ANVIL);
      this.func_218492_c(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
      this.func_218492_c(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE);
      this.func_218492_c(Blocks.COMPARATOR);
      this.func_218492_c(Blocks.DAYLIGHT_DETECTOR);
      this.func_218492_c(Blocks.REDSTONE_BLOCK);
      this.func_218492_c(Blocks.QUARTZ_BLOCK);
      this.func_218492_c(Blocks.CHISELED_QUARTZ_BLOCK);
      this.func_218492_c(Blocks.QUARTZ_PILLAR);
      this.func_218492_c(Blocks.QUARTZ_STAIRS);
      this.func_218492_c(Blocks.ACTIVATOR_RAIL);
      this.func_218492_c(Blocks.WHITE_TERRACOTTA);
      this.func_218492_c(Blocks.ORANGE_TERRACOTTA);
      this.func_218492_c(Blocks.MAGENTA_TERRACOTTA);
      this.func_218492_c(Blocks.LIGHT_BLUE_TERRACOTTA);
      this.func_218492_c(Blocks.YELLOW_TERRACOTTA);
      this.func_218492_c(Blocks.LIME_TERRACOTTA);
      this.func_218492_c(Blocks.PINK_TERRACOTTA);
      this.func_218492_c(Blocks.GRAY_TERRACOTTA);
      this.func_218492_c(Blocks.LIGHT_GRAY_TERRACOTTA);
      this.func_218492_c(Blocks.CYAN_TERRACOTTA);
      this.func_218492_c(Blocks.PURPLE_TERRACOTTA);
      this.func_218492_c(Blocks.BLUE_TERRACOTTA);
      this.func_218492_c(Blocks.BROWN_TERRACOTTA);
      this.func_218492_c(Blocks.GREEN_TERRACOTTA);
      this.func_218492_c(Blocks.RED_TERRACOTTA);
      this.func_218492_c(Blocks.BLACK_TERRACOTTA);
      this.func_218492_c(Blocks.ACACIA_STAIRS);
      this.func_218492_c(Blocks.DARK_OAK_STAIRS);
      this.func_218492_c(Blocks.SLIME_BLOCK);
      this.func_218492_c(Blocks.IRON_TRAPDOOR);
      this.func_218492_c(Blocks.PRISMARINE);
      this.func_218492_c(Blocks.PRISMARINE_BRICKS);
      this.func_218492_c(Blocks.DARK_PRISMARINE);
      this.func_218492_c(Blocks.PRISMARINE_STAIRS);
      this.func_218492_c(Blocks.PRISMARINE_BRICK_STAIRS);
      this.func_218492_c(Blocks.DARK_PRISMARINE_STAIRS);
      this.func_218492_c(Blocks.HAY_BLOCK);
      this.func_218492_c(Blocks.WHITE_CARPET);
      this.func_218492_c(Blocks.ORANGE_CARPET);
      this.func_218492_c(Blocks.MAGENTA_CARPET);
      this.func_218492_c(Blocks.LIGHT_BLUE_CARPET);
      this.func_218492_c(Blocks.YELLOW_CARPET);
      this.func_218492_c(Blocks.LIME_CARPET);
      this.func_218492_c(Blocks.PINK_CARPET);
      this.func_218492_c(Blocks.GRAY_CARPET);
      this.func_218492_c(Blocks.LIGHT_GRAY_CARPET);
      this.func_218492_c(Blocks.CYAN_CARPET);
      this.func_218492_c(Blocks.PURPLE_CARPET);
      this.func_218492_c(Blocks.BLUE_CARPET);
      this.func_218492_c(Blocks.BROWN_CARPET);
      this.func_218492_c(Blocks.GREEN_CARPET);
      this.func_218492_c(Blocks.RED_CARPET);
      this.func_218492_c(Blocks.BLACK_CARPET);
      this.func_218492_c(Blocks.TERRACOTTA);
      this.func_218492_c(Blocks.COAL_BLOCK);
      this.func_218492_c(Blocks.RED_SANDSTONE);
      this.func_218492_c(Blocks.CHISELED_RED_SANDSTONE);
      this.func_218492_c(Blocks.CUT_RED_SANDSTONE);
      this.func_218492_c(Blocks.RED_SANDSTONE_STAIRS);
      this.func_218492_c(Blocks.SMOOTH_STONE);
      this.func_218492_c(Blocks.SMOOTH_SANDSTONE);
      this.func_218492_c(Blocks.SMOOTH_QUARTZ);
      this.func_218492_c(Blocks.SMOOTH_RED_SANDSTONE);
      this.func_218492_c(Blocks.SPRUCE_FENCE_GATE);
      this.func_218492_c(Blocks.BIRCH_FENCE_GATE);
      this.func_218492_c(Blocks.JUNGLE_FENCE_GATE);
      this.func_218492_c(Blocks.ACACIA_FENCE_GATE);
      this.func_218492_c(Blocks.DARK_OAK_FENCE_GATE);
      this.func_218492_c(Blocks.SPRUCE_FENCE);
      this.func_218492_c(Blocks.BIRCH_FENCE);
      this.func_218492_c(Blocks.JUNGLE_FENCE);
      this.func_218492_c(Blocks.ACACIA_FENCE);
      this.func_218492_c(Blocks.DARK_OAK_FENCE);
      this.func_218492_c(Blocks.END_ROD);
      this.func_218492_c(Blocks.PURPUR_BLOCK);
      this.func_218492_c(Blocks.PURPUR_PILLAR);
      this.func_218492_c(Blocks.PURPUR_STAIRS);
      this.func_218492_c(Blocks.END_STONE_BRICKS);
      this.func_218492_c(Blocks.MAGMA_BLOCK);
      this.func_218492_c(Blocks.NETHER_WART_BLOCK);
      this.func_218492_c(Blocks.RED_NETHER_BRICKS);
      this.func_218492_c(Blocks.BONE_BLOCK);
      this.func_218492_c(Blocks.OBSERVER);
      this.func_218492_c(Blocks.WHITE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.ORANGE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.MAGENTA_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.YELLOW_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.LIME_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.PINK_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.GRAY_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.CYAN_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.PURPLE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.BLUE_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.BROWN_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.GREEN_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.RED_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.BLACK_GLAZED_TERRACOTTA);
      this.func_218492_c(Blocks.WHITE_CONCRETE);
      this.func_218492_c(Blocks.ORANGE_CONCRETE);
      this.func_218492_c(Blocks.MAGENTA_CONCRETE);
      this.func_218492_c(Blocks.LIGHT_BLUE_CONCRETE);
      this.func_218492_c(Blocks.YELLOW_CONCRETE);
      this.func_218492_c(Blocks.LIME_CONCRETE);
      this.func_218492_c(Blocks.PINK_CONCRETE);
      this.func_218492_c(Blocks.GRAY_CONCRETE);
      this.func_218492_c(Blocks.LIGHT_GRAY_CONCRETE);
      this.func_218492_c(Blocks.CYAN_CONCRETE);
      this.func_218492_c(Blocks.PURPLE_CONCRETE);
      this.func_218492_c(Blocks.BLUE_CONCRETE);
      this.func_218492_c(Blocks.BROWN_CONCRETE);
      this.func_218492_c(Blocks.GREEN_CONCRETE);
      this.func_218492_c(Blocks.RED_CONCRETE);
      this.func_218492_c(Blocks.BLACK_CONCRETE);
      this.func_218492_c(Blocks.WHITE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.ORANGE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.MAGENTA_CONCRETE_POWDER);
      this.func_218492_c(Blocks.LIGHT_BLUE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.YELLOW_CONCRETE_POWDER);
      this.func_218492_c(Blocks.LIME_CONCRETE_POWDER);
      this.func_218492_c(Blocks.PINK_CONCRETE_POWDER);
      this.func_218492_c(Blocks.GRAY_CONCRETE_POWDER);
      this.func_218492_c(Blocks.LIGHT_GRAY_CONCRETE_POWDER);
      this.func_218492_c(Blocks.CYAN_CONCRETE_POWDER);
      this.func_218492_c(Blocks.PURPLE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.BLUE_CONCRETE_POWDER);
      this.func_218492_c(Blocks.BROWN_CONCRETE_POWDER);
      this.func_218492_c(Blocks.GREEN_CONCRETE_POWDER);
      this.func_218492_c(Blocks.RED_CONCRETE_POWDER);
      this.func_218492_c(Blocks.BLACK_CONCRETE_POWDER);
      this.func_218492_c(Blocks.KELP);
      this.func_218492_c(Blocks.DRIED_KELP_BLOCK);
      this.func_218492_c(Blocks.DEAD_TUBE_CORAL_BLOCK);
      this.func_218492_c(Blocks.DEAD_BRAIN_CORAL_BLOCK);
      this.func_218492_c(Blocks.DEAD_BUBBLE_CORAL_BLOCK);
      this.func_218492_c(Blocks.DEAD_FIRE_CORAL_BLOCK);
      this.func_218492_c(Blocks.DEAD_HORN_CORAL_BLOCK);
      this.func_218492_c(Blocks.CONDUIT);
      this.func_218492_c(Blocks.DRAGON_EGG);
      this.func_218492_c(Blocks.field_222405_kQ);
      this.func_218492_c(Blocks.field_222407_kV);
      this.func_218492_c(Blocks.field_222408_kW);
      this.func_218492_c(Blocks.field_222409_kX);
      this.func_218492_c(Blocks.field_222410_kY);
      this.func_218492_c(Blocks.field_222411_kZ);
      this.func_218492_c(Blocks.field_222437_la);
      this.func_218492_c(Blocks.field_222438_lb);
      this.func_218492_c(Blocks.field_222439_lc);
      this.func_218492_c(Blocks.field_222440_ld);
      this.func_218492_c(Blocks.field_222441_le);
      this.func_218492_c(Blocks.field_222442_lf);
      this.func_218492_c(Blocks.field_222443_lg);
      this.func_218492_c(Blocks.field_222444_lh);
      this.func_218492_c(Blocks.field_222445_li);
      this.func_218492_c(Blocks.field_222459_lw);
      this.func_218492_c(Blocks.field_222460_lx);
      this.func_218492_c(Blocks.field_222461_ly);
      this.func_218492_c(Blocks.field_222462_lz);
      this.func_218492_c(Blocks.field_222412_lA);
      this.func_218492_c(Blocks.field_222413_lB);
      this.func_218492_c(Blocks.field_222414_lC);
      this.func_218492_c(Blocks.field_222415_lD);
      this.func_218492_c(Blocks.field_222416_lE);
      this.func_218492_c(Blocks.field_222417_lF);
      this.func_218492_c(Blocks.field_222418_lG);
      this.func_218492_c(Blocks.field_222419_lH);
      this.func_218492_c(Blocks.field_222421_lJ);
      this.func_218492_c(Blocks.field_222420_lI);
      this.func_218493_a(Blocks.FARMLAND, Blocks.DIRT);
      this.func_218493_a(Blocks.TRIPWIRE, Items.STRING);
      this.func_218493_a(Blocks.GRASS_PATH, Blocks.DIRT);
      this.func_218493_a(Blocks.KELP_PLANT, Blocks.KELP);
      this.func_218493_a(Blocks.field_222404_kP, Blocks.field_222405_kQ);
      this.func_218522_a(Blocks.STONE, (p_218490_0_) -> {
         return func_218515_b(p_218490_0_, Blocks.COBBLESTONE);
      });
      this.func_218522_a(Blocks.GRASS_BLOCK, (p_218529_0_) -> {
         return func_218515_b(p_218529_0_, Blocks.DIRT);
      });
      this.func_218522_a(Blocks.PODZOL, (p_218514_0_) -> {
         return func_218515_b(p_218514_0_, Blocks.DIRT);
      });
      this.func_218522_a(Blocks.MYCELIUM, (p_218501_0_) -> {
         return func_218515_b(p_218501_0_, Blocks.DIRT);
      });
      this.func_218522_a(Blocks.TUBE_CORAL_BLOCK, (p_218539_0_) -> {
         return func_218515_b(p_218539_0_, Blocks.DEAD_TUBE_CORAL_BLOCK);
      });
      this.func_218522_a(Blocks.BRAIN_CORAL_BLOCK, (p_218462_0_) -> {
         return func_218515_b(p_218462_0_, Blocks.DEAD_BRAIN_CORAL_BLOCK);
      });
      this.func_218522_a(Blocks.BUBBLE_CORAL_BLOCK, (p_218505_0_) -> {
         return func_218515_b(p_218505_0_, Blocks.DEAD_BUBBLE_CORAL_BLOCK);
      });
      this.func_218522_a(Blocks.FIRE_CORAL_BLOCK, (p_218499_0_) -> {
         return func_218515_b(p_218499_0_, Blocks.DEAD_FIRE_CORAL_BLOCK);
      });
      this.func_218522_a(Blocks.HORN_CORAL_BLOCK, (p_218502_0_) -> {
         return func_218515_b(p_218502_0_, Blocks.DEAD_HORN_CORAL_BLOCK);
      });
      this.func_218522_a(Blocks.BOOKSHELF, (p_218534_0_) -> {
         return func_218530_a(p_218534_0_, Items.BOOK, ConstantRange.func_215835_a(3));
      });
      this.func_218522_a(Blocks.CLAY, (p_218465_0_) -> {
         return func_218530_a(p_218465_0_, Items.CLAY_BALL, ConstantRange.func_215835_a(4));
      });
      this.func_218522_a(Blocks.ENDER_CHEST, (p_218558_0_) -> {
         return func_218530_a(p_218558_0_, Blocks.OBSIDIAN, ConstantRange.func_215835_a(8));
      });
      this.func_218522_a(Blocks.SNOW_BLOCK, (p_218556_0_) -> {
         return func_218530_a(p_218556_0_, Items.SNOWBALL, ConstantRange.func_215835_a(4));
      });
      this.func_218507_a(Blocks.CHORUS_PLANT, func_218463_a(Items.CHORUS_FRUIT, RandomValueRange.func_215837_a(0.0F, 1.0F)));
      this.func_218547_a(Blocks.POTTED_OAK_SAPLING);
      this.func_218547_a(Blocks.POTTED_SPRUCE_SAPLING);
      this.func_218547_a(Blocks.POTTED_BIRCH_SAPLING);
      this.func_218547_a(Blocks.POTTED_JUNGLE_SAPLING);
      this.func_218547_a(Blocks.POTTED_ACACIA_SAPLING);
      this.func_218547_a(Blocks.POTTED_DARK_OAK_SAPLING);
      this.func_218547_a(Blocks.POTTED_FERN);
      this.func_218547_a(Blocks.POTTED_DANDELION);
      this.func_218547_a(Blocks.POTTED_POPPY);
      this.func_218547_a(Blocks.POTTED_BLUE_ORCHID);
      this.func_218547_a(Blocks.POTTED_ALLIUM);
      this.func_218547_a(Blocks.POTTED_AZURE_BLUET);
      this.func_218547_a(Blocks.POTTED_RED_TULIP);
      this.func_218547_a(Blocks.POTTED_ORANGE_TULIP);
      this.func_218547_a(Blocks.POTTED_WHITE_TULIP);
      this.func_218547_a(Blocks.POTTED_PINK_TULIP);
      this.func_218547_a(Blocks.POTTED_OXEYE_DAISY);
      this.func_218547_a(Blocks.field_222398_eF);
      this.func_218547_a(Blocks.field_222399_eG);
      this.func_218547_a(Blocks.field_222400_eH);
      this.func_218547_a(Blocks.POTTED_RED_MUSHROOM);
      this.func_218547_a(Blocks.POTTED_BROWN_MUSHROOM);
      this.func_218547_a(Blocks.POTTED_DEAD_BUSH);
      this.func_218547_a(Blocks.POTTED_CACTUS);
      this.func_218547_a(Blocks.field_222406_kR);
      this.func_218522_a(Blocks.ACACIA_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.BIRCH_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.BRICK_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.COBBLESTONE_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.DARK_OAK_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.DARK_PRISMARINE_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.JUNGLE_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.NETHER_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.OAK_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.PETRIFIED_OAK_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.PRISMARINE_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.PRISMARINE_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.PURPUR_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.QUARTZ_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.RED_SANDSTONE_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.SANDSTONE_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222403_hT, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222402_hL, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.SPRUCE_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.STONE_BRICK_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.STONE_SLAB, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222401_hJ, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222446_lj, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222447_lk, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222448_ll, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222449_lm, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222450_ln, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222451_lo, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222452_lp, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222453_lq, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222454_lr, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222455_ls, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222456_lt, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222457_lu, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.field_222458_lv, BlockLootTables::func_218513_d);
      this.func_218522_a(Blocks.ACACIA_DOOR, (p_218483_0_) -> {
         return func_218562_a(p_218483_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.BIRCH_DOOR, (p_218528_0_) -> {
         return func_218562_a(p_218528_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.DARK_OAK_DOOR, (p_218468_0_) -> {
         return func_218562_a(p_218468_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.IRON_DOOR, (p_218510_0_) -> {
         return func_218562_a(p_218510_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.JUNGLE_DOOR, (p_218498_0_) -> {
         return func_218562_a(p_218498_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.OAK_DOOR, (p_218480_0_) -> {
         return func_218562_a(p_218480_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.SPRUCE_DOOR, (p_218527_0_) -> {
         return func_218562_a(p_218527_0_, DoorBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.BLACK_BED, (p_218567_0_) -> {
         return func_218562_a(p_218567_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.BLUE_BED, (p_218555_0_) -> {
         return func_218562_a(p_218555_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.BROWN_BED, (p_218543_0_) -> {
         return func_218562_a(p_218543_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.CYAN_BED, (p_218479_0_) -> {
         return func_218562_a(p_218479_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.GRAY_BED, (p_218521_0_) -> {
         return func_218562_a(p_218521_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.GREEN_BED, (p_218470_0_) -> {
         return func_218562_a(p_218470_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.LIGHT_BLUE_BED, (p_218536_0_) -> {
         return func_218562_a(p_218536_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.LIGHT_GRAY_BED, (p_218545_0_) -> {
         return func_218562_a(p_218545_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.LIME_BED, (p_218557_0_) -> {
         return func_218562_a(p_218557_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.MAGENTA_BED, (p_218566_0_) -> {
         return func_218562_a(p_218566_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.PURPLE_BED, (p_218520_0_) -> {
         return func_218562_a(p_218520_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.ORANGE_BED, (p_218472_0_) -> {
         return func_218562_a(p_218472_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.PINK_BED, (p_218537_0_) -> {
         return func_218562_a(p_218537_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.RED_BED, (p_218549_0_) -> {
         return func_218562_a(p_218549_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.WHITE_BED, (p_218569_0_) -> {
         return func_218562_a(p_218569_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.YELLOW_BED, (p_218517_0_) -> {
         return func_218562_a(p_218517_0_, BedBlock.PART, BedPart.HEAD);
      });
      this.func_218522_a(Blocks.LILAC, (p_218488_0_) -> {
         return func_218562_a(p_218488_0_, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.SUNFLOWER, (p_218503_0_) -> {
         return func_218562_a(p_218503_0_, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.PEONY, (p_218497_0_) -> {
         return func_218562_a(p_218497_0_, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.ROSE_BUSH, (p_218504_0_) -> {
         return func_218562_a(p_218504_0_, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
      });
      this.func_218522_a(Blocks.TNT, (p_218516_0_) -> {
         return func_218562_a(p_218516_0_, TNTBlock.UNSTABLE, false);
      });
      this.func_218522_a(Blocks.COCOA, (p_218478_0_) -> {
         return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(func_218552_a(p_218478_0_, ItemLootEntry.func_216168_a(Items.COCOA_BEANS).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(3)).acceptCondition(BlockStateProperty.builder(p_218478_0_).with(CocoaBlock.AGE, 2))))));
      });
      this.func_218522_a(Blocks.SEA_PICKLE, (p_218551_0_) -> {
         return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(func_218552_a(p_218551_0_, ItemLootEntry.func_216168_a(p_218551_0_).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(2)).acceptCondition(BlockStateProperty.builder(p_218551_0_).with(SeaPickleBlock.PICKLES, 2))).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(3)).acceptCondition(BlockStateProperty.builder(p_218551_0_).with(SeaPickleBlock.PICKLES, 3))).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(4)).acceptCondition(BlockStateProperty.builder(p_218551_0_).with(SeaPickleBlock.PICKLES, 4))))));
      });
      this.func_218522_a(Blocks.field_222436_lZ, (p_218565_0_) -> {
         return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216045_a(func_218552_a(p_218565_0_, ItemLootEntry.func_216168_a(Items.field_222035_iX)))).func_216040_a(LootPool.func_216096_a().func_216045_a(ItemLootEntry.func_216168_a(Items.BONE_MEAL)).acceptCondition(BlockStateProperty.builder(p_218565_0_).with(ComposterBlock.field_220298_a, 8)));
      });
      this.func_218522_a(Blocks.BEACON, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.BREWING_STAND, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.CHEST, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.DISPENSER, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.DROPPER, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.ENCHANTING_TABLE, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.FURNACE, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.HOPPER, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.TRAPPED_CHEST, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222423_lL, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222424_lM, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222422_lK, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222425_lN, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222426_lO, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222427_lP, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222428_lQ, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222429_lR, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222430_lS, BlockLootTables::func_218481_e);
      this.func_218522_a(Blocks.field_222431_lT, BlockLootTables::func_218546_a);
      this.func_218522_a(Blocks.field_222432_lU, BlockLootTables::func_218546_a);
      this.func_218522_a(Blocks.SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.BLACK_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.BLUE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.BROWN_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.CYAN_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.GRAY_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.GREEN_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.LIGHT_BLUE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.LIGHT_GRAY_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.LIME_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.MAGENTA_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.ORANGE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.PINK_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.PURPLE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.RED_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.WHITE_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.YELLOW_SHULKER_BOX, BlockLootTables::func_218544_f);
      this.func_218522_a(Blocks.BLACK_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.BLUE_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.BROWN_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.CYAN_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.GRAY_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.GREEN_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.LIGHT_BLUE_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.LIGHT_GRAY_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.LIME_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.MAGENTA_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.ORANGE_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.PINK_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.PURPLE_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.RED_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.WHITE_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.YELLOW_BANNER, BlockLootTables::func_218559_g);
      this.func_218522_a(Blocks.PLAYER_HEAD, (p_218473_0_) -> {
         return LootTable.func_216119_b().func_216040_a(func_218560_a(p_218473_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(p_218473_0_).func_212841_b_(CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY).func_216056_a("Owner", "SkullOwner")))));
      });
      this.func_218522_a(Blocks.BIRCH_LEAVES, (p_218518_0_) -> {
         return func_218540_a(p_218518_0_, Blocks.BIRCH_SAPLING, field_218579_g);
      });
      this.func_218522_a(Blocks.ACACIA_LEAVES, (p_218477_0_) -> {
         return func_218540_a(p_218477_0_, Blocks.ACACIA_SAPLING, field_218579_g);
      });
      this.func_218522_a(Blocks.JUNGLE_LEAVES, (p_218500_0_) -> {
         return func_218540_a(p_218500_0_, Blocks.JUNGLE_SAPLING, field_218580_h);
      });
      this.func_218522_a(Blocks.SPRUCE_LEAVES, (p_218506_0_) -> {
         return func_218540_a(p_218506_0_, Blocks.SPRUCE_SAPLING, field_218579_g);
      });
      this.func_218522_a(Blocks.OAK_LEAVES, (p_218471_0_) -> {
         return func_218526_b(p_218471_0_, Blocks.OAK_SAPLING, field_218579_g);
      });
      this.func_218522_a(Blocks.DARK_OAK_LEAVES, (p_218538_0_) -> {
         return func_218526_b(p_218538_0_, Blocks.DARK_OAK_SAPLING, field_218579_g);
      });
      ILootCondition.IBuilder ilootcondition$ibuilder = BlockStateProperty.builder(Blocks.BEETROOTS).with(BeetrootBlock.BEETROOT_AGE, 3);
      this.func_218522_a(Blocks.BEETROOTS, (p_218474_1_) -> {
         return func_218541_a(p_218474_1_, Items.BEETROOT, Items.BEETROOT_SEEDS, ilootcondition$ibuilder);
      });
      ILootCondition.IBuilder ilootcondition$ibuilder1 = BlockStateProperty.builder(Blocks.WHEAT).with(CropsBlock.AGE, 7);
      this.func_218522_a(Blocks.WHEAT, (p_218489_1_) -> {
         return func_218541_a(p_218489_1_, Items.WHEAT, Items.WHEAT_SEEDS, ilootcondition$ibuilder1);
      });
      ILootCondition.IBuilder ilootcondition$ibuilder2 = BlockStateProperty.builder(Blocks.CARROTS).with(CarrotBlock.AGE, 7);
      this.func_218522_a(Blocks.CARROTS, (p_218542_1_) -> {
         return func_218552_a(p_218542_1_, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216045_a(ItemLootEntry.func_216168_a(Items.CARROT))).func_216040_a(LootPool.func_216096_a().acceptCondition(ilootcondition$ibuilder2).func_216045_a(ItemLootEntry.func_216168_a(Items.CARROT).func_212841_b_(ApplyBonus.func_215870_a(Enchantments.FORTUNE, 0.5714286F, 3)))));
      });
      ILootCondition.IBuilder ilootcondition$ibuilder3 = BlockStateProperty.builder(Blocks.POTATOES).with(PotatoBlock.AGE, 7);
      this.func_218522_a(Blocks.POTATOES, (p_218563_1_) -> {
         return func_218552_a(p_218563_1_, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216045_a(ItemLootEntry.func_216168_a(Items.POTATO))).func_216040_a(LootPool.func_216096_a().acceptCondition(ilootcondition$ibuilder3).func_216045_a(ItemLootEntry.func_216168_a(Items.POTATO).func_212841_b_(ApplyBonus.func_215870_a(Enchantments.FORTUNE, 0.5714286F, 3)))).func_216040_a(LootPool.func_216096_a().acceptCondition(ilootcondition$ibuilder3).func_216045_a(ItemLootEntry.func_216168_a(Items.POISONOUS_POTATO).acceptCondition(RandomChance.builder(0.02F)))));
      });
      this.func_218522_a(Blocks.field_222434_lW, (p_218554_0_) -> {
         return func_218552_a(p_218554_0_, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().acceptCondition(BlockStateProperty.builder(Blocks.field_222434_lW).with(SweetBerryBushBlock.field_220125_a, 3)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_222112_pR)).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 3.0F))).func_212841_b_(ApplyBonus.func_215871_b(Enchantments.FORTUNE))).func_216040_a(LootPool.func_216096_a().acceptCondition(BlockStateProperty.builder(Blocks.field_222434_lW).with(SweetBerryBushBlock.field_220125_a, 2)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_222112_pR)).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(1.0F, 2.0F))).func_212841_b_(ApplyBonus.func_215871_b(Enchantments.FORTUNE))));
      });
      this.func_218522_a(Blocks.BROWN_MUSHROOM_BLOCK, (p_218568_0_) -> {
         return func_218491_c(p_218568_0_, Blocks.BROWN_MUSHROOM);
      });
      this.func_218522_a(Blocks.RED_MUSHROOM_BLOCK, (p_218548_0_) -> {
         return func_218491_c(p_218548_0_, Blocks.RED_MUSHROOM);
      });
      this.func_218522_a(Blocks.COAL_ORE, (p_218487_0_) -> {
         return func_218476_a(p_218487_0_, Items.COAL);
      });
      this.func_218522_a(Blocks.EMERALD_ORE, (p_218525_0_) -> {
         return func_218476_a(p_218525_0_, Items.EMERALD);
      });
      this.func_218522_a(Blocks.NETHER_QUARTZ_ORE, (p_218572_0_) -> {
         return func_218476_a(p_218572_0_, Items.QUARTZ);
      });
      this.func_218522_a(Blocks.DIAMOND_ORE, (p_218550_0_) -> {
         return func_218476_a(p_218550_0_, Items.DIAMOND);
      });
      this.func_218522_a(Blocks.LAPIS_ORE, (p_218531_0_) -> {
         return func_218519_a(p_218531_0_, func_218552_a(p_218531_0_, ItemLootEntry.func_216168_a(Items.LAPIS_LAZULI).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(4.0F, 9.0F))).func_212841_b_(ApplyBonus.func_215869_a(Enchantments.FORTUNE))));
      });
      this.func_218522_a(Blocks.COBWEB, (p_218467_0_) -> {
         return func_218535_c(p_218467_0_, func_218560_a(p_218467_0_, ItemLootEntry.func_216168_a(Items.STRING)));
      });
      this.func_218522_a(Blocks.DEAD_BUSH, (p_218509_0_) -> {
         return func_218511_b(p_218509_0_, func_218552_a(p_218509_0_, ItemLootEntry.func_216168_a(Items.STICK).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 2.0F)))));
      });
      this.func_218522_a(Blocks.SEAGRASS, BlockLootTables::func_218486_d);
      this.func_218522_a(Blocks.VINE, BlockLootTables::func_218486_d);
      this.func_218507_a(Blocks.TALL_SEAGRASS, func_218486_d(Blocks.SEAGRASS));
      this.func_218507_a(Blocks.LARGE_FERN, func_218486_d(Blocks.FERN));
      this.func_218522_a(Blocks.TALL_GRASS, (p_218512_0_) -> {
         return func_218511_b(Blocks.GRASS, ((StandaloneLootEntry.Builder)func_218560_a(p_218512_0_, ItemLootEntry.func_216168_a(Items.WHEAT_SEEDS)).acceptCondition(BlockStateProperty.builder(p_218512_0_).with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER))).acceptCondition(RandomChance.builder(0.125F)));
      });
      this.func_218522_a(Blocks.MELON_STEM, (p_218496_0_) -> {
         return func_218475_b(p_218496_0_, Items.MELON_SEEDS);
      });
      this.func_218522_a(Blocks.PUMPKIN_STEM, (p_218532_0_) -> {
         return func_218475_b(p_218532_0_, Items.PUMPKIN_SEEDS);
      });
      this.func_218522_a(Blocks.CHORUS_FLOWER, (p_218464_0_) -> {
         return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(func_218560_a(p_218464_0_, ItemLootEntry.func_216168_a(p_218464_0_)).acceptCondition(EntityHasProperty.func_215998_a(LootContext.EntityTarget.THIS))));
      });
      this.func_218522_a(Blocks.FERN, BlockLootTables::func_218570_h);
      this.func_218522_a(Blocks.GRASS, BlockLootTables::func_218570_h);
      this.func_218522_a(Blocks.GLOWSTONE, (p_218571_0_) -> {
         return func_218519_a(p_218571_0_, func_218552_a(p_218571_0_, ItemLootEntry.func_216168_a(Items.GLOWSTONE_DUST).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 4.0F))).func_212841_b_(ApplyBonus.func_215871_b(Enchantments.FORTUNE)).func_212841_b_(LimitCount.func_215911_a(IntClamper.func_215843_a(1, 4)))));
      });
      this.func_218522_a(Blocks.MELON, (p_218553_0_) -> {
         return func_218519_a(p_218553_0_, func_218552_a(p_218553_0_, ItemLootEntry.func_216168_a(Items.MELON_SLICE).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(3.0F, 7.0F))).func_212841_b_(ApplyBonus.func_215871_b(Enchantments.FORTUNE)).func_212841_b_(LimitCount.func_215911_a(IntClamper.func_215851_b(9)))));
      });
      this.func_218522_a(Blocks.REDSTONE_ORE, (p_218485_0_) -> {
         return func_218519_a(p_218485_0_, func_218552_a(p_218485_0_, ItemLootEntry.func_216168_a(Items.REDSTONE).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(4.0F, 5.0F))).func_212841_b_(ApplyBonus.func_215871_b(Enchantments.FORTUNE))));
      });
      this.func_218522_a(Blocks.SEA_LANTERN, (p_218533_0_) -> {
         return func_218519_a(p_218533_0_, func_218552_a(p_218533_0_, ItemLootEntry.func_216168_a(Items.PRISMARINE_CRYSTALS).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 3.0F))).func_212841_b_(ApplyBonus.func_215871_b(Enchantments.FORTUNE)).func_212841_b_(LimitCount.func_215911_a(IntClamper.func_215843_a(1, 5)))));
      });
      this.func_218522_a(Blocks.NETHER_WART, (p_218469_0_) -> {
         return LootTable.func_216119_b().func_216040_a(func_218552_a(p_218469_0_, LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.NETHER_WART).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(2.0F, 4.0F)).acceptCondition(BlockStateProperty.builder(p_218469_0_).with(NetherWartBlock.AGE, 3))).func_212841_b_(ApplyBonus.func_215871_b(Enchantments.FORTUNE).acceptCondition(BlockStateProperty.builder(p_218469_0_).with(NetherWartBlock.AGE, 3))))));
      });
      this.func_218522_a(Blocks.SNOW, (p_218508_0_) -> {
         return LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().acceptCondition(EntityHasProperty.func_215998_a(LootContext.EntityTarget.THIS)).func_216045_a(AlternativesLootEntry.func_216149_a(AlternativesLootEntry.func_216149_a(ItemLootEntry.func_216168_a(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 1)), ((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 2))).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(2))), ((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 3))).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(3))), ((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 4))).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(4))), ((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 5))).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(5))), ((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 6))).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(6))), ((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(Items.SNOWBALL).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 7))).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(7))), ItemLootEntry.func_216168_a(Items.SNOWBALL).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(8)))).acceptCondition(field_218574_b), AlternativesLootEntry.func_216149_a(ItemLootEntry.func_216168_a(p_218508_0_).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 1)), ItemLootEntry.func_216168_a(p_218508_0_).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(2))).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 2)), ItemLootEntry.func_216168_a(p_218508_0_).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(3))).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 3)), ItemLootEntry.func_216168_a(p_218508_0_).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(4))).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 4)), ItemLootEntry.func_216168_a(p_218508_0_).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(5))).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 5)), ItemLootEntry.func_216168_a(p_218508_0_).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(6))).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 6)), ItemLootEntry.func_216168_a(p_218508_0_).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(7))).acceptCondition(BlockStateProperty.builder(p_218508_0_).with(SnowBlock.LAYERS, 7)), ItemLootEntry.func_216168_a(Blocks.SNOW_BLOCK)))));
      });
      this.func_218522_a(Blocks.GRAVEL, (p_218495_0_) -> {
         return func_218519_a(p_218495_0_, func_218560_a(p_218495_0_, ((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(Items.FLINT).acceptCondition(TableBonus.builder(Enchantments.FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F))).func_216080_a(ItemLootEntry.func_216168_a(p_218495_0_))));
      });
      this.func_218522_a(Blocks.field_222433_lV, (p_218484_0_) -> {
         return func_218519_a(p_218484_0_, func_218560_a(p_218484_0_, ItemLootEntry.func_216168_a(Items.CHARCOAL).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(2)))));
      });
      this.func_218466_b(Blocks.GLASS);
      this.func_218466_b(Blocks.WHITE_STAINED_GLASS);
      this.func_218466_b(Blocks.ORANGE_STAINED_GLASS);
      this.func_218466_b(Blocks.MAGENTA_STAINED_GLASS);
      this.func_218466_b(Blocks.LIGHT_BLUE_STAINED_GLASS);
      this.func_218466_b(Blocks.YELLOW_STAINED_GLASS);
      this.func_218466_b(Blocks.LIME_STAINED_GLASS);
      this.func_218466_b(Blocks.PINK_STAINED_GLASS);
      this.func_218466_b(Blocks.GRAY_STAINED_GLASS);
      this.func_218466_b(Blocks.LIGHT_GRAY_STAINED_GLASS);
      this.func_218466_b(Blocks.CYAN_STAINED_GLASS);
      this.func_218466_b(Blocks.PURPLE_STAINED_GLASS);
      this.func_218466_b(Blocks.BLUE_STAINED_GLASS);
      this.func_218466_b(Blocks.BROWN_STAINED_GLASS);
      this.func_218466_b(Blocks.GREEN_STAINED_GLASS);
      this.func_218466_b(Blocks.RED_STAINED_GLASS);
      this.func_218466_b(Blocks.BLACK_STAINED_GLASS);
      this.func_218466_b(Blocks.GLASS_PANE);
      this.func_218466_b(Blocks.WHITE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.ORANGE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.MAGENTA_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.YELLOW_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.LIME_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.PINK_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.GRAY_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.CYAN_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.PURPLE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.BLUE_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.BROWN_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.GREEN_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.RED_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.BLACK_STAINED_GLASS_PANE);
      this.func_218466_b(Blocks.ICE);
      this.func_218466_b(Blocks.PACKED_ICE);
      this.func_218466_b(Blocks.BLUE_ICE);
      this.func_218466_b(Blocks.TURTLE_EGG);
      this.func_218466_b(Blocks.MUSHROOM_STEM);
      this.func_218466_b(Blocks.DEAD_TUBE_CORAL);
      this.func_218466_b(Blocks.DEAD_BRAIN_CORAL);
      this.func_218466_b(Blocks.DEAD_BUBBLE_CORAL);
      this.func_218466_b(Blocks.DEAD_FIRE_CORAL);
      this.func_218466_b(Blocks.DEAD_HORN_CORAL);
      this.func_218466_b(Blocks.TUBE_CORAL);
      this.func_218466_b(Blocks.BRAIN_CORAL);
      this.func_218466_b(Blocks.BUBBLE_CORAL);
      this.func_218466_b(Blocks.FIRE_CORAL);
      this.func_218466_b(Blocks.HORN_CORAL);
      this.func_218466_b(Blocks.DEAD_TUBE_CORAL_FAN);
      this.func_218466_b(Blocks.DEAD_BRAIN_CORAL_FAN);
      this.func_218466_b(Blocks.DEAD_BUBBLE_CORAL_FAN);
      this.func_218466_b(Blocks.DEAD_FIRE_CORAL_FAN);
      this.func_218466_b(Blocks.DEAD_HORN_CORAL_FAN);
      this.func_218466_b(Blocks.TUBE_CORAL_FAN);
      this.func_218466_b(Blocks.BRAIN_CORAL_FAN);
      this.func_218466_b(Blocks.BUBBLE_CORAL_FAN);
      this.func_218466_b(Blocks.FIRE_CORAL_FAN);
      this.func_218466_b(Blocks.HORN_CORAL_FAN);
      this.func_218564_a(Blocks.INFESTED_STONE, Blocks.STONE);
      this.func_218564_a(Blocks.INFESTED_COBBLESTONE, Blocks.COBBLESTONE);
      this.func_218564_a(Blocks.INFESTED_STONE_BRICKS, Blocks.STONE_BRICKS);
      this.func_218564_a(Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
      this.func_218564_a(Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
      this.func_218564_a(Blocks.INFESTED_CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS);
      this.func_218507_a(Blocks.CAKE, func_218482_a());
      this.func_218507_a(Blocks.ATTACHED_PUMPKIN_STEM, func_218482_a());
      this.func_218507_a(Blocks.ATTACHED_MELON_STEM, func_218482_a());
      this.func_218507_a(Blocks.FROSTED_ICE, func_218482_a());
      this.func_218507_a(Blocks.SPAWNER, func_218482_a());
      Set<ResourceLocation> set = Sets.newHashSet();

      for(Block block : Registry.field_212618_g) {
         ResourceLocation resourcelocation = block.getLootTable();
         if (resourcelocation != LootTables.EMPTY && set.add(resourcelocation)) {
            LootTable.Builder loottable$builder = this.field_218581_i.remove(resourcelocation);
            if (loottable$builder == null) {
               throw new IllegalStateException(String.format("Missing loottable '%s' for '%s'", resourcelocation, Registry.field_212618_g.getKey(block)));
            }

            p_accept_1_.accept(resourcelocation, loottable$builder);
         }
      }

      if (!this.field_218581_i.isEmpty()) {
         throw new IllegalStateException("Created block loot tables for non-blocks: " + this.field_218581_i.keySet());
      }
   }

   public void func_218547_a(Block p_218547_1_) {
      this.func_218522_a(p_218547_1_, (p_218524_0_) -> {
         return func_218523_c(((FlowerPotBlock)p_218524_0_).func_220276_d());
      });
   }

   public void func_218564_a(Block p_218564_1_, Block p_218564_2_) {
      this.func_218507_a(p_218564_1_, func_218561_b(p_218564_2_));
   }

   public void func_218493_a(Block p_218493_1_, IItemProvider p_218493_2_) {
      this.func_218507_a(p_218493_1_, func_218546_a(p_218493_2_));
   }

   public void func_218466_b(Block p_218466_1_) {
      this.func_218564_a(p_218466_1_, p_218466_1_);
   }

   public void func_218492_c(Block p_218492_1_) {
      this.func_218493_a(p_218492_1_, p_218492_1_);
   }

   private void func_218522_a(Block p_218522_1_, Function<Block, LootTable.Builder> p_218522_2_) {
      this.func_218507_a(p_218522_1_, p_218522_2_.apply(p_218522_1_));
   }

   private void func_218507_a(Block p_218507_1_, LootTable.Builder p_218507_2_) {
      this.field_218581_i.put(p_218507_1_.getLootTable(), p_218507_2_);
   }
}