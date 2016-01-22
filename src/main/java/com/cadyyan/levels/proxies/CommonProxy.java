package com.cadyyan.levels.proxies;

import com.cadyyan.levels.Settings;
import com.cadyyan.levels.handlers.PlayerEventHandlers;
import com.cadyyan.levels.registries.SkillRegistry;
import com.cadyyan.levels.skills.SkillCarpentry;
import com.google.gson.Gson;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class CommonProxy implements IProxy
{
	private final Gson gsonLoader = new Gson();

	@Override
	public void registerEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandlers());
	}

	public void registerSkills()
	{
		if (Settings.Skills.enabledCarpentry)
			SkillRegistry.getInstance().registerSkill(new SkillCarpentry());
	}
}
