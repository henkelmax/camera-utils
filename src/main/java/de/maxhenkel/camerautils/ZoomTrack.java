package de.maxhenkel.camerautils;

import net.minecraft.util.Mth;

public class ZoomTrack {

    private float from, to;
    private int totalTime, time;

    public ZoomTrack(float from, float to, int totalTime) {
        this.from = from;
        this.to = to;
        this.totalTime = totalTime;
    }

    public void tick() {
        time++;
    }

    public float getCurrentFOV() {
        float perc = Mth.clamp((float) time / (float) totalTime, 0F, 1F);
        return Mth.lerp(perc, from, to);
    }

}
