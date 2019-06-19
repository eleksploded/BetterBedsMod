package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.StandaloneLootEntry;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.LocationCheck;
import net.minecraft.world.storage.loot.functions.EnchantWithLevels;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetDamage;
import net.minecraft.world.storage.loot.functions.SetNBT;

public class FishingLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   public static final ILootCondition.IBuilder field_218589_a = LocationCheck.builder((new LocationPredicate.Builder()).func_218012_a(Biomes.JUNGLE));
   public static final ILootCondition.IBuilder field_218590_b = LocationCheck.builder((new LocationPredicate.Builder()).func_218012_a(Biomes.JUNGLE_HILLS));
   public static final ILootCondition.IBuilder field_218591_c = LocationCheck.builder((new LocationPredicate.Builder()).func_218012_a(Biomes.JUNGLE_EDGE));
   public static final ILootCondition.IBuilder field_218592_d = LocationCheck.builder((new LocationPredicate.Builder()).func_218012_a(Biomes.field_222370_aw));
   public static final ILootCondition.IBuilder field_218593_e = LocationCheck.builder((new LocationPredicate.Builder()).func_218012_a(Biomes.MODIFIED_JUNGLE));
   public static final ILootCondition.IBuilder field_218594_f = LocationCheck.builder((new LocationPredicate.Builder()).func_218012_a(Biomes.MODIFIED_JUNGLE_EDGE));
   public static final ILootCondition.IBuilder field_218595_g = LocationCheck.builder((new LocationPredicate.Builder()).func_218012_a(Biomes.field_222371_ax));

   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
      p_accept_1_.accept(LootTables.GAMEPLAY_FISHING, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(TableLootEntry.func_216171_a(LootTables.GAMEPLAY_FISHING_JUNK).func_216086_a(10).func_216085_b(-2)).func_216045_a(TableLootEntry.func_216171_a(LootTables.GAMEPLAY_FISHING_TREASURE).func_216086_a(5).func_216085_b(2)).func_216045_a(TableLootEntry.func_216171_a(LootTables.GAMEPLAY_FISHING_FISH).func_216086_a(85).func_216085_b(-1))));
      p_accept_1_.accept(LootTables.GAMEPLAY_FISHING_FISH, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216045_a(ItemLootEntry.func_216168_a(Items.COD).func_216086_a(60)).func_216045_a(ItemLootEntry.func_216168_a(Items.SALMON).func_216086_a(25)).func_216045_a(ItemLootEntry.func_216168_a(Items.TROPICAL_FISH).func_216086_a(2)).func_216045_a(ItemLootEntry.func_216168_a(Items.PUFFERFISH).func_216086_a(13))));
      p_accept_1_.accept(LootTables.GAMEPLAY_FISHING_JUNK, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216045_a(ItemLootEntry.func_216168_a(Items.LEATHER_BOOTS).func_216086_a(10).func_212841_b_(SetDamage.func_215931_a(RandomValueRange.func_215837_a(0.0F, 0.9F)))).func_216045_a(ItemLootEntry.func_216168_a(Items.LEATHER).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.BONE).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.POTION).func_216086_a(10).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218588_0_) -> {
         p_218588_0_.putString("Potion", "minecraft:water");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.STRING).func_216086_a(5)).func_216045_a(ItemLootEntry.func_216168_a(Items.FISHING_ROD).func_216086_a(2).func_212841_b_(SetDamage.func_215931_a(RandomValueRange.func_215837_a(0.0F, 0.9F)))).func_216045_a(ItemLootEntry.func_216168_a(Items.BOWL).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.STICK).func_216086_a(5)).func_216045_a(ItemLootEntry.func_216168_a(Items.INK_SAC).func_216086_a(1).func_212841_b_(SetCount.func_215932_a(ConstantRange.func_215835_a(10)))).func_216045_a(ItemLootEntry.func_216168_a(Blocks.TRIPWIRE_HOOK).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.ROTTEN_FLESH).func_216086_a(10)).func_216045_a(((StandaloneLootEntry.Builder)ItemLootEntry.func_216168_a(Blocks.field_222405_kQ).acceptCondition(field_218589_a.alternative(field_218590_b).alternative(field_218591_c).alternative(field_218592_d).alternative(field_218593_e).alternative(field_218594_f).alternative(field_218595_g))).func_216086_a(10))));
      p_accept_1_.accept(LootTables.GAMEPLAY_FISHING_TREASURE, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216045_a(ItemLootEntry.func_216168_a(Blocks.LILY_PAD)).func_216045_a(ItemLootEntry.func_216168_a(Items.NAME_TAG)).func_216045_a(ItemLootEntry.func_216168_a(Items.SADDLE)).func_216045_a(ItemLootEntry.func_216168_a(Items.BOW).func_212841_b_(SetDamage.func_215931_a(RandomValueRange.func_215837_a(0.0F, 0.25F))).func_212841_b_(EnchantWithLevels.func_215895_a(ConstantRange.func_215835_a(30)).func_216059_e())).func_216045_a(ItemLootEntry.func_216168_a(Items.FISHING_ROD).func_212841_b_(SetDamage.func_215931_a(RandomValueRange.func_215837_a(0.0F, 0.25F))).func_212841_b_(EnchantWithLevels.func_215895_a(ConstantRange.func_215835_a(30)).func_216059_e())).func_216045_a(ItemLootEntry.func_216168_a(Items.BOOK).func_212841_b_(EnchantWithLevels.func_215895_a(ConstantRange.func_215835_a(30)).func_216059_e())).func_216045_a(ItemLootEntry.func_216168_a(Items.NAUTILUS_SHELL))));
   }
}