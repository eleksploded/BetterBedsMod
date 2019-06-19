package net.minecraft.tileentity;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.CampfireBlock;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CampfireTileEntity extends TileEntity implements IClearable, ITickableTileEntity {
   private final NonNullList<ItemStack> field_213987_a = NonNullList.withSize(4, ItemStack.EMPTY);
   private final int[] field_213988_b = new int[4];
   private final int[] field_213989_c = new int[4];

   public CampfireTileEntity() {
      super(TileEntityType.field_222488_F);
   }

   public void tick() {
      boolean flag = this.getBlockState().get(CampfireBlock.LIT);
      boolean flag1 = this.world.isRemote;
      if (flag1) {
         if (flag) {
            this.func_213982_r();
         }

      } else {
         if (flag) {
            this.func_213978_f();
         } else {
            for(int i = 0; i < this.field_213987_a.size(); ++i) {
               if (this.field_213988_b[i] > 0) {
                  this.field_213988_b[i] = MathHelper.clamp(this.field_213988_b[i] - 2, 0, this.field_213989_c[i]);
               }
            }
         }

      }
   }

   private void func_213978_f() {
      for(int i = 0; i < this.field_213987_a.size(); ++i) {
         ItemStack itemstack = this.field_213987_a.get(i);
         if (!itemstack.isEmpty()) {
            ++this.field_213988_b[i];
            if (this.field_213988_b[i] >= this.field_213989_c[i]) {
               IInventory iinventory = new Inventory(itemstack);
               ItemStack itemstack1 = this.world.getRecipeManager().func_215371_a(IRecipeType.field_222153_e, iinventory, this.world).map((p_213979_1_) -> {
                  return p_213979_1_.getCraftingResult(iinventory);
               }).orElse(itemstack);
               BlockPos blockpos = this.getPos();
               InventoryHelper.spawnItemStack(this.world, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), itemstack1);
               this.field_213987_a.set(i, ItemStack.EMPTY);
               this.func_213981_s();
            }
         }
      }

   }

   private void func_213982_r() {
      World world = this.getWorld();
      if (world != null) {
         BlockPos blockpos = this.getPos();
         Random random = world.rand;
         if (random.nextFloat() < 0.11F) {
            for(int i = 0; i < random.nextInt(2) + 2; ++i) {
               CampfireBlock.func_220098_a(world, blockpos, this.getBlockState().get(CampfireBlock.SIGNAL_FIRE), false);
            }
         }

         int l = this.getBlockState().get(CampfireBlock.FACING).getHorizontalIndex();

         for(int j = 0; j < this.field_213987_a.size(); ++j) {
            if (!this.field_213987_a.get(j).isEmpty() && random.nextFloat() < 0.2F) {
               Direction direction = Direction.byHorizontalIndex(Math.floorMod(j + l, 4));
               float f = 0.3125F;
               double d0 = (double)blockpos.getX() + 0.5D - (double)((float)direction.getXOffset() * 0.3125F) + (double)((float)direction.rotateY().getXOffset() * 0.3125F);
               double d1 = (double)blockpos.getY() + 0.5D;
               double d2 = (double)blockpos.getZ() + 0.5D - (double)((float)direction.getZOffset() * 0.3125F) + (double)((float)direction.rotateY().getZOffset() * 0.3125F);

               for(int k = 0; k < 4; ++k) {
                  world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
               }
            }
         }

      }
   }

   public NonNullList<ItemStack> func_213985_c() {
      return this.field_213987_a;
   }

   public void read(CompoundNBT compound) {
      super.read(compound);
      this.field_213987_a.clear();
      ItemStackHelper.loadAllItems(compound, this.field_213987_a);
      if (compound.contains("CookingTimes", 11)) {
         int[] aint = compound.getIntArray("CookingTimes");
         System.arraycopy(aint, 0, this.field_213988_b, 0, Math.min(this.field_213989_c.length, aint.length));
      }

      if (compound.contains("CookingTotalTimes", 11)) {
         int[] aint1 = compound.getIntArray("CookingTotalTimes");
         System.arraycopy(aint1, 0, this.field_213989_c, 0, Math.min(this.field_213989_c.length, aint1.length));
      }

   }

   public CompoundNBT write(CompoundNBT compound) {
      this.func_213983_d(compound);
      compound.putIntArray("CookingTimes", this.field_213988_b);
      compound.putIntArray("CookingTotalTimes", this.field_213989_c);
      return compound;
   }

   private CompoundNBT func_213983_d(CompoundNBT p_213983_1_) {
      super.write(p_213983_1_);
      ItemStackHelper.saveAllItems(p_213983_1_, this.field_213987_a, true);
      return p_213983_1_;
   }

   /**
    * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
    * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
    */
   @Nullable
   public SUpdateTileEntityPacket getUpdatePacket() {
      return new SUpdateTileEntityPacket(this.pos, 13, this.getUpdateTag());
   }

   /**
    * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
    * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
    */
   public CompoundNBT getUpdateTag() {
      return this.func_213983_d(new CompoundNBT());
   }

   public Optional<CampfireCookingRecipe> func_213980_a(ItemStack p_213980_1_) {
      return this.field_213987_a.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().func_215371_a(IRecipeType.field_222153_e, new Inventory(p_213980_1_), this.world);
   }

   public boolean func_213984_a(ItemStack p_213984_1_, int p_213984_2_) {
      for(int i = 0; i < this.field_213987_a.size(); ++i) {
         ItemStack itemstack = this.field_213987_a.get(i);
         if (itemstack.isEmpty()) {
            this.field_213989_c[i] = p_213984_2_;
            this.field_213988_b[i] = 0;
            this.field_213987_a.set(i, p_213984_1_.split(1));
            this.func_213981_s();
            return true;
         }
      }

      return false;
   }

   private void func_213981_s() {
      this.markDirty();
      this.getWorld().notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
   }

   public void clear() {
      this.field_213987_a.clear();
   }

   public void func_213986_d() {
      if (!this.getWorld().isRemote) {
         InventoryHelper.func_219961_a(this.getWorld(), this.getPos(), this.func_213985_c());
      }

      this.func_213981_s();
   }
}