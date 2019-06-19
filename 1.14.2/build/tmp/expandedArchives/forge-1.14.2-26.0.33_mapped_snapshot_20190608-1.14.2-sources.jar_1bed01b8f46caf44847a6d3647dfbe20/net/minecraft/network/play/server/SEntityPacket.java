package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SEntityPacket implements IPacket<IClientPlayNetHandler> {
   protected int entityId;
   protected short field_149072_b;
   protected short field_149073_c;
   protected short field_149070_d;
   protected byte yaw;
   protected byte pitch;
   protected boolean onGround;
   protected boolean rotating;

   public static long func_218743_a(double p_218743_0_) {
      return MathHelper.lfloor(p_218743_0_ * 4096.0D);
   }

   public static Vec3d func_218744_a(long p_218744_0_, long p_218744_2_, long p_218744_4_) {
      return (new Vec3d((double)p_218744_0_, (double)p_218744_2_, (double)p_218744_4_)).scale((double)2.4414062E-4F);
   }

   public SEntityPacket() {
   }

   public SEntityPacket(int entityIdIn) {
      this.entityId = entityIdIn;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.entityId = buf.readVarInt();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeVarInt(this.entityId);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IClientPlayNetHandler handler) {
      handler.handleEntityMovement(this);
   }

   public String toString() {
      return "Entity_" + super.toString();
   }

   @OnlyIn(Dist.CLIENT)
   public Entity getEntity(World worldIn) {
      return worldIn.getEntityByID(this.entityId);
   }

   @OnlyIn(Dist.CLIENT)
   public short getX() {
      return this.field_149072_b;
   }

   @OnlyIn(Dist.CLIENT)
   public short getY() {
      return this.field_149073_c;
   }

   @OnlyIn(Dist.CLIENT)
   public short getZ() {
      return this.field_149070_d;
   }

   @OnlyIn(Dist.CLIENT)
   public byte getYaw() {
      return this.yaw;
   }

   @OnlyIn(Dist.CLIENT)
   public byte getPitch() {
      return this.pitch;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isRotating() {
      return this.rotating;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean getOnGround() {
      return this.onGround;
   }

   public static class LookPacket extends SEntityPacket {
      public LookPacket() {
         this.rotating = true;
      }

      public LookPacket(int entityIdIn, byte yawIn, byte pitchIn, boolean onGroundIn) {
         super(entityIdIn);
         this.yaw = yawIn;
         this.pitch = pitchIn;
         this.rotating = true;
         this.onGround = onGroundIn;
      }

      /**
       * Reads the raw packet data from the data stream.
       */
      public void readPacketData(PacketBuffer buf) throws IOException {
         super.readPacketData(buf);
         this.yaw = buf.readByte();
         this.pitch = buf.readByte();
         this.onGround = buf.readBoolean();
      }

      /**
       * Writes the raw packet data to the data stream.
       */
      public void writePacketData(PacketBuffer buf) throws IOException {
         super.writePacketData(buf);
         buf.writeByte(this.yaw);
         buf.writeByte(this.pitch);
         buf.writeBoolean(this.onGround);
      }
   }

   public static class MovePacket extends SEntityPacket {
      public MovePacket() {
         this.rotating = true;
      }

      public MovePacket(int p_i49988_1_, short p_i49988_2_, short p_i49988_3_, short p_i49988_4_, byte p_i49988_5_, byte p_i49988_6_, boolean p_i49988_7_) {
         super(p_i49988_1_);
         this.field_149072_b = p_i49988_2_;
         this.field_149073_c = p_i49988_3_;
         this.field_149070_d = p_i49988_4_;
         this.yaw = p_i49988_5_;
         this.pitch = p_i49988_6_;
         this.onGround = p_i49988_7_;
         this.rotating = true;
      }

      /**
       * Reads the raw packet data from the data stream.
       */
      public void readPacketData(PacketBuffer buf) throws IOException {
         super.readPacketData(buf);
         this.field_149072_b = buf.readShort();
         this.field_149073_c = buf.readShort();
         this.field_149070_d = buf.readShort();
         this.yaw = buf.readByte();
         this.pitch = buf.readByte();
         this.onGround = buf.readBoolean();
      }

      /**
       * Writes the raw packet data to the data stream.
       */
      public void writePacketData(PacketBuffer buf) throws IOException {
         super.writePacketData(buf);
         buf.writeShort(this.field_149072_b);
         buf.writeShort(this.field_149073_c);
         buf.writeShort(this.field_149070_d);
         buf.writeByte(this.yaw);
         buf.writeByte(this.pitch);
         buf.writeBoolean(this.onGround);
      }
   }

   public static class RelativeMovePacket extends SEntityPacket {
      public RelativeMovePacket() {
      }

      public RelativeMovePacket(int p_i49990_1_, short p_i49990_2_, short p_i49990_3_, short p_i49990_4_, boolean p_i49990_5_) {
         super(p_i49990_1_);
         this.field_149072_b = p_i49990_2_;
         this.field_149073_c = p_i49990_3_;
         this.field_149070_d = p_i49990_4_;
         this.onGround = p_i49990_5_;
      }

      /**
       * Reads the raw packet data from the data stream.
       */
      public void readPacketData(PacketBuffer buf) throws IOException {
         super.readPacketData(buf);
         this.field_149072_b = buf.readShort();
         this.field_149073_c = buf.readShort();
         this.field_149070_d = buf.readShort();
         this.onGround = buf.readBoolean();
      }

      /**
       * Writes the raw packet data to the data stream.
       */
      public void writePacketData(PacketBuffer buf) throws IOException {
         super.writePacketData(buf);
         buf.writeShort(this.field_149072_b);
         buf.writeShort(this.field_149073_c);
         buf.writeShort(this.field_149070_d);
         buf.writeBoolean(this.onGround);
      }
   }
}