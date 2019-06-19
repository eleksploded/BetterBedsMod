package net.minecraft.client.audio;

import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuardianSound extends TickableSound {
   private final GuardianEntity field_174934_k;

   public GuardianSound(GuardianEntity guardian) {
      super(SoundEvents.ENTITY_GUARDIAN_ATTACK, SoundCategory.HOSTILE);
      this.field_174934_k = guardian;
      this.attenuationType = ISound.AttenuationType.NONE;
      this.repeat = true;
      this.repeatDelay = 0;
   }

   public void tick() {
      if (!this.field_174934_k.removed && this.field_174934_k.getAttackTarget() == null) {
         this.x = (float)this.field_174934_k.posX;
         this.y = (float)this.field_174934_k.posY;
         this.z = (float)this.field_174934_k.posZ;
         float f = this.field_174934_k.getAttackAnimationScale(0.0F);
         this.volume = 0.0F + 1.0F * f * f;
         this.pitch = 0.7F + 0.5F * f;
      } else {
         this.donePlaying = true;
      }
   }
}