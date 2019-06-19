package net.minecraft.world.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DefaultTypeReferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimensionSavedDataManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, WorldSavedData> savedDatum = Maps.newHashMap();
   private final DataFixer field_215758_c;
   private final File field_215759_d;

   public DimensionSavedDataManager(File p_i51279_1_, DataFixer p_i51279_2_) {
      this.field_215758_c = p_i51279_2_;
      this.field_215759_d = p_i51279_1_;
   }

   private File func_215754_a(String p_215754_1_) {
      return new File(this.field_215759_d, p_215754_1_ + ".dat");
   }

   public <T extends WorldSavedData> T func_215752_a(Supplier<T> p_215752_1_, String p_215752_2_) {
      T t = this.func_215753_b(p_215752_1_, p_215752_2_);
      if (t != null) {
         return t;
      } else {
         T t1 = p_215752_1_.get();
         this.func_215757_a(t1);
         return t1;
      }
   }

   @Nullable
   public <T extends WorldSavedData> T func_215753_b(Supplier<T> p_215753_1_, String p_215753_2_) {
      WorldSavedData worldsaveddata = this.savedDatum.get(p_215753_2_);
      if (worldsaveddata == net.minecraftforge.common.util.DummyWorldSaveData.DUMMY) return null;
      if (worldsaveddata == null) {
         try {
            File file1 = this.func_215754_a(p_215753_2_);
            if (file1.exists()) {
               worldsaveddata = (WorldSavedData)p_215753_1_.get();
               CompoundNBT compoundnbt = this.func_215755_a(p_215753_2_, SharedConstants.getVersion().getWorldVersion());
               worldsaveddata.read(compoundnbt.getCompound("data"));
               this.savedDatum.put(p_215753_2_, worldsaveddata);
            } else {
               this.savedDatum.put(p_215753_2_, net.minecraftforge.common.util.DummyWorldSaveData.DUMMY);
               return null;
            }
         } catch (Exception exception) {
            LOGGER.error("Error loading saved data: {}", p_215753_2_, exception);
         }
      }

      return (T)worldsaveddata;
   }

   public void func_215757_a(WorldSavedData p_215757_1_) {
      this.savedDatum.put(p_215757_1_.getName(), p_215757_1_);
   }

   public CompoundNBT func_215755_a(String p_215755_1_, int p_215755_2_) throws IOException {
      File file1 = this.func_215754_a(p_215755_1_);

      CompoundNBT compoundnbt1;
      try (PushbackInputStream pushbackinputstream = new PushbackInputStream(new FileInputStream(file1), 2)) {
         CompoundNBT compoundnbt;
         if (this.func_215756_a(pushbackinputstream)) {
            compoundnbt = CompressedStreamTools.readCompressed(pushbackinputstream);
         } else {
            try (DataInputStream datainputstream = new DataInputStream(pushbackinputstream)) {
               compoundnbt = CompressedStreamTools.read(datainputstream);
            }
         }

         int i = compoundnbt.contains("DataVersion", 99) ? compoundnbt.getInt("DataVersion") : 1343;
         compoundnbt1 = NBTUtil.update(this.field_215758_c, DefaultTypeReferences.SAVED_DATA, compoundnbt, i, p_215755_2_);
      }

      return compoundnbt1;
   }

   private boolean func_215756_a(PushbackInputStream p_215756_1_) throws IOException {
      byte[] abyte = new byte[2];
      boolean flag = false;
      int i = p_215756_1_.read(abyte, 0, 2);
      if (i == 2) {
         int j = (abyte[1] & 255) << 8 | abyte[0] & 255;
         if (j == 35615) {
            flag = true;
         }
      }

      if (i != 0) {
         p_215756_1_.unread(abyte, 0, i);
      }

      return flag;
   }

   public void save() {
      for(WorldSavedData worldsaveddata : this.savedDatum.values()) {
         worldsaveddata.func_215158_a(this.func_215754_a(worldsaveddata.getName()));
      }

   }
}