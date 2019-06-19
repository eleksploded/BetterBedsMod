package net.minecraft.data.loot;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetNBT;

public class GiftLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
   public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
      p_accept_1_.accept(LootTables.field_215797_af, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.RABBIT_HIDE).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.RABBIT_FOOT).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.CHICKEN).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.FEATHER).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.ROTTEN_FLESH).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.STRING).func_216086_a(10)).func_216045_a(ItemLootEntry.func_216168_a(Items.PHANTOM_MEMBRANE).func_216086_a(2))));
      p_accept_1_.accept(LootTables.field_215798_ag, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.CHAINMAIL_HELMET)).func_216045_a(ItemLootEntry.func_216168_a(Items.CHAINMAIL_CHESTPLATE)).func_216045_a(ItemLootEntry.func_216168_a(Items.CHAINMAIL_LEGGINGS)).func_216045_a(ItemLootEntry.func_216168_a(Items.CHAINMAIL_BOOTS))));
      p_accept_1_.accept(LootTables.field_215799_ah, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.COOKED_RABBIT)).func_216045_a(ItemLootEntry.func_216168_a(Items.COOKED_CHICKEN)).func_216045_a(ItemLootEntry.func_216168_a(Items.COOKED_PORKCHOP)).func_216045_a(ItemLootEntry.func_216168_a(Items.COOKED_BEEF)).func_216045_a(ItemLootEntry.func_216168_a(Items.COOKED_MUTTON))));
      p_accept_1_.accept(LootTables.field_215800_ai, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.MAP)).func_216045_a(ItemLootEntry.func_216168_a(Items.PAPER))));
      p_accept_1_.accept(LootTables.field_215801_aj, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.REDSTONE)).func_216045_a(ItemLootEntry.func_216168_a(Items.LAPIS_LAZULI))));
      p_accept_1_.accept(LootTables.field_215802_ak, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.BREAD)).func_216045_a(ItemLootEntry.func_216168_a(Items.PUMPKIN_PIE)).func_216045_a(ItemLootEntry.func_216168_a(Items.COOKIE))));
      p_accept_1_.accept(LootTables.field_215803_al, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.COD)).func_216045_a(ItemLootEntry.func_216168_a(Items.SALMON))));
      p_accept_1_.accept(LootTables.field_215804_am, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.ARROW).func_216086_a(26)).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218596_0_) -> {
         p_218596_0_.putString("Potion", "minecraft:swiftness");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218597_0_) -> {
         p_218597_0_.putString("Potion", "minecraft:slowness");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218608_0_) -> {
         p_218608_0_.putString("Potion", "minecraft:strength");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218606_0_) -> {
         p_218606_0_.putString("Potion", "minecraft:healing");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218603_0_) -> {
         p_218603_0_.putString("Potion", "minecraft:harming");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218607_0_) -> {
         p_218607_0_.putString("Potion", "minecraft:leaping");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218601_0_) -> {
         p_218601_0_.putString("Potion", "minecraft:regeneration");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218600_0_) -> {
         p_218600_0_.putString("Potion", "minecraft:fire_resistance");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218599_0_) -> {
         p_218599_0_.putString("Potion", "minecraft:water_breathing");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218598_0_) -> {
         p_218598_0_.putString("Potion", "minecraft:invisibility");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218605_0_) -> {
         p_218605_0_.putString("Potion", "minecraft:night_vision");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218602_0_) -> {
         p_218602_0_.putString("Potion", "minecraft:weakness");
      })))).func_216045_a(ItemLootEntry.func_216168_a(Items.TIPPED_ARROW).func_212841_b_(SetCount.func_215932_a(RandomValueRange.func_215837_a(0.0F, 1.0F))).func_212841_b_(SetNBT.func_215952_a(Util.make(new CompoundNBT(), (p_218604_0_) -> {
         p_218604_0_.putString("Potion", "minecraft:poison");
      }))))));
      p_accept_1_.accept(LootTables.field_215805_an, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.LEATHER))));
      p_accept_1_.accept(LootTables.field_215806_ao, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.BOOK))));
      p_accept_1_.accept(LootTables.field_215807_ap, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221776_cx))));
      p_accept_1_.accept(LootTables.field_215808_aq, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221603_aE)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221604_aF)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221605_aG)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221606_aH)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221607_aI)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221608_aJ)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221609_aK)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221610_aL)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221611_aM)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221612_aN)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221613_aO)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221614_aP)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221615_aQ)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221616_aR)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221617_aS)).func_216045_a(ItemLootEntry.func_216168_a(Items.field_221618_aT))));
      p_accept_1_.accept(LootTables.field_215809_ar, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.STONE_PICKAXE)).func_216045_a(ItemLootEntry.func_216168_a(Items.STONE_AXE)).func_216045_a(ItemLootEntry.func_216168_a(Items.STONE_HOE)).func_216045_a(ItemLootEntry.func_216168_a(Items.STONE_SHOVEL))));
      p_accept_1_.accept(LootTables.field_215810_as, LootTable.func_216119_b().func_216040_a(LootPool.func_216096_a().func_216046_a(ConstantRange.func_215835_a(1)).func_216045_a(ItemLootEntry.func_216168_a(Items.STONE_AXE)).func_216045_a(ItemLootEntry.func_216168_a(Items.GOLDEN_AXE)).func_216045_a(ItemLootEntry.func_216168_a(Items.IRON_AXE))));
   }
}