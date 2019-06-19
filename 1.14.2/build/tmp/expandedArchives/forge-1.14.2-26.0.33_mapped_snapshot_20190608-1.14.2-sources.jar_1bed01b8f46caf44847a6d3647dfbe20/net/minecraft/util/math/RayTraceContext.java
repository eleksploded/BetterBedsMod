package net.minecraft.util.math;

import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class RayTraceContext {
   private final Vec3d field_222254_a;
   private final Vec3d field_222255_b;
   private final RayTraceContext.BlockMode field_222256_c;
   private final RayTraceContext.FluidMode field_222257_d;
   private final ISelectionContext field_222258_e;

   public RayTraceContext(Vec3d p_i50009_1_, Vec3d p_i50009_2_, RayTraceContext.BlockMode p_i50009_3_, RayTraceContext.FluidMode p_i50009_4_, Entity p_i50009_5_) {
      this.field_222254_a = p_i50009_1_;
      this.field_222255_b = p_i50009_2_;
      this.field_222256_c = p_i50009_3_;
      this.field_222257_d = p_i50009_4_;
      this.field_222258_e = ISelectionContext.forEntity(p_i50009_5_);
   }

   public Vec3d func_222250_a() {
      return this.field_222255_b;
   }

   public Vec3d func_222253_b() {
      return this.field_222254_a;
   }

   public VoxelShape func_222251_a(BlockState p_222251_1_, IBlockReader p_222251_2_, BlockPos p_222251_3_) {
      return this.field_222256_c.get(p_222251_1_, p_222251_2_, p_222251_3_, this.field_222258_e);
   }

   public VoxelShape func_222252_a(IFluidState p_222252_1_, IBlockReader p_222252_2_, BlockPos p_222252_3_) {
      return this.field_222257_d.func_222248_a(p_222252_1_) ? p_222252_1_.func_215676_d(p_222252_2_, p_222252_3_) : VoxelShapes.empty();
   }

   public static enum BlockMode implements RayTraceContext.IVoxelProvider {
      COLLIDER(BlockState::getCollisionShape),
      OUTLINE(BlockState::getShape);

      private final RayTraceContext.IVoxelProvider field_222245_c;

      private BlockMode(RayTraceContext.IVoxelProvider p_i49926_3_) {
         this.field_222245_c = p_i49926_3_;
      }

      public VoxelShape get(BlockState p_get_1_, IBlockReader p_get_2_, BlockPos p_get_3_, ISelectionContext p_get_4_) {
         return this.field_222245_c.get(p_get_1_, p_get_2_, p_get_3_, p_get_4_);
      }
   }

   public static enum FluidMode {
      NONE((p_222247_0_) -> {
         return false;
      }),
      SOURCE_ONLY(IFluidState::isSource),
      ANY((p_222246_0_) -> {
         return !p_222246_0_.isEmpty();
      });

      private final Predicate<IFluidState> field_222249_d;

      private FluidMode(Predicate<IFluidState> p_i49923_3_) {
         this.field_222249_d = p_i49923_3_;
      }

      public boolean func_222248_a(IFluidState p_222248_1_) {
         return this.field_222249_d.test(p_222248_1_);
      }
   }

   public interface IVoxelProvider {
      VoxelShape get(BlockState p_get_1_, IBlockReader p_get_2_, BlockPos p_get_3_, ISelectionContext p_get_4_);
   }
}