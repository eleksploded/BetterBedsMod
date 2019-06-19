package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;

public class JumpOnBedTask extends Task<MobEntity> {
   private final float field_220470_a;
   @Nullable
   private BlockPos field_220471_b;
   private int field_220472_c;
   private int field_220473_d;
   private int field_220474_e;

   public JumpOnBedTask(float p_i50362_1_) {
      super(ImmutableMap.of(MemoryModuleType.field_220956_q, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_220950_k, MemoryModuleStatus.VALUE_ABSENT));
      this.field_220470_a = p_i50362_1_;
   }

   protected boolean shouldExecute(ServerWorld worldIn, MobEntity owner) {
      return owner.isChild() && this.func_220469_b(worldIn, owner);
   }

   protected void startExecuting(ServerWorld p_212831_1_, MobEntity p_212831_2_, long p_212831_3_) {
      super.startExecuting(p_212831_1_, p_212831_2_, p_212831_3_);
      this.func_220463_a(p_212831_2_).ifPresent((p_220461_3_) -> {
         this.field_220471_b = p_220461_3_;
         this.field_220472_c = 100;
         this.field_220473_d = 3 + p_212831_1_.rand.nextInt(4);
         this.field_220474_e = 0;
         this.func_220467_a(p_212831_2_, p_220461_3_);
      });
   }

   protected void resetTask(ServerWorld p_212835_1_, MobEntity p_212835_2_, long p_212835_3_) {
      super.resetTask(p_212835_1_, p_212835_2_, p_212835_3_);
      this.field_220471_b = null;
      this.field_220472_c = 0;
      this.field_220473_d = 0;
      this.field_220474_e = 0;
   }

   protected boolean shouldContinueExecuting(ServerWorld p_212834_1_, MobEntity p_212834_2_, long p_212834_3_) {
      return p_212834_2_.isChild() && this.field_220471_b != null && this.func_220466_a(p_212834_1_, this.field_220471_b) && !this.func_220464_e(p_212834_1_, p_212834_2_) && !this.func_220462_f(p_212834_1_, p_212834_2_);
   }

   protected boolean isTimedOut(long p_220383_1_) {
      return false;
   }

   protected void updateTask(ServerWorld p_212833_1_, MobEntity p_212833_2_, long p_212833_3_) {
      if (!this.func_220468_c(p_212833_1_, p_212833_2_)) {
         --this.field_220472_c;
      } else if (this.field_220474_e > 0) {
         --this.field_220474_e;
      } else {
         if (this.func_220465_d(p_212833_1_, p_212833_2_)) {
            p_212833_2_.getJumpHelper().setJumping();
            --this.field_220473_d;
            this.field_220474_e = 5;
         }

      }
   }

   private void func_220467_a(MobEntity p_220467_1_, BlockPos p_220467_2_) {
      p_220467_1_.getBrain().setMemory(MemoryModuleType.field_220950_k, new WalkTarget(p_220467_2_, this.field_220470_a, 0));
   }

   private boolean func_220469_b(ServerWorld p_220469_1_, MobEntity p_220469_2_) {
      return this.func_220468_c(p_220469_1_, p_220469_2_) || this.func_220463_a(p_220469_2_).isPresent();
   }

   private boolean func_220468_c(ServerWorld p_220468_1_, MobEntity p_220468_2_) {
      BlockPos blockpos = new BlockPos(p_220468_2_);
      BlockPos blockpos1 = blockpos.down();
      return this.func_220466_a(p_220468_1_, blockpos) || this.func_220466_a(p_220468_1_, blockpos1);
   }

   private boolean func_220465_d(ServerWorld p_220465_1_, MobEntity p_220465_2_) {
      return this.func_220466_a(p_220465_1_, new BlockPos(p_220465_2_));
   }

   private boolean func_220466_a(ServerWorld p_220466_1_, BlockPos p_220466_2_) {
      return p_220466_1_.getBlockState(p_220466_2_).isIn(BlockTags.field_219747_F);
   }

   private Optional<BlockPos> func_220463_a(MobEntity p_220463_1_) {
      return p_220463_1_.getBrain().getMemory(MemoryModuleType.field_220956_q);
   }

   private boolean func_220464_e(ServerWorld p_220464_1_, MobEntity p_220464_2_) {
      return !this.func_220468_c(p_220464_1_, p_220464_2_) && this.field_220472_c <= 0;
   }

   private boolean func_220462_f(ServerWorld p_220462_1_, MobEntity p_220462_2_) {
      return this.func_220468_c(p_220462_1_, p_220462_2_) && this.field_220473_d <= 0;
   }
}