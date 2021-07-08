package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.config.ConfigBuilder;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Function;

public class ConfigValueButton extends AbstractButton {

    private ConfigBuilder.ConfigEntry<Boolean> entry;
    private Function<Boolean, Component> component;

    public ConfigValueButton(int i, int j, int k, int l, ConfigBuilder.ConfigEntry<Boolean> entry, Function<Boolean, Component> component) {
        super(i, j, k, l, TextComponent.EMPTY);
        this.entry = entry;
        this.component = component;
        updateText();
    }

    private void updateText() {
        setMessage(component.apply(entry.get()));
    }

    @Override
    public void onPress() {
        entry.set(!entry.get());
        entry.save();
        updateText();
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        defaultButtonNarrationText(narrationElementOutput);
    }
}
