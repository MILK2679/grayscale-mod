package com.example.grayscalemod;

import com.example.grayscalemod.command.GrayscaleCommand;
import com.example.grayscalemod.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GrayscaleMod.MODID)
public class GrayscaleMod {
    public static final String MODID = "grayscalemod";
    public static boolean grayscaleActive = false;
    public static long transitionStart = 0;
    public static boolean transitionIn = true;

    private static final float DURATION_MS = 1000f;
    private static final ResourceLocation EFFECT =
        new ResourceLocation(MODID, "shaders/post/grayscale.json");

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
        Minecraft mc = Minecraft.getInstance();

        float elapsed = (System.currentTimeMillis() - transitionStart) / DURATION_MS;
        float progress = Math.min(elapsed, 1.0f);

        if (grayscaleActive) {
            if (mc.gameRenderer.currentEffect() == null) {
                mc.gameRenderer.loadEffect(EFFECT);
            }
        } else {
            // 淡出结束后彻底关闭
            if (!transitionIn && progress >= 1.0f) {
                if (mc.gameRenderer.currentEffect() != null) {
                    mc.gameRenderer.shutdownEffect();
                }
            }
        }
    }

    public static float getProgress() {
        float elapsed = (System.currentTimeMillis() - transitionStart) / DURATION_MS;
        float p = Math.min(elapsed, 1.0f);
        // ease in-out
        p = p * p * (3f - 2f * p);
        return transitionIn ? p : 1.0f - p;
    }
}
