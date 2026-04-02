package com.example.grayscalemod.mixin;

import com.example.grayscalemod.GrayscaleMod;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow private PostChain postEffect;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(float pPartialTicks, long pNanoTime, boolean pRenderLevel, CallbackInfo ci) {
        if (postEffect == null) return;
        postEffect.getPasses().forEach(pass -> {
            pass.getEffect().safeGetUniform("Progress")
                .set(GrayscaleMod.getProgress());
        });
    }
}
