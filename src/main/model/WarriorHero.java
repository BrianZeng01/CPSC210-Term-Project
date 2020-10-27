package model;


// Warrior hero
public class WarriorHero extends Hero {

    // EFFECTS: Creates new warrior hero with base defence 5
    //          ,warrior class, and given name
    public WarriorHero(String name) {
        super(name);
        this.defence = SPECIAL_BASE_STAT;
        this.heroClass = "warrior";
    }

    @Override
    public int firstSkill() {
        if (getMana() < getFirstSkillManaCost()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + (1.05 * getStrength())));

        super.usedSkill(1);
        return damage;
    }

    @Override
    public int secondSkill() {
        if (getMana() < getSecondSkillManaCost()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + (0.1 * getStrength())));
        super.usedSkill(2);
        return damage;
    }

    @Override
    public int thirdSkill() {
        if (getMana() < getThirdSkillManaCost()) {
            return -1;
        }

        int damage;
        if (Math.random() >= 0.5) {
            damage = (int) Math.round(basicAttack() * 3 * (1 + (0.2 * getStrength())));
        } else {
            damage = 0;
        }

        super.usedSkill(3);
        return damage;
    }
}
