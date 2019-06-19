package net.minecraft.entity.passive;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FleeSunGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveThroughVillageAtNightGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FoxEntity extends AnimalEntity {
   private static final DataParameter<Integer> field_213523_bz = EntityDataManager.createKey(FoxEntity.class, DataSerializers.field_187192_b);
   private static final DataParameter<Byte> field_213524_bA = EntityDataManager.createKey(FoxEntity.class, DataSerializers.field_187191_a);
   private static final DataParameter<Optional<UUID>> field_213509_bB = EntityDataManager.createKey(FoxEntity.class, DataSerializers.field_187203_m);
   private static final DataParameter<Optional<UUID>> field_213510_bD = EntityDataManager.createKey(FoxEntity.class, DataSerializers.field_187203_m);
   private static final Predicate<ItemEntity> field_213511_bE = (p_213489_0_) -> {
      return !p_213489_0_.cannotPickup() && p_213489_0_.isAlive();
   };
   private static final Predicate<Entity> field_213512_bF = (p_213470_0_) -> {
      if (!(p_213470_0_ instanceof LivingEntity)) {
         return false;
      } else {
         LivingEntity livingentity = (LivingEntity)p_213470_0_;
         return livingentity.getLastAttackedEntity() != null && livingentity.getLastAttackedEntityTime() < livingentity.ticksExisted + 600;
      }
   };
   private static final Predicate<Entity> field_213513_bG = (p_213498_0_) -> {
      return p_213498_0_ instanceof ChickenEntity || p_213498_0_ instanceof RabbitEntity;
   };
   private static final Predicate<Entity> field_213514_bH = (p_213463_0_) -> {
      return !p_213463_0_.isSneaking() && EntityPredicates.CAN_AI_TARGET.test(p_213463_0_);
   };
   private Goal attackAnimals;
   private Goal attackTurtles;
   private Goal attackFish;
   private float field_213518_bL;
   private float field_213519_bM;
   private float field_213520_bN;
   private float field_213521_bO;
   private int field_213522_bP;

   public FoxEntity(EntityType<? extends FoxEntity> p_i50271_1_, World p_i50271_2_) {
      super(p_i50271_1_, p_i50271_2_);
      this.field_70749_g = new FoxEntity.LookHelperController();
      this.field_70765_h = new FoxEntity.MoveHelperController();
      this.setPathPriority(PathNodeType.DANGER_OTHER, 0.0F);
      this.setPathPriority(PathNodeType.DAMAGE_OTHER, 0.0F);
      this.setCanPickUpLoot(true);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_213509_bB, Optional.empty());
      this.dataManager.register(field_213510_bD, Optional.empty());
      this.dataManager.register(field_213523_bz, 0);
      this.dataManager.register(field_213524_bA, (byte)0);
   }

   protected void initEntityAI() {
      this.attackAnimals = new NearestAttackableTargetGoal<>(this, AnimalEntity.class, 10, false, false, (p_213487_0_) -> {
         return p_213487_0_ instanceof ChickenEntity || p_213487_0_ instanceof RabbitEntity;
      });
      this.attackTurtles = new NearestAttackableTargetGoal<>(this, TurtleEntity.class, 10, false, false, TurtleEntity.TARGET_DRY_BABY);
      this.attackFish = new NearestAttackableTargetGoal<>(this, AbstractFishEntity.class, 20, false, false, (p_213456_0_) -> {
         return p_213456_0_ instanceof AbstractGroupFishEntity;
      });
      this.field_70714_bg.addTask(0, new FoxEntity.SwimGoal());
      this.field_70714_bg.addTask(1, new FoxEntity.JumpGoal());
      this.field_70714_bg.addTask(2, new FoxEntity.PanicGoal(2.2D));
      this.field_70714_bg.addTask(3, new AvoidEntityGoal<>(this, PlayerEntity.class, 16.0F, 1.6D, 1.4D, (p_213497_1_) -> {
         return field_213514_bH.test(p_213497_1_) && !this.func_213468_c(p_213497_1_.getUniqueID());
      }));
      this.field_70714_bg.addTask(3, new AvoidEntityGoal<>(this, WolfEntity.class, 8.0F, 1.6D, 1.4D, (p_213469_0_) -> {
         return !((WolfEntity)p_213469_0_).isTamed();
      }));
      this.field_70714_bg.addTask(4, new FoxEntity.FollowTargetGoal());
      this.field_70714_bg.addTask(5, new FoxEntity.PounceGoal());
      this.field_70714_bg.addTask(5, new FoxEntity.MateGoal(1.0D));
      this.field_70714_bg.addTask(5, new FoxEntity.FindShelterGoal(1.25D));
      this.field_70714_bg.addTask(6, new FoxEntity.BiteGoal((double)1.2F, true));
      this.field_70714_bg.addTask(6, new FoxEntity.SleepGoal());
      this.field_70714_bg.addTask(7, new FoxEntity.FollowGoal(this, 1.25D));
      this.field_70714_bg.addTask(8, new FoxEntity.StrollGoal(32, 200));
      this.field_70714_bg.addTask(9, new FoxEntity.EatBerriesGoal((double)1.2F, 12, 2));
      this.field_70714_bg.addTask(9, new LeapAtTargetGoal(this, 0.4F));
      this.field_70714_bg.addTask(10, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
      this.field_70714_bg.addTask(10, new FoxEntity.FindItemsGoal());
      this.field_70714_bg.addTask(11, new FoxEntity.WatchGoal(this, PlayerEntity.class, 24.0F));
      this.field_70714_bg.addTask(12, new FoxEntity.SitAndLookGoal());
      this.field_70715_bh.addTask(3, new FoxEntity.RevengeGoal(LivingEntity.class, false, false, (p_213493_1_) -> {
         return field_213512_bF.test(p_213493_1_) && !this.func_213468_c(p_213493_1_.getUniqueID());
      }));
   }

   public SoundEvent func_213353_d(ItemStack p_213353_1_) {
      return SoundEvents.field_219634_dx;
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      if (!this.world.isRemote && this.isAlive() && this.isServerWorld()) {
         ++this.field_213522_bP;
         ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
         if (this.func_213464_j(itemstack)) {
            if (this.field_213522_bP > 600) {
               ItemStack itemstack1 = itemstack.onItemUseFinish(this.world, this);
               if (!itemstack1.isEmpty()) {
                  this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack1);
               }

               this.field_213522_bP = 0;
            } else if (this.field_213522_bP > 560 && this.rand.nextFloat() < 0.1F) {
               this.playSound(this.func_213353_d(itemstack), 1.0F, 1.0F);
               this.world.setEntityState(this, (byte)45);
            }
         }

         LivingEntity livingentity = this.getAttackTarget();
         if (livingentity == null || !livingentity.isAlive()) {
            this.func_213451_t(false);
            this.func_213502_u(false);
         }
      }

      if (this.isPlayerSleeping() || this.isMovementBlocked()) {
         this.isJumping = false;
         this.moveStrafing = 0.0F;
         this.moveForward = 0.0F;
         this.randomYawVelocity = 0.0F;
      }

      super.livingTick();
      if (this.func_213473_el() && this.rand.nextFloat() < 0.05F) {
         this.playSound(SoundEvents.field_219630_dt, 1.0F, 1.0F);
      }

   }

   /**
    * Dead and sleeping entities cannot move
    */
   protected boolean isMovementBlocked() {
      return this.getHealth() <= 0.0F;
   }

   private boolean func_213464_j(ItemStack p_213464_1_) {
      return p_213464_1_.getItem().func_219971_r() && this.getAttackTarget() == null && this.onGround && !this.isPlayerSleeping();
   }

   /**
    * Gives armor or weapon for entity based on given DifficultyInstance
    */
   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
      if (this.rand.nextFloat() < 0.2F) {
         float f = this.rand.nextFloat();
         ItemStack itemstack;
         if (f < 0.05F) {
            itemstack = new ItemStack(Items.EMERALD);
         } else if (f < 0.2F) {
            itemstack = new ItemStack(Items.EGG);
         } else if (f < 0.4F) {
            itemstack = this.rand.nextBoolean() ? new ItemStack(Items.RABBIT_FOOT) : new ItemStack(Items.RABBIT_HIDE);
         } else if (f < 0.6F) {
            itemstack = new ItemStack(Items.WHEAT);
         } else if (f < 0.8F) {
            itemstack = new ItemStack(Items.LEATHER);
         } else {
            itemstack = new ItemStack(Items.FEATHER);
         }

         this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
      }

   }

   /**
    * Handler for {@link World#setEntityState}
    */
   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 45) {
         ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
         if (!itemstack.isEmpty()) {
            for(int i = 0; i < 8; ++i) {
               Vec3d vec3d = (new Vec3d(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).rotatePitch(-this.rotationPitch * ((float)Math.PI / 180F)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F));
               this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, itemstack), this.posX + this.getLookVec().x / 2.0D, this.posY, this.posZ + this.getLookVec().z / 2.0D, vec3d.x, vec3d.y + 0.05D, vec3d.z);
            }
         }
      } else {
         super.handleStatusUpdate(id);
      }

   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
   }

   public FoxEntity createChild(AgeableEntity ageable) {
      FoxEntity foxentity = EntityType.field_220356_B.create(this.world);
      foxentity.func_213474_a(this.rand.nextBoolean() ? this.func_213471_dV() : ((FoxEntity)ageable).func_213471_dV());
      return foxentity;
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      Biome biome = p_213386_1_.getBiome(new BlockPos(this));
      FoxEntity.Type foxentity$type = FoxEntity.Type.func_221086_a(biome);
      boolean flag = false;
      if (p_213386_4_ instanceof FoxEntity.FoxData) {
         foxentity$type = ((FoxEntity.FoxData)p_213386_4_).field_220366_a;
         if (((FoxEntity.FoxData)p_213386_4_).field_220367_b >= 2) {
            flag = true;
         } else {
            ++((FoxEntity.FoxData)p_213386_4_).field_220367_b;
         }
      } else {
         p_213386_4_ = new FoxEntity.FoxData(foxentity$type);
         ++((FoxEntity.FoxData)p_213386_4_).field_220367_b;
      }

      this.func_213474_a(foxentity$type);
      if (flag) {
         this.setGrowingAge(-24000);
      }

      this.func_213501_ej();
      this.setEquipmentBasedOnDifficulty(p_213386_2_);
      return super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
   }

   private void func_213501_ej() {
      if (this.func_213471_dV() == FoxEntity.Type.RED) {
         this.field_70715_bh.addTask(4, this.attackAnimals);
         this.field_70715_bh.addTask(4, this.attackTurtles);
         this.field_70715_bh.addTask(6, this.attackFish);
      } else {
         this.field_70715_bh.addTask(4, this.attackFish);
         this.field_70715_bh.addTask(6, this.attackAnimals);
         this.field_70715_bh.addTask(6, this.attackTurtles);
      }

   }

   /**
    * Decreases ItemStack size by one
    */
   protected void consumeItemFromStack(PlayerEntity player, ItemStack stack) {
      if (this.isBreedingItem(stack)) {
         this.playSound(this.func_213353_d(stack), 1.0F, 1.0F);
      }

      super.consumeItemFromStack(player, stack);
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return this.isChild() ? p_213348_2_.height * 0.95F : 0.4F;
   }

   public FoxEntity.Type func_213471_dV() {
      return FoxEntity.Type.func_221080_a(this.dataManager.get(field_213523_bz));
   }

   private void func_213474_a(FoxEntity.Type p_213474_1_) {
      this.dataManager.set(field_213523_bz, p_213474_1_.func_221083_c());
   }

   private List<UUID> func_213479_ek() {
      List<UUID> list = Lists.newArrayList();
      list.add(this.dataManager.get(field_213509_bB).orElse((UUID)null));
      list.add(this.dataManager.get(field_213510_bD).orElse((UUID)null));
      return list;
   }

   private void func_213465_b(@Nullable UUID p_213465_1_) {
      if (this.dataManager.get(field_213509_bB).isPresent()) {
         this.dataManager.set(field_213510_bD, Optional.ofNullable(p_213465_1_));
      } else {
         this.dataManager.set(field_213509_bB, Optional.ofNullable(p_213465_1_));
      }

   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      List<UUID> list = this.func_213479_ek();
      ListNBT listnbt = new ListNBT();

      for(UUID uuid : list) {
         if (uuid != null) {
            listnbt.add(NBTUtil.writeUniqueId(uuid));
         }
      }

      p_213281_1_.put("TrustedUUIDs", listnbt);
      p_213281_1_.putBoolean("Sleeping", this.isPlayerSleeping());
      p_213281_1_.putString("Type", this.func_213471_dV().func_221082_a());
      p_213281_1_.putBoolean("Sitting", this.func_213455_dW());
      p_213281_1_.putBoolean("Crouching", this.func_213453_ef());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      ListNBT listnbt = compound.getList("TrustedUUIDs", 10);

      for(int i = 0; i < listnbt.size(); ++i) {
         this.func_213465_b(NBTUtil.readUniqueId(listnbt.getCompound(i)));
      }

      this.func_213485_x(compound.getBoolean("Sleeping"));
      this.func_213474_a(FoxEntity.Type.func_221087_a(compound.getString("Type")));
      this.func_213466_r(compound.getBoolean("Sitting"));
      this.func_213451_t(compound.getBoolean("Crouching"));
      this.func_213501_ej();
   }

   public boolean func_213455_dW() {
      return this.func_213507_r(1);
   }

   public void func_213466_r(boolean p_213466_1_) {
      this.func_213505_d(1, p_213466_1_);
   }

   public boolean func_213472_dX() {
      return this.func_213507_r(64);
   }

   private void func_213492_v(boolean p_213492_1_) {
      this.func_213505_d(64, p_213492_1_);
   }

   private boolean func_213473_el() {
      return this.func_213507_r(128);
   }

   private void func_213482_w(boolean p_213482_1_) {
      this.func_213505_d(128, p_213482_1_);
   }

   /**
    * Returns whether player is sleeping or not
    */
   public boolean isPlayerSleeping() {
      return this.func_213507_r(32);
   }

   private void func_213485_x(boolean p_213485_1_) {
      this.func_213505_d(32, p_213485_1_);
   }

   private void func_213505_d(int p_213505_1_, boolean p_213505_2_) {
      if (p_213505_2_) {
         this.dataManager.set(field_213524_bA, (byte)(this.dataManager.get(field_213524_bA) | p_213505_1_));
      } else {
         this.dataManager.set(field_213524_bA, (byte)(this.dataManager.get(field_213524_bA) & ~p_213505_1_));
      }

   }

   private boolean func_213507_r(int p_213507_1_) {
      return (this.dataManager.get(field_213524_bA) & p_213507_1_) != 0;
   }

   public boolean func_213365_e(ItemStack p_213365_1_) {
      EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(p_213365_1_);
      if (!this.getItemStackFromSlot(equipmentslottype).isEmpty()) {
         return false;
      } else {
         return equipmentslottype == EquipmentSlotType.MAINHAND && super.func_213365_e(p_213365_1_);
      }
   }

   protected boolean canEquipItem(ItemStack stack) {
      Item item = stack.getItem();
      ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
      return itemstack.isEmpty() || this.field_213522_bP > 0 && item.func_219971_r() && !itemstack.getItem().func_219971_r();
   }

   private void func_213495_k(ItemStack p_213495_1_) {
      if (!p_213495_1_.isEmpty() && !this.world.isRemote) {
         ItemEntity itementity = new ItemEntity(this.world, this.posX + this.getLookVec().x, this.posY + 1.0D, this.posZ + this.getLookVec().z, p_213495_1_);
         itementity.setPickupDelay(40);
         itementity.setThrowerId(this.getUniqueID());
         this.playSound(SoundEvents.field_219629_dC, 1.0F, 1.0F);
         this.world.func_217376_c(itementity);
      }
   }

   private void func_213486_l(ItemStack p_213486_1_) {
      ItemEntity itementity = new ItemEntity(this.world, this.posX, this.posY, this.posZ, p_213486_1_);
      this.world.func_217376_c(itementity);
   }

   /**
    * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
    * better.
    */
   protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
      ItemStack itemstack = itemEntity.getItem();
      if (this.canEquipItem(itemstack)) {
         int i = itemstack.getCount();
         if (i > 1) {
            this.func_213486_l(itemstack.split(i - 1));
         }

         this.func_213495_k(this.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
         this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack.split(1));
         this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0F;
         this.onItemPickup(itemEntity, itemstack.getCount());
         itemEntity.remove();
         this.field_213522_bP = 0;
      }

   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
      if (this.isServerWorld()) {
         boolean flag = this.isInWater();
         if (flag || this.getAttackTarget() != null || this.world.isThundering()) {
            this.func_213454_em();
         }

         if (flag || this.isPlayerSleeping()) {
            this.func_213466_r(false);
         }

         if (this.func_213472_dX() && this.world.rand.nextFloat() < 0.2F) {
            BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
            BlockState blockstate = this.world.getBlockState(blockpos);
            this.world.playEvent(2001, blockpos, Block.getStateId(blockstate));
         }
      }

      this.field_213519_bM = this.field_213518_bL;
      if (this.func_213467_eg()) {
         this.field_213518_bL += (1.0F - this.field_213518_bL) * 0.4F;
      } else {
         this.field_213518_bL += (0.0F - this.field_213518_bL) * 0.4F;
      }

      this.field_213521_bO = this.field_213520_bN;
      if (this.func_213453_ef()) {
         this.field_213520_bN += 0.2F;
         if (this.field_213520_bN > 3.0F) {
            this.field_213520_bN = 3.0F;
         }
      } else {
         this.field_213520_bN = 0.0F;
      }

   }

   /**
    * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
    * the animal type)
    */
   public boolean isBreedingItem(ItemStack stack) {
      return stack.getItem() == Items.field_222112_pR;
   }

   protected void func_213406_a(PlayerEntity p_213406_1_, AgeableEntity p_213406_2_) {
      ((FoxEntity)p_213406_2_).func_213465_b(p_213406_1_.getUniqueID());
   }

   public boolean func_213480_dY() {
      return this.func_213507_r(16);
   }

   public void func_213461_s(boolean p_213461_1_) {
      this.func_213505_d(16, p_213461_1_);
   }

   public boolean func_213490_ee() {
      return this.field_213520_bN == 3.0F;
   }

   public void func_213451_t(boolean p_213451_1_) {
      this.func_213505_d(4, p_213451_1_);
   }

   public boolean func_213453_ef() {
      return this.func_213507_r(4);
   }

   public void func_213502_u(boolean p_213502_1_) {
      this.func_213505_d(8, p_213502_1_);
   }

   public boolean func_213467_eg() {
      return this.func_213507_r(8);
   }

   @OnlyIn(Dist.CLIENT)
   public float func_213475_v(float p_213475_1_) {
      return MathHelper.func_219799_g(p_213475_1_, this.field_213519_bM, this.field_213518_bL) * 0.11F * (float)Math.PI;
   }

   @OnlyIn(Dist.CLIENT)
   public float func_213503_w(float p_213503_1_) {
      return MathHelper.func_219799_g(p_213503_1_, this.field_213521_bO, this.field_213520_bN);
   }

   /**
    * Sets the active target the Task system uses for tracking
    */
   public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
      if (this.func_213473_el() && entitylivingbaseIn == null) {
         this.func_213482_w(false);
      }

      super.setAttackTarget(entitylivingbaseIn);
   }

   public void fall(float distance, float damageMultiplier) {
      int i = MathHelper.ceil((distance - 5.0F) * damageMultiplier);
      if (i > 0) {
         this.attackEntityFrom(DamageSource.FALL, (float)i);
         if (this.isBeingRidden()) {
            for(Entity entity : this.getRecursivePassengers()) {
               entity.attackEntityFrom(DamageSource.FALL, (float)i);
            }
         }

         BlockState blockstate = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double)this.prevRotationYaw, this.posZ));
         if (!blockstate.isAir() && !this.isSilent()) {
            SoundType soundtype = blockstate.getSoundType();
            this.world.playSound((PlayerEntity)null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
         }

      }
   }

   private void func_213454_em() {
      this.func_213485_x(false);
   }

   private void func_213499_en() {
      this.func_213502_u(false);
      this.func_213451_t(false);
      this.func_213466_r(false);
      this.func_213485_x(false);
      this.func_213482_w(false);
      this.func_213492_v(false);
   }

   private boolean func_213478_eo() {
      return !this.isPlayerSleeping() && !this.func_213455_dW() && !this.func_213472_dX();
   }

   /**
    * Plays living's sound at its position
    */
   public void playAmbientSound() {
      SoundEvent soundevent = this.getAmbientSound();
      if (soundevent == SoundEvents.field_219636_dz) {
         this.playSound(soundevent, 2.0F, this.getSoundPitch());
      } else {
         super.playAmbientSound();
      }

   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      if (this.isPlayerSleeping()) {
         return SoundEvents.field_219627_dA;
      } else {
         if (!this.world.isDaytime() && this.rand.nextFloat() < 0.1F) {
            List<PlayerEntity> list = this.world.getEntitiesWithinAABB(PlayerEntity.class, this.getBoundingBox().grow(16.0D, 16.0D, 16.0D), EntityPredicates.NOT_SPECTATING);
            if (list.isEmpty()) {
               return SoundEvents.field_219636_dz;
            }
         }

         return SoundEvents.field_219631_du;
      }
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.field_219635_dy;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.field_219633_dw;
   }

   private boolean func_213468_c(UUID p_213468_1_) {
      return this.func_213479_ek().contains(p_213468_1_);
   }

   protected void func_213345_d(DamageSource p_213345_1_) {
      ItemStack itemstack = this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
      if (!itemstack.isEmpty()) {
         this.entityDropItem(itemstack);
         this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
      }

      super.func_213345_d(p_213345_1_);
   }

   public static boolean func_213481_a(FoxEntity p_213481_0_, LivingEntity p_213481_1_) {
      double d0 = p_213481_1_.posZ - p_213481_0_.posZ;
      double d1 = p_213481_1_.posX - p_213481_0_.posX;
      double d2 = d0 / d1;
      int i = 6;

      for(int j = 0; j < 6; ++j) {
         double d3 = d2 == 0.0D ? 0.0D : d0 * (double)((float)j / 6.0F);
         double d4 = d2 == 0.0D ? d1 * (double)((float)j / 6.0F) : d3 / d2;

         for(int k = 1; k < 4; ++k) {
            if (!p_213481_0_.world.getBlockState(new BlockPos(p_213481_0_.posX + d4, p_213481_0_.posY + (double)k, p_213481_0_.posZ + d3)).getMaterial().isReplaceable()) {
               return false;
            }
         }
      }

      return true;
   }

   public class AlertablePredicate implements Predicate<LivingEntity> {
      public boolean test(LivingEntity p_test_1_) {
         if (p_test_1_ instanceof FoxEntity) {
            return false;
         } else if (!(p_test_1_ instanceof ChickenEntity) && !(p_test_1_ instanceof RabbitEntity) && !(p_test_1_ instanceof MonsterEntity)) {
            if (p_test_1_ instanceof TameableEntity) {
               return !((TameableEntity)p_test_1_).isTamed();
            } else if (!(p_test_1_ instanceof PlayerEntity) || !p_test_1_.isSpectator() && !((PlayerEntity)p_test_1_).isCreative()) {
               if (FoxEntity.this.func_213468_c(p_test_1_.getUniqueID())) {
                  return false;
               } else {
                  return !p_test_1_.isPlayerSleeping() && !p_test_1_.isSneaking();
               }
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   abstract class BaseGoal extends Goal {
      private final EntityPredicate field_220816_b = (new EntityPredicate()).func_221013_a(12.0D).func_221014_c().func_221012_a(FoxEntity.this.new AlertablePredicate());

      private BaseGoal() {
      }

      protected boolean func_220813_g() {
         BlockPos blockpos = new BlockPos(FoxEntity.this);
         return !FoxEntity.this.world.func_217337_f(blockpos) && FoxEntity.this.getBlockPathWeight(blockpos) >= 0.0F;
      }

      protected boolean func_220814_h() {
         return !FoxEntity.this.world.func_217374_a(LivingEntity.class, this.field_220816_b, FoxEntity.this, FoxEntity.this.getBoundingBox().grow(12.0D, 6.0D, 12.0D)).isEmpty();
      }
   }

   class BiteGoal extends MeleeAttackGoal {
      public BiteGoal(double p_i50731_2_, boolean p_i50731_4_) {
         super(FoxEntity.this, p_i50731_2_, p_i50731_4_);
      }

      protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
         double d0 = this.getAttackReachSqr(enemy);
         if (distToEnemySqr <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            this.field_75441_b.attackEntityAsMob(enemy);
            FoxEntity.this.playSound(SoundEvents.field_219632_dv, 1.0F, 1.0F);
         }

      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         FoxEntity.this.func_213502_u(false);
         super.startExecuting();
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return !FoxEntity.this.func_213455_dW() && !FoxEntity.this.isPlayerSleeping() && !FoxEntity.this.func_213453_ef() && !FoxEntity.this.func_213472_dX() && super.shouldExecute();
      }
   }

   public class EatBerriesGoal extends MoveToBlockGoal {
      protected int field_220731_g;

      public EatBerriesGoal(double p_i50737_2_, int p_i50737_4_, int p_i50737_5_) {
         super(FoxEntity.this, p_i50737_2_, p_i50737_4_, p_i50737_5_);
      }

      public double getTargetDistanceSq() {
         return 2.0D;
      }

      public boolean shouldMove() {
         return this.timeoutCounter % 100 == 0;
      }

      /**
       * Return true to set given position as destination
       */
      protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
         BlockState blockstate = worldIn.getBlockState(pos);
         return blockstate.getBlock() == Blocks.field_222434_lW && blockstate.get(SweetBerryBushBlock.field_220125_a) >= 2;
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         if (this.getIsAboveDestination()) {
            if (this.field_220731_g >= 40) {
               this.func_220730_m();
            } else {
               ++this.field_220731_g;
            }
         } else if (!this.getIsAboveDestination() && FoxEntity.this.rand.nextFloat() < 0.05F) {
            FoxEntity.this.playSound(SoundEvents.field_219628_dB, 1.0F, 1.0F);
         }

         super.tick();
      }

      protected void func_220730_m() {
         if (FoxEntity.this.world.getGameRules().getBoolean("mobGriefing")) {
            BlockState blockstate = FoxEntity.this.world.getBlockState(this.destinationBlock);
            if (blockstate.getBlock() == Blocks.field_222434_lW) {
               int i = blockstate.get(SweetBerryBushBlock.field_220125_a);
               blockstate.with(SweetBerryBushBlock.field_220125_a, Integer.valueOf(1));
               int j = 1 + FoxEntity.this.world.rand.nextInt(2) + (i == 3 ? 1 : 0);
               ItemStack itemstack = FoxEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
               if (itemstack.isEmpty()) {
                  FoxEntity.this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.field_222112_pR));
                  --j;
               }

               if (j > 0) {
                  Block.spawnAsEntity(FoxEntity.this.world, this.destinationBlock, new ItemStack(Items.field_222112_pR, j));
               }

               FoxEntity.this.playSound(SoundEvents.field_219693_lB, 1.0F, 1.0F);
               FoxEntity.this.world.setBlockState(this.destinationBlock, blockstate.with(SweetBerryBushBlock.field_220125_a, Integer.valueOf(1)), 2);
            }
         }
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return !FoxEntity.this.isPlayerSleeping() && super.shouldExecute();
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.field_220731_g = 0;
         FoxEntity.this.func_213466_r(false);
         super.startExecuting();
      }
   }

   class FindItemsGoal extends Goal {
      public FindItemsGoal() {
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (!FoxEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
            return false;
         } else if (FoxEntity.this.getAttackTarget() == null && FoxEntity.this.getRevengeTarget() == null) {
            if (!FoxEntity.this.func_213478_eo()) {
               return false;
            } else if (FoxEntity.this.getRNG().nextInt(10) != 0) {
               return false;
            } else {
               List<ItemEntity> list = FoxEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, FoxEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), FoxEntity.field_213511_bE);
               return !list.isEmpty() && FoxEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
            }
         } else {
            return false;
         }
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         List<ItemEntity> list = FoxEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, FoxEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), FoxEntity.field_213511_bE);
         ItemStack itemstack = FoxEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
         if (itemstack.isEmpty() && !list.isEmpty()) {
            FoxEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
         }

      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         List<ItemEntity> list = FoxEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, FoxEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), FoxEntity.field_213511_bE);
         if (!list.isEmpty()) {
            FoxEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
         }

      }
   }

   class FindShelterGoal extends FleeSunGoal {
      private int field_220704_c = 100;

      public FindShelterGoal(double p_i50724_2_) {
         super(FoxEntity.this, p_i50724_2_);
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (!FoxEntity.this.isPlayerSleeping() && this.field_75372_a.getAttackTarget() == null) {
            if (FoxEntity.this.world.isThundering()) {
               return true;
            } else if (this.field_220704_c > 0) {
               --this.field_220704_c;
               return false;
            } else {
               this.field_220704_c = 100;
               BlockPos blockpos = new BlockPos(this.field_75372_a);
               return FoxEntity.this.world.isDaytime() && FoxEntity.this.world.func_217337_f(blockpos) && !((ServerWorld)FoxEntity.this.world).func_217483_b_(blockpos) && this.func_220702_g();
            }
         } else {
            return false;
         }
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         FoxEntity.this.func_213499_en();
         super.startExecuting();
      }
   }

   class FollowGoal extends FollowParentGoal {
      private final FoxEntity field_220706_b;

      public FollowGoal(FoxEntity p_i50735_2_, double p_i50735_3_) {
         super(p_i50735_2_, p_i50735_3_);
         this.field_220706_b = p_i50735_2_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return !this.field_220706_b.func_213473_el() && super.shouldExecute();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return !this.field_220706_b.func_213473_el() && super.shouldContinueExecuting();
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.field_220706_b.func_213499_en();
         super.startExecuting();
      }
   }

   class FollowTargetGoal extends Goal {
      public FollowTargetGoal() {
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (FoxEntity.this.isPlayerSleeping()) {
            return false;
         } else {
            LivingEntity livingentity = FoxEntity.this.getAttackTarget();
            return livingentity != null && livingentity.isAlive() && FoxEntity.field_213513_bG.test(livingentity) && FoxEntity.this.getDistanceSq(livingentity) > 36.0D && !FoxEntity.this.func_213453_ef() && !FoxEntity.this.func_213467_eg() && !FoxEntity.this.isJumping;
         }
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         FoxEntity.this.func_213466_r(false);
         FoxEntity.this.func_213492_v(false);
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         LivingEntity livingentity = FoxEntity.this.getAttackTarget();
         if (livingentity != null && FoxEntity.func_213481_a(FoxEntity.this, livingentity)) {
            FoxEntity.this.func_213502_u(true);
            FoxEntity.this.func_213451_t(true);
            FoxEntity.this.getNavigator().clearPath();
            FoxEntity.this.getLookHelper().setLookPositionWithEntity(livingentity, (float)FoxEntity.this.getHorizontalFaceSpeed(), (float)FoxEntity.this.getVerticalFaceSpeed());
         } else {
            FoxEntity.this.func_213502_u(false);
            FoxEntity.this.func_213451_t(false);
         }

      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         LivingEntity livingentity = FoxEntity.this.getAttackTarget();
         FoxEntity.this.getLookHelper().setLookPositionWithEntity(livingentity, (float)FoxEntity.this.getHorizontalFaceSpeed(), (float)FoxEntity.this.getVerticalFaceSpeed());
         if (FoxEntity.this.getDistanceSq(livingentity) <= 36.0D) {
            FoxEntity.this.func_213502_u(true);
            FoxEntity.this.func_213451_t(true);
            FoxEntity.this.getNavigator().clearPath();
         } else {
            FoxEntity.this.getNavigator().tryMoveToEntityLiving(livingentity, 1.5D);
         }

      }
   }

   public static class FoxData implements ILivingEntityData {
      public final FoxEntity.Type field_220366_a;
      public int field_220367_b;

      public FoxData(FoxEntity.Type p_i50734_1_) {
         this.field_220366_a = p_i50734_1_;
      }
   }

   class JumpGoal extends Goal {
      int field_220811_a;

      public JumpGoal() {
         this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.MOVE));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return FoxEntity.this.func_213472_dX();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return this.shouldExecute() && this.field_220811_a > 0;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.field_220811_a = 40;
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         FoxEntity.this.func_213492_v(false);
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         --this.field_220811_a;
      }
   }

   public class LookHelperController extends LookController {
      public LookHelperController() {
         super(FoxEntity.this);
      }

      /**
       * Updates look
       */
      public void tick() {
         if (!FoxEntity.this.isPlayerSleeping()) {
            super.tick();
         }

      }

      protected boolean func_220680_b() {
         return !FoxEntity.this.func_213480_dY() && !FoxEntity.this.func_213453_ef() && !FoxEntity.this.func_213467_eg() & !FoxEntity.this.func_213472_dX();
      }
   }

   class MateGoal extends BreedGoal {
      public MateGoal(double p_i50738_2_) {
         super(FoxEntity.this, p_i50738_2_);
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         ((FoxEntity)this.field_75390_d).func_213499_en();
         ((FoxEntity)this.field_75391_e).func_213499_en();
         super.startExecuting();
      }

      /**
       * Spawns a baby animal of the same type.
       */
      protected void spawnBaby() {
         FoxEntity foxentity = (FoxEntity)this.field_75390_d.createChild(this.field_75391_e);
         if (foxentity != null) {
            ServerPlayerEntity serverplayerentity = this.field_75390_d.getLoveCause();
            ServerPlayerEntity serverplayerentity1 = this.field_75391_e.getLoveCause();
            ServerPlayerEntity serverplayerentity2 = serverplayerentity;
            if (serverplayerentity != null) {
               foxentity.func_213465_b(serverplayerentity.getUniqueID());
            } else {
               serverplayerentity2 = serverplayerentity1;
            }

            if (serverplayerentity1 != null && serverplayerentity != serverplayerentity1) {
               foxentity.func_213465_b(serverplayerentity1.getUniqueID());
            }

            if (serverplayerentity2 != null) {
               serverplayerentity2.addStat(Stats.ANIMALS_BRED);
               CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity2, this.field_75390_d, this.field_75391_e, foxentity);
            }

            int i = 6000;
            this.field_75390_d.setGrowingAge(6000);
            this.field_75391_e.setGrowingAge(6000);
            this.field_75390_d.resetInLove();
            this.field_75391_e.resetInLove();
            foxentity.setGrowingAge(-24000);
            foxentity.setLocationAndAngles(this.field_75390_d.posX, this.field_75390_d.posY, this.field_75390_d.posZ, 0.0F, 0.0F);
            this.world.func_217376_c(foxentity);
            this.world.setEntityState(this.field_75390_d, (byte)18);
            if (this.world.getGameRules().getBoolean("doMobLoot")) {
               this.world.func_217376_c(new ExperienceOrbEntity(this.world, this.field_75390_d.posX, this.field_75390_d.posY, this.field_75390_d.posZ, this.field_75390_d.getRNG().nextInt(7) + 1));
            }

         }
      }
   }

   class MoveHelperController extends MovementController {
      public MoveHelperController() {
         super(FoxEntity.this);
      }

      public void tick() {
         if (FoxEntity.this.func_213478_eo()) {
            super.tick();
         }

      }
   }

   class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
      public PanicGoal(double p_i50729_2_) {
         super(FoxEntity.this, p_i50729_2_);
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return !FoxEntity.this.func_213473_el() && super.shouldExecute();
      }
   }

   public class PounceGoal extends net.minecraft.entity.ai.goal.JumpGoal {
      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (!FoxEntity.this.func_213490_ee()) {
            return false;
         } else {
            LivingEntity livingentity = FoxEntity.this.getAttackTarget();
            if (livingentity != null && livingentity.isAlive()) {
               if (livingentity.getAdjustedHorizontalFacing() != livingentity.getHorizontalFacing()) {
                  return false;
               } else {
                  boolean flag = FoxEntity.func_213481_a(FoxEntity.this, livingentity);
                  if (!flag) {
                     FoxEntity.this.getNavigator().getPathToEntityLiving(livingentity);
                     FoxEntity.this.func_213451_t(false);
                     FoxEntity.this.func_213502_u(false);
                  }

                  return flag;
               }
            } else {
               return false;
            }
         }
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         LivingEntity livingentity = FoxEntity.this.getAttackTarget();
         if (livingentity != null && livingentity.isAlive()) {
            double d0 = FoxEntity.this.getMotion().y;
            return (!(d0 * d0 < (double)0.05F) || !(Math.abs(FoxEntity.this.rotationPitch) < 15.0F) || !FoxEntity.this.onGround) && !FoxEntity.this.func_213472_dX();
         } else {
            return false;
         }
      }

      public boolean isPreemptible() {
         return false;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         FoxEntity.this.setJumping(true);
         FoxEntity.this.func_213461_s(true);
         FoxEntity.this.func_213502_u(false);
         LivingEntity livingentity = FoxEntity.this.getAttackTarget();
         FoxEntity.this.getLookHelper().setLookPositionWithEntity(livingentity, 60.0F, 30.0F);
         Vec3d vec3d = (new Vec3d(livingentity.posX - FoxEntity.this.posX, livingentity.posY - FoxEntity.this.posY, livingentity.posZ - FoxEntity.this.posZ)).normalize();
         FoxEntity.this.setMotion(FoxEntity.this.getMotion().add(vec3d.x * 0.8D, 0.9D, vec3d.z * 0.8D));
         FoxEntity.this.getNavigator().clearPath();
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         FoxEntity.this.func_213451_t(false);
         FoxEntity.this.field_213520_bN = 0.0F;
         FoxEntity.this.field_213521_bO = 0.0F;
         FoxEntity.this.func_213502_u(false);
         FoxEntity.this.func_213461_s(false);
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         LivingEntity livingentity = FoxEntity.this.getAttackTarget();
         if (livingentity != null) {
            FoxEntity.this.getLookHelper().setLookPositionWithEntity(livingentity, 60.0F, 30.0F);
         }

         if (!FoxEntity.this.func_213472_dX()) {
            Vec3d vec3d = FoxEntity.this.getMotion();
            if (vec3d.y * vec3d.y < (double)0.03F && FoxEntity.this.rotationPitch != 0.0F) {
               FoxEntity.this.rotationPitch = this.updateRotation(FoxEntity.this.rotationPitch, 0.0F, 0.2F);
            } else {
               double d0 = Math.sqrt(Entity.func_213296_b(vec3d));
               double d1 = Math.signum(-vec3d.y) * Math.acos(d0 / vec3d.length()) * (double)(180F / (float)Math.PI);
               FoxEntity.this.rotationPitch = (float)d1;
            }
         }

         if (livingentity != null && FoxEntity.this.getDistance(livingentity) <= 2.0F) {
            FoxEntity.this.attackEntityAsMob(livingentity);
         } else if (FoxEntity.this.rotationPitch > 0.0F && FoxEntity.this.onGround && (float)FoxEntity.this.getMotion().y != 0.0F && FoxEntity.this.world.getBlockState(new BlockPos(FoxEntity.this)).getBlock() == Blocks.SNOW) {
            FoxEntity.this.rotationPitch = 60.0F;
            FoxEntity.this.setAttackTarget((LivingEntity)null);
            FoxEntity.this.func_213492_v(true);
         }

      }
   }

   class RevengeGoal extends NearestAttackableTargetGoal<LivingEntity> {
      @Nullable
      private LivingEntity field_220786_j;
      private LivingEntity field_220787_k;
      private int field_220788_l;

      public RevengeGoal(Class<LivingEntity> p_i50743_2_, boolean p_i50743_3_, boolean p_i50743_4_, @Nullable Predicate<LivingEntity> p_i50743_5_) {
         super(FoxEntity.this, p_i50743_2_, 10, p_i50743_3_, p_i50743_4_, p_i50743_5_);
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (this.targetChance > 0 && this.field_75299_d.getRNG().nextInt(this.targetChance) != 0) {
            return false;
         } else {
            for(UUID uuid : FoxEntity.this.func_213479_ek()) {
               if (uuid != null && FoxEntity.this.world instanceof ServerWorld) {
                  Entity entity = ((ServerWorld)FoxEntity.this.world).func_217461_a(uuid);
                  if (entity instanceof LivingEntity) {
                     LivingEntity livingentity = (LivingEntity)entity;
                     this.field_220787_k = livingentity;
                     this.field_220786_j = livingentity.getRevengeTarget();
                     int i = livingentity.getRevengeTimer();
                     return i != this.field_220788_l && this.func_220777_a(this.field_220786_j, EntityPredicate.DEFAULT);
                  }
               }
            }

            return false;
         }
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         FoxEntity.this.setAttackTarget(this.field_220786_j);
         this.field_75309_a = this.field_220786_j;
         if (this.field_220787_k != null) {
            this.field_220788_l = this.field_220787_k.getRevengeTimer();
         }

         FoxEntity.this.playSound(SoundEvents.field_219630_dt, 1.0F, 1.0F);
         FoxEntity.this.func_213482_w(true);
         FoxEntity.this.func_213454_em();
         super.startExecuting();
      }
   }

   class SitAndLookGoal extends FoxEntity.BaseGoal {
      private double field_220819_c;
      private double field_220820_d;
      private int field_220821_e;
      private int field_220822_f;

      public SitAndLookGoal() {
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return FoxEntity.this.getRevengeTarget() == null && FoxEntity.this.getRNG().nextFloat() < 0.02F && !FoxEntity.this.isPlayerSleeping() && FoxEntity.this.getAttackTarget() == null && FoxEntity.this.getNavigator().noPath() && !this.func_220814_h() && !FoxEntity.this.func_213480_dY() && !FoxEntity.this.func_213453_ef();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return this.field_220822_f > 0;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.func_220817_j();
         this.field_220822_f = 2 + FoxEntity.this.getRNG().nextInt(3);
         FoxEntity.this.func_213466_r(true);
         FoxEntity.this.getNavigator().clearPath();
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         FoxEntity.this.func_213466_r(false);
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         --this.field_220821_e;
         if (this.field_220821_e <= 0) {
            --this.field_220822_f;
            this.func_220817_j();
         }

         FoxEntity.this.getLookHelper().setLookPosition(FoxEntity.this.posX + this.field_220819_c, FoxEntity.this.posY + (double)FoxEntity.this.getEyeHeight(), FoxEntity.this.posZ + this.field_220820_d, (float)FoxEntity.this.getHorizontalFaceSpeed(), (float)FoxEntity.this.getVerticalFaceSpeed());
      }

      private void func_220817_j() {
         double d0 = (Math.PI * 2D) * FoxEntity.this.getRNG().nextDouble();
         this.field_220819_c = Math.cos(d0);
         this.field_220820_d = Math.sin(d0);
         this.field_220821_e = 80 + FoxEntity.this.getRNG().nextInt(20);
      }
   }

   class SleepGoal extends FoxEntity.BaseGoal {
      private int field_220825_c = FoxEntity.this.rand.nextInt(140);

      public SleepGoal() {
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (FoxEntity.this.moveStrafing == 0.0F && FoxEntity.this.moveVertical == 0.0F && FoxEntity.this.moveForward == 0.0F) {
            return this.func_220823_j() || FoxEntity.this.isPlayerSleeping();
         } else {
            return false;
         }
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return this.func_220823_j();
      }

      private boolean func_220823_j() {
         if (this.field_220825_c > 0) {
            --this.field_220825_c;
            return false;
         } else {
            return FoxEntity.this.world.isDaytime() && this.func_220813_g() && !this.func_220814_h();
         }
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         this.field_220825_c = FoxEntity.this.rand.nextInt(140);
         FoxEntity.this.func_213499_en();
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         FoxEntity.this.func_213466_r(false);
         FoxEntity.this.func_213451_t(false);
         FoxEntity.this.func_213502_u(false);
         FoxEntity.this.setJumping(false);
         FoxEntity.this.func_213485_x(true);
         FoxEntity.this.getNavigator().clearPath();
         FoxEntity.this.getMoveHelper().setMoveTo(FoxEntity.this.posX, FoxEntity.this.posY, FoxEntity.this.posZ, 0.0D);
      }
   }

   class StrollGoal extends MoveThroughVillageAtNightGoal {
      public StrollGoal(int p_i50726_2_, int p_i50726_3_) {
         super(FoxEntity.this, p_i50726_3_);
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         FoxEntity.this.func_213499_en();
         super.startExecuting();
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return super.shouldExecute() && this.func_220759_g();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return super.shouldContinueExecuting() && this.func_220759_g();
      }

      private boolean func_220759_g() {
         return !FoxEntity.this.isPlayerSleeping() && !FoxEntity.this.func_213455_dW() && !FoxEntity.this.func_213473_el() && FoxEntity.this.getAttackTarget() == null;
      }
   }

   class SwimGoal extends net.minecraft.entity.ai.goal.SwimGoal {
      public SwimGoal() {
         super(FoxEntity.this);
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         super.startExecuting();
         FoxEntity.this.func_213499_en();
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return FoxEntity.this.isInWater() && FoxEntity.this.getSubmergedHeight() > 0.25D || FoxEntity.this.isInLava();
      }
   }

   public static enum Type {
      RED(0, "red", Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA_HILLS),
      SNOW(1, "snow", Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS);

      private static final FoxEntity.Type[] field_221088_c = Arrays.stream(values()).sorted(Comparator.comparingInt(FoxEntity.Type::func_221083_c)).toArray((p_221084_0_) -> {
         return new FoxEntity.Type[p_221084_0_];
      });
      private static final Map<String, FoxEntity.Type> field_221089_d = Arrays.stream(values()).collect(Collectors.toMap(FoxEntity.Type::func_221082_a, (p_221081_0_) -> {
         return p_221081_0_;
      }));
      private final int field_221090_e;
      private final String field_221091_f;
      private final List<Biome> field_221092_g;

      private Type(int p_i50721_3_, String p_i50721_4_, Biome... p_i50721_5_) {
         this.field_221090_e = p_i50721_3_;
         this.field_221091_f = p_i50721_4_;
         this.field_221092_g = Arrays.asList(p_i50721_5_);
      }

      public String func_221082_a() {
         return this.field_221091_f;
      }

      public List<Biome> func_221085_b() {
         return this.field_221092_g;
      }

      public int func_221083_c() {
         return this.field_221090_e;
      }

      public static FoxEntity.Type func_221087_a(String p_221087_0_) {
         return field_221089_d.getOrDefault(p_221087_0_, RED);
      }

      public static FoxEntity.Type func_221080_a(int p_221080_0_) {
         if (p_221080_0_ < 0 || p_221080_0_ > field_221088_c.length) {
            p_221080_0_ = 0;
         }

         return field_221088_c[p_221080_0_];
      }

      public static FoxEntity.Type func_221086_a(Biome p_221086_0_) {
         return SNOW.func_221085_b().contains(p_221086_0_) ? SNOW : RED;
      }
   }

   class WatchGoal extends LookAtGoal {
      public WatchGoal(MobEntity p_i50733_2_, Class<? extends LivingEntity> p_i50733_3_, float p_i50733_4_) {
         super(p_i50733_2_, p_i50733_3_, p_i50733_4_);
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return super.shouldExecute() && !FoxEntity.this.func_213472_dX() && !FoxEntity.this.func_213467_eg();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return super.shouldContinueExecuting() && !FoxEntity.this.func_213472_dX() && !FoxEntity.this.func_213467_eg();
      }
   }
}