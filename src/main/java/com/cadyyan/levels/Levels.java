package com.cadyyan.levels;

import com.cadyyan.levels.commands.CommandLevel;
import com.cadyyan.levels.handlers.ConfigurationHandler;
import com.cadyyan.levels.proxies.IProxy;
import com.cadyyan.levels.registries.RecipeModificationRegistry;
import com.cadyyan.levels.utils.SerializationHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@SuppressWarnings("unused")
@Mod(modid = Levels.MOD_ID, name = Levels.NAME, version = Levels.VERSION, acceptedMinecraftVersions = "[1.8.9]")
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
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerEventHandlers();
		proxy.registerSkills();
		RecipeModificationRegistry.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}

	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		SerializationHelper.init();

		event.registerServerCommand(new CommandLevel());
	}
}
