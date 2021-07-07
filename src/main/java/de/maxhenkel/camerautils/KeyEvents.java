package de.maxhenkel.camerautils;

import de.maxhenkel.camerautils.config.ClientConfig;
import de.maxhenkel.camerautils.gui.ThirdPersonCameraScreen;
import de.maxhenkel.camerautils.gui.ThirdPersonScreen;
import de.maxhenkel.camerautils.gui.ZoomScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class KeyEvents {

    private final Minecraft mc;

    public KeyEvents() {
        mc = Minecraft.getInstance();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.player == null) {
                return;
            }
            if (CameraUtils.ZOOM.consumeClick() && isCTRLDown()) {
                mc.setScreen(new ZoomScreen());
            }
            if (CameraUtils.THIRD_PERSON_DISTANCE.consumeClick() && isCTRLDown()) {
                mc.setScreen(new ThirdPersonScreen());
            }
            if (CameraUtils.THIRD_PERSON_CAM_1.consumeClick()) {
                if (isCTRLDown()) {
                    mc.setScreen(new ThirdPersonCameraScreen(0,
                            CameraUtils.CLIENT_CONFIG.thirdPersonOffsetX1,
                            CameraUtils.CLIENT_CONFIG.thirdPersonOffsetY1,
                            CameraUtils.CLIENT_CONFIG.thirdPersonOffsetZ1
                    ));
                } else {
                    onShoulderCam(0);
                }
            }
            if (CameraUtils.THIRD_PERSON_CAM_2.consumeClick()) {
                if (isCTRLDown()) {
                    mc.setScreen(new ThirdPersonCameraScreen(1,
                            CameraUtils.CLIENT_CONFIG.thirdPersonOffsetX2,
                            CameraUtils.CLIENT_CONFIG.thirdPersonOffsetY2,
                            CameraUtils.CLIENT_CONFIG.thirdPersonOffsetZ2
                    ));
                } else {
                    onShoulderCam(1);
                }
            }
            if (ClientConfig.thirdPersonCam >= 0 && mc.options.keyTogglePerspective.consumeClick()) {
                ClientConfig.thirdPersonCam = -1;
                mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            }
            if (CameraUtils.DETACH_CAMERA.consumeClick()) {
                toggleDetachCamera();
            }
        });
    }

    public boolean onScroll(double amount) {
        if (CameraUtils.THIRD_PERSON_DISTANCE.isDown() && !mc.options.getCameraType().isFirstPerson() && CameraUtils.CLIENT_CONFIG.thirdPersonCam < 0) {
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

    private void onShoulderCam(int value) {
        if (ClientConfig.thirdPersonCam == value) {
            ClientConfig.thirdPersonCam = -1;
        } else {
            ClientConfig.thirdPersonCam = value;
        }
        mc.options.setCameraType(CameraType.FIRST_PERSON);

        checkPostEffect();
    }

    private void toggleDetachCamera() {
        ClientConfig.detached = !ClientConfig.detached;

        if (ClientConfig.detached) {
            mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);

            if (!isCTRLDown() || Math.sqrt(mc.player.distanceToSqr(ClientConfig.x, ClientConfig.y, ClientConfig.z)) > 100F) {
                ClientConfig.xRot = mc.player.getViewXRot(0F);
                ClientConfig.yRot = mc.player.getViewYRot(0F);
                ClientConfig.x = mc.player.getX();
                ClientConfig.y = mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose());
                ClientConfig.z = mc.player.getZ();
            }
        } else {
            mc.options.setCameraType(CameraType.FIRST_PERSON);
        }
        checkPostEffect();
    }

    public void checkPostEffect() {
        mc.gameRenderer.checkEntityPostEffect(mc.options.getCameraType().isFirstPerson() ? mc.getCameraEntity() : null);
    }

    public boolean isCTRLDown() {
        return Screen.hasControlDown();
    }

}
