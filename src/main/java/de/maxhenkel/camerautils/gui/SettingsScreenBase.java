package de.maxhenkel.camerautils.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

public class SettingsScreenBase extends CameraScreenBase {

    private static final ResourceLocation VISIBILITY = new ResourceLocation(CameraUtils.MODID, "textures/visibility.png");
    protected ResourceLocation texture;

    private HoverArea visibilityArea;
    private float opacity;

    public SettingsScreenBase(Component title, ResourceLocation texture, int xSize, int ySize) {
        super(title, xSize, ySize);
        this.texture = texture;
        this.opacity = CameraUtils.CLIENT_CONFIG.guiOpacity.get().floatValue();
    }

    @Override
    protected void init() {
        super.init();
        visibilityArea = new HoverArea(xSize - 7 - 16, 4, 16, 16);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, opacity);
        RenderSystem.setShaderTexture(0, texture);
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        int titleWidth = font.width(getTitle());
        font.draw(matrixStack, getTitle().getVisualOrderText(), guiLeft + (xSize - titleWidth) / 2, guiTop + 7, FONT_COLOR);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, VISIBILITY);
        if (visibilityArea.isHovered(guiLeft, guiTop, mouseX, mouseY)) {
            blit(matrixStack, guiLeft + xSize - 7 - 16, guiTop + 4, 16, 0, 16, 16, 32, 32);
        } else {
            blit(matrixStack, guiLeft + xSize - 7 - 16, guiTop + 4, 0, 0, 16, 16, 32, 32);
        }
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        if (visibilityArea.isHovered(guiLeft, guiTop, (int) d, (int) e)) {
            opacity -= 0.25F;
            if (opacity < 0F) {
                opacity = 1F;
            }
            CameraUtils.CLIENT_CONFIG.guiOpacity.set((double) opacity);
            CameraUtils.CLIENT_CONFIG.guiOpacity.save();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F));
            return true;
        }
        return super.mouseClicked(d, e, i);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
