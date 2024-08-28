package de.maxhenkel.camerautils.config;

import de.maxhenkel.configbuilder.ConfigBuilder;
import de.maxhenkel.configbuilder.entry.ConfigEntry;

public class ClientConfig {

    public static final double MAX_ZOOM = 0.1D;
    public static final double MIN_ZOOM = 1.5D;

    public final ConfigEntry<Double> smoothness;
    public final ConfigEntry<Double> minSmoothValue;
    public final ConfigEntry<Double> maxSmoothValue;
    public final ConfigEntry<Double> cinematicCameraModifier;
    public final ConfigEntry<Double> zoom;
    public final ConfigEntry<Double> zoomSensitivity;
    public final ConfigEntry<Double> thirdPersonDistance;
    public final ConfigEntry<Double> thirdPersonDistanceSensitivity;
    public final ConfigEntry<Double> thirdPersonOffsetX1;
    public final ConfigEntry<Double> thirdPersonOffsetY1;
    public final ConfigEntry<Double> thirdPersonOffsetZ1;
    public final ConfigEntry<Double> thirdPersonRotationX1;
    public final ConfigEntry<Boolean> thirdPersonInverted1;
    public final ConfigEntry<Boolean> thirdPersonHideGui1;
    public final ConfigEntry<Double> thirdPersonOffsetX2;
    public final ConfigEntry<Double> thirdPersonOffsetY2;
    public final ConfigEntry<Double> thirdPersonOffsetZ2;
    public final ConfigEntry<Double> thirdPersonRotationX2;
    public final ConfigEntry<Boolean> thirdPersonInverted2;
    public final ConfigEntry<Boolean> thirdPersonHideGui2;
    public final ConfigEntry<ModifierKey> modifierKey;
    public final ConfigEntry<Double> guiOpacity;
    public final ConfigEntry<Double> zoomAnimationFrom;
    public final ConfigEntry<Double> zoomAnimationTo;
    public final ConfigEntry<Integer> zoomAnimationDuration;

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
        zoom = builder.doubleEntry("zoom", 0.1D, 0D, 2D);
        zoomSensitivity = builder.doubleEntry("zoom_sensitivity", 0.01D, 0.001D, 1D);
        thirdPersonDistance = builder.doubleEntry("third_person_zoom", 4D, 0D, 100D);
        thirdPersonDistanceSensitivity = builder.doubleEntry("third_person_zoom_sensitivity", 0.1D, 0.001D, 1D);
        thirdPersonOffsetX1 = builder.doubleEntry("third_person_cam_1_offset_x", -4D, -100D, 100D);
        thirdPersonOffsetY1 = builder.doubleEntry("third_person_cam_1_offset_y", 0D, -100D, 100D);
        thirdPersonOffsetZ1 = builder.doubleEntry("third_person_cam_1_offset_z", 0D, -100D, 100D);
        thirdPersonInverted1 = builder.booleanEntry("third_person_cam_1_inverted", false);
        thirdPersonHideGui1 = builder.booleanEntry("third_person_cam_1_hide_gui", false);
        thirdPersonRotationX1 = builder.doubleEntry("third_person_cam_1_rotation_x", 0D, -90D, 90D);
        thirdPersonOffsetX2 = builder.doubleEntry("third_person_cam_2_offset_x", -4D, -100D, 100D);
        thirdPersonOffsetY2 = builder.doubleEntry("third_person_cam_2_offset_y", 0D, -100D, 100D);
        thirdPersonOffsetZ2 = builder.doubleEntry("third_person_cam_2_offset_z", 0D, -100D, 100D);
        thirdPersonInverted2 = builder.booleanEntry("third_person_cam_2_inverted", false);
        thirdPersonHideGui2 = builder.booleanEntry("third_person_cam_2_hide_gui", false);
        thirdPersonRotationX2 = builder.doubleEntry("third_person_cam_2_rotation_x", 0D, -90D, 90D);
        modifierKey = builder.enumEntry("modifier_key", ModifierKey.RIGHT_ALT);
        guiOpacity = builder.doubleEntry("gui_opacity", 1D, 0D, 1D);
        zoomAnimationFrom = builder.doubleEntry("zoom_animation_from", 1D, 0.01D, 2D);
        zoomAnimationTo = builder.doubleEntry("zoom_animation_to", 0.1D, 0.01D, 2D);
        zoomAnimationDuration = builder.integerEntry("zoom_animation_duration", 200, 1, Integer.MAX_VALUE);
    }

    public static enum ModifierKey {
        CTRL, RIGHT_ALT
    }

}
