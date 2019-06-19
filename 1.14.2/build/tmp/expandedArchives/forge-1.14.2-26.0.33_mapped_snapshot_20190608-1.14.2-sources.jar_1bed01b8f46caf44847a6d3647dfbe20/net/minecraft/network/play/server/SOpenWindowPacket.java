package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SOpenWindowPacket implements IPacket<IClientPlayNetHandler> {
   private int field_218751_a;
   private int field_218752_b;
   private ITextComponent field_218753_c;

   public SOpenWindowPacket() {
   }

   public SOpenWindowPacket(int p_i50769_1_, ContainerType<?> p_i50769_2_, ITextComponent p_i50769_3_) {
      this.field_218751_a = p_i50769_1_;
      this.field_218752_b = Registry.field_218366_G.getId(p_i50769_2_);
      this.field_218753_c = p_i50769_3_;
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_218751_a = buf.readVarInt();
      this.field_218752_b = buf.readVarInt();
      this.field_218753_c = buf.readTextComponent();
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeVarInt(this.field_218751_a);
      buf.writeVarInt(this.field_218752_b);
      buf.writeTextComponent(this.field_218753_c);
   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IClientPlayNetHandler handler) {
      handler.func_217272_a(this);
   }

   @OnlyIn(Dist.CLIENT)
   public int func_218750_b() {
      return this.field_218751_a;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public ContainerType<?> func_218749_c() {
      return Registry.field_218366_G.getByValue(this.field_218752_b);
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent func_218748_d() {
      return this.field_218753_c;
   }
}