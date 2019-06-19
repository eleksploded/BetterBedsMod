package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;

public class JumpController {
   private final MobEntity field_75663_a;
   protected boolean isJumping;

   public JumpController(MobEntity entityIn) {
      this.field_75663_a = entityIn;
   }

   public void setJumping() {
      this.isJumping = true;
   }

   /**
    * Called to actually make the entity jump if isJumping is true.
    */
   public void tick() {
      this.field_75663_a.setJumping(this.isJumping);
      this.isJumping = false;
   }
}