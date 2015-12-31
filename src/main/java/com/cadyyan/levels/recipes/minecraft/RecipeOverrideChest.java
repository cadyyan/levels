package com.cadyyan.levels.recipes.minecraft;

import com.cadyyan.levels.recipes.IRecipeOverride;
import com.cadyyan.levels.recipes.LevelRecipe;
import com.cadyyan.levels.registries.SkillRegistry;
import com.cadyyan.levels.skills.ISkill;
import net.minecraft.init.Blocks;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.HashMap;
import java.util.Map;

public class RecipeOverrideChest implements IRecipeOverride
{
	private static final Map<ISkill, Integer> REQUIRED_SKILLS = new HashMap<ISkill, Integer>();
	static {
		REQUIRED_SKILLS.put(SkillRegistry.getInstance().getSkill("carpentry"), 1);
	}

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
		return new LevelRecipe(recipe, REQUIRED_SKILLS);
	}
}
