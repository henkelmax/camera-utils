package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.config.ClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;move(DDD)V", ordinal = 0))
    private void move(Camera camera, double x, double y, double z) {
        if (ClientConfig.detached) {
            //Don't move with detached cam
        } else if (CameraUtils.CLIENT_CONFIG.shoulderCam.get()) {
            move(CameraUtils.CLIENT_CONFIG.shoulderCamOffsetX.get(), CameraUtils.CLIENT_CONFIG.shoulderCamOffsetY.get(), CameraUtils.CLIENT_CONFIG.shoulderCamOffsetZ.get());
        } else if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            move(-getMaxZoom(CameraUtils.CLIENT_CONFIG.thirdPersonDistance.get()), 0D, 0D);
        } else {
            move(-getMaxZoom(4D), 0D, 0D);
        }
    }

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setPosition(DDD)V", ordinal = 0))
    private void setPosition(Camera camera, double x, double y, double z) {
        if (ClientConfig.detached) {
            setRotation(ClientConfig.yRot, ClientConfig.xRot);
            setPosition(ClientConfig.x, ClientConfig.y, ClientConfig.z);
        } else {
            setPosition(x, y, z);
        }
    }

    @Shadow
    protected abstract void move(double d, double e, double f);

    @Shadow
    protected abstract double getMaxZoom(double d);

    @Shadow
    protected abstract void setRotation(float f, float g);

    @Shadow
    protected abstract void setPosition(double d, double e, double f);
}
