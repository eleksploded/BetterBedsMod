package net.minecraft.block;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ISidedInventoryProvider;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ComposterBlock extends Block implements ISidedInventoryProvider {
   public static final IntegerProperty field_220298_a = BlockStateProperties.field_222509_am;
   public static final Object2FloatMap<IItemProvider> field_220299_b = new Object2FloatOpenHashMap<>();
   public static final VoxelShape field_220300_c = VoxelShapes.fullCube();
   private static final VoxelShape[] field_220301_d = Util.make(new VoxelShape[9], (p_220291_0_) -> {
      for(int i = 0; i < 8; ++i) {
         p_220291_0_[i] = VoxelShapes.combineAndSimplify(field_220300_c, Block.makeCuboidShape(2.0D, (double)Math.max(2, 1 + i * 2), 2.0D, 14.0D, 16.0D, 14.0D), IBooleanFunction.ONLY_FIRST);
      }

      p_220291_0_[8] = p_220291_0_[7];
   });

   public static void func_220297_d() {
      field_220299_b.defaultReturnValue(-1.0F);
      float f = 0.3F;
      float f1 = 0.5F;
      float f2 = 0.65F;
      float f3 = 0.85F;
      float f4 = 1.0F;
      func_220290_a(0.3F, Items.field_221640_ah);
      func_220290_a(0.3F, Items.field_221634_ae);
      func_220290_a(0.3F, Items.field_221636_af);
      func_220290_a(0.3F, Items.field_221644_aj);
      func_220290_a(0.3F, Items.field_221642_ai);
      func_220290_a(0.3F, Items.field_221638_ag);
      func_220290_a(0.3F, Items.field_221592_t);
      func_220290_a(0.3F, Items.field_221593_u);
      func_220290_a(0.3F, Items.field_221594_v);
      func_220290_a(0.3F, Items.field_221595_w);
      func_220290_a(0.3F, Items.field_221596_x);
      func_220290_a(0.3F, Items.field_221597_y);
      func_220290_a(0.3F, Items.BEETROOT_SEEDS);
      func_220290_a(0.3F, Items.DRIED_KELP);
      func_220290_a(0.3F, Items.field_221674_ay);
      func_220290_a(0.3F, Items.field_222066_kO);
      func_220290_a(0.3F, Items.MELON_SEEDS);
      func_220290_a(0.3F, Items.PUMPKIN_SEEDS);
      func_220290_a(0.3F, Items.field_221600_aB);
      func_220290_a(0.3F, Items.field_222112_pR);
      func_220290_a(0.3F, Items.WHEAT_SEEDS);
      func_220290_a(0.5F, Items.field_222067_kP);
      func_220290_a(0.5F, Items.field_221916_fp);
      func_220290_a(0.5F, Items.field_221774_cw);
      func_220290_a(0.5F, Items.field_222065_kN);
      func_220290_a(0.5F, Items.field_221796_dh);
      func_220290_a(0.5F, Items.MELON_SLICE);
      func_220290_a(0.65F, Items.field_221601_aC);
      func_220290_a(0.65F, Items.field_221816_dr);
      func_220290_a(0.65F, Items.field_221687_cF);
      func_220290_a(0.65F, Items.field_221689_cG);
      func_220290_a(0.65F, Items.field_221794_dg);
      func_220290_a(0.65F, Items.APPLE);
      func_220290_a(0.65F, Items.BEETROOT);
      func_220290_a(0.65F, Items.CARROT);
      func_220290_a(0.65F, Items.COCOA_BEANS);
      func_220290_a(0.65F, Items.POTATO);
      func_220290_a(0.65F, Items.WHEAT);
      func_220290_a(0.65F, Items.field_221692_bh);
      func_220290_a(0.65F, Items.field_221694_bi);
      func_220290_a(0.65F, Items.field_221788_dd);
      func_220290_a(0.65F, Items.field_221619_aU);
      func_220290_a(0.65F, Items.field_221620_aV);
      func_220290_a(0.65F, Items.field_221621_aW);
      func_220290_a(0.65F, Items.field_221622_aX);
      func_220290_a(0.65F, Items.field_221623_aY);
      func_220290_a(0.65F, Items.field_221624_aZ);
      func_220290_a(0.65F, Items.field_221678_ba);
      func_220290_a(0.65F, Items.field_221680_bb);
      func_220290_a(0.65F, Items.field_221682_bc);
      func_220290_a(0.65F, Items.field_221684_bd);
      func_220290_a(0.65F, Items.field_221686_be);
      func_220290_a(0.65F, Items.field_221688_bf);
      func_220290_a(0.65F, Items.field_221690_bg);
      func_220290_a(0.65F, Items.field_221676_az);
      func_220290_a(0.65F, Items.field_221908_fl);
      func_220290_a(0.65F, Items.field_221910_fm);
      func_220290_a(0.65F, Items.field_221912_fn);
      func_220290_a(0.65F, Items.field_221914_fo);
      func_220290_a(0.65F, Items.field_221918_fq);
      func_220290_a(0.85F, Items.field_221807_eN);
      func_220290_a(0.85F, Items.field_221784_db);
      func_220290_a(0.85F, Items.field_221786_dc);
      func_220290_a(0.85F, Items.BREAD);
      func_220290_a(0.85F, Items.BAKED_POTATO);
      func_220290_a(0.85F, Items.COOKIE);
      func_220290_a(1.0F, Items.field_222070_lD);
      func_220290_a(1.0F, Items.PUMPKIN_PIE);
   }

   private static void func_220290_a(float p_220290_0_, IItemProvider p_220290_1_) {
      field_220299_b.put(p_220290_1_.asItem(), p_220290_0_);
   }

   public ComposterBlock(Block.Properties p_i49986_1_) {
      super(p_i49986_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(field_220298_a, Integer.valueOf(0)));
   }

   @OnlyIn(Dist.CLIENT)
   public static void func_220292_a(World p_220292_0_, BlockPos p_220292_1_, boolean p_220292_2_) {
      BlockState blockstate = p_220292_0_.getBlockState(p_220292_1_);
      p_220292_0_.playSound((double)p_220292_1_.getX(), (double)p_220292_1_.getY(), (double)p_220292_1_.getZ(), p_220292_2_ ? SoundEvents.field_219622_bj : SoundEvents.field_219621_bi, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
      double d0 = blockstate.getShape(p_220292_0_, p_220292_1_).max(Direction.Axis.Y, 0.5D, 0.5D) + 0.03125D;
      double d1 = (double)0.13125F;
      double d2 = (double)0.7375F;
      Random random = p_220292_0_.getRandom();

      for(int i = 0; i < 10; ++i) {
         double d3 = random.nextGaussian() * 0.02D;
         double d4 = random.nextGaussian() * 0.02D;
         double d5 = random.nextGaussian() * 0.02D;
         p_220292_0_.addParticle(ParticleTypes.field_218420_D, (double)p_220292_1_.getX() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), (double)p_220292_1_.getY() + d0 + (double)random.nextFloat() * (1.0D - d0), (double)p_220292_1_.getZ() + (double)0.13125F + (double)0.7375F * (double)random.nextFloat(), d3, d4, d5);
      }

   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      return field_220301_d[p_220053_1_.get(field_220298_a)];
   }

   public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return field_220300_c;
   }

   public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
      return field_220301_d[0];
   }

   public void onBlockAdded(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
      if (p_220082_1_.get(field_220298_a) == 7) {
         p_220082_2_.getPendingBlockTicks().scheduleTick(p_220082_3_, p_220082_1_.getBlock(), 20);
      }

   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      int i = p_220051_1_.get(field_220298_a);
      ItemStack itemstack = p_220051_4_.getHeldItem(p_220051_5_);
      if (i < 8 && field_220299_b.containsKey(itemstack.getItem())) {
         if (i < 7 && !p_220051_2_.isRemote) {
            boolean flag = func_220293_b(p_220051_1_, p_220051_2_, p_220051_3_, itemstack);
            p_220051_2_.playEvent(1500, p_220051_3_, flag ? 1 : 0);
            if (!p_220051_4_.playerAbilities.isCreativeMode) {
               itemstack.shrink(1);
            }
         }

         return true;
      } else if (i == 8) {
         if (!p_220051_2_.isRemote) {
            float f = 0.7F;
            double d0 = (double)(p_220051_2_.rand.nextFloat() * 0.7F) + (double)0.15F;
            double d1 = (double)(p_220051_2_.rand.nextFloat() * 0.7F) + (double)0.060000002F + 0.6D;
            double d2 = (double)(p_220051_2_.rand.nextFloat() * 0.7F) + (double)0.15F;
            ItemEntity itementity = new ItemEntity(p_220051_2_, (double)p_220051_3_.getX() + d0, (double)p_220051_3_.getY() + d1, (double)p_220051_3_.getZ() + d2, new ItemStack(Items.BONE_MEAL));
            itementity.setDefaultPickupDelay();
            p_220051_2_.func_217376_c(itementity);
         }

         func_220294_d(p_220051_1_, p_220051_2_, p_220051_3_);
         p_220051_2_.playSound((PlayerEntity)null, p_220051_3_, SoundEvents.field_219620_bh, SoundCategory.BLOCKS, 1.0F, 1.0F);
         return true;
      } else {
         return false;
      }
   }

   private static void func_220294_d(BlockState p_220294_0_, IWorld p_220294_1_, BlockPos p_220294_2_) {
      p_220294_1_.setBlockState(p_220294_2_, p_220294_0_.with(field_220298_a, Integer.valueOf(0)), 3);
   }

   private static boolean func_220293_b(BlockState p_220293_0_, IWorld p_220293_1_, BlockPos p_220293_2_, ItemStack p_220293_3_) {
      int i = p_220293_0_.get(field_220298_a);
      float f = field_220299_b.getFloat(p_220293_3_.getItem());
      if ((i != 0 || !(f > 0.0F)) && !(p_220293_1_.getRandom().nextDouble() < (double)f)) {
         return false;
      } else {
         int j = i + 1;
         p_220293_1_.setBlockState(p_220293_2_, p_220293_0_.with(field_220298_a, Integer.valueOf(j)), 3);
         if (j == 7) {
            p_220293_1_.getPendingBlockTicks().scheduleTick(p_220293_2_, p_220293_0_.getBlock(), 20);
         }

         return true;
      }
   }

   public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
      if (state.get(field_220298_a) == 7) {
         worldIn.setBlockState(pos, state.cycle(field_220298_a), 3);
         worldIn.playSound((PlayerEntity)null, pos, SoundEvents.field_219623_bk, SoundCategory.BLOCKS, 1.0F, 1.0F);
      }

      super.tick(state, worldIn, pos, random);
   }

   /**
    * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
    * is fine.
    */
   public boolean hasComparatorInputOverride(BlockState state) {
      return true;
   }

   /**
    * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
    * Implementing/overriding is fine.
    */
   public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
      return blockState.get(field_220298_a);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_220298_a);
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }

   public ISidedInventory func_219966_a(BlockState p_219966_1_, IWorld p_219966_2_, BlockPos p_219966_3_) {
      int i = p_219966_1_.get(field_220298_a);
      if (i == 8) {
         return new ComposterBlock.FullInventory(p_219966_1_, p_219966_2_, p_219966_3_, new ItemStack(Items.BONE_MEAL));
      } else {
         return (ISidedInventory)(i < 7 ? new ComposterBlock.PartialInventory(p_219966_1_, p_219966_2_, p_219966_3_) : new ComposterBlock.EmptyInventory());
      }
   }

   static class EmptyInventory extends Inventory implements ISidedInventory {
      public EmptyInventory() {
         super(0);
      }

      public int[] getSlotsForFace(Direction side) {
         return new int[0];
      }

      /**
       * Returns true if automation can insert the given item in the given slot from the given side.
       */
      public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
         return false;
      }

      /**
       * Returns true if automation can extract the given item in the given slot from the given side.
       */
      public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
         return false;
      }
   }

   static class FullInventory extends Inventory implements ISidedInventory {
      private final BlockState field_213920_a;
      private final IWorld field_213921_b;
      private final BlockPos field_213922_c;
      private boolean field_213923_d;

      public FullInventory(BlockState p_i50463_1_, IWorld p_i50463_2_, BlockPos p_i50463_3_, ItemStack p_i50463_4_) {
         super(p_i50463_4_);
         this.field_213920_a = p_i50463_1_;
         this.field_213921_b = p_i50463_2_;
         this.field_213922_c = p_i50463_3_;
      }

      /**
       * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
       */
      public int getInventoryStackLimit() {
         return 1;
      }

      public int[] getSlotsForFace(Direction side) {
         return side == Direction.DOWN ? new int[]{0} : new int[0];
      }

      /**
       * Returns true if automation can insert the given item in the given slot from the given side.
       */
      public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
         return false;
      }

      /**
       * Returns true if automation can extract the given item in the given slot from the given side.
       */
      public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
         return !this.field_213923_d && direction == Direction.DOWN && stack.getItem() == Items.BONE_MEAL;
      }

      /**
       * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
       * it hasn't changed and skip it.
       */
      public void markDirty() {
         ComposterBlock.func_220294_d(this.field_213920_a, this.field_213921_b, this.field_213922_c);
         this.field_213923_d = true;
      }
   }

   static class PartialInventory extends Inventory implements ISidedInventory {
      private final BlockState field_213916_a;
      private final IWorld field_213917_b;
      private final BlockPos field_213918_c;
      private boolean field_213919_d;

      public PartialInventory(BlockState p_i50464_1_, IWorld p_i50464_2_, BlockPos p_i50464_3_) {
         super(1);
         this.field_213916_a = p_i50464_1_;
         this.field_213917_b = p_i50464_2_;
         this.field_213918_c = p_i50464_3_;
      }

      /**
       * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
       */
      public int getInventoryStackLimit() {
         return 1;
      }

      public int[] getSlotsForFace(Direction side) {
         return side == Direction.UP ? new int[]{0} : new int[0];
      }

      /**
       * Returns true if automation can insert the given item in the given slot from the given side.
       */
      public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
         return !this.field_213919_d && direction == Direction.UP && ComposterBlock.field_220299_b.containsKey(itemStackIn.getItem());
      }

      /**
       * Returns true if automation can extract the given item in the given slot from the given side.
       */
      public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
         return false;
      }

      /**
       * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
       * it hasn't changed and skip it.
       */
      public void markDirty() {
         ItemStack itemstack = this.getStackInSlot(0);
         if (!itemstack.isEmpty()) {
            this.field_213919_d = true;
            ComposterBlock.func_220293_b(this.field_213916_a, this.field_213917_b, this.field_213918_c, itemstack);
            this.removeStackFromSlot(0);
         }

      }
   }
}