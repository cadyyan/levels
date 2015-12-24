package com.cadyyan.levels.events;

import com.cadyyan.levels.registries.LevelRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandlers
{
	@SubscribeEvent
	public void onPlayerLoadedFromFile(PlayerEvent.LoadFromFile event)
	{
		EntityPlayer player = event.entityPlayer;

		if (player.getEntityWorld().isRemote)
			return;

		LevelRegistry.getInstance().loadPlayerLevelsFromDisk(player);
	}
}
