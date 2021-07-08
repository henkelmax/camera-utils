package de.maxhenkel.camerautils.config;

public class ClientConfig {

    public final ConfigBuilder.ConfigEntry<Double> smoothness;
    public final ConfigBuilder.ConfigEntry<Double> minSmoothValue;
    public final ConfigBuilder.ConfigEntry<Double> maxSmoothValue;
    public final ConfigBuilder.ConfigEntry<Double> cinematicCameraModifier;
    public final ConfigBuilder.ConfigEntry<Double> zoom;
    public final ConfigBuilder.ConfigEntry<Double> zoomSensitivity;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonDistance;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonDistanceSensitivity;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetX1;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetY1;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetZ1;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetX2;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetY2;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetZ2;

    public static int thirdPersonCam = -1;

    // Detached Camera
    public static boolean detached;
    public static float xRot, yRot;
    public static double x, y, z;

    public ClientConfig(ConfigBuilder builder) {
        smoothness = builder.doubleEntry("smoothness", 0D, 0D, 1D);
        minSmoothValue = builder.doubleEntry("min_smoothness", 40D, 1D, 1000D);
        maxSmoothValue = builder.doubleEntry("max_smoothness", 100D, 1D, 1000D);
        cinematicCameraModifier = builder.doubleEntry("cinematic_camera_modifier", 1D, 0D, 1D);
        zoom = builder.doubleEntry("zoom", 0.1D, 0.001D, 2D);
        zoomSensitivity = builder.doubleEntry("zoom_sensitivity", 0.01D, 0.001D, 1D);
        thirdPersonDistance = builder.doubleEntry("third_person_zoom", 4D, 0D, 100D);
        thirdPersonDistanceSensitivity = builder.doubleEntry("third_person_zoom_sensitivity", 0.1D, 0.001D, 1D);
        thirdPersonOffsetX1 = builder.doubleEntry("third_person_cam_1_offset_x", -4D, -100D, 0D);
        thirdPersonOffsetY1 = builder.doubleEntry("third_person_cam_1_offset_y", 0D, -100D, 100D);
        thirdPersonOffsetZ1 = builder.doubleEntry("third_person_cam_1_offset_z", 0D, -100D, 100D);
        thirdPersonOffsetX2 = builder.doubleEntry("third_person_cam_2_offset_x", -4D, -100D, 0D);
        thirdPersonOffsetY2 = builder.doubleEntry("third_person_cam_2_offset_y", 0D, -100D, 100D);
        thirdPersonOffsetZ2 = builder.doubleEntry("third_person_cam_2_offset_z", 0D, -100D, 100D);
    }

}
