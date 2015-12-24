package com.cadyyan.levels;

import com.cadyyan.levels.items.RecipeDecorator;
import com.cadyyan.levels.proxies.IProxy;
import com.cadyyan.levels.utils.SerializationHelper;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

@Mod(modid = Levels.MOD_ID, name = Levels.NAME, version = Levels.VERSION)
public class Levels
{
	public static final String MOD_ID  = "levels";
	public static final String NAME    = "Levels";
	public static final String VERSION = "@MOD_VERSION@";

	@Instance(Levels.MOD_ID)
	public static Levels instance;

	@SidedProxy(
		clientSide = "com.cadyyan.levels.proxies.CommonProxy",
		serverSide = "com.cadyyan.levels.proxies.CommonProxy")
	public static IProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerEventHandlers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		overrideRecipes();
	}

	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		SerializationHelper.init();
	}

	private void overrideRecipes()
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = recipes.size() - 1; i > 0; i--)
		{
			IRecipe recipe = recipes.get(i);

			if (recipe instanceof ShapelessRecipes ||
					recipe instanceof ShapedRecipes ||
					recipe instanceof ShapelessOreRecipe ||
					recipe instanceof ShapedOreRecipe)
			{
				RecipeDecorator recipeDecorator = new RecipeDecorator(recipe);

				recipes.set(i, recipeDecorator);
			}
		}
	}
}
