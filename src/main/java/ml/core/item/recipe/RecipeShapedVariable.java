package ml.core.item.recipe;

import ml.core.item.StackUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

/**
 * A simple ShapedRecipe clone that will call out to subclasses to match each slot
 * @author Matchlighter
 */
public abstract class RecipeShapedVariable extends CRecipeShapedBase implements IRecipe {
    
	
	public RecipeShapedVariable(int w, int h) {
		super(w,h);
	}
	
    public RecipeShapedVariable(Object... recipe) {
		super(recipe);
    }

	@Override
	protected boolean checkMatch(InventoryCrafting inv, int offx, int offy, boolean mirror) {
		for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++) {
			for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++) {
				int lx = x - offx;
				int ly = y - offy;

				ItemStack itemstack1 = inv.getStackInRowAndColumn(x, y);
				
				if (lx>=0 && lx<this.width && ly>=0 && ly<this.height) {
					if (!itemMatchesAt(mirror ? this.width-lx-1 : lx, ly, itemstack1, inv))
						return false;
				} else if (itemstack1 != null)
					return false;
			}
		}

		return true;
	}
	
	/**
	 * @param lx Localized x-position. Falls within 0&le;x&lt;width
	 * @param ly Localized y-position. Falls within 0&le;y&lt;height
	 * @param is {@link ItemStack} in the slot
	 * @return
	 */
	public boolean itemMatchesAt(int lx, int ly, ItemStack is, InventoryCrafting ic) {
		Object target = pattern[lx+ly*width];
		if (is != null || target != null) {
			if ((target == null && is != null) || (target != null && is == null))
				return false;
			
			if (!StackUtils.checkItemEquals(target, is))
				return false;
		}
		return true;
	}

}
