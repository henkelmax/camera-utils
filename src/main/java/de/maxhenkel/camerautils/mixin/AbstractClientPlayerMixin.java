package de.maxhenkel.camerautils.mixin;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Inject(at = @At("RETURN"), method = "getFieldOfViewModifier", cancellable = true)
    private void getFieldOfViewModifier(CallbackInfoReturnable<Float> info) {
        info.setReturnValue(info.getReturnValue() * (CameraUtils.ZOOM.isDown() ? CameraUtils.CLIENT_CONFIG.zoom.get().floatValue() : 1F));
    }

}
