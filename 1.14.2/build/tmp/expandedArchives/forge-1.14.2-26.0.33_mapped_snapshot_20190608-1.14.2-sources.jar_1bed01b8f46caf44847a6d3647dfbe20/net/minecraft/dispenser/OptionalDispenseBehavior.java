package net.minecraft.dispenser;

public abstract class OptionalDispenseBehavior extends DefaultDispenseItemBehavior {
   protected boolean field_218407_b = true;

   /**
    * Play the dispense sound from the specified block.
    */
   protected void playDispenseSound(IBlockSource source) {
      source.getWorld().playEvent(this.field_218407_b ? 1000 : 1001, source.getBlockPos(), 0);
   }
}