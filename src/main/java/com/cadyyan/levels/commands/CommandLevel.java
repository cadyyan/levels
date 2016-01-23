package com.cadyyan.levels.commands;

import com.cadyyan.levels.registries.LevelStore;
import com.cadyyan.levels.registries.SkillRegistry;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class CommandLevel extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "level";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "commands.level.usage";
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length <= 0)
			throw new WrongUsageException("commands.level.usage");

		// TODO(cadyyan): handle localization of text

		String skillName = args[0];
		if (!SkillRegistry.getInstance().skillIsRegistered(skillName))
		{
			IChatComponent chatComponent = new ChatComponentText(skillName + " is not a valid skill");
			sender.addChatMessage(chatComponent);
			return;
		}

		EntityPlayer player = getTargetPlayer(sender, args);
		int level = LevelStore.getInstance().getPlayerLevels(player).getLevelForSkill(skillName);

		IChatComponent chatComponent = new ChatComponentText(skillName + ": " + Integer.toString(level));
		sender.addChatMessage(chatComponent);
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 1;
	}

	private EntityPlayer getTargetPlayer(ICommandSender sender, String[] args) throws PlayerNotFoundException
	{
		if (args.length == 1)
			return getCommandSenderAsPlayer(sender);
		else
			return getPlayer(sender, args[2]);
	}
}
