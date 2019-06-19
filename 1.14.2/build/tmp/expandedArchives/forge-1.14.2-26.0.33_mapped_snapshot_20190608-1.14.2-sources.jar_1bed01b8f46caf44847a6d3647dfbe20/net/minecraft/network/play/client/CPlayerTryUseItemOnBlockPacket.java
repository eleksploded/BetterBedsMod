package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CPlayerTryUseItemOnBlockPacket implements IPacket<IServerPlayNetHandler> {
   private BlockRayTraceResult field_218795_a;
   private Hand field_187027_c;

   public CPlayerTryUseItemOnBlockPacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CPlayerTryUseItemOnBlockPacket(Hand p_i50756_1_, BlockRayTraceResult p_i50756_2_) {
      this.field_187027_c = p_i50756_1_;
      this.field_218795_a = p_i50756_2_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_187027_c = buf.readEnumValue(Hand.class);
      this.field_218795_a = buf.func_218669_q();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeEnumValue(this.field_187027_c);
      buf.func_218668_a(this.field_218795_a);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IServerPlayNetHandler handler) {
      handler.processTryUseItemOnBlock(this);
   }

   public Hand getHand() {
      return this.field_187027_c;
   }

   public BlockRayTraceResult func_218794_c() {
      return this.field_218795_a;
   }
}