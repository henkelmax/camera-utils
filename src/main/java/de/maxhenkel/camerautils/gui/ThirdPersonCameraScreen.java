package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.Utils;
import de.maxhenkel.camerautils.config.ConfigBuilder;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ThirdPersonCameraScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CameraUtils.MODID, "textures/gui/gui_camera.png");

    private ConfigBuilder.ConfigEntry<Double> offsetX;
    private ConfigBuilder.ConfigEntry<Double> offsetY;
    private ConfigBuilder.ConfigEntry<Double> offsetZ;
    private int slot;

    public ThirdPersonCameraScreen(int slot, ConfigBuilder.ConfigEntry<Double> offsetX, ConfigBuilder.ConfigEntry<Double> offsetY, ConfigBuilder.ConfigEntry<Double> offsetZ) {
        super(new TranslatableComponent("gui.camerautils.third_person_camera.title", slot + 1), TEXTURE, 248, 166);
        this.slot = slot;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10, xSize - 20, 20,
                offsetX,
                -10D,
                0D,
                0.1D,
                value -> new TranslatableComponent("message.camerautils.offset_x", Utils.round(value, 2))
        ));
        addRenderableWidget(new ConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25, xSize - 20, 20,
                offsetY,
                -5D,
                5D,
                0.1D,
                value -> new TranslatableComponent("message.camerautils.offset_y", Utils.round(value, 2))
        ));
        addRenderableWidget(new ConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 2, xSize - 20, 20,
                offsetZ,
                -5D,
                5D,
                0.1D,
                value -> new TranslatableComponent("message.camerautils.offset_z", Utils.round(value, 2))
        ));
        addRenderableWidget(new Button(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 3, xSize - 20, 20, new TranslatableComponent("message.camerautils.reset"), button -> {
            offsetX.reset();
            offsetX.save();
            offsetY.reset();
            offsetY.save();
            offsetZ.reset();
            offsetZ.save();
            minecraft.setScreen(new ThirdPersonCameraScreen(slot, offsetX, offsetY, offsetZ));
        }));
    }

}
