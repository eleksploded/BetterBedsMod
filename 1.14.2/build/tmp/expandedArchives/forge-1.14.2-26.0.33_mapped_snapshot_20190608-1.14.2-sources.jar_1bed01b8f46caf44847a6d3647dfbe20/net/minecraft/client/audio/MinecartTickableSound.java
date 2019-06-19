package net.minecraft.client.audio;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MinecartTickableSound extends TickableSound {
   private final AbstractMinecartEntity field_147670_k;
   private float distance = 0.0F;

   public MinecartTickableSound(AbstractMinecartEntity minecartIn) {
      super(SoundEvents.ENTITY_MINECART_RIDING, SoundCategory.NEUTRAL);
      this.field_147670_k = minecartIn;
      this.repeat = true;
      this.repeatDelay = 0;
      this.volume = 0.0F;
      this.x = (float)minecartIn.posX;
      this.y = (float)minecartIn.posY;
      this.z = (float)minecartIn.posZ;
   }

   public boolean canBeSilent() {
      return true;
   }

   public void tick() {
      if (this.field_147670_k.removed) {
         this.donePlaying = true;
      } else {
         this.x = (float)this.field_147670_k.posX;
         this.y = (float)this.field_147670_k.posY;
         this.z = (float)this.field_147670_k.posZ;
         float f = MathHelper.sqrt(Entity.func_213296_b(this.field_147670_k.getMotion()));
         if ((double)f >= 0.01D) {
            this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
            this.volume = MathHelper.func_219799_g(MathHelper.clamp(f, 0.0F, 0.5F), 0.0F, 0.7F);
         } else {
            this.distance = 0.0F;
            this.volume = 0.0F;
         }

      }
   }
}