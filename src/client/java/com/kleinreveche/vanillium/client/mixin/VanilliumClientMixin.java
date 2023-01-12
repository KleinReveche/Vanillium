package com.kleinreveche.vanillium.client.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class VanilliumClientMixin {
    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo ci){

    }
}
