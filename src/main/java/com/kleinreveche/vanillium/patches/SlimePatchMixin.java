package com.kleinreveche.vanillium.patches;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin({Slime.class})
public class SlimePatchMixin {
    @Inject(method = {"checkSlimeSpawnRules"}, at = {@At("RETURN")}, cancellable = true)
    private static void checkSlimeSpawnRules(EntityType<Slime> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource, CallbackInfoReturnable<Boolean> cir) {
        if (levelAccessor instanceof ServerLevel world) {
            if (world.getServer().getWorldData().isFlatWorld())
                cir.setReturnValue(false);
        }
    }
}
