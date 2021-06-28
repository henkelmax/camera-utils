package de.maxhenkel.camerautils.mixin;

import com.mojang.blaze3d.Blaze3D;
import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.util.SmoothDouble;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    @Final
    private SmoothDouble smoothTurnX;
    @Shadow
    @Final
    private SmoothDouble smoothTurnY;
    @Shadow
    private double accumulatedDX;
    @Shadow
    private double accumulatedDY;
    @Shadow
    private double lastMouseEventTime;

    @Inject(at = @At("HEAD"), method = "turnPlayer", cancellable = true)
    private void turnPlayer(CallbackInfo info) {
        if (!isMouseGrabbed() || !minecraft.isWindowActive() || !CameraUtils.CLIENT_CONFIG.cinematicCamera.get() || minecraft.player == null) {
            return;
        }
        Double smoothness = CameraUtils.CLIENT_CONFIG.smoothness.get();
        Double min = CameraUtils.CLIENT_CONFIG.minSmoothSliderValue.get();
        Double max = CameraUtils.CLIENT_CONFIG.maxSmoothSliderValue.get();

        if (smoothness <= 0D) {
            return;
        }

        info.cancel();

        double time = Blaze3D.getTime();
        double deltaTime = time - lastMouseEventTime;
        lastMouseEventTime = time;
        double sensitivity = minecraft.options.sensitivity * 0.6D + 0.2D;
        double sensCubed = sensitivity * sensitivity * sensitivity;
        double h = sensCubed * 8D;

        boolean inSpyGlass = minecraft.options.getCameraType().isFirstPerson() && minecraft.player.isScoping();

        double smoothedX = smoothTurnX.getNewDeltaValue(accumulatedDX * (inSpyGlass ? sensCubed : h), deltaTime * h * toValue(smoothness, min, max));
        double smoothedY = smoothTurnY.getNewDeltaValue(accumulatedDY * (inSpyGlass ? sensCubed : h), deltaTime * h * toValue(smoothness, min, max));

        accumulatedDX = 0D;
        accumulatedDY = 0D;

        minecraft.player.turn(smoothedX, smoothedY * (minecraft.options.invertYMouse ? -1D : 1D));
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

    @Shadow
    public abstract boolean isMouseGrabbed();

    private static double toValue(double d, double min, double max) {
        return min + (max - min) * (1D - d);
    }

}
