package com.example.grayscalemod;

import com.example.grayscalemod.command.GrayscaleCommand;
import com.example.grayscalemod.network.ModNetworking;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GrayscaleMod.MODID)
public class GrayscaleMod {
    public static final String MODID = "grayscalemod";

    public GrayscaleMod() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        ModNetworking.register();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        GrayscaleCommand.register(event.getDispatcher());
    }
}