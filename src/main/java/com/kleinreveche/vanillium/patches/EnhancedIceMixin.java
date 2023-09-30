package com.kleinreveche.vanillium.patches;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CropBlock.class})
public class EnhancedIceMixin {
    @Inject(method = {"randomTick"}, at = {@At("HEAD")}, cancellable = true)
    private void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        Block block = serverLevel.getBlockState(blockPos.below(2)).getBlock();
        if (block.equals(Blocks.PACKED_ICE) || block.equals(Blocks.BLUE_ICE))
            ci.cancel();
    }
}
