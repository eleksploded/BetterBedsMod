package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.ServerWorld;

public class FindHidingPlaceTask extends Task<LivingEntity> {
   private final float field_220457_a;
   private final int field_220458_b;
   private final int field_220459_c;
   private Optional<BlockPos> field_220460_d = Optional.empty();

   public FindHidingPlaceTask(int p_i50361_1_, float p_i50361_2_, int p_i50361_3_) {
      super(ImmutableMap.of(MemoryModuleType.field_220950_k, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.field_220941_b, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_220961_v, MemoryModuleStatus.REGISTERED));
      this.field_220458_b = p_i50361_1_;
      this.field_220457_a = p_i50361_2_;
      this.field_220459_c = p_i50361_3_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
      Optional<BlockPos> optional = worldIn.func_217443_B().func_219127_a((p_220454_0_) -> {
         return p_220454_0_ == PointOfInterestType.field_221069_q;
      }, (p_220456_0_) -> {
         return true;
      }, new BlockPos(owner), this.field_220459_c + 1, PointOfInterestManager.Status.ANY);
      if (optional.isPresent() && optional.get().func_218137_a(owner.getPositionVec(), (double)this.field_220459_c)) {
         this.field_220460_d = optional;
      } else {
         this.field_220460_d = Optional.empty();
      }

      return true;
   }

   protected void startExecuting(ServerWorld p_212831_1_, LivingEntity p_212831_2_, long p_212831_3_) {
      Brain<?> brain = p_212831_2_.getBrain();
      Optional<BlockPos> optional = this.field_220460_d;
      if (!optional.isPresent()) {
         optional = p_212831_1_.func_217443_B().func_219163_a((p_220453_0_) -> {
            return p_220453_0_ == PointOfInterestType.field_221069_q;
         }, (p_220455_0_) -> {
            return true;
         }, PointOfInterestManager.Status.ANY, new BlockPos(p_212831_2_), this.field_220458_b, p_212831_2_.getRNG());
         if (!optional.isPresent()) {
            Optional<GlobalPos> optional1 = brain.getMemory(MemoryModuleType.field_220941_b);
            if (optional1.isPresent()) {
               optional = Optional.of(optional1.get().func_218180_b());
            }
         }
      }

      if (optional.isPresent()) {
         brain.removeMemory(MemoryModuleType.field_220954_o);
         brain.removeMemory(MemoryModuleType.field_220951_l);
         brain.removeMemory(MemoryModuleType.field_220953_n);
         brain.removeMemory(MemoryModuleType.field_220952_m);
         brain.setMemory(MemoryModuleType.field_220961_v, GlobalPos.of(p_212831_1_.getDimension().getType(), optional.get()));
         if (!optional.get().func_218137_a(p_212831_2_.getPositionVec(), (double)this.field_220459_c)) {
            brain.setMemory(MemoryModuleType.field_220950_k, new WalkTarget(optional.get(), this.field_220457_a, this.field_220459_c));
         }
      }

   }
}