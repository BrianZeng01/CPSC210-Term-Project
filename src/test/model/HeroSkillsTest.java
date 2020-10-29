package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class HeroSkillsTest {
    public Hero hero;

    @Test
    public void firstSkillTest() {
        int baseDamage = basicAttackBaseDamage();
        int maxBaseDamage = maxDamage(baseDamage);
        int minBaseDamage = minDamage(baseDamage);
        int maxSkillDamage;
        int minSkillDamage;
        if (hero.getHeroClass().equals("warrior")) {
            maxSkillDamage = (int) Math.round(maxBaseDamage * (1 + (hero.getSkillMultiplier() * hero.getStrength())));
            minSkillDamage = (int) Math.round(minBaseDamage * (1 + (hero.getSkillMultiplier() * hero.getStrength())));
        } else if (hero.getHeroClass().equals("archer")) {
            maxSkillDamage = (int) Math.round(maxBaseDamage * (1 + ((1 + hero.getSkillMultiplier()) * hero.getAgility())));
            minSkillDamage = (int) Math.round(minBaseDamage * (1 + ((1 + hero.getSkillMultiplier()) * hero.getAgility())));
        } else {
            maxSkillDamage = (int) Math.round(maxBaseDamage * (1 + ((1 + hero.getSkillMultiplier()) * hero.getIntelligence())));
            minSkillDamage = (int) Math.round(minBaseDamage * (1 + ((1 + hero.getSkillMultiplier()) * hero.getIntelligence())));
        }

        int actualDamage = hero.firstSkill();
        assertTrue(actualDamage <= maxSkillDamage && actualDamage >= minSkillDamage);
        assertEquals(hero.getMaxFirstSkillCoolDown(), hero.getFirstSkillCoolDown());
    }

    @Test
    public void firstSkillNoManaTest() {
        hero.spendMana(hero.getMaxMana());
        assertEquals(-1, hero.firstSkill());
    }

    @Test
    public void secondSkillTest() {
        int baseDamage = basicAttackBaseDamage();
        int maxBaseDamage = maxDamage(baseDamage);
        int minBaseDamage = minDamage(baseDamage);
        int maxSkillDamage;
        int minSkillDamage;
        if (hero.getHeroClass().equals("warrior")) {
            maxSkillDamage = (int) Math.round(maxBaseDamage * (1 + ((2 * hero.getSkillMultiplier()) * hero.getStrength())));
            minSkillDamage = (int) Math.round(minBaseDamage * (1 + ((2 * hero.getSkillMultiplier()) * hero.getStrength())));
        } else if (hero.getHeroClass().equals("archer")) {
            maxSkillDamage = (int) Math.round(maxBaseDamage * (1 + ((1 + (2 * hero.getSkillMultiplier())) * hero.getAgility())));
            minSkillDamage = (int) Math.round(minBaseDamage * (1 + ((1 + (2 * hero.getSkillMultiplier())) * hero.getAgility())));
        } else {
            maxSkillDamage = (int) Math.round(maxBaseDamage * (1 + ((1 + (2 * hero.getSkillMultiplier())) * hero.getIntelligence())));
            minSkillDamage = (int) Math.round(minBaseDamage * (1 + ((1 + (2 * hero.getSkillMultiplier())) * hero.getIntelligence())));
        }

        int actualDamage = hero.secondSkill();
        assertTrue(actualDamage <= maxSkillDamage  && actualDamage >= minSkillDamage );
        assertEquals(hero.getMaxSecondSkillCoolDown(), hero.getSecondSkillCoolDown());
    }

    @Test
    public void secondSkillNoManaTest() {
        hero.spendMana(hero.getMaxMana());
        assertEquals(-1, hero.secondSkill());
    }

    @Test
    public void thirdSkillTest() {
        int baseDamage = basicAttackBaseDamage();
        int maxBaseDamage = maxDamage(baseDamage);
        int minBaseDamage = minDamage(baseDamage);
        int maxSkillDamage;
        int minSkillDamage;
        if (hero.getHeroClass().equals("warrior")) {
            maxSkillDamage = (int) Math.round(maxBaseDamage * 3 * (1 + ((4 * hero.getSkillMultiplier()) * hero.getStrength())));
            minSkillDamage = (int) Math.round(minBaseDamage * 3 * (1 + ((4 * hero.getSkillMultiplier()) * hero.getStrength())));
        } else if (hero.getHeroClass().equals("archer")) {
            maxSkillDamage = (int) Math.round(maxBaseDamage * 3 * (1 + ((1 + (2 * hero.getSkillMultiplier())) * hero.getAgility())));
            minSkillDamage = (int) Math.round(minBaseDamage * 3 * (1 + ((1 + (2 * hero.getSkillMultiplier())) * hero.getAgility())));
        } else {
            maxSkillDamage = (int) Math.round(maxBaseDamage * 3 * (1 + ((1 + (2 * hero.getSkillMultiplier())) * hero.getIntelligence())));
            minSkillDamage = (int) Math.round(minBaseDamage * 3 * (1 + ((1 + (2 * hero.getSkillMultiplier())) * hero.getIntelligence())));
        }

        int actualDamage = hero.thirdSkill();
        assertTrue((actualDamage <= maxSkillDamage && actualDamage >= minSkillDamage) || actualDamage == 0);
        assertEquals(hero.getMaxThirdSkillCoolDown(), hero.getThirdSkillCoolDown());
    }

    @Test
    public void thirdSkillNoManaTest() {
        hero.spendMana(hero.getMaxMana());
        assertEquals(-1, hero.thirdSkill());
    }

    public int basicAttackBaseDamage() {
        int baseDamage = (int) Math.round(hero.getStrength() * ((100 - hero.getStrength()) / 100.0001))
                * hero.getAttackMultiplier();
        return baseDamage;
    }

    public int minDamage(int baseDamage) {
        int minDamage = (int) Math.round(baseDamage * 0.75);
        return minDamage;
    }

    public int maxDamage(int baseDamage) {
        int maxDamage = (int) Math.round(baseDamage * 1.25);
        return maxDamage;
    }

}
