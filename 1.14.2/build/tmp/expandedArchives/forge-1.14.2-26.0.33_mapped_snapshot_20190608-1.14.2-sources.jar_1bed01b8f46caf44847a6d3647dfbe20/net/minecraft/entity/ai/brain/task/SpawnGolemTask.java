package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.ServerWorld;

public class SpawnGolemTask extends Task<VillagerEntity> {
   private int field_220600_a;
   private boolean field_220601_b;

   public SpawnGolemTask() {
      super(ImmutableMap.of(MemoryModuleType.field_220942_c, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_220951_l, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_220960_u, MemoryModuleStatus.REGISTERED));
   }

   protected boolean shouldExecute(ServerWorld worldIn, VillagerEntity owner) {
      return this.func_220599_a(worldIn.getDayTime() % 24000L, owner.func_213763_er());
   }

   protected void resetTask(ServerWorld p_212835_1_, VillagerEntity p_212835_2_, long p_212835_3_) {
      this.field_220601_b = false;
      this.field_220600_a = 0;
      p_212835_2_.getBrain().removeMemory(MemoryModuleType.field_220951_l);
   }

   protected void updateTask(ServerWorld p_212833_1_, VillagerEntity p_212833_2_, long p_212833_3_) {
      Brain<VillagerEntity> brain = p_212833_2_.getBrain();
      VillagerEntity.GolemStatus villagerentity$golemstatus = brain.getMemory(MemoryModuleType.field_220960_u).orElseGet(VillagerEntity.GolemStatus::new);
      villagerentity$golemstatus.func_221143_a(p_212833_3_);
      brain.setMemory(MemoryModuleType.field_220960_u, villagerentity$golemstatus);
      if (!this.field_220601_b) {
         p_212833_2_.func_213766_ei();
         this.field_220601_b = true;
         p_212833_2_.func_213767_ej();
         brain.getMemory(MemoryModuleType.field_220942_c).ifPresent((p_220598_1_) -> {
            brain.setMemory(MemoryModuleType.field_220951_l, new BlockPosWrapper(p_220598_1_.func_218180_b()));
         });
      }

      ++this.field_220600_a;
   }

   protected boolean shouldContinueExecuting(ServerWorld p_212834_1_, VillagerEntity p_212834_2_, long p_212834_3_) {
      Optional<GlobalPos> optional = p_212834_2_.getBrain().getMemory(MemoryModuleType.field_220942_c);
      if (!optional.isPresent()) {
         return false;
      } else {
         GlobalPos globalpos = optional.get();
         return this.field_220600_a < 100 && Objects.equals(globalpos.func_218177_a(), p_212834_1_.getDimension().getType()) && globalpos.func_218180_b().func_218137_a(p_212834_2_.getPositionVec(), 1.73D);
      }
   }

   private boolean func_220599_a(long p_220599_1_, long p_220599_3_) {
      return p_220599_3_ == 0L || p_220599_1_ < p_220599_3_ || p_220599_1_ > p_220599_3_ + 3500L;
   }
}