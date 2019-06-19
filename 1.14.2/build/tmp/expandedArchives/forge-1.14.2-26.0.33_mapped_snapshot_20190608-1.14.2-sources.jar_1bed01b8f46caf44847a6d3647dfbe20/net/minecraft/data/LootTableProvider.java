package net.minecraft.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.data.loot.GiftLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.ValidationResults;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableProvider implements IDataProvider {
   private static final Logger field_218441_b = LogManager.getLogger();
   private static final Gson field_218442_c = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
   private final DataGenerator field_218443_d;
   private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> field_218444_e = ImmutableList.of(Pair.of(FishingLootTables::new, LootParameterSets.field_216262_c), Pair.of(ChestLootTables::new, LootParameterSets.field_216261_b), Pair.of(EntityLootTables::new, LootParameterSets.field_216263_d), Pair.of(BlockLootTables::new, LootParameterSets.field_216267_h), Pair.of(GiftLootTables::new, LootParameterSets.field_216264_e));

   public LootTableProvider(DataGenerator p_i50789_1_) {
      this.field_218443_d = p_i50789_1_;
   }

   /**
    * Performs this provider's action.
    */
   public void act(DirectoryCache cache) {
      Path path = this.field_218443_d.getOutputFolder();
      Map<ResourceLocation, LootTable> map = Maps.newHashMap();
      this.field_218444_e.forEach((p_218438_1_) -> {
         p_218438_1_.getFirst().get().accept((p_218437_2_, p_218437_3_) -> {
            if (map.put(p_218437_2_, p_218437_3_.func_216039_a(p_218438_1_.getSecond()).func_216038_b()) != null) {
               throw new IllegalStateException("Duplicate loot table " + p_218437_2_);
            }
         });
      });
      ValidationResults validationresults = new ValidationResults();

      for(ResourceLocation resourcelocation : Sets.difference(LootTables.func_215796_a(), map.keySet())) {
         validationresults.func_216105_a("Missing built-in table: " + resourcelocation);
      }

      map.forEach((p_218436_2_, p_218436_3_) -> {
         LootTableManager.func_215302_a(validationresults, p_218436_2_, p_218436_3_, map::get);
      });
      Multimap<String, String> multimap = validationresults.func_216106_a();
      if (!multimap.isEmpty()) {
         multimap.forEach((p_218435_0_, p_218435_1_) -> {
            field_218441_b.warn("Found validation problem in " + p_218435_0_ + ": " + p_218435_1_);
         });
         throw new IllegalStateException("Failed to validate loot tables, see logs");
      } else {
         map.forEach((p_218440_2_, p_218440_3_) -> {
            Path path1 = func_218439_a(path, p_218440_2_);

            try {
               IDataProvider.func_218426_a(field_218442_c, cache, LootTableManager.func_215301_a(p_218440_3_), path1);
            } catch (IOException ioexception) {
               field_218441_b.error("Couldn't save loot table {}", path1, ioexception);
            }

         });
      }
   }

   private static Path func_218439_a(Path p_218439_0_, ResourceLocation p_218439_1_) {
      return p_218439_0_.resolve("data/" + p_218439_1_.getNamespace() + "/loot_tables/" + p_218439_1_.getPath() + ".json");
   }

   /**
    * Gets a name for this provider, to use in logging.
    */
   public String getName() {
      return "LootTables";
   }
}