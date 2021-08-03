package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.config.ConfigBuilder;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Function;

public class DoubleConfigValueSlider extends AbstractSliderButton {

    private ConfigBuilder.ConfigEntry<Double> entry;
    private double min, max, interval;
    private Function<Double, Component> text;

    public DoubleConfigValueSlider(int x, int y, int width, int height, ConfigBuilder.ConfigEntry<Double> entry, double min, double max, double interval, Function<Double, Component> text) {
        super(x, y, width, height, TextComponent.EMPTY, getPercentage(min, max, entry.get()));
        this.entry = entry;
        this.min = min;
        this.max = max;
        this.interval = interval;
        this.text = text;
        updateMessage();
    }

    @Override
    protected void updateMessage() {
        setMessage(getMsg());
    }

    public Component getMsg() {
        return text.apply(getValue(min, max, interval, value));
    }

    @Override
    protected void applyValue() {
        entry.set(getValue(min, max, interval, value));
        entry.save();
    }

    private static double getPercentage(double min, double max, double value) {
        return (value - min) / (max - min);
    }

    private static double getValue(double min, double max, double interval, double value) {
        return snap(min + value * (max - min), interval);
    }

    private static double snap(double value, double interval) {
        return Math.round(value / interval) * interval;
    }
}
