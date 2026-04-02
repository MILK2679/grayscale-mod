package com.example.grayscalemod.mixin;

import com.example.grayscalemod.GrayscaleMod;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow private PostChain postEffect;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        if (postEffect == null) return;
        try {
            List<PostPass> passes = postEffect.passes;
            for (PostPass pass : passes) {
                pass.getEffect().safeGetUniform("Progress").set(GrayscaleMod.getProgress());
            }
        } catch (Exception ignored) {}
    }
}
