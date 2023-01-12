package com.kleinreveche.vanillium.patches;


import com.kleinreveche.vanillium.utils.UtilHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin({AnvilMenu.class})
public abstract class AnvilMenuMixin {
    @Shadow
    @Final
    private DataSlot cost;

    @Redirect(method = {"onTake"}, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;giveExperienceLevels(I)V"))
    public void updateExperienceLevel(Player player, int negativeLevelCost) {
        int xpCost = UtilHandler.getXpDifference(player, 0, -negativeLevelCost);
        player.giveExperiencePoints(-xpCost);
    }

    @Inject(method = {"createResult"}, at = {@At(value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/DataSlot;get()I", ordinal = 1)}, locals = LocalCapture.CAPTURE_FAILSOFT)
    public void updateResult(CallbackInfo ci, ItemStack itemStack, int i, int j, int k) {
        if (k > 0 && k == i)
            this.cost.set(1);
    }
}

