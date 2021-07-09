package de.maxhenkel.camerautils;

import com.mojang.blaze3d.platform.InputConstants;
import de.maxhenkel.camerautils.config.ClientConfig;
import de.maxhenkel.camerautils.config.ConfigBuilder;
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
    public static KeyMapping ZOOM;
    public static KeyMapping THIRD_PERSON_CAM_1;
    public static KeyMapping THIRD_PERSON_CAM_2;
    public static KeyMapping THIRD_PERSON_DISTANCE;
    public static KeyMapping DETACH_CAMERA;

    @Override
    public void onInitializeClient() {
        KEY_EVENTS = new KeyEvents();

        ZOOM = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.zoom", GLFW.GLFW_KEY_Z, "key.categories.camerautils"));
        THIRD_PERSON_CAM_1 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_cam_1", GLFW.GLFW_KEY_F6, "key.categories.camerautils"));
        THIRD_PERSON_CAM_2 = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_cam_2", GLFW.GLFW_KEY_F7, "key.categories.camerautils"));
        THIRD_PERSON_DISTANCE = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.third_person_distance", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils"));
        DETACH_CAMERA = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.detach_camera", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils"));
        Minecraft mc = Minecraft.getInstance();
        ConfigBuilder.create(mc.gameDirectory.toPath().resolve("config").resolve(MODID).resolve("camerautils.properties"), builder -> CLIENT_CONFIG = new ClientConfig(builder));
    }
}
