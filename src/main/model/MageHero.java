package model;


// Mage hero
public class MageHero extends Hero {

    // EFFECTS: Creates new mage hero with base intelligence 5
    //          ,mage class, and given name
    public MageHero(String name) {
        super(name);
        this.intelligence = SPECIAL_BASE_STAT;
        this.heroClass = "mage";
    }

    @Override
    public int firstSkill() {
        if (getMana() < getFirstSkillManaCost()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + (1.25 * getIntelligence())));

        super.usedSkill(1);
        return damage;
    }

    @Override
    public int secondSkill() {
        if (getMana() < getSecondSkillManaCost()) {
            return -1;
        }
        int damage = (int) Math.round(basicAttack() * (1 + (1.5 * getIntelligence())));
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
            damage = (int) Math.round(basicAttack() * 3 * (1 + (1.5 * getIntelligence())));
        } else {
            damage = 0;
        }

        super.usedSkill(3);
        return damage;
    }
}
