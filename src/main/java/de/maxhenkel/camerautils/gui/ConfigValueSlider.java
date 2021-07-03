package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.config.ConfigBuilder;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Function;

public class ConfigValueSlider extends AbstractSliderButton {

    private ConfigBuilder.ConfigEntry<Double> entry;
    private double min, max;
    private Function<Double, Component> text;

    public ConfigValueSlider(int x, int y, int width, int height, ConfigBuilder.ConfigEntry<Double> entry, double min, double max, Function<Double, Component> text) {
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
        entry.set(getValue(min, max, value));
        entry.save();
    }

    private static double getPercentage(double min, double max, double value) {
        return (value - min) / (max - min);
    }

    private static double getValue(double min, double max, double value) {
        return min + value * (max - min);
    }
}
