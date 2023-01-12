package com.kleinreveche.vanillium.patches;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({FishingHook.class})
public class FishingUnpatchMixin {
    @Inject(method = {"calculateOpenWater"}, cancellable = true, at = {@At("RETURN")})
    private void calculateOpenWater(BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }
}
