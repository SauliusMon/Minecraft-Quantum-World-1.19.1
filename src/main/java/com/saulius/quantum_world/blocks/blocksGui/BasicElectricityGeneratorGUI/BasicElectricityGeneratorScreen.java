package com.saulius.quantum_world.blocks.blocksGui.BasicElectricityGeneratorGUI;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.blocks.blocksGui.render.EnergyInfo;
import com.saulius.quantum_world.networking.ModPackets;
import com.saulius.quantum_world.networking.packets.energyAndScaleSync.ClientRequestForEnergyAndScaleSync;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BasicElectricityGeneratorScreen extends AbstractContainerScreen<BasicElectricityGeneratorMenu> {

    private static final ResourceLocation RESOURCE = new ResourceLocation(
            QuantumWorld.MODID, "textures/gui/basic_electricity_generator_gui.png");

    public BasicElectricityGeneratorScreen(BasicElectricityGeneratorMenu basicElectricityGeneratorMenu, Inventory inventory, Component component) {
        super(basicElectricityGeneratorMenu, inventory, component);
    }

    private EnergyInfo energyInfoArea;

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int posX, int posY) {
        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, RESOURCE);
        int imageLocX = (width - imageWidth) / 2;                                                              // width height - screen parameters
        int imageLocY = (height - imageHeight) / 2;

        this.blit(poseStack, imageLocX, imageLocY, 0, 0, imageWidth, imageHeight);            //Position of GUI

        ModPackets.sendToServer(new ClientRequestForEnergyAndScaleSync(menu.blockEntity.getBlockPos()));      //Doing 20 times per second
        redrawBattery(poseStack, imageLocX, imageLocY);
    }
//     Format : PoseStack,
//     Image cords relative to screen X-Y
//     Pos to offset - X-Y
//     Image size - X-Y

    @Override
    protected void renderLabels(PoseStack poseStack, int notUsed0, int notUsed1) {
        font.draw(poseStack, menu.blockEntity.getDisplayName(), 76, 5, 0);

        String energyString = String.valueOf(menu.blockEntity.getEnergyStorage().getEnergyStored());
        int startOfEnergyStringX = 58;
        if (energyString.length() > 1)
            startOfEnergyStringX -= (energyString.length() - 1) * 6;

        font.draw(poseStack, energyString, startOfEnergyStringX, 74, 0);
    }

    private void redrawBattery(PoseStack poseStack, int imageLocX, int imageLocY) {
        int energyAmount = menu.blockEntity.getEnergyStorage().getEnergyStored();
        int energyAmountScale = Math.max(0, Math.min(60, energyAmount / (menu.blockEntity.getEnergyStorage().getMaxEnergyStored() / 60)));

        this.blit(poseStack, imageLocX + 41, imageLocY + 68 - energyAmountScale, 176, 73 - energyAmountScale, 21, energyAmountScale);
    }

    @Override
    public void render(PoseStack poseStack, int posX, int poxY, float delta) {
        renderBackground(poseStack);                                       // Background color when screen is open
        super.render(poseStack, posX, poxY, delta);                        // Displayed menu parameters
        renderTooltip(poseStack, posX, poxY);                              // ToolTip when mouse hovers on item in slot
    }
}
