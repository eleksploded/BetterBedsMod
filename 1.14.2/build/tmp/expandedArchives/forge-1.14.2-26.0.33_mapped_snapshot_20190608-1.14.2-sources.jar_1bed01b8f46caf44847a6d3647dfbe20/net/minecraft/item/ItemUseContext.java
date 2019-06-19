package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemUseContext {
   protected final PlayerEntity field_196001_b;
   protected final Hand field_221534_c;
   protected final BlockRayTraceResult field_221535_d;
   protected final World world;
   protected final ItemStack item;

   public ItemUseContext(PlayerEntity player, Hand handIn, BlockRayTraceResult rayTraceResultIn) {
      this(player.world, player, handIn, player.getHeldItem(handIn), rayTraceResultIn);
   }

   protected ItemUseContext(World worldIn, @Nullable PlayerEntity player, Hand handIn, ItemStack heldItem, BlockRayTraceResult rayTraceResultIn) {
      this.field_196001_b = player;
      this.field_221534_c = handIn;
      this.field_221535_d = rayTraceResultIn;
      this.item = heldItem;
      this.world = worldIn;
   }

   public BlockPos getPos() {
      return this.field_221535_d.getPos();
   }

   public Direction getFace() {
      return this.field_221535_d.getFace();
   }

   public Vec3d func_221532_j() {
      return this.field_221535_d.getHitVec();
   }

   public boolean func_221533_k() {
      return this.field_221535_d.isInside();
   }

   public ItemStack getItem() {
      return this.item;
   }

   @Nullable
   public PlayerEntity getPlayer() {
      return this.field_196001_b;
   }

   public Hand func_221531_n() {
      return this.field_221534_c;
   }

   public World getWorld() {
      return this.world;
   }

   public Direction getPlacementHorizontalFacing() {
      return this.field_196001_b == null ? Direction.NORTH : this.field_196001_b.getHorizontalFacing();
   }

   public boolean isPlacerSneaking() {
      return this.field_196001_b != null && this.field_196001_b.isSneaking();
   }

   public float getPlacementYaw() {
      return this.field_196001_b == null ? 0.0F : this.field_196001_b.rotationYaw;
   }
}