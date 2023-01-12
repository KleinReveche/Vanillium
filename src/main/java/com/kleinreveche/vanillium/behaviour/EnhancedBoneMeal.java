package com.kleinreveche.vanillium.behaviour;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collections;

public class EnhancedBoneMeal implements UseBlockCallback {
    public EnhancedBoneMeal() {
        UseBlockCallback.EVENT.register(this);
    }

    public InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult block) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem().equals(Items.BONE_MEAL)) {
            BlockPos blockPos = block.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            Block currentBlock = blockState.getBlock();
            if (currentBlock.equals(Blocks.CACTUS) || currentBlock.equals(Blocks.SUGAR_CANE)) {
                for (int y = blockPos.getY(); y <= 256; y++) {
                    BlockPos upperPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
                    Block upperBlock = world.getBlockState(upperPos).getBlock();
                    if (upperBlock.equals(Blocks.AIR)) {
                        world.setBlockAndUpdate(upperPos, currentBlock.defaultBlockState());
                        world.levelEvent(2005, upperPos, 0);
                        world.levelEvent(2005, upperPos.above(), 0);
                        if (!player.isCreative())
                            stack.shrink(1);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            else if (currentBlock.equals(Blocks.VINE)) {
                for (int y = blockPos.getY(); y > 0; y--) {
                    BlockPos downPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
                    Block downBlock = world.getBlockState(downPos).getBlock();
                    if (downBlock.equals(Blocks.AIR)) {
                        world.setBlockAndUpdate(downPos, currentBlock.defaultBlockState());
                        world.levelEvent(2005, downPos, 0);
                        world.levelEvent(2005, downPos.below(), 0);
                        if (!player.isCreative())
                            stack.shrink(1);
                        return InteractionResult.SUCCESS;
                    }
                }
            } else if (currentBlock.equals(Blocks.NETHER_WART)) {
                for (Property<?> property : Collections.unmodifiableCollection(blockState.getProperties())) {
                    if (property instanceof IntegerProperty prop) {
                        String name = prop.getName();
                        if (name.equals("age")) {
                            Comparable<?> cv = blockState.getValue(property);
                            int value = Integer.parseUnsignedInt(cv.toString());
                            int max = Collections.<Integer>max(prop.getPossibleValues());
                            if (value == max)
                                break;
                            world.setBlockAndUpdate(blockPos, world.getBlockState(blockPos).cycle(property));
                            if (!player.isCreative())
                                stack.shrink(1);
                            world.levelEvent(2005, blockPos, 0);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }
}
