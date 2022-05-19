package de.maxhenkel.camerautils.gui;

import de.maxhenkel.configbuilder.ConfigEntry;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

public class ConfigValueButton extends AbstractButton {

    private ConfigEntry<Boolean> entry;
    private Function<Boolean, Component> component;

    public ConfigValueButton(int i, int j, int k, int l, ConfigEntry<Boolean> entry, Function<Boolean, Component> component) {
        super(i, j, k, l, Component.literal(""));
        this.entry = entry;
        this.component = component;
        updateText();
    }

    private void updateText() {
        setMessage(component.apply(entry.get()));
    }

    @Override
    public void onPress() {
        entry.set(!entry.get()).save();
        updateText();
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        defaultButtonNarrationText(narrationElementOutput);
    }
}
