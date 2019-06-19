package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.ServerWorld;

public class HurtBySensor extends Sensor<LivingEntity> {
   protected void update(ServerWorld p_212872_1_, LivingEntity p_212872_2_) {
      Brain<?> brain = p_212872_2_.getBrain();
      if (p_212872_2_.getLastDamageSource() != null) {
         brain.setMemory(MemoryModuleType.field_220957_r, p_212872_2_.getLastDamageSource());
         Entity entity = brain.getMemory(MemoryModuleType.field_220957_r).get().getTrueSource();
         if (entity instanceof LivingEntity) {
            brain.setMemory(MemoryModuleType.field_220958_s, (LivingEntity)entity);
         }
      } else {
         brain.removeMemory(MemoryModuleType.field_220957_r);
      }

   }

   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.field_220957_r, MemoryModuleType.field_220958_s);
   }
}