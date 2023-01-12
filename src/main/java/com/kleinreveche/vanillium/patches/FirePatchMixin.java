package com.kleinreveche.vanillium.patches;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class})
public abstract class FirePatchMixin extends Entity {
    public FirePatchMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Shadow
    public abstract boolean hasEffect(MobEffect paramMobEffect);

    @Inject(method = {"die"}, at = {@At("HEAD")})
    private void die(DamageSource source, CallbackInfo info) {
        if (source.isFire() &&
                !isOnFire())
            setSecondsOnFire(1);
    }

    @Inject(method = {"baseTick"}, at = {@At("HEAD")})
    private void baseTick(CallbackInfo info) {
        if (hasEffect(MobEffects.FIRE_RESISTANCE))
            clearFire();
    }
}
