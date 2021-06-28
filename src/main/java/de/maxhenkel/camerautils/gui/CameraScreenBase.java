package de.maxhenkel.camerautils.gui;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CameraScreenBase extends Screen {

    protected static final int FONT_COLOR = 4210752;

    protected int guiLeft;
    protected int guiTop;
    protected int xSize;
    protected int ySize;

    protected CameraScreenBase(Component title, int xSize, int ySize) {
        super(title);
        this.xSize = xSize;
        this.ySize = ySize;
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (width - this.xSize) / 2;
        this.guiTop = (height - this.ySize) / 2;
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }

}
