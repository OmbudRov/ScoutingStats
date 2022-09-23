
package com.scoutingstats;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import com.google.inject.Provides;

@Slf4j
@PluginDescriptor(name = "Scouting Stats", description = "Super Duper Omega COX Scout Counter")
public class ScoutingStatsPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;
	@Inject
	private Client client;
	@Inject
	private ScoutingStatsConfig config;

	private ScoutingStatsOverlay ScoutingStatsOverlay = null;
	private static final int OUTSIDE_COX_LOBBY = 4919;
	public int Entries = 0;
	public int Exits = 0;
	public boolean flag = false;

	@Override
	protected void startUp()
	{
		ScoutingStatsOverlay = new ScoutingStatsOverlay(this, Entries, Exits);
		overlayManager.add(ScoutingStatsOverlay);
		updateOverlay();
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(ScoutingStatsOverlay);
	}

	protected void updateOverlay()
	{
		ScoutingStatsOverlay.Entries = Entries;
		ScoutingStatsOverlay.Exits = Exits;
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		boolean isInRaid = client.getVarbitValue(Varbits.IN_RAID) == 1;
		boolean outsideRaidLobby = (client.getLocalPlayer().getWorldLocation().getRegionID() == OUTSIDE_COX_LOBBY);
		if (isInRaid && !flag)
		{
			Entries++;
			flag = true;
		}
		else if (flag && outsideRaidLobby)
		{
			Exits++;
			flag = false;
		}
	}

	@Provides
	ScoutingStatsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ScoutingStatsConfig.class);
	}
}
