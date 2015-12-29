package com.cadyyan.levels.handlers;

import com.cadyyan.levels.Levels;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler
{
	public static Configuration configuration;

	public static void init(File suggestedConfigFile)
	{
		File configDir = new File(suggestedConfigFile.getParentFile(), Levels.MOD_ID);
		if (!configDir.exists() && !configDir.mkdirs())
			throw new RuntimeException("Unable to create the configuration directory");

		File generalConfigFile = new File(configDir, "configuration.cfg");

		configuration = new Configuration(generalConfigFile, true);
		loadConfiguration();
	}

	public static void loadConfiguration()
	{
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (!event.modID.equalsIgnoreCase(Levels.MOD_ID))
			return;

		configuration.save();
	}
}