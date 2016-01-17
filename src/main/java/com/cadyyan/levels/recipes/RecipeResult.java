package com.cadyyan.levels.recipes;

import com.cadyyan.levels.registries.SkillRegistry;
import com.cadyyan.levels.skills.ISkill;

import java.util.HashMap;
import java.util.Map;

public class RecipeResult
{
	private Map<ISkill, Long> skillExperience = new HashMap<ISkill, Long>();

	public void addSkillExperience(String skillName, long xp)
	{
		addSkillExperience(SkillRegistry.getInstance().getSkill(skillName), xp);
	}

	public void addSkillExperience(final ISkill skill, long xp)
	{
		skillExperience.put(skill, xp);
	}

	public final Map<ISkill, Long> getExperienceForAllSkills()
	{
		return skillExperience;
	}

	public long getExperienceForSkill(String skillName)
	{
		return getExperienceForSkill(SkillRegistry.getInstance().getSkill(skillName));
	}

	public long getExperienceForSkill(final ISkill skill)
	{
		return skillExperience.getOrDefault(skill, 0L);
	}
}
