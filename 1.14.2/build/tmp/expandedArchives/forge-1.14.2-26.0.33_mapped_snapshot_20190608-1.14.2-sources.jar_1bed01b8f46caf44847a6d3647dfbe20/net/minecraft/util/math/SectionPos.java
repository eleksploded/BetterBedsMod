package net.minecraft.util.math;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;

public class SectionPos extends Vec3i {
   private SectionPos(int p_i50794_1_, int p_i50794_2_, int p_i50794_3_) {
      super(p_i50794_1_, p_i50794_2_, p_i50794_3_);
   }

   public static SectionPos of(int p_218154_0_, int p_218154_1_, int p_218154_2_) {
      return new SectionPos(p_218154_0_, p_218154_1_, p_218154_2_);
   }

   public static SectionPos from(BlockPos p_218167_0_) {
      return new SectionPos(toChunk(p_218167_0_.getX()), toChunk(p_218167_0_.getY()), toChunk(p_218167_0_.getZ()));
   }

   public static SectionPos from(ChunkPos p_218156_0_, int y) {
      return new SectionPos(p_218156_0_.x, y, p_218156_0_.z);
   }

   public static SectionPos from(Entity p_218157_0_) {
      return new SectionPos(toChunk(MathHelper.floor(p_218157_0_.posX)), toChunk(MathHelper.floor(p_218157_0_.posY)), toChunk(MathHelper.floor(p_218157_0_.posZ)));
   }

   public static SectionPos from(long p_218170_0_) {
      return new SectionPos(extractX(p_218170_0_), extractY(p_218170_0_), extractZ(p_218170_0_));
   }

   public static long withOffset(long p_218172_0_, Direction p_218172_2_) {
      return withOffset(p_218172_0_, p_218172_2_.getXOffset(), p_218172_2_.getYOffset(), p_218172_2_.getZOffset());
   }

   public static long withOffset(long p_218174_0_, int p_218174_2_, int p_218174_3_, int p_218174_4_) {
      return asLong(extractX(p_218174_0_) + p_218174_2_, extractY(p_218174_0_) + p_218174_3_, extractZ(p_218174_0_) + p_218174_4_);
   }

   public static int toChunk(int worldCoord) {
      return worldCoord >> 4;
   }

   public static int func_218171_b(int p_218171_0_) {
      return p_218171_0_ & 15;
   }

   public static short toRelativeOffset(BlockPos p_218150_0_) {
      int i = func_218171_b(p_218150_0_.getX());
      int j = func_218171_b(p_218150_0_.getY());
      int k = func_218171_b(p_218150_0_.getZ());
      return (short)(i << 8 | k << 4 | j);
   }

   public static int toWorld(int chunkCoord) {
      return chunkCoord << 4;
   }

   public static int extractX(long packed) {
      return (int)(packed << 0 >> 42);
   }

   public static int extractY(long packed) {
      return (int)(packed << 44 >> 44);
   }

   public static int extractZ(long packed) {
      return (int)(packed << 22 >> 42);
   }

   public int func_218149_a() {
      return this.getX();
   }

   public int func_218163_b() {
      return this.getY();
   }

   public int func_218148_c() {
      return this.getZ();
   }

   public int getWorldStartX() {
      return this.func_218149_a() << 4;
   }

   public int getWorldStartY() {
      return this.func_218163_b() << 4;
   }

   public int getWorldStartZ() {
      return this.func_218148_c() << 4;
   }

   public int getWorldEndX() {
      return (this.func_218149_a() << 4) + 15;
   }

   public int getWorldEndY() {
      return (this.func_218163_b() << 4) + 15;
   }

   public int getWorldEndZ() {
      return (this.func_218148_c() << 4) + 15;
   }

   public static long func_218162_e(long p_218162_0_) {
      return asLong(toChunk(BlockPos.unpackX(p_218162_0_)), toChunk(BlockPos.unpackY(p_218162_0_)), toChunk(BlockPos.unpackZ(p_218162_0_)));
   }

   public static long func_218169_f(long p_218169_0_) {
      return p_218169_0_ & -1048576L;
   }

   public BlockPos asBlockPos() {
      return new BlockPos(toWorld(this.func_218149_a()), toWorld(this.func_218163_b()), toWorld(this.func_218148_c()));
   }

   public BlockPos getCenter() {
      int i = 8;
      return this.asBlockPos().add(8, 8, 8);
   }

   public ChunkPos asChunkPos() {
      return new ChunkPos(this.func_218149_a(), this.func_218148_c());
   }

   public static long asLong(int p_218166_0_, int p_218166_1_, int p_218166_2_) {
      long i = 0L;
      i = i | ((long)p_218166_0_ & 4194303L) << 42;
      i = i | ((long)p_218166_1_ & 1048575L) << 0;
      i = i | ((long)p_218166_2_ & 4194303L) << 20;
      return i;
   }

   public long asLong() {
      return asLong(this.func_218149_a(), this.func_218163_b(), this.func_218148_c());
   }

   public Stream<BlockPos> allBlocksWithin() {
      return BlockPos.func_218287_a(this.getWorldStartX(), this.getWorldStartY(), this.getWorldStartZ(), this.getWorldEndX(), this.getWorldEndY(), this.getWorldEndZ());
   }

   public static Stream<SectionPos> func_218158_a(SectionPos p_218158_0_, int p_218158_1_) {
      int i = p_218158_0_.func_218149_a();
      int j = p_218158_0_.func_218163_b();
      int k = p_218158_0_.func_218148_c();
      return func_218168_a(i - p_218158_1_, j - p_218158_1_, k - p_218158_1_, i + p_218158_1_, j + p_218158_1_, k + p_218158_1_);
   }

   public static Stream<SectionPos> func_218168_a(final int p_218168_0_, final int p_218168_1_, final int p_218168_2_, final int p_218168_3_, final int p_218168_4_, final int p_218168_5_) {
      return StreamSupport.stream(new AbstractSpliterator<SectionPos>((long)((p_218168_3_ - p_218168_0_ + 1) * (p_218168_4_ - p_218168_1_ + 1) * (p_218168_5_ - p_218168_2_ + 1)), 64) {
         final CubeCoordinateIterator field_218394_a = new CubeCoordinateIterator(p_218168_0_, p_218168_1_, p_218168_2_, p_218168_3_, p_218168_4_, p_218168_5_);

         public boolean tryAdvance(Consumer<? super SectionPos> p_tryAdvance_1_) {
            if (this.field_218394_a.func_218301_a()) {
               p_tryAdvance_1_.accept(new SectionPos(this.field_218394_a.func_218304_b(), this.field_218394_a.func_218302_c(), this.field_218394_a.func_218303_d()));
               return true;
            } else {
               return false;
            }
         }
      }, false);
   }
}