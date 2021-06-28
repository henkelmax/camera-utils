package de.maxhenkel.camerautils.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    private float fov;

    @Shadow
    private float oldFov;

    @Inject(at = @At("HEAD"), method = "tickFov", cancellable = true)
    private void getFieldOfViewModifier(CallbackInfo info) {
        info.cancel();
        float newFOV = 1F;
        if (minecraft.getCameraEntity() instanceof AbstractClientPlayer) {
            AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer) minecraft.getCameraEntity();
            newFOV = abstractClientPlayer.getFieldOfViewModifier();
        }

        oldFov = fov;
        fov += (newFOV - fov) * 0.5F;
        if (this.fov > 2F) {
            this.fov = 2F;
        }

        if (this.fov < 0.01F) {
            this.fov = 0.01F;
        }
    }

}
