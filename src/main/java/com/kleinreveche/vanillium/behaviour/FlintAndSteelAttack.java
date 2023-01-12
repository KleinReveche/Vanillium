package com.kleinreveche.vanillium.behaviour;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class FlintAndSteelAttack implements AttackEntityCallback {
    public FlintAndSteelAttack() {
        AttackEntityCallback.EVENT.register(this);
    }

    public InteractionResult interact(Player player, Level world, InteractionHand hand, Entity entity, EntityHitResult entityHitResult) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem().equals(Items.FLINT_AND_STEEL)) {
                entity.setSecondsOnFire(5);
                if (player instanceof ServerPlayer && !player.isCreative())
                    stack.hurt(1, player.getRandom(), (ServerPlayer) player);
            }
        }
        return InteractionResult.PASS;
    }
}
