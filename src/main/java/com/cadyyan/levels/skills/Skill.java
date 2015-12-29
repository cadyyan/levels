package com.cadyyan.levels.skills;

public abstract class Skill implements ISkill
{
	protected String unlocalizedName;

	public Skill(String unlocalizedName)
	{
		this.unlocalizedName = unlocalizedName;
	}

	@Override
	public String getUnlocalizedName()
	{
		return unlocalizedName;
	}
}
