package de.maxhenkel.camerautils;

import com.mojang.blaze3d.platform.InputConstants;
import de.maxhenkel.camerautils.config.ClientConfig;
import de.maxhenkel.camerautils.config.ConfigBuilder;
import de.maxhenkel.camerautils.gui.CameraScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
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
    public static KeyMapping CAMERA_GUI;
    public static KeyMapping TOGGLE_SMOOTH_CAMERA;

    @Override
    public void onInitializeClient() {
        CAMERA_GUI = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.open_gui", GLFW.GLFW_KEY_O, "key.categories.camerautils"));
        TOGGLE_SMOOTH_CAMERA = KeyBindingHelper.registerKeyBinding(new KeyMapping("key.camerautils.toggle_smooth_camera", InputConstants.UNKNOWN.getValue(), "key.categories.camerautils"));
        Minecraft mc = Minecraft.getInstance();
        ConfigBuilder.create(mc.gameDirectory.toPath().resolve("config").resolve(MODID).resolve("camerautils.properties"), builder -> CLIENT_CONFIG = new ClientConfig(builder));

        new KeyEvents();
    }
}
