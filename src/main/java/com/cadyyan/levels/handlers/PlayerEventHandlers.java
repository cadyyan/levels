package com.cadyyan.levels.handlers;

import com.cadyyan.levels.registries.LevelStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

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
		// TODO(cadyyan): handle crafting experience
	}
}
