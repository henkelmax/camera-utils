package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.config.ClientConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    private float xRot;
    @Shadow
    private float yRot;

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;move(FFF)V", ordinal = 0))
    private void move(Camera instance, float f, float g, float h) {
        if (ClientConfig.detached) {
            //Don't move with detached cam
        } else if (ClientConfig.thirdPersonCam == 0) {
            boolean inverted = CameraUtils.CLIENT_CONFIG.thirdPersonInverted1.get();
            move(CameraUtils.CLIENT_CONFIG.thirdPersonOffsetX1.get().floatValue(), CameraUtils.CLIENT_CONFIG.thirdPersonOffsetY1.get().floatValue(), CameraUtils.CLIENT_CONFIG.thirdPersonOffsetZ1.get().floatValue());
            setRotation(yRot + (inverted ? 180F : 0F), CameraUtils.CLIENT_CONFIG.thirdPersonRotationX1.get().floatValue() + (inverted ? -xRot : xRot));
        } else if (ClientConfig.thirdPersonCam == 1) {
            boolean inverted = CameraUtils.CLIENT_CONFIG.thirdPersonInverted2.get();
            move(CameraUtils.CLIENT_CONFIG.thirdPersonOffsetX2.get().floatValue(), CameraUtils.CLIENT_CONFIG.thirdPersonOffsetY2.get().floatValue(), CameraUtils.CLIENT_CONFIG.thirdPersonOffsetZ2.get().floatValue());
            setRotation(yRot + (inverted ? 180F : 0F), CameraUtils.CLIENT_CONFIG.thirdPersonRotationX2.get().floatValue() + (inverted ? -xRot : xRot));
        } else if (!Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            move(-getMaxZoom(CameraUtils.CLIENT_CONFIG.thirdPersonDistance.get().floatValue()), 0F, 0F);
        } else {
            move(-getMaxZoom(4F), 0F, 0F);
        }
    }

    @Inject(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setPosition(DDD)V", shift = At.Shift.AFTER))
    private void setPosition(CallbackInfo ci) {
        if (ClientConfig.detached) {
            setRotation(ClientConfig.yRot, ClientConfig.xRot);
            setPosition(ClientConfig.x, ClientConfig.y, ClientConfig.z);
        }
    }

    @Shadow
    protected abstract void move(float d, float e, float f);

    @Shadow
    protected abstract float getMaxZoom(float d);

    @Shadow
    protected abstract void setRotation(float f, float g);

    @Shadow
    protected abstract void setPosition(double d, double e, double f);

}
