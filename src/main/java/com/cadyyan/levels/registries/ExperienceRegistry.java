package com.cadyyan.levels.registries;

import com.cadyyan.levels.handlers.ConfigurationHandler;
import com.cadyyan.levels.recipes.RecipeResult;
import com.cadyyan.levels.utils.LogUtility;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExperienceRegistry
{
	private static Map<String, RecipeResult> experience = new HashMap<String, RecipeResult>();

	public static void init()
	{
		LogUtility.info("Loading experience for crafting...");

		File recipeDir = new File(ConfigurationHandler.configDir, "recipes");

		if (!recipeDir.exists() && !recipeDir.mkdirs())
			throw new RuntimeException("Could not create the configuration directory for recipes");

		File[] recipeFiles = recipeDir.listFiles();
		if (recipeFiles == null)
			return;

		for (File recipeFile : recipeFiles)
		{
			try
			{
				JsonReader jsonReader = new JsonReader(new FileReader(recipeFile));

				jsonReader.beginObject();
				while (jsonReader.hasNext())
					loadItemRecipe(jsonReader);
				jsonReader.endObject();

				jsonReader.close();
			}
			catch (FileNotFoundException e)
			{
				LogUtility.error("It looks like this file was deleted before it could be read: {}", recipeFile.getAbsoluteFile());
			}
			catch (IOException e)
			{
				LogUtility.error("Something happened when trying to read file: {}", recipeFile.getAbsoluteFile());
			}
		}
	}

	public static void registerExperienceForCrafting(String itemName, String skillName, long xp)
	{
		if (!experience.containsKey(itemName))
			experience.put(itemName, new RecipeResult());

		experience.get(itemName).addSkillExperience(skillName, xp);
	}

	public static RecipeResult getExperienceForCrafting(String itemName)
	{
		return experience.getOrDefault(itemName, new RecipeResult());
	}

	private static void loadItemRecipe(JsonReader jsonReader) throws IOException
	{
		String itemName = jsonReader.nextString();

		LogUtility.trace("Loading experience for item: {}", itemName);

		jsonReader.beginObject();
		while (jsonReader.hasNext())
		{
			String skillName = jsonReader.nextString();
			long xp          = jsonReader.nextLong();

			registerExperienceForCrafting(itemName, skillName, xp);
		}
		jsonReader.endObject();

		LogUtility.trace("Finished loaded experience for item: {}", itemName);
	}
}
