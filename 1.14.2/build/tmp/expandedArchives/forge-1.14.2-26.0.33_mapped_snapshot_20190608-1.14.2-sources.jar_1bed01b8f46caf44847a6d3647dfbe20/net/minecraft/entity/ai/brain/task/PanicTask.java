package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.world.ServerWorld;

public class PanicTask extends Task<LivingEntity> {
   public PanicTask() {
      super(ImmutableMap.of());
   }

   protected void startExecuting(ServerWorld p_212831_1_, LivingEntity p_212831_2_, long p_212831_3_) {
      if (func_220512_b(p_212831_2_) || func_220513_a(p_212831_2_)) {
         Brain<?> brain = p_212831_2_.getBrain();
         if (!brain.func_218214_c(Activity.PANIC)) {
            brain.removeMemory(MemoryModuleType.field_220954_o);
            brain.removeMemory(MemoryModuleType.field_220950_k);
            brain.removeMemory(MemoryModuleType.field_220951_l);
            brain.removeMemory(MemoryModuleType.field_220953_n);
            brain.removeMemory(MemoryModuleType.field_220952_m);
         }

         brain.switchTo(Activity.PANIC);
      }

   }

   public static boolean func_220513_a(LivingEntity p_220513_0_) {
      return p_220513_0_.getBrain().hasMemory(MemoryModuleType.field_220959_t);
   }

   public static boolean func_220512_b(LivingEntity p_220512_0_) {
      return p_220512_0_.getBrain().hasMemory(MemoryModuleType.field_220957_r);
   }
}