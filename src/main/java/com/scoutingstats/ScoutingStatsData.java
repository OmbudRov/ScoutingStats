package com.scoutingstats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScoutingStatsData {
    ScoutingStatsData(int TotalRaidsEntered, int TotalRaidsExited)
    {
        this.TotalRaidsEntered=TotalRaidsEntered;
        this.TotalRaidsExited=TotalRaidsExited;
    }
    ScoutingStatsData()
    {
        this.TotalRaidsEntered=0;
        this.TotalRaidsExited=0;
    }

    @Expose
    @SerializedName("total-raids-entered")
    private final int TotalRaidsEntered;

    @Expose
    @SerializedName("total-raids-exited")
    private final int TotalRaidsExited;

    public int getTotalRaidsEntered()
    {
        return TotalRaidsEntered;
    }

    public int getTotalRaidsExited() {
        return TotalRaidsExited;
    }
}
