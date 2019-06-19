package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SOpenBookWindowPacket implements IPacket<IClientPlayNetHandler> {
   private Hand field_218747_a;

   public SOpenBookWindowPacket() {
   }

   public SOpenBookWindowPacket(Hand p_i50770_1_) {
      this.field_218747_a = p_i50770_1_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_218747_a = buf.readEnumValue(Hand.class);
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeEnumValue(this.field_218747_a);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IClientPlayNetHandler handler) {
      handler.func_217268_a(this);
   }

   @OnlyIn(Dist.CLIENT)
   public Hand func_218746_b() {
      return this.field_218747_a;
   }
}