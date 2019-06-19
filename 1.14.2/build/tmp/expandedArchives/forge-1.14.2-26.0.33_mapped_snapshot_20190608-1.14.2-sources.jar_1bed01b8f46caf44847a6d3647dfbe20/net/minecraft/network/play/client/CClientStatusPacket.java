package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CClientStatusPacket implements IPacket<IServerPlayNetHandler> {
   private CClientStatusPacket.State field_149437_a;

   public CClientStatusPacket() {
   }

   public CClientStatusPacket(CClientStatusPacket.State p_i46886_1_) {
      this.field_149437_a = p_i46886_1_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_149437_a = buf.readEnumValue(CClientStatusPacket.State.class);
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeEnumValue(this.field_149437_a);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IServerPlayNetHandler handler) {
      handler.processClientStatus(this);
   }

   public CClientStatusPacket.State getStatus() {
      return this.field_149437_a;
   }

   public static enum State {
      PERFORM_RESPAWN,
      REQUEST_STATS;
   }
}