package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpriteRenderer<T extends Entity & IRendersAsItem> extends EntityRenderer<T> {
   private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
   private final float field_217763_f;

   public SpriteRenderer(EntityRendererManager p_i50956_1_, net.minecraft.client.renderer.ItemRenderer p_i50956_2_, float p_i50956_3_) {
      super(p_i50956_1_);
      this.itemRenderer = p_i50956_2_;
      this.field_217763_f = p_i50956_3_;
   }

   public SpriteRenderer(EntityRendererManager p_i50957_1_, net.minecraft.client.renderer.ItemRenderer p_i50957_2_) {
      this(p_i50957_1_, p_i50957_2_, 1.0F);
   }

   /**
    * Renders the desired {@code T} type Entity.
    */
   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
      GlStateManager.pushMatrix();
      GlStateManager.translatef((float)x, (float)y, (float)z);
      GlStateManager.enableRescaleNormal();
      GlStateManager.scalef(this.field_217763_f, this.field_217763_f, this.field_217763_f);
      GlStateManager.rotatef(-this.field_76990_c.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotatef((float)(this.field_76990_c.options.thirdPersonView == 2 ? -1 : 1) * this.field_76990_c.playerViewX, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
      this.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
      if (this.renderOutlines) {
         GlStateManager.enableColorMaterial();
         GlStateManager.setupSolidRenderingTextureCombine(this.getTeamColor(entity));
      }

      this.itemRenderer.renderItem(((IRendersAsItem)entity).getPotion(), ItemCameraTransforms.TransformType.GROUND);
      if (this.renderOutlines) {
         GlStateManager.tearDownSolidRenderingTextureCombine();
         GlStateManager.disableColorMaterial();
      }

      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
   }

   /**
    * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
    */
   protected ResourceLocation getEntityTexture(Entity entity) {
      return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
   }
}