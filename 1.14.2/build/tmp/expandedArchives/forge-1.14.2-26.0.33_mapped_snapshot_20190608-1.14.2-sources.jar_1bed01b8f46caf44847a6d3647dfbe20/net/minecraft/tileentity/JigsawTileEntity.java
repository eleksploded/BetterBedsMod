package net.minecraft.tileentity;

import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JigsawTileEntity extends TileEntity {
   private ResourceLocation field_214059_a = new ResourceLocation("empty");
   private ResourceLocation field_214060_b = new ResourceLocation("empty");
   private String field_214061_c = "minecraft:air";

   public JigsawTileEntity(TileEntityType<?> p_i49960_1_) {
      super(p_i49960_1_);
   }

   public JigsawTileEntity() {
      this(TileEntityType.field_222487_E);
   }

   @OnlyIn(Dist.CLIENT)
   public ResourceLocation func_214053_c() {
      return this.field_214059_a;
   }

   @OnlyIn(Dist.CLIENT)
   public ResourceLocation func_214056_d() {
      return this.field_214060_b;
   }

   @OnlyIn(Dist.CLIENT)
   public String func_214054_f() {
      return this.field_214061_c;
   }

   public void func_214057_a(ResourceLocation p_214057_1_) {
      this.field_214059_a = p_214057_1_;
   }

   public void func_214058_b(ResourceLocation p_214058_1_) {
      this.field_214060_b = p_214058_1_;
   }

   public void func_214055_a(String p_214055_1_) {
      this.field_214061_c = p_214055_1_;
   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      compound.putString("attachement_type", this.field_214059_a.toString());
      compound.putString("target_pool", this.field_214060_b.toString());
      compound.putString("final_state", this.field_214061_c);
      return compound;
   }

   public void read(CompoundNBT compound) {
      super.read(compound);
      this.field_214059_a = new ResourceLocation(compound.getString("attachement_type"));
      this.field_214060_b = new ResourceLocation(compound.getString("target_pool"));
      this.field_214061_c = compound.getString("final_state");
   }

   /**
    * Retrieves packet to send to the client whenever this Tile Entity is resynced via World.notifyBlockUpdate. For
    * modded TE's, this packet comes back to you clientside in {@link #onDataPacket}
    */
   @Nullable
   public SUpdateTileEntityPacket getUpdatePacket() {
      return new SUpdateTileEntityPacket(this.pos, 12, this.getUpdateTag());
   }

   /**
    * Get an NBT compound to sync to the client with SPacketChunkData, used for initial loading of the chunk or when
    * many blocks change at once. This compound comes back to you clientside in {@link handleUpdateTag}
    */
   public CompoundNBT getUpdateTag() {
      return this.write(new CompoundNBT());
   }
}