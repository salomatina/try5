package ru.mephi;

public class Randomizer {

    public int getRandom(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public int getBinaryNumber() {
        return getRandom(0, 1);
    }

    public int getSkillsNumber() {
        return getRandom(1, 6);
    }

}
