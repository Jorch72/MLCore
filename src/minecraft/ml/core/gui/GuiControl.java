package ml.core.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiControl extends Gui {
	
	public GuiContainerControl guiContainer;
	public boolean enabled = true;
	
	public GuiControl(GuiContainerControl gcc) {
		guiContainer = gcc;
	}

	public void update() {}
	
	public abstract boolean isPointIn(int pX, int pY);
	
	public void renderFirst(Minecraft mc, int mouseX, int mouseY) {}
	public void renderBackground(Minecraft mc, int mouseX, int mouseY) {}
	public void renderForeground(Minecraft mc, int mouseX, int mouseY) {}
	
	public List<String> getTooltipLines(int mx, int my) {
		return new ArrayList<String>();
	}
	
	public boolean onMouseClicked(int mouseX, int mouseY, MouseButton button) {
		return false;
	}
	
	public void onMouseMovedOrUp(int mX, int mY, int act) {}
	
	public boolean onKeyPress(char chr, int key) {
		return false;
	}
	
	public float getZLevel() { return this.zLevel; }
}
