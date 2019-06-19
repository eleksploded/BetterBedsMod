package net.minecraft.inventory.container;

import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.MerchantInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.stats.Stats;

public class MerchantResultSlot extends Slot {
   private final MerchantInventory field_75233_a;
   private final PlayerEntity field_75232_b;
   private int removeCount;
   private final IMerchant field_75234_h;

   public MerchantResultSlot(PlayerEntity player, IMerchant merchant, MerchantInventory merchantInventory, int slotIndex, int xPosition, int yPosition) {
      super(merchantInventory, slotIndex, xPosition, yPosition);
      this.field_75232_b = player;
      this.field_75234_h = merchant;
      this.field_75233_a = merchantInventory;
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
      stack.onCrafting(this.field_75232_b.world, this.field_75232_b, this.removeCount);
      this.removeCount = 0;
   }

   public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
      this.onCrafting(stack);
      MerchantOffer merchantoffer = this.field_75233_a.func_214025_g();
      if (merchantoffer != null) {
         ItemStack itemstack = this.field_75233_a.getStackInSlot(0);
         ItemStack itemstack1 = this.field_75233_a.getStackInSlot(1);
         if (merchantoffer.func_222215_b(itemstack, itemstack1) || merchantoffer.func_222215_b(itemstack1, itemstack)) {
            this.field_75234_h.func_213704_a(merchantoffer);
            thePlayer.addStat(Stats.TRADED_WITH_VILLAGER);
            this.field_75233_a.setInventorySlotContents(0, itemstack);
            this.field_75233_a.setInventorySlotContents(1, itemstack1);
         }

         this.field_75234_h.func_213702_q(this.field_75234_h.func_213708_dV() + merchantoffer.func_222210_n());
      }

      return stack;
   }
}