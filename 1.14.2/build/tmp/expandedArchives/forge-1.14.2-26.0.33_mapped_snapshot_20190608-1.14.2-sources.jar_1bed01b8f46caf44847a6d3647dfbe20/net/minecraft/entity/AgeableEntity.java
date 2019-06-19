package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class AgeableEntity extends CreatureEntity {
   private static final DataParameter<Boolean> BABY = EntityDataManager.createKey(AgeableEntity.class, DataSerializers.field_187198_h);
   protected int growingAge;
   protected int forcedAge;
   protected int forcedAgeTimer;

   protected AgeableEntity(EntityType<? extends AgeableEntity> type, World p_i48581_2_) {
      super(type, p_i48581_2_);
   }

   @Nullable
   public abstract AgeableEntity createChild(AgeableEntity ageable);

   protected void func_213406_a(PlayerEntity p_213406_1_, AgeableEntity p_213406_2_) {
   }

   public boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      Item item = itemstack.getItem();
      if (item instanceof SpawnEggItem && ((SpawnEggItem)item).hasType(itemstack.getTag(), this.getType())) {
         if (!this.world.isRemote) {
            AgeableEntity ageableentity = this.createChild(this);
            if (ageableentity != null) {
               ageableentity.setGrowingAge(-24000);
               ageableentity.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
               this.world.func_217376_c(ageableentity);
               if (itemstack.hasDisplayName()) {
                  ageableentity.setCustomName(itemstack.getDisplayName());
               }

               this.func_213406_a(player, ageableentity);
               if (!player.playerAbilities.isCreativeMode) {
                  itemstack.shrink(1);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(BABY, false);
   }

   /**
    * The age value may be negative or positive or zero. If it's negative, it get's incremented on each tick, if it's
    * positive, it get's decremented each tick. Don't confuse this with EntityLiving.getAge. With a negative value the
    * Entity is considered a child.
    */
   public int getGrowingAge() {
      if (this.world.isRemote) {
         return this.dataManager.get(BABY) ? -1 : 1;
      } else {
         return this.growingAge;
      }
   }

   /**
    * Increases this entity's age, optionally updating {@link #forcedAge}. If the entity is an adult (if the entity's
    * age is greater than or equal to 0) then the entity's age will be set to {@link #forcedAge}.
    */
   public void ageUp(int growthSeconds, boolean updateForcedAge) {
      int i = this.getGrowingAge();
      int j = i;
      i = i + growthSeconds * 20;
      if (i > 0) {
         i = 0;
      }

      int k = i - j;
      this.setGrowingAge(i);
      if (updateForcedAge) {
         this.forcedAge += k;
         if (this.forcedAgeTimer == 0) {
            this.forcedAgeTimer = 40;
         }
      }

      if (this.getGrowingAge() == 0) {
         this.setGrowingAge(this.forcedAge);
      }

   }

   /**
    * Increases this entity's age. If the entity is an adult (if the entity's age is greater than or equal to 0) then
    * the entity's age will be set to {@link #forcedAge}. This method does not update {@link #forcedAge}.
    */
   public void addGrowth(int growth) {
      this.ageUp(growth, false);
   }

   /**
    * The age value may be negative or positive or zero. If it's negative, it get's incremented on each tick, if it's
    * positive, it get's decremented each tick. With a negative value the Entity is considered a child.
    */
   public void setGrowingAge(int age) {
      int i = this.growingAge;
      this.growingAge = age;
      if (i < 0 && age >= 0 || i >= 0 && age < 0) {
         this.dataManager.set(BABY, age < 0);
         this.onGrowingAdult();
      }

   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putInt("Age", this.getGrowingAge());
      p_213281_1_.putInt("ForcedAge", this.forcedAge);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.setGrowingAge(compound.getInt("Age"));
      this.forcedAge = compound.getInt("ForcedAge");
   }

   public void notifyDataManagerChange(DataParameter<?> key) {
      if (BABY.equals(key)) {
         this.recalculateSize();
      }

      super.notifyDataManagerChange(key);
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      super.livingTick();
      if (this.world.isRemote) {
         if (this.forcedAgeTimer > 0) {
            if (this.forcedAgeTimer % 4 == 0) {
               this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + 0.5D + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), 0.0D, 0.0D, 0.0D);
            }

            --this.forcedAgeTimer;
         }
      } else if (this.isAlive()) {
         int i = this.getGrowingAge();
         if (i < 0) {
            ++i;
            this.setGrowingAge(i);
         } else if (i > 0) {
            --i;
            this.setGrowingAge(i);
         }
      }

   }

   /**
    * This is called when Entity's growing age timer reaches 0 (negative values are considered as a child, positive as
    * an adult)
    */
   protected void onGrowingAdult() {
   }

   /**
    * If Animal, checks if the age timer is negative
    */
   public boolean isChild() {
      return this.getGrowingAge() < 0;
   }
}