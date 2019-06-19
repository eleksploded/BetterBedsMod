package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.util.math.IPosWrapper;
import net.minecraft.world.ServerWorld;

public class WalkTowardsLookTargetTask extends Task<LivingEntity> {
   private final float field_220543_a;
   private final int field_220544_b;

   public WalkTowardsLookTargetTask(float p_i50344_1_, int p_i50344_2_) {
      super(ImmutableMap.of(MemoryModuleType.field_220950_k, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.field_220951_l, MemoryModuleStatus.VALUE_PRESENT));
      this.field_220543_a = p_i50344_1_;
      this.field_220544_b = p_i50344_2_;
   }

   protected void startExecuting(ServerWorld p_212831_1_, LivingEntity p_212831_2_, long p_212831_3_) {
      Brain<?> brain = p_212831_2_.getBrain();
      IPosWrapper iposwrapper = brain.getMemory(MemoryModuleType.field_220951_l).get();
      brain.setMemory(MemoryModuleType.field_220950_k, new WalkTarget(iposwrapper, this.field_220543_a, this.field_220544_b));
   }
}