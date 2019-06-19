package net.minecraft.world.gen.feature.jigsaw;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class EmptyJigsawPiece extends JigsawPiece {
   public static final EmptyJigsawPiece field_214856_a = new EmptyJigsawPiece();

   private EmptyJigsawPiece() {
      super(JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING);
   }

   public List<Template.BlockInfo> func_214849_a(TemplateManager p_214849_1_, BlockPos p_214849_2_, Rotation p_214849_3_, Random p_214849_4_) {
      return Collections.emptyList();
   }

   public MutableBoundingBox func_214852_a(TemplateManager p_214852_1_, BlockPos p_214852_2_, Rotation p_214852_3_) {
      return MutableBoundingBox.getNewBoundingBox();
   }

   public boolean func_214848_a(TemplateManager p_214848_1_, IWorld p_214848_2_, BlockPos p_214848_3_, Rotation p_214848_4_, MutableBoundingBox p_214848_5_, Random p_214848_6_) {
      return true;
   }

   public IJigsawDeserializer func_214853_a() {
      return IJigsawDeserializer.field_214931_e;
   }

   public <T> Dynamic<T> func_214851_a(DynamicOps<T> p_214851_1_) {
      return new Dynamic<>(p_214851_1_, p_214851_1_.emptyMap());
   }

   public String toString() {
      return "Empty";
   }
}