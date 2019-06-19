package net.minecraft.entity.monster;

import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.Map;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VindicatorEntity extends AbstractIllagerEntity {
   private static final Predicate<Difficulty> field_213681_b = (p_213678_0_) -> {
      return p_213678_0_ == Difficulty.NORMAL || p_213678_0_ == Difficulty.HARD;
   };
   private boolean johnny;

   public VindicatorEntity(EntityType<? extends VindicatorEntity> p_i50189_1_, World p_i50189_2_) {
      super(p_i50189_1_, p_i50189_2_);
   }

   protected void initEntityAI() {
      super.initEntityAI();
      this.field_70714_bg.addTask(0, new SwimGoal(this));
      this.field_70714_bg.addTask(1, new VindicatorEntity.BreakDoorGoal(this));
      this.field_70714_bg.addTask(2, new AbstractIllagerEntity.RaidOpenDoorGoal(this));
      this.field_70714_bg.addTask(3, new AbstractRaiderEntity.FindTargetGoal(this, 10.0F));
      this.field_70714_bg.addTask(4, new VindicatorEntity.AttackGoal(this));
      this.field_70715_bh.addTask(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).func_220794_a());
      this.field_70715_bh.addTask(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
      this.field_70715_bh.addTask(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
      this.field_70715_bh.addTask(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
      this.field_70715_bh.addTask(4, new VindicatorEntity.JohnnyAttackGoal(this));
      this.field_70714_bg.addTask(8, new RandomWalkingGoal(this, 0.6D));
      this.field_70714_bg.addTask(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
      this.field_70714_bg.addTask(10, new LookAtGoal(this, MobEntity.class, 8.0F));
   }

   protected void updateAITasks() {
      if (!this.isAIDisabled()) {
         if (((ServerWorld)this.world).hasRaid(new BlockPos(this))) {
            ((GroundPathNavigator)this.getNavigator()).setBreakDoors(true);
         } else {
            ((GroundPathNavigator)this.getNavigator()).setBreakDoors(false);
         }
      }

      super.updateAITasks();
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.35F);
      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(12.0D);
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      if (this.johnny) {
         p_213281_1_.putBoolean("Johnny", true);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public AbstractIllagerEntity.ArmPose getArmPose() {
      if (this.func_213398_dR()) {
         return AbstractIllagerEntity.ArmPose.ATTACKING;
      } else {
         return this.func_213656_en() ? AbstractIllagerEntity.ArmPose.CELEBRATING : AbstractIllagerEntity.ArmPose.CROSSED;
      }
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      if (compound.contains("Johnny", 99)) {
         this.johnny = compound.getBoolean("Johnny");
      }

   }

   public SoundEvent func_213654_dW() {
      return SoundEvents.field_219707_mP;
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      ILivingEntityData ilivingentitydata = super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
      ((GroundPathNavigator)this.getNavigator()).setBreakDoors(true);
      this.setEquipmentBasedOnDifficulty(p_213386_2_);
      this.setEnchantmentBasedOnDifficulty(p_213386_2_);
      return ilivingentitydata;
   }

   /**
    * Gives armor or weapon for entity based on given DifficultyInstance
    */
   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
      if (this.func_213663_ek() == null) {
         this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_AXE));
      }

   }

   /**
    * Returns whether this Entity is on the same team as the given Entity.
    */
   public boolean isOnSameTeam(Entity entityIn) {
      if (super.isOnSameTeam(entityIn)) {
         return true;
      } else if (entityIn instanceof LivingEntity && ((LivingEntity)entityIn).getCreatureAttribute() == CreatureAttribute.ILLAGER) {
         return this.getTeam() == null && entityIn.getTeam() == null;
      } else {
         return false;
      }
   }

   public void setCustomName(@Nullable ITextComponent name) {
      super.setCustomName(name);
      if (!this.johnny && name != null && name.getString().equals("Johnny")) {
         this.johnny = true;
      }

   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_VINDICATOR_AMBIENT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_VINDICATOR_DEATH;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_VINDICATOR_HURT;
   }

   public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {
      ItemStack itemstack = new ItemStack(Items.IRON_AXE);
      Raid raid = this.func_213663_ek();
      int i = 1;
      if (p_213660_1_ > raid.func_221306_a(Difficulty.NORMAL)) {
         i = 2;
      }

      boolean flag = this.rand.nextFloat() <= raid.func_221308_w();
      if (flag) {
         Map<Enchantment, Integer> map = Maps.newHashMap();
         map.put(Enchantments.SHARPNESS, i);
         EnchantmentHelper.setEnchantments(map, itemstack);
      }

      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
   }

   class AttackGoal extends MeleeAttackGoal {
      public AttackGoal(VindicatorEntity p_i50577_2_) {
         super(p_i50577_2_, 1.0D, false);
      }

      protected double getAttackReachSqr(LivingEntity attackTarget) {
         if (this.field_75441_b.getRidingEntity() instanceof RavagerEntity) {
            float f = this.field_75441_b.getRidingEntity().getWidth() - 0.1F;
            return (double)(f * 2.0F * f * 2.0F + attackTarget.getWidth());
         } else {
            return super.getAttackReachSqr(attackTarget);
         }
      }
   }

   static class BreakDoorGoal extends net.minecraft.entity.ai.goal.BreakDoorGoal {
      public BreakDoorGoal(MobEntity p_i50578_1_) {
         super(p_i50578_1_, 6, VindicatorEntity.field_213681_b);
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         VindicatorEntity vindicatorentity = (VindicatorEntity)this.field_75356_a;
         return vindicatorentity.func_213657_el() && super.shouldContinueExecuting();
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         VindicatorEntity vindicatorentity = (VindicatorEntity)this.field_75356_a;
         return vindicatorentity.func_213657_el() && vindicatorentity.rand.nextInt(10) == 0 && super.shouldExecute();
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         super.startExecuting();
         this.field_75356_a.func_213332_m(0);
      }
   }

   static class JohnnyAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
      public JohnnyAttackGoal(VindicatorEntity vindicator) {
         super(vindicator, LivingEntity.class, 0, true, true, LivingEntity::attackable);
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return ((VindicatorEntity)this.field_75299_d).johnny && super.shouldExecute();
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         super.startExecuting();
         this.field_75299_d.func_213332_m(0);
      }
   }
}