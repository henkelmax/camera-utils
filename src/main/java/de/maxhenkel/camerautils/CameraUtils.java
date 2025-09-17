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
import net.minecraft.resources.ResourceLocation;
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

    public static KeyMapping.Category CATEGORY_CAMERAUTILS;
    public static KeyMapping.Category CATEGORY_CAMERAUTILS_SETTINGS;

    public static KeyMapping ZOOM;
    public static KeyMapping THIRD_PERSON_CAM_1;
    public static KeyMapping THIRD_PERSON_CAM_2;
    public static KeyMapping THIRD_PERSON_OFFSET;
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

        CATEGORY_CAMERAUTILS = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(MODID, "camerautils"));
        CATEGORY_CAMERAUTILS_SETTINGS = KeyMapping.Category.register(ResourceLocation.fromNamespaceAndPath(MODID, "camerautils_settings"));

        ZOOM = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom", GLFW.GLFW_KEY_Z, CATEGORY_CAMERAUTILS));
        THIRD_PERSON_CAM_1 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_cam_1", GLFW.GLFW_KEY_F6, CATEGORY_CAMERAUTILS));
        THIRD_PERSON_CAM_2 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_cam_2", GLFW.GLFW_KEY_F7, CATEGORY_CAMERAUTILS));
        THIRD_PERSON_OFFSET = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_offset", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS));
        DETACH_CAMERA = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.detach_camera", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS));
        ZOOM_ANIMATION = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom_animation", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS));

        CINEMATIC_CAMERA_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.cinematic_camera_settings", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS_SETTINGS));
        THIRD_PERSON_CAMERA_1_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_camera_1_settings", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS_SETTINGS));
        THIRD_PERSON_CAMERA_2_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_camera_2_settings", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS_SETTINGS));
        THIRD_PERSON_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_settings", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS_SETTINGS));
        ZOOM_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom_settings", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS_SETTINGS));
        ZOOM_ANIMATION_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom_animation_settings", InputConstants.UNKNOWN.getValue(), CATEGORY_CAMERAUTILS_SETTINGS));

        Minecraft mc = Minecraft.getInstance();
        CLIENT_CONFIG = ConfigBuilder
                .builder(ClientConfig::new)
                .path(mc.gameDirectory.toPath().resolve("config").resolve(MODID).resolve("camerautils.properties"))
                .build();
    }
}
