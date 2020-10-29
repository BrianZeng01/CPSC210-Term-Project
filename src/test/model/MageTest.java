package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class MageTest extends HeroSkillsTest {

    @BeforeEach
    public void setUp() {
        Hero mage = new MageHero("Name");
        hero = mage;
    }
}
