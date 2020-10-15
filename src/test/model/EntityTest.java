package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTest {
    Entity entity;

    @BeforeEach
    public void entityTestSetup() {
        entity = new Monster("Golem", 150, 5);
    }

    @Test
    public void getNameTest() {
        assertEquals("Golem", entity.getName());
    }

    @Test
    public void takeDamageDeadTest() {
        entity.takeDamage(150);
        assertTrue(entity.isDead());
    }

    @Test
    public void takeDamageAliveTest() {
        entity.takeDamage(149);
        assertFalse(entity.isDead());
    }
}
