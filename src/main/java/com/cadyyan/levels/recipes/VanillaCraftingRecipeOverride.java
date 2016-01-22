package com.cadyyan.levels.recipes;

import com.cadyyan.levels.serializers.PlayerLevels;
import com.cadyyan.levels.registries.LevelStore;
import com.cadyyan.levels.skills.ISkill;
import com.cadyyan.levels.utils.LogUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Map;

public class VanillaCraftingRecipeOverride extends RecipeOverride
{
	@Override
	public boolean matches(InventoryCrafting inventory, World world)
	{
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

		PlayerLevels playerLevels = LevelStore.getInstance().getPlayerLevels(player);
		for (Map.Entry<ISkill, Integer> entry : recipeModification.getRequirements().entrySet())
		{
			ISkill skill      = entry.getKey();
			int requiredLevel = entry.getValue();

			int level = playerLevels.getLevelForSkill(skill.getUnlocalizedName());
			if (level < requiredLevel)
				return false;
		}

		return true;
	}
}
