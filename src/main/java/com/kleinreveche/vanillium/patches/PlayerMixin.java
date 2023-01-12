package com.kleinreveche.vanillium.patches;

import com.kleinreveche.vanillium.utils.UtilHandler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({Player.class})
public abstract class PlayerMixin {
    @Redirect(method = {"onEnchantmentPerformed"}, at = @At(value = "FIELD",
            target = "Lnet/minecraft/world/entity/player/Player;experienceLevel:I", ordinal = 1))
    public void onEnchantmentPerformed(Player player, int newXpLevel) {
        int currentLevel = player.experienceLevel;
        int vanillaLevelCost = currentLevel - newXpLevel;
        if (currentLevel >= 30) {
            int xpCost = UtilHandler.getXpDifference(player, 30 - vanillaLevelCost, 30);
            player.giveExperiencePoints(-xpCost);
        } else {
            player.experienceLevel = newXpLevel;
        }
    }
}

