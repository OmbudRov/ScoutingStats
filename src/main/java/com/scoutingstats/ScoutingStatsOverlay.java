package com.scoutingstats;

import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;


import java.awt.*;

public class ScoutingStatsOverlay extends OverlayPanel
{
	private final ScoutingStatsPlugin plugin;
	public int Entries;
	public int Exits;
	private final PanelComponent panelComponent = new PanelComponent();

	ScoutingStatsOverlay(ScoutingStatsPlugin plugin, int Entries, int Exits)
	{
		super(plugin);
		this.Entries = Entries;
		this.Exits = Exits;
		this.plugin = plugin;

		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
	}


	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.getChildren().clear();
		String Title = "Scouting Stats";
		panelComponent.getChildren().add(TitleComponent.builder().text(Title).build());

		panelComponent.getChildren().add(LineComponent.builder().left("Entries:").right(String.valueOf(plugin.Entries)).build());

		panelComponent.getChildren().add(LineComponent.builder().left("Exits:").right(String.valueOf(plugin.Exits)).build());
		return panelComponent.render(graphics);
	}
}
