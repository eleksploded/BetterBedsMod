package net.minecraft.client.audio;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RidingMinecartTickableSound extends TickableSound {
   private final PlayerEntity field_147672_k;
   private final AbstractMinecartEntity field_147671_l;

   public RidingMinecartTickableSound(PlayerEntity playerIn, AbstractMinecartEntity minecartIn) {
      super(SoundEvents.ENTITY_MINECART_INSIDE, SoundCategory.NEUTRAL);
      this.field_147672_k = playerIn;
      this.field_147671_l = minecartIn;
      this.attenuationType = ISound.AttenuationType.NONE;
      this.repeat = true;
      this.repeatDelay = 0;
      this.volume = 0.0F;
   }

   public boolean canBeSilent() {
      return true;
   }

   public void tick() {
      if (!this.field_147671_l.removed && this.field_147672_k.isPassenger() && this.field_147672_k.getRidingEntity() == this.field_147671_l) {
         float f = MathHelper.sqrt(Entity.func_213296_b(this.field_147671_l.getMotion()));
         if ((double)f >= 0.01D) {
            this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 1.0F) * 0.75F;
         } else {
            this.volume = 0.0F;
         }

      } else {
         this.donePlaying = true;
      }
   }
}