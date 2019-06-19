package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.IDynamicDeserializer;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawJunction;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

public abstract class AbstractVillagePiece extends StructurePiece {
   protected final JigsawPiece field_214832_a;
   protected BlockPos field_214833_b;
   private final int field_214835_d;
   protected final Rotation field_214834_c;
   private final List<JigsawJunction> field_214836_e = Lists.newArrayList();
   private final TemplateManager field_214837_f;

   public AbstractVillagePiece(IStructurePieceType p_i51346_1_, TemplateManager p_i51346_2_, JigsawPiece p_i51346_3_, BlockPos p_i51346_4_, int p_i51346_5_, Rotation p_i51346_6_, MutableBoundingBox p_i51346_7_) {
      super(p_i51346_1_, 0);
      this.field_214837_f = p_i51346_2_;
      this.field_214832_a = p_i51346_3_;
      this.field_214833_b = p_i51346_4_;
      this.field_214835_d = p_i51346_5_;
      this.field_214834_c = p_i51346_6_;
      this.boundingBox = p_i51346_7_;
   }

   public AbstractVillagePiece(TemplateManager p_i51347_1_, CompoundNBT p_i51347_2_, IStructurePieceType p_i51347_3_) {
      super(p_i51347_3_, p_i51347_2_);
      this.field_214837_f = p_i51347_1_;
      this.field_214833_b = new BlockPos(p_i51347_2_.getInt("PosX"), p_i51347_2_.getInt("PosY"), p_i51347_2_.getInt("PosZ"));
      this.field_214835_d = p_i51347_2_.getInt("ground_level_delta");
      this.field_214832_a = IDynamicDeserializer.func_214907_a(new Dynamic<>(NBTDynamicOps.INSTANCE, p_i51347_2_.getCompound("pool_element")), Registry.field_218365_F, "element_type", EmptyJigsawPiece.field_214856_a);
      this.field_214834_c = Rotation.valueOf(p_i51347_2_.getString("rotation"));
      this.boundingBox = this.field_214832_a.func_214852_a(p_i51347_1_, this.field_214833_b, this.field_214834_c);
      ListNBT listnbt = p_i51347_2_.getList("junctions", 10);
      this.field_214836_e.clear();
      listnbt.forEach((p_214827_1_) -> {
         this.field_214836_e.add(JigsawJunction.func_214894_a(new Dynamic<>(NBTDynamicOps.INSTANCE, p_214827_1_)));
      });
   }

   /**
    * (abstract) Helper method to read subclass data from NBT
    */
   protected void readAdditional(CompoundNBT tagCompound) {
      tagCompound.putInt("PosX", this.field_214833_b.getX());
      tagCompound.putInt("PosY", this.field_214833_b.getY());
      tagCompound.putInt("PosZ", this.field_214833_b.getZ());
      tagCompound.putInt("ground_level_delta", this.field_214835_d);
      tagCompound.put("pool_element", this.field_214832_a.func_214847_b(NBTDynamicOps.INSTANCE).getValue());
      tagCompound.putString("rotation", this.field_214834_c.name());
      ListNBT listnbt = new ListNBT();

      for(JigsawJunction jigsawjunction : this.field_214836_e) {
         listnbt.add(jigsawjunction.func_214897_a(NBTDynamicOps.INSTANCE).getValue());
      }

      tagCompound.put("junctions", listnbt);
   }

   /**
    * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at the
    * end, it adds Fences...
    */
   public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos p_74875_4_) {
      return this.field_214832_a.func_214848_a(this.field_214837_f, worldIn, this.field_214833_b, this.field_214834_c, structureBoundingBoxIn, randomIn);
   }

   public void offset(int x, int y, int z) {
      super.offset(x, y, z);
      this.field_214833_b = this.field_214833_b.add(x, y, z);
   }

   public Rotation func_214809_Y_() {
      return this.field_214834_c;
   }

   public String toString() {
      return String.format("<%s | %s | %s | %s>", this.getClass().getSimpleName(), this.field_214833_b, this.field_214834_c, this.field_214832_a);
   }

   public JigsawPiece func_214826_b() {
      return this.field_214832_a;
   }

   public BlockPos func_214828_c() {
      return this.field_214833_b;
   }

   public int func_214830_d() {
      return this.field_214835_d;
   }

   public void func_214831_a(JigsawJunction p_214831_1_) {
      this.field_214836_e.add(p_214831_1_);
   }

   public List<JigsawJunction> func_214829_e() {
      return this.field_214836_e;
   }
}