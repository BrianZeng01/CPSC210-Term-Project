package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {
    Monster monster;

    @BeforeEach
    public void monsterTestSetup() {
        monster = new Monster("Slime", 50, 3);
    }

    @Test
    public void takeDamageAliveTest() {
        assertTrue(monster.takeDamage(30));
        assertEquals(20, monster.getHealth());
    }

    @Test
    public void takeDamageTwiceAliveTest() {
        assertTrue(monster.takeDamage(30));
        assertEquals(20, monster.getHealth());
        assertTrue(monster.takeDamage(19));
        assertEquals(1, monster.getHealth());
    }

    @Test
    public void takeDamageDeadTest() {
        assertFalse(monster.takeDamage(50));
    }

    @Test
    public void basicAttack() {
        int damage = monster.getStrength() * monster.getAttackMultiplier();
        assertEquals(damage, monster.basicAttack());
    }

}
