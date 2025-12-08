package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class CinematicCameraScreen extends SettingsScreenBase {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(CameraUtils.MODID, "textures/gui/generic_2.png");

    public CinematicCameraScreen() {
        super(Component.translatable("gui.camerautils.cinematic_camera.title"), TEXTURE, 248, 79);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10, xSize - 20, 20,
                CameraUtils.CLIENT_CONFIG.cinematicCameraModifier,
                0D,
                1D,
                0.05D,
                value -> Component.translatable("message.camerautils.cinematic_camera_modifier", (int) (value * 100D))
        ));
        addRenderableWidget(Button.builder(Component.translatable("message.camerautils.reset"), button -> {
            CameraUtils.CLIENT_CONFIG.cinematicCameraModifier.reset();
            CameraUtils.CLIENT_CONFIG.cinematicCameraModifier.save();
            minecraft.setScreen(new CinematicCameraScreen());
        }).bounds(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25, xSize - 20, 20).build());
    }

}
