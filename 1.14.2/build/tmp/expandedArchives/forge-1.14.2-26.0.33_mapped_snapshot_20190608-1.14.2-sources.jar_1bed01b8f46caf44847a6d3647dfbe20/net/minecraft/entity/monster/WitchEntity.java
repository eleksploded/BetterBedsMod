package net.minecraft.entity.monster;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetExpiringGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.ToggleableNearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WitchEntity extends AbstractRaiderEntity implements IRangedAttackMob {
   private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25D, AttributeModifier.Operation.ADDITION)).setSaved(false);
   private static final DataParameter<Boolean> IS_DRINKING = EntityDataManager.createKey(WitchEntity.class, DataSerializers.field_187198_h);
   private int potionUseTimer;
   private NearestAttackableTargetExpiringGoal<AbstractRaiderEntity> field_213694_bC;
   private ToggleableNearestAttackableTargetGoal<PlayerEntity> field_213695_bD;

   public WitchEntity(EntityType<? extends WitchEntity> p_i50188_1_, World p_i50188_2_) {
      super(p_i50188_1_, p_i50188_2_);
   }

   protected void initEntityAI() {
      super.initEntityAI();
      this.field_213694_bC = new NearestAttackableTargetExpiringGoal<>(this, AbstractRaiderEntity.class, true, (p_213693_1_) -> {
         return p_213693_1_ != null && this.func_213657_el() && p_213693_1_.getType() != EntityType.WITCH;
      });
      this.field_213695_bD = new ToggleableNearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (Predicate<LivingEntity>)null);
      this.field_70714_bg.addTask(1, new SwimGoal(this));
      this.field_70714_bg.addTask(2, new RangedAttackGoal(this, 1.0D, 60, 10.0F));
      this.field_70714_bg.addTask(2, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.field_70714_bg.addTask(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      this.field_70714_bg.addTask(3, new LookRandomlyGoal(this));
      this.field_70715_bh.addTask(1, new HurtByTargetGoal(this, AbstractRaiderEntity.class));
      this.field_70715_bh.addTask(2, this.field_213694_bC);
      this.field_70715_bh.addTask(3, this.field_213695_bD);
   }

   protected void registerData() {
      super.registerData();
      this.getDataManager().register(IS_DRINKING, false);
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_WITCH_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_WITCH_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_WITCH_DEATH;
   }

   /**
    * Set whether this witch is aggressive at an entity.
    */
   public void setDrinkingPotion(boolean drinkingPotion) {
      this.getDataManager().set(IS_DRINKING, drinkingPotion);
   }

   public boolean isDrinkingPotion() {
      return this.getDataManager().get(IS_DRINKING);
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      if (!this.world.isRemote && this.isAlive()) {
         this.field_213694_bC.func_220780_j();
         if (this.field_213694_bC.func_220781_h() <= 0) {
            this.field_213695_bD.func_220783_a(true);
         } else {
            this.field_213695_bD.func_220783_a(false);
         }

         if (this.isDrinkingPotion()) {
            if (this.potionUseTimer-- <= 0) {
               this.setDrinkingPotion(false);
               ItemStack itemstack = this.getHeldItemMainhand();
               this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
               if (itemstack.getItem() == Items.POTION) {
                  List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemstack);
                  if (list != null) {
                     for(EffectInstance effectinstance : list) {
                        this.addPotionEffect(new EffectInstance(effectinstance));
                     }
                  }
               }

               this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(MODIFIER);
            }
         } else {
            Potion potion = null;
            if (this.rand.nextFloat() < 0.15F && this.areEyesInFluid(FluidTags.WATER) && !this.isPotionActive(Effects.field_76427_o)) {
               potion = Potions.field_185248_t;
            } else if (this.rand.nextFloat() < 0.15F && (this.isBurning() || this.getLastDamageSource() != null && this.getLastDamageSource().isFireDamage()) && !this.isPotionActive(Effects.field_76426_n)) {
               potion = Potions.field_185241_m;
            } else if (this.rand.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
               potion = Potions.field_185250_v;
            } else if (this.rand.nextFloat() < 0.5F && this.getAttackTarget() != null && !this.isPotionActive(Effects.field_76424_c) && this.getAttackTarget().getDistanceSq(this) > 121.0D) {
               potion = Potions.field_185243_o;
            }

            if (potion != null) {
               this.setItemStackToSlot(EquipmentSlotType.MAINHAND, PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion));
               this.potionUseTimer = this.getHeldItemMainhand().getUseDuration();
               this.setDrinkingPotion(true);
               this.world.playSound((PlayerEntity)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_DRINK, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
               IAttributeInstance iattributeinstance = this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
               iattributeinstance.removeModifier(MODIFIER);
               iattributeinstance.applyModifier(MODIFIER);
            }
         }

         if (this.rand.nextFloat() < 7.5E-4F) {
            this.world.setEntityState(this, (byte)15);
         }
      }

      super.livingTick();
   }

   public SoundEvent func_213654_dW() {
      return SoundEvents.field_219725_nh;
   }

   /**
    * Handler for {@link World#setEntityState}
    */
   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 15) {
         for(int i = 0; i < this.rand.nextInt(35) + 10; ++i) {
            this.world.addParticle(ParticleTypes.WITCH, this.posX + this.rand.nextGaussian() * (double)0.13F, this.getBoundingBox().maxY + 0.5D + this.rand.nextGaussian() * (double)0.13F, this.posZ + this.rand.nextGaussian() * (double)0.13F, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.handleStatusUpdate(id);
      }

   }

   /**
    * Reduces damage, depending on potions
    */
   protected float applyPotionDamageCalculations(DamageSource source, float damage) {
      damage = super.applyPotionDamageCalculations(source, damage);
      if (source.getTrueSource() == this) {
         damage = 0.0F;
      }

      if (source.isMagicDamage()) {
         damage = (float)((double)damage * 0.15D);
      }

      return damage;
   }

   /**
    * Attack the specified entity using a ranged attack.
    */
   public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
      if (!this.isDrinkingPotion()) {
         Vec3d vec3d = target.getMotion();
         double d0 = target.posX + vec3d.x - this.posX;
         double d1 = target.posY + (double)target.getEyeHeight() - (double)1.1F - this.posY;
         double d2 = target.posZ + vec3d.z - this.posZ;
         float f = MathHelper.sqrt(d0 * d0 + d2 * d2);
         Potion potion = Potions.field_185252_x;
         if (target instanceof AbstractRaiderEntity) {
            if (target.getHealth() <= 4.0F) {
               potion = Potions.field_185250_v;
            } else {
               potion = Potions.field_185220_C;
            }

            this.setAttackTarget((LivingEntity)null);
         } else if (f >= 8.0F && !target.isPotionActive(Effects.field_76421_d)) {
            potion = Potions.field_185246_r;
         } else if (target.getHealth() >= 8.0F && !target.isPotionActive(Effects.field_76436_u)) {
            potion = Potions.field_185254_z;
         } else if (f <= 3.0F && !target.isPotionActive(Effects.field_76437_t) && this.rand.nextFloat() < 0.25F) {
            potion = Potions.field_185226_I;
         }

         PotionEntity potionentity = new PotionEntity(this.world, this);
         potionentity.setItem(PotionUtils.addPotionToItemStack(new ItemStack(Items.SPLASH_POTION), potion));
         potionentity.rotationPitch -= -20.0F;
         potionentity.shoot(d0, d1 + (double)(f * 0.2F), d2, 0.75F, 8.0F);
         this.world.playSound((PlayerEntity)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
         this.world.func_217376_c(potionentity);
      }
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return 1.62F;
   }

   public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {
   }

   public boolean func_213637_dY() {
      return false;
   }
}