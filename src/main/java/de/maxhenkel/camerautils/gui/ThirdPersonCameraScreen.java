package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.Utils;
import de.maxhenkel.camerautils.config.ConfigBuilder;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class ThirdPersonCameraScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(CameraUtils.MODID, "textures/gui/generic_7.png");

    private ConfigBuilder.ConfigEntry<Double> offsetX;
    private ConfigBuilder.ConfigEntry<Double> offsetY;
    private ConfigBuilder.ConfigEntry<Double> offsetZ;
    private ConfigBuilder.ConfigEntry<Double> rotationX;
    private ConfigBuilder.ConfigEntry<Boolean> inverted;
    private ConfigBuilder.ConfigEntry<Boolean> hideGui;
    private int slot;

    public ThirdPersonCameraScreen(int slot,
                                   ConfigBuilder.ConfigEntry<Double> offsetX,
                                   ConfigBuilder.ConfigEntry<Double> offsetY,
                                   ConfigBuilder.ConfigEntry<Double> offsetZ,
                                   ConfigBuilder.ConfigEntry<Double> rotationX,
                                   ConfigBuilder.ConfigEntry<Boolean> inverted,
                                   ConfigBuilder.ConfigEntry<Boolean> hideGui
    ) {
        super(new TranslatableComponent("gui.camerautils.third_person_camera.title", slot + 1), TEXTURE, 248, 204);
        this.slot = slot;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.rotationX = rotationX;
        this.inverted = inverted;
        this.hideGui = hideGui;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10, xSize - 20, 20,
                offsetX,
                -10D,
                10D,
                0.1D,
                value -> new TranslatableComponent("message.camerautils.offset_x", Utils.round(value, 2))
        ));
        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25, xSize - 20, 20,
                offsetY,
                -5D,
                5D,
                0.1D,
                value -> new TranslatableComponent("message.camerautils.offset_y", Utils.round(value, 2))
        ));
        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 2, xSize - 20, 20,
                offsetZ,
                -5D,
                5D,
                0.1D,
                value -> new TranslatableComponent("message.camerautils.offset_z", Utils.round(value, 2))
        ));
        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 3, xSize - 20, 20,
                rotationX,
                -180D,
                180D,
                1D,
                value -> new TranslatableComponent("message.camerautils.rotation_x", value.intValue())
        ));
        addRenderableWidget(new ConfigValueButton(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 4, xSize - 20, 20,
                inverted,
                value -> new TranslatableComponent("message.camerautils.inverted", value)
        ));
        addRenderableWidget(new ConfigValueButton(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 5, xSize - 20, 20,
                hideGui,
                value -> new TranslatableComponent("message.camerautils.hide_gui", value)
        ));
        addRenderableWidget(new Button(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 6, xSize - 20, 20, new TranslatableComponent("message.camerautils.reset"), button -> {
            offsetX.reset();
            offsetX.save();
            offsetY.reset();
            offsetY.save();
            offsetZ.reset();
            offsetZ.save();
            rotationX.reset();
            rotationX.save();
            inverted.reset();
            inverted.save();
            hideGui.reset();
            hideGui.save();
            minecraft.setScreen(new ThirdPersonCameraScreen(slot, offsetX, offsetY, offsetZ, rotationX, inverted, hideGui));
        }));
    }

}
