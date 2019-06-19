package net.minecraft.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.CUpdateCommandBlockPacket;
import net.minecraft.tileentity.CommandBlockLogic;
import net.minecraft.tileentity.CommandBlockTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CommandBlockScreen extends AbstractCommandBlockScreen {
   private final CommandBlockTileEntity field_184078_g;
   private Button field_184079_s;
   private Button field_184080_t;
   private Button field_184081_u;
   private CommandBlockTileEntity.Mode field_184082_w = CommandBlockTileEntity.Mode.REDSTONE;
   private boolean conditional;
   private boolean automatic;

   public CommandBlockScreen(CommandBlockTileEntity commandBlockIn) {
      this.field_184078_g = commandBlockIn;
   }

   CommandBlockLogic getLogic() {
      return this.field_184078_g.getCommandBlockLogic();
   }

   int func_195236_i() {
      return 135;
   }

   protected void init() {
      super.init();
      this.field_184079_s = this.addButton(new Button(this.width / 2 - 50 - 100 - 4, 165, 100, 20, I18n.format("advMode.mode.sequence"), (p_214191_1_) -> {
         this.nextMode();
         this.updateMode();
      }));
      this.field_184080_t = this.addButton(new Button(this.width / 2 - 50, 165, 100, 20, I18n.format("advMode.mode.unconditional"), (p_214190_1_) -> {
         this.conditional = !this.conditional;
         this.updateConditional();
      }));
      this.field_184081_u = this.addButton(new Button(this.width / 2 + 50 + 4, 165, 100, 20, I18n.format("advMode.mode.redstoneTriggered"), (p_214189_1_) -> {
         this.automatic = !this.automatic;
         this.updateAutoExec();
      }));
      this.field_195240_g.active = false;
      this.field_195242_i.active = false;
      this.field_184079_s.active = false;
      this.field_184080_t.active = false;
      this.field_184081_u.active = false;
   }

   public void updateGui() {
      CommandBlockLogic commandblocklogic = this.field_184078_g.getCommandBlockLogic();
      this.field_195237_a.setText(commandblocklogic.getCommand());
      this.field_195238_s = commandblocklogic.shouldTrackOutput();
      this.field_184082_w = this.field_184078_g.getMode();
      this.conditional = this.field_184078_g.isConditional();
      this.automatic = this.field_184078_g.isAuto();
      this.updateTrackOutput();
      this.updateMode();
      this.updateConditional();
      this.updateAutoExec();
      this.field_195240_g.active = true;
      this.field_195242_i.active = true;
      this.field_184079_s.active = true;
      this.field_184080_t.active = true;
      this.field_184081_u.active = true;
   }

   public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
      super.resize(p_resize_1_, p_resize_2_, p_resize_3_);
      this.updateTrackOutput();
      this.updateMode();
      this.updateConditional();
      this.updateAutoExec();
      this.field_195240_g.active = true;
      this.field_195242_i.active = true;
      this.field_184079_s.active = true;
      this.field_184080_t.active = true;
      this.field_184081_u.active = true;
   }

   protected void func_195235_a(CommandBlockLogic p_195235_1_) {
      this.minecraft.getConnection().sendPacket(new CUpdateCommandBlockPacket(new BlockPos(p_195235_1_.getPositionVector()), this.field_195237_a.getText(), this.field_184082_w, p_195235_1_.shouldTrackOutput(), this.conditional, this.automatic));
   }

   private void updateMode() {
      switch(this.field_184082_w) {
      case SEQUENCE:
         this.field_184079_s.setMessage(I18n.format("advMode.mode.sequence"));
         break;
      case AUTO:
         this.field_184079_s.setMessage(I18n.format("advMode.mode.auto"));
         break;
      case REDSTONE:
         this.field_184079_s.setMessage(I18n.format("advMode.mode.redstone"));
      }

   }

   private void nextMode() {
      switch(this.field_184082_w) {
      case SEQUENCE:
         this.field_184082_w = CommandBlockTileEntity.Mode.AUTO;
         break;
      case AUTO:
         this.field_184082_w = CommandBlockTileEntity.Mode.REDSTONE;
         break;
      case REDSTONE:
         this.field_184082_w = CommandBlockTileEntity.Mode.SEQUENCE;
      }

   }

   private void updateConditional() {
      if (this.conditional) {
         this.field_184080_t.setMessage(I18n.format("advMode.mode.conditional"));
      } else {
         this.field_184080_t.setMessage(I18n.format("advMode.mode.unconditional"));
      }

   }

   private void updateAutoExec() {
      if (this.automatic) {
         this.field_184081_u.setMessage(I18n.format("advMode.mode.autoexec.bat"));
      } else {
         this.field_184081_u.setMessage(I18n.format("advMode.mode.redstoneTriggered"));
      }

   }
}