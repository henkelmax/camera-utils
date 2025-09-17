package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ARGB;

public class SettingsScreenBase extends CameraScreenBase {

    private static final ResourceLocation VISIBILITY = ResourceLocation.fromNamespaceAndPath(CameraUtils.MODID, "textures/visibility.png");
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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (opacity >= 1F) {
            renderBlurredBackground(guiGraphics);
        }

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, guiLeft, guiTop, 0F, 0F, xSize, ySize, xSize, ySize, 256, 256, ARGB.colorFromFloat(opacity, 1F, 1F, 1F));

        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        int titleWidth = font.width(getTitle());
        guiGraphics.drawString(font, getTitle().getVisualOrderText(), guiLeft + (xSize - titleWidth) / 2, guiTop + 7, FONT_COLOR, false);

        if (visibilityArea.isHovered(guiLeft, guiTop, mouseX, mouseY)) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, VISIBILITY, guiLeft + xSize - 7 - 16, guiTop + 4, 16, 0, 16, 16, 32, 32);
        } else {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, VISIBILITY, guiLeft + xSize - 7 - 16, guiTop + 4, 0, 0, 16, 16, 32, 32);
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {

    }

    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        if (visibilityArea.isHovered(guiLeft, guiTop, (int) mouseButtonEvent.x(), (int) mouseButtonEvent.y())) {
            opacity -= 0.25F;
            if (opacity < 0F) {
                opacity = 1F;
            }
            CameraUtils.CLIENT_CONFIG.guiOpacity.set((double) opacity);
            CameraUtils.CLIENT_CONFIG.guiOpacity.save();
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1F));
            return true;
        }
        return super.mouseClicked(mouseButtonEvent, bl);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
