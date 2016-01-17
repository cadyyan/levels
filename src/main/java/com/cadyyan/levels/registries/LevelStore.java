package com.cadyyan.levels.registries;

import com.cadyyan.levels.PlayerLevels;
import com.cadyyan.levels.utils.LogUtility;
import com.cadyyan.levels.utils.SerializationHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.entity.player.EntityPlayer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelStore
{
	private static final Gson jsonSerializer = (new GsonBuilder()).setPrettyPrinting()
			.registerTypeAdapter(PlayerLevels.class, new PlayerLevels()).create();
	private static LevelStore registry;
	private File playerLevelDirectory;
	private Map<UUID, PlayerLevels> playerLevelCache = new HashMap<UUID, PlayerLevels>();
	// TODO(cadyyan): cache the player data

	public static LevelStore getInstance()
	{
		if (registry == null)
			registry = new LevelStore();

		return registry;
	}

	public void loadPlayerLevelsFromDisk(UUID playerUUID)
	{
		if (playerUUID == null)
			return;

		if (playerLevelCache.containsKey(playerUUID))
			return;

		File playerLevelFile = getPlayerLevelFile(playerUUID);
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

	public void savePlayerLevelsToDisk(UUID playerUUID)
	{
		if (playerUUID == null)
			return;

		if (!playerLevelCache.containsKey(playerUUID))
			return;

		File playerLevelFile = getPlayerLevelFile(playerUUID);
		if (!playerLevelFile.exists() || !playerLevelFile.isFile())
			return;

		PlayerLevels playerLevels = playerLevelCache.get(playerUUID);
		// TODO(cadyyan): it would probably also be beneficial to have a flag that says whether the data has even been modified

		// We write everything to a temporary file first so that if something happens while writing the data to disk
		// we don't risk corrupting the existing data. Only the latest progress will be lost. After we finish writing
		// the new data to disk we can replace the old file with the new one.
		File tmpFile = getPlayerLevelTempFile(playerUUID);
		try
		{
			JsonWriter jsonWriter = new JsonWriter(new FileWriter(tmpFile));
			jsonWriter.setIndent("    ");
			jsonSerializer.toJson(playerLevels, PlayerLevels.class, jsonWriter);
			jsonWriter.close();
		}
		catch (IOException e)
		{
			LogUtility.error("Unable to save player {}'s level data to disk.\n{}", e.getStackTrace());
		}
	}

	public void savePlayerLevelsToDisk(EntityPlayer player)
	{
		if (player == null || player.getUniqueID() == null)
			return;

		savePlayerLevelsToDisk(player.getUniqueID());
	}

	public PlayerLevels getPlayerLevels(EntityPlayer player)
	{
		return playerLevelCache.getOrDefault(player, new PlayerLevels());
	}

	private LevelStore()
	{
		playerLevelDirectory = SerializationHelper.getPlayerDataDirectory();
	}

	private File getPlayerLevelFile(UUID playerUUID)
	{
		return new File(playerLevelDirectory, playerUUID.toString() + ".json");
	}

	private File getPlayerLevelTempFile(UUID playerUUID)
	{
		return new File(playerLevelDirectory, playerUUID.toString() + ".json.tmp");
	}
}
