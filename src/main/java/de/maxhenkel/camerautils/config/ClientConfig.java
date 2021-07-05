package de.maxhenkel.camerautils.config;

public class ClientConfig {

    public final ConfigBuilder.ConfigEntry<Double> smoothness;
    public final ConfigBuilder.ConfigEntry<Double> minSmoothSliderValue;
    public final ConfigBuilder.ConfigEntry<Double> maxSmoothSliderValue;
    public final ConfigBuilder.ConfigEntry<Double> zoom;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonDistance;
    public final ConfigBuilder.ConfigEntry<Double> zoomSensitivity;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonDistanceSensitivity;
    public final ConfigBuilder.ConfigEntry<Boolean> cinematicCamera;
    public final ConfigBuilder.ConfigEntry<Boolean> shoulderCam;
    public final ConfigBuilder.ConfigEntry<Double> shoulderCamOffsetX;
    public final ConfigBuilder.ConfigEntry<Double> shoulderCamOffsetZ;

    public ClientConfig(ConfigBuilder builder) {
        smoothness = builder.doubleEntry("smoothness", 1D, 0D, 1D);
        minSmoothSliderValue = builder.doubleEntry("min_slider_smoothness", 50D, 1D, 1000D);
        maxSmoothSliderValue = builder.doubleEntry("max_slider_smoothness", 100D, 1D, 1000D);
        zoom = builder.doubleEntry("zoom", 0.1D, 0.001D, 2D);
        thirdPersonDistance = builder.doubleEntry("third_person_zoom", 4D, 0D, 100D);
        cinematicCamera = builder.booleanEntry("cinematic_camera", false);
        zoomSensitivity = builder.doubleEntry("zoom_sensitivity", 0.01D, 0.001D, 1D);
        thirdPersonDistanceSensitivity = builder.doubleEntry("third_person_zoom_sensitivity", 0.1D, 0.001D, 1D);
        shoulderCam = builder.booleanEntry("shoulder_cam", false);
        shoulderCamOffsetX = builder.doubleEntry("shoulder_cam_offset_x", -2D, -100D, 100D);
        shoulderCamOffsetZ = builder.doubleEntry("shoulder_cam_offset_z", 1D, -100D, 100D);
    }

}
