package com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking;

import net.minecraftforge.energy.IEnergyStorage;

public interface AbstractModEnergyAndTick {

    public IEnergyStorage getEnergyStorage();
    public int getScale();

    public void setScale(int scale);
}
