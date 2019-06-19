package net.minecraft.entity.ai.brain.memory;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPosWrapper;
import net.minecraft.util.math.IPosWrapper;
import net.minecraft.util.math.Vec3d;

public class WalkTarget {
   private final IPosWrapper field_220967_a;
   private final float speed;
   private final int field_220969_c;

   public WalkTarget(BlockPos p_i50302_1_, float p_i50302_2_, int p_i50302_3_) {
      this(new BlockPosWrapper(p_i50302_1_), p_i50302_2_, p_i50302_3_);
   }

   public WalkTarget(Vec3d p_i50303_1_, float p_i50303_2_, int p_i50303_3_) {
      this(new BlockPosWrapper(new BlockPos(p_i50303_1_)), p_i50303_2_, p_i50303_3_);
   }

   public WalkTarget(IPosWrapper p_i50304_1_, float p_i50304_2_, int p_i50304_3_) {
      this.field_220967_a = p_i50304_1_;
      this.speed = p_i50304_2_;
      this.field_220969_c = p_i50304_3_;
   }

   public IPosWrapper func_220966_a() {
      return this.field_220967_a;
   }

   public float func_220965_b() {
      return this.speed;
   }

   public int func_220964_c() {
      return this.field_220969_c;
   }
}