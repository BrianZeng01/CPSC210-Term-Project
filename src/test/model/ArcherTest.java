package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArcherTest extends HeroSkillsTest {

    @BeforeEach
    public void setUp() {
        Hero archer = new ArcherHero("Name");
        for (int i = 0; i < archer.getMaxLevel(); i++) {
            archer.levelUp();
            archer.increaseAgility();
        }
        hero = archer;
    }
}
