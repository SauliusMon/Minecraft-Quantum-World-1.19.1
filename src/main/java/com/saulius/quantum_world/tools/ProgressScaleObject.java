package com.saulius.quantum_world.tools;

public class ProgressScaleObject {

    private int maxProgress;
    private int scale;


    public ProgressScaleObject(int maxProgress) {
        this.maxProgress = maxProgress;
        scale = 0;
    }

    public void setScale (int scale) { this.scale = scale;}

    public int getScale () { return scale;}

    public void resetScale () { this.scale = maxProgress;}

    public int getMaxScale() { return maxProgress;}

    public void reduceScale() { this.scale--;}
}
