package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.ServerWorld;

public class CreateBabyVillagerTask extends Task<VillagerEntity> {
   private long field_220483_a;

   public CreateBabyVillagerTask() {
      super(ImmutableMap.of(MemoryModuleType.field_220953_n, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_220946_g, MemoryModuleStatus.VALUE_PRESENT), 350, 350);
   }

   protected boolean shouldExecute(ServerWorld worldIn, VillagerEntity owner) {
      return this.func_220478_b(owner);
   }

   protected boolean shouldContinueExecuting(ServerWorld p_212834_1_, VillagerEntity p_212834_2_, long p_212834_3_) {
      return p_212834_3_ <= this.field_220483_a && this.func_220478_b(p_212834_2_);
   }

   protected void startExecuting(ServerWorld p_212831_1_, VillagerEntity p_212831_2_, long p_212831_3_) {
      VillagerEntity villagerentity = this.func_220482_a(p_212831_2_);
      BrainUtil.func_220618_a(p_212831_2_, villagerentity);
      p_212831_1_.setEntityState(villagerentity, (byte)18);
      p_212831_1_.setEntityState(p_212831_2_, (byte)18);
      int i = 275 + p_212831_2_.getRNG().nextInt(50);
      this.field_220483_a = p_212831_3_ + (long)i;
   }

   protected void updateTask(ServerWorld p_212833_1_, VillagerEntity p_212833_2_, long p_212833_3_) {
      VillagerEntity villagerentity = this.func_220482_a(p_212833_2_);
      if (!(p_212833_2_.getDistanceSq(villagerentity) > 5.0D)) {
         BrainUtil.func_220618_a(p_212833_2_, villagerentity);
         if (p_212833_3_ >= this.field_220483_a) {
            Optional<BlockPos> optional = this.func_220479_b(p_212833_1_, p_212833_2_);
            if (!optional.isPresent()) {
               p_212833_1_.setEntityState(villagerentity, (byte)13);
               p_212833_1_.setEntityState(p_212833_2_, (byte)13);
               return;
            }

            p_212833_2_.func_213765_en();
            villagerentity.func_213765_en();
            Optional<VillagerEntity> optional1 = this.func_220480_a(p_212833_2_, villagerentity);
            if (optional1.isPresent()) {
               p_212833_2_.func_213758_s(12);
               villagerentity.func_213758_s(12);
               this.func_220477_a(p_212833_1_, optional1.get(), optional.get());
            } else {
               p_212833_1_.func_217443_B().func_219142_b(optional.get());
            }
         }

         if (p_212833_2_.getRNG().nextInt(35) == 0) {
            p_212833_1_.setEntityState(villagerentity, (byte)12);
            p_212833_1_.setEntityState(p_212833_2_, (byte)12);
         }

      }
   }

   protected void resetTask(ServerWorld p_212835_1_, VillagerEntity p_212835_2_, long p_212835_3_) {
      p_212835_2_.getBrain().removeMemory(MemoryModuleType.field_220953_n);
   }

   private VillagerEntity func_220482_a(VillagerEntity p_220482_1_) {
      return p_220482_1_.getBrain().getMemory(MemoryModuleType.field_220953_n).get();
   }

   private boolean func_220478_b(VillagerEntity p_220478_1_) {
      Brain<VillagerEntity> brain = p_220478_1_.getBrain();
      if (!brain.getMemory(MemoryModuleType.field_220953_n).isPresent()) {
         return false;
      } else {
         VillagerEntity villagerentity = this.func_220482_a(p_220478_1_);
         return BrainUtil.func_220623_a(brain, MemoryModuleType.field_220953_n, EntityType.VILLAGER) && p_220478_1_.func_213743_em() && villagerentity.func_213743_em();
      }
   }

   private Optional<BlockPos> func_220479_b(ServerWorld p_220479_1_, VillagerEntity p_220479_2_) {
      return p_220479_1_.func_217443_B().func_219157_a(PointOfInterestType.field_221069_q.func_221045_c(), (p_220481_0_) -> {
         return true;
      }, new BlockPos(p_220479_2_), 48);
   }

   private Optional<VillagerEntity> func_220480_a(VillagerEntity p_220480_1_, VillagerEntity p_220480_2_) {
      VillagerEntity villagerentity = p_220480_1_.createChild(p_220480_2_);
      if (villagerentity == null) {
         return Optional.empty();
      } else {
         p_220480_1_.setGrowingAge(6000);
         p_220480_2_.setGrowingAge(6000);
         villagerentity.setGrowingAge(-24000);
         villagerentity.setLocationAndAngles(p_220480_1_.posX, p_220480_1_.posY, p_220480_1_.posZ, 0.0F, 0.0F);
         p_220480_1_.world.func_217376_c(villagerentity);
         p_220480_1_.world.setEntityState(villagerentity, (byte)12);
         return Optional.of(villagerentity);
      }
   }

   private void func_220477_a(ServerWorld p_220477_1_, VillagerEntity p_220477_2_, BlockPos p_220477_3_) {
      GlobalPos globalpos = GlobalPos.of(p_220477_1_.getDimension().getType(), p_220477_3_);
      p_220477_2_.getBrain().setMemory(MemoryModuleType.field_220941_b, globalpos);
   }
}