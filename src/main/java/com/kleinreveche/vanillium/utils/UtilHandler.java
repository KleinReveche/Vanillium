package com.kleinreveche.vanillium.utils;

import net.minecraft.world.entity.player.Player;

public class UtilHandler {
    public static int getXpDifference(Player player, int from, int to) {
        int currentLevel = player.experienceLevel;
        int xpSum = 0;
        for (int l = from; l < to; l++) {
            player.experienceLevel = l;
            xpSum += player.getXpNeededForNextLevel();
        }
        player.experienceLevel = currentLevel;
        return xpSum;
    }
}
