package com.cadyyan.levels.handlers;

import com.cadyyan.levels.registries.LevelStore;
import com.cadyyan.levels.registries.RecipeModificationRegistry;
import com.cadyyan.levels.serializers.ItemRecipeModification;
import com.cadyyan.levels.serializers.PlayerLevels;
import com.cadyyan.levels.skills.ISkill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import java.util.Map;

public class PlayerEventHandlers
{
	@SubscribeEvent
	public void onPlayerLoadedFromFile(PlayerEvent.LoadFromFile event)
	{
		EntityPlayer player = event.entityPlayer;

		if (player.getEntityWorld().isRemote)
			return;

		LevelStore.getInstance().loadPlayerLevelsFromDisk(player);
	}

	@SubscribeEvent
	public void onPlayerSaveToFile(PlayerEvent.SaveToFile event)
	{
		EntityPlayer player = event.entityPlayer;

		if (player.getEntityWorld().isRemote)
			return;

		LevelStore.getInstance().savePlayerLevelsToDisk(player);
	}

	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent event)
	{
		// TODO(cadyyan): we may need to check for fake players
		EntityPlayer player = event.player;
		ItemStack itemStack = event.crafting;

		if (player.worldObj.isRemote)
			return;

		PlayerLevels playerLevels                 = LevelStore.getInstance().getPlayerLevels(player);
		ItemRecipeModification recipeModification = RecipeModificationRegistry.getRecipeModification(itemStack);
		if (recipeModification == null)
			return;

		for (Map.Entry<ISkill, Double> entry : recipeModification.getExperience().entrySet())
		{
			ISkill skill = entry.getKey();
			double xp    = entry.getValue();

			if (playerLevels.addExperience(skill.getUnlocalizedName(), xp))
			{
				// TODO(cadyyan): this needs to be translated
				IChatComponent chatComponent = new ChatComponentText(
						EnumChatFormatting.GREEN +
						"Congratulations!" + EnumChatFormatting.RESET + " You've reached level " +
						playerLevels.getLevelForSkill(skill.getUnlocalizedName()) +
						" " + skill.getUnlocalizedName()
				);

				player.addChatMessage(chatComponent);
			}
		}
	}
}
