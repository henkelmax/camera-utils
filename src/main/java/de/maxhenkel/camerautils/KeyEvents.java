package de.maxhenkel.camerautils;

import com.mojang.blaze3d.platform.InputConstants;
import de.maxhenkel.camerautils.config.ClientConfig;
import de.maxhenkel.camerautils.gui.*;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class KeyEvents {

    private static final Minecraft mc = Minecraft.getInstance();

    public KeyEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.player == null) {
                return;
            }
            if (CameraUtils.ZOOM.consumeClick() && isModifierDown()) {
                openZoomSettings();
            }
            if (CameraUtils.THIRD_PERSON_OFFSET.consumeClick() && isModifierDown()) {
                openThirdPersonSettings();
            }
            if (CameraUtils.THIRD_PERSON_CAM_1.consumeClick()) {
                if (isModifierDown()) {
                    openThirdPerson1Settings();
                } else {
                    onShoulderCam(0);
                }
            }
            if (CameraUtils.THIRD_PERSON_CAM_2.consumeClick()) {
                if (isModifierDown()) {
                    openThirdPerson2Settings();
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
            if (CameraUtils.ZOOM_ANIMATION.consumeClick()) {
                if (isModifierDown()) {
                    openZoomAnimationSettings();
                } else {
                    if (CameraUtils.ZOOM_TRACK == null) {
                        CameraUtils.ZOOM_TRACK = new ZoomTrack(CameraUtils.CLIENT_CONFIG.zoomAnimationFrom.get().floatValue(), CameraUtils.CLIENT_CONFIG.zoomAnimationTo.get().floatValue(), CameraUtils.CLIENT_CONFIG.zoomAnimationDuration.get());
                    } else {
                        CameraUtils.ZOOM_TRACK = null;
                    }
                }
            }

            if (CameraUtils.CINEMATIC_CAMERA_GUI.consumeClick()) {
                openCinematicCameraSettings();
            }
            if (CameraUtils.THIRD_PERSON_CAMERA_1_GUI.consumeClick()) {
                openThirdPerson1Settings();
            }
            if (CameraUtils.THIRD_PERSON_CAMERA_2_GUI.consumeClick()) {
                openThirdPerson2Settings();
            }
            if (CameraUtils.THIRD_PERSON_GUI.consumeClick()) {
                openThirdPersonSettings();
            }
            if (CameraUtils.ZOOM_GUI.consumeClick()) {
                openZoomSettings();
            }
            if (CameraUtils.ZOOM_ANIMATION_GUI.consumeClick()) {
                openZoomAnimationSettings();
            }
        });
    }

    public boolean onScroll(double amount) {
        if (CameraUtils.THIRD_PERSON_OFFSET.isDown() && !mc.options.getCameraType().isFirstPerson() && ClientConfig.thirdPersonCam < 0) {
            double distance = CameraUtils.CLIENT_CONFIG.thirdPersonOffset.get();
            double sensitivity = CameraUtils.CLIENT_CONFIG.thirdPersonOffsetSensitivity.get();
            distance = Math.max(-32D, Math.min(32D, distance + (-amount * sensitivity)));
            CameraUtils.CLIENT_CONFIG.thirdPersonOffset.set(distance);
            CameraUtils.CLIENT_CONFIG.thirdPersonOffset.save();
            mc.player.displayClientMessage(Component.translatable("message.camerautils.third_person_offset", Utils.round(distance, 2)), true);
            return true;
        }
        if (CameraUtils.ZOOM.isDown()) {
            double zoom = CameraUtils.CLIENT_CONFIG.zoom.get();
            double zoomSensitivity = CameraUtils.CLIENT_CONFIG.zoomSensitivity.get();
            zoom = Math.max(0D, Math.min(2D, zoom + (-amount * zoomSensitivity)));
            CameraUtils.CLIENT_CONFIG.zoom.set(zoom);
            CameraUtils.CLIENT_CONFIG.zoom.save();
            mc.player.displayClientMessage(Component.translatable("message.camerautils.zoom", Math.round((1D - zoom) * 100D)), true);
            return true;
        }
        return false;
    }

    public boolean onSmoothCameraClick() {
        if (isModifierDown()) {
            openCinematicCameraSettings();
            return true;
        }

        return false;
    }

    private void onShoulderCam(int value) {
        if (ClientConfig.thirdPersonCam <= -1) {
            ClientConfig.detached = false;
        }

        if (ClientConfig.thirdPersonCam == value) {
            ClientConfig.thirdPersonCam = -1;
            if (value == 0) {
                if (CameraUtils.CLIENT_CONFIG.thirdPersonHideGui1.get()) {
                    mc.options.hideGui = false;
                }
            } else if (value == 1) {
                if (CameraUtils.CLIENT_CONFIG.thirdPersonHideGui2.get()) {
                    mc.options.hideGui = false;
                }
            }
        } else {
            ClientConfig.thirdPersonCam = value;
            if (value == 0) {
                if (CameraUtils.CLIENT_CONFIG.thirdPersonHideGui1.get()) {
                    mc.options.hideGui = true;
                }
            } else if (value == 1) {
                if (CameraUtils.CLIENT_CONFIG.thirdPersonHideGui2.get()) {
                    mc.options.hideGui = true;
                }
            }
        }
        mc.options.setCameraType(CameraType.FIRST_PERSON);

        checkPostEffect();
    }

    private void toggleDetachCamera() {
        ClientConfig.detached = !ClientConfig.detached;

        if (ClientConfig.detached) {
            ClientConfig.thirdPersonCam = -1;
            mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);

            if (!isModifierDown() || Math.sqrt(mc.player.distanceToSqr(ClientConfig.x, ClientConfig.y, ClientConfig.z)) > 100F) {
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

    public void openCinematicCameraSettings() {
        mc.setScreen(new CinematicCameraScreen());
    }

    public void openThirdPerson1Settings() {
        mc.setScreen(new ThirdPersonCameraScreen(0,
                CameraUtils.CLIENT_CONFIG.thirdPersonOffsetX1,
                CameraUtils.CLIENT_CONFIG.thirdPersonOffsetY1,
                CameraUtils.CLIENT_CONFIG.thirdPersonOffsetZ1,
                CameraUtils.CLIENT_CONFIG.thirdPersonRotationX1,
                CameraUtils.CLIENT_CONFIG.thirdPersonInverted1,
                CameraUtils.CLIENT_CONFIG.thirdPersonHideGui1
        ));
    }

    public void openThirdPerson2Settings() {
        mc.setScreen(new ThirdPersonCameraScreen(1,
                CameraUtils.CLIENT_CONFIG.thirdPersonOffsetX2,
                CameraUtils.CLIENT_CONFIG.thirdPersonOffsetY2,
                CameraUtils.CLIENT_CONFIG.thirdPersonOffsetZ2,
                CameraUtils.CLIENT_CONFIG.thirdPersonRotationX2,
                CameraUtils.CLIENT_CONFIG.thirdPersonInverted2,
                CameraUtils.CLIENT_CONFIG.thirdPersonHideGui2
        ));
    }

    public void openThirdPersonSettings() {
        mc.setScreen(new ThirdPersonScreen());
    }

    public void openZoomSettings() {
        mc.setScreen(new ZoomScreen());
    }

    public void openZoomAnimationSettings() {
        mc.setScreen(new ZoomAnimationScreen());
    }

    public static boolean isModifierDown() {
        if (CameraUtils.CLIENT_CONFIG.modifierKey.get().equals(ClientConfig.ModifierKey.CTRL)) {
            return Screen.hasControlDown();
        } else {
            return InputConstants.isKeyDown(mc.getWindow().getWindow(), GLFW.GLFW_KEY_RIGHT_ALT);
        }
    }

}
