package de.maxhenkel.camerautils.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.config.ClientConfig;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickFov(CallbackInfo info) {
        if (CameraUtils.ZOOM_TRACK != null) {
            CameraUtils.ZOOM_TRACK.tick();
        }
    }

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void bobView(CameraRenderState cameraState, PoseStack poseStack, CallbackInfo info) {
        if (ClientConfig.detached) {
            info.cancel();
        }
    }

}
