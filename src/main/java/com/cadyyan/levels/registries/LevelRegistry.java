package com.cadyyan.levels.registries;

import com.cadyyan.levels.PlayerLevels;
import com.cadyyan.levels.utils.LogUtility;
import com.cadyyan.levels.utils.SerializationHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelRegistry
{
	private static final Gson jsonSerializer = (new GsonBuilder()).setPrettyPrinting()
			.registerTypeAdapter(PlayerLevels.class, new PlayerLevels()).create();
	private static LevelRegistry registry;
	private File playerLevelDirectory;
	private Map<UUID, PlayerLevels> playerLevelCache = new HashMap<UUID, PlayerLevels>();
	// TODO(cadyyan): cache the player data

	public static LevelRegistry getInstance()
	{
		if (registry == null)
			registry = new LevelRegistry();

		return registry;
	}

	public void loadPlayerLevelsFromDisk(UUID playerUUID)
	{
		if (playerUUID == null)
			return;

		if (playerLevelCache.containsKey(playerUUID))
			return;

		File playerLevelFile = new File(playerLevelDirectory, playerUUID.toString() + ".json");
		if (!playerLevelFile.exists() || !playerLevelFile.isFile())
			return;

		try
		{
			JsonReader jsonReader = new JsonReader(new FileReader(playerLevelFile));
			PlayerLevels playerLevels = jsonSerializer.fromJson(jsonReader,  PlayerLevels.class);
			jsonReader.close();

			playerLevelCache.put(playerUUID, playerLevels);
		}
		catch (FileNotFoundException e)
		{
			LogUtility.error("Unable to find player level file for {}", playerUUID.toString());
		}
		catch (IOException e)
		{
			LogUtility.error("There was a problem closing the player level file:\n{}", e.getStackTrace());
		}
	}

	public void loadPlayerLevelsFromDisk(EntityPlayer player)
	{
		if (player == null || player.getUniqueID() == null)
			return;

		loadPlayerLevelsFromDisk(player.getUniqueID());
	}

	private LevelRegistry()
	{
		playerLevelDirectory = SerializationHelper.getPlayerDataDirectory();
	}
}
