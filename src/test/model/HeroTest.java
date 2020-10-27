package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HeroTest {
    Hero hero;

    // Must Initilize Hero class due to difference in base stats
    @BeforeEach
    public void generalHeroTestSetup() {
        this.hero = new WarriorHero("MyHero");
    }

    @Test
    public void gainExpTest() {
        hero.gainExp(50);
        assertEquals(50, hero.getExperience());
        hero.gainExp(100);
        assertEquals(150, hero.getExperience());
    }

    @Test
    public void incrementExperienceRequiredToLevelTest() {
        int initialNeeded = hero.getExperienceRequiredToLevel();
        hero.gainExp(hero.getExperienceRequiredToLevel());
        assertEquals(initialNeeded * hero.getExperienceMultiplier(), hero.getExperienceRequiredToLevel());
    }

    @Test
    public void incrementExperienceRequiredToLevelTwiceTest() {
        int initialNeeded = hero.getExperienceRequiredToLevel();
        int multiplier = hero.getExperienceMultiplier();
        hero.gainExp(hero.getExperienceRequiredToLevel());
        hero.gainExp(hero.getExperienceRequiredToLevel());
        assertEquals(initialNeeded * multiplier * multiplier, hero.getExperienceRequiredToLevel());
    }

    @Test
    public void incrementExperienceRequiredMaxLevelTest() {
        for (int i = 1; i < hero.getMaxLevel(); i++) {
            hero.gainExp(hero.getExperienceRequiredToLevel());
        }
        int experienceRequired = hero.getExperienceRequiredToLevel();
        int maxExp = hero.getExperience();
        hero.gainExp(hero.getExperienceRequiredToLevel());
        assertEquals(maxExp, hero.getExperience());
        assertEquals(experienceRequired, hero.getExperienceRequiredToLevel());

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
    public void basicAttackTest() {
        int baseDamage = (int) Math.round(hero.getStrength() * ((100 - hero.getStrength()) / 100.01))
                * hero.getAttackMultiplier();
        int minDamage = (int) Math.round(baseDamage * 0.75);
        int maxDamage = (int) Math.round(baseDamage * 1.25);
        int basicAttackDamage = hero.basicAttack();
        assertTrue(basicAttackDamage >= minDamage && basicAttackDamage <= maxDamage);
    }

    @Test
    public void recoverTest() {
        hero.takeDamage(5);
        hero.spendMana(10);
        hero.recover();
        assertEquals(hero.getMaxHealth(), hero.getHealth());
        assertEquals(hero.getMaxMana(), hero.getMana());
    }

    @Test
    public void equipAccessoryEnoughRoomTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        hero.getInventory().pickUpAccessory(accessory);
        assertTrue(hero.equipAccessory(accessory));
        assertEquals(hero.getBaseStats() + 1, hero.getStrength());
        assertEquals(hero.getSpecialBaseStat() + 2, hero.getDefence());
        assertEquals(hero.getBaseStats() + 3, hero.getAgility());
        assertEquals(hero.getBaseStats() + 4, hero.getIntelligence());
    }

    @Test
    public void equipAccessoryEnoughRoomMultipleTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        Accessory accessory2 = new Accessory("Pendant", 1,2,3,4);
        hero.getInventory().pickUpAccessory(accessory);
        hero.getInventory().pickUpAccessory(accessory2);
        assertTrue(hero.equipAccessory(accessory));
        assertTrue(hero.equipAccessory(accessory2));
        assertEquals(hero.getBaseStats() + 2, hero.getStrength());
        assertEquals(hero.getSpecialBaseStat() + 4, hero.getDefence());
        assertEquals(hero.getBaseStats() + 6, hero.getAgility());
        assertEquals(hero.getBaseStats() + 8, hero.getIntelligence());
    }

    @Test
    public void equipAccessoryNotEnoughRoomTest() {
        for (int i = 0 ; i < hero.getInventory().getMaxEquipmentSlots(); i ++) {
            Accessory accessory = new Accessory("Pendant", 1,2,3,4);
            hero.getInventory().pickUpAccessory(accessory);
            assertTrue(hero.equipAccessory(accessory));
        }
        assertEquals(hero.getInventory().getMaxEquipmentSlots(),
                hero.getInventory().getNumberOfAccessoriesInEquipmentSlots());
        Accessory accessory2 = new Accessory("Pendant", 1,2,3,4);
        hero.getInventory().pickUpAccessory(accessory2);
        assertFalse(hero.equipAccessory(accessory2));
    }

    @Test
    public void unequipAccessoryEnoughRoomTest() {
        Accessory accessory = new Accessory("Pendant", 1,2,3,4);
        hero.getInventory().pickUpAccessory(accessory);
        hero.equipAccessory(accessory);
        assertTrue(hero.unequipAccessory(accessory));

        assertEquals(hero.getBaseStats(), hero.getStrength());
        assertEquals(hero.getSpecialBaseStat(), hero.getDefence());
        assertEquals(hero.getBaseStats(), hero.getAgility());
        assertEquals(hero.getBaseStats(), hero.getIntelligence());

    }

    @Test
    public void unequipAccessoryEnoughRoomMultipleTest() {
        Accessory accessory = new Accessory("Pendant", 2,2,3,4);
        Accessory accessory2 = new Accessory("Pendant", 1,2,3,4);
        hero.getInventory().pickUpAccessory(accessory);
        hero.getInventory().pickUpAccessory(accessory2);
        hero.equipAccessory(accessory);
        hero.equipAccessory(accessory2);
        hero.unequipAccessory(accessory);
        assertEquals(hero.getBaseStats() + 1, hero.getStrength());
        assertEquals(hero.getSpecialBaseStat() + 2, hero.getDefence());
        assertEquals(hero.getBaseStats() + 3, hero.getAgility());
        assertEquals(hero.getBaseStats() + 4, hero.getIntelligence());

        hero.unequipAccessory(accessory2);
        assertEquals(hero.getBaseStats(), hero.getStrength());
        assertEquals(hero.getSpecialBaseStat(), hero.getDefence());
        assertEquals(hero.getBaseStats(), hero.getAgility());
        assertEquals(hero.getBaseStats(), hero.getIntelligence());
    }

    @Test
    public void unequipAccessoryNotEnoughRoomTest() {
        Accessory equipedAccessory = new Accessory( "test" ,1,2,2,2);
        hero.getInventory().pickUpAccessory(equipedAccessory);
        hero.equipAccessory(equipedAccessory);

        for (int i = 0 ; i < hero.getInventory().getMaxInventorySlots(); i ++) {
            Accessory accessory = new Accessory("Pendant", 1,2,3,4);
            hero.getInventory().pickUpAccessory(accessory);
        }
        assertEquals(hero.getInventory().getMaxInventorySlots(),
                hero.getInventory().getNumberOfAccessoriesInInventorySlots());

        assertFalse(hero.unequipAccessory(equipedAccessory));
    }

    @Test
    public void takeDamageAliveTest() {
        int damage = hero.getBaseHealthAndMana() - 1;
        int damageAfterDefence = (int) Math.round(damage * (100 / (100 + (2.75 * hero.getDefence()))));
        assertTrue(hero.takeDamage(damage));
        assertEquals(hero.getBaseHealthAndMana()-damageAfterDefence, hero.getHealth());
    }


    @Test
    public void takeDamageTwiceAliveTest() {
        int damage = hero.getBaseHealthAndMana()/2 - 1;
        int damageAfterDefence = (int)
                Math.round(damage * (100 / (100 + (hero.getDefenceMultiplier() * hero.getDefence()))));
        assertTrue(hero.takeDamage(damage));
        assertEquals(hero.getBaseHealthAndMana()-damageAfterDefence, hero.getHealth());
        assertTrue(hero.takeDamage(damage));
        assertEquals(hero.getBaseHealthAndMana()-2*damageAfterDefence, hero.getHealth());
    }

    @Test
    public void takeDamageDieTest() {
        assertFalse(hero.takeDamage(10000));
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
        int damageAfterDefence = (int) Math.round(damage * (100 / (100 + (hero.getDefenceMultiplier() * hero.getDefence()))));
        hero.takeDamage(damage);
        assertTrue(hero.drinkHealthPotion());
        assertEquals( hero.getMaxHealth() - damageAfterDefence + hero.getHealthAndManaPotionValue(), hero.getHealth());
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
        hero.takeDamage(1000);
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
