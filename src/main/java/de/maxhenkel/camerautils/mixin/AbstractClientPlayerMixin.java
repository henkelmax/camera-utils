package de.maxhenkel.camerautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.config.ClientConfig;
import net.minecraft.client.player.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @ModifyReturnValue(at = @At("RETURN"), method = "getFieldOfViewModifier")
    private float getFieldOfViewModifier(float original) {
        if (CameraUtils.ZOOM_TRACK != null) {
            return CameraUtils.ZOOM_TRACK.getCurrentFOV();
        } else {
            return original * (CameraUtils.ZOOM.isDown() ? (float) convert(CameraUtils.CLIENT_CONFIG.zoom.get()) : 1F);
        }
    }

    @Unique
    private double convert(double value) {
        if (value >= 2D) {
            return ClientConfig.MIN_ZOOM;
        } else if (value <= 0D) {
            return ClientConfig.MAX_ZOOM;
        } else if (value > 1D) {
            double factor = value - 1D;
            return 1D + factor * (ClientConfig.MIN_ZOOM - 1D);
        } else if (value < 1D) {
            return ClientConfig.MAX_ZOOM + value * (1D - ClientConfig.MAX_ZOOM);
        }
        return value;
    }

}
