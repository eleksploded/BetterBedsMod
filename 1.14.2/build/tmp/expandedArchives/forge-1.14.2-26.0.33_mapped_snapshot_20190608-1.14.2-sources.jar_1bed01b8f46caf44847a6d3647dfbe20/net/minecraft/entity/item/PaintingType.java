package net.minecraft.entity.item;

import net.minecraft.util.registry.Registry;

public class PaintingType extends net.minecraftforge.registries.ForgeRegistryEntry<PaintingType> {
   public static final PaintingType KEBAB = func_221119_a("kebab", 16, 16);
   public static final PaintingType AZTEC = func_221119_a("aztec", 16, 16);
   public static final PaintingType ALBAN = func_221119_a("alban", 16, 16);
   public static final PaintingType AZTEC2 = func_221119_a("aztec2", 16, 16);
   public static final PaintingType BOMB = func_221119_a("bomb", 16, 16);
   public static final PaintingType PLANT = func_221119_a("plant", 16, 16);
   public static final PaintingType WASTELAND = func_221119_a("wasteland", 16, 16);
   public static final PaintingType POOL = func_221119_a("pool", 32, 16);
   public static final PaintingType COURBET = func_221119_a("courbet", 32, 16);
   public static final PaintingType SEA = func_221119_a("sea", 32, 16);
   public static final PaintingType SUNSET = func_221119_a("sunset", 32, 16);
   public static final PaintingType CREEBET = func_221119_a("creebet", 32, 16);
   public static final PaintingType WANDERER = func_221119_a("wanderer", 16, 32);
   public static final PaintingType GRAHAM = func_221119_a("graham", 16, 32);
   public static final PaintingType MATCH = func_221119_a("match", 32, 32);
   public static final PaintingType BUST = func_221119_a("bust", 32, 32);
   public static final PaintingType STAGE = func_221119_a("stage", 32, 32);
   public static final PaintingType VOID = func_221119_a("void", 32, 32);
   public static final PaintingType SKULL_AND_ROSES = func_221119_a("skull_and_roses", 32, 32);
   public static final PaintingType WITHER = func_221119_a("wither", 32, 32);
   public static final PaintingType FIGHTERS = func_221119_a("fighters", 64, 32);
   public static final PaintingType POINTER = func_221119_a("pointer", 64, 64);
   public static final PaintingType PIGSCENE = func_221119_a("pigscene", 64, 64);
   public static final PaintingType BURNING_SKULL = func_221119_a("burning_skull", 64, 64);
   public static final PaintingType SKELETON = func_221119_a("skeleton", 64, 48);
   public static final PaintingType DONKEY_KONG = func_221119_a("donkey_kong", 64, 48);
   private final int width;
   private final int height;

   private static PaintingType func_221119_a(String p_221119_0_, int p_221119_1_, int p_221119_2_) {
      return Registry.register(Registry.field_212620_i, p_221119_0_, new PaintingType(p_221119_1_, p_221119_2_));
   }

   public PaintingType(int p_i50222_1_, int p_i50222_2_) {
      this.width = p_i50222_1_;
      this.height = p_i50222_2_;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }
}