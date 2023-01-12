package com.kleinreveche.vanillium.behaviour;

public class BehaviourRegistry {
    public static void register(){
        new EditPlacedSign();
        new EnhancedBoneMeal();
        new EnhancedBoneMealDispenserBehaviour();
        new GrowerKelp();
        new GrowerKelpDispenserBehaviour();
        new FlintAndSteelAttack();
    }
}
