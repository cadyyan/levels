package com.cadyyan.levels.proxies;

import com.cadyyan.levels.handlers.PlayerEventHandlers;
import com.cadyyan.levels.plugins.IPlugin;
import com.cadyyan.levels.plugins.PluginMinecraft;
import com.cadyyan.levels.utils.LogUtility;
import net.minecraftforge.common.MinecraftForge;

import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class CommonProxy implements IProxy
{
	private Set<IPlugin> plugins = new LinkedHashSet<IPlugin>();

	@Override
	public void registerPlugin(IPlugin plugin)
	{
		plugins.add(plugin);
	}

	@Override
	public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandlers());
	}

	@Override
	public void initializePlugins()
	{
		// TODO(cadyyan): do a config check to turn off plugins
		for (IPlugin plugin : plugins)
		{
			LogUtility.trace("Initializing {} plugin", plugin.getName());

			if (!plugin.versionMatches())
				// TODO(cadyyan): this should be reported rather than causing a crash
				throw new RuntimeException("Unsupported version");

			plugin.init();

			LogUtility.trace("{} plugin initialized", plugin.getName());
		}
	}
}
