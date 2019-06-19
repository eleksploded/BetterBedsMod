package net.minecraft.world.lighting;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class LightEngine<M extends LightDataMap<M>, S extends SectionLightStorage<M>> extends LevelBasedGraph implements IWorldLightListener {
   private static final Direction[] field_215628_d = Direction.values();
   protected final IChunkLightProvider field_215625_a;
   protected final LightType field_215626_b;
   protected final S field_215627_c;
   private boolean field_215629_e;
   private final BlockPos.MutableBlockPos field_215630_f = new BlockPos.MutableBlockPos();
   private final long[] field_215631_g = new long[2];
   private final IBlockReader[] field_215632_h = new IBlockReader[2];

   public LightEngine(IChunkLightProvider p_i51296_1_, LightType p_i51296_2_, S p_i51296_3_) {
      super(16, 256, 8192);
      this.field_215625_a = p_i51296_1_;
      this.field_215626_b = p_i51296_2_;
      this.field_215627_c = p_i51296_3_;
      this.func_215624_c();
   }

   protected void func_215473_f(long p_215473_1_) {
      this.field_215627_c.func_215532_c();
      if (this.field_215627_c.func_215518_g(SectionPos.func_218162_e(p_215473_1_))) {
         super.func_215473_f(p_215473_1_);
      }

   }

   @Nullable
   private IBlockReader func_215615_a(int p_215615_1_, int p_215615_2_) {
      long i = ChunkPos.asLong(p_215615_1_, p_215615_2_);

      for(int j = 0; j < 2; ++j) {
         if (i == this.field_215631_g[j]) {
            return this.field_215632_h[j];
         }
      }

      IBlockReader iblockreader = this.field_215625_a.func_217202_b(p_215615_1_, p_215615_2_);

      for(int k = 1; k > 0; --k) {
         this.field_215631_g[k] = this.field_215631_g[k - 1];
         this.field_215632_h[k] = this.field_215632_h[k - 1];
      }

      this.field_215631_g[0] = i;
      this.field_215632_h[0] = iblockreader;
      return iblockreader;
   }

   private void func_215624_c() {
      Arrays.fill(this.field_215631_g, ChunkPos.field_222244_a);
      Arrays.fill(this.field_215632_h, (Object)null);
   }

   protected VoxelShape func_215618_a(long p_215618_1_, @Nullable AtomicInteger p_215618_3_) {
      if (p_215618_1_ == Long.MAX_VALUE) {
         if (p_215618_3_ != null) {
            p_215618_3_.set(0);
         }

         return VoxelShapes.empty();
      } else {
         int i = SectionPos.toChunk(BlockPos.unpackX(p_215618_1_));
         int j = SectionPos.toChunk(BlockPos.unpackZ(p_215618_1_));
         IBlockReader iblockreader = this.func_215615_a(i, j);
         if (iblockreader == null) {
            if (p_215618_3_ != null) {
               p_215618_3_.set(16);
            }

            return VoxelShapes.fullCube();
         } else {
            this.field_215630_f.func_218294_g(p_215618_1_);
            BlockState blockstate = iblockreader.getBlockState(this.field_215630_f);
            boolean flag = blockstate.isSolid() && blockstate.func_215691_g();
            if (p_215618_3_ != null) {
               p_215618_3_.set(blockstate.getOpacity(this.field_215625_a.func_212864_k_(), this.field_215630_f));
            }

            return flag ? blockstate.getRenderShape(this.field_215625_a.func_212864_k_(), this.field_215630_f) : VoxelShapes.empty();
         }
      }
   }

   public static int func_215613_a(IBlockReader p_215613_0_, BlockState p_215613_1_, BlockPos p_215613_2_, BlockState p_215613_3_, BlockPos p_215613_4_, Direction p_215613_5_, int p_215613_6_) {
      boolean flag = p_215613_1_.isSolid() && p_215613_1_.func_215691_g();
      boolean flag1 = p_215613_3_.isSolid() && p_215613_3_.func_215691_g();
      if (!flag && !flag1) {
         return p_215613_6_;
      } else {
         VoxelShape voxelshape = flag ? p_215613_1_.getRenderShape(p_215613_0_, p_215613_2_) : VoxelShapes.empty();
         VoxelShape voxelshape1 = flag1 ? p_215613_3_.getRenderShape(p_215613_0_, p_215613_4_) : VoxelShapes.empty();
         return VoxelShapes.doAdjacentCubeSidesFillSquare(voxelshape, voxelshape1, p_215613_5_) ? 16 : p_215613_6_;
      }
   }

   protected boolean func_215485_a(long p_215485_1_) {
      return p_215485_1_ == Long.MAX_VALUE;
   }

   protected int func_215477_a(long p_215477_1_, long p_215477_3_, int p_215477_5_) {
      return 0;
   }

   protected int func_215471_c(long p_215471_1_) {
      return p_215471_1_ == Long.MAX_VALUE ? 0 : 15 - this.field_215627_c.func_215521_h(p_215471_1_);
   }

   protected int func_215622_a(NibbleArray p_215622_1_, long p_215622_2_) {
      return 15 - p_215622_1_.get(SectionPos.func_218171_b(BlockPos.unpackX(p_215622_2_)), SectionPos.func_218171_b(BlockPos.unpackY(p_215622_2_)), SectionPos.func_218171_b(BlockPos.unpackZ(p_215622_2_)));
   }

   protected void func_215476_a(long p_215476_1_, int p_215476_3_) {
      this.field_215627_c.func_215517_b(p_215476_1_, Math.min(15, 15 - p_215476_3_));
   }

   protected int func_215480_b(long p_215480_1_, long p_215480_3_, int p_215480_5_) {
      return 0;
   }

   public boolean func_215619_a() {
      return this.func_215481_b() || this.field_215627_c.func_215481_b() || this.field_215627_c.func_215527_a();
   }

   public int func_215616_a(int p_215616_1_, boolean p_215616_2_, boolean p_215616_3_) {
      if (!this.field_215629_e) {
         if (this.field_215627_c.func_215481_b()) {
            p_215616_1_ = this.field_215627_c.func_215483_b(p_215616_1_);
            if (p_215616_1_ == 0) {
               return p_215616_1_;
            }
         }

         this.field_215627_c.func_215522_a(this, p_215616_2_, p_215616_3_);
      }

      this.field_215629_e = true;
      if (this.func_215481_b()) {
         p_215616_1_ = this.func_215483_b(p_215616_1_);
         this.func_215624_c();
         if (p_215616_1_ == 0) {
            return p_215616_1_;
         }
      }

      this.field_215629_e = false;
      this.field_215627_c.func_215533_d();
      return p_215616_1_;
   }

   protected void func_215621_a(long p_215621_1_, @Nullable NibbleArray p_215621_3_) {
      this.field_215627_c.func_215529_a(p_215621_1_, p_215621_3_);
   }

   @Nullable
   public NibbleArray func_215612_a(SectionPos p_215612_1_) {
      return this.field_215627_c.func_222858_h(p_215612_1_.asLong());
   }

   public int func_215611_b(BlockPos p_215611_1_) {
      return this.field_215627_c.func_215525_d(p_215611_1_.toLong());
   }

   @OnlyIn(Dist.CLIENT)
   public String func_215614_b(long p_215614_1_) {
      return "" + this.field_215627_c.func_215471_c(p_215614_1_);
   }

   public void func_215617_a(BlockPos p_215617_1_) {
      long i = p_215617_1_.toLong();
      this.func_215473_f(i);

      for(Direction direction : field_215628_d) {
         this.func_215473_f(BlockPos.offset(i, direction));
      }

   }

   public void func_215623_a(BlockPos p_215623_1_, int p_215623_2_) {
   }

   public void func_215566_a(SectionPos p_215566_1_, boolean p_215566_2_) {
      this.field_215627_c.func_215519_c(p_215566_1_.asLong(), p_215566_2_);
   }

   public void func_215620_a(ChunkPos p_215620_1_, boolean p_215620_2_) {
      long i = SectionPos.func_218169_f(SectionPos.asLong(p_215620_1_.x, 0, p_215620_1_.z));
      this.field_215627_c.func_215532_c();
      this.field_215627_c.func_215526_b(i, p_215620_2_);
   }

   public void func_223129_b(ChunkPos p_223129_1_, boolean p_223129_2_) {
      long i = SectionPos.func_218169_f(SectionPos.asLong(p_223129_1_.x, 0, p_223129_1_.z));
      this.field_215627_c.func_223113_c(i, p_223129_2_);
   }
}