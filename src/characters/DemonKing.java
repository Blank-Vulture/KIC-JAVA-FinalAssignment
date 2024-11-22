package characters;

import utility.Character;
import utility.Fighter;
import utility.Runner;
import utility.Talk;

/**
 * 魔王クラス
 */
public class DemonKing extends Character implements Fighter, Runner, Talk {
    private int baseAttackPower;
    private boolean runAway;

    public DemonKing(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower * 3; // 魔王は非常に強い
        this.runAway = false;
    }

    @Override
    public void talk(String message) {
        System.out.println(getName() + "：「" + message + "」");
    }

    @Override
    public void attack(Fighter target) {
        if (runAway || !isAlive()) {
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

        if (getHp() <= getMaxHp() / 2 && !runAway) {
            // HPが50%以下になったら一定の確率で逃げる
            if (Math.random() < 0.3) { // 30%の確率で逃げる
                runAway();
            }
        }

        if (!isAlive()) {
            System.out.println(getName() + "は倒れた！");
        }
    }

    @Override
    public void runAway() {
        runAway = true;
        System.out.println(getName() + "は闇の中に消え去った！");
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