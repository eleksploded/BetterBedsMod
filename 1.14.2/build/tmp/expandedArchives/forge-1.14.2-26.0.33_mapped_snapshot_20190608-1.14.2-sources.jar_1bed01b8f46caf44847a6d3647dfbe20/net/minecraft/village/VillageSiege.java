package net.minecraft.village;

import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class VillageSiege {
   private final ServerWorld field_75537_a;
   private boolean hasSetupSiege;
   private VillageSiege.State field_75536_c = VillageSiege.State.SIEGE_DONE;
   private int siegeCount;
   private int nextSpawnTime;
   private int spawnX;
   private int spawnY;
   private int spawnZ;

   public VillageSiege(ServerWorld p_i50299_1_) {
      this.field_75537_a = p_i50299_1_;
   }

   /**
    * Runs a single tick for the village siege
    */
   public void tick() {
      if (this.field_75537_a.isDaytime()) {
         this.field_75536_c = VillageSiege.State.SIEGE_DONE;
         this.hasSetupSiege = false;
      } else {
         float f = this.field_75537_a.getCelestialAngle(0.0F);
         if ((double)f == 0.5D) {
            this.field_75536_c = this.field_75537_a.rand.nextInt(10) == 0 ? VillageSiege.State.SIEGE_TONIGHT : VillageSiege.State.SIEGE_DONE;
         }

         if (this.field_75536_c != VillageSiege.State.SIEGE_DONE) {
            if (!this.hasSetupSiege) {
               if (!this.trySetupSiege()) {
                  return;
               }

               this.hasSetupSiege = true;
            }

            if (this.nextSpawnTime > 0) {
               --this.nextSpawnTime;
            } else {
               this.nextSpawnTime = 2;
               if (this.siegeCount > 0) {
                  this.spawnZombie();
                  --this.siegeCount;
               } else {
                  this.field_75536_c = VillageSiege.State.SIEGE_DONE;
               }

            }
         }
      }
   }

   private boolean trySetupSiege() {
      Iterator iterator = this.field_75537_a.getPlayers().iterator();

      while(true) {
         if (!iterator.hasNext()) {
            return false;
         }

         PlayerEntity playerentity = (PlayerEntity)iterator.next();
         if (!playerentity.isSpectator()) {
            BlockPos blockpos = new BlockPos(playerentity);
            if (this.field_75537_a.func_217483_b_(blockpos)) {
               for(int i = 0; i < 10; ++i) {
                  float f = this.field_75537_a.rand.nextFloat() * ((float)Math.PI * 2F);
                  this.spawnX = blockpos.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0F);
                  this.spawnY = blockpos.getY();
                  this.spawnZ = blockpos.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0F);
               }

               Vec3d vec3d = this.findRandomSpawnPos(new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
               if (vec3d != null) {
                  if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.village.VillageSiegeEvent(this, field_75537_a, playerentity, vec3d))) return false;
                  break;
               }
            }
         }
      }

      this.nextSpawnTime = 0;
      this.siegeCount = 20;
      return true;
   }

   private void spawnZombie() {
      Vec3d vec3d = this.findRandomSpawnPos(new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
      if (vec3d != null) {
         ZombieEntity zombieentity;
         try {
            zombieentity = EntityType.ZOMBIE.create(this.field_75537_a); //Forge: Direct Initialization is deprecated, use EntityType.
            zombieentity.onInitialSpawn(this.field_75537_a, this.field_75537_a.getDifficultyForLocation(new BlockPos(zombieentity)), SpawnReason.EVENT, (ILivingEntityData)null, (CompoundNBT)null);
         } catch (Exception exception) {
            exception.printStackTrace();
            return;
         }

         zombieentity.setLocationAndAngles(vec3d.x, vec3d.y, vec3d.z, this.field_75537_a.rand.nextFloat() * 360.0F, 0.0F);
         this.field_75537_a.func_217376_c(zombieentity);
      }
   }

   @Nullable
   private Vec3d findRandomSpawnPos(BlockPos pos) {
      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos = pos.add(this.field_75537_a.rand.nextInt(16) - 8, this.field_75537_a.rand.nextInt(6) - 3, this.field_75537_a.rand.nextInt(16) - 8);
         if (this.field_75537_a.func_217483_b_(blockpos) && WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.field_75537_a, blockpos, EntityType.ZOMBIE)) { //Forge: MC-154328 passing in null always returns false, so pass in ZOMBIE
            return new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
         }
      }

      return null;
   }

   static enum State {
      SIEGE_CAN_ACTIVATE,
      SIEGE_TONIGHT,
      SIEGE_DONE;
   }
}