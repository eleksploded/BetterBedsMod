package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class EntityModel<T extends Entity> extends Model {
   public float field_217112_c;
   public boolean field_217113_d;
   public boolean field_217114_e = true;

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
   }

   public void func_217111_a(EntityModel<T> p_217111_1_) {
      p_217111_1_.field_217112_c = this.field_217112_c;
      p_217111_1_.field_217113_d = this.field_217113_d;
      p_217111_1_.field_217114_e = this.field_217114_e;
   }
}