package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArcherTest extends HeroSkillsTest {

    @BeforeEach
    public void setUp() {
        Hero archer = new ArcherHero("Name");
        hero = archer;
    }
}
