package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ZoomAnimationScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CameraUtils.MODID, "textures/gui/generic_4.png");

    public ZoomAnimationScreen() {
        super(Component.translatable("gui.camerautils.zoom_animation.title"), TEXTURE, 248, 129);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.zoomAnimationFrom,
                0.01D,
                2D,
                0.01D,
                value -> Component.translatable("message.camerautils.zoom_animation_from", ((int) (value * 100D)))
        ));
        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.zoomAnimationTo,
                0.01D,
                2D,
                0.01D,
                value -> Component.translatable("message.camerautils.zoom_animation_to", ((int) (value * 100D)))
        ));
        addRenderableWidget(new IntegerConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 2, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.zoomAnimationDuration,
                1,
                20 * 30,
                value -> Component.translatable("message.camerautils.zoom_animation_duration", value)
        ));
        addRenderableWidget(Button.builder(Component.translatable("message.camerautils.reset"), button -> {
            CameraUtils.CLIENT_CONFIG.zoomAnimationFrom.reset();
            CameraUtils.CLIENT_CONFIG.zoomAnimationFrom.save();
            CameraUtils.CLIENT_CONFIG.zoomAnimationTo.reset();
            CameraUtils.CLIENT_CONFIG.zoomAnimationTo.save();
            CameraUtils.CLIENT_CONFIG.zoomAnimationDuration.reset();
            CameraUtils.CLIENT_CONFIG.zoomAnimationDuration.save();
            minecraft.setScreen(new ZoomAnimationScreen());
        }).bounds(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 3, xSize - 20, 20).build());
    }

}
