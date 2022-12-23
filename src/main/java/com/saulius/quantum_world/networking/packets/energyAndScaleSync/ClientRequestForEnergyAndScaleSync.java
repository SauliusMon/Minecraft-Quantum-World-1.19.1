package com.saulius.quantum_world.networking.packets.energyAndScaleSync;

import com.saulius.quantum_world.blocks.blocksTile.abstarctsForNetworking.AbstractModEnergyAndTick;
import com.saulius.quantum_world.networking.ModPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientRequestForEnergyAndScaleSync {
    private BlockPos blockPos;

    public ClientRequestForEnergyAndScaleSync(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public ClientRequestForEnergyAndScaleSync(FriendlyByteBuf friendlyByteBuf) {
        this.blockPos = friendlyByteBuf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.blockPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            AbstractModEnergyAndTick blockEntity = (AbstractModEnergyAndTick) supplier.get().getSender().getLevel().getBlockEntity(blockPos);
            ModPackets.sendToPlayer(new ClientEnergyAndScaleSync(blockPos, blockEntity.getEnergyStorage().getEnergyStored(), blockEntity.getScale()), supplier.get().getSender());
        });
        return true;
    }
}
