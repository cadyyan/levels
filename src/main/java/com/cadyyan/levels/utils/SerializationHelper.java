package com.cadyyan.levels.utils;

import com.cadyyan.levels.Levels;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.File;

public class SerializationHelper
{
	private static File playerDataDirectory;

	public static void init()
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		File worldDirectory = server.getEntityWorld().getSaveHandler().getWorldDirectory();
		playerDataDirectory = new File(worldDirectory, "playerdata" + File.separator + Levels.MOD_ID.toLowerCase());

		if (!playerDataDirectory.mkdirs())
			LogUtility.error("Unable to create the player level directory.");
	}

	public static File getPlayerDataDirectory()
	{
		return playerDataDirectory;
	}

	private SerializationHelper()
	{
	}
}
