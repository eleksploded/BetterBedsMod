package net.minecraft.world.gen.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum HillsLayer implements IAreaTransformer2, IDimOffset1Transformer {
   INSTANCE;

   private static final Logger LOGGER = LogManager.getLogger();
   private static final int BIRCH_FOREST = Registry.field_212624_m.getId(Biomes.BIRCH_FOREST);
   private static final int BIRCH_FOREST_HILLS = Registry.field_212624_m.getId(Biomes.BIRCH_FOREST_HILLS);
   private static final int DESERT = Registry.field_212624_m.getId(Biomes.DESERT);
   private static final int DESERT_HILLS = Registry.field_212624_m.getId(Biomes.DESERT_HILLS);
   private static final int MOUNTAINS = Registry.field_212624_m.getId(Biomes.MOUNTAINS);
   private static final int WOODED_MOUNTAINS = Registry.field_212624_m.getId(Biomes.WOODED_MOUNTAINS);
   private static final int FOREST = Registry.field_212624_m.getId(Biomes.FOREST);
   private static final int WOODED_HILLS = Registry.field_212624_m.getId(Biomes.WOODED_HILLS);
   private static final int SNOWY_TUNDRA = Registry.field_212624_m.getId(Biomes.SNOWY_TUNDRA);
   private static final int SNOWY_MOUNTAINS = Registry.field_212624_m.getId(Biomes.SNOWY_MOUNTAINS);
   private static final int JUNGLE = Registry.field_212624_m.getId(Biomes.JUNGLE);
   private static final int JUNGLE_HILLS = Registry.field_212624_m.getId(Biomes.JUNGLE_HILLS);
   private static final int field_215729_o = Registry.field_212624_m.getId(Biomes.field_222370_aw);
   private static final int field_215730_p = Registry.field_212624_m.getId(Biomes.field_222371_ax);
   private static final int BADLANDS = Registry.field_212624_m.getId(Biomes.BADLANDS);
   private static final int WOODED_BADLANDS_PLATEAU = Registry.field_212624_m.getId(Biomes.WOODED_BADLANDS_PLATEAU);
   private static final int PLAINS = Registry.field_212624_m.getId(Biomes.PLAINS);
   private static final int GIANT_TREE_TAIGA = Registry.field_212624_m.getId(Biomes.GIANT_TREE_TAIGA);
   private static final int GIANT_TREE_TAIGA_HILLS = Registry.field_212624_m.getId(Biomes.GIANT_TREE_TAIGA_HILLS);
   private static final int DARK_FOREST = Registry.field_212624_m.getId(Biomes.DARK_FOREST);
   private static final int SAVANNA = Registry.field_212624_m.getId(Biomes.SAVANNA);
   private static final int SAVANA_PLATEAU = Registry.field_212624_m.getId(Biomes.SAVANNA_PLATEAU);
   private static final int TAIGA = Registry.field_212624_m.getId(Biomes.TAIGA);
   private static final int SNOWY_TAIGA = Registry.field_212624_m.getId(Biomes.SNOWY_TAIGA);
   private static final int SNOWY_TAIGA_HILLS = Registry.field_212624_m.getId(Biomes.SNOWY_TAIGA_HILLS);
   private static final int TAIGA_HILLS = Registry.field_212624_m.getId(Biomes.TAIGA_HILLS);

   public int func_215723_a(INoiseRandom p_215723_1_, IArea p_215723_2_, IArea p_215723_3_, int p_215723_4_, int p_215723_5_) {
      int i = p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 1), this.func_215722_b(p_215723_5_ + 1));
      int j = p_215723_3_.getValue(this.func_215721_a(p_215723_4_ + 1), this.func_215722_b(p_215723_5_ + 1));
      if (i > 255) {
         LOGGER.debug("old! {}", (int)i);
      }

      int k = (j - 2) % 29;
      if (!LayerUtil.isShallowOcean(i) && j >= 2 && k == 1) {
         Biome biome = Registry.field_212624_m.getByValue(i);
         if (biome == null || !biome.isMutation()) {
            Biome biome2 = Biome.getMutationForBiome(biome);
            return biome2 == null ? i : Registry.field_212624_m.getId(biome2);
         }
      }

      if (p_215723_1_.random(3) == 0 || k == 0) {
         int l = i;
         if (i == DESERT) {
            l = DESERT_HILLS;
         } else if (i == FOREST) {
            l = WOODED_HILLS;
         } else if (i == BIRCH_FOREST) {
            l = BIRCH_FOREST_HILLS;
         } else if (i == DARK_FOREST) {
            l = PLAINS;
         } else if (i == TAIGA) {
            l = TAIGA_HILLS;
         } else if (i == GIANT_TREE_TAIGA) {
            l = GIANT_TREE_TAIGA_HILLS;
         } else if (i == SNOWY_TAIGA) {
            l = SNOWY_TAIGA_HILLS;
         } else if (i == PLAINS) {
            l = p_215723_1_.random(3) == 0 ? WOODED_HILLS : FOREST;
         } else if (i == SNOWY_TUNDRA) {
            l = SNOWY_MOUNTAINS;
         } else if (i == JUNGLE) {
            l = JUNGLE_HILLS;
         } else if (i == field_215729_o) {
            l = field_215730_p;
         } else if (i == LayerUtil.OCEAN) {
            l = LayerUtil.DEEP_OCEAN;
         } else if (i == LayerUtil.LUKEWARM_OCEAN) {
            l = LayerUtil.DEEP_LUKEWARM_OCEAN;
         } else if (i == LayerUtil.COLD_OCEAN) {
            l = LayerUtil.DEEP_COLD_OCEAN;
         } else if (i == LayerUtil.FROZEN_OCEAN) {
            l = LayerUtil.DEEP_FROZEN_OCEAN;
         } else if (i == MOUNTAINS) {
            l = WOODED_MOUNTAINS;
         } else if (i == SAVANNA) {
            l = SAVANA_PLATEAU;
         } else if (LayerUtil.areBiomesSimilar(i, WOODED_BADLANDS_PLATEAU)) {
            l = BADLANDS;
         } else if ((i == LayerUtil.DEEP_OCEAN || i == LayerUtil.DEEP_LUKEWARM_OCEAN || i == LayerUtil.DEEP_COLD_OCEAN || i == LayerUtil.DEEP_FROZEN_OCEAN) && p_215723_1_.random(3) == 0) {
            l = p_215723_1_.random(2) == 0 ? PLAINS : FOREST;
         }

         if (k == 0 && l != i) {
            Biome biome1 = Biome.getMutationForBiome(Registry.field_212624_m.getByValue(l));
            l = biome1 == null ? i : Registry.field_212624_m.getId(biome1);
         }

         if (l != i) {
            int i1 = 0;
            if (LayerUtil.areBiomesSimilar(p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 1), this.func_215722_b(p_215723_5_ + 0)), i)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 2), this.func_215722_b(p_215723_5_ + 1)), i)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 0), this.func_215722_b(p_215723_5_ + 1)), i)) {
               ++i1;
            }

            if (LayerUtil.areBiomesSimilar(p_215723_2_.getValue(this.func_215721_a(p_215723_4_ + 1), this.func_215722_b(p_215723_5_ + 2)), i)) {
               ++i1;
            }

            if (i1 >= 3) {
               return l;
            }
         }
      }

      return i;
   }
}