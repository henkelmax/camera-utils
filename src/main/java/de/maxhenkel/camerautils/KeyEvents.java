package de.maxhenkel.camerautils;

import de.maxhenkel.camerautils.gui.CameraScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;

public class KeyEvents {

    private final Minecraft mc;

    public KeyEvents() {
        mc = Minecraft.getInstance();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.player == null) {
                return;
            }
            if (CameraUtils.CAMERA_GUI.consumeClick()) {
                mc.setScreen(new CameraScreen());
            }
            if (CameraUtils.TOGGLE_SMOOTH_CAMERA.consumeClick()) {
                toggleSmoothCamera();
            }
        });
    }

    private void toggleSmoothCamera() {
        boolean newValue = !CameraUtils.CLIENT_CONFIG.cinematicCamera.get();
        CameraUtils.CLIENT_CONFIG.cinematicCamera.set(newValue);
        CameraUtils.CLIENT_CONFIG.cinematicCamera.save();
        if (newValue) {
            mc.player.displayClientMessage(new TranslatableComponent("message.camerautils.cinematic_camera.on"), true);
        } else {
            mc.player.displayClientMessage(new TranslatableComponent("message.camerautils.cinematic_camera.off"), true);
        }
    }

}
