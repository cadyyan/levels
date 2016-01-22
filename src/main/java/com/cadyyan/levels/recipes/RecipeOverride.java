package com.cadyyan.levels.recipes;

import com.cadyyan.levels.serializers.ItemRecipeModification;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RecipeOverride implements IRecipe, IRecipeOverride
{
	protected IRecipe recipe;
	protected ItemRecipeModification recipeModification;

	public RecipeOverride()
	{
		// This needs to be kept as the default constructor so that we can auto-create instances
	}

	@Override
	public IRecipe getRecipe()
	{
		return recipe;
	}

	@Override
	public ItemRecipeModification getRecipeModification()
	{
		return recipeModification;
	}

	@Override
	public void setRecipe(IRecipe recipe)
	{
		this.recipe = recipe;
	}

	@Override
	public void setRecipeModification(ItemRecipeModification modification)
	{
		this.recipeModification = modification;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
		return recipe.matches(inventory, world);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory)
	{
		return recipe.getCraftingResult(inventory);
	}

	@Override
	public int getRecipeSize()
	{
		return recipe.getRecipeSize();
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return recipe.getRecipeOutput();
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inventory)
	{
		return recipe.getRemainingItems(inventory);
	}
}
