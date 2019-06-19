package net.minecraft.entity.passive;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PandaEntity extends AnimalEntity {
   private static final DataParameter<Integer> field_213609_bA = EntityDataManager.createKey(PandaEntity.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_213593_bB = EntityDataManager.createKey(PandaEntity.class, DataSerializers.field_187192_b);
   private static final DataParameter<Integer> field_213594_bD = EntityDataManager.createKey(PandaEntity.class, DataSerializers.field_187192_b);
   private static final DataParameter<Byte> field_213595_bE = EntityDataManager.createKey(PandaEntity.class, DataSerializers.field_187191_a);
   private static final DataParameter<Byte> field_213596_bF = EntityDataManager.createKey(PandaEntity.class, DataSerializers.field_187191_a);
   private static final DataParameter<Byte> field_213597_bG = EntityDataManager.createKey(PandaEntity.class, DataSerializers.field_187191_a);
   private boolean field_213598_bH;
   private boolean field_213599_bI;
   public int field_213608_bz;
   private Vec3d field_213600_bJ;
   private float field_213601_bK;
   private float field_213602_bL;
   private float field_213603_bM;
   private float field_213604_bN;
   private float field_213605_bO;
   private float field_213606_bP;
   private static final Predicate<ItemEntity> field_213607_bQ = (p_213575_0_) -> {
      Item item = p_213575_0_.getItem().getItem();
      return (item == Blocks.field_222405_kQ.asItem() || item == Blocks.CAKE.asItem()) && p_213575_0_.isAlive() && !p_213575_0_.cannotPickup();
   };

   public PandaEntity(EntityType<? extends PandaEntity> p_i50252_1_, World p_i50252_2_) {
      super(p_i50252_1_, p_i50252_2_);
      this.field_70765_h = new PandaEntity.MoveHelperController(this);
      if (!this.isChild()) {
         this.setCanPickUpLoot(true);
      }

   }

   public boolean func_213365_e(ItemStack p_213365_1_) {
      EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(p_213365_1_);
      if (!this.getItemStackFromSlot(equipmentslottype).isEmpty()) {
         return false;
      } else {
         return equipmentslottype == EquipmentSlotType.MAINHAND && super.func_213365_e(p_213365_1_);
      }
   }

   public int func_213544_dV() {
      return this.dataManager.get(field_213609_bA);
   }

   public void func_213588_r(int p_213588_1_) {
      this.dataManager.set(field_213609_bA, p_213588_1_);
   }

   public boolean func_213539_dW() {
      return this.func_213547_u(2);
   }

   public boolean func_213556_dX() {
      return this.func_213547_u(8);
   }

   public void func_213553_r(boolean p_213553_1_) {
      this.func_213587_d(8, p_213553_1_);
   }

   public boolean func_213567_dY() {
      return this.func_213547_u(16);
   }

   public void func_213542_s(boolean p_213542_1_) {
      this.func_213587_d(16, p_213542_1_);
   }

   public boolean func_213578_dZ() {
      return this.dataManager.get(field_213594_bD) > 0;
   }

   public void func_213534_t(boolean p_213534_1_) {
      this.dataManager.set(field_213594_bD, p_213534_1_ ? 1 : 0);
   }

   private int func_213559_es() {
      return this.dataManager.get(field_213594_bD);
   }

   private void func_213571_t(int p_213571_1_) {
      this.dataManager.set(field_213594_bD, p_213571_1_);
   }

   public void func_213581_u(boolean p_213581_1_) {
      this.func_213587_d(2, p_213581_1_);
      if (!p_213581_1_) {
         this.func_213562_s(0);
      }

   }

   public int func_213585_ee() {
      return this.dataManager.get(field_213593_bB);
   }

   public void func_213562_s(int p_213562_1_) {
      this.dataManager.set(field_213593_bB, p_213562_1_);
   }

   public PandaEntity.Type func_213549_ef() {
      return PandaEntity.Type.func_221105_a(this.dataManager.get(field_213595_bE));
   }

   public void func_213589_a(PandaEntity.Type p_213589_1_) {
      if (p_213589_1_.func_221106_a() > 6) {
         p_213589_1_ = PandaEntity.Type.func_221104_a(this.rand);
      }

      this.dataManager.set(field_213595_bE, (byte)p_213589_1_.func_221106_a());
   }

   public PandaEntity.Type func_213536_eg() {
      return PandaEntity.Type.func_221105_a(this.dataManager.get(field_213596_bF));
   }

   public void func_213541_b(PandaEntity.Type p_213541_1_) {
      if (p_213541_1_.func_221106_a() > 6) {
         p_213541_1_ = PandaEntity.Type.func_221104_a(this.rand);
      }

      this.dataManager.set(field_213596_bF, (byte)p_213541_1_.func_221106_a());
   }

   public boolean func_213564_eh() {
      return this.func_213547_u(4);
   }

   public void func_213576_v(boolean p_213576_1_) {
      this.func_213587_d(4, p_213576_1_);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_213609_bA, 0);
      this.dataManager.register(field_213593_bB, 0);
      this.dataManager.register(field_213595_bE, (byte)0);
      this.dataManager.register(field_213596_bF, (byte)0);
      this.dataManager.register(field_213597_bG, (byte)0);
      this.dataManager.register(field_213594_bD, 0);
   }

   private boolean func_213547_u(int p_213547_1_) {
      return (this.dataManager.get(field_213597_bG) & p_213547_1_) != 0;
   }

   private void func_213587_d(int p_213587_1_, boolean p_213587_2_) {
      byte b0 = this.dataManager.get(field_213597_bG);
      if (p_213587_2_) {
         this.dataManager.set(field_213597_bG, (byte)(b0 | p_213587_1_));
      } else {
         this.dataManager.set(field_213597_bG, (byte)(b0 & ~p_213587_1_));
      }

   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putString("MainGene", this.func_213549_ef().func_221100_b());
      p_213281_1_.putString("HiddenGene", this.func_213536_eg().func_221100_b());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.func_213589_a(PandaEntity.Type.func_221108_a(compound.getString("MainGene")));
      this.func_213541_b(PandaEntity.Type.func_221108_a(compound.getString("HiddenGene")));
   }

   @Nullable
   public AgeableEntity createChild(AgeableEntity ageable) {
      PandaEntity pandaentity = EntityType.field_220353_aa.create(this.world);
      if (ageable instanceof PandaEntity) {
         pandaentity.func_213545_a(this, (PandaEntity)ageable);
      }

      pandaentity.func_213554_ep();
      return pandaentity;
   }

   protected void initEntityAI() {
      this.field_70714_bg.addTask(0, new SwimGoal(this));
      this.field_70714_bg.addTask(2, new PandaEntity.PanicGoal(this, 2.0D));
      this.field_70714_bg.addTask(2, new PandaEntity.MateGoal(this, 1.0D));
      this.field_70714_bg.addTask(3, new PandaEntity.AttackGoal(this, (double)1.2F, true));
      this.field_70714_bg.addTask(4, new TemptGoal(this, 1.0D, Ingredient.fromItems(Blocks.field_222405_kQ.asItem()), false));
      this.field_70714_bg.addTask(6, new PandaEntity.AvoidGoal<>(this, PlayerEntity.class, 8.0F, 2.0D, 2.0D));
      this.field_70714_bg.addTask(6, new PandaEntity.AvoidGoal<>(this, MonsterEntity.class, 4.0F, 2.0D, 2.0D));
      this.field_70714_bg.addTask(7, new PandaEntity.SitGoal());
      this.field_70714_bg.addTask(8, new PandaEntity.LieBackGoal(this));
      this.field_70714_bg.addTask(8, new PandaEntity.ChildPlayGoal(this));
      this.field_70714_bg.addTask(9, new PandaEntity.WatchGoal(this, PlayerEntity.class, 6.0F));
      this.field_70714_bg.addTask(10, new LookRandomlyGoal(this));
      this.field_70714_bg.addTask(12, new PandaEntity.RollGoal(this));
      this.field_70714_bg.addTask(13, new FollowParentGoal(this, 1.25D));
      this.field_70714_bg.addTask(14, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.field_70715_bh.addTask(1, (new PandaEntity.RevengeGoal(this)).func_220794_a(new Class[0]));
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.15F);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
   }

   public PandaEntity.Type func_213590_ei() {
      return PandaEntity.Type.func_221101_b(this.func_213549_ef(), this.func_213536_eg());
   }

   public boolean func_213584_ej() {
      return this.func_213590_ei() == PandaEntity.Type.LAZY;
   }

   public boolean func_213569_ek() {
      return this.func_213590_ei() == PandaEntity.Type.WORRIED;
   }

   public boolean func_213557_el() {
      return this.func_213590_ei() == PandaEntity.Type.PLAYFUL;
   }

   public boolean func_213582_en() {
      return this.func_213590_ei() == PandaEntity.Type.WEAK;
   }

   public boolean func_213398_dR() {
      return this.func_213590_ei() == PandaEntity.Type.AGGRESSIVE;
   }

   public boolean canBeLeashedTo(PlayerEntity player) {
      return false;
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      this.playSound(SoundEvents.field_219680_ht, 1.0F, 1.0F);
      if (!this.func_213398_dR()) {
         this.field_213599_bI = true;
      }

      return super.attackEntityAsMob(entityIn);
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
      if (this.func_213569_ek()) {
         if (this.world.isThundering() && !this.isInWater()) {
            this.func_213553_r(true);
            this.func_213534_t(false);
         } else if (!this.func_213578_dZ()) {
            this.func_213553_r(false);
         }
      }

      if (this.getAttackTarget() == null) {
         this.field_213598_bH = false;
         this.field_213599_bI = false;
      }

      if (this.func_213544_dV() > 0) {
         if (this.getAttackTarget() != null) {
            this.faceEntity(this.getAttackTarget(), 90.0F, 90.0F);
         }

         if (this.func_213544_dV() == 29 || this.func_213544_dV() == 14) {
            this.playSound(SoundEvents.field_219676_hp, 1.0F, 1.0F);
         }

         this.func_213588_r(this.func_213544_dV() - 1);
      }

      if (this.func_213539_dW()) {
         this.func_213562_s(this.func_213585_ee() + 1);
         if (this.func_213585_ee() > 20) {
            this.func_213581_u(false);
            this.func_213577_ez();
         } else if (this.func_213585_ee() == 1) {
            this.playSound(SoundEvents.field_219669_hj, 1.0F, 1.0F);
         }
      }

      if (this.func_213564_eh()) {
         this.func_213535_ey();
      } else {
         this.field_213608_bz = 0;
      }

      if (this.func_213556_dX()) {
         this.rotationPitch = 0.0F;
      }

      this.func_213574_ev();
      this.func_213546_et();
      this.func_213563_ew();
      this.func_213550_ex();
   }

   public boolean func_213566_eo() {
      return this.func_213569_ek() && this.world.isThundering();
   }

   private void func_213546_et() {
      if (!this.func_213578_dZ() && this.func_213556_dX() && !this.func_213566_eo() && !this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty() && this.rand.nextInt(80) == 1) {
         this.func_213534_t(true);
      } else if (this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty() || !this.func_213556_dX()) {
         this.func_213534_t(false);
      }

      if (this.func_213578_dZ()) {
         this.func_213533_eu();
         if (!this.world.isRemote && this.func_213559_es() > 80 && this.rand.nextInt(20) == 1) {
            if (this.func_213559_es() > 100 && this.func_213548_j(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND))) {
               if (!this.world.isRemote) {
                  this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
               }

               this.func_213553_r(false);
            }

            this.func_213534_t(false);
            return;
         }

         this.func_213571_t(this.func_213559_es() + 1);
      }

   }

   private void func_213533_eu() {
      if (this.func_213559_es() % 5 == 0) {
         this.playSound(SoundEvents.field_219674_hn, 0.5F + 0.5F * (float)this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

         for(int i = 0; i < 6; ++i) {
            Vec3d vec3d = new Vec3d(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double)this.rand.nextFloat() - 0.5D) * 0.1D);
            vec3d = vec3d.rotatePitch(-this.rotationPitch * ((float)Math.PI / 180F));
            vec3d = vec3d.rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F));
            double d0 = (double)(-this.rand.nextFloat()) * 0.6D - 0.3D;
            Vec3d vec3d1 = new Vec3d(((double)this.rand.nextFloat() - 0.5D) * 0.8D, d0, 1.0D + ((double)this.rand.nextFloat() - 0.5D) * 0.4D);
            vec3d1 = vec3d1.rotateYaw(-this.renderYawOffset * ((float)Math.PI / 180F));
            vec3d1 = vec3d1.add(this.posX, this.posY + (double)this.getEyeHeight() + 1.0D, this.posZ);
            this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItemStackFromSlot(EquipmentSlotType.MAINHAND)), vec3d1.x, vec3d1.y, vec3d1.z, vec3d.x, vec3d.y + 0.05D, vec3d.z);
         }
      }

   }

   private void func_213574_ev() {
      this.field_213602_bL = this.field_213601_bK;
      if (this.func_213556_dX()) {
         this.field_213601_bK = Math.min(1.0F, this.field_213601_bK + 0.15F);
      } else {
         this.field_213601_bK = Math.max(0.0F, this.field_213601_bK - 0.19F);
      }

   }

   private void func_213563_ew() {
      this.field_213604_bN = this.field_213603_bM;
      if (this.func_213567_dY()) {
         this.field_213603_bM = Math.min(1.0F, this.field_213603_bM + 0.15F);
      } else {
         this.field_213603_bM = Math.max(0.0F, this.field_213603_bM - 0.19F);
      }

   }

   private void func_213550_ex() {
      this.field_213606_bP = this.field_213605_bO;
      if (this.func_213564_eh()) {
         this.field_213605_bO = Math.min(1.0F, this.field_213605_bO + 0.15F);
      } else {
         this.field_213605_bO = Math.max(0.0F, this.field_213605_bO - 0.19F);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public float func_213561_v(float p_213561_1_) {
      return MathHelper.func_219799_g(p_213561_1_, this.field_213602_bL, this.field_213601_bK);
   }

   @OnlyIn(Dist.CLIENT)
   public float func_213583_w(float p_213583_1_) {
      return MathHelper.func_219799_g(p_213583_1_, this.field_213604_bN, this.field_213603_bM);
   }

   @OnlyIn(Dist.CLIENT)
   public float func_213591_x(float p_213591_1_) {
      return MathHelper.func_219799_g(p_213591_1_, this.field_213606_bP, this.field_213605_bO);
   }

   private void func_213535_ey() {
      ++this.field_213608_bz;
      if (this.field_213608_bz > 32) {
         this.func_213576_v(false);
      } else {
         if (!this.world.isRemote) {
            Vec3d vec3d = this.getMotion();
            if (this.field_213608_bz == 1) {
               float f = this.rotationYaw * ((float)Math.PI / 180F);
               float f1 = this.isChild() ? 0.1F : 0.2F;
               this.field_213600_bJ = new Vec3d(vec3d.x + (double)(-MathHelper.sin(f) * f1), 0.0D, vec3d.z + (double)(MathHelper.cos(f) * f1));
               this.setMotion(this.field_213600_bJ.add(0.0D, 0.27D, 0.0D));
            } else if ((float)this.field_213608_bz != 7.0F && (float)this.field_213608_bz != 15.0F && (float)this.field_213608_bz != 23.0F) {
               this.setMotion(this.field_213600_bJ.x, vec3d.y, this.field_213600_bJ.z);
            } else {
               this.setMotion(0.0D, this.onGround ? 0.27D : vec3d.y, 0.0D);
            }
         }

      }
   }

   private void func_213577_ez() {
      Vec3d vec3d = this.getMotion();
      this.world.addParticle(ParticleTypes.field_218421_R, this.posX - (double)(this.getWidth() + 1.0F) * 0.5D * (double)MathHelper.sin(this.renderYawOffset * ((float)Math.PI / 180F)), this.posY + (double)this.getEyeHeight() - (double)0.1F, this.posZ + (double)(this.getWidth() + 1.0F) * 0.5D * (double)MathHelper.cos(this.renderYawOffset * ((float)Math.PI / 180F)), vec3d.x, 0.0D, vec3d.z);
      this.playSound(SoundEvents.field_219670_hk, 1.0F, 1.0F);

      for(PandaEntity pandaentity : this.world.getEntitiesWithinAABB(PandaEntity.class, this.getBoundingBox().grow(10.0D))) {
         if (!pandaentity.isChild() && pandaentity.onGround && !pandaentity.isInWater() && pandaentity.func_213537_eq()) {
            pandaentity.jump();
         }
      }

      if (!this.world.isRemote() && this.rand.nextInt(700) == 0 && this.world.getGameRules().getBoolean("doMobLoot")) {
         this.entityDropItem(Items.SLIME_BALL);
      }

   }

   /**
    * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
    * better.
    */
   protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
      if (this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty() && field_213607_bQ.test(itemEntity)) {
         ItemStack itemstack = itemEntity.getItem();
         this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
         this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0F;
         this.onItemPickup(itemEntity, itemstack.getCount());
         itemEntity.remove();
      }

   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      this.func_213553_r(false);
      return super.attackEntityFrom(source, amount);
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      p_213386_4_ = super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
      this.func_213589_a(PandaEntity.Type.func_221104_a(this.rand));
      this.func_213541_b(PandaEntity.Type.func_221104_a(this.rand));
      this.func_213554_ep();
      if (p_213386_4_ instanceof PandaEntity.PandaData) {
         if (this.rand.nextInt(5) == 0) {
            this.setGrowingAge(-24000);
         }
      } else {
         p_213386_4_ = new PandaEntity.PandaData();
      }

      return p_213386_4_;
   }

   public void func_213545_a(PandaEntity p_213545_1_, @Nullable PandaEntity p_213545_2_) {
      if (p_213545_2_ == null) {
         if (this.rand.nextBoolean()) {
            this.func_213589_a(p_213545_1_.func_213568_eA());
            this.func_213541_b(PandaEntity.Type.func_221104_a(this.rand));
         } else {
            this.func_213589_a(PandaEntity.Type.func_221104_a(this.rand));
            this.func_213541_b(p_213545_1_.func_213568_eA());
         }
      } else if (this.rand.nextBoolean()) {
         this.func_213589_a(p_213545_1_.func_213568_eA());
         this.func_213541_b(p_213545_2_.func_213568_eA());
      } else {
         this.func_213589_a(p_213545_2_.func_213568_eA());
         this.func_213541_b(p_213545_1_.func_213568_eA());
      }

      if (this.rand.nextInt(32) == 0) {
         this.func_213589_a(PandaEntity.Type.func_221104_a(this.rand));
      }

      if (this.rand.nextInt(32) == 0) {
         this.func_213541_b(PandaEntity.Type.func_221104_a(this.rand));
      }

   }

   private PandaEntity.Type func_213568_eA() {
      return this.rand.nextBoolean() ? this.func_213549_ef() : this.func_213536_eg();
   }

   public void func_213554_ep() {
      if (this.func_213582_en()) {
         this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
      }

      if (this.func_213584_ej()) {
         this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.07F);
      }

   }

   private void func_213586_eB() {
      if (!this.isInWater()) {
         this.setMoveForward(0.0F);
         this.getNavigator().clearPath();
         this.func_213553_r(true);
      }

   }

   public boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (itemstack.getItem() instanceof SpawnEggItem) {
         return super.processInteract(player, hand);
      } else if (this.func_213566_eo()) {
         return false;
      } else if (this.func_213567_dY()) {
         this.func_213542_s(false);
         return true;
      } else if (this.isBreedingItem(itemstack)) {
         if (this.getAttackTarget() != null) {
            this.field_213598_bH = true;
         }

         if (this.isChild()) {
            this.consumeItemFromStack(player, itemstack);
            this.ageUp((int)((float)(-this.getGrowingAge() / 20) * 0.1F), true);
         } else if (!this.world.isRemote && this.getGrowingAge() == 0 && this.canBreed()) {
            this.consumeItemFromStack(player, itemstack);
            this.setInLove(player);
         } else {
            if (this.world.isRemote || this.func_213556_dX() || this.isInWater()) {
               return false;
            }

            this.func_213586_eB();
            this.func_213534_t(true);
            ItemStack itemstack1 = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
            if (!itemstack1.isEmpty() && !player.playerAbilities.isCreativeMode) {
               this.entityDropItem(itemstack1);
            }

            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(itemstack.getItem(), 1));
            this.consumeItemFromStack(player, itemstack);
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      if (this.func_213398_dR()) {
         return SoundEvents.field_219677_hq;
      } else {
         return this.func_213569_ek() ? SoundEvents.field_219678_hr : SoundEvents.field_219672_hl;
      }
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.field_219675_ho, 0.15F, 1.0F);
   }

   /**
    * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
    * the animal type)
    */
   public boolean isBreedingItem(ItemStack stack) {
      return stack.getItem() == Blocks.field_222405_kQ.asItem();
   }

   private boolean func_213548_j(ItemStack p_213548_1_) {
      return this.isBreedingItem(p_213548_1_) || p_213548_1_.getItem() == Blocks.CAKE.asItem();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.field_219673_hm;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.field_219679_hs;
   }

   public boolean func_213537_eq() {
      return !this.func_213567_dY() && !this.func_213566_eo() && !this.func_213578_dZ() && !this.func_213564_eh() && !this.func_213556_dX();
   }

   static class AttackGoal extends MeleeAttackGoal {
      private final PandaEntity field_220722_d;

      public AttackGoal(PandaEntity p_i51467_1_, double p_i51467_2_, boolean p_i51467_4_) {
         super(p_i51467_1_, p_i51467_2_, p_i51467_4_);
         this.field_220722_d = p_i51467_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return this.field_220722_d.func_213537_eq() && super.shouldExecute();
      }
   }

   static class AvoidGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final PandaEntity field_220875_i;

      public AvoidGoal(PandaEntity p_i51466_1_, Class<T> p_i51466_2_, float p_i51466_3_, double p_i51466_4_, double p_i51466_6_) {
         super(p_i51466_1_, p_i51466_2_, p_i51466_3_, p_i51466_4_, p_i51466_6_, EntityPredicates.NOT_SPECTATING::test);
         this.field_220875_i = p_i51466_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return this.field_220875_i.func_213569_ek() && this.field_220875_i.func_213537_eq() && super.shouldExecute();
      }
   }

   static class ChildPlayGoal extends Goal {
      private final PandaEntity field_220833_a;

      public ChildPlayGoal(PandaEntity p_i51448_1_) {
         this.field_220833_a = p_i51448_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (this.field_220833_a.isChild() && this.field_220833_a.func_213537_eq()) {
            if (this.field_220833_a.func_213582_en() && this.field_220833_a.rand.nextInt(500) == 1) {
               return true;
            } else {
               return this.field_220833_a.rand.nextInt(6000) == 1;
            }
         } else {
            return false;
         }
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return false;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.field_220833_a.func_213581_u(true);
      }
   }

   static class LieBackGoal extends Goal {
      private final PandaEntity field_220828_a;
      private int field_220829_b;

      public LieBackGoal(PandaEntity p_i51460_1_) {
         this.field_220828_a = p_i51460_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return this.field_220829_b < this.field_220828_a.ticksExisted && this.field_220828_a.func_213584_ej() && this.field_220828_a.func_213537_eq() && this.field_220828_a.rand.nextInt(400) == 1;
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         if (!this.field_220828_a.isInWater() && (this.field_220828_a.func_213584_ej() || this.field_220828_a.rand.nextInt(600) != 1)) {
            return this.field_220828_a.rand.nextInt(2000) != 1;
         } else {
            return false;
         }
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.field_220828_a.func_213542_s(true);
         this.field_220829_b = 0;
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         this.field_220828_a.func_213542_s(false);
         this.field_220829_b = this.field_220828_a.ticksExisted + 200;
      }
   }

   static class MateGoal extends BreedGoal {
      private static final EntityPredicate field_220692_d = (new EntityPredicate()).func_221013_a(8.0D).func_221011_b().func_221008_a();
      private final PandaEntity field_220693_e;
      private int field_220694_f;

      public MateGoal(PandaEntity p_i51464_1_, double p_i51464_2_) {
         super(p_i51464_1_, p_i51464_2_);
         this.field_220693_e = p_i51464_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (super.shouldExecute() && this.field_220693_e.func_213544_dV() == 0) {
            if (!this.func_220691_h()) {
               if (this.field_220694_f <= this.field_220693_e.ticksExisted) {
                  this.field_220693_e.func_213588_r(32);
                  this.field_220694_f = this.field_220693_e.ticksExisted + 600;
                  if (this.field_220693_e.isServerWorld()) {
                     PlayerEntity playerentity = this.world.func_217370_a(field_220692_d, this.field_220693_e);
                     this.field_220693_e.setAttackTarget(playerentity);
                  }
               }

               return false;
            } else {
               return true;
            }
         } else {
            return false;
         }
      }

      private boolean func_220691_h() {
         BlockPos blockpos = new BlockPos(this.field_220693_e);
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

         for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 8; ++j) {
               for(int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                  for(int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                     blockpos$mutableblockpos.setPos(blockpos).move(k, i, l);
                     if (this.world.getBlockState(blockpos$mutableblockpos).getBlock() == Blocks.field_222405_kQ) {
                        return true;
                     }
                  }
               }
            }
         }

         return false;
      }
   }

   static class MoveHelperController extends MovementController {
      private final PandaEntity field_220672_i;

      public MoveHelperController(PandaEntity p_i51456_1_) {
         super(p_i51456_1_);
         this.field_220672_i = p_i51456_1_;
      }

      public void tick() {
         if (this.field_220672_i.func_213537_eq()) {
            super.tick();
         }
      }
   }

   static class PandaData implements ILivingEntityData {
      private PandaData() {
      }
   }

   static class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
      private final PandaEntity field_220740_f;

      public PanicGoal(PandaEntity p_i51454_1_, double p_i51454_2_) {
         super(p_i51454_1_, p_i51454_2_);
         this.field_220740_f = p_i51454_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (!this.field_220740_f.isBurning()) {
            return false;
         } else {
            BlockPos blockpos = this.getRandPos(this.field_75267_a.world, this.field_75267_a, 5, 4);
            if (blockpos != null) {
               this.randPosX = (double)blockpos.getX();
               this.randPosY = (double)blockpos.getY();
               this.randPosZ = (double)blockpos.getZ();
               return true;
            } else {
               return this.findRandomPosition();
            }
         }
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         if (this.field_220740_f.func_213556_dX()) {
            this.field_220740_f.getNavigator().clearPath();
            return false;
         } else {
            return super.shouldContinueExecuting();
         }
      }
   }

   static class RevengeGoal extends HurtByTargetGoal {
      private final PandaEntity field_220798_a;

      public RevengeGoal(PandaEntity p_i51462_1_, Class<?>... p_i51462_2_) {
         super(p_i51462_1_, p_i51462_2_);
         this.field_220798_a = p_i51462_1_;
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         if (!this.field_220798_a.field_213598_bH && !this.field_220798_a.field_213599_bI) {
            return super.shouldContinueExecuting();
         } else {
            this.field_220798_a.setAttackTarget((LivingEntity)null);
            return false;
         }
      }

      protected void func_220793_a(MobEntity p_220793_1_, LivingEntity p_220793_2_) {
         if (p_220793_1_ instanceof PandaEntity && ((PandaEntity)p_220793_1_).func_213398_dR()) {
            p_220793_1_.setAttackTarget(p_220793_2_);
         }

      }
   }

   static class RollGoal extends Goal {
      private final PandaEntity field_220830_a;

      public RollGoal(PandaEntity p_i51452_1_) {
         this.field_220830_a = p_i51452_1_;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if ((this.field_220830_a.isChild() || this.field_220830_a.func_213557_el()) && this.field_220830_a.onGround) {
            if (!this.field_220830_a.func_213537_eq()) {
               return false;
            } else {
               float f = this.field_220830_a.rotationYaw * ((float)Math.PI / 180F);
               int i = 0;
               int j = 0;
               float f1 = -MathHelper.sin(f);
               float f2 = MathHelper.cos(f);
               if ((double)Math.abs(f1) > 0.5D) {
                  i = (int)((float)i + f1 / Math.abs(f1));
               }

               if ((double)Math.abs(f2) > 0.5D) {
                  j = (int)((float)j + f2 / Math.abs(f2));
               }

               if (this.field_220830_a.world.getBlockState((new BlockPos(this.field_220830_a)).add(i, -1, j)).isAir()) {
                  return true;
               } else if (this.field_220830_a.func_213557_el() && this.field_220830_a.rand.nextInt(60) == 1) {
                  return true;
               } else {
                  return this.field_220830_a.rand.nextInt(500) == 1;
               }
            }
         } else {
            return false;
         }
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return false;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.field_220830_a.func_213576_v(true);
      }

      public boolean isPreemptible() {
         return false;
      }
   }

   class SitGoal extends Goal {
      private int field_220832_b;

      public SitGoal() {
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (this.field_220832_b <= PandaEntity.this.ticksExisted && !PandaEntity.this.isChild() && !PandaEntity.this.isInWater() && PandaEntity.this.func_213537_eq() && PandaEntity.this.func_213544_dV() <= 0) {
            List<ItemEntity> list = PandaEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, PandaEntity.this.getBoundingBox().grow(6.0D, 6.0D, 6.0D), PandaEntity.field_213607_bQ);
            return !list.isEmpty() || !PandaEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
         } else {
            return false;
         }
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         if (!PandaEntity.this.isInWater() && (PandaEntity.this.func_213584_ej() || PandaEntity.this.rand.nextInt(600) != 1)) {
            return PandaEntity.this.rand.nextInt(2000) != 1;
         } else {
            return false;
         }
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         if (!PandaEntity.this.func_213556_dX() && !PandaEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
            PandaEntity.this.func_213586_eB();
         }

      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         List<ItemEntity> list = PandaEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, PandaEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), PandaEntity.field_213607_bQ);
         if (!list.isEmpty() && PandaEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
            PandaEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
         } else if (!PandaEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
            PandaEntity.this.func_213586_eB();
         }

         this.field_220832_b = 0;
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         ItemStack itemstack = PandaEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
         if (!itemstack.isEmpty()) {
            PandaEntity.this.entityDropItem(itemstack);
            PandaEntity.this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
            int i = PandaEntity.this.func_213584_ej() ? PandaEntity.this.rand.nextInt(50) + 10 : PandaEntity.this.rand.nextInt(150) + 10;
            this.field_220832_b = PandaEntity.this.ticksExisted + i * 20;
         }

         PandaEntity.this.func_213553_r(false);
      }
   }

   public static enum Type {
      NORMAL(0, "normal", false),
      LAZY(1, "lazy", false),
      WORRIED(2, "worried", false),
      PLAYFUL(3, "playful", false),
      BROWN(4, "brown", true),
      WEAK(5, "weak", true),
      AGGRESSIVE(6, "aggressive", false);

      private static final PandaEntity.Type[] field_221109_h = Arrays.stream(values()).sorted(Comparator.comparingInt(PandaEntity.Type::func_221106_a)).toArray((p_221102_0_) -> {
         return new PandaEntity.Type[p_221102_0_];
      });
      private final int field_221110_i;
      private final String field_221111_j;
      private final boolean field_221112_k;

      private Type(int p_i51468_3_, String p_i51468_4_, boolean p_i51468_5_) {
         this.field_221110_i = p_i51468_3_;
         this.field_221111_j = p_i51468_4_;
         this.field_221112_k = p_i51468_5_;
      }

      public int func_221106_a() {
         return this.field_221110_i;
      }

      public String func_221100_b() {
         return this.field_221111_j;
      }

      public boolean func_221107_c() {
         return this.field_221112_k;
      }

      private static PandaEntity.Type func_221101_b(PandaEntity.Type p_221101_0_, PandaEntity.Type p_221101_1_) {
         if (p_221101_0_.func_221107_c()) {
            return p_221101_0_ == p_221101_1_ ? p_221101_0_ : NORMAL;
         } else {
            return p_221101_0_;
         }
      }

      public static PandaEntity.Type func_221105_a(int p_221105_0_) {
         if (p_221105_0_ < 0 || p_221105_0_ >= field_221109_h.length) {
            p_221105_0_ = 0;
         }

         return field_221109_h[p_221105_0_];
      }

      public static PandaEntity.Type func_221108_a(String p_221108_0_) {
         for(PandaEntity.Type pandaentity$type : values()) {
            if (pandaentity$type.field_221111_j.equals(p_221108_0_)) {
               return pandaentity$type;
            }
         }

         return NORMAL;
      }

      public static PandaEntity.Type func_221104_a(Random p_221104_0_) {
         int i = p_221104_0_.nextInt(16);
         if (i == 0) {
            return LAZY;
         } else if (i == 1) {
            return WORRIED;
         } else if (i == 2) {
            return PLAYFUL;
         } else if (i == 4) {
            return AGGRESSIVE;
         } else if (i < 9) {
            return WEAK;
         } else {
            return i < 11 ? BROWN : NORMAL;
         }
      }
   }

   static class WatchGoal extends LookAtGoal {
      private final PandaEntity field_220718_f;

      public WatchGoal(PandaEntity p_i51458_1_, Class<? extends LivingEntity> p_i51458_2_, float p_i51458_3_) {
         super(p_i51458_1_, p_i51458_2_, p_i51458_3_);
         this.field_220718_f = p_i51458_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return this.field_220718_f.func_213537_eq() && super.shouldExecute();
      }
   }
}