package com.cadyyan.levels.plugins;

public interface IPlugin
{
	String getName();
	boolean versionMatches();
	void init();
}
