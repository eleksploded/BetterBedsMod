package net.minecraft.world.spawner;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.storage.WorldInfo;

public class WanderingTraderSpawner {
   private final Random field_221246_a = new Random();
   private final ServerWorld field_221247_b;
   private int field_221248_c;
   private int field_221249_d;
   private int field_221250_e;

   public WanderingTraderSpawner(ServerWorld p_i50177_1_) {
      this.field_221247_b = p_i50177_1_;
      this.field_221248_c = 1200;
      WorldInfo worldinfo = p_i50177_1_.getWorldInfo();
      this.field_221249_d = worldinfo.func_215765_R();
      this.field_221250_e = worldinfo.func_215760_S();
      if (this.field_221249_d == 0 && this.field_221250_e == 0) {
         this.field_221249_d = 24000;
         worldinfo.func_215764_j(this.field_221249_d);
         this.field_221250_e = 25;
         worldinfo.func_215762_k(this.field_221250_e);
      }

   }

   public void func_221242_a() {
      if (--this.field_221248_c <= 0) {
         this.field_221248_c = 1200;
         WorldInfo worldinfo = this.field_221247_b.getWorldInfo();
         this.field_221249_d -= 1200;
         worldinfo.func_215764_j(this.field_221249_d);
         if (this.field_221249_d <= 0) {
            this.field_221249_d = 24000;
            if (this.field_221247_b.getGameRules().getBoolean("doMobSpawning")) {
               int i = this.field_221250_e;
               this.field_221250_e = MathHelper.clamp(this.field_221250_e + 25, 25, 75);
               worldinfo.func_215762_k(this.field_221250_e);
               if (this.field_221246_a.nextInt(100) <= i) {
                  if (this.func_221245_b()) {
                     this.field_221250_e = 25;
                  }

               }
            }
         }
      }
   }

   private boolean func_221245_b() {
      PlayerEntity playerentity = this.field_221247_b.getRandomPlayer();
      if (playerentity == null) {
         return true;
      } else if (this.field_221246_a.nextInt(10) != 0) {
         return false;
      } else {
         BlockPos blockpos = playerentity.getPosition();
         int i = 48;
         PointOfInterestManager pointofinterestmanager = this.field_221247_b.func_217443_B();
         Optional<BlockPos> optional = pointofinterestmanager.func_219127_a(PointOfInterestType.field_221070_r.func_221045_c(), (p_221241_0_) -> {
            return true;
         }, blockpos, 48, PointOfInterestManager.Status.ANY);
         BlockPos blockpos1 = optional.orElse(blockpos);
         BlockPos blockpos2 = this.func_221244_a(blockpos1, 48);
         if (blockpos2 != null) {
            if (this.field_221247_b.getBiome(blockpos2) == Biomes.THE_VOID) {
               return false;
            }

            WanderingTraderEntity wanderingtraderentity = EntityType.field_220351_aK.func_220342_a(this.field_221247_b, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos2, SpawnReason.EVENT, false, false);
            if (wanderingtraderentity != null) {
               for(int j = 0; j < 2; ++j) {
                  this.func_221243_a(wanderingtraderentity, 4);
               }

               this.field_221247_b.getWorldInfo().func_215761_a(wanderingtraderentity.getUniqueID());
               wanderingtraderentity.func_213728_s(48000);
               wanderingtraderentity.func_213726_g(blockpos1);
               wanderingtraderentity.func_213390_a(blockpos1, 16);
               return true;
            }
         }

         return false;
      }
   }

   private void func_221243_a(WanderingTraderEntity p_221243_1_, int p_221243_2_) {
      BlockPos blockpos = this.func_221244_a(new BlockPos(p_221243_1_), p_221243_2_);
      if (blockpos != null) {
         TraderLlamaEntity traderllamaentity = EntityType.field_220354_ax.func_220342_a(this.field_221247_b, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos, SpawnReason.EVENT, false, false);
         if (traderllamaentity != null) {
            traderllamaentity.setLeashHolder(p_221243_1_, true);
         }
      }
   }

   @Nullable
   private BlockPos func_221244_a(BlockPos p_221244_1_, int p_221244_2_) {
      BlockPos blockpos = null;

      for(int i = 0; i < 10; ++i) {
         int j = p_221244_1_.getX() + this.field_221246_a.nextInt(p_221244_2_ * 2) - p_221244_2_;
         int k = p_221244_1_.getZ() + this.field_221246_a.nextInt(p_221244_2_ * 2) - p_221244_2_;
         int l = this.field_221247_b.getHeight(Heightmap.Type.WORLD_SURFACE, j, k);
         BlockPos blockpos1 = new BlockPos(j, l, k);
         if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, this.field_221247_b, blockpos1, EntityType.field_220351_aK)) {
            blockpos = blockpos1;
            break;
         }
      }

      return blockpos;
   }
}