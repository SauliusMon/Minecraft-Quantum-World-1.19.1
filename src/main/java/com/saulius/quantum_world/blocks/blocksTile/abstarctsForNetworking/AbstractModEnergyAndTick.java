package com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking;

import com.saulius.quantum_world.tools.ProgressScaleObject;
import net.minecraftforge.energy.IEnergyStorage;

public interface AbstractModEnergyAndTick {

    public IEnergyStorage getEnergyStorage();

    public ProgressScaleObject getProgressScale();
}
