package net.minecraft.entity.passive;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CowEntity extends AnimalEntity {
   public CowEntity(EntityType<? extends CowEntity> type, World p_i48567_2_) {
      super(type, p_i48567_2_);
   }

   protected void initEntityAI() {
      this.field_70714_bg.addTask(0, new SwimGoal(this));
      this.field_70714_bg.addTask(1, new PanicGoal(this, 2.0D));
      this.field_70714_bg.addTask(2, new BreedGoal(this, 1.0D));
      this.field_70714_bg.addTask(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.WHEAT), false));
      this.field_70714_bg.addTask(4, new FollowParentGoal(this, 1.25D));
      this.field_70714_bg.addTask(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.field_70714_bg.addTask(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
      this.field_70714_bg.addTask(7, new LookRandomlyGoal(this));
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.2F);
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_COW_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_COW_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_COW_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
   }

   /**
    * Returns the volume for the sounds this mob makes.
    */
   protected float getSoundVolume() {
      return 0.4F;
   }

   public boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (itemstack.getItem() == Items.BUCKET && !player.playerAbilities.isCreativeMode && !this.isChild()) {
         player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
         itemstack.shrink(1);
         if (itemstack.isEmpty()) {
            player.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
         } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET))) {
            player.dropItem(new ItemStack(Items.MILK_BUCKET), false);
         }

         return true;
      } else {
         return super.processInteract(player, hand);
      }
   }

   public CowEntity createChild(AgeableEntity ageable) {
      return EntityType.COW.create(this.world);
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return this.isChild() ? p_213348_2_.height * 0.95F : 1.3F;
   }
}