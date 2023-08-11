package com.scoutingstats;

import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;


import java.awt.*;

public class ScoutingStatsOverlay extends OverlayPanel
{
	private final ScoutingStatsPlugin plugin;
	private final ScoutingStatsConfig config;
	public int Entries;
	public int Exits;
	public int TotalEntries;
	public int TotalExits;
	private final PanelComponent panelComponent = new PanelComponent();

	ScoutingStatsOverlay(ScoutingStatsPlugin plugin, ScoutingStatsConfig config, int Entries, int Exits, int TotalEntries, int TotalExits)
	{
		super(plugin);
		this.config = config;
		this.Entries = Entries;
		this.Exits = Exits;
		this.TotalEntries = TotalEntries;
		this.TotalExits = TotalExits;
		this.plugin = plugin;
		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
	}


	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.getChildren().clear();
		String Title = "Scouting Stats";
		panelComponent.getChildren().add(TitleComponent.builder().text(Title).build());

		if(config.RaidsEntered())
		{
			panelComponent.getChildren().add(LineComponent.builder().left("Entries:").right(String.valueOf(Entries)).build());
		}
		if(config.TotalRaidsEntered())
		{
			panelComponent.getChildren().add(LineComponent.builder().left("Total Entries:").right(String.valueOf(TotalEntries)).build());
		}
		if(config.RaidsExited())
		{
			panelComponent.getChildren().add(LineComponent.builder().left("Exits:").right(String.valueOf(Exits)).build());
		}
		if(config.TotalRaidsExited())
		{
			panelComponent.getChildren().add(LineComponent.builder().left("Entries:").right(String.valueOf(TotalExits)).build());
		}
		return panelComponent.render(graphics);
	}
}
