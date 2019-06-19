package net.minecraft.client.renderer.tileentity.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BedModel extends Model {
   private final RendererModel field_193772_a;
   private final RendererModel field_193773_b;
   private final RendererModel[] field_193774_c = new RendererModel[4];

   public BedModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_193772_a = new RendererModel(this, 0, 0);
      this.field_193772_a.addBox(0.0F, 0.0F, 0.0F, 16, 16, 6, 0.0F);
      this.field_193773_b = new RendererModel(this, 0, 22);
      this.field_193773_b.addBox(0.0F, 0.0F, 0.0F, 16, 16, 6, 0.0F);
      this.field_193774_c[0] = new RendererModel(this, 50, 0);
      this.field_193774_c[1] = new RendererModel(this, 50, 6);
      this.field_193774_c[2] = new RendererModel(this, 50, 12);
      this.field_193774_c[3] = new RendererModel(this, 50, 18);
      this.field_193774_c[0].addBox(0.0F, 6.0F, -16.0F, 3, 3, 3);
      this.field_193774_c[1].addBox(0.0F, 6.0F, 0.0F, 3, 3, 3);
      this.field_193774_c[2].addBox(-16.0F, 6.0F, -16.0F, 3, 3, 3);
      this.field_193774_c[3].addBox(-16.0F, 6.0F, 0.0F, 3, 3, 3);
      this.field_193774_c[0].rotateAngleX = ((float)Math.PI / 2F);
      this.field_193774_c[1].rotateAngleX = ((float)Math.PI / 2F);
      this.field_193774_c[2].rotateAngleX = ((float)Math.PI / 2F);
      this.field_193774_c[3].rotateAngleX = ((float)Math.PI / 2F);
      this.field_193774_c[0].rotateAngleZ = 0.0F;
      this.field_193774_c[1].rotateAngleZ = ((float)Math.PI / 2F);
      this.field_193774_c[2].rotateAngleZ = ((float)Math.PI * 1.5F);
      this.field_193774_c[3].rotateAngleZ = (float)Math.PI;
   }

   public void render() {
      this.field_193772_a.render(0.0625F);
      this.field_193773_b.render(0.0625F);
      this.field_193774_c[0].render(0.0625F);
      this.field_193774_c[1].render(0.0625F);
      this.field_193774_c[2].render(0.0625F);
      this.field_193774_c[3].render(0.0625F);
   }

   public void preparePiece(boolean p_193769_1_) {
      this.field_193772_a.showModel = p_193769_1_;
      this.field_193773_b.showModel = !p_193769_1_;
      this.field_193774_c[0].showModel = !p_193769_1_;
      this.field_193774_c[1].showModel = p_193769_1_;
      this.field_193774_c[2].showModel = !p_193769_1_;
      this.field_193774_c[3].showModel = p_193769_1_;
   }
}