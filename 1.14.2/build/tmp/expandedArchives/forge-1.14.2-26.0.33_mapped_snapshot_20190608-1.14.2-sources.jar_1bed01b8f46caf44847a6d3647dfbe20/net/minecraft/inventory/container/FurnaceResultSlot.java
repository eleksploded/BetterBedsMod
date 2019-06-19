package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class FurnaceResultSlot extends Slot {
   private final PlayerEntity field_75229_a;
   private int removeCount;

   public FurnaceResultSlot(PlayerEntity player, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
      super(inventoryIn, slotIndex, xPosition, yPosition);
      this.field_75229_a = player;
   }

   /**
    * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
    */
   public boolean isItemValid(ItemStack stack) {
      return false;
   }

   /**
    * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new stack.
    */
   public ItemStack decrStackSize(int amount) {
      if (this.getHasStack()) {
         this.removeCount += Math.min(amount, this.getStack().getCount());
      }

      return super.decrStackSize(amount);
   }

   public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
      this.onCrafting(stack);
      super.onTake(thePlayer, stack);
      return stack;
   }

   /**
    * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
    * internal count then calls onCrafting(item).
    */
   protected void onCrafting(ItemStack stack, int amount) {
      this.removeCount += amount;
      this.onCrafting(stack);
   }

   /**
    * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
    */
   protected void onCrafting(ItemStack stack) {
      stack.onCrafting(this.field_75229_a.world, this.field_75229_a, this.removeCount);
      if (!this.field_75229_a.world.isRemote && this.inventory instanceof AbstractFurnaceTileEntity) {
         ((AbstractFurnaceTileEntity)this.inventory).func_213995_d(this.field_75229_a);
      }

      this.removeCount = 0;
      net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.field_75229_a, stack);
   }
}