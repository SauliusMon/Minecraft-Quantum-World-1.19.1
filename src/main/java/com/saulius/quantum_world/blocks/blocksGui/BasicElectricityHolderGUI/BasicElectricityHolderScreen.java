package com.saulius.quantum_world.blocks.blocksGui.BasicElectricityHolderGUI;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.blocks.blocksGui.render.EnergyInfo;
import com.saulius.quantum_world.networking.ModPackets;
import com.saulius.quantum_world.networking.packets.energySync.ClientRequestForEnergySync;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicElectricityHolderScreen extends AbstractContainerScreen<BasicElectricityHolderMenu> {
    private static final ResourceLocation RESOURCE = new ResourceLocation(
            QuantumWorld.MODID, "textures/gui/basic_electricity_holder_gui.png");

    public BasicElectricityHolderScreen(BasicElectricityHolderMenu basicElectricityHolderMenu, Inventory inventory, Component component) {
        super(basicElectricityHolderMenu, inventory, component);
    }

    private EnergyInfo energyInfoArea;

    @Override
    public void init() {
        super.init();
        //assignEnergyInfoArea();
    }


//    private void assignEnergyInfoArea() {
//        int x = (width - imageWidth) / 2;                                                     // width height - screen parameters
//        int y = (height - imageHeight) / 2;
//
//        energyInfoArea = new EnergyInfo(x + 156, y + 13, menu.blockEntity.getEnergyStorage());
//    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int posX, int posY) {
        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RESOURCE);
        int imageLocX = (width - imageWidth) / 2;                                                              // width height - screen parameters
        int imageLocY = (height - imageHeight) / 2;

        this.blit(poseStack, imageLocX, imageLocY, 0, 0, imageWidth, imageHeight);            //Position of GUI

        ModPackets.sendToServer(new ClientRequestForEnergySync(menu.blockEntity.getBlockPos()));              //Doing 20 times per second
        redrawBatteries(poseStack, imageLocX, imageLocY);

    }
//     Format : PoseStack,
//     Image cords relative to screen X-Y
//     Pos to offset - X-Y
//     Image size - X-Y

    @Override
    protected void renderLabels(PoseStack poseStack, int notUsed0, int notUsed1) {
        font.draw(poseStack, menu.blockEntity.getDisplayName(), 76, 5, 0);

        String energyString = String.valueOf(menu.blockEntity.getEnergyStorage().getEnergyStored());
        int startOfEnergyStringX = 111;
        if (energyString.length() > 1)
            startOfEnergyStringX -= (energyString.length() - 1) * 6;

        font.draw(poseStack, energyString + " EM", startOfEnergyStringX, 77, 0);
    }

    private void redrawBatteries(PoseStack poseStack, int imageLocX, int imageLocY) {
        int energyAmount = menu.blockEntity.getEnergyStorage().getEnergyStored();
        int amountOfBatteriesFullyFilled = energyAmount / 10_000;
        int partiallyFilledBattery = energyAmount % 10_000;
        int energyAmountScale = Math.max(0, Math.min(60, partiallyFilledBattery / (menu.blockEntity.getEnergyStorage().getMaxEnergyStored() / 4 / 60)));

        // blit(start of rectangle being replaced X-Y start of rectangle to replace with X-Y, replacing rectangle size X-Y)
        switch (amountOfBatteriesFullyFilled) {
            case 0:
                this.blit(poseStack, imageLocX + 41, imageLocY + 74 - energyAmountScale, 176, 73 - energyAmountScale, 21, energyAmountScale);
                break;
            case 1:
                this.blit(poseStack, imageLocX + 41, imageLocY + 14, 176, 13, 21, 60);
                this.blit(poseStack, imageLocX + 64, imageLocY + 74 - energyAmountScale, 176, 73 - energyAmountScale, 21, energyAmountScale);
                break;
            case 2:
                this.blit(poseStack, imageLocX + 41, imageLocY + 14, 176, 13, 44, 60);
                this.blit(poseStack, imageLocX + 87, imageLocY + 74 - energyAmountScale, 176, 73 - energyAmountScale, 21, energyAmountScale);

                break;
            case 3:
                this.blit(poseStack, imageLocX + 41, imageLocY + 14, 176, 13, 67, 60);
                this.blit(poseStack, imageLocX + 110, imageLocY + 74 - energyAmountScale, 176, 73 - energyAmountScale, 21, energyAmountScale);
                break;
            case 4:
                this.blit(poseStack, imageLocX + 41, imageLocY + 14, 176, 13, 67, 60);
                this.blit(poseStack, imageLocX + 110, imageLocY + 14, 176, 13, 21, 60);
                break;
        }
    }

    @Override
    public void render(PoseStack poseStack, int posX, int posY, float delta) {
        renderBackground(poseStack);                                       // Background color when screen is open
        super.render(poseStack, posX, posY, delta);                        // Displayed menu parameters
        renderTooltip(poseStack, posX, posY);
    }
}
