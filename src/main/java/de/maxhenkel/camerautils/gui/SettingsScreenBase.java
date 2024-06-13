package de.maxhenkel.camerautils.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.joml.Matrix4f;

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
            renderTransparentBackground(guiGraphics);
        }

        colorBlit(guiGraphics, texture, guiLeft, guiTop, 0, 0, xSize, ySize, 256, 256, opacity);

        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        int titleWidth = font.width(getTitle());
        guiGraphics.drawString(font, getTitle().getVisualOrderText(), guiLeft + (xSize - titleWidth) / 2, guiTop + 7, FONT_COLOR, false);

        if (visibilityArea.isHovered(guiLeft, guiTop, mouseX, mouseY)) {
            guiGraphics.blit(VISIBILITY, guiLeft + xSize - 7 - 16, guiTop + 4, 16, 0, 16, 16, 32, 32);
        } else {
            guiGraphics.blit(VISIBILITY, guiLeft + xSize - 7 - 16, guiTop + 4, 0, 0, 16, 16, 32, 32);
        }
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {

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

    private void colorBlit(GuiGraphics guiGraphics, ResourceLocation resourceLocation, int i, int j, float f, float g, int k, int l, int m, int n, float alpha) {
        colorBlit(guiGraphics, resourceLocation, i, j, k, l, f, g, k, l, m, n, alpha);
    }

    private void colorBlit(GuiGraphics guiGraphics, ResourceLocation resourceLocation, int i, int j, int k, int l, float f, float g, int m, int n, int o, int p, float alpha) {
        colorBlit(guiGraphics, resourceLocation, i, i + k, j, j + l, 0, m, n, f, g, o, p, alpha);
    }

    private void colorBlit(GuiGraphics guiGraphics, ResourceLocation resourceLocation, int i, int j, int k, int l, int m, int n, int o, float f, float g, int p, int q, float alpha) {
        colorBlit(guiGraphics, resourceLocation, i, j, k, l, m, f / (float) p, (f + (float) n) / (float) p, g / (float) q, (g + (float) o) / (float) q, alpha);
    }

    private void colorBlit(GuiGraphics guiGraphics, ResourceLocation resourceLocation, int i, int j, int k, int l, int m, float f, float g, float h, float n, float alpha) {
        RenderSystem.setShaderTexture(0, resourceLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferBuilder.addVertex(matrix4f, (float) i, (float) k, (float) m).setColor(1F, 1F, 1F, alpha).setUv(f, h);
        bufferBuilder.addVertex(matrix4f, (float) i, (float) l, (float) m).setColor(1F, 1F, 1F, alpha).setUv(f, n);
        bufferBuilder.addVertex(matrix4f, (float) j, (float) l, (float) m).setColor(1F, 1F, 1F, alpha).setUv(g, n);
        bufferBuilder.addVertex(matrix4f, (float) j, (float) k, (float) m).setColor(1F, 1F, 1F, alpha).setUv(g, h);
        BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
        RenderSystem.disableBlend();
    }

}
