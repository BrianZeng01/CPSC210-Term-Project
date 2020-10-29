package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WarriorTest extends HeroSkillsTest {

    @BeforeEach
    public void setUp() {
        Hero warrior = new WarriorHero("Name");
        for (int i = 0; i < warrior.getMaxLevel(); i++) {
            warrior.levelUp();
            warrior.increaseStrength();
        }
        hero = warrior;
    }
}
