package com.scoutingstats;

import com.google.gson.Gson;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Varbits;
import net.runelite.api.events.GameTick;
import net.runelite.client.RuneLite;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import com.google.inject.Provides;

import java.io.*;

@Slf4j
@PluginDescriptor(name = "Scouting Stats", description = "Super Duper Omega COX Scout Counter")
public class ScoutingStatsPlugin extends Plugin {
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private Client client;
    @Inject
    private ScoutingStatsConfig config;
    @Inject
    private Gson GSON;

    private static final File Data_Folder;

    static {
        Data_Folder = new File(RuneLite.RUNELITE_DIR, "scouting-stats");
        Data_Folder.mkdirs();
    }

    private ScoutingStatsOverlay ScoutingStatsOverlay = null;
    private static final int OUTSIDE_COX_LOBBY = 4919;
    int Entries = 0;
    int Exits = 0;
    int TotalEntries = 0;
    int TotalExits = 0;
    public boolean flag = false;

    @Override
    protected void startUp() {
        if (client.getGameState().equals(GameState.LOGGED_IN) && client.getLocalPlayer().getName() != null) {
            importData();
        }
        ScoutingStatsOverlay = new ScoutingStatsOverlay(this, config, Entries, Exits, TotalEntries, TotalExits);
        overlayManager.add(ScoutingStatsOverlay);
        updateOverlay();
    }

    @Override
    protected void shutDown() {
        overlayManager.remove(ScoutingStatsOverlay);
        if (client.getLocalPlayer().getName() != null) {
            exportData(new File(Data_Folder, client.getLocalPlayer().getName() + ".json"));
        }
    }


    protected void updateOverlay() {
        ScoutingStatsOverlay.Entries = Entries;
        ScoutingStatsOverlay.Exits = Exits;
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        boolean isInRaid = client.getVarbitValue(Varbits.IN_RAID) == 1;
        boolean outsideRaidLobby = (client.getLocalPlayer().getWorldLocation().getRegionID() == OUTSIDE_COX_LOBBY);
        if (isInRaid && !flag) {
            Entries++;
            flag = true;
        } else if (flag && outsideRaidLobby) {
            Exits++;
            flag = false;
        }
        updateOverlay();
    }

    private void importData() {
        if (!config.SaveData()) {
            return;
        }
        System.out.println(client.getLocalPlayer().getName());
        Data_Folder.mkdirs();
        File data = new File(Data_Folder, client.getLocalPlayer().getName() + ".json");
        try {
            if (!data.exists()) {
                Writer writer = new FileWriter(data);
                GSON.toJson(new ScoutingStatsData(), ScoutingStatsData.class, writer);
                writer.flush();
                writer.close();
            } else {
                ScoutingStatsData scoutingStatsData = GSON.fromJson(new FileReader(data), ScoutingStatsData.class);
                TotalEntries = scoutingStatsData.getTotalRaidsEntered();
                TotalExits = scoutingStatsData.getTotalRaidsExited();
            }
        } catch (IOException e) {
            log.error("Error Occurred while importing Scouting Stats Data: " + e.getMessage());
        }
    }

    void exportData(File file) {
        if (!config.SaveData()) {
            return;
        }
        ScoutingStatsData scoutingStatsData = new ScoutingStatsData(TotalEntries, TotalExits);
        try {
            Writer writer = new FileWriter(file);
            GSON.toJson(scoutingStatsData, ScoutingStatsData.class, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {

            log.error("Error Occurred while exporting Scouting Stats Data: " + e.getMessage());
        }
    }

    @Provides
    ScoutingStatsConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(ScoutingStatsConfig.class);
    }
}
