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
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonRotationX1;
    public final ConfigBuilder.ConfigEntry<Boolean> thirdPersonInverted1;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetX2;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetY2;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonOffsetZ2;
    public final ConfigBuilder.ConfigEntry<Double> thirdPersonRotationX2;
    public final ConfigBuilder.ConfigEntry<Boolean> thirdPersonInverted2;
    public final ConfigBuilder.ConfigEntry<ModifierKey> modifierKey;
    public final ConfigBuilder.ConfigEntry<Double> guiOpacity;

    public static int thirdPersonCam = -1;

    // Detached Camera
    public static boolean detached;
    public static float xRot, yRot;
    public static double x, y, z;

    public static boolean hidePlayer;

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
        thirdPersonInverted1 = builder.booleanEntry("third_person_cam_1_inverted", false);
        thirdPersonRotationX1 = builder.doubleEntry("third_person_cam_1_rotation_x", 0D, -90D, 90D);
        thirdPersonOffsetX2 = builder.doubleEntry("third_person_cam_2_offset_x", -4D, -100D, 0D);
        thirdPersonOffsetY2 = builder.doubleEntry("third_person_cam_2_offset_y", 0D, -100D, 100D);
        thirdPersonOffsetZ2 = builder.doubleEntry("third_person_cam_2_offset_z", 0D, -100D, 100D);
        thirdPersonInverted2 = builder.booleanEntry("third_person_cam_2_rinverted", false);
        thirdPersonRotationX2 = builder.doubleEntry("third_person_cam_2_rotation_x", 0D, -90D, 90D);
        modifierKey = builder.enumEntry("modifier_key", ModifierKey.RIGHT_ALT);
        guiOpacity = builder.doubleEntry("gui_opacity", 1D, 0D, 1D);
    }

    public static enum ModifierKey {
        CTRL, RIGHT_ALT
    }

}
