package com.cadyyan.levels.handlers;

import com.cadyyan.levels.Levels;
import com.cadyyan.levels.Settings;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class ConfigurationHandler
{
	public static Configuration configuration;
	public static File configDir;

	public static void init(File suggestedConfigFile)
	{
		configDir = new File(suggestedConfigFile.getParentFile(), Levels.MOD_ID);
		if (!configDir.exists() && !configDir.mkdirs())
			throw new RuntimeException("Unable to create the configuration directory");

		File generalConfigFile = new File(configDir, "configuration.cfg");

		configuration = new Configuration(generalConfigFile, true);
		loadConfiguration();
	}

	public static void loadConfiguration()
	{
		Settings.Plugins.enabledMinecraft = configuration.getBoolean("minecraft", "skills", true, "Enable Minecraft plugin");
		Settings.Skills.enabledCarpentry = configuration.getBoolean("carpentry", "skills", true, "Enable the carpentry skill");
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (!event.modID.equalsIgnoreCase(Levels.MOD_ID))
			return;

		configuration.save();
	}
}
