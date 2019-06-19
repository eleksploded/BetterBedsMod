package net.minecraft.network.play.client;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CSeenAdvancementsPacket implements IPacket<IServerPlayNetHandler> {
   private CSeenAdvancementsPacket.Action field_194166_a;
   private ResourceLocation tab;

   public CSeenAdvancementsPacket() {
   }

   @OnlyIn(Dist.CLIENT)
   public CSeenAdvancementsPacket(CSeenAdvancementsPacket.Action p_i47595_1_, @Nullable ResourceLocation p_i47595_2_) {
      this.field_194166_a = p_i47595_1_;
      this.tab = p_i47595_2_;
   }

   @OnlyIn(Dist.CLIENT)
   public static CSeenAdvancementsPacket openedTab(Advancement p_194163_0_) {
      return new CSeenAdvancementsPacket(CSeenAdvancementsPacket.Action.OPENED_TAB, p_194163_0_.getId());
   }

   @OnlyIn(Dist.CLIENT)
   public static CSeenAdvancementsPacket closedScreen() {
      return new CSeenAdvancementsPacket(CSeenAdvancementsPacket.Action.CLOSED_SCREEN, (ResourceLocation)null);
   }

   /**
    * Reads the raw packet data from the data stream.
    */
   public void readPacketData(PacketBuffer buf) throws IOException {
      this.field_194166_a = buf.readEnumValue(CSeenAdvancementsPacket.Action.class);
      if (this.field_194166_a == CSeenAdvancementsPacket.Action.OPENED_TAB) {
         this.tab = buf.readResourceLocation();
      }

   }

   /**
    * Writes the raw packet data to the data stream.
    */
   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeEnumValue(this.field_194166_a);
      if (this.field_194166_a == CSeenAdvancementsPacket.Action.OPENED_TAB) {
         buf.writeResourceLocation(this.tab);
      }

   }

   /**
    * Passes this Packet on to the NetHandler for processing.
    */
   public void processPacket(IServerPlayNetHandler handler) {
      handler.handleSeenAdvancements(this);
   }

   public CSeenAdvancementsPacket.Action getAction() {
      return this.field_194166_a;
   }

   public ResourceLocation getTab() {
      return this.tab;
   }

   public static enum Action {
      OPENED_TAB,
      CLOSED_SCREEN;
   }
}