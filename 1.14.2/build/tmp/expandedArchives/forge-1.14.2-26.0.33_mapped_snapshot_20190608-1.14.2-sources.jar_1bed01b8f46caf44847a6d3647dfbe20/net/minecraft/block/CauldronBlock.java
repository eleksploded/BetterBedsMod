package net.minecraft.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CauldronBlock extends Block {
   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
   private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
   protected static final VoxelShape field_220269_b = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.func_216384_a(makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), IBooleanFunction.ONLY_FIRST);

   public CauldronBlock(Block.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(LEVEL, Integer.valueOf(0)));
   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      return field_220269_b;
   }

   public boolean isSolid(BlockState state) {
      return false;
   }

   public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return INSIDE;
   }

   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
      int i = state.get(LEVEL);
      float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;
      if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getBoundingBox().minY <= (double)f) {
         entityIn.extinguish();
         this.setWaterLevel(worldIn, pos, state, i - 1);
      }

   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      ItemStack itemstack = p_220051_4_.getHeldItem(p_220051_5_);
      if (itemstack.isEmpty()) {
         return true;
      } else {
         int i = p_220051_1_.get(LEVEL);
         Item item = itemstack.getItem();
         if (item == Items.WATER_BUCKET) {
            if (i < 3 && !p_220051_2_.isRemote) {
               if (!p_220051_4_.playerAbilities.isCreativeMode) {
                  p_220051_4_.setHeldItem(p_220051_5_, new ItemStack(Items.BUCKET));
               }

               p_220051_4_.addStat(Stats.FILL_CAULDRON);
               this.setWaterLevel(p_220051_2_, p_220051_3_, p_220051_1_, 3);
               p_220051_2_.playSound((PlayerEntity)null, p_220051_3_, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return true;
         } else if (item == Items.BUCKET) {
            if (i == 3 && !p_220051_2_.isRemote) {
               if (!p_220051_4_.playerAbilities.isCreativeMode) {
                  itemstack.shrink(1);
                  if (itemstack.isEmpty()) {
                     p_220051_4_.setHeldItem(p_220051_5_, new ItemStack(Items.WATER_BUCKET));
                  } else if (!p_220051_4_.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                     p_220051_4_.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                  }
               }

               p_220051_4_.addStat(Stats.USE_CAULDRON);
               this.setWaterLevel(p_220051_2_, p_220051_3_, p_220051_1_, 0);
               p_220051_2_.playSound((PlayerEntity)null, p_220051_3_, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            return true;
         } else if (item == Items.GLASS_BOTTLE) {
            if (i > 0 && !p_220051_2_.isRemote) {
               if (!p_220051_4_.playerAbilities.isCreativeMode) {
                  ItemStack itemstack4 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.field_185230_b);
                  p_220051_4_.addStat(Stats.USE_CAULDRON);
                  itemstack.shrink(1);
                  if (itemstack.isEmpty()) {
                     p_220051_4_.setHeldItem(p_220051_5_, itemstack4);
                  } else if (!p_220051_4_.inventory.addItemStackToInventory(itemstack4)) {
                     p_220051_4_.dropItem(itemstack4, false);
                  } else if (p_220051_4_ instanceof ServerPlayerEntity) {
                     ((ServerPlayerEntity)p_220051_4_).sendContainerToPlayer(p_220051_4_.container);
                  }
               }

               p_220051_2_.playSound((PlayerEntity)null, p_220051_3_, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
               this.setWaterLevel(p_220051_2_, p_220051_3_, p_220051_1_, i - 1);
            }

            return true;
         } else if (item == Items.POTION && PotionUtils.getPotionFromItem(itemstack) == Potions.field_185230_b) {
            if (i < 3 && !p_220051_2_.isRemote) {
               if (!p_220051_4_.playerAbilities.isCreativeMode) {
                  ItemStack itemstack3 = new ItemStack(Items.GLASS_BOTTLE);
                  p_220051_4_.addStat(Stats.USE_CAULDRON);
                  p_220051_4_.setHeldItem(p_220051_5_, itemstack3);
                  if (p_220051_4_ instanceof ServerPlayerEntity) {
                     ((ServerPlayerEntity)p_220051_4_).sendContainerToPlayer(p_220051_4_.container);
                  }
               }

               p_220051_2_.playSound((PlayerEntity)null, p_220051_3_, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
               this.setWaterLevel(p_220051_2_, p_220051_3_, p_220051_1_, i + 1);
            }

            return true;
         } else {
            if (i > 0 && item instanceof IDyeableArmorItem) {
               IDyeableArmorItem idyeablearmoritem = (IDyeableArmorItem)item;
               if (idyeablearmoritem.hasColor(itemstack) && !p_220051_2_.isRemote) {
                  idyeablearmoritem.removeColor(itemstack);
                  this.setWaterLevel(p_220051_2_, p_220051_3_, p_220051_1_, i - 1);
                  p_220051_4_.addStat(Stats.CLEAN_ARMOR);
                  return true;
               }
            }

            if (i > 0 && item instanceof BannerItem) {
               if (BannerTileEntity.getPatterns(itemstack) > 0 && !p_220051_2_.isRemote) {
                  ItemStack itemstack2 = itemstack.copy();
                  itemstack2.setCount(1);
                  BannerTileEntity.removeBannerData(itemstack2);
                  p_220051_4_.addStat(Stats.CLEAN_BANNER);
                  if (!p_220051_4_.playerAbilities.isCreativeMode) {
                     itemstack.shrink(1);
                     this.setWaterLevel(p_220051_2_, p_220051_3_, p_220051_1_, i - 1);
                  }

                  if (itemstack.isEmpty()) {
                     p_220051_4_.setHeldItem(p_220051_5_, itemstack2);
                  } else if (!p_220051_4_.inventory.addItemStackToInventory(itemstack2)) {
                     p_220051_4_.dropItem(itemstack2, false);
                  } else if (p_220051_4_ instanceof ServerPlayerEntity) {
                     ((ServerPlayerEntity)p_220051_4_).sendContainerToPlayer(p_220051_4_.container);
                  }
               }

               return true;
            } else if (i > 0 && item instanceof BlockItem) {
               Block block = ((BlockItem)item).getBlock();
               if (block instanceof ShulkerBoxBlock && !p_220051_2_.isRemote()) {
                  ItemStack itemstack1 = new ItemStack(Blocks.SHULKER_BOX, 1);
                  if (itemstack.hasTag()) {
                     itemstack1.setTag(itemstack.getTag().copy());
                  }

                  p_220051_4_.setHeldItem(p_220051_5_, itemstack1);
                  this.setWaterLevel(p_220051_2_, p_220051_3_, p_220051_1_, i - 1);
                  p_220051_4_.addStat(Stats.CLEAN_SHULKER_BOX);
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   public void setWaterLevel(World worldIn, BlockPos pos, BlockState state, int level) {
      worldIn.setBlockState(pos, state.with(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 3))), 2);
      worldIn.updateComparatorOutputLevel(pos, this);
   }

   /**
    * Called similar to random ticks, but only when it is raining.
    */
   public void fillWithRain(World worldIn, BlockPos pos) {
      if (worldIn.rand.nextInt(20) == 1) {
         float f = worldIn.getBiome(pos).getTemperature(pos);
         if (!(f < 0.15F)) {
            BlockState blockstate = worldIn.getBlockState(pos);
            if (blockstate.get(LEVEL) < 3) {
               worldIn.setBlockState(pos, blockstate.cycle(LEVEL), 2);
            }

         }
      }
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
      return blockState.get(LEVEL);
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(LEVEL);
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }
}