package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.world.ServerWorld;

public class WakeUpTask extends Task<LivingEntity> {
   public WakeUpTask() {
      super(ImmutableMap.of());
   }

   protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
      return !owner.getBrain().func_218214_c(Activity.REST) && owner.isPlayerSleeping();
   }

   protected void startExecuting(ServerWorld p_212831_1_, LivingEntity p_212831_2_, long p_212831_3_) {
      p_212831_2_.func_213366_dy();
   }
}