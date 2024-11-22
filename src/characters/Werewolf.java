package characters;

import utility.Character;
import utility.Fighter;
import utility.Runner;

public class Werewolf extends Character implements Fighter, Runner {
    private int baseAttackPower;
    private boolean runAway;

    public Werewolf(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower + 10; // 狼男は攻撃力が高い
        this.runAway = false;
    }

    @Override
    public void attack(Fighter target) {
        if (runAway || !isAlive()) {
            System.out.println(getName() + "は攻撃できない！");
            return;
        }

        int damage = (int) (Math.random() * baseAttackPower) + 5;
        System.out.println(getName() + "は鋭い爪で" + ((Character) target).getName() + "を引っ掻いた！" + damage + "のダメージ！");
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
        System.out.println(getName() + "は遠吠えをして逃げ出した！");
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