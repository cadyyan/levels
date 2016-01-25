package com.cadyyan.levels.serializers;

import java.util.HashMap;
import java.util.Map;

public class PlayerLevels
{
	private static final double LOG_BASE_1_25 = Math.log(1.25);
	private static final int MIN_LEVEL = 1;
	private static final int MAX_LEVEL = 99;

	private Map<String, Double> experience;

	public PlayerLevels()
	{
		this(new HashMap<String, Double>());
	}

	public PlayerLevels(Map<String, Double> experience)
	{
		this.experience = experience;
	}

	public double getExperienceForSkill(String skillName)
	{
		return experience.getOrDefault(skillName, 0.0);
	}

	public int getLevelForSkill(String skillName)
	{
		double skillXP = getExperienceForSkill(skillName);

		return (int) Math.min(Math.floor(Math.log(skillXP / 100.0 + 1.0) / LOG_BASE_1_25) + 1.0, MAX_LEVEL);
	}

	public double getRequiredExperience(String skillName)
	{
		return getRequiredExperience(getLevelForSkill(skillName) + 1);
	}

	public double getRequiredExperience(int level)
	{
		if (level < MIN_LEVEL || level > MAX_LEVEL)
			throw new IllegalArgumentException(
					"Levels must be between " + Integer.toString(MIN_LEVEL) + " and " + Integer.toString(MAX_LEVEL));

		return 100.0 * Math.pow(1.25, level - 1.0) - 100.0;
	}

	public boolean addExperience(String skillName, double xp)
	{
		boolean leveledUp = false;

		xp += this.getExperienceForSkill(skillName);

		if (xp >= getRequiredExperience(skillName))
			leveledUp = true;

		this.experience.put(skillName, xp);

		return leveledUp;
	}

	public void setExperience(String skillName, double xp)
	{
		this.experience.put(skillName, xp);
	}

	public void incrementLevel(String skillName, int levels)
	{
		int currentLevel = this.getLevelForSkill(skillName);

		// Do nothing if we're already maxed out. This prevents us from resetting someones experience on accident
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
		double xp = this.getRequiredExperience(level);

		this.experience.put(skillName, xp);
	}

	public void reset(String skillName)
	{
		this.experience.put(skillName, 0.0);
	}

	public void resetAll()
	{
		for (String skillName : this.experience.keySet())
			reset(skillName);
	}
}
