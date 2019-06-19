package net.minecraft.world.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;

public class BlockLightStorage extends SectionLightStorage<BlockLightStorage.StorageMap> {
   protected BlockLightStorage(IChunkLightProvider p_i51300_1_) {
      super(LightType.BLOCK, p_i51300_1_, new BlockLightStorage.StorageMap(new Long2ObjectOpenHashMap<>()));
   }

   protected int func_215525_d(long p_215525_1_) {
      long i = SectionPos.func_218162_e(p_215525_1_);
      NibbleArray nibblearray = this.func_215520_a(i, false);
      return nibblearray == null ? 0 : nibblearray.get(SectionPos.func_218171_b(BlockPos.unpackX(p_215525_1_)), SectionPos.func_218171_b(BlockPos.unpackY(p_215525_1_)), SectionPos.func_218171_b(BlockPos.unpackZ(p_215525_1_)));
   }

   public static final class StorageMap extends LightDataMap<BlockLightStorage.StorageMap> {
      public StorageMap(Long2ObjectOpenHashMap<NibbleArray> p_i50064_1_) {
         super(p_i50064_1_);
      }

      public BlockLightStorage.StorageMap func_212858_b_() {
         return new BlockLightStorage.StorageMap(this.field_215645_a.clone());
      }
   }
}