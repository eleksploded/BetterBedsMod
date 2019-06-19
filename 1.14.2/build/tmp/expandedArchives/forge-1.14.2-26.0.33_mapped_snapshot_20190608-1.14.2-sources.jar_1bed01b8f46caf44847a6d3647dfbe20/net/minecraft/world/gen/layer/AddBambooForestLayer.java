package net.minecraft.world.gen.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC1Transformer;

public enum AddBambooForestLayer implements IC1Transformer {
   INSTANCE;

   private static final int field_215732_b = Registry.field_212624_m.getId(Biomes.JUNGLE);
   private static final int field_215733_c = Registry.field_212624_m.getId(Biomes.field_222370_aw);

   public int apply(INoiseRandom context, int value) {
      return context.random(10) == 0 && value == field_215732_b ? field_215733_c : value;
   }
}