package net.minecraft.world.gen.surfacebuilders;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class SurfaceBuilderConfig implements ISurfaceBuilderConfig {
   private final BlockState field_204111_a;
   private final BlockState field_204112_b;
   private final BlockState field_204113_c;

   public SurfaceBuilderConfig(BlockState topMaterial, BlockState underMaterial, BlockState underWaterMaterial) {
      this.field_204111_a = topMaterial;
      this.field_204112_b = underMaterial;
      this.field_204113_c = underWaterMaterial;
   }

   public BlockState getTopMaterial() {
      return this.field_204111_a;
   }

   public BlockState getUnderMaterial() {
      return this.field_204112_b;
   }

   public BlockState getUnderWaterMaterial() {
      return this.field_204113_c;
   }

   public static SurfaceBuilderConfig func_215455_a(Dynamic<?> p_215455_0_) {
      BlockState blockstate = p_215455_0_.get("top_material").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      BlockState blockstate1 = p_215455_0_.get("under_material").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      BlockState blockstate2 = p_215455_0_.get("underwater_material").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      return new SurfaceBuilderConfig(blockstate, blockstate1, blockstate2);
   }
}