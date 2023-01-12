package com.kleinreveche.vanillium.patches;

import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MerchantOffer.class})
public class MerchantOfferMixin {
    @Shadow
    @Final
    @Mutable
    private int maxUses;

    @Shadow
    private int demand;

    @Inject(method = {"<init>*"}, at = {@At("RETURN")})
    public void forceHighMaxUseCount(CallbackInfo ci) {
        this.maxUses = 1000000;
        this.demand = 0;
    }

    @Inject(method = {"updateDemand"}, at = {@At("RETURN")})
    public void updateDemand(CallbackInfo ci) {
        this.demand = 0;
    }
}

