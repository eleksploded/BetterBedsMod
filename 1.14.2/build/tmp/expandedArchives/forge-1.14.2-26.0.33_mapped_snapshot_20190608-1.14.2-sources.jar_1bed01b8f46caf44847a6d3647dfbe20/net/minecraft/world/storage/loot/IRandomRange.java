package net.minecraft.world.storage.loot;

import java.util.Random;
import net.minecraft.util.ResourceLocation;

public interface IRandomRange {
   ResourceLocation field_215831_a = new ResourceLocation("constant");
   ResourceLocation field_215832_b = new ResourceLocation("uniform");
   ResourceLocation field_215833_c = new ResourceLocation("binomial");

   int generateInt(Random rand);

   ResourceLocation func_215830_a();
}