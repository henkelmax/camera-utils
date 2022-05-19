package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SimpleOptionsSubScreen;
import net.minecraft.network.chat.Component;
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
    private OptionInstance<?>[] smallOptions;

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(Screen screen, Options options, Component component, OptionInstance<?>[] options2, CallbackInfo info) {
        List<OptionInstance<?>> optionsList = new ArrayList<>(Arrays.asList(smallOptions));
        optionsList.add(new OptionInstance<>("options.camerautils.movement_smoothness", OptionInstance.noTooltip(), SimpleOptionsSubScreenMixin::label, OptionInstance.UnitDouble.INSTANCE, CameraUtils.CLIENT_CONFIG.smoothness.get(), (value) -> {
            CameraUtils.CLIENT_CONFIG.smoothness.set(value);
            CameraUtils.CLIENT_CONFIG.smoothness.save();
        }));
        smallOptions = optionsList.toArray(new OptionInstance[0]);
    }

    private static Component label(Component component, double value) {
        if (value <= 0) {
            return Component.translatable("message.camerautils.smooth_value_off");
        }
        return Component.translatable("message.camerautils.smooth_value", (int) (value * 100D));
    }
}
