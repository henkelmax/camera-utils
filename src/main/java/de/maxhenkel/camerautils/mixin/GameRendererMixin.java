package de.maxhenkel.camerautils.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private float fov;

    @Shadow
    private float oldFov;

    @Inject(at = @At("HEAD"), method = "tickFov")
    private void tickFov(CallbackInfo info) {
        if (CameraUtils.ZOOM_TRACK != null) {
            CameraUtils.ZOOM_TRACK.tick();
        }
    }
    @ModifyConstant(method = "tickFov", constant = @Constant(floatValue = 1.5F))
    private float increaaseFloatValue(float constant) {
        return 2.0F;
    }


    @Inject(at = @At("HEAD"), method = "bobView", cancellable = true)
    private void bobView(PoseStack poseStack, float f, CallbackInfo info) {
        if (ClientConfig.detached) {
            info.cancel();
        }
    }

}
