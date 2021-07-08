package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    @Final
    public Options options;

    @Inject(at = @At("HEAD"), method = "handleKeybinds", cancellable = true)
    private void handleKeybinds(CallbackInfo info) {
        if (options.keySmoothCamera.consumeClick()) {
            if (!CameraUtils.KEY_EVENTS.onSmoothCameraClick()) {
                options.smoothCamera = !options.smoothCamera;
            }
        }
    }

}
