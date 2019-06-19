package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorld;

public class WalkToTargetTask extends Task<MobEntity> {
   @Nullable
   private Path field_220488_a;
   @Nullable
   private BlockPos field_220489_b;
   private float field_220490_c;
   private int field_220491_d;

   public WalkToTargetTask(int p_i50356_1_) {
      super(ImmutableMap.of(MemoryModuleType.field_220954_o, MemoryModuleStatus.VALUE_ABSENT, MemoryModuleType.field_220950_k, MemoryModuleStatus.VALUE_PRESENT), p_i50356_1_);
   }

   protected boolean shouldExecute(ServerWorld worldIn, MobEntity owner) {
      Brain<?> brain = owner.getBrain();
      WalkTarget walktarget = brain.getMemory(MemoryModuleType.field_220950_k).get();
      if (!this.func_220486_b(owner, walktarget) && this.func_220487_a(owner, walktarget, worldIn.getGameTime())) {
         this.field_220489_b = walktarget.func_220966_a().func_220608_a();
         return true;
      } else {
         brain.removeMemory(MemoryModuleType.field_220950_k);
         return false;
      }
   }

   protected boolean shouldContinueExecuting(ServerWorld p_212834_1_, MobEntity p_212834_2_, long p_212834_3_) {
      if (this.field_220488_a != null && this.field_220489_b != null) {
         Optional<WalkTarget> optional = p_212834_2_.getBrain().getMemory(MemoryModuleType.field_220950_k);
         PathNavigator pathnavigator = p_212834_2_.getNavigator();
         return !pathnavigator.noPath() && optional.isPresent() && !this.func_220486_b(p_212834_2_, optional.get());
      } else {
         return false;
      }
   }

   protected void resetTask(ServerWorld p_212835_1_, MobEntity p_212835_2_, long p_212835_3_) {
      p_212835_2_.getNavigator().clearPath();
      p_212835_2_.getBrain().removeMemory(MemoryModuleType.field_220950_k);
      p_212835_2_.getBrain().removeMemory(MemoryModuleType.field_220954_o);
      this.field_220488_a = null;
   }

   protected void startExecuting(ServerWorld p_212831_1_, MobEntity p_212831_2_, long p_212831_3_) {
      p_212831_2_.getBrain().setMemory(MemoryModuleType.field_220954_o, this.field_220488_a);
      p_212831_2_.getNavigator().setPath(this.field_220488_a, (double)this.field_220490_c);
      this.field_220491_d = p_212831_1_.getRandom().nextInt(10);
   }

   protected void updateTask(ServerWorld p_212833_1_, MobEntity p_212833_2_, long p_212833_3_) {
      --this.field_220491_d;
      if (this.field_220491_d <= 0) {
         Path path = p_212833_2_.getNavigator().getPath();
         Brain<?> brain = p_212833_2_.getBrain();
         if (this.field_220488_a != path) {
            this.field_220488_a = path;
            brain.setMemory(MemoryModuleType.field_220954_o, path);
         }

         if (path != null && this.field_220489_b != null) {
            WalkTarget walktarget = brain.getMemory(MemoryModuleType.field_220950_k).get();
            if (walktarget.func_220966_a().func_220608_a().distanceSq(this.field_220489_b) > 4.0D && this.func_220487_a(p_212833_2_, walktarget, p_212833_1_.getGameTime())) {
               this.field_220489_b = walktarget.func_220966_a().func_220608_a();
               this.startExecuting(p_212833_1_, p_212833_2_, p_212833_3_);
            }

         }
      }
   }

   private boolean func_220487_a(MobEntity p_220487_1_, WalkTarget p_220487_2_, long p_220487_3_) {
      BlockPos blockpos = p_220487_2_.func_220966_a().func_220608_a();
      this.field_220488_a = p_220487_1_.getNavigator().getPathToPos(blockpos);
      this.field_220490_c = p_220487_2_.func_220965_b();
      if (!this.func_220486_b(p_220487_1_, p_220487_2_)) {
         Brain<?> brain = p_220487_1_.getBrain();
         boolean flag = this.field_220488_a != null && this.field_220488_a.func_222862_a(p_220487_2_.func_220966_a().func_220608_a());
         if (flag) {
            brain.setMemory(MemoryModuleType.field_223021_x, Optional.empty());
         } else if (!brain.hasMemory(MemoryModuleType.field_223021_x)) {
            brain.setMemory(MemoryModuleType.field_223021_x, p_220487_3_);
         }

         if (this.field_220488_a != null) {
            return true;
         }

         Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards((CreatureEntity)p_220487_1_, 10, 7, new Vec3d(blockpos));
         if (vec3d != null) {
            this.field_220488_a = p_220487_1_.getNavigator().getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
            return this.field_220488_a != null;
         }
      }

      return false;
   }

   private boolean func_220486_b(MobEntity p_220486_1_, WalkTarget p_220486_2_) {
      return p_220486_2_.func_220966_a().func_220608_a().manhattanDistance(new BlockPos(p_220486_1_)) <= p_220486_2_.func_220964_c();
   }
}