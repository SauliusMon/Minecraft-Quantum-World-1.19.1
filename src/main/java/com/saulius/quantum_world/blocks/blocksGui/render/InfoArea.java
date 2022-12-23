package com.saulius.quantum_world.blocks.blocksGui.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.Rect2i;

public abstract class InfoArea extends GuiComponent {
    protected final Rect2i area;

    protected InfoArea(Rect2i area) {
        this.area = area;
    }

    public abstract void draw(PoseStack transform);
}
