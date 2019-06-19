package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SilverfishBlock extends Block {
   private final Block mimickedBlock;
   private static final Map<Block, Block> field_196470_b = Maps.newIdentityHashMap();

   public SilverfishBlock(Block blockIn, Block.Properties properties) {
      super(properties);
      this.mimickedBlock = blockIn;
      field_196470_b.put(blockIn, this);
   }

   public Block getMimickedBlock() {
      return this.mimickedBlock;
   }

   public static boolean canContainSilverfish(BlockState state) {
      return field_196470_b.containsKey(state.getBlock());
   }

   /**
    * Spawn additional block drops such as experience or other entities
    */
   public void spawnAdditionalDrops(BlockState p_220062_1_, World p_220062_2_, BlockPos p_220062_3_, ItemStack p_220062_4_) {
      super.spawnAdditionalDrops(p_220062_1_, p_220062_2_, p_220062_3_, p_220062_4_);
      if (!p_220062_2_.isRemote && p_220062_2_.getGameRules().getBoolean("doTileDrops") && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, p_220062_4_) == 0) {
         SilverfishEntity silverfishentity = EntityType.SILVERFISH.create(p_220062_2_);
         silverfishentity.setLocationAndAngles((double)p_220062_3_.getX() + 0.5D, (double)p_220062_3_.getY(), (double)p_220062_3_.getZ() + 0.5D, 0.0F, 0.0F);
         p_220062_2_.func_217376_c(silverfishentity);
         silverfishentity.spawnExplosionParticle();
      }

   }

   public static BlockState infest(Block blockIn) {
      return field_196470_b.get(blockIn).getDefaultState();
   }
}