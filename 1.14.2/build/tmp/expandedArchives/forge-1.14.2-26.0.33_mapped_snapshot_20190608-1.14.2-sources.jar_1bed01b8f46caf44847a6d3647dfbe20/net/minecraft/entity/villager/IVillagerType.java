package net.minecraft.entity.villager;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public interface IVillagerType {
   IVillagerType field_221173_a = func_221171_a("desert");
   IVillagerType field_221174_b = func_221171_a("jungle");
   IVillagerType field_221175_c = func_221171_a("plains");
   IVillagerType field_221176_d = func_221171_a("savanna");
   IVillagerType field_221177_e = func_221171_a("snow");
   IVillagerType field_221178_f = func_221171_a("swamp");
   IVillagerType field_221179_g = func_221171_a("taiga");
   Map<Biome, IVillagerType> field_221180_h = Util.make(Maps.newHashMap(), (p_221172_0_) -> {
      p_221172_0_.put(Biomes.BADLANDS, field_221173_a);
      p_221172_0_.put(Biomes.BADLANDS_PLATEAU, field_221173_a);
      p_221172_0_.put(Biomes.DESERT, field_221173_a);
      p_221172_0_.put(Biomes.DESERT_HILLS, field_221173_a);
      p_221172_0_.put(Biomes.DESERT_LAKES, field_221173_a);
      p_221172_0_.put(Biomes.ERODED_BADLANDS, field_221173_a);
      p_221172_0_.put(Biomes.MODIFIED_BADLANDS_PLATEAU, field_221173_a);
      p_221172_0_.put(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, field_221173_a);
      p_221172_0_.put(Biomes.WOODED_BADLANDS_PLATEAU, field_221173_a);
      p_221172_0_.put(Biomes.field_222370_aw, field_221174_b);
      p_221172_0_.put(Biomes.field_222371_ax, field_221174_b);
      p_221172_0_.put(Biomes.JUNGLE, field_221174_b);
      p_221172_0_.put(Biomes.JUNGLE_EDGE, field_221174_b);
      p_221172_0_.put(Biomes.JUNGLE_HILLS, field_221174_b);
      p_221172_0_.put(Biomes.MODIFIED_JUNGLE, field_221174_b);
      p_221172_0_.put(Biomes.MODIFIED_JUNGLE_EDGE, field_221174_b);
      p_221172_0_.put(Biomes.SAVANNA_PLATEAU, field_221176_d);
      p_221172_0_.put(Biomes.SAVANNA, field_221176_d);
      p_221172_0_.put(Biomes.SHATTERED_SAVANNA, field_221176_d);
      p_221172_0_.put(Biomes.SHATTERED_SAVANNA_PLATEAU, field_221176_d);
      p_221172_0_.put(Biomes.DEEP_FROZEN_OCEAN, field_221177_e);
      p_221172_0_.put(Biomes.FROZEN_OCEAN, field_221177_e);
      p_221172_0_.put(Biomes.FROZEN_RIVER, field_221177_e);
      p_221172_0_.put(Biomes.ICE_SPIKES, field_221177_e);
      p_221172_0_.put(Biomes.SNOWY_BEACH, field_221177_e);
      p_221172_0_.put(Biomes.SNOWY_MOUNTAINS, field_221177_e);
      p_221172_0_.put(Biomes.SNOWY_TAIGA, field_221177_e);
      p_221172_0_.put(Biomes.SNOWY_TAIGA_HILLS, field_221177_e);
      p_221172_0_.put(Biomes.SNOWY_TAIGA_MOUNTAINS, field_221177_e);
      p_221172_0_.put(Biomes.SNOWY_TUNDRA, field_221177_e);
      p_221172_0_.put(Biomes.SWAMP, field_221178_f);
      p_221172_0_.put(Biomes.SWAMP_HILLS, field_221178_f);
      p_221172_0_.put(Biomes.GIANT_SPRUCE_TAIGA, field_221179_g);
      p_221172_0_.put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, field_221179_g);
      p_221172_0_.put(Biomes.GIANT_TREE_TAIGA, field_221179_g);
      p_221172_0_.put(Biomes.GIANT_TREE_TAIGA_HILLS, field_221179_g);
      p_221172_0_.put(Biomes.GRAVELLY_MOUNTAINS, field_221179_g);
      p_221172_0_.put(Biomes.MODIFIED_GRAVELLY_MOUNTAINS, field_221179_g);
      p_221172_0_.put(Biomes.MOUNTAIN_EDGE, field_221179_g);
      p_221172_0_.put(Biomes.MOUNTAINS, field_221179_g);
      p_221172_0_.put(Biomes.TAIGA, field_221179_g);
      p_221172_0_.put(Biomes.TAIGA_HILLS, field_221179_g);
      p_221172_0_.put(Biomes.TAIGA_MOUNTAINS, field_221179_g);
      p_221172_0_.put(Biomes.WOODED_MOUNTAINS, field_221179_g);
   });

   static IVillagerType func_221171_a(final String p_221171_0_) {
      return Registry.register(Registry.field_218369_K, new ResourceLocation(p_221171_0_), new IVillagerType() {
         public String toString() {
            return p_221171_0_;
         }
      });
   }

   static IVillagerType func_221170_a(Biome p_221170_0_) {
      return field_221180_h.getOrDefault(p_221170_0_, field_221175_c);
   }
}