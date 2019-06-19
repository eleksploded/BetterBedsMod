package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.World;

public class BreedGoal extends Goal {
   private static final EntityPredicate field_220689_d = (new EntityPredicate()).func_221013_a(8.0D).func_221008_a().func_221011_b().func_221014_c();
   protected final AnimalEntity field_75390_d;
   private final Class<? extends AnimalEntity> mateClass;
   protected final World world;
   protected AnimalEntity field_75391_e;
   private int spawnBabyDelay;
   private final double moveSpeed;

   public BreedGoal(AnimalEntity animal, double speedIn) {
      this(animal, speedIn, animal.getClass());
   }

   public BreedGoal(AnimalEntity p_i47306_1_, double p_i47306_2_, Class<? extends AnimalEntity> p_i47306_4_) {
      this.field_75390_d = p_i47306_1_;
      this.world = p_i47306_1_.world;
      this.mateClass = p_i47306_4_;
      this.moveSpeed = p_i47306_2_;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (!this.field_75390_d.isInLove()) {
         return false;
      } else {
         this.field_75391_e = this.getNearbyMate();
         return this.field_75391_e != null;
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return this.field_75391_e.isAlive() && this.field_75391_e.isInLove() && this.spawnBabyDelay < 60;
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75391_e = null;
      this.spawnBabyDelay = 0;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.field_75390_d.getLookHelper().setLookPositionWithEntity(this.field_75391_e, 10.0F, (float)this.field_75390_d.getVerticalFaceSpeed());
      this.field_75390_d.getNavigator().tryMoveToEntityLiving(this.field_75391_e, this.moveSpeed);
      ++this.spawnBabyDelay;
      if (this.spawnBabyDelay >= 60 && this.field_75390_d.getDistanceSq(this.field_75391_e) < 9.0D) {
         this.spawnBaby();
      }

   }

   /**
    * Loops through nearby animals and finds another animal of the same type that can be mated with. Returns the first
    * valid mate found.
    */
   @Nullable
   private AnimalEntity getNearbyMate() {
      List<AnimalEntity> list = this.world.func_217374_a(this.mateClass, field_220689_d, this.field_75390_d, this.field_75390_d.getBoundingBox().grow(8.0D));
      double d0 = Double.MAX_VALUE;
      AnimalEntity animalentity = null;

      for(AnimalEntity animalentity1 : list) {
         if (this.field_75390_d.canMateWith(animalentity1) && this.field_75390_d.getDistanceSq(animalentity1) < d0) {
            animalentity = animalentity1;
            d0 = this.field_75390_d.getDistanceSq(animalentity1);
         }
      }

      return animalentity;
   }

   /**
    * Spawns a baby animal of the same type.
    */
   protected void spawnBaby() {
      AgeableEntity ageableentity = this.field_75390_d.createChild(this.field_75391_e);
      final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(field_75390_d, field_75391_e, ageableentity);
      final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
      ageableentity = event.getChild();
      if (cancelled) {
         //Reset the "inLove" state for the animals
         this.field_75390_d.setGrowingAge(6000);
         this.field_75391_e.setGrowingAge(6000);
         this.field_75390_d.resetInLove();
         this.field_75391_e.resetInLove();
         return;
      }
      if (ageableentity != null) {
         ServerPlayerEntity serverplayerentity = this.field_75390_d.getLoveCause();
         if (serverplayerentity == null && this.field_75391_e.getLoveCause() != null) {
            serverplayerentity = this.field_75391_e.getLoveCause();
         }

         if (serverplayerentity != null) {
            serverplayerentity.addStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this.field_75390_d, this.field_75391_e, ageableentity);
         }

         this.field_75390_d.setGrowingAge(6000);
         this.field_75391_e.setGrowingAge(6000);
         this.field_75390_d.resetInLove();
         this.field_75391_e.resetInLove();
         ageableentity.setGrowingAge(-24000);
         ageableentity.setLocationAndAngles(this.field_75390_d.posX, this.field_75390_d.posY, this.field_75390_d.posZ, 0.0F, 0.0F);
         this.world.func_217376_c(ageableentity);
         this.world.setEntityState(this.field_75390_d, (byte)18);
         if (this.world.getGameRules().getBoolean("doMobLoot")) {
            this.world.func_217376_c(new ExperienceOrbEntity(this.world, this.field_75390_d.posX, this.field_75390_d.posY, this.field_75390_d.posZ, this.field_75390_d.getRNG().nextInt(7) + 1));
         }

      }
   }
}