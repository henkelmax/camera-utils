package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Option;
import net.minecraft.client.Options;
import net.minecraft.client.ProgressOption;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(SimpleOptionsSubScreen.class)
public class SimpleOptionsSubScreenMixin {

    @Shadow
    @Final
    @Mutable
    private Option[] smallOptions;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(Screen screen, Options options, Component component, Option[] options2, CallbackInfo info) {
        List<Option> optionsList = new ArrayList<>(Arrays.asList(smallOptions));
        optionsList.add(new ProgressOption("options.camerautils.movement_smoothness", 0D, 1D, 0F, (opts) -> {
            return CameraUtils.CLIENT_CONFIG.smoothness.get();
        }, (opts, value) -> {
            CameraUtils.CLIENT_CONFIG.smoothness.set(value);
            CameraUtils.CLIENT_CONFIG.smoothness.save();
        }, (opts, progressOption) -> {
            double value = progressOption.get(opts);
            if (value <= 0) {
                return new TranslatableComponent("message.camerautils.smooth_value_off");
            }
            return new TranslatableComponent("message.camerautils.smooth_value", (int) (value * 100D));
        }));
        smallOptions = optionsList.toArray(new Option[0]);
    }
}
