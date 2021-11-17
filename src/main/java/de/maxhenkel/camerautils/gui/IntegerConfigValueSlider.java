package de.maxhenkel.camerautils.gui;

import de.maxhenkel.configbuilder.ConfigEntry;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Function;

public class IntegerConfigValueSlider extends AbstractSliderButton {

    private ConfigEntry<Integer> entry;
    private int min, max;
    private Function<Integer, Component> text;

    public IntegerConfigValueSlider(int x, int y, int width, int height, ConfigEntry<Integer> entry, int min, int max, Function<Integer, Component> text) {
        super(x, y, width, height, TextComponent.EMPTY, getPercentage(min, max, entry.get()));
        this.entry = entry;
        this.min = min;
        this.max = max;
        this.text = text;
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(getMsg());
    }

    public Component getMsg() {
        return text.apply(getValue(min, max, value));
    }

    @Override
    protected void applyValue() {
        entry.set(getValue(min, max, value)).save();
    }

    private static double getPercentage(int min, int max, int value) {
        return ((double) value - (double) min) / ((double) max - (double) min);
    }

    private static int getValue(int min, int max, double value) {
        return (int) ((double) min + value * ((double) max - (double) min));
    }
}
