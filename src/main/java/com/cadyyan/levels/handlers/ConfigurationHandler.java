package com.cadyyan.levels.handlers;

import com.cadyyan.levels.Levels;
import com.cadyyan.levels.Settings;
import com.google.common.io.Files;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

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

		if (configuration.hasChanged())
			configuration.save();

		File recipeDir = new File(configDir, "recipes");
		try
		{
			if (!recipeDir.exists())
				loadDefaultRecipeConfigs(recipeDir);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Unable to load default recipe configurations", e);
		}
		catch (URISyntaxException e)
		{
			throw new RuntimeException("Unable to load default recipe configurations", e);
		}
	}

	public static void loadConfiguration()
	{
		Settings.Skills.enabledCarpentry = configuration.getBoolean("carpentry", "skills", true, "Enable the carpentry skill");
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (!event.modID.equalsIgnoreCase(Levels.MOD_ID))
			return;

		configuration.save();
	}

	private static void loadDefaultRecipeConfigs(File recipeDir) throws IOException, URISyntaxException
	{
		if (!recipeDir.mkdirs())
			throw new RuntimeException("Unable to create the recipe configuration directory");

		// TODO(cadyyan): it would be nice if this worked to compare with what the jar has to add things that are
		// TODO(cadyyan): missing and ignore things that are already there
		// TODO(cadyyan): this will probably blow up as a jar
		URL recipeURL = ConfigurationHandler.class.getClassLoader().getResource("assets/levels/defaults/config/recipes");
		if (recipeURL == null)
			throw new RuntimeException("Unable to load default recipe directory. The directory could not be found");

		File defaultRecipesDir = new File(recipeURL.toURI());
		File[] recipeFiles     = defaultRecipesDir.listFiles();
		if (recipeFiles == null)
			return; // Skipping because the directory is empty

		for (File recipeFile : recipeFiles)
		{
			byte[] input        = Files.toByteArray(recipeFile);
			OutputStream output = new FileOutputStream(new File(recipeDir, recipeFile.getName()));

			output.write(input);

			output.close();
		}
	}
}
