package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorldsTest {

    Worlds worlds;

    @BeforeEach
    public void worldsTestSetup() {
        worlds = new Worlds();
    }

    @Test
    public void createWorldTest() {
        assertEquals(0, worlds.getNumberOfWorlds());
        assertTrue(worlds.createWorld("Test World", "Test Hero",
                "warrior", "easy" ));
        assertEquals(1, worlds.getNumberOfWorlds());
    }

    @Test
    public void createWorldMultipleTest() {
        assertTrue(worlds.createWorld("Test World", "Test Hero",
                 "warrior", "easy" ));
        assertTrue(worlds.createWorld("Test World2", "Test Hero",
                 "warrior", "easy" ));
        assertEquals(2, worlds.getNumberOfWorlds());
    }

    @Test
    public void createWorldFullTest() {
        for (int i = 0 ; i < worlds.getMaxWorlds(); i++) {
            worlds.createWorld("Test World", "Test Hero",
                    "warrior", "easy" );
        }
        assertFalse(worlds.createWorld("Test World", "Test Hero",
                "warrior", "easy" ));
    }

    @Test
    public void deleteWorldTest() {
        worlds.createWorld("Test World", "Test Hero",
                "warrior", "easy" );
        assertTrue(worlds.deleteWorld(1));
    }

    @Test
    public void deleteWorldManyWorldsTest() {
        worlds.createWorld("Test World", "Test Hero",
                "warrior", "easy" );
        worlds.createWorld("Test World2", "Test Hero",
                "warrior", "easy" );
        assertTrue(worlds.deleteWorld(1));
        assertEquals("Test World2" ,worlds.getWorld(2).getWorldName());
    }

    @Test
    public void deleteWorldDNETest() {
        assertFalse(worlds.deleteWorld(2));
    }

    @Test
    public void worldsIsFullTest() {
        for (int i = 0 ; i < worlds.getMaxWorlds(); i++) {
            worlds.createWorld("Test World", "Test Hero",
                    "warrior", "easy" );
        }
        assertTrue(worlds.worldsIsFull());
    }

    @Test
    public void worldsIsNotFullTest() {
        assertFalse(worlds.worldsIsFull());
    }

    @Test
    public void getWorldTest() {
        worlds.createWorld("Test World", "Test Hero",
                "warrior", "easy" );
        assertEquals("Test World", worlds.getWorld(1).getWorldName());

        worlds.createWorld("Test World2", "Test Hero",
                "warrior", "easy" );
        assertEquals("Test World", worlds.getWorld(1).getWorldName());
        assertEquals("Test World2", worlds.getWorld(2).getWorldName());
    }

    @Test
    public void getNumberOfWorldsTest() {
        assertEquals(0, worlds.getNumberOfWorlds());
    }

    @Test
    public void getUsedWorldNumbers() {
        worlds.createWorld("Test World", "Test Hero",
                "warrior", "easy" );
        assertEquals(1, worlds.getUsedWorldNumbers().get(0));
    }
}

