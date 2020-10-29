package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {
    Monster monster;

    @BeforeEach
    public void monsterTestSetup() {
        monster = new Monster("Slime", 50, 3, 1, 50);
    }

    @Test
    public void getExpTest() {
        assertEquals(50, monster.getExp());
    }

    @Test
    public void dropLootTest() {
        List<Integer> loot = monster.dropLoot();
        assertTrue(loot.get(0) >= 0 && loot.get(0) < 15);
        assertTrue(loot.get(1) >= 0 && loot.get(1) < 15);
        assertTrue(loot.get(2) == -1 || loot.get(2) < 0);
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
