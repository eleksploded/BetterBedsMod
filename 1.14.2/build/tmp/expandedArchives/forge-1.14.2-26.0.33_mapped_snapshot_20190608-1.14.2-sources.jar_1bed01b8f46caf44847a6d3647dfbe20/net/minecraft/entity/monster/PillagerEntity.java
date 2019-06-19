package net.minecraft.entity.monster;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PillagerEntity extends AbstractIllagerEntity implements ICrossbowUser, IRangedAttackMob {
   private static final DataParameter<Boolean> field_213676_b = EntityDataManager.createKey(PillagerEntity.class, DataSerializers.field_187198_h);
   private final Inventory field_213677_bz = new Inventory(5);

   public PillagerEntity(EntityType<? extends PillagerEntity> p_i50198_1_, World p_i50198_2_) {
      super(p_i50198_1_, p_i50198_2_);
   }

   protected void initEntityAI() {
      super.initEntityAI();
      this.field_70714_bg.addTask(0, new SwimGoal(this));
      this.field_70714_bg.addTask(2, new AbstractRaiderEntity.FindTargetGoal(this, 10.0F));
      this.field_70714_bg.addTask(3, new RangedCrossbowAttackGoal<>(this, 1.0D, 8.0F));
      this.field_70714_bg.addTask(8, new RandomWalkingGoal(this, 0.6D));
      this.field_70714_bg.addTask(9, new LookAtGoal(this, PlayerEntity.class, 15.0F, 1.0F));
      this.field_70714_bg.addTask(10, new LookAtGoal(this, MobEntity.class, 15.0F));
      this.field_70715_bh.addTask(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).func_220794_a());
      this.field_70715_bh.addTask(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
      this.field_70715_bh.addTask(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
      this.field_70715_bh.addTask(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.35F);
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_213676_b, false);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean func_213675_dX() {
      return this.dataManager.get(field_213676_b);
   }

   public void func_213671_a(boolean p_213671_1_) {
      this.dataManager.set(field_213676_b, p_213671_1_);
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      ListNBT listnbt = new ListNBT();

      for(int i = 0; i < this.field_213677_bz.getSizeInventory(); ++i) {
         ItemStack itemstack = this.field_213677_bz.getStackInSlot(i);
         if (!itemstack.isEmpty()) {
            listnbt.add(itemstack.write(new CompoundNBT()));
         }
      }

      p_213281_1_.put("Inventory", listnbt);
   }

   @OnlyIn(Dist.CLIENT)
   public AbstractIllagerEntity.ArmPose getArmPose() {
      if (this.func_213675_dX()) {
         return AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE;
      } else if (this.func_213382_a(Items.field_222114_py)) {
         return AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD;
      } else {
         return this.func_213398_dR() ? AbstractIllagerEntity.ArmPose.ATTACKING : AbstractIllagerEntity.ArmPose.CROSSED;
      }
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      ListNBT listnbt = compound.getList("Inventory", 10);

      for(int i = 0; i < listnbt.size(); ++i) {
         ItemStack itemstack = ItemStack.read(listnbt.getCompound(i));
         if (!itemstack.isEmpty()) {
            this.field_213677_bz.addItem(itemstack);
         }
      }

      this.setCanPickUpLoot(true);
   }

   public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
      Block block = worldIn.getBlockState(pos.down()).getBlock();
      return block != Blocks.GRASS_BLOCK && block != Blocks.SAND ? 0.5F - worldIn.getBrightness(pos) : 10.0F;
   }

   /**
    * Checks to make sure the light is not too bright where the mob is spawning
    */
   protected boolean isValidLightLevel() {
      return true;
   }

   public boolean canSpawn(IWorld p_213380_1_, SpawnReason p_213380_2_) {
      return p_213380_1_.getLightFor(LightType.BLOCK, new BlockPos(this.posX, this.posY, this.posZ)) > 8 ? false : super.canSpawn(p_213380_1_, p_213380_2_);
   }

   /**
    * Will return how many at most can spawn in a chunk at once.
    */
   public int getMaxSpawnedInChunk() {
      return 1;
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      this.setEquipmentBasedOnDifficulty(p_213386_2_);
      this.setEnchantmentBasedOnDifficulty(p_213386_2_);
      return super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
   }

   /**
    * Gives armor or weapon for entity based on given DifficultyInstance
    */
   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
      ItemStack itemstack = new ItemStack(Items.field_222114_py);
      if (this.rand.nextInt(300) == 0) {
         Map<Enchantment, Integer> map = Maps.newHashMap();
         map.put(Enchantments.field_222194_I, 1);
         EnchantmentHelper.setEnchantments(map, itemstack);
      }

      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
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

   protected SoundEvent getAmbientSound() {
      return SoundEvents.field_219686_is;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.field_219688_iu;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.field_219689_iv;
   }

   /**
    * Attack the specified entity using a ranged attack.
    */
   public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
      Hand hand = ProjectileHelper.func_221274_a(this, Items.field_222114_py);
      ItemStack itemstack = this.getHeldItem(hand);
      if (this.func_213382_a(Items.field_222114_py)) {
         CrossbowItem.func_220014_a(this.world, this, hand, itemstack, 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
      }

      this.idleTime = 0;
   }

   public void func_213670_a(LivingEntity p_213670_1_, ItemStack p_213670_2_, IProjectile p_213670_3_, float p_213670_4_) {
      Entity entity = (Entity)p_213670_3_;
      double d0 = p_213670_1_.posX - this.posX;
      double d1 = p_213670_1_.posZ - this.posZ;
      double d2 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
      double d3 = p_213670_1_.getBoundingBox().minY + (double)(p_213670_1_.getHeight() / 3.0F) - entity.posY + d2 * (double)0.2F;
      Vector3f vector3f = this.func_213673_a(new Vec3d(d0, d3, d1), p_213670_4_);
      p_213670_3_.shoot((double)vector3f.getX(), (double)vector3f.getY(), (double)vector3f.getZ(), 1.6F, (float)(14 - this.world.getDifficulty().getId() * 4));
      this.playSound(SoundEvents.field_219616_bH, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
   }

   private Vector3f func_213673_a(Vec3d p_213673_1_, float p_213673_2_) {
      Vec3d vec3d = p_213673_1_.normalize();
      Vec3d vec3d1 = vec3d.crossProduct(new Vec3d(0.0D, 1.0D, 0.0D));
      if (vec3d1.lengthSquared() <= 1.0E-7D) {
         vec3d1 = vec3d.crossProduct(this.func_213286_i(1.0F));
      }

      Quaternion quaternion = new Quaternion(new Vector3f(vec3d1), 90.0F, true);
      Vector3f vector3f = new Vector3f(vec3d);
      vector3f.func_214905_a(quaternion);
      Quaternion quaternion1 = new Quaternion(vector3f, p_213673_2_, true);
      Vector3f vector3f1 = new Vector3f(vec3d);
      vector3f1.func_214905_a(quaternion1);
      return vector3f1;
   }

   public Inventory func_213674_eg() {
      return this.field_213677_bz;
   }

   /**
    * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
    * better.
    */
   protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
      ItemStack itemstack = itemEntity.getItem();
      if (itemstack.getItem() instanceof BannerItem) {
         super.updateEquipmentIfNeeded(itemEntity);
      } else {
         Item item = itemstack.getItem();
         if (this.func_213672_b(item)) {
            ItemStack itemstack1 = this.field_213677_bz.addItem(itemstack);
            if (itemstack1.isEmpty()) {
               itemEntity.remove();
            } else {
               itemstack.setCount(itemstack1.getCount());
            }
         }
      }

   }

   private boolean func_213672_b(Item p_213672_1_) {
      return this.func_213657_el() && p_213672_1_ == Items.WHITE_BANNER;
   }

   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
      if (super.replaceItemInInventory(inventorySlot, itemStackIn)) {
         return true;
      } else {
         int i = inventorySlot - 300;
         if (i >= 0 && i < this.field_213677_bz.getSizeInventory()) {
            this.field_213677_bz.setInventorySlotContents(i, itemStackIn);
            return true;
         } else {
            return false;
         }
      }
   }

   public void func_213660_a(int p_213660_1_, boolean p_213660_2_) {
      Raid raid = this.func_213663_ek();
      boolean flag = this.rand.nextFloat() <= raid.func_221308_w();
      if (flag) {
         ItemStack itemstack = new ItemStack(Items.field_222114_py);
         Map<Enchantment, Integer> map = Maps.newHashMap();
         if (p_213660_1_ > raid.func_221306_a(Difficulty.NORMAL)) {
            map.put(Enchantments.field_222193_H, 2);
         } else if (p_213660_1_ > raid.func_221306_a(Difficulty.EASY)) {
            map.put(Enchantments.field_222193_H, 1);
         }

         map.put(Enchantments.field_222192_G, 1);
         EnchantmentHelper.setEnchantments(map, itemstack);
         this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
      }

   }

   public boolean func_213392_I() {
      return super.func_213392_I() && this.func_213674_eg().isEmpty();
   }

   public SoundEvent func_213654_dW() {
      return SoundEvents.field_219687_it;
   }

   public boolean func_213397_c(double p_213397_1_) {
      return super.func_213397_c(p_213397_1_) && this.func_213674_eg().isEmpty();
   }
}