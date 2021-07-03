package de.maxhenkel.camerautils.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.Utils;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class CameraScreen extends CameraScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CameraUtils.MODID, "textures/gui/gui_camera.png");


    public CameraScreen() {
        super(new TranslatableComponent("gui.camerautils.gui.title"), 248, 166);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ToggleCinematicCameraButton(guiLeft + 10, guiTop + 7 + font.lineHeight + 10, xSize / 2 - 20, 20));
        addRenderableWidget(new SmoothCameraSlider(guiLeft + xSize / 2 + 10, guiTop + 7 + font.lineHeight + 10, xSize / 2 - 20, 20));
        addRenderableWidget(new ConfigValueSlider(guiLeft + xSize / 2 + 10, guiTop + 7 + font.lineHeight + 10 + 25, xSize / 2 - 20, 20,
                CameraUtils.CLIENT_CONFIG.zoomSensitivity,
                0.01D,
                1D,
                value -> new TextComponent(String.valueOf(Utils.round(value, 2)))
        ));

        addRenderableWidget(new ConfigValueSlider(guiLeft + 80, guiTop + 7 + font.lineHeight + 10 + 25 * 2, (xSize - 100) / 2, 20,
                CameraUtils.CLIENT_CONFIG.shoulderCamOffsetX,
                -10D,
                0D,
                value -> new TextComponent("X: " + Utils.round(value, 2))
        ));
        addRenderableWidget(new ConfigValueSlider(guiLeft + 80 + (xSize - 100) / 2 + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 2, (xSize - 100) / 2, 20,
                CameraUtils.CLIENT_CONFIG.shoulderCamOffsetZ,
                -3D,
                3D,
                value -> new TextComponent("Z: " + Utils.round(value, 2))
        ));
    }


    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        int titleWidth = font.width(getTitle());
        font.draw(matrixStack, getTitle().getVisualOrderText(), guiLeft + (xSize - titleWidth) / 2, guiTop + 7, FONT_COLOR);

        font.draw(matrixStack, new TranslatableComponent("message.camerautils.zoom_setting").getVisualOrderText(), guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 + (20 - font.lineHeight) / 2F, FONT_COLOR);
        font.draw(matrixStack, new TranslatableComponent("message.camerautils.shoulder_cam_setting").getVisualOrderText(), guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 2 + (20 - font.lineHeight) / 2F, FONT_COLOR);
    }

}
