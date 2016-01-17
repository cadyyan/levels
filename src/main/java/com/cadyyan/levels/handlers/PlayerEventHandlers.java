package com.cadyyan.levels.handlers;

import com.cadyyan.levels.PlayerLevels;
import com.cadyyan.levels.recipes.RecipeResult;
import com.cadyyan.levels.registries.ExperienceRegistry;
import com.cadyyan.levels.registries.LevelStore;
import com.cadyyan.levels.skills.ISkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import java.util.Map;

public class PlayerEventHandlers
{
	@SubscribeEvent
	public void onPlayerLoadedFromFile(PlayerEvent.LoadFromFile event)
	{
		EntityPlayer player = event.entityPlayer;

		if (player.getEntityWorld().isRemote)
			return;

		LevelStore.getInstance().loadPlayerLevelsFromDisk(player);
	}

	@SubscribeEvent
	public void onPlayerSaveToFile(PlayerEvent.SaveToFile event)
	{
		EntityPlayer player = event.entityPlayer;

		if (player.getEntityWorld().isRemote)
			return;

		LevelStore.getInstance().savePlayerLevelsToDisk(player);
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event)
	{
		String itemName           = event.crafting.getUnlocalizedName();
		RecipeResult result       = ExperienceRegistry.getExperienceForCrafting(itemName);
		EntityPlayer player       = event.player;
		PlayerLevels playerLevels = LevelStore.getInstance().getPlayerLevels(player);

		for (Map.Entry<ISkill, Long> skillExperience : result.getExperienceForAllSkills().entrySet())
			playerLevels.addExperience(skillExperience.getKey().getUnlocalizedName(), skillExperience.getValue());
	}
}
