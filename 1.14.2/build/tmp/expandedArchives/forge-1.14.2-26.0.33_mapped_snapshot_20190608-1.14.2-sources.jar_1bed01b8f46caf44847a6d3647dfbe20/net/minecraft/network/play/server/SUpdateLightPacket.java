package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SUpdateLightPacket implements IPacket<IClientPlayNetHandler> {
   private int field_218720_a;
   private int field_218721_b;
   private int field_218722_c;
   private int field_218723_d;
   private int field_218724_e;
   private int field_218725_f;
   private List<byte[]> field_218726_g;
   private List<byte[]> field_218727_h;

   public SUpdateLightPacket() {
   }

   public SUpdateLightPacket(ChunkPos p_i50774_1_, WorldLightManager p_i50774_2_) {
      this.field_218720_a = p_i50774_1_.x;
      this.field_218721_b = p_i50774_1_.z;
      this.field_218726_g = Lists.newArrayList();
      this.field_218727_h = Lists.newArrayList();

      for(int i = 0; i < 18; ++i) {
         NibbleArray nibblearray = p_i50774_2_.func_215569_a(LightType.SKY).func_215612_a(SectionPos.from(p_i50774_1_, -1 + i));
         NibbleArray nibblearray1 = p_i50774_2_.func_215569_a(LightType.BLOCK).func_215612_a(SectionPos.from(p_i50774_1_, -1 + i));
         if (nibblearray != null) {
            if (nibblearray.func_215655_c()) {
               this.field_218724_e |= 1 << i;
            } else {
               this.field_218722_c |= 1 << i;
               this.field_218726_g.add((byte[])nibblearray.getData().clone());
            }
         }

         if (nibblearray1 != null) {
            if (nibblearray1.func_215655_c()) {
               this.field_218725_f |= 1 << i;
            } else {
               this.field_218723_d |= 1 << i;
               this.field_218727_h.add((byte[])nibblearray1.getData().clone());
            }
         }
      }

   }

   public SUpdateLightPacket(ChunkPos p_i50775_1_, WorldLightManager p_i50775_2_, int p_i50775_3_, int p_i50775_4_) {
      this.field_218720_a = p_i50775_1_.x;
      this.field_218721_b = p_i50775_1_.z;
      this.field_218722_c = p_i50775_3_;
      this.field_218723_d = p_i50775_4_;
      this.field_218726_g = Lists.newArrayList();
      this.field_218727_h = Lists.newArrayList();

      for(int i = 0; i < 18; ++i) {
         if ((this.field_218722_c & 1 << i) != 0) {
            NibbleArray nibblearray = p_i50775_2_.func_215569_a(LightType.SKY).func_215612_a(SectionPos.from(p_i50775_1_, -1 + i));
            if (nibblearray != null && !nibblearray.func_215655_c()) {
               this.field_218726_g.add((byte[])nibblearray.getData().clone());
            } else {
               this.field_218722_c &= ~(1 << i);
               if (nibblearray != null) {
                  this.field_218724_e |= 1 << i;
               }
            }
         }

         if ((this.field_218723_d & 1 << i) != 0) {
            NibbleArray nibblearray1 = p_i50775_2_.func_215569_a(LightType.BLOCK).func_215612_a(SectionPos.from(p_i50775_1_, -1 + i));
            if (nibblearray1 != null && !nibblearray1.func_215655_c()) {
               this.field_218727_h.add((byte[])nibblearray1.getData().clone());
            } else {
               this.field_218723_d &= ~(1 << i);
               if (nibblearray1 != null) {
                  this.field_218725_f |= 1 << i;
               }
            }
         }
      }

   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_218720_a = buf.readVarInt();
      this.field_218721_b = buf.readVarInt();
      this.field_218722_c = buf.readVarInt();
      this.field_218723_d = buf.readVarInt();
      this.field_218724_e = buf.readVarInt();
      this.field_218725_f = buf.readVarInt();
      this.field_218726_g = Lists.newArrayList();

      for(int i = 0; i < 18; ++i) {
         if ((this.field_218722_c & 1 << i) != 0) {
            this.field_218726_g.add(buf.readByteArray(2048));
         }
      }

      this.field_218727_h = Lists.newArrayList();

      for(int j = 0; j < 18; ++j) {
         if ((this.field_218723_d & 1 << j) != 0) {
            this.field_218727_h.add(buf.readByteArray(2048));
         }
      }

   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeVarInt(this.field_218720_a);
      buf.writeVarInt(this.field_218721_b);
      buf.writeVarInt(this.field_218722_c);
      buf.writeVarInt(this.field_218723_d);
      buf.writeVarInt(this.field_218724_e);
      buf.writeVarInt(this.field_218725_f);

      for(byte[] abyte : this.field_218726_g) {
         buf.writeByteArray(abyte);
      }

      for(byte[] abyte1 : this.field_218727_h) {
         buf.writeByteArray(abyte1);
      }

   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IClientPlayNetHandler handler) {
      handler.func_217269_a(this);
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218716_b() {
      return this.field_218720_a;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218714_c() {
      return this.field_218721_b;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218715_d() {
      return this.field_218722_c;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218717_e() {
      return this.field_218724_e;
   }

   @OnlyIn(Dist.CLIENT)
   public List<byte[]> func_218712_f() {
      return this.field_218726_g;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218718_g() {
      return this.field_218723_d;
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218719_h() {
      return this.field_218725_f;
   }

   @OnlyIn(Dist.CLIENT)
   public List<byte[]> func_218713_i() {
      return this.field_218727_h;
   }
}