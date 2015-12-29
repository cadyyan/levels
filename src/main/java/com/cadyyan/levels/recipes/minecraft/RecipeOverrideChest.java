package com.cadyyan.levels.recipes.minecraft;

import com.cadyyan.levels.recipes.IRecipeOverride;
import com.cadyyan.levels.recipes.LevelRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeOverrideChest implements IRecipeOverride
{
	@Override
	public boolean shouldOverrideRecipe(IRecipe recipe)
	{
		return !(!(recipe instanceof ShapelessRecipes) &&
				!(recipe instanceof ShapedRecipes) &&
				!(recipe instanceof ShapelessOreRecipe) &&
				!(recipe instanceof ShapedOreRecipe)) &&
				recipe.getRecipeOutput().getUnlocalizedName().equals(Blocks.chest.getUnlocalizedName());
	}

	@Override
	public IRecipe override(IRecipe recipe)
	{
		return new LevelRecipe(recipe);
	}
}
