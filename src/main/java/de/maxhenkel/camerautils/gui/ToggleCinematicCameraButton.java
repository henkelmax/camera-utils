package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ToggleCinematicCameraButton extends AbstractButton {

    public ToggleCinematicCameraButton(int i, int j, int k, int l) {
        super(i, j, k, l, TextComponent.EMPTY);
        updateText();
    }

    private void updateText() {
        if (CameraUtils.CLIENT_CONFIG.cinematicCamera.get()) {
            setMessage(new TranslatableComponent("message.camerautils.cinematic_camera.on"));
        } else {
            setMessage(new TranslatableComponent("message.camerautils.cinematic_camera.off"));
        }
    }

    @Override
    public void onPress() {
        CameraUtils.CLIENT_CONFIG.cinematicCamera.set(!CameraUtils.CLIENT_CONFIG.cinematicCamera.get());
        CameraUtils.CLIENT_CONFIG.cinematicCamera.save();
        updateText();
    }

    @Override
    public void updateNarration(NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }
}
