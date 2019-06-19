package net.minecraft.entity.player;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PlayerAbilities {
   public boolean disableDamage;
   public boolean isFlying;
   public boolean allowFlying;
   public boolean isCreativeMode;
   public boolean allowEdit = true;
   private float field_75096_f = 0.05F;
   private float walkSpeed = 0.1F;

   public void write(CompoundNBT tagCompound) {
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putBoolean("invulnerable", this.disableDamage);
      compoundnbt.putBoolean("flying", this.isFlying);
      compoundnbt.putBoolean("mayfly", this.allowFlying);
      compoundnbt.putBoolean("instabuild", this.isCreativeMode);
      compoundnbt.putBoolean("mayBuild", this.allowEdit);
      compoundnbt.putFloat("flySpeed", this.field_75096_f);
      compoundnbt.putFloat("walkSpeed", this.walkSpeed);
      tagCompound.put("abilities", compoundnbt);
   }

   public void read(CompoundNBT tagCompound) {
      if (tagCompound.contains("abilities", 10)) {
         CompoundNBT compoundnbt = tagCompound.getCompound("abilities");
         this.disableDamage = compoundnbt.getBoolean("invulnerable");
         this.isFlying = compoundnbt.getBoolean("flying");
         this.allowFlying = compoundnbt.getBoolean("mayfly");
         this.isCreativeMode = compoundnbt.getBoolean("instabuild");
         if (compoundnbt.contains("flySpeed", 99)) {
            this.field_75096_f = compoundnbt.getFloat("flySpeed");
            this.walkSpeed = compoundnbt.getFloat("walkSpeed");
         }

         if (compoundnbt.contains("mayBuild", 1)) {
            this.allowEdit = compoundnbt.getBoolean("mayBuild");
         }
      }

   }

   public float getFlySpeed() {
      return this.field_75096_f;
   }

   @OnlyIn(Dist.CLIENT)
   public void setFlySpeed(float p_195931_1_) {
      this.field_75096_f = p_195931_1_;
   }

   public float getWalkSpeed() {
      return this.walkSpeed;
   }

   @OnlyIn(Dist.CLIENT)
   public void setWalkSpeed(float speed) {
      this.walkSpeed = speed;
   }
}