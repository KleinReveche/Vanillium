package com.kleinreveche.vanillium.behaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;

public class EnhancedBoneMealDispenserBehaviour extends OptionalDispenseItemBehavior {
    public EnhancedBoneMealDispenserBehaviour() {
        DispenserBlock.registerBehavior(Items.BONE_MEAL, this);
    }

    protected @NotNull ItemStack execute(BlockSource block, ItemStack stack) {
        setSuccess(true);
        ServerLevel world = block.level().getLevel();
        BlockPos blockPos = block.pos().relative(block.state().getValue(DispenserBlock.FACING));
        if (BoneMealItem.growCrop(stack, world, blockPos) || BoneMealItem.growWaterPlant(stack, world, blockPos, null)) {
            world.levelEvent(2005, blockPos, 0);
        } else {
            BlockState blockState = world.getBlockState(blockPos);
            Block currentBlock = blockState.getBlock();
            if (currentBlock.equals(Blocks.SUGAR_CANE) || currentBlock.equals(Blocks.CACTUS)) {
                for (int y = blockPos.getY(); y <= 256; y++) {
                    BlockPos upperPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
                    Block upperBlock = world.getBlockState(upperPos).getBlock();
                    if (upperBlock.equals(Blocks.AIR)) {
                        world.setBlockAndUpdate(upperPos, blockState.getBlock().defaultBlockState());
                        world.levelEvent(2005, upperPos, 0);
                        world.levelEvent(2005, upperPos.above(), 0);
                        stack.shrink(1);
                        break;
                    }
                }
            } else if (blockState.getBlock().equals(Blocks.VINE)) {
                for (int y = blockPos.getY(); y > 0; y--) {
                    BlockPos downPos = new BlockPos(blockPos.getX(), y, blockPos.getZ());
                    Block downBlock = world.getBlockState(downPos).getBlock();
                    if (downBlock.equals(Blocks.AIR)) {
                        world.setBlockAndUpdate(downPos, blockState.getBlock().defaultBlockState());
                        world.levelEvent(2005, downPos, 0);
                        world.levelEvent(2005, downPos.below(), 0);
                        stack.shrink(1);
                        break;
                    }
                }
            } else if (blockState.getBlock().equals(Blocks.NETHER_WART)) {
                for (Property<?> property : Collections.unmodifiableCollection(blockState.getProperties())) {
                    if (property instanceof IntegerProperty prop) {
                        String name = prop.getName();
                        if (name.equals("age")) {
                            Comparable<?> cv = blockState.getValue(property);
                            int value = Integer.parseUnsignedInt(cv.toString());
                            int max = Collections.max(prop.getPossibleValues());
                            if (value == max)
                                break;
                            world.setBlockAndUpdate(blockPos, world.getBlockState(blockPos).cycle(property));
                            world.levelEvent(2005, blockPos, 0);
                            stack.shrink(1);
                            break;
                        }
                    }
                }
            } else {
                setSuccess(false);
            }
        }
        return stack;
    }
}
