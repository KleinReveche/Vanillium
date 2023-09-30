package com.kleinreveche.vanillium.behaviour;

public class BehaviourRegistry {
    public static void register(){
        new EnhancedBoneMeal();
        new EnhancedBoneMealDispenserBehaviour();
        new GrowerKelp();
        new GrowerKelpDispenserBehaviour();
        new FlintAndSteelAttack();
    }
}
