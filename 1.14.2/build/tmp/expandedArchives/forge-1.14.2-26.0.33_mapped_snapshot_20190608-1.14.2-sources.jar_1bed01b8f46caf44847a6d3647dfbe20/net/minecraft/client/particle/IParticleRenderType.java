package net.minecraft.client.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IParticleRenderType {
   IParticleRenderType field_217601_a = new IParticleRenderType() {
      public void func_217600_a(BufferBuilder p_217600_1_, TextureManager p_217600_2_) {
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         p_217600_2_.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
         p_217600_1_.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
      }

      public void func_217599_a(Tessellator p_217599_1_) {
         p_217599_1_.draw();
      }

      public String toString() {
         return "TERRAIN_SHEET";
      }
   };
   IParticleRenderType field_217602_b = new IParticleRenderType() {
      public void func_217600_a(BufferBuilder p_217600_1_, TextureManager p_217600_2_) {
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         p_217600_2_.bindTexture(AtlasTexture.field_215262_g);
         p_217600_1_.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
      }

      public void func_217599_a(Tessellator p_217599_1_) {
         p_217599_1_.draw();
      }

      public String toString() {
         return "PARTICLE_SHEET_OPAQUE";
      }
   };
   IParticleRenderType field_217603_c = new IParticleRenderType() {
      public void func_217600_a(BufferBuilder p_217600_1_, TextureManager p_217600_2_) {
         RenderHelper.disableStandardItemLighting();
         GlStateManager.depthMask(false);
         p_217600_2_.bindTexture(AtlasTexture.field_215262_g);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         GlStateManager.alphaFunc(516, 0.003921569F);
         p_217600_1_.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
      }

      public void func_217599_a(Tessellator p_217599_1_) {
         p_217599_1_.draw();
      }

      public String toString() {
         return "PARTICLE_SHEET_TRANSLUCENT";
      }
   };
   IParticleRenderType field_217604_d = new IParticleRenderType() {
      public void func_217600_a(BufferBuilder p_217600_1_, TextureManager p_217600_2_) {
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         p_217600_2_.bindTexture(AtlasTexture.field_215262_g);
         RenderHelper.disableStandardItemLighting();
         p_217600_1_.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
      }

      public void func_217599_a(Tessellator p_217599_1_) {
         p_217599_1_.draw();
      }

      public String toString() {
         return "PARTICLE_SHEET_LIT";
      }
   };
   IParticleRenderType field_217605_e = new IParticleRenderType() {
      public void func_217600_a(BufferBuilder p_217600_1_, TextureManager p_217600_2_) {
         GlStateManager.depthMask(true);
         GlStateManager.disableBlend();
      }

      public void func_217599_a(Tessellator p_217599_1_) {
      }

      public String toString() {
         return "CUSTOM";
      }
   };
   IParticleRenderType field_217606_f = new IParticleRenderType() {
      public void func_217600_a(BufferBuilder p_217600_1_, TextureManager p_217600_2_) {
      }

      public void func_217599_a(Tessellator p_217599_1_) {
      }

      public String toString() {
         return "NO_RENDER";
      }
   };

   void func_217600_a(BufferBuilder p_217600_1_, TextureManager p_217600_2_);

   void func_217599_a(Tessellator p_217599_1_);
}