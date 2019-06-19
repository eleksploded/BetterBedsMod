package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SweetBerryBushBlock extends BushBlock implements IGrowable {
   public static final IntegerProperty field_220125_a = BlockStateProperties.AGE_0_3;
   private static final VoxelShape field_220126_b = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);
   private static final VoxelShape field_220127_c = Block.makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

   public SweetBerryBushBlock(Block.Properties p_i49971_1_) {
      super(p_i49971_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(field_220125_a, Integer.valueOf(0)));
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
      return new ItemStack(Items.field_222112_pR);
   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      if (p_220053_1_.get(field_220125_a) == 0) {
         return field_220126_b;
      } else {
         return p_220053_1_.get(field_220125_a) < 3 ? field_220127_c : super.getShape(p_220053_1_, p_220053_2_, p_220053_3_, p_220053_4_);
      }
   }

   public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
      super.tick(state, worldIn, pos, random);
      int i = state.get(field_220125_a);
      if (i < 3 && random.nextInt(5) == 0 && worldIn.getLightSubtracted(pos.up(), 0) >= 9) {
         worldIn.setBlockState(pos, state.with(field_220125_a, Integer.valueOf(i + 1)), 2);
      }

   }

   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
      if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.field_220356_B) {
         entityIn.setMotionMultiplier(state, new Vec3d((double)0.8F, 0.75D, (double)0.8F));
         if (!worldIn.isRemote && state.get(field_220125_a) > 0 && (entityIn.lastTickPosX != entityIn.posX || entityIn.lastTickPosZ != entityIn.posZ)) {
            double d0 = Math.abs(entityIn.posX - entityIn.lastTickPosX);
            double d1 = Math.abs(entityIn.posZ - entityIn.lastTickPosZ);
            if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
               entityIn.attackEntityFrom(DamageSource.field_220302_v, 1.0F);
            }
         }

      }
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      int i = p_220051_1_.get(field_220125_a);
      boolean flag = i == 3;
      if (!flag && p_220051_4_.getHeldItem(p_220051_5_).getItem() == Items.BONE_MEAL) {
         return false;
      } else if (i > 1) {
         int j = 1 + p_220051_2_.rand.nextInt(2);
         spawnAsEntity(p_220051_2_, p_220051_3_, new ItemStack(Items.field_222112_pR, j + (flag ? 1 : 0)));
         p_220051_2_.playSound((PlayerEntity)null, p_220051_3_, SoundEvents.field_219693_lB, SoundCategory.BLOCKS, 1.0F, 0.8F + p_220051_2_.rand.nextFloat() * 0.4F);
         p_220051_2_.setBlockState(p_220051_3_, p_220051_1_.with(field_220125_a, Integer.valueOf(1)), 2);
         return true;
      } else {
         return super.onBlockActivated(p_220051_1_, p_220051_2_, p_220051_3_, p_220051_4_, p_220051_5_, p_220051_6_);
      }
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_220125_a);
   }

   /**
    * Whether this IGrowable can grow
    */
   public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
      return state.get(field_220125_a) < 3;
   }

   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void grow(World worldIn, Random rand, BlockPos pos, BlockState state) {
      int i = Math.min(3, state.get(field_220125_a) + 1);
      worldIn.setBlockState(pos, state.with(field_220125_a, Integer.valueOf(i)), 2);
   }
}