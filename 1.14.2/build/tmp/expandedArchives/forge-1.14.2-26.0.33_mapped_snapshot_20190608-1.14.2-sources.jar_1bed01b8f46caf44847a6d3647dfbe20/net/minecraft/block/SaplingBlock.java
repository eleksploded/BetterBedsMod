package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.trees.Tree;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SaplingBlock extends BushBlock implements IGrowable {
   public static final IntegerProperty STAGE = BlockStateProperties.STAGE_0_1;
   protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
   private final Tree field_196387_c;

   protected SaplingBlock(Tree p_i48337_1_, Block.Properties properties) {
      super(properties);
      this.field_196387_c = p_i48337_1_;
      this.setDefaultState(this.stateContainer.getBaseState().with(STAGE, Integer.valueOf(0)));
   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      return SHAPE;
   }

   public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
      super.tick(state, worldIn, pos, random);
      if (!worldIn.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
      if (worldIn.getLight(pos.up()) >= 9 && random.nextInt(7) == 0) {
         this.grow(worldIn, pos, state, random);
      }

   }

   public void grow(IWorld worldIn, BlockPos pos, BlockState state, Random rand) {
      if (state.get(STAGE) == 0) {
         worldIn.setBlockState(pos, state.cycle(STAGE), 4);
      } else {
         if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(worldIn, rand, pos)) return;
         this.field_196387_c.spawn(worldIn, pos, state, rand);
      }

   }

   /**
    * Whether this IGrowable can grow
    */
   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return true;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return (double)worldIn.rand.nextFloat() < 0.45D;
   }

   public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
      this.grow(worldIn, pos, state, rand);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(STAGE);
   }
}