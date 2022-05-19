package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.Utils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ThirdPersonScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CameraUtils.MODID, "textures/gui/generic_3.png");

    public ThirdPersonScreen() {
        super(Component.translatable("gui.camerautils.third_person_distance.title"), TEXTURE, 248, 104);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.thirdPersonDistanceSensitivity,
                0.1D,
                1D,
                0.1D,
                value -> Component.translatable("message.camerautils.third_person_distance_sensitivity_slider", Utils.round(value, 2))
        ));
        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.thirdPersonDistance,
                0D,
                100D,
                0.25D,
                value -> Component.translatable("message.camerautils.third_person_distance", Utils.round(value, 2))
        ));
        addRenderableWidget(new Button(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 2, xSize - 20, 20, Component.translatable("message.camerautils.reset"), button -> {
            CameraUtils.CLIENT_CONFIG.thirdPersonDistanceSensitivity.reset();
            CameraUtils.CLIENT_CONFIG.thirdPersonDistanceSensitivity.save();
            CameraUtils.CLIENT_CONFIG.thirdPersonDistance.reset();
            CameraUtils.CLIENT_CONFIG.thirdPersonDistance.save();
            minecraft.setScreen(new ThirdPersonScreen());
        }));
    }

}
