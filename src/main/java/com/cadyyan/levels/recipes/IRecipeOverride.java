package com.cadyyan.levels.recipes;

import net.minecraft.item.crafting.IRecipe;

public interface IRecipeOverride
{
	boolean shouldOverrideRecipe(IRecipe recipe);
	IRecipe override(IRecipe recipe);
}
