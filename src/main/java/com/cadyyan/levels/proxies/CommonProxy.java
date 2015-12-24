package com.cadyyan.levels.proxies;

import com.cadyyan.levels.events.PlayerEventHandlers;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IProxy
{
	@Override
	public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandlers());
	}
}
