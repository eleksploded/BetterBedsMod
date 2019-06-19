package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CPlayerDiggingPacket implements IPacket<IServerPlayNetHandler> {
   private BlockPos position;
   private Direction field_179716_b;
   private CPlayerDiggingPacket.Action field_149508_e;

   public CPlayerDiggingPacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CPlayerDiggingPacket(CPlayerDiggingPacket.Action actionIn, BlockPos posIn, Direction facingIn) {
      this.field_149508_e = actionIn;
      this.position = posIn;
      this.field_179716_b = facingIn;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_149508_e = buf.readEnumValue(CPlayerDiggingPacket.Action.class);
      this.position = buf.readBlockPos();
      this.field_179716_b = Direction.byIndex(buf.readUnsignedByte());
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeEnumValue(this.field_149508_e);
      buf.writeBlockPos(this.position);
      buf.writeByte(this.field_179716_b.getIndex());
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IServerPlayNetHandler handler) {
      handler.processPlayerDigging(this);
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public Direction getFacing() {
      return this.field_179716_b;
   }

   public CPlayerDiggingPacket.Action getAction() {
      return this.field_149508_e;
   }

   public static enum Action {
      START_DESTROY_BLOCK,
      ABORT_DESTROY_BLOCK,
      STOP_DESTROY_BLOCK,
      DROP_ALL_ITEMS,
      DROP_ITEM,
      RELEASE_USE_ITEM,
      SWAP_HELD_ITEMS;
   }
}