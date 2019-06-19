package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.ServerWorld;

public class SleepAtHomeTask extends Task<LivingEntity> {
   private long field_220552_a;

   public SleepAtHomeTask() {
      super(ImmutableMap.of(MemoryModuleType.field_220941_b, MemoryModuleStatus.VALUE_PRESENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
      if (owner.isPassenger()) {
         return false;
      } else {
         GlobalPos globalpos = owner.getBrain().getMemory(MemoryModuleType.field_220941_b).get();
         if (!Objects.equals(worldIn.getDimension().getType(), globalpos.func_218177_a())) {
            return false;
         } else {
            BlockState blockstate = worldIn.getBlockState(globalpos.func_218180_b());
            return globalpos.func_218180_b().func_218137_a(owner.getPositionVec(), 2.0D) && blockstate.getBlock().isIn(BlockTags.field_219747_F) && !blockstate.get(BedBlock.OCCUPIED);
         }
      }
   }

   protected boolean shouldContinueExecuting(ServerWorld p_212834_1_, LivingEntity p_212834_2_, long p_212834_3_) {
      Optional<GlobalPos> optional = p_212834_2_.getBrain().getMemory(MemoryModuleType.field_220941_b);
      if (!optional.isPresent()) {
         return false;
      } else {
         BlockPos blockpos = optional.get().func_218180_b();
         return p_212834_2_.getBrain().func_218214_c(Activity.REST) && p_212834_2_.posY > (double)blockpos.getY() + 0.4D && blockpos.func_218137_a(p_212834_2_.getPositionVec(), 1.14D);
      }
   }

   protected void startExecuting(ServerWorld p_212831_1_, LivingEntity p_212831_2_, long p_212831_3_) {
      if (p_212831_3_ > this.field_220552_a) {
         p_212831_2_.func_213342_e(p_212831_2_.getBrain().getMemory(MemoryModuleType.field_220941_b).get().func_218180_b());
      }

   }

   protected boolean isTimedOut(long p_220383_1_) {
      return false;
   }

   protected void resetTask(ServerWorld p_212835_1_, LivingEntity p_212835_2_, long p_212835_3_) {
      if (p_212835_2_.isPlayerSleeping()) {
         p_212835_2_.func_213366_dy();
         this.field_220552_a = p_212835_3_ + 40L;
      }

   }
}