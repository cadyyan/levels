package com.cadyyan.levels.serializers;

import com.cadyyan.levels.recipes.IRecipeOverride;
import com.cadyyan.levels.registries.SkillRegistry;
import com.cadyyan.levels.skills.ISkill;
import com.cadyyan.levels.utils.LogUtility;
import net.minecraft.item.crafting.IRecipe;

import java.util.HashMap;
import java.util.Map;

public class ItemRecipeModification
{
	@SuppressWarnings("unused")
	private String recipeType;
	@SuppressWarnings("unused")
	private String recipeOverrideType;
	@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "unused"})
	private Map<String, Integer> requirements;
	@SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "unused"})
	private Map<String, Long> experience; // TODO(cadyyan): it would be nice to make this a floating point value

	public ItemRecipeModification()
	{
		// This is required by Gson
	}

	public Class<? extends IRecipe> getRecipeType()
	{
		try
		{
			return Class.forName(recipeType).asSubclass(IRecipe.class);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("Unable to find recipe type " + recipeType);
		}
	}

	public Class<? extends IRecipeOverride> getRecipeOverrideType() throws ClassNotFoundException
	{
		return Class.forName(recipeOverrideType).asSubclass(IRecipeOverride.class);
	}

	public IRecipeOverride getRecipeOverride()
	{
		try
		{
			IRecipeOverride recipe = getRecipeOverrideType().newInstance();
			recipe.setRecipeModification(this);

			return recipe;
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException("Unable to instantiate recipe override", e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Unable to setup recipe override", e);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("Unable to find recipe override class", e);
		}
	}

	public final Map<ISkill, Integer> getRequirements()
	{
		Map<ISkill, Integer> results = new HashMap<ISkill, Integer>();
		SkillRegistry skillRegistry = SkillRegistry.getInstance();

		for (Map.Entry<String, Integer> entry : requirements.entrySet())
		{
			String skillName = entry.getKey();

			if (!skillRegistry.skillIsRegistered(skillName))
			{
				LogUtility.error("Unknown skill name {}! Skipping this recipe", skillName);
				continue;
			}

			results.put(skillRegistry.getSkill(skillName), entry.getValue());
		}

		return results;
	}

	public final Map<ISkill, Long> getExperience()
	{
		Map<ISkill, Long> results = new HashMap<ISkill, Long>();
		SkillRegistry skillRegistry = SkillRegistry.getInstance();

		for (Map.Entry<String, Long> entry : experience.entrySet())
		{
			String skillName = entry.getKey();

			if (!skillRegistry.skillIsRegistered(skillName))
			{
				LogUtility.error("Unknown skill name {}! Skipping this recipe", skillName);
				continue;
			}

			results.put(skillRegistry.getSkill(skillName), entry.getValue());
		}

		return results;
	}
}
