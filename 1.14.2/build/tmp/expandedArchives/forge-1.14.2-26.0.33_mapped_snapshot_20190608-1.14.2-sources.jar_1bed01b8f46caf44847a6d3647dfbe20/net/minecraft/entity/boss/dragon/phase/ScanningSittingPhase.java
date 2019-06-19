package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ScanningSittingPhase extends SittingPhase {
   private static final EntityPredicate field_221115_b = (new EntityPredicate()).func_221013_a(150.0D);
   private final EntityPredicate field_221116_c;
   private int scanningTime;

   public ScanningSittingPhase(EnderDragonEntity dragonIn) {
      super(dragonIn);
      this.field_221116_c = (new EntityPredicate()).func_221013_a(20.0D).func_221012_a((p_221114_1_) -> {
         return Math.abs(p_221114_1_.posY - dragonIn.posY) <= 10.0D;
      });
   }

   /**
    * Gives the phase a chance to update its status.
    * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
    */
   public void serverTick() {
      ++this.scanningTime;
      LivingEntity livingentity = this.field_188661_a.world.func_217372_a(this.field_221116_c, this.field_188661_a, this.field_188661_a.posX, this.field_188661_a.posY, this.field_188661_a.posZ);
      if (livingentity != null) {
         if (this.scanningTime > 25) {
            this.field_188661_a.getPhaseManager().setPhase(PhaseType.SITTING_ATTACKING);
         } else {
            Vec3d vec3d = (new Vec3d(livingentity.posX - this.field_188661_a.posX, 0.0D, livingentity.posZ - this.field_188661_a.posZ)).normalize();
            Vec3d vec3d1 = (new Vec3d((double)MathHelper.sin(this.field_188661_a.rotationYaw * ((float)Math.PI / 180F)), 0.0D, (double)(-MathHelper.cos(this.field_188661_a.rotationYaw * ((float)Math.PI / 180F))))).normalize();
            float f = (float)vec3d1.dotProduct(vec3d);
            float f1 = (float)(Math.acos((double)f) * (double)(180F / (float)Math.PI)) + 0.5F;
            if (f1 < 0.0F || f1 > 10.0F) {
               double d0 = livingentity.posX - this.field_188661_a.field_70986_h.posX;
               double d1 = livingentity.posZ - this.field_188661_a.field_70986_h.posZ;
               double d2 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d0, d1) * (double)(180F / (float)Math.PI) - (double)this.field_188661_a.rotationYaw), -100.0D, 100.0D);
               this.field_188661_a.randomYawVelocity *= 0.8F;
               float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) + 1.0F;
               float f3 = f2;
               if (f2 > 40.0F) {
                  f2 = 40.0F;
               }

               this.field_188661_a.randomYawVelocity = (float)((double)this.field_188661_a.randomYawVelocity + d2 * (double)(0.7F / f2 / f3));
               this.field_188661_a.rotationYaw += this.field_188661_a.randomYawVelocity;
            }
         }
      } else if (this.scanningTime >= 100) {
         livingentity = this.field_188661_a.world.func_217372_a(field_221115_b, this.field_188661_a, this.field_188661_a.posX, this.field_188661_a.posY, this.field_188661_a.posZ);
         this.field_188661_a.getPhaseManager().setPhase(PhaseType.TAKEOFF);
         if (livingentity != null) {
            this.field_188661_a.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
            this.field_188661_a.getPhaseManager().getPhase(PhaseType.CHARGING_PLAYER).setTarget(new Vec3d(livingentity.posX, livingentity.posY, livingentity.posZ));
         }
      }

   }

   /**
    * Called when this phase is set to active
    */
   public void initPhase() {
      this.scanningTime = 0;
   }

   public PhaseType<ScanningSittingPhase> getType() {
      return PhaseType.SITTING_SCANNING;
   }
}