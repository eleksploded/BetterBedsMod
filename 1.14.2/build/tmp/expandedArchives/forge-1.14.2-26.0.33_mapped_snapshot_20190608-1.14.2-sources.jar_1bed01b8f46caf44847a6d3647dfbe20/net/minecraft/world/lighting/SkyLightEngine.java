package net.minecraft.world.lighting;

import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class SkyLightEngine extends LightEngine<SkyLightStorage.StorageMap, SkyLightStorage> {
   private static final Direction[] field_215633_d = Direction.values();
   private static final Direction[] field_215634_e = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

   public SkyLightEngine(IChunkLightProvider p_i51289_1_) {
      super(p_i51289_1_, LightType.SKY, new SkyLightStorage(p_i51289_1_));
   }

   protected int func_215480_b(long p_215480_1_, long p_215480_3_, int p_215480_5_) {
      if (p_215480_3_ == Long.MAX_VALUE) {
         return 15;
      } else {
         if (p_215480_1_ == Long.MAX_VALUE) {
            if (!this.field_215627_c.func_215551_l(p_215480_3_)) {
               return 15;
            }

            p_215480_5_ = 0;
         }

         if (p_215480_5_ >= 15) {
            return p_215480_5_;
         } else {
            AtomicInteger atomicinteger = new AtomicInteger();
            VoxelShape voxelshape = this.func_215618_a(p_215480_3_, atomicinteger);
            if (atomicinteger.get() >= 15) {
               return 15;
            } else {
               int i = BlockPos.unpackX(p_215480_1_);
               int j = BlockPos.unpackY(p_215480_1_);
               int k = BlockPos.unpackZ(p_215480_1_);
               int l = BlockPos.unpackX(p_215480_3_);
               int i1 = BlockPos.unpackY(p_215480_3_);
               int j1 = BlockPos.unpackZ(p_215480_3_);
               boolean flag = i == l && k == j1;
               int k1 = Integer.signum(l - i);
               int l1 = Integer.signum(i1 - j);
               int i2 = Integer.signum(j1 - k);
               Direction direction;
               if (p_215480_1_ == Long.MAX_VALUE) {
                  direction = Direction.DOWN;
               } else {
                  direction = Direction.func_218383_a(k1, l1, i2);
               }

               VoxelShape voxelshape1 = this.func_215618_a(p_215480_1_, (AtomicInteger)null);
               if (direction != null) {
                  if (VoxelShapes.doAdjacentCubeSidesFillSquare(voxelshape1, voxelshape, direction)) {
                     return 15;
                  }
               } else {
                  if (VoxelShapes.doAdjacentCubeSidesFillSquare(voxelshape1, VoxelShapes.empty(), Direction.DOWN)) {
                     return 15;
                  }

                  int j2 = flag ? -1 : 0;
                  Direction direction1 = Direction.func_218383_a(k1, j2, i2);
                  if (direction1 == null) {
                     return 15;
                  }

                  if (VoxelShapes.doAdjacentCubeSidesFillSquare(VoxelShapes.empty(), voxelshape, direction1)) {
                     return 15;
                  }
               }

               boolean flag1 = p_215480_1_ == Long.MAX_VALUE || flag && j > i1;
               return flag1 && p_215480_5_ == 0 && atomicinteger.get() == 0 ? 0 : p_215480_5_ + Math.max(1, atomicinteger.get());
            }
         }
      }
   }

   protected void func_215478_a(long p_215478_1_, int p_215478_3_, boolean p_215478_4_) {
      long i = SectionPos.func_218162_e(p_215478_1_);
      int j = BlockPos.unpackY(p_215478_1_);
      int k = SectionPos.func_218171_b(j);
      int l = SectionPos.toChunk(j);
      int i1;
      if (k != 0) {
         i1 = 0;
      } else {
         int j1;
         for(j1 = 0; !this.field_215627_c.func_215518_g(SectionPos.withOffset(i, 0, -j1 - 1, 0)) && this.field_215627_c.func_215550_a(l - j1 - 1); ++j1) {
            ;
         }

         i1 = j1;
      }

      long i3 = BlockPos.offset(p_215478_1_, 0, -1 - i1 * 16, 0);
      long k1 = SectionPos.func_218162_e(i3);
      if (i == k1 || this.field_215627_c.func_215518_g(k1)) {
         this.func_215475_b(p_215478_1_, i3, p_215478_3_, p_215478_4_);
      }

      long l1 = BlockPos.offset(p_215478_1_, Direction.UP);
      long i2 = SectionPos.func_218162_e(l1);
      if (i == i2 || this.field_215627_c.func_215518_g(i2)) {
         this.func_215475_b(p_215478_1_, l1, p_215478_3_, p_215478_4_);
      }

      for(Direction direction : field_215634_e) {
         int j2 = 0;

         while(true) {
            long k2 = BlockPos.offset(p_215478_1_, direction.getXOffset(), -j2, direction.getZOffset());
            long l2 = SectionPos.func_218162_e(k2);
            if (i == l2) {
               this.func_215475_b(p_215478_1_, k2, p_215478_3_, p_215478_4_);
               break;
            }

            if (this.field_215627_c.func_215518_g(l2)) {
               this.func_215475_b(p_215478_1_, k2, p_215478_3_, p_215478_4_);
            }

            ++j2;
            if (j2 > i1 * 16) {
               break;
            }
         }
      }

   }

   protected int func_215477_a(long p_215477_1_, long p_215477_3_, int p_215477_5_) {
      int i = p_215477_5_;
      if (Long.MAX_VALUE != p_215477_3_) {
         int j = this.func_215480_b(Long.MAX_VALUE, p_215477_1_, 0);
         if (p_215477_5_ > j) {
            i = j;
         }

         if (i == 0) {
            return i;
         }
      }

      long j1 = SectionPos.func_218162_e(p_215477_1_);
      NibbleArray nibblearray = this.field_215627_c.func_215520_a(j1, true);

      for(Direction direction : field_215633_d) {
         long k = BlockPos.offset(p_215477_1_, direction);
         long l = SectionPos.func_218162_e(k);
         NibbleArray nibblearray1;
         if (j1 == l) {
            nibblearray1 = nibblearray;
         } else {
            nibblearray1 = this.field_215627_c.func_215520_a(l, true);
         }

         if (nibblearray1 != null) {
            if (k != p_215477_3_) {
               int k1 = this.func_215480_b(k, p_215477_1_, this.func_215622_a(nibblearray1, k));
               if (i > k1) {
                  i = k1;
               }

               if (i == 0) {
                  return i;
               }
            }
         } else if (direction != Direction.DOWN) {
            for(k = BlockPos.func_218288_f(k); !this.field_215627_c.func_215518_g(l) && !this.field_215627_c.func_215549_m(l); k = BlockPos.offset(k, 0, 16, 0)) {
               l = SectionPos.withOffset(l, Direction.UP);
            }

            NibbleArray nibblearray2 = this.field_215627_c.func_215520_a(l, true);
            if (k != p_215477_3_) {
               int i1;
               if (nibblearray2 != null) {
                  i1 = this.func_215480_b(k, p_215477_1_, this.func_215622_a(nibblearray2, k));
               } else {
                  i1 = this.field_215627_c.func_215548_n(l) ? 0 : 15;
               }

               if (i > i1) {
                  i = i1;
               }

               if (i == 0) {
                  return i;
               }
            }
         }
      }

      return i;
   }

   protected void func_215473_f(long p_215473_1_) {
      this.field_215627_c.func_215532_c();
      long i = SectionPos.func_218162_e(p_215473_1_);
      if (this.field_215627_c.func_215518_g(i)) {
         super.func_215473_f(p_215473_1_);
      } else {
         for(p_215473_1_ = BlockPos.func_218288_f(p_215473_1_); !this.field_215627_c.func_215518_g(i) && !this.field_215627_c.func_215549_m(i); p_215473_1_ = BlockPos.offset(p_215473_1_, 0, 16, 0)) {
            i = SectionPos.withOffset(i, Direction.UP);
         }

         if (this.field_215627_c.func_215518_g(i)) {
            super.func_215473_f(p_215473_1_);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public String func_215614_b(long p_215614_1_) {
      return super.func_215614_b(p_215614_1_) + (this.field_215627_c.func_215549_m(p_215614_1_) ? "*" : "");
   }
}