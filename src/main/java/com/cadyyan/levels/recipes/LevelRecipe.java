package com.cadyyan.levels.recipes;

import com.cadyyan.levels.PlayerLevels;
import com.cadyyan.levels.registries.LevelRegistry;
import com.cadyyan.levels.skills.ISkill;
import com.cadyyan.levels.utils.LogUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.HashMap;
import java.util.Map;

public class LevelRecipe implements IRecipe
{
	private final IRecipe recipe;
	private final Map<ISkill, Integer> requiredSkills;

	public LevelRecipe(IRecipe recipe, Map<ISkill, Integer> requiredSkills)
	{
		this.recipe = recipe;
		this.requiredSkills = requiredSkills;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
		// TODO(cadyyan): we aren't currently using this object but will when levels are implemented
		EntityPlayer player;

		Container container = ReflectionHelper.getPrivateValue(InventoryCrafting.class, inventory, "eventHandler");
		if (container instanceof ContainerPlayer)
		{
			ContainerPlayer playerCraftingArea = (ContainerPlayer) container;
			player = ReflectionHelper.getPrivateValue(ContainerPlayer.class, playerCraftingArea, "thePlayer");
		}
		else if (container instanceof ContainerWorkbench)
		{
			ContainerWorkbench worktableCraftingArea = (ContainerWorkbench) container;
			SlotCrafting craftingSlot = (SlotCrafting) worktableCraftingArea.getSlot(0);
			player = ReflectionHelper.getPrivateValue(SlotCrafting.class, craftingSlot, "thePlayer");
		}
		else
		{
			// TODO(cadyyan): we don't currently support other crafting methods. yet...
			LogUtility.warn("Unable to handle container of type {}", container.getClass().getCanonicalName());

			return recipe.matches(inventory, world);
		}

		// Check for a valid recipe
		if (!recipe.matches(inventory, world))
			return false;

		PlayerLevels playerLevels = LevelRegistry.getInstance().getPlayerLevels(player);
		for (Map.Entry<ISkill, Integer> skillEntry : requiredSkills.entrySet())
		{
			ISkill skill = skillEntry.getKey();
			int requiredLevel = skillEntry.getValue();

			int level = playerLevels.getLevelForSkill(skill.getUnlocalizedName());
			if (level < requiredLevel)
				return false;
		}
		// TODO(cadyyan): check the player's level and the item that they are trying to craft

		return true;
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
