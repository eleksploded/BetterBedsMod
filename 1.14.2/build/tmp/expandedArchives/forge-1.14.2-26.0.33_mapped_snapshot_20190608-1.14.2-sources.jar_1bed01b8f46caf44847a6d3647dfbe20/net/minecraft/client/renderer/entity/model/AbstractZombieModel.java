package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractZombieModel<T extends MonsterEntity> extends BipedModel<T> {
   protected AbstractZombieModel(float p_i51070_1_, float p_i51070_2_, int p_i51070_3_, int p_i51070_4_) {
      super(p_i51070_1_, p_i51070_2_, p_i51070_3_, p_i51070_4_);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      boolean flag = this.func_212850_a_(p_212844_1_);
      float f = MathHelper.sin(this.field_217112_c * (float)Math.PI);
      float f1 = MathHelper.sin((1.0F - (1.0F - this.field_217112_c) * (1.0F - this.field_217112_c)) * (float)Math.PI);
      this.field_178723_h.rotateAngleZ = 0.0F;
      this.field_178724_i.rotateAngleZ = 0.0F;
      this.field_178723_h.rotateAngleY = -(0.1F - f * 0.6F);
      this.field_178724_i.rotateAngleY = 0.1F - f * 0.6F;
      float f2 = -(float)Math.PI / (flag ? 1.5F : 2.25F);
      this.field_178723_h.rotateAngleX = f2;
      this.field_178724_i.rotateAngleX = f2;
      this.field_178723_h.rotateAngleX += f * 1.2F - f1 * 0.4F;
      this.field_178724_i.rotateAngleX += f * 1.2F - f1 * 0.4F;
      this.field_178723_h.rotateAngleZ += MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.rotateAngleZ -= MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.rotateAngleX += MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
      this.field_178724_i.rotateAngleX -= MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
   }

   public abstract boolean func_212850_a_(T p_212850_1_);
}