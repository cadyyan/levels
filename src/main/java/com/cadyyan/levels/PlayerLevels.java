package com.cadyyan.levels;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PlayerLevels implements JsonSerializer<PlayerLevels>, JsonDeserializer<PlayerLevels>
{
	private static final double LOG_BASE_1_25 = Math.log(1.25);
	private static final int MIN_LEVEL = 1;
	private static final int MAX_LEVEL = 99;

	private Map<String, Long> experience;

	public PlayerLevels()
	{
		this(new HashMap<String, Long>());
	}

	public PlayerLevels(Map<String, Long> experience)
	{
		this.experience = experience;
	}

	public long getExperienceForSkill(String skillName)
	{
		return experience.getOrDefault(skillName, 0L);
	}

	public int getLevelForSkill(String skillName)
	{
		long skillXP = getExperienceForSkill(skillName);

		return Math.min((int) Math.floor(Math.log(skillXP / 100 + 1) / LOG_BASE_1_25) + 1, MAX_LEVEL);
	}

	public int getRequiredExperience(int level)
	{
		if (level < MIN_LEVEL || level > MAX_LEVEL)
			throw new IllegalArgumentException(
					"Levels must be between " + Integer.toString(MIN_LEVEL) + " and " + Integer.toString(MAX_LEVEL));

		return (int) Math.floor(100 * Math.pow(1.25, level - 1) - 100);
	}

	public void addExperience(String skillName, long xp)
	{
		long currentXP = this.getExperienceForSkill(skillName);

		this.experience.put(skillName, currentXP + xp);
	}

	public void setExperience(String skillName, long xp)
	{
		this.experience.put(skillName, xp);
	}

	public void incrementLevel(String skillName, int levels)
	{
		int currentLevel = this.getLevelForSkill(skillName);

		// Do nothing if we're already maxed out. This prevents us from reseting someones experience on accident
		if (currentLevel == MAX_LEVEL)
			return;

		if (currentLevel + levels > MAX_LEVEL)
			levels = MAX_LEVEL - currentLevel;

		setExperience(skillName, getRequiredExperience(currentLevel + levels));
	}

	public void incrementLevel(String skillName)
	{
		incrementLevel(skillName, 1);
	}

	public void setLevel(String skillName, int level)
	{
		long xp = this.getRequiredExperience(level);

		this.experience.put(skillName, xp);
	}

	public void reset(String skillName)
	{
		this.experience.put(skillName, 0L);
	}

	public void resetAll()
	{
		for (String skillName : this.experience.keySet())
			reset(skillName);
	}

	@Override
	public JsonElement serialize(PlayerLevels src, Type typeOfSrc, JsonSerializationContext context)
	{
		JsonObject serializedData = new JsonObject();

		for (String skillName : experience.keySet())
			serializedData.addProperty(skillName, experience.get(skillName));

		return serializedData;
	}

	@Override
	public PlayerLevels deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
			JsonParseException
	{
		if (!json.isJsonObject())
			throw new JsonParseException("Malformed or unexpected JSON data found for player. Expected an object.");

		JsonObject data = (JsonObject) json;
		Map<String, Long> xp = new HashMap<String, Long>();
		for (Map.Entry<String, JsonElement> entry : data.entrySet())
		{
			String skillName = entry.getKey();
			JsonElement element = entry.getValue();

			 if (!element.isJsonPrimitive())
				 throw new JsonParseException("Malformed or unexpected JSON data found for player. Expected a primitive value.");

			xp.put(skillName, element.getAsLong());
		}

		return new PlayerLevels(xp);
	}
}
