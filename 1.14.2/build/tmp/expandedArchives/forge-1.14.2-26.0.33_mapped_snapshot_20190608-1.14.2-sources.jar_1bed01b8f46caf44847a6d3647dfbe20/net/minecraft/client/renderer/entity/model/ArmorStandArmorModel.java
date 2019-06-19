package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmorStandArmorModel extends BipedModel<ArmorStandEntity> {
   public ArmorStandArmorModel() {
      this(0.0F);
   }

   public ArmorStandArmorModel(float modelSize) {
      this(modelSize, 64, 32);
   }

   protected ArmorStandArmorModel(float modelSize, int textureWidthIn, int textureHeightIn) {
      super(modelSize, 0.0F, textureWidthIn, textureHeightIn);
   }

   public void func_212844_a_(ArmorStandEntity p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      this.field_78116_c.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getHeadRotation().getX();
      this.field_78116_c.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getHeadRotation().getY();
      this.field_78116_c.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getHeadRotation().getZ();
      this.field_78116_c.setRotationPoint(0.0F, 1.0F, 0.0F);
      this.field_78115_e.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getX();
      this.field_78115_e.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getY();
      this.field_78115_e.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getZ();
      this.field_178724_i.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getLeftArmRotation().getX();
      this.field_178724_i.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getLeftArmRotation().getY();
      this.field_178724_i.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getLeftArmRotation().getZ();
      this.field_178723_h.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getRightArmRotation().getX();
      this.field_178723_h.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getRightArmRotation().getY();
      this.field_178723_h.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getRightArmRotation().getZ();
      this.field_178722_k.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getLeftLegRotation().getX();
      this.field_178722_k.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getLeftLegRotation().getY();
      this.field_178722_k.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getLeftLegRotation().getZ();
      this.field_178722_k.setRotationPoint(1.9F, 11.0F, 0.0F);
      this.field_178721_j.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getRightLegRotation().getX();
      this.field_178721_j.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getRightLegRotation().getY();
      this.field_178721_j.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getRightLegRotation().getZ();
      this.field_178721_j.setRotationPoint(-1.9F, 11.0F, 0.0F);
      this.field_178720_f.func_217177_a(this.field_78116_c);
   }
}