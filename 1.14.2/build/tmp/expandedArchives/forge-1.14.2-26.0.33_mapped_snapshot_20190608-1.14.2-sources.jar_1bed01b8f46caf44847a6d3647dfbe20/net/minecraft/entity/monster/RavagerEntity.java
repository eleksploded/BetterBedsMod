package net.minecraft.entity.monster;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RavagerEntity extends AbstractRaiderEntity {
   private static final Predicate<Entity> field_213690_b = (p_213685_0_) -> {
      return p_213685_0_.isAlive() && !(p_213685_0_ instanceof RavagerEntity);
   };
   private int attackTick;
   private int stunTick;
   private int roarTick;

   public RavagerEntity(EntityType<? extends RavagerEntity> p_i50197_1_, World p_i50197_2_) {
      super(p_i50197_1_, p_i50197_2_);
      this.stepHeight = 1.0F;
      this.experienceValue = 20;
   }

   protected void initEntityAI() {
      super.initEntityAI();
      this.field_70714_bg.addTask(0, new SwimGoal(this));
      this.field_70714_bg.addTask(4, new RavagerEntity.AttackGoal());
      this.field_70714_bg.addTask(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
      this.field_70714_bg.addTask(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
      this.field_70714_bg.addTask(10, new LookAtGoal(this, MobEntity.class, 8.0F));
      this.field_70715_bh.addTask(2, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).func_220794_a());
      this.field_70715_bh.addTask(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
      this.field_70715_bh.addTask(4, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
      this.field_70715_bh.addTask(4, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
   }

   protected void func_213385_F() {
      boolean flag = !(this.getControllingPassenger() instanceof MobEntity) || this.getControllingPassenger().getType().func_220341_a(EntityTypeTags.field_219765_b);
      boolean flag1 = !(this.getRidingEntity() instanceof BoatEntity);
      this.field_70714_bg.setFlag(Goal.Flag.MOVE, flag);
      this.field_70714_bg.setFlag(Goal.Flag.JUMP, flag && flag1);
      this.field_70714_bg.setFlag(Goal.Flag.LOOK, flag);
      this.field_70714_bg.setFlag(Goal.Flag.TARGET, flag);
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
      this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
      this.getAttribute(SharedMonsterAttributes.field_221120_g).setBaseValue(1.5D);
      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putInt("AttackTick", this.attackTick);
      p_213281_1_.putInt("StunTick", this.stunTick);
      p_213281_1_.putInt("RoarTick", this.roarTick);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.attackTick = compound.getInt("AttackTick");
      this.stunTick = compound.getInt("StunTick");
      this.roarTick = compound.getInt("RoarTick");
   }

   public SoundEvent func_213654_dW() {
      return SoundEvents.field_219645_fe;
   }

   /**
    * Returns new PathNavigateGround instance
    */
   protected PathNavigator createNavigator(World worldIn) {
      return new RavagerEntity.Navigator(this, worldIn);
   }

   public int getHorizontalFaceSpeed() {
      return 45;
   }

   /**
    * Returns the Y offset from the entity's position for any entity riding this one.
    */
   public double getMountedYOffset() {
      return 2.1D;
   }

   /**
    * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
    * by a player and the player is holding a carrot-on-a-stick
    */
   public boolean canBeSteered() {
      return !this.isAIDisabled() && this.getControllingPassenger() instanceof LivingEntity;
   }

   /**
    * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
    * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
    */
   @Nullable
   public Entity getControllingPassenger() {
      return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      super.livingTick();
      if (this.isAlive()) {
         if (this.isMovementBlocked()) {
            this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
         } else {
            double d0 = this.getAttackTarget() != null ? 0.35D : 0.3D;
            double d1 = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
            this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(MathHelper.func_219803_d(0.1D, d1, d0));
         }

         if (this.collidedHorizontally && this.world.getGameRules().getBoolean("mobGriefing")) {
            boolean flag = false;
            AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(0.2D);

            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(MathHelper.floor(axisalignedbb.minX), MathHelper.floor(axisalignedbb.minY), MathHelper.floor(axisalignedbb.minZ), MathHelper.floor(axisalignedbb.maxX), MathHelper.floor(axisalignedbb.maxY), MathHelper.floor(axisalignedbb.maxZ))) {
               BlockState blockstate = this.world.getBlockState(blockpos);
               Block block = blockstate.getBlock();
               if (block instanceof LeavesBlock) {
                  flag = this.world.destroyBlock(blockpos, true) || flag;
               }
            }

            if (!flag && this.onGround) {
               this.jump();
            }
         }

         if (this.roarTick > 0) {
            --this.roarTick;
            if (this.roarTick == 10) {
               this.func_213686_ei();
            }
         }

         if (this.attackTick > 0) {
            --this.attackTick;
         }

         if (this.stunTick > 0) {
            --this.stunTick;
            this.func_213682_eh();
            if (this.stunTick == 0) {
               this.playSound(SoundEvents.field_219650_fj, 1.0F, 1.0F);
               this.roarTick = 20;
            }
         }

      }
   }

   private void func_213682_eh() {
      if (this.rand.nextInt(6) == 0) {
         double d0 = this.posX - (double)this.getWidth() * Math.sin((double)(this.renderYawOffset * ((float)Math.PI / 180F))) + (this.rand.nextDouble() * 0.6D - 0.3D);
         double d1 = this.posY + (double)this.getHeight() - 0.3D;
         double d2 = this.posZ + (double)this.getWidth() * Math.cos((double)(this.renderYawOffset * ((float)Math.PI / 180F))) + (this.rand.nextDouble() * 0.6D - 0.3D);
         this.world.addParticle(ParticleTypes.ENTITY_EFFECT, d0, d1, d2, 0.4980392156862745D, 0.5137254901960784D, 0.5725490196078431D);
      }

   }

   /**
    * Dead and sleeping entities cannot move
    */
   protected boolean isMovementBlocked() {
      return super.isMovementBlocked() || this.attackTick > 0 || this.stunTick > 0 || this.roarTick > 0;
   }

   /**
    * returns true if the entity provided in the argument can be seen. (Raytrace)
    */
   public boolean canEntityBeSeen(Entity entityIn) {
      return this.stunTick <= 0 && this.roarTick <= 0 ? super.canEntityBeSeen(entityIn) : false;
   }

   protected void func_213371_e(LivingEntity p_213371_1_) {
      if (this.roarTick == 0) {
         if (this.rand.nextDouble() < 0.5D) {
            this.stunTick = 40;
            this.playSound(SoundEvents.field_219649_fi, 1.0F, 1.0F);
            this.world.setEntityState(this, (byte)39);
            p_213371_1_.applyEntityCollision(this);
         } else {
            this.func_213688_a(p_213371_1_);
         }

         p_213371_1_.velocityChanged = true;
      }

   }

   private void func_213686_ei() {
      if (this.isAlive()) {
         for(Entity entity : this.world.getEntitiesWithinAABB(LivingEntity.class, this.getBoundingBox().grow(4.0D), field_213690_b)) {
            if (!(entity instanceof AbstractIllagerEntity)) {
               entity.attackEntityFrom(DamageSource.causeMobDamage(this), 6.0F);
            }

            this.func_213688_a(entity);
         }

         Vec3d vec3d = this.getBoundingBox().getCenter();

         for(int i = 0; i < 40; ++i) {
            double d0 = this.rand.nextGaussian() * 0.2D;
            double d1 = this.rand.nextGaussian() * 0.2D;
            double d2 = this.rand.nextGaussian() * 0.2D;
            this.world.addParticle(ParticleTypes.POOF, vec3d.x, vec3d.y, vec3d.z, d0, d1, d2);
         }
      }

   }

   private void func_213688_a(Entity p_213688_1_) {
      double d0 = p_213688_1_.posX - this.posX;
      double d1 = p_213688_1_.posZ - this.posZ;
      double d2 = d0 * d0 + d1 * d1;
      p_213688_1_.addVelocity(d0 / d2 * 4.0D, 0.2D, d1 / d2 * 4.0D);
   }

   /**
    * Handler for {@link World#setEntityState}
    */
   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 4) {
         this.attackTick = 10;
         this.playSound(SoundEvents.field_219644_fd, 1.0F, 1.0F);
      } else if (id == 39) {
         this.stunTick = 40;
      }

      super.handleStatusUpdate(id);
   }

   @OnlyIn(Dist.CLIENT)
   public int func_213683_l() {
      return this.attackTick;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_213684_dX() {
      return this.stunTick;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_213687_eg() {
      return this.roarTick;
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      this.attackTick = 10;
      this.world.setEntityState(this, (byte)4);
      this.playSound(SoundEvents.field_219644_fd, 1.0F, 1.0F);
      return super.attackEntityAsMob(entityIn);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return SoundEvents.field_219643_fc;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.field_219647_fg;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.field_219646_ff;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.field_219648_fh, 0.15F, 1.0F);
   }

   public boolean isNotColliding(IWorldReader worldIn) {
      return !worldIn.containsAnyLiquid(this.getBoundingBox());
   }

   public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {
   }

   public boolean func_213637_dY() {
      return false;
   }

   class AttackGoal extends MeleeAttackGoal {
      public AttackGoal() {
         super(RavagerEntity.this, 1.0D, true);
      }

      protected double getAttackReachSqr(LivingEntity attackTarget) {
         float f = RavagerEntity.this.getWidth() - 0.1F;
         return (double)(f * 2.0F * f * 2.0F + attackTarget.getWidth());
      }
   }

   static class Navigator extends GroundPathNavigator {
      public Navigator(MobEntity p_i50754_1_, World p_i50754_2_) {
         super(p_i50754_1_, p_i50754_2_);
      }

      protected PathFinder getPathFinder(int p_179679_1_) {
         this.nodeProcessor = new RavagerEntity.Processor();
         return new PathFinder(this.nodeProcessor, p_179679_1_);
      }
   }

   static class Processor extends WalkNodeProcessor {
      private Processor() {
      }

      protected PathNodeType func_215744_a(IBlockReader p_215744_1_, boolean p_215744_2_, boolean p_215744_3_, BlockPos p_215744_4_, PathNodeType p_215744_5_) {
         return p_215744_5_ == PathNodeType.LEAVES ? PathNodeType.OPEN : super.func_215744_a(p_215744_1_, p_215744_2_, p_215744_3_, p_215744_4_, p_215744_5_);
      }
   }
}