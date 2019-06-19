package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;

public class ExpireHidingTask extends Task<LivingEntity> {
   private final int field_220537_a;
   private final int field_220538_b;
   private int field_220539_c;

   public ExpireHidingTask(int p_i50349_1_, int p_i50349_2_) {
      super(ImmutableMap.of(MemoryModuleType.field_220961_v, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_220962_w, MemoryModuleStatus.VALUE_PRESENT));
      this.field_220538_b = p_i50349_1_ * 20;
      this.field_220539_c = 0;
      this.field_220537_a = p_i50349_2_;
   }

   protected void startExecuting(ServerWorld p_212831_1_, LivingEntity p_212831_2_, long p_212831_3_) {
      Brain<?> brain = p_212831_2_.getBrain();
      Optional<Long> optional = brain.getMemory(MemoryModuleType.field_220962_w);
      boolean flag = optional.get() + 300L <= p_212831_3_;
      if (this.field_220539_c <= this.field_220538_b && !flag) {
         BlockPos blockpos = brain.getMemory(MemoryModuleType.field_220961_v).get().func_218180_b();
         if (blockpos.func_218141_a(new BlockPos(p_212831_2_), (double)(this.field_220537_a + 1))) {
            ++this.field_220539_c;
         }

      } else {
         brain.removeMemory(MemoryModuleType.field_220962_w);
         brain.removeMemory(MemoryModuleType.field_220961_v);
         brain.updateActivity(p_212831_1_.getDayTime(), p_212831_1_.getGameTime());
         this.field_220539_c = 0;
      }
   }
}