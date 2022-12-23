package com.saulius.quantum_world.networking;

import com.saulius.quantum_world.QuantumWorld;
import com.saulius.quantum_world.networking.packets.energyAndScaleSync.ClientEnergyAndScaleSync;
import com.saulius.quantum_world.networking.packets.energyAndScaleSync.ClientRequestForEnergyAndScaleSync;
import com.saulius.quantum_world.networking.packets.energySync.ClientEnergySync;
import com.saulius.quantum_world.networking.packets.energySync.ClientRequestForEnergySync;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModPackets {
    private static SimpleChannel INSTANCE;

    private static int Id = 0;
    private static int returnId() {
        return Id++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(QuantumWorld.MODID, "packets")).networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(c -> true).serverAcceptedVersions(s -> true).simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ClientRequestForEnergySync.class, returnId(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ClientRequestForEnergySync::new).encoder(ClientRequestForEnergySync::toBytes)
                .consumerMainThread(ClientRequestForEnergySync::handle).add();

        net.messageBuilder(ClientEnergySync.class, returnId(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientEnergySync::new).encoder(ClientEnergySync::toBytes)
                .consumerMainThread(ClientEnergySync::handle).add();

        net.messageBuilder(ClientRequestForEnergyAndScaleSync.class, returnId(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ClientRequestForEnergyAndScaleSync::new).encoder(ClientRequestForEnergyAndScaleSync::toBytes)
                .consumerMainThread(ClientRequestForEnergyAndScaleSync::handle).add();

        net.messageBuilder(ClientEnergyAndScaleSync.class, returnId(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientEnergyAndScaleSync::new).encoder(ClientEnergyAndScaleSync::toBytes)
                .consumerMainThread(ClientEnergyAndScaleSync::handle).add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }


    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
