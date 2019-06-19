package net.minecraft.world.chunk.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.structure.LegacyStructureDataUtil;
import net.minecraft.world.storage.DimensionSavedDataManager;

public class ChunkLoader extends RegionFileCache {
   protected final DataFixer field_219168_b;
   @Nullable
   private LegacyStructureDataUtil field_219167_a;

   public ChunkLoader(File p_i49939_1_, DataFixer p_i49939_2_) {
      super(p_i49939_1_);
      this.field_219168_b = p_i49939_2_;
   }

   public CompoundNBT func_219166_a(DimensionType p_219166_1_, Supplier<DimensionSavedDataManager> p_219166_2_, CompoundNBT p_219166_3_) {
      int i = getDataVersion(p_219166_3_);
      int j = 1493;
      if (i < 1493) {
         p_219166_3_ = NBTUtil.update(this.field_219168_b, DefaultTypeReferences.CHUNK, p_219166_3_, i, 1493);
         if (p_219166_3_.getCompound("Level").getBoolean("hasLegacyStructureData")) {
            if (this.field_219167_a == null) {
               this.field_219167_a = LegacyStructureDataUtil.func_215130_a(p_219166_1_, p_219166_2_.get());
            }

            p_219166_3_ = this.field_219167_a.func_212181_a(p_219166_3_);
         }
      }

      p_219166_3_ = NBTUtil.update(this.field_219168_b, DefaultTypeReferences.CHUNK, p_219166_3_, Math.max(1493, i));
      if (i < SharedConstants.getVersion().getWorldVersion()) {
         p_219166_3_.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
      }

      return p_219166_3_;
   }

   public static int getDataVersion(CompoundNBT p_219165_0_) {
      return p_219165_0_.contains("DataVersion", 99) ? p_219165_0_.getInt("DataVersion") : -1;
   }

   public void writeChunk(ChunkPos pos, CompoundNBT compound) throws IOException {
      super.writeChunk(pos, compound);
      if (this.field_219167_a != null) {
         this.field_219167_a.func_208216_a(pos.asLong());
      }

   }
}