package ml.core.lib;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class RenderLib {

	public static RenderItem itemRenderer = new RenderItem();
	public static int zLevel = 0;

	public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height)
	{
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV((double)(x + 0), (double)(y + height), (double)zLevel, (double)((float)(u + 0) * var7), (double)((float)(v + height) * var8));
		var9.addVertexWithUV((double)(x + width), (double)(y + height), (double)zLevel, (double)((float)(u + width) * var7), (double)((float)(v + height) * var8));
		var9.addVertexWithUV((double)(x + width), (double)(y + 0), (double)zLevel, (double)((float)(u + width) * var7), (double)((float)(v + 0) * var8));
		var9.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)zLevel, (double)((float)(u + 0) * var7), (double)((float)(v + 0) * var8));
		var9.draw();
	}

	public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		float var7 = (float)(par5 >> 24 & 255) / 255.0F;
		float var8 = (float)(par5 >> 16 & 255) / 255.0F;
		float var9 = (float)(par5 >> 8 & 255) / 255.0F;
		float var10 = (float)(par5 & 255) / 255.0F;
		float var11 = (float)(par6 >> 24 & 255) / 255.0F;
		float var12 = (float)(par6 >> 16 & 255) / 255.0F;
		float var13 = (float)(par6 >> 8 & 255) / 255.0F;
		float var14 = (float)(par6 & 255) / 255.0F;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Tessellator var15 = Tessellator.instance;
		var15.startDrawingQuads();
		var15.setColorRGBA_F(var8, var9, var10, var7);
		var15.addVertex((double)par3, (double)par2, (double)zLevel);
		var15.addVertex((double)par1, (double)par2, (double)zLevel);
		var15.setColorRGBA_F(var12, var13, var14, var11);
		var15.addVertex((double)par1, (double)par4, (double)zLevel);
		var15.addVertex((double)par3, (double)par4, (double)zLevel);
		var15.draw();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public static void drawStackAt(Minecraft mc, int x, int y, ItemStack is){
		GL11.glEnable(GL11.GL_LIGHTING);
		itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, is, x, y);
		itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, is, x, y);
		GL11.glDisable(GL11.GL_LIGHTING);
	}

	public static void drawSpecialStackAt(Minecraft mc, int x, int y, ItemStack is, String str){
		GL11.glEnable(GL11.GL_LIGHTING);
		ItemStack tis = is.copy();
		tis.stackSize = 1;
		itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, tis, x, y);
		itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, tis, x, y);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		mc.fontRenderer.drawStringWithShadow(str, x + 19 - 2 - mc.fontRenderer.getStringWidth(str), y + 6 + 3, 16777215);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glDisable(GL11.GL_LIGHTING);
	}

	public static void drawSlicedRect(int x, int y, int w, int h, int uBase, int vBase, int uW, int uH, int tBord, int rBord, int bBord, int lBord){
		drawTexturedModalRect(x, y, uBase, vBase, w-rBord, h-bBord);
		drawTexturedModalRect(x+lBord, y, uBase+uW-(w-lBord), vBase, w-lBord, h-bBord);
		drawTexturedModalRect(x+lBord, y+tBord, uBase+uW-(w-lBord), vBase+uH-(h-tBord), w-lBord, h-tBord);
		drawTexturedModalRect(x, y+tBord, uBase, vBase+uH-(h-tBord), w-rBord, h-tBord);
	}

	public static void renderItemIn2D(Tessellator tes, float umin, float vmin, float umax, float vmax, int szX, int szY, float thickness) { //Terribly Slow (WIP)
		tes.startDrawingQuads();
		tes.setNormal(0.0F, 0.0F, 1.0F);
		tes.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)umin, (double)vmax);
		tes.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)umax, (double)vmax);
		tes.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)umax, (double)vmin);
		tes.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)umin, (double)vmin);
		tes.draw();

		tes.startDrawingQuads();
		tes.setNormal(0.0F, 0.0F, -1.0F);
		tes.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - thickness), (double)umin, (double)vmin);
		tes.addVertexWithUV(1.0D, 1.0D, (double)(0.0F - thickness), (double)umax, (double)vmin);
		tes.addVertexWithUV(1.0D, 0.0D, (double)(0.0F - thickness), (double)umax, (double)vmax);
		tes.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - thickness), (double)umin, (double)vmax);
		tes.draw();

		GL11.glPushMatrix();
		GL11.glTranslatef(0, 0, -thickness);

		float fx = 1/(float)szX;
		float fy = 1/(float)szY;
		GL11.glScalef(fx, fy, 1F);

		for (float x=0; x<szX; x++){
			float u = umin + (umax-umin) * (x/(float)szX);
			for (float y=0; y<szY; y++){
				float v = vmin + (vmax-vmin) * (y/(float)szY);
				float ay = szY-y;
				
				tes.startDrawingQuads();
				tes.setNormal(-1.0F, 0.0F, 0.0F);
				tes.addVertexWithUV(x+1, ay, 0, u, v);
				tes.addVertexWithUV(x+1, ay, thickness, u, v+fy);
				tes.addVertexWithUV(x+1, ay-1, thickness, u+fx, v+fy);
				tes.addVertexWithUV(x+1, ay-1, 0, u+fx, v);
				tes.draw();
				
				tes.startDrawingQuads();
				tes.setNormal(1.0F, 0F, 0.0F);
				tes.addVertexWithUV(x, ay, 0, u, v);
				tes.addVertexWithUV(x, ay-1, 0, u+fx, v);
				tes.addVertexWithUV(x, ay-1, thickness, u+fx, v+fy);
				tes.addVertexWithUV(x, ay, thickness, u, v+fy);
				tes.draw();

				tes.startDrawingQuads();
				tes.setNormal(0.0F, 1.0F, 0.0F);
				tes.addVertexWithUV(x, ay, 0, u, v);
				tes.addVertexWithUV(x, ay, thickness, u, v+fy);
				tes.addVertexWithUV(x+1, ay, thickness, u+fx, v+fy);
				tes.addVertexWithUV(x+1, ay, 0, u+fx, v);
				tes.draw();

				tes.startDrawingQuads();
				tes.setNormal(0.0F, -1.0F, 0.0F);
				tes.addVertexWithUV(x, ay-1, 0, u, v);
				tes.addVertexWithUV(x+1, ay-1, 0, u+fx, v);
				tes.addVertexWithUV(x+1, ay-1, thickness, u+fx, v+fy);
				tes.addVertexWithUV(x, ay-1, thickness, u, v+fy);
				tes.draw();
			}
		}

		GL11.glPopMatrix();
	}
}