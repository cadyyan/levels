package com.cadyyan.levels.recipes;

import com.cadyyan.levels.serializers.ItemRecipeModification;
import net.minecraft.item.crafting.IRecipe;

public interface IRecipeOverride
{
	IRecipe getRecipe();
	ItemRecipeModification getRecipeModification();
	void setRecipe(IRecipe recipe);
	void setRecipeModification(ItemRecipeModification modification);
}
