package de.maxhenkel.camerautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @ModifyReturnValue(at = @At("RETURN"), method = "getFieldOfViewModifier")
    private float getFieldOfViewModifier(float original) {
        if (CameraUtils.ZOOM_TRACK != null) {
            return CameraUtils.ZOOM_TRACK.getCurrentFOV();
        } else {
            return original * (CameraUtils.ZOOM.isDown() ? CameraUtils.CLIENT_CONFIG.zoom.get().floatValue() : 1F);
        }
    }

}
