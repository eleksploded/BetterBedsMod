package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.model.Model;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShieldModel extends Model {
   private final RendererModel field_187063_a;
   private final RendererModel field_187064_b;

   public ShieldModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_187063_a = new RendererModel(this, 0, 0);
      this.field_187063_a.addBox(-6.0F, -11.0F, -2.0F, 12, 22, 1, 0.0F);
      this.field_187064_b = new RendererModel(this, 26, 0);
      this.field_187064_b.addBox(-1.0F, -3.0F, -1.0F, 2, 6, 6, 0.0F);
   }

   public void render() {
      this.field_187063_a.render(0.0625F);
      this.field_187064_b.render(0.0625F);
   }
}