package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FlamingSittingPhase extends SittingPhase {
   private int flameTicks;
   private int flameCount;
   private AreaEffectCloudEntity field_188666_d;

   public FlamingSittingPhase(EnderDragonEntity dragonIn) {
      super(dragonIn);
   }

   /**
    * Generates particle effects appropriate to the phase (or sometimes sounds).
    * Called by dragon's onLivingUpdate. Only used when worldObj.isRemote.
    */
   public void clientTick() {
      ++this.flameTicks;
      if (this.flameTicks % 2 == 0 && this.flameTicks < 10) {
         Vec3d vec3d = this.field_188661_a.getHeadLookVec(1.0F).normalize();
         vec3d.rotateYaw((-(float)Math.PI / 4F));
         double d0 = this.field_188661_a.field_70986_h.posX;
         double d1 = this.field_188661_a.field_70986_h.posY + (double)(this.field_188661_a.field_70986_h.getHeight() / 2.0F);
         double d2 = this.field_188661_a.field_70986_h.posZ;

         for(int i = 0; i < 8; ++i) {
            double d3 = d0 + this.field_188661_a.getRNG().nextGaussian() / 2.0D;
            double d4 = d1 + this.field_188661_a.getRNG().nextGaussian() / 2.0D;
            double d5 = d2 + this.field_188661_a.getRNG().nextGaussian() / 2.0D;

            for(int j = 0; j < 6; ++j) {
               this.field_188661_a.world.addParticle(ParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.x * (double)0.08F * (double)j, -vec3d.y * (double)0.6F, -vec3d.z * (double)0.08F * (double)j);
            }

            vec3d.rotateYaw(0.19634955F);
         }
      }

   }

   /**
    * Gives the phase a chance to update its status.
    * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
    */
   public void serverTick() {
      ++this.flameTicks;
      if (this.flameTicks >= 200) {
         if (this.flameCount >= 4) {
            this.field_188661_a.getPhaseManager().setPhase(PhaseType.TAKEOFF);
         } else {
            this.field_188661_a.getPhaseManager().setPhase(PhaseType.SITTING_SCANNING);
         }
      } else if (this.flameTicks == 10) {
         Vec3d vec3d = (new Vec3d(this.field_188661_a.field_70986_h.posX - this.field_188661_a.posX, 0.0D, this.field_188661_a.field_70986_h.posZ - this.field_188661_a.posZ)).normalize();
         float f = 5.0F;
         double d0 = this.field_188661_a.field_70986_h.posX + vec3d.x * 5.0D / 2.0D;
         double d1 = this.field_188661_a.field_70986_h.posZ + vec3d.z * 5.0D / 2.0D;
         double d2 = this.field_188661_a.field_70986_h.posY + (double)(this.field_188661_a.field_70986_h.getHeight() / 2.0F);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(d0, d2, d1);

         while(this.field_188661_a.world.isAirBlock(blockpos$mutableblockpos) && d2 >= 0) { //Forge: Fix infinite loop if ground is missing.
            --d2;
            blockpos$mutableblockpos.setPos(d0, d2, d1);
         }

         d2 = (double)(MathHelper.floor(d2) + 1);
         this.field_188666_d = new AreaEffectCloudEntity(this.field_188661_a.world, d0, d2, d1);
         this.field_188666_d.setOwner(this.field_188661_a);
         this.field_188666_d.setRadius(5.0F);
         this.field_188666_d.setDuration(200);
         this.field_188666_d.setParticleData(ParticleTypes.DRAGON_BREATH);
         this.field_188666_d.addEffect(new EffectInstance(Effects.field_76433_i));
         this.field_188661_a.world.func_217376_c(this.field_188666_d);
      }

   }

   /**
    * Called when this phase is set to active
    */
   public void initPhase() {
      this.flameTicks = 0;
      ++this.flameCount;
   }

   public void removeAreaEffect() {
      if (this.field_188666_d != null) {
         this.field_188666_d.remove();
         this.field_188666_d = null;
      }

   }

   public PhaseType<FlamingSittingPhase> getType() {
      return PhaseType.SITTING_FLAMING;
   }

   public void resetFlameCount() {
      this.flameCount = 0;
   }
}