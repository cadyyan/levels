package com.cadyyan.levels.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BlockItemHelper
{
	public static ResourceLocation getResourceLocation(Item item)
	{
		return Item.itemRegistry.getNameForObject(item);
	}

	public static ResourceLocation getResourceLocation(Block block)
	{
		return Block.blockRegistry.getNameForObject(block);
	}

	public static ResourceLocation getResourceLocation(ItemStack itemStack)
	{
		return getResourceLocation(itemStack.getItem());
	}

	public static String getResourceLocationString(Item item)
	{
		return getResourceLocation(item).toString();
	}

	public static String getResourceLocationString(Block block)
	{
		return getResourceLocation(block).toString();
	}

	public static String getResourceLocationString(ItemStack itemStack)
	{
		return getResourceLocationString(itemStack.getItem());
	}

	private BlockItemHelper()
	{
	}
}
