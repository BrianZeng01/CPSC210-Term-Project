package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorldTest {
    World world;

    @BeforeEach
    public void worldTestSetup() {
        this.world = new World("Test World", "Test Hero",
                "warrior", "easy", 1);
    }

    @Test
    public void constructorWarriorTest() {
        this.world = new World("Test World", "Test Hero",
                "warrior", "medium", 1);
        assertEquals("warrior", world.getHero().getHeroClass());
    }

    @Test
    public void constructorArcherTest() {
        this.world = new World("Test World", "Test Hero",
                "archer", "medium", 1);
        assertEquals("archer", world.getHero().getHeroClass());
    }

    @Test
    public void constructorMageTest() {
        this.world = new World("Test World", "Test Hero",
                "mage", "medium", 1);
        assertEquals("mage", world.getHero().getHeroClass());
    }

    @Test
    public void changeDifficultyTest() {
        assertEquals("easy", world.getDifficulty());
        world.changeDifficulty("medium");
        assertEquals("medium", world.getDifficulty());
        world.changeDifficulty("hard");
        assertEquals("hard", world.getDifficulty());
    }

    @Test
    public void nextRoundTest() {
        assertEquals(1, world.getRound());
        world.nextRound();
        assertEquals(2, world.getRound());
        world.nextRound();
        world.nextRound();
        assertEquals(4, world.getRound());
    }

}
