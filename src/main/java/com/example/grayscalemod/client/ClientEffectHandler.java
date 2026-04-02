package com.example.grayscalemod.client;

import com.example.grayscalemod.GrayscaleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientEffectHandler {

    private static final ResourceLocation EFFECT =
        new ResourceLocation(GrayscaleMod.MODID, "shaders/post/grayscale.json");

    public static void apply(boolean enable) {
        GrayscaleMod.grayscaleActive = enable;
        GrayscaleMod.transitionStart = System.currentTimeMillis();
        GrayscaleMod.transitionIn = enable;

        Minecraft mc = Minecraft.getInstance();
        if (enable) {
            mc.gameRenderer.loadEffect(EFFECT);
        }

        // 末地传送音效
        if (mc.player != null) {
            mc.player.playSound(
                SoundEvents.ENDERMAN_TELEPORT,
                1.0f,
                enable ? 0.5f : 1.2f
            );
        }
    }
}
