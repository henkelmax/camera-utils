package de.maxhenkel.camerautils.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.camerautils.config.ClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void getCameraEntity(AbstractClientPlayer abstractClientPlayer, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo info) {
        if (abstractClientPlayer == Minecraft.getInstance().player) {
            if (ClientConfig.hidePlayer) {
                info.cancel();
            }
        }
    }

}
