package com.kleinreveche.vanillium.patches;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ProtectionEnchantment.class})
public class ProtectionUnpatchMixin {
    @Inject(at = {@At("HEAD")}, method = {"checkCompatibility"}, cancellable = true)
    private void checkCompatibility(Enchantment other, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }
}
