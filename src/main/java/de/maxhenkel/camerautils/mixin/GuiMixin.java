package de.maxhenkel.camerautils.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void renderCrosshair(PoseStack poseStack, CallbackInfo ci) {
        if (CameraUtils.CLIENT_CONFIG.shoulderCam.get()) {
            ci.cancel();
        }
    }

}
