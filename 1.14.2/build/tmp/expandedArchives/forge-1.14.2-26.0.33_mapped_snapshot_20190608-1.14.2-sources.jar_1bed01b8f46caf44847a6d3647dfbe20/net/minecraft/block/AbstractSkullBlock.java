package net.minecraft.block;

import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractSkullBlock extends ContainerBlock {
   private final SkullBlock.ISkullType field_196293_a;

   public AbstractSkullBlock(SkullBlock.ISkullType iSkullType, Block.Properties properties) {
      super(properties);
      this.field_196293_a = iSkullType;
   }

   /**
    * @deprecated call via {@link IBlockState#hasCustomBreakingProgress()} whenever possible. Implementing/overriding is
    * fine.
    */
   @OnlyIn(Dist.CLIENT)
   public boolean hasCustomBreakingProgress(BlockState state) {
      return true;
   }

   public TileEntity createNewTileEntity(IBlockReader worldIn) {
      return new SkullTileEntity();
   }

   @OnlyIn(Dist.CLIENT)
   public SkullBlock.ISkullType getSkullType() {
      return this.field_196293_a;
   }
}