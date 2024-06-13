package de.maxhenkel.camerautils.gui;

import de.maxhenkel.camerautils.CameraUtils;
import de.maxhenkel.camerautils.Utils;
import de.maxhenkel.configbuilder.entry.ConfigEntry;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ThirdPersonCameraScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CameraUtils.MODID, "textures/gui/generic_7.png");

    private ConfigEntry<Double> offsetX;
    private ConfigEntry<Double> offsetY;
    private ConfigEntry<Double> offsetZ;
    private ConfigEntry<Double> rotationX;
    private ConfigEntry<Boolean> inverted;
    private ConfigEntry<Boolean> hideGui;
    private int slot;

    public ThirdPersonCameraScreen(int slot,
                                   ConfigEntry<Double> offsetX,
                                   ConfigEntry<Double> offsetY,
                                   ConfigEntry<Double> offsetZ,
                                   ConfigEntry<Double> rotationX,
                                   ConfigEntry<Boolean> inverted,
                                   ConfigEntry<Boolean> hideGui
    ) {
        super(Component.translatable("gui.camerautils.third_person_camera.title", slot + 1), TEXTURE, 248, 204);
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
                value -> Component.translatable("message.camerautils.offset_x", Utils.round(value, 2))
        ));
        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25, xSize - 20, 20,
                offsetY,
                -5D,
                5D,
                0.1D,
                value -> Component.translatable("message.camerautils.offset_y", Utils.round(value, 2))
        ));
        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 2, xSize - 20, 20,
                offsetZ,
                -5D,
                5D,
                0.1D,
                value -> Component.translatable("message.camerautils.offset_z", Utils.round(value, 2))
        ));
        addRenderableWidget(new DoubleConfigValueSlider(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 3, xSize - 20, 20,
                rotationX,
                -180D,
                180D,
                1D,
                value -> Component.translatable("message.camerautils.rotation_x", value.intValue())
        ));
        addRenderableWidget(new ConfigValueButton(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 4, xSize - 20, 20,
                inverted,
                value -> Component.translatable("message.camerautils.inverted", value)
        ));
        addRenderableWidget(new ConfigValueButton(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 5, xSize - 20, 20,
                hideGui,
                value -> Component.translatable("message.camerautils.hide_gui", value)
        ));
        addRenderableWidget(Button.builder(Component.translatable("message.camerautils.reset"), button -> {
            offsetX.reset().save();
            offsetY.reset().save();
            offsetZ.reset().save();
            rotationX.reset().save();
            inverted.reset().save();
            hideGui.reset().save();
            minecraft.setScreen(new ThirdPersonCameraScreen(slot, offsetX, offsetY, offsetZ, rotationX, inverted, hideGui));
        }).bounds(guiLeft + 10, guiTop + 7 + font.lineHeight + 10 + 25 * 6, xSize - 20, 20).build());
    }

}
