package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralHeroTest {
    Hero hero;

    @BeforeEach
    public void generalHeroTestSetup() {
        this.hero = new WarriorHero("MyHero");
    }

    @Test
    public void constructorTest() {
        assertEquals("MyHero", hero.getName());
        assertEquals(hero.getSpecialBaseStat(), hero.getDefence());
        assertEquals(hero.getBaseStats(), hero.getIntelligence());
        assertEquals(hero.getBaseHealthAndMana(), hero.getHealth());
    }

    @Test
    public void levelUpTest() {
        hero.levelUp();
        assertEquals(hero.getSkillPointsGrantedPerLevel(), hero.getSkillPoints());
        assertEquals(2, hero.getLevel());
        assertEquals(hero.getMaxHealthAndManaIncrement() + hero.getBaseHealthAndMana(), hero.getMaxHealth());
        assertEquals(hero.getMaxHealthAndManaIncrement() + hero.getBaseHealthAndMana(), hero.getMaxMana());
    }

    @Test
    public void levelUpTwiceTest() {
        hero.levelUp();
        hero.levelUp();
        assertEquals(2*hero.getSkillPointsGrantedPerLevel(), hero.getSkillPoints());
        assertEquals(3, hero.getLevel());
        assertEquals(2*hero.getMaxHealthAndManaIncrement() + hero.getBaseHealthAndMana(), hero.getMaxHealth());
        assertEquals(2*hero.getMaxHealthAndManaIncrement() + hero.getBaseHealthAndMana(), hero.getMaxMana());
    }

    @Test
    public void increaseStatsTest() {
        hero.levelUp();
        hero.increaseStrength();
        hero.increaseAgility();
        hero.increaseDefence();
        hero.increaseIntelligence();
        assertEquals(2, hero.getStrength());
        assertEquals(2, hero.getAgility());
        assertEquals(1 + hero.getSpecialBaseStat(), hero.getDefence());
        assertEquals(2, hero.getIntelligence());
        hero.increaseStrength();
        assertEquals(3, hero.getStrength());
    }

    @Test
    public void equipAccessoryTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);

    }

    @Test
    public void takeDamageAliveTest() {
        int damage = hero.getBaseHealthAndMana() - 1;
        assertTrue(hero.takeDamage(damage));
        assertEquals(hero.getBaseHealthAndMana()-damage, hero.getHealth());
    }


    @Test
    public void takeDamageTwiceAliveTest() {
        int damage = hero.getBaseHealthAndMana()/2 - 1;
        assertTrue(hero.takeDamage(damage));
        assertEquals(hero.getBaseHealthAndMana()-damage, hero.getHealth());
        assertTrue(hero.takeDamage(damage));
        assertEquals(hero.getBaseHealthAndMana()-2*damage, hero.getHealth());
    }

    @Test
    public void takeDamageDieTest() {
        int damage = hero.getBaseHealthAndMana();
        assertFalse(hero.takeDamage(damage));
    }

    @Test
    public void spendManaSufficientTest() {
        int spend = hero.getBaseHealthAndMana() - 1;
        assertTrue(hero.spendMana(spend));
        assertEquals(hero.getBaseHealthAndMana() - spend, hero.getMana());
    }

    @Test
    public void spendManaTwiceSufficientTest() {
        int spend = hero.getBaseHealthAndMana()/2 - 1;
        assertTrue(hero.spendMana(spend));
        assertEquals(hero.getBaseHealthAndMana() - spend, hero.getMana());
        assertTrue(hero.spendMana(spend));
        assertEquals(hero.getBaseHealthAndMana() - 2*spend, hero.getMana());
    }

    @Test
    public void spendManaInsufficientTest() {
        int spend = hero.getBaseHealthAndMana() + 1;
        assertFalse(hero.spendMana(spend));
        assertEquals(hero.getBaseHealthAndMana(), hero.getMana());
    }

    @Test
    public void drinkManaPotionSufficientMaxManaTest() {
        assertTrue(hero.drinkManaPotion());
        assertTrue(hero.drinkManaPotion());
        assertEquals(3, hero.getInventory().manaPotions);
        assertEquals(hero.getMaxMana(), hero.getMana());
    }

    @Test
    public void drinkManaPotionSufficientUsedManaTest() {
        int spend = hero.getMaxMana();
        assertTrue(hero.spendMana(spend));
        assertTrue(hero.drinkManaPotion());
        assertEquals( hero.getHealthAndManaPotionValue(), hero.getMana());
        assertEquals(4, hero.getInventory().manaPotions);
    }

    @Test
    public void drinkManaPotionInsufficientTest() {
        for (int i = 0 ; i < hero.getInventory().getStarterPotions() ; i++) {
            hero.drinkManaPotion();
        }
        assertEquals(0, hero.getInventory().manaPotions);
        assertFalse(hero.drinkManaPotion());
    }

    @Test
    public void drinkHealthPotionSufficientMaxHealthTest() {
        assertTrue(hero.drinkHealthPotion());
        assertTrue(hero.drinkHealthPotion());
        assertEquals(3, hero.getInventory().healthPotions);
        assertEquals(hero.getMaxHealth(), hero.getHealth());
    }

    @Test
    public void drinkHealthPotionSufficientTakenDamageTest() {
        int damage = hero.getMaxHealth()/2 ;
        hero.takeDamage(damage);
        assertTrue(hero.drinkHealthPotion());
        assertEquals( hero.getMaxHealth()/2 + hero.getHealthAndManaPotionValue(), hero.getHealth());
        assertEquals(4, hero.getInventory().healthPotions);
    }

    @Test
    public void drinkHealthPotionInsufficientTest() {
        for (int i = 0 ; i < hero.getInventory().getStarterPotions() ; i++) {
            hero.drinkHealthPotion();
        }
        assertEquals(0, hero.getInventory().healthPotions);
        assertFalse(hero.drinkHealthPotion());
    }

    @Test
    public void isDeadTrue() {
        int damage = hero.getBaseHealthAndMana();
        hero.takeDamage(damage);
        assertTrue(hero.isDead());
    }

    @Test
    public void isDeadFalse() {
        int damage = hero.getBaseHealthAndMana() - 1;
        hero.takeDamage(damage);
        assertFalse(hero.isDead());
    }

    @Test
    public void hasSkillPointsTest() {
        assertFalse(hero.hasSkillPoints());
        hero.levelUp();
        assertTrue(hero.hasSkillPoints());
    }
}
