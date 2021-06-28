package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class SmoothCameraSlider extends AbstractSliderButton {

    public SmoothCameraSlider(int x, int y, int width, int height) {
        super(x, y, width, height, TextComponent.EMPTY, CameraUtils.CLIENT_CONFIG.smoothness.get());
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(getMsg());
    }

    public Component getMsg() {
        if (value <= 0D) {
            return new TranslatableComponent("message.camerautils.smooth_value_off");
        } else {
            return new TranslatableComponent("message.camerautils.smooth_value", Math.round(value * 100F));
        }
    }

    @Override
    protected void applyValue() {
        CameraUtils.CLIENT_CONFIG.smoothness.set(value);
        CameraUtils.CLIENT_CONFIG.smoothness.save();
    }
}
