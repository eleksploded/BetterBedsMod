package net.minecraft.client.renderer.tileentity.model;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BannerModel extends Model {
   private final RendererModel field_178690_a;
   private final RendererModel field_178688_b;
   private final RendererModel field_178689_c;

   public BannerModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_178690_a = new RendererModel(this, 0, 0);
      this.field_178690_a.addBox(-10.0F, 0.0F, -2.0F, 20, 40, 1, 0.0F);
      this.field_178688_b = new RendererModel(this, 44, 0);
      this.field_178688_b.addBox(-1.0F, -30.0F, -1.0F, 2, 42, 2, 0.0F);
      this.field_178689_c = new RendererModel(this, 0, 42);
      this.field_178689_c.addBox(-10.0F, -32.0F, -1.0F, 20, 2, 2, 0.0F);
   }

   /**
    * Renders the banner model in.
    */
   public void renderBanner() {
      this.field_178690_a.rotationPointY = -32.0F;
      this.field_178690_a.render(0.0625F);
      this.field_178688_b.render(0.0625F);
      this.field_178689_c.render(0.0625F);
   }

   public RendererModel func_205057_b() {
      return this.field_178688_b;
   }

   public RendererModel func_205056_c() {
      return this.field_178690_a;
   }
}