package net.minecraft.world.gen;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerationStage {
   public static enum Carving {
      AIR("air"),
      LIQUID("liquid");

      private static final Map<String, GenerationStage.Carving> field_222673_c = Arrays.stream(values()).collect(Collectors.toMap(GenerationStage.Carving::func_222671_a, (p_222672_0_) -> {
         return p_222672_0_;
      }));
      private final String field_222674_d;

      private Carving(String p_i50879_3_) {
         this.field_222674_d = p_i50879_3_;
      }

      public String func_222671_a() {
         return this.field_222674_d;
      }
   }

   public static enum Decoration {
      RAW_GENERATION("raw_generation"),
      LOCAL_MODIFICATIONS("local_modifications"),
      UNDERGROUND_STRUCTURES("underground_structures"),
      SURFACE_STRUCTURES("surface_structures"),
      UNDERGROUND_ORES("underground_ores"),
      UNDERGROUND_DECORATION("underground_decoration"),
      VEGETAL_DECORATION("vegetal_decoration"),
      TOP_LAYER_MODIFICATION("top_layer_modification");

      private static final Map<String, GenerationStage.Decoration> field_222677_i = Arrays.stream(values()).collect(Collectors.toMap(GenerationStage.Decoration::func_222676_a, (p_222675_0_) -> {
         return p_222675_0_;
      }));
      private final String field_222678_j;

      private Decoration(String p_i50878_3_) {
         this.field_222678_j = p_i50878_3_;
      }

      public String func_222676_a() {
         return this.field_222678_j;
      }
   }
}