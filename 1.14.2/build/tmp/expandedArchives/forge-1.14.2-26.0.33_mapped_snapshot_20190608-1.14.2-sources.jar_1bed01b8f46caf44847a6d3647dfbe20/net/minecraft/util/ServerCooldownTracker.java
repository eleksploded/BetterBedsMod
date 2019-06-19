package net.minecraft.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.SCooldownPacket;

public class ServerCooldownTracker extends CooldownTracker {
   private final ServerPlayerEntity field_185149_a;

   public ServerCooldownTracker(ServerPlayerEntity playerIn) {
      this.field_185149_a = playerIn;
   }

   protected void notifyOnSet(Item itemIn, int ticksIn) {
      super.notifyOnSet(itemIn, ticksIn);
      this.field_185149_a.connection.sendPacket(new SCooldownPacket(itemIn, ticksIn));
   }

   protected void notifyOnRemove(Item itemIn) {
      super.notifyOnRemove(itemIn);
      this.field_185149_a.connection.sendPacket(new SCooldownPacket(itemIn, 0));
   }
}