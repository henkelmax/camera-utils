package de.maxhenkel.camerautils.config;

public class ClientConfig {

    public final ConfigBuilder.ConfigEntry<Double> smoothness;
    public final ConfigBuilder.ConfigEntry<Double> minSmoothSliderValue;
    public final ConfigBuilder.ConfigEntry<Double> maxSmoothSliderValue;
    public final ConfigBuilder.ConfigEntry<Double> zoom;
    public final ConfigBuilder.ConfigEntry<Double> zoomSensitivity;
    public final ConfigBuilder.ConfigEntry<Boolean> cinematicCamera;

    public ClientConfig(ConfigBuilder builder) {
        smoothness = builder.doubleEntry("smoothness", 1D, 0D, 1D);
        minSmoothSliderValue = builder.doubleEntry("min_slider_smoothness", 50D, 1D, 1000D);
        maxSmoothSliderValue = builder.doubleEntry("max_slider_smoothness", 100D, 1D, 1000D);
        cinematicCamera = builder.booleanEntry("cinematic_camera", false);
        zoom = builder.doubleEntry("zoom", 0.1D, 0.001D, 2D);
        zoomSensitivity = builder.doubleEntry("zoom_sensitivity", 0.01D, 0.001D, 1D);
    }

}
