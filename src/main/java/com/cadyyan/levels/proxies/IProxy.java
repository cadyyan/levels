package com.cadyyan.levels.proxies;

import com.cadyyan.levels.plugins.IPlugin;

public interface IProxy
{
	void registerPlugin(IPlugin plugin);
	void registerEventHandlers();
	void initializePlugins();
}
