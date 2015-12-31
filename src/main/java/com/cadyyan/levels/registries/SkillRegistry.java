package com.cadyyan.levels.registries;

import com.cadyyan.levels.skills.ISkill;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SkillRegistry
{
	private static SkillRegistry instance;

	public static SkillRegistry getInstance()
	{
		if (instance == null)
			instance = new SkillRegistry();

		return instance;
	}

	private Map<String, ISkill> skills = new HashMap<String, ISkill>();

	public void registerSkill(ISkill skill)
	{
		skills.put(skill.getUnlocalizedName(), skill);
	}

	public ISkill getSkill(String name)
	{
		return skills.getOrDefault(name, null);
	}

	public Collection<ISkill> getAllSkills()
	{
		return skills.values();
	}
}
