package com.example.grayscalemod.network;

import com.example.grayscalemod.client.ClientEffectHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GrayscalePacket {
    public final boolean enable;

    public GrayscalePacket(boolean enable) { this.enable = enable; }

    public static void encode(GrayscalePacket msg, FriendlyByteBuf buf) {
        buf.writeBoolean(msg.enable);
    }

    public static GrayscalePacket decode(FriendlyByteBuf buf) {
        return new GrayscalePacket(buf.readBoolean());
    }

    public static void handle(GrayscalePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() ->
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
                () -> () -> ClientEffectHandler.apply(msg.enable))
        );
        ctx.get().setPacketHandled(true);
    }
}