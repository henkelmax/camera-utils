package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;move(DDD)V", ordinal = 0))
    private void move(Camera camera, double x, double y, double z) {
        if (!CameraUtils.CLIENT_CONFIG.shoulderCam.get()) {
            move(-getMaxZoom(4D), 0D, 0D);
            return;
        }

        move(CameraUtils.CLIENT_CONFIG.shoulderCamOffsetX.get(), 0D, CameraUtils.CLIENT_CONFIG.shoulderCamOffsetZ.get());
    }

    @Shadow
    protected abstract void move(double d, double e, double f);

    @Shadow
    protected abstract double getMaxZoom(double d);

}
