package net.dainplay.rpgworldmod.util;

import net.minecraft.core.Direction;

import java.util.Random;

public class RandomDirection {
    public static Direction generate () {
        Direction [] arr = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        Random random = new Random();
        int select = random.nextInt(arr.length);
        return arr[select];
    }
}
