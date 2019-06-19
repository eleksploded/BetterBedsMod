package net.minecraft.block;

import net.minecraft.item.DyeColor;
import net.minecraft.util.BlockRenderLayer;

public class StainedGlassBlock extends AbstractGlassBlock implements IBeaconBeamColorProvider {
   private final DyeColor field_196458_a;

   public StainedGlassBlock(DyeColor colorIn, Block.Properties properties) {
      super(properties);
      this.field_196458_a = colorIn;
   }

   public DyeColor getColor() {
      return this.field_196458_a;
   }

   /**
    * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
    * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
    */
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.TRANSLUCENT;
   }
}