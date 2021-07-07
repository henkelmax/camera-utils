package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.Utils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ZoomScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CameraUtils.MODID, "textures/gui/gui_camera.png");

    public ZoomScreen() {
        super(new TranslatableComponent("gui.camerautils.zoom.title"), TEXTURE, 248, 166);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.zoomSensitivity,
                0.01D,
                0.25D,
                0.01D,
                value -> new TranslatableComponent("message.camerautils.zoom_sensitivity_slider", Utils.round(value * 100D, 2))
        ));
    }

}
