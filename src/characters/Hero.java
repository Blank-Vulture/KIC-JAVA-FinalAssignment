package characters;

import utility.Character;
import utility.Fighter;
import utility.Runner;
import utility.Talk;

import java.util.Scanner;

/**
 * 勇者クラス
 */
public class Hero extends Character implements Fighter, Runner, Talk {
    private int baseAttackPower;
    private boolean runAway;

    public Hero(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower;
        this.runAway = false;
    }

    @Override
    public void talk(String message) {
        System.out.println(getName() + "：「" + message + "」");
    }

    // ユーザーの行動を選択するメソッド
    public void takeTurn(Fighter target, Scanner scanner) {
        if (runAway || !isAlive()) {
            System.out.println(getName() + "は行動できない！");
            return;
        }

        // ユーザーの行動を選択する
        System.out.println("\nあなたのターンです。行動を選択してください。");
        System.out.println("1: 攻撃");
        System.out.println("2: 逃げる");
        System.out.println("3: 眠る");
        System.out.print("選択肢（1-3）: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            choice = 1;
            System.out.println("無効な入力です。攻撃します。");
        }

        switch (choice) {
            case 1:
                // 攻撃処理
                attack(target);
                break;
            case 2:
                // 逃げる
                runAway();
                break;
            case 3:
                // 眠る
                sleep();
                break;
            default:
                System.out.println("無効な選択です。攻撃します。");
                attack(target);
        }
    }

    @Override
    public void attack(Fighter target) {
        int damage = (int) (Math.random() * baseAttackPower) + 1;
        System.out.println(getName() + "は" + ((Character) target).getName() + "を攻撃した！" + damage + "のダメージ！");
        target.takeDamage(damage);
    }

    @Override
    public void takeDamage(int amount) {
        setHp(getHp() - amount);
        System.out.println(getName() + "は" + amount + "のダメージを受けた。（HP: " + getHp() + "/" + getMaxHp() + "）");
        if (!isAlive()) {
            System.out.println(getName() + "は倒れてしまった！");
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

    // 逃走状態をリセット
    @Override
    public void resetRunAway() {
        runAway = false;
    }

    public void sleep() {
        System.out.println(getName() + "は眠ってHPを回復した！");
        setHp(getMaxHp());
        System.out.println("（HP: " + getHp() + "/" + getMaxHp() + "）");
    }

    public int getBaseAttackPower() {
        return baseAttackPower;
    }

    public void setBaseAttackPower(int baseAttackPower) {
        this.baseAttackPower = baseAttackPower;
    }
}