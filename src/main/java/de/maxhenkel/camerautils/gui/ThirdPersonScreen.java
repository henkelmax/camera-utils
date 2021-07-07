package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.Utils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ThirdPersonScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CameraUtils.MODID, "textures/gui/gui_camera.png");

    public ThirdPersonScreen() {
        super(new TranslatableComponent("gui.camerautils.third_person_distance.title"), TEXTURE, 248, 166);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.thirdPersonDistanceSensitivity,
                0.1D,
                1D,
                0.1D,
                value -> new TranslatableComponent("message.camerautils.third_person_distance_sensitivity_slider", Utils.round(value, 2))
        ));

        addRenderableWidget(new ConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.thirdPersonDistance,
                0D,
                100D,
                0.25D,
                value -> new TranslatableComponent("message.camerautils.third_person_distance", Utils.round(value, 2))
        ));
    }

}
