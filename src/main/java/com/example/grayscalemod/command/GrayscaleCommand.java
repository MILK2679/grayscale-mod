package com.example.grayscalemod.command;

import com.example.grayscalemod.network.GrayscalePacket;
import com.example.grayscalemod.network.ModNetworking;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GrayscaleCommand {

    private static final Set<UUID> grayscalePlayers = new HashSet<>();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("bw")
                .requires(src -> src.hasPermission(2))
                .then(Commands.argument("target", EntityArgument.player())
                    .executes(ctx -> {
                        ServerPlayer executor = ctx.getSource().getPlayerOrException();
                        ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
                        return toggle(executor, target);
                    })
                )
        );
    }

    private static int toggle(ServerPlayer executor, ServerPlayer target) {
        boolean enable = !grayscalePlayers.contains(executor.getUUID());

        if (enable) {
            grayscalePlayers.add(executor.getUUID());
            grayscalePlayers.add(target.getUUID());
        } else {
            grayscalePlayers.remove(executor.getUUID());
            grayscalePlayers.remove(target.getUUID());
        }

        GrayscalePacket packet = new GrayscalePacket(enable);
        ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> executor), packet);

        if (!executor.getUUID().equals(target.getUUID())) {
            ModNetworking.CHANNEL.send(PacketDistributor.PLAYER.with(() -> target), packet);
        }

        return 1;
    }
}
