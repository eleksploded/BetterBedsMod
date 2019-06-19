package net.minecraft.util.math;

import java.util.List;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;

public class EntityPosWrapper implements IPosWrapper {
   private final LivingEntity field_220611_a;

   public EntityPosWrapper(LivingEntity p_i50368_1_) {
      this.field_220611_a = p_i50368_1_;
   }

   public BlockPos func_220608_a() {
      return new BlockPos(this.field_220611_a);
   }

   public Vec3d func_220609_b() {
      return new Vec3d(this.field_220611_a.posX, this.field_220611_a.posY + (double)this.field_220611_a.getEyeHeight(), this.field_220611_a.posZ);
   }

   public boolean func_220610_a(LivingEntity p_220610_1_) {
      Optional<List<LivingEntity>> optional = p_220610_1_.getBrain().getMemory(MemoryModuleType.field_220946_g);
      return this.field_220611_a.isAlive() && optional.isPresent() && optional.get().contains(this.field_220611_a);
   }

   public String toString() {
      return "EntityPosWrapper for " + this.field_220611_a;
   }
}