package net.minecraft.entity.boss.dragon.phase;

import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StrafePlayerPhase extends Phase {
   private static final Logger LOGGER = LogManager.getLogger();
   private int fireballCharge;
   private Path currentPath;
   private Vec3d targetLocation;
   private LivingEntity field_188693_f;
   private boolean holdingPatternClockwise;

   public StrafePlayerPhase(EnderDragonEntity dragonIn) {
      super(dragonIn);
   }

   /**
    * Gives the phase a chance to update its status.
    * Called by dragon's onLivingUpdate. Only used when !worldObj.isRemote.
    */
   public void serverTick() {
      if (this.field_188693_f == null) {
         LOGGER.warn("Skipping player strafe phase because no player was found");
         this.field_188661_a.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
      } else {
         if (this.currentPath != null && this.currentPath.isFinished()) {
            double d0 = this.field_188693_f.posX;
            double d1 = this.field_188693_f.posZ;
            double d2 = d0 - this.field_188661_a.posX;
            double d3 = d1 - this.field_188661_a.posZ;
            double d4 = (double)MathHelper.sqrt(d2 * d2 + d3 * d3);
            double d5 = Math.min((double)0.4F + d4 / 80.0D - 1.0D, 10.0D);
            this.targetLocation = new Vec3d(d0, this.field_188693_f.posY + d5, d1);
         }

         double d12 = this.targetLocation == null ? 0.0D : this.targetLocation.squareDistanceTo(this.field_188661_a.posX, this.field_188661_a.posY, this.field_188661_a.posZ);
         if (d12 < 100.0D || d12 > 22500.0D) {
            this.findNewTarget();
         }

         double d13 = 64.0D;
         if (this.field_188693_f.getDistanceSq(this.field_188661_a) < 4096.0D) {
            if (this.field_188661_a.canEntityBeSeen(this.field_188693_f)) {
               ++this.fireballCharge;
               Vec3d vec3d1 = (new Vec3d(this.field_188693_f.posX - this.field_188661_a.posX, 0.0D, this.field_188693_f.posZ - this.field_188661_a.posZ)).normalize();
               Vec3d vec3d = (new Vec3d((double)MathHelper.sin(this.field_188661_a.rotationYaw * ((float)Math.PI / 180F)), 0.0D, (double)(-MathHelper.cos(this.field_188661_a.rotationYaw * ((float)Math.PI / 180F))))).normalize();
               float f1 = (float)vec3d.dotProduct(vec3d1);
               float f = (float)(Math.acos((double)f1) * (double)(180F / (float)Math.PI));
               f = f + 0.5F;
               if (this.fireballCharge >= 5 && f >= 0.0F && f < 10.0F) {
                  double d14 = 1.0D;
                  Vec3d vec3d2 = this.field_188661_a.getLook(1.0F);
                  double d6 = this.field_188661_a.field_70986_h.posX - vec3d2.x * 1.0D;
                  double d7 = this.field_188661_a.field_70986_h.posY + (double)(this.field_188661_a.field_70986_h.getHeight() / 2.0F) + 0.5D;
                  double d8 = this.field_188661_a.field_70986_h.posZ - vec3d2.z * 1.0D;
                  double d9 = this.field_188693_f.posX - d6;
                  double d10 = this.field_188693_f.posY + (double)(this.field_188693_f.getHeight() / 2.0F) - (d7 + (double)(this.field_188661_a.field_70986_h.getHeight() / 2.0F));
                  double d11 = this.field_188693_f.posZ - d8;
                  this.field_188661_a.world.playEvent((PlayerEntity)null, 1017, new BlockPos(this.field_188661_a), 0);
                  DragonFireballEntity dragonfireballentity = new DragonFireballEntity(this.field_188661_a.world, this.field_188661_a, d9, d10, d11);
                  dragonfireballentity.setLocationAndAngles(d6, d7, d8, 0.0F, 0.0F);
                  this.field_188661_a.world.func_217376_c(dragonfireballentity);
                  this.fireballCharge = 0;
                  if (this.currentPath != null) {
                     while(!this.currentPath.isFinished()) {
                        this.currentPath.incrementPathIndex();
                     }
                  }

                  this.field_188661_a.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
               }
            } else if (this.fireballCharge > 0) {
               --this.fireballCharge;
            }
         } else if (this.fireballCharge > 0) {
            --this.fireballCharge;
         }

      }
   }

   private void findNewTarget() {
      if (this.currentPath == null || this.currentPath.isFinished()) {
         int i = this.field_188661_a.initPathPoints();
         int j = i;
         if (this.field_188661_a.getRNG().nextInt(8) == 0) {
            this.holdingPatternClockwise = !this.holdingPatternClockwise;
            j = i + 6;
         }

         if (this.holdingPatternClockwise) {
            ++j;
         } else {
            --j;
         }

         if (this.field_188661_a.getFightManager() != null && this.field_188661_a.getFightManager().getNumAliveCrystals() > 0) {
            j = j % 12;
            if (j < 0) {
               j += 12;
            }
         } else {
            j = j - 12;
            j = j & 7;
            j = j + 12;
         }

         this.currentPath = this.field_188661_a.findPath(i, j, (PathPoint)null);
         if (this.currentPath != null) {
            this.currentPath.incrementPathIndex();
         }
      }

      this.navigateToNextPathNode();
   }

   private void navigateToNextPathNode() {
      if (this.currentPath != null && !this.currentPath.isFinished()) {
         Vec3d vec3d = this.currentPath.getCurrentPos();
         this.currentPath.incrementPathIndex();
         double d0 = vec3d.x;
         double d2 = vec3d.z;

         double d1;
         while(true) {
            d1 = vec3d.y + (double)(this.field_188661_a.getRNG().nextFloat() * 20.0F);
            if (!(d1 < vec3d.y)) {
               break;
            }
         }

         this.targetLocation = new Vec3d(d0, d1, d2);
      }

   }

   /**
    * Called when this phase is set to active
    */
   public void initPhase() {
      this.fireballCharge = 0;
      this.targetLocation = null;
      this.currentPath = null;
      this.field_188693_f = null;
   }

   public void setTarget(LivingEntity p_188686_1_) {
      this.field_188693_f = p_188686_1_;
      int i = this.field_188661_a.initPathPoints();
      int j = this.field_188661_a.getNearestPpIdx(this.field_188693_f.posX, this.field_188693_f.posY, this.field_188693_f.posZ);
      int k = MathHelper.floor(this.field_188693_f.posX);
      int l = MathHelper.floor(this.field_188693_f.posZ);
      double d0 = (double)k - this.field_188661_a.posX;
      double d1 = (double)l - this.field_188661_a.posZ;
      double d2 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
      double d3 = Math.min((double)0.4F + d2 / 80.0D - 1.0D, 10.0D);
      int i1 = MathHelper.floor(this.field_188693_f.posY + d3);
      PathPoint pathpoint = new PathPoint(k, i1, l);
      this.currentPath = this.field_188661_a.findPath(i, j, pathpoint);
      if (this.currentPath != null) {
         this.currentPath.incrementPathIndex();
         this.navigateToNextPathNode();
      }

   }

   /**
    * Returns the location the dragon is flying toward
    */
   @Nullable
   public Vec3d getTargetLocation() {
      return this.targetLocation;
   }

   public PhaseType<StrafePlayerPhase> getType() {
      return PhaseType.STRAFE_PLAYER;
   }
}