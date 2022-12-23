package com.saulius.quantum_world.networking.packets.energySync;

import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergy;
import com.saulius.quantum_world.networking.ModPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientRequestForEnergySync {

    private BlockPos blockPos;
    public ClientRequestForEnergySync(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public ClientRequestForEnergySync(FriendlyByteBuf friendlyByteBuf) {
        this.blockPos = friendlyByteBuf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.blockPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            AbstractModEnergy blockEntity = (AbstractModEnergy) supplier.get().getSender().getLevel().getBlockEntity(blockPos);
            ModPackets.sendToPlayer(new ClientEnergySync(blockPos, blockEntity.getEnergyStorage().getEnergyStored()), supplier.get().getSender());
        });
        return true;
    }
}
