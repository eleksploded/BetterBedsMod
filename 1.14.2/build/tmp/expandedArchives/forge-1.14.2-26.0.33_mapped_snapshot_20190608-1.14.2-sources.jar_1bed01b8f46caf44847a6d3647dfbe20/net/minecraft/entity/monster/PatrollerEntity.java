package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.raid.Raid;

public abstract class PatrollerEntity extends MonsterEntity {
   private BlockPos patrolTarget;
   private boolean patrolLeader;
   private boolean patrolling;

   protected PatrollerEntity(EntityType<? extends PatrollerEntity> p_i50201_1_, World p_i50201_2_) {
      super(p_i50201_1_, p_i50201_2_);
   }

   protected void initEntityAI() {
      super.initEntityAI();
      this.field_70714_bg.addTask(4, new PatrollerEntity.PatrolGoal<>(this, 0.7D, 0.595D));
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      if (this.patrolTarget != null) {
         p_213281_1_.put("PatrolTarget", NBTUtil.writeBlockPos(this.patrolTarget));
      }

      p_213281_1_.putBoolean("PatrolLeader", this.patrolLeader);
      p_213281_1_.putBoolean("Patrolling", this.patrolling);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      if (compound.contains("PatrolTarget")) {
         this.patrolTarget = NBTUtil.readBlockPos(compound.getCompound("PatrolTarget"));
      }

      this.patrolLeader = compound.getBoolean("PatrolLeader");
      this.patrolling = compound.getBoolean("Patrolling");
   }

   /**
    * Returns the Y Offset of this entity.
    */
   public double getYOffset() {
      return -0.45D;
   }

   public boolean func_213637_dY() {
      return true;
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      if (p_213386_3_ != SpawnReason.PATROL && p_213386_3_ != SpawnReason.EVENT && p_213386_3_ != SpawnReason.STRUCTURE && this.rand.nextFloat() < 0.06F && this.func_213637_dY()) {
         this.patrolLeader = true;
      }

      if (this.func_213630_eb()) {
         this.setItemStackToSlot(EquipmentSlotType.HEAD, Raid.ILLAGER_BANNER);
         this.setDropChance(EquipmentSlotType.HEAD, 2.0F);
      }

      if (p_213386_3_ == SpawnReason.PATROL) {
         this.patrolling = true;
      }

      return super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
   }

   public boolean func_213397_c(double p_213397_1_) {
      return !this.patrolling || p_213397_1_ > 16384.0D;
   }

   public void func_213631_g(BlockPos p_213631_1_) {
      this.patrolTarget = p_213631_1_;
      this.patrolling = true;
   }

   public BlockPos func_213638_dZ() {
      return this.patrolTarget;
   }

   public boolean func_213632_ea() {
      return this.patrolTarget != null;
   }

   public void func_213635_r(boolean p_213635_1_) {
      this.patrolLeader = p_213635_1_;
      this.patrolling = true;
   }

   public boolean func_213630_eb() {
      return this.patrolLeader;
   }

   public boolean func_213634_ed() {
      return true;
   }

   public void func_213636_ee() {
      this.patrolTarget = (new BlockPos(this)).add(-500 + this.rand.nextInt(1000), 0, -500 + this.rand.nextInt(1000));
      this.patrolling = true;
   }

   protected boolean func_213633_ef() {
      return this.patrolling;
   }

   public static class PatrolGoal<T extends PatrollerEntity> extends Goal {
      private final T field_220839_a;
      private final double field_220840_b;
      private final double field_220841_c;

      public PatrolGoal(T p_i50070_1_, double p_i50070_2_, double p_i50070_4_) {
         this.field_220839_a = p_i50070_1_;
         this.field_220840_b = p_i50070_2_;
         this.field_220841_c = p_i50070_4_;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return this.field_220839_a.func_213633_ef() && this.field_220839_a.getAttackTarget() == null && !this.field_220839_a.isBeingRidden() && this.field_220839_a.func_213632_ea();
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         boolean flag = this.field_220839_a.func_213630_eb();
         PathNavigator pathnavigator = this.field_220839_a.getNavigator();
         if (pathnavigator.noPath()) {
            if (flag && this.field_220839_a.func_213638_dZ().func_218137_a(this.field_220839_a.getPositionVec(), 10.0D)) {
               this.field_220839_a.func_213636_ee();
            } else {
               Vec3d vec3d = new Vec3d(this.field_220839_a.func_213638_dZ());
               Vec3d vec3d1 = new Vec3d(this.field_220839_a.posX, this.field_220839_a.posY, this.field_220839_a.posZ);
               Vec3d vec3d2 = vec3d1.subtract(vec3d);
               vec3d = vec3d2.rotateYaw(90.0F).scale(0.4D).add(vec3d);
               Vec3d vec3d3 = vec3d.subtract(vec3d1).normalize().scale(10.0D).add(vec3d1);
               BlockPos blockpos = new BlockPos(vec3d3);
               blockpos = this.field_220839_a.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, blockpos);
               if (!pathnavigator.tryMoveToXYZ((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), flag ? this.field_220841_c : this.field_220840_b)) {
                  this.func_220837_g();
               } else if (flag) {
                  for(PatrollerEntity patrollerentity : this.field_220839_a.world.getEntitiesWithinAABB(PatrollerEntity.class, this.field_220839_a.getBoundingBox().grow(16.0D), (p_220838_0_) -> {
                     return !p_220838_0_.func_213630_eb() && p_220838_0_.func_213634_ed();
                  })) {
                     patrollerentity.func_213631_g(blockpos);
                  }
               }
            }
         }

      }

      private void func_220837_g() {
         Random random = this.field_220839_a.getRNG();
         BlockPos blockpos = this.field_220839_a.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (new BlockPos(this.field_220839_a)).add(-8 + random.nextInt(16), 0, -8 + random.nextInt(16)));
         this.field_220839_a.getNavigator().tryMoveToXYZ((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), this.field_220840_b);
      }
   }
}