package com.example.grayscalemod.network;

import com.example.grayscalemod.GrayscaleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static final String PROTOCOL = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(GrayscaleMod.MODID, "main"),
        () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals
    );

    public static void register() {
        CHANNEL.registerMessage(0, GrayscalePacket.class,
            GrayscalePacket::encode,
            GrayscalePacket::decode,
            GrayscalePacket::handle
        );
    }
}