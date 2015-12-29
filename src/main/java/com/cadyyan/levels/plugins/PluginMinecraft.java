package com.cadyyan.levels.plugins;

import com.cadyyan.levels.recipes.IRecipeOverride;
import com.cadyyan.levels.recipes.minecraft.RecipeOverrideChest;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PluginMinecraft implements IPlugin
{
	private static final Set<IRecipeOverride> PLUGIN_RECIPES = new LinkedHashSet<IRecipeOverride>();

	@Override
	public boolean versionMatches()
	{
		return true;
	}

	@Override
	public void init()
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (IRecipeOverride pluginRecipe : PLUGIN_RECIPES)
		{
			for (int i = recipes.size() - 1; i > 0; i--)
			{
				IRecipe recipe = recipes.get(i);
				if (!pluginRecipe.shouldOverrideRecipe(recipe))
					continue;

				recipes.set(i, pluginRecipe.override(recipe));
			}
		}
	}

	static {
		PLUGIN_RECIPES.add(new RecipeOverrideChest());
	}
}
