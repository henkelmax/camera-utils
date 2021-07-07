package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import net.minecraft.util.SmoothDouble;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Redirect(method = "turnPlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;sensitivity:D", opcode = Opcodes.GETFIELD))
    private double sensitivity(Options options) {
        if (!CameraUtils.ZOOM.isDown()) {
            return options.sensitivity;
        }

        return options.sensitivity * Math.min(1D, CameraUtils.CLIENT_CONFIG.zoom.get());
    }

    @Redirect(method = "turnPlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;smoothCamera:Z", opcode = Opcodes.GETFIELD))
    private boolean smoothCamera(Options options) {
        return options.smoothCamera || CameraUtils.CLIENT_CONFIG.smoothness.get() > 0D;
    }

    @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/SmoothDouble;getNewDeltaValue(DD)D"))
    private double getNewDeltaValue(SmoothDouble smoothDouble, double d1, double d2) {
        if (minecraft.options.smoothCamera) {
            return smoothDouble.getNewDeltaValue(d1, d2);
        } else {
            double smoothness = CameraUtils.CLIENT_CONFIG.smoothness.get();
            double min = CameraUtils.CLIENT_CONFIG.minSmoothValue.get();
            double max = CameraUtils.CLIENT_CONFIG.maxSmoothValue.get();
            if (smoothness <= 0D) {
                return smoothDouble.getNewDeltaValue(d1, d2);
            }
            return smoothDouble.getNewDeltaValue(d1, d2 * toValue(smoothness, min, max));
        }
    }

    @Inject(at = @At("HEAD"), method = "onScroll", cancellable = true)
    private void onScroll(long window, double d, double amount, CallbackInfo info) {
        if (window != Minecraft.getInstance().getWindow().getWindow()) {
            return;
        }
        if (CameraUtils.KEY_EVENTS.onScroll(amount)) {
            info.cancel();
        }
    }

    private static double toValue(double d, double min, double max) {
        return min + (max - min) * (1D - d);
    }

}
