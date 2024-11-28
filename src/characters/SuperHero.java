package characters;

import utility.Character;
import utility.Fighter;
import utility.Runner;
import utility.Talk;

import java.util.Scanner;

/**
 * スーパー勇者クラス
 */
public class SuperHero extends Character implements Fighter, Runner, Talk {
    private int baseAttackPower;
    private boolean runAway;
    private boolean isFlying;

    public SuperHero(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp * 3); // スーパー勇者はHPが3倍);
        this.baseAttackPower = baseAttackPower * 2; // スーパー勇者は攻撃力が2倍
        this.runAway = false;
        this.isFlying = false; // 初期状態では地上にいる
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
        System.out.println("3: " + (isFlying ? "着陸する" : "飛ぶ"));

        // 飛んでいない場合のみ「眠る」選択肢を表示
        if (!isFlying) {
            System.out.println("4: 眠る");
        }
        System.out.print("選択肢を入力してください: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            choice = 1;
            System.out.println("無効な入力です。攻撃します。");
        }

        // 飛行状態に応じて選択肢を処理
        if (isFlying && choice == 4) {
            System.out.println("無効な選択です。攻撃します。");
            attack(target);
            return;
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
                // 飛ぶ or 着陸する
                if (isFlying) {
                    land();
                } else {
                    fly();
                }
                break;
            case 4:
                // 眠る（地上にいる場合のみ）
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
        if (isFlying) {
            // 飛んでいる場合はダメージを半減
            amount /= 2;
            System.out.println(getName() + "は飛んでいるのでダメージが半減した！");
        }
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

    // 飛ぶメソッド
    public void fly() {
        if (!isFlying) {
            isFlying = true;
            System.out.println(getName() + "は空高く飛び上がった！");
        } else {
            System.out.println(getName() + "は既に飛んでいる！");
        }
    }

    // 着陸するメソッド
    public void land() {
        if (isFlying) {
            isFlying = false;
            System.out.println(getName() + "は地上に降り立った！");
        } else {
            System.out.println(getName() + "は既に地上にいる！");
        }
    }

    // 眠るメソッド
    public void sleep() {
        if (isFlying) {
            System.out.println(getName() + "は飛んでいるので眠れない！");
        } else {
            System.out.println(getName() + "は眠ってHPを回復した！");
            setHp(getMaxHp());
            System.out.println("（HP: " + getHp() + "/" + getMaxHp() + "）");
        }
    }

    public boolean isFlying() {
        return isFlying;
    }

    public int getBaseAttackPower() {
        return baseAttackPower;
    }

    public void setBaseAttackPower(int baseAttackPower) {
        this.baseAttackPower = baseAttackPower;
    }
}