package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.CameraType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CameraType.class)
public class CameraTypeMixin {

    @Inject(method = "isFirstPerson", at = @At("HEAD"), cancellable = true)
    private void isFirstPerson(CallbackInfoReturnable<Boolean> ci) {
        if (CameraUtils.CLIENT_CONFIG.shoulderCam.get()) {
            ci.setReturnValue(false);
        }
    }

    @Inject(method = "isMirrored", at = @At("HEAD"), cancellable = true)
    private void isMirrored(CallbackInfoReturnable<Boolean> ci) {
        if (CameraUtils.CLIENT_CONFIG.shoulderCam.get()) {
            ci.setReturnValue(false);
        }
    }

}
