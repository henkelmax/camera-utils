package de.maxhenkel.camerautils.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class CameraScreen extends CameraScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CameraUtils.MODID, "textures/gui/gui_camera.png");


    public CameraScreen() {
        super(new TranslatableComponent("gui.camerautils.gui.title"), 195, 76);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ToggleCinematicCameraButton(guiLeft + 10, guiTop + ySize - 10 - 20 - 5 - 20, xSize - 20, 20));
        addRenderableWidget(new SmoothCameraSlider(guiLeft + 10, guiTop + ySize - 10 - 20, xSize - 20, 20));
    }


    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        int titleWidth = font.width(getTitle());
        font.draw(matrixStack, getTitle().getVisualOrderText(), (float) (guiLeft + (xSize - titleWidth) / 2), guiTop + 7, FONT_COLOR);

    }

}
