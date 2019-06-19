package net.minecraft.world.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.IFluidState;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ChunkSection {
   private static final IBlockStatePalette<BlockState> field_205512_a = new BlockStatePaletteRegistry<>(Block.BLOCK_STATE_IDS, Blocks.AIR.getDefaultState());
   private final int yBase;
   private short field_76682_b;
   private short field_76683_c;
   private short field_206918_e;
   private final BlockStateContainer<BlockState> data;

   public ChunkSection(int p_i49943_1_) {
      this(p_i49943_1_, (short)0, (short)0, (short)0);
   }

   public ChunkSection(int p_i49944_1_, short p_i49944_2_, short p_i49944_3_, short p_i49944_4_) {
      this.yBase = p_i49944_1_;
      this.field_76682_b = p_i49944_2_;
      this.field_76683_c = p_i49944_3_;
      this.field_206918_e = p_i49944_4_;
      this.data = new BlockStateContainer<>(field_205512_a, Block.BLOCK_STATE_IDS, NBTUtil::readBlockState, NBTUtil::writeBlockState, Blocks.AIR.getDefaultState());
   }

   public BlockState get(int x, int y, int z) {
      return this.data.get(x, y, z);
   }

   public IFluidState getFluidState(int x, int y, int z) {
      return this.data.get(x, y, z).getFluidState();
   }

   public void func_222635_a() {
      this.data.lock();
   }

   public void func_222637_b() {
      this.data.unlock();
   }

   public BlockState func_222629_a(int p_222629_1_, int p_222629_2_, int p_222629_3_, BlockState p_222629_4_) {
      return this.set(p_222629_1_, p_222629_2_, p_222629_3_, p_222629_4_, true);
   }

   public BlockState set(int x, int y, int z, BlockState state, boolean p_177484_5_) {
      BlockState blockstate;
      if (p_177484_5_) {
         blockstate = this.data.func_222641_a(x, y, z, state);
      } else {
         blockstate = this.data.func_222639_b(x, y, z, state);
      }

      IFluidState ifluidstate = blockstate.getFluidState();
      IFluidState ifluidstate1 = state.getFluidState();
      if (!blockstate.isAir()) {
         --this.field_76682_b;
         if (blockstate.ticksRandomly()) {
            --this.field_76683_c;
         }
      }

      if (!ifluidstate.isEmpty()) {
         --this.field_206918_e;
      }

      if (!state.isAir()) {
         ++this.field_76682_b;
         if (state.ticksRandomly()) {
            ++this.field_76683_c;
         }
      }

      if (!ifluidstate1.isEmpty()) {
         ++this.field_206918_e;
      }

      return blockstate;
   }

   /**
    * Returns whether or not this block storage's Chunk is fully empty, based on its internal reference count.
    */
   public boolean isEmpty() {
      return this.field_76682_b == 0;
   }

   public static boolean isEmpty(@Nullable ChunkSection p_222628_0_) {
      return p_222628_0_ == Chunk.EMPTY_SECTION || p_222628_0_.isEmpty();
   }

   public boolean needsRandomTickAny() {
      return this.needsRandomTick() || this.needsRandomTickFluid();
   }

   /**
    * Returns whether or not this block storage's Chunk will require random ticking, used to avoid looping through
    * random block ticks when there are no blocks that would randomly tick.
    */
   public boolean needsRandomTick() {
      return this.field_76683_c > 0;
   }

   public boolean needsRandomTickFluid() {
      return this.field_206918_e > 0;
   }

   /**
    * Gets the y coordinate that this chunk section starts at (which is a multiple of 16). To get the y number, use
    * <code>section.getYLocation() >> 4</code>. Note that there is a section below the world for lighting purposes.
    */
   public int getYLocation() {
      return this.yBase;
   }

   public void recalculateRefCounts() {
      this.field_76682_b = 0;
      this.field_76683_c = 0;
      this.field_206918_e = 0;

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            for(int k = 0; k < 16; ++k) {
               BlockState blockstate = this.get(i, j, k);
               IFluidState ifluidstate = this.getFluidState(i, j, k);
               if (!blockstate.isAir()) {
                  ++this.field_76682_b;
                  if (blockstate.ticksRandomly()) {
                     ++this.field_76683_c;
                  }
               }

               if (!ifluidstate.isEmpty()) {
                  ++this.field_76682_b;
                  if (ifluidstate.ticksRandomly()) {
                     ++this.field_206918_e;
                  }
               }
            }
         }
      }

   }

   public BlockStateContainer<BlockState> getData() {
      return this.data;
   }

   @OnlyIn(Dist.CLIENT)
   public void func_222634_a(PacketBuffer p_222634_1_) {
      this.field_76682_b = p_222634_1_.readShort();
      this.data.read(p_222634_1_);
   }

   public void func_222630_b(PacketBuffer p_222630_1_) {
      p_222630_1_.writeShort(this.field_76682_b);
      this.data.write(p_222630_1_);
   }

   public int func_222633_j() {
      return 2 + this.data.getSerializedSize();
   }

   public boolean func_222636_a(BlockState p_222636_1_) {
      return this.data.func_222640_a(p_222636_1_);
   }
}