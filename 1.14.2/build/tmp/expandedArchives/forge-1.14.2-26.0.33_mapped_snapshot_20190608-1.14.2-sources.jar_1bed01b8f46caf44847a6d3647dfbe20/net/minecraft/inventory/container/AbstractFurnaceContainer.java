package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ServerRecipePlacerFurnace;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractFurnaceContainer extends RecipeBookContainer<IInventory> {
   private final IInventory field_217063_d;
   private final IIntArray field_217064_e;
   protected final World field_217062_c;
   private final IRecipeType<? extends AbstractCookingRecipe> field_217065_f;

   protected AbstractFurnaceContainer(ContainerType<?> p_i50103_1_, IRecipeType<? extends AbstractCookingRecipe> p_i50103_2_, int p_i50103_3_, PlayerInventory p_i50103_4_) {
      this(p_i50103_1_, p_i50103_2_, p_i50103_3_, p_i50103_4_, new Inventory(3), new IntArray(4));
   }

   protected AbstractFurnaceContainer(ContainerType<?> p_i50104_1_, IRecipeType<? extends AbstractCookingRecipe> p_i50104_2_, int p_i50104_3_, PlayerInventory p_i50104_4_, IInventory p_i50104_5_, IIntArray p_i50104_6_) {
      super(p_i50104_1_, p_i50104_3_);
      this.field_217065_f = p_i50104_2_;
      func_216962_a(p_i50104_5_, 3);
      func_216959_a(p_i50104_6_, 4);
      this.field_217063_d = p_i50104_5_;
      this.field_217064_e = p_i50104_6_;
      this.field_217062_c = p_i50104_4_.player.world;
      this.addSlot(new Slot(p_i50104_5_, 0, 56, 17));
      this.addSlot(new FurnaceFuelSlot(this, p_i50104_5_, 1, 56, 53));
      this.addSlot(new FurnaceResultSlot(p_i50104_4_.player, p_i50104_5_, 2, 116, 35));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(p_i50104_4_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlot(new Slot(p_i50104_4_, k, 8 + k * 18, 142));
      }

      this.func_216961_a(p_i50104_6_);
   }

   public void func_201771_a(RecipeItemHelper p_201771_1_) {
      if (this.field_217063_d instanceof IRecipeHelperPopulator) {
         ((IRecipeHelperPopulator)this.field_217063_d).fillStackedContents(p_201771_1_);
      }

   }

   public void clear() {
      this.field_217063_d.clear();
   }

   public void func_217056_a(boolean p_217056_1_, IRecipe<?> p_217056_2_, ServerPlayerEntity p_217056_3_) {
      (new ServerRecipePlacerFurnace<>(this)).place(p_217056_3_, (IRecipe<IInventory>)p_217056_2_, p_217056_1_);
   }

   public boolean matches(IRecipe<? super IInventory> p_201769_1_) {
      return p_201769_1_.matches(this.field_217063_d, this.field_217062_c);
   }

   public int getOutputSlot() {
      return 2;
   }

   public int getWidth() {
      return 1;
   }

   public int getHeight() {
      return 1;
   }

   @OnlyIn(Dist.CLIENT)
   public int getSize() {
      return 3;
   }

   /**
    * Determines whether supplied player can use this container
    */
   public boolean canInteractWith(PlayerEntity playerIn) {
      return this.field_217063_d.isUsableByPlayer(playerIn);
   }

   /**
    * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
    * inventory and the other inventory(s).
    */
   public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if (index == 2) {
            if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            slot.onSlotChange(itemstack1, itemstack);
         } else if (index != 1 && index != 0) {
            if (this.func_217057_a(itemstack1)) {
               if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (this.func_217058_b(itemstack1)) {
               if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 3 && index < 30) {
               if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
         } else {
            slot.onSlotChanged();
         }

         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(playerIn, itemstack1);
      }

      return itemstack;
   }

   protected boolean func_217057_a(ItemStack p_217057_1_) {
      return this.field_217062_c.getRecipeManager().func_215371_a((IRecipeType)this.field_217065_f, new Inventory(p_217057_1_), this.field_217062_c).isPresent();
   }

   protected boolean func_217058_b(ItemStack p_217058_1_) {
      return AbstractFurnaceTileEntity.func_213991_b(p_217058_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public int func_217060_j() {
      int i = this.field_217064_e.func_221476_a(2);
      int j = this.field_217064_e.func_221476_a(3);
      return j != 0 && i != 0 ? i * 24 / j : 0;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_217059_k() {
      int i = this.field_217064_e.func_221476_a(1);
      if (i == 0) {
         i = 200;
      }

      return this.field_217064_e.func_221476_a(0) * 13 / i;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_217061_l() {
      return this.field_217064_e.func_221476_a(0) > 0;
   }
}