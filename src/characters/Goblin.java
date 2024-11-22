package characters;

import utility.Character;
import utility.Fighter;
import utility.Runner;

public class Goblin extends Character implements Fighter, Runner {
    private int baseAttackPower;
    private boolean runAway;

    public Goblin(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower;
        this.runAway = false;
    }

    @Override
    public void attack(Fighter target) {
        if (runAway || !isAlive()) {
            System.out.println(getName() + "は攻撃できない！");
            return;
        }

        int damage = (int) (Math.random() * (baseAttackPower / 2)) + 1;
        System.out.println(getName() + "は棍棒で" + ((Character) target).getName() + "を殴った！" + damage + "のダメージ！");
        target.takeDamage(damage);
    }

    @Override
    public void takeDamage(int amount) {
        setHp(getHp() - amount);
        System.out.println(getName() + "は" + amount + "のダメージを受けた。（HP: " + getHp() + "/" + getMaxHp() + "）");
        if (!isAlive()) {
            System.out.println(getName() + "は倒れた！");
        }
    }

    @Override
    public void runAway() {
        runAway = true;
        System.out.println(getName() + "は逃げ出した！");
    }

    @Override
    public boolean isRunAway() {
        return runAway;
    }

    @Override
    public void resetRunAway() {
        runAway = false;
    }
}