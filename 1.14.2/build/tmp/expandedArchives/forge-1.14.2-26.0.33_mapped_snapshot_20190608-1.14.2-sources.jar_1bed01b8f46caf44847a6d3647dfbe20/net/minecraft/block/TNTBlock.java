package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TNTBlock extends Block {
   public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;

   public TNTBlock(Block.Properties properties) {
      super(properties);
      this.setDefaultState(this.getDefaultState().with(UNSTABLE, Boolean.valueOf(false)));
   }

   public void onBlockAdded(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
      if (p_220082_4_.getBlock() != p_220082_1_.getBlock()) {
         if (p_220082_2_.isBlockPowered(p_220082_3_)) {
            explode(p_220082_2_, p_220082_3_);
            p_220082_2_.removeBlock(p_220082_3_, false);
         }

      }
   }

   public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
      if (p_220069_2_.isBlockPowered(p_220069_3_)) {
         explode(p_220069_2_, p_220069_3_);
         p_220069_2_.removeBlock(p_220069_3_, false);
      }

   }

   /**
    * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
    * this block
    */
   public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!worldIn.isRemote() && !player.isCreative() && state.get(UNSTABLE)) {
         explode(worldIn, pos);
      }

      super.onBlockHarvested(worldIn, pos, state, player);
   }

   /**
    * Called when this Block is destroyed by an Explosion
    */
   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
      if (!worldIn.isRemote) {
         TNTEntity tntentity = new TNTEntity(worldIn, (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), explosionIn.getExplosivePlacedBy());
         tntentity.setFuse((short)(worldIn.rand.nextInt(tntentity.getFuse() / 4) + tntentity.getFuse() / 8));
         worldIn.func_217376_c(tntentity);
      }
   }

   public static void explode(World p_196534_0_, BlockPos worldIn) {
      explode(p_196534_0_, worldIn, (LivingEntity)null);
   }

   private static void explode(World p_196535_0_, BlockPos p_196535_1_, @Nullable LivingEntity p_196535_2_) {
      if (!p_196535_0_.isRemote) {
         TNTEntity tntentity = new TNTEntity(p_196535_0_, (double)((float)p_196535_1_.getX() + 0.5F), (double)p_196535_1_.getY(), (double)((float)p_196535_1_.getZ() + 0.5F), p_196535_2_);
         p_196535_0_.func_217376_c(tntentity);
         p_196535_0_.playSound((PlayerEntity)null, tntentity.posX, tntentity.posY, tntentity.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      ItemStack itemstack = p_220051_4_.getHeldItem(p_220051_5_);
      Item item = itemstack.getItem();
      if (item != Items.FLINT_AND_STEEL && item != Items.FIRE_CHARGE) {
         return super.onBlockActivated(p_220051_1_, p_220051_2_, p_220051_3_, p_220051_4_, p_220051_5_, p_220051_6_);
      } else {
         explode(p_220051_2_, p_220051_3_, p_220051_4_);
         p_220051_2_.setBlockState(p_220051_3_, Blocks.AIR.getDefaultState(), 11);
         if (item == Items.FLINT_AND_STEEL) {
            itemstack.func_222118_a(1, p_220051_4_, (p_220287_1_) -> {
               p_220287_1_.func_213334_d(p_220051_5_);
            });
         } else {
            itemstack.shrink(1);
         }

         return true;
      }
   }

   public void onProjectileCollision(World p_220066_1_, BlockState p_220066_2_, BlockRayTraceResult p_220066_3_, Entity p_220066_4_) {
      if (!p_220066_1_.isRemote && p_220066_4_ instanceof AbstractArrowEntity) {
         AbstractArrowEntity abstractarrowentity = (AbstractArrowEntity)p_220066_4_;
         Entity entity = abstractarrowentity.getShooter();
         if (abstractarrowentity.isBurning()) {
            BlockPos blockpos = p_220066_3_.getPos();
            explode(p_220066_1_, blockpos, entity instanceof LivingEntity ? (LivingEntity)entity : null);
            p_220066_1_.removeBlock(blockpos, false);
         }
      }

   }

   /**
    * Return whether this block can drop from an explosion.
    */
   public boolean canDropFromExplosion(Explosion explosionIn) {
      return false;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(UNSTABLE);
   }
}