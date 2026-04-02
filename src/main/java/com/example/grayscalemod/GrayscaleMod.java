package com.example.grayscalemod;

import com.example.grayscalemod.command.GrayscaleCommand;
import com.example.grayscalemod.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.OnlyIn;

@Mod(GrayscaleMod.MODID)
public class GrayscaleMod {
    public static final String MODID = "grayscalemod";
    public static boolean grayscaleActive = false;

    private static final ResourceLocation EFFECT =
        new ResourceLocation("minecraft", "shaders/post/desaturate.json");

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

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!grayscaleActive) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.gameRenderer.currentEffect() == null) {
            mc.gameRenderer.loadEffect(EFFECT);
        }
    }
}
