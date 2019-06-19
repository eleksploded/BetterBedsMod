package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClimberPathNavigator extends GroundPathNavigator {
   private BlockPos targetPosition;

   public ClimberPathNavigator(MobEntity entityLivingIn, World worldIn) {
      super(entityLivingIn, worldIn);
   }

   /**
    * Returns path to given BlockPos
    */
   public Path getPathToPos(BlockPos pos) {
      this.targetPosition = pos;
      return super.getPathToPos(pos);
   }

   /**
    * Returns the path to the given EntityLiving. Args : entity
    */
   public Path getPathToEntityLiving(Entity entityIn) {
      this.targetPosition = new BlockPos(entityIn);
      return super.getPathToEntityLiving(entityIn);
   }

   /**
    * Try to find and set a path to EntityLiving. Returns true if successful. Args : entity, speed
    */
   public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
      Path path = this.getPathToEntityLiving(entityIn);
      if (path != null) {
         return this.setPath(path, speedIn);
      } else {
         this.targetPosition = new BlockPos(entityIn);
         this.speed = speedIn;
         return true;
      }
   }

   public void tick() {
      if (!this.noPath()) {
         super.tick();
      } else {
         if (this.targetPosition != null) {
            if (!this.targetPosition.func_218137_a(this.field_75515_a.getPositionVec(), (double)this.field_75515_a.getWidth()) && (!(this.field_75515_a.posY > (double)this.targetPosition.getY()) || !(new BlockPos((double)this.targetPosition.getX(), this.field_75515_a.posY, (double)this.targetPosition.getZ())).func_218137_a(this.field_75515_a.getPositionVec(), (double)this.field_75515_a.getWidth()))) {
               this.field_75515_a.getMoveHelper().setMoveTo((double)this.targetPosition.getX(), (double)this.targetPosition.getY(), (double)this.targetPosition.getZ(), this.speed);
            } else {
               this.targetPosition = null;
            }
         }

      }
   }
}