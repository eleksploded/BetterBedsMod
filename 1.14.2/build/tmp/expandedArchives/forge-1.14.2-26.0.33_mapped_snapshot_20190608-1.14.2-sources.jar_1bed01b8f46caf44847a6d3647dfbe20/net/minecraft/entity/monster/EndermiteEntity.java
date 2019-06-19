package net.minecraft.entity.monster;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class EndermiteEntity extends MonsterEntity {
   private static final EntityPredicate field_213628_b = (new EntityPredicate()).func_221013_a(5.0D).func_221010_e();
   private int lifetime;
   private boolean playerSpawned;

   public EndermiteEntity(EntityType<? extends EndermiteEntity> p_i50209_1_, World p_i50209_2_) {
      super(p_i50209_1_, p_i50209_2_);
      this.experienceValue = 3;
   }

   protected void initEntityAI() {
      this.field_70714_bg.addTask(1, new SwimGoal(this));
      this.field_70714_bg.addTask(2, new MeleeAttackGoal(this, 1.0D, false));
      this.field_70714_bg.addTask(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.field_70714_bg.addTask(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      this.field_70714_bg.addTask(8, new LookRandomlyGoal(this));
      this.field_70715_bh.addTask(1, (new HurtByTargetGoal(this)).func_220794_a());
      this.field_70715_bh.addTask(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return 0.1F;
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
   }

   /**
    * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
    * prevent them from trampling crops
    */
   protected boolean canTriggerWalking() {
      return false;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_ENDERMITE_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_ENDERMITE_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_ENDERMITE_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.ENTITY_ENDERMITE_STEP, 0.15F, 1.0F);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.lifetime = compound.getInt("Lifetime");
      this.playerSpawned = compound.getBoolean("PlayerSpawned");
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putInt("Lifetime", this.lifetime);
      p_213281_1_.putBoolean("PlayerSpawned", this.playerSpawned);
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      this.renderYawOffset = this.rotationYaw;
      super.tick();
   }

   /**
    * Set the render yaw offset
    */
   public void setRenderYawOffset(float offset) {
      this.rotationYaw = offset;
      super.setRenderYawOffset(offset);
   }

   /**
    * Returns the Y Offset of this entity.
    */
   public double getYOffset() {
      return 0.1D;
   }

   public boolean isSpawnedByPlayer() {
      return this.playerSpawned;
   }

   /**
    * Sets if this mob was spawned by a player or not.
    */
   public void setSpawnedByPlayer(boolean spawnedByPlayer) {
      this.playerSpawned = spawnedByPlayer;
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      super.livingTick();
      if (this.world.isRemote) {
         for(int i = 0; i < 2; ++i) {
            this.world.addParticle(ParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.getWidth(), this.posY + this.rand.nextDouble() * (double)this.getHeight(), this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.getWidth(), (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
         }
      } else {
         if (!this.isNoDespawnRequired()) {
            ++this.lifetime;
         }

         if (this.lifetime >= 2400) {
            this.remove();
         }
      }

   }

   /**
    * Checks to make sure the light is not too bright where the mob is spawning
    */
   protected boolean isValidLightLevel() {
      return true;
   }

   public boolean canSpawn(IWorld p_213380_1_, SpawnReason p_213380_2_) {
      if (super.canSpawn(p_213380_1_, p_213380_2_)) {
         PlayerEntity playerentity = this.world.func_217370_a(field_213628_b, this);
         return playerentity == null;
      } else {
         return false;
      }
   }

   public CreatureAttribute getCreatureAttribute() {
      return CreatureAttribute.ARTHROPOD;
   }
}