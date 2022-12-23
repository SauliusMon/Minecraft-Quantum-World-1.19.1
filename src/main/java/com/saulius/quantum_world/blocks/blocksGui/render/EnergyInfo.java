package com.saulius.quantum_world.blocks.blocksGui.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.saulius.quantum_world.QuantumWorld;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;
import java.util.function.Function;

public class EnergyInfo extends InfoArea {

    private final IEnergyStorage energy;

    public EnergyInfo(int x, int y, IEnergyStorage energy) {
        this(x, y, energy, 8, 64);
    }

    protected EnergyInfo(int x, int y, IEnergyStorage energy, int height, int width) {
        super(new Rect2i(x, y, width, height));
        this.energy = energy;
    }

    public List<Component> getToolTips() {
        return List.of(Component.literal(String.valueOf(energy.getEnergyStored())));
    }

    @Override
    public void draw(PoseStack transform) {
        final int height = area.getHeight();
        int stored = (int)(height*(energy.getEnergyStored()/(float)energy.getMaxEnergyStored()));
        fillGradient(
                transform,
                area.getX(),
                area.getY()+(height-stored),
                area.getX() + area.getWidth(),
                area.getY() +area.getHeight(),
                0xffb51500,
                0xff600b00
        );
    }
}
