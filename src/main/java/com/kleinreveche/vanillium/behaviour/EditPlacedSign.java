package com.kleinreveche.vanillium.behaviour;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.phys.BlockHitResult;

public class EditPlacedSign implements UseBlockCallback {

    public EditPlacedSign()  { UseBlockCallback.EVENT.register(this); }

    @Override
    public InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = world.getBlockEntity(hitResult.getBlockPos());
        if(blockEntity instanceof SignBlockEntity signBlockEntity && player.isCrouching() && !world.isClientSide()){
            signBlockEntity.setEditable(true);
            player.openTextEdit(signBlockEntity);
            player.swing(hand);
            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}
