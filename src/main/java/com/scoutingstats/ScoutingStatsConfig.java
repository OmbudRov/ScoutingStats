package com.scoutingstats;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ScoutingStats")
public interface ScoutingStatsConfig extends Config
{
    @ConfigItem(
            position = 0,
            keyName = "RaidsEntered",
            name = "Raids Entered",
            description = "Displays the number of layouts scouted this session"
    )
    default boolean RaidsEntered()
    {
        return true;
    }

    @ConfigItem(
            position = 1,
            keyName = "RaidsExited",
            name = "Raids Exited",
            description = "Displays the number of scouted layouts rejected this session"
    )
    default boolean RaidsExited()
    {
        return true;
    }

    @ConfigItem(
            position = 2,
            keyName = "SaveData",
            name = "Save Data",
            description = "Save data across different sessions"
    )
    default boolean SaveData()
    {
        return true;
    }
    @ConfigItem(
            position = 3,
            keyName = "TotalRaidsEntered",
            name = "Total Raids Entered",
            description = "Displays the number of layouts scouted in total"
    )
    default boolean TotalRaidsEntered()
    {
        return true;
    }

    @ConfigItem(
            position = 4,
            keyName = "TotalRaidsExited",
            name = "Total Raids Exited",
            description = "Displays the number of scouted layouts rejected in total"
    )
    default boolean TotalRaidsExited()
    {
        return true;
    }
}
