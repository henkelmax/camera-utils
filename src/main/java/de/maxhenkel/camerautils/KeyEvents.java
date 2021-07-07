package de.maxhenkel.camerautils;

import de.maxhenkel.camerautils.config.ClientConfig;
import de.maxhenkel.camerautils.gui.CameraScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.CameraType;
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
            if (CameraUtils.SHOULDER_CAM.consumeClick()) {
                onShoulderCam();
            }
            if (CameraUtils.DETACH_CAMERA.consumeClick()) {
                toggleDetachCamera();
            }
        });
    }

    public boolean onScroll(double amount) {
        if (CameraUtils.THIRD_PERSON_DISTANCE.isDown() && !mc.options.getCameraType().isFirstPerson() && !CameraUtils.CLIENT_CONFIG.shoulderCam.get()) {
            double zoom = CameraUtils.CLIENT_CONFIG.thirdPersonDistance.get();
            double zoomSensitivity = CameraUtils.CLIENT_CONFIG.thirdPersonDistanceSensitivity.get();
            zoom = Math.max(0.001, Math.min(100, zoom + (-amount * zoomSensitivity)));
            CameraUtils.CLIENT_CONFIG.thirdPersonDistance.set(zoom);
            CameraUtils.CLIENT_CONFIG.thirdPersonDistance.save();
            mc.player.displayClientMessage(new TranslatableComponent("message.camerautils.third_person_distance", Utils.round(zoom, 2)), true);
            return true;
        }
        if (CameraUtils.ZOOM.isDown()) {
            double zoom = CameraUtils.CLIENT_CONFIG.zoom.get();
            double zoomSensitivity = CameraUtils.CLIENT_CONFIG.zoomSensitivity.get();
            zoom = Math.max(0.001, Math.min(2, zoom + (-amount * zoomSensitivity)));
            CameraUtils.CLIENT_CONFIG.zoom.set(zoom);
            CameraUtils.CLIENT_CONFIG.zoom.save();
            mc.player.displayClientMessage(new TranslatableComponent("message.camerautils.zoom", Math.round((1D - zoom) * 100D)), true);
            return true;
        }
        return false;
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

    private void onShoulderCam() {
        boolean newValue = !CameraUtils.CLIENT_CONFIG.shoulderCam.get();
        CameraUtils.CLIENT_CONFIG.shoulderCam.set(newValue);
        CameraUtils.CLIENT_CONFIG.shoulderCam.save();
        if (!newValue) {
            mc.options.setCameraType(CameraType.FIRST_PERSON);
        }
    }

    private void toggleDetachCamera() {
        ClientConfig.detached = !ClientConfig.detached;

        if (ClientConfig.detached) {
            mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            ClientConfig.xRot = mc.player.getViewXRot(0F);
            ClientConfig.yRot = mc.player.getViewYRot(0F);
            ClientConfig.x = mc.player.getX();
            ClientConfig.y = mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose());
            ClientConfig.z = mc.player.getZ();
        } else {
            mc.options.setCameraType(CameraType.FIRST_PERSON);
        }
    }

}
