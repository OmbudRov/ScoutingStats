package com.scoutingstats;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ScoutingStatsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ScoutingStatsPlugin.class);
		RuneLite.main(args);
	}
}