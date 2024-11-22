package characters;

import utility.Character;
import utility.Fighter;
import utility.Runner;
import utility.Talk;

public class DemonKing extends Character implements Fighter, Runner, Talk {
    private int baseAttackPower;
    private boolean runAway;

    public DemonKing(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower * 2; // 魔王は非常に強い
        this.runAway = false;
    }

    @Override
    public void talk(String message) {
        System.out.println(getName() + "：「" + message + "」");
    }

    @Override
    public void attack(Fighter target) {
        if (!isAlive()) {
            System.out.println(getName() + "は攻撃できない！");
            return;
        }

        int damage = (int) (Math.random() * baseAttackPower) + 20;
        System.out.println(getName() + "は闇の力で" + ((Character) target).getName() + "を攻撃した！" + damage + "のダメージ！");
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
        System.out.println(getName() + "は逃げることを拒否した！");
    }

    @Override
    public boolean isRunAway() {
        return false; // 魔王は逃げない
    }

    @Override
    public void resetRunAway() {
        // 何もしない
    }
}