package com.cadyyan.levels.proxies;

import com.cadyyan.levels.Settings;
import com.cadyyan.levels.handlers.ConfigurationHandler;
import com.cadyyan.levels.handlers.PlayerEventHandlers;
import com.cadyyan.levels.recipes.IRecipeOverride;
import com.cadyyan.levels.registries.SkillRegistry;
import com.cadyyan.levels.skills.SkillCarpentry;
import com.cadyyan.levels.utils.BlockItemHelper;
import com.cadyyan.levels.utils.LogUtility;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;

import java.io.*;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public class CommonProxy implements IProxy
{
	@Override
	public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandlers());
	}

	public void registerSkills()
	{
		if (Settings.Skills.enabledCarpentry)
			SkillRegistry.getInstance().registerSkill(new SkillCarpentry());
	}

	@Override
	public void dumpRecipes()
	{
		if (!Settings.Debug.enableRecipeDump)
			return;

		if (Settings.Debug.enableSmartRecipeDump)
			LogUtility.info("Dumping all recipes that have not been modified...");
		else
			LogUtility.info("Dumping all recipes...");

		File dumpFile = new File(ConfigurationHandler.configDir, "recipe_dump.txt");

		try
		{
			FileWriter dumpStream = new FileWriter(dumpFile);
			List<IRecipe> recipes       = CraftingManager.getInstance().getRecipeList();

			dumpStream.write("item,recipeType");

			for (IRecipe recipe : recipes)
			{
				if (Settings.Debug.enableSmartRecipeDump && recipe instanceof IRecipeOverride)
					continue;

				if (recipe.getRecipeOutput() == null)
				{
					LogUtility.warn("Skipping recipe with no output");
					continue;
				}

				StringBuilder stringBuilder = new StringBuilder();
				Formatter formatter         = new Formatter(stringBuilder, Locale.US);

				formatter.format(
					"%s,%s\n",
					BlockItemHelper.getResourceLocationString(recipe.getRecipeOutput()),
					recipe.getClass().getCanonicalName()
				);

				dumpStream.write(formatter.toString());
			}

			dumpStream.close();

			LogUtility.info("Finished dumping recipes");
		}
		catch (FileNotFoundException e)
		{
			LogUtility.error("Unable to find file: " + dumpFile.getAbsolutePath());
		}
		catch (IOException e)
		{
			LogUtility.error("Unable to dump recipes to file: " + dumpFile.getAbsolutePath());
		}
	}
}
