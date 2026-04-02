package com.example.grayscalemod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientEffectHandler {

    private static final ResourceLocation EFFECT =
        new ResourceLocation("minecraft", "shaders/post/desaturate.json");

    public static void apply(boolean enable) {
        Minecraft mc = Minecraft.getInstance();
        if (enable) {
            mc.gameRenderer.loadEffect(EFFECT);
        } else {
            mc.gameRenderer.shutdownEffect();
        }
    }
}
