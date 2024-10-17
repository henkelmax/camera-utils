package de.maxhenkel.camerautils;

import com.mojang.blaze3d.platform.InputConstants;
import de.maxhenkel.camerautils.config.ClientConfig;
import de.maxhenkel.configbuilder.ConfigBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class CameraUtils implements ClientModInitializer {

    public static final String MODID = "camerautils";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static ClientConfig CLIENT_CONFIG;
    public static KeyEvents KEY_EVENTS;
    public static ZoomTrack ZOOM_TRACK;

    public static KeyMapping ZOOM;
    public static KeyMapping THIRD_PERSON_CAM_1;
    public static KeyMapping THIRD_PERSON_CAM_2;
    public static KeyMapping THIRD_PERSON_DISTANCE;
    public static KeyMapping DETACH_CAMERA;
    public static KeyMapping ZOOM_ANIMATION;

    public static KeyMapping CINEMATIC_CAMERA_GUI;
    public static KeyMapping THIRD_PERSON_CAMERA_1_GUI;
    public static KeyMapping THIRD_PERSON_CAMERA_2_GUI;
    public static KeyMapping THIRD_PERSON_GUI;
    public static KeyMapping ZOOM_GUI;
    public static KeyMapping ZOOM_ANIMATION_GUI;

    @Override
    public void onInitializeClient() {
        KEY_EVENTS = new KeyEvents();

        ZOOM = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom", GLFW.GLFW_KEY_Z, "key.categories.camerautils"));
        THIRD_PERSON_CAM_1 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_cam_1", GLFW.GLFW_KEY_F6, "key.categories.camerautils"));
        THIRD_PERSON_CAM_2 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_cam_2", GLFW.GLFW_KEY_F7, "key.categories.camerautils"));
        THIRD_PERSON_DISTANCE = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_distance", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils"));
        DETACH_CAMERA = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.detach_camera", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils"));
        ZOOM_ANIMATION = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom_animation", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils"));

        CINEMATIC_CAMERA_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.cinematic_camera_settings", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils_settings"));
        THIRD_PERSON_CAMERA_1_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_camera_1_settings", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils_settings"));
        THIRD_PERSON_CAMERA_2_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_camera_2_settings", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils_settings"));
        THIRD_PERSON_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_settings", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils_settings"));
        ZOOM_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom_settings", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils_settings"));
        ZOOM_ANIMATION_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom_animation_settings", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils_settings"));

        Minecraft mc = Minecraft.getInstance();
        CLIENT_CONFIG = ConfigBuilder
                .builder(ClientConfig::new)
                .path(mc.gameDirectory.toPath().resolve("config").resolve(MODID).resolve("camerautils.properties"))
                .build();
    }
}
