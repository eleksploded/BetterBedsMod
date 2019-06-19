package net.minecraft.entity.ai.controller;

import net.minecraft.block.BlockState;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;

public class MovementController {
   protected final MobEntity field_75648_a;
   protected double posX;
   protected double posY;
   protected double posZ;
   protected double speed;
   protected float moveForward;
   protected float moveStrafe;
   protected MovementController.Action field_188491_h = MovementController.Action.WAIT;

   public MovementController(MobEntity entitylivingIn) {
      this.field_75648_a = entitylivingIn;
   }

   public boolean isUpdating() {
      return this.field_188491_h == MovementController.Action.MOVE_TO;
   }

   public double getSpeed() {
      return this.speed;
   }

   /**
    * Sets the speed and location to move to
    */
   public void setMoveTo(double x, double y, double z, double speedIn) {
      this.posX = x;
      this.posY = y;
      this.posZ = z;
      this.speed = speedIn;
      if (this.field_188491_h != MovementController.Action.JUMPING) {
         this.field_188491_h = MovementController.Action.MOVE_TO;
      }

   }

   public void strafe(float forward, float strafe) {
      this.field_188491_h = MovementController.Action.STRAFE;
      this.moveForward = forward;
      this.moveStrafe = strafe;
      this.speed = 0.25D;
   }

   public void tick() {
      if (this.field_188491_h == MovementController.Action.STRAFE) {
         float f = (float)this.field_75648_a.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
         float f1 = (float)this.speed * f;
         float f2 = this.moveForward;
         float f3 = this.moveStrafe;
         float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);
         if (f4 < 1.0F) {
            f4 = 1.0F;
         }

         f4 = f1 / f4;
         f2 = f2 * f4;
         f3 = f3 * f4;
         float f5 = MathHelper.sin(this.field_75648_a.rotationYaw * ((float)Math.PI / 180F));
         float f6 = MathHelper.cos(this.field_75648_a.rotationYaw * ((float)Math.PI / 180F));
         float f7 = f2 * f6 - f3 * f5;
         float f8 = f3 * f6 + f2 * f5;
         PathNavigator pathnavigator = this.field_75648_a.getNavigator();
         if (pathnavigator != null) {
            NodeProcessor nodeprocessor = pathnavigator.getNodeProcessor();
            if (nodeprocessor != null && nodeprocessor.getPathNodeType(this.field_75648_a.world, MathHelper.floor(this.field_75648_a.posX + (double)f7), MathHelper.floor(this.field_75648_a.posY), MathHelper.floor(this.field_75648_a.posZ + (double)f8)) != PathNodeType.WALKABLE) {
               this.moveForward = 1.0F;
               this.moveStrafe = 0.0F;
               f1 = f;
            }
         }

         this.field_75648_a.setAIMoveSpeed(f1);
         this.field_75648_a.setMoveForward(this.moveForward);
         this.field_75648_a.setMoveStrafing(this.moveStrafe);
         this.field_188491_h = MovementController.Action.WAIT;
      } else if (this.field_188491_h == MovementController.Action.MOVE_TO) {
         this.field_188491_h = MovementController.Action.WAIT;
         double d0 = this.posX - this.field_75648_a.posX;
         double d1 = this.posZ - this.field_75648_a.posZ;
         double d2 = this.posY - this.field_75648_a.posY;
         double d3 = d0 * d0 + d2 * d2 + d1 * d1;
         if (d3 < (double)2.5000003E-7F) {
            this.field_75648_a.setMoveForward(0.0F);
            return;
         }

         float f9 = (float)(MathHelper.atan2(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
         this.field_75648_a.rotationYaw = this.limitAngle(this.field_75648_a.rotationYaw, f9, 90.0F);
         this.field_75648_a.setAIMoveSpeed((float)(this.speed * this.field_75648_a.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
         BlockPos blockpos = new BlockPos(this.field_75648_a);
         BlockState blockstate = this.field_75648_a.world.getBlockState(blockpos);
         VoxelShape voxelshape = blockstate.getCollisionShape(this.field_75648_a.world, blockpos);
         if (d2 > (double)this.field_75648_a.stepHeight && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.field_75648_a.getWidth()) || !voxelshape.isEmpty() && this.field_75648_a.posY < voxelshape.getEnd(Direction.Axis.Y) + (double)blockpos.getY()) {
            this.field_75648_a.getJumpHelper().setJumping();
            this.field_188491_h = MovementController.Action.JUMPING;
         }
      } else if (this.field_188491_h == MovementController.Action.JUMPING) {
         this.field_75648_a.setAIMoveSpeed((float)(this.speed * this.field_75648_a.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));
         if (this.field_75648_a.onGround) {
            this.field_188491_h = MovementController.Action.WAIT;
         }
      } else {
         this.field_75648_a.setMoveForward(0.0F);
      }

   }

   /**
    * Attempt to rotate the first angle to become the second angle, but only allow overall direction change to at max be
    * third parameter
    */
   protected float limitAngle(float sourceAngle, float targetAngle, float maximumChange) {
      float f = MathHelper.wrapDegrees(targetAngle - sourceAngle);
      if (f > maximumChange) {
         f = maximumChange;
      }

      if (f < -maximumChange) {
         f = -maximumChange;
      }

      float f1 = sourceAngle + f;
      if (f1 < 0.0F) {
         f1 += 360.0F;
      } else if (f1 > 360.0F) {
         f1 -= 360.0F;
      }

      return f1;
   }

   public double getX() {
      return this.posX;
   }

   public double getY() {
      return this.posY;
   }

   public double getZ() {
      return this.posZ;
   }

   public static enum Action {
      WAIT,
      MOVE_TO,
      STRAFE,
      JUMPING;
   }
}