package com.cadyyan.levels.registries;

import com.cadyyan.levels.handlers.ConfigurationHandler;
import com.cadyyan.levels.recipes.IRecipeOverride;
import com.cadyyan.levels.recipes.VanillaCraftingRecipeOverride;
import com.cadyyan.levels.serializers.ItemRecipeModification;
import com.cadyyan.levels.utils.LogUtility;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeModificationRegistry
{
	private static final Gson GSON_LOADER = new Gson();

	private static Map<Class<? extends IRecipe>, Class<? extends IRecipeOverride>> recipeOverrideTypes =
			new HashMap<Class<? extends IRecipe>, Class<? extends IRecipeOverride>>();
	private static Map<String, ItemRecipeModification> recipeModifications =
			new HashMap<String, ItemRecipeModification>();

	public static void init()
	{
		initRecipeOverrideTypes();
		initRecipeModifications();
	}

	public static void registerRecipeOverrideType(String name,
	                                              Class<? extends IRecipe> recipeType,
	                                              Class<? extends IRecipeOverride> recipeOverrideType,
	                                              RecipeSorter.Category recipeCategory,
	                                              String dependencies)
	{
		if (recipeType == null)
			throw new NullPointerException("Must have non-null recipe type");

		if (recipeOverrideType == null)
			throw new NullPointerException("Must have non-null recipe override type");

		if (recipeCategory == null)
			throw new NullPointerException("Must have non-null recipe category");

		recipeOverrideTypes.put(recipeType, recipeOverrideType);
		RecipeSorter.register(name, recipeOverrideType.asSubclass(IRecipe.class), recipeCategory, dependencies);
	}

	public static Class<? extends IRecipeOverride> getRecipeOverrideType(Class<? extends IRecipe> recipeType)
	{
		return recipeOverrideTypes.get(recipeType);
	}

	private static void initRecipeOverrideTypes()
	{
		LogUtility.info("Setting up recipe modification types");

		registerRecipeOverrideType(
				"levels:minecraftshapeless",
				ShapelessRecipes.class,
				VanillaCraftingRecipeOverride.class,
				RecipeSorter.Category.SHAPELESS, "before:minecraft:shapeless"
		);
		registerRecipeOverrideType(
				"levels:forgeshapelessore",
				ShapelessOreRecipe.class,
				VanillaCraftingRecipeOverride.class,
				RecipeSorter.Category.SHAPELESS,
				"before:forge:shapelessore"
		);
		registerRecipeOverrideType(
				"levels:minecraftshaped",
				ShapedRecipes.class,
				VanillaCraftingRecipeOverride.class,
				RecipeSorter.Category.SHAPED,
				"before:minecraft:shaped"
		);
		registerRecipeOverrideType(
				"levels:forgeshapedore",
				ShapedOreRecipe.class,
				VanillaCraftingRecipeOverride.class,
				RecipeSorter.Category.SHAPED,
		        "before:forge:shapedore"
		);

		LogUtility.info("Recipe modification types setup");
	}

	private static void initRecipeModifications()
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
					loadItemModifications(jsonReader);
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

		LogUtility.info("Crafting experience loaded");
	}

	private static void loadItemModifications(JsonReader jsonReader) throws IOException
	{
		String itemName = jsonReader.nextName();

		LogUtility.trace("Loading experience for item: {}", itemName);

		ItemRecipeModification recipeModification = GSON_LOADER.fromJson(jsonReader, ItemRecipeModification.class);

		recipeModifications.put(itemName, recipeModification);
		applyRecipeOverride(itemName, recipeModification);

		LogUtility.trace("Finished loaded experience for item: {}", itemName);
	}

	private static void applyRecipeOverride(String itemName, ItemRecipeModification recipeModification)
	{
		IRecipeOverride recipeOverride  = recipeModification.getRecipeOverride();
		CraftingManager craftingManager = CraftingManager.getInstance();
		List<IRecipe> recipes           = craftingManager.getRecipeList();
		Item item                       = GameRegistry.findItem(itemName.split(":")[0], itemName.split(":")[1]);

		// This should be a modification safe way of doing this hopefully!
		for (int i = recipes.size() - 1; i >= 0; i--)
		{
			IRecipe recipe = recipes.get(i);

			if (!recipeModification.getRecipeType().isInstance(recipe))
				continue;

			if (!recipe.getRecipeOutput().getItem().equals(item))
				continue;

			recipeOverride.setRecipe(recipe);
			recipes.set(i, (IRecipe) recipeOverride);
		}
	}
}
