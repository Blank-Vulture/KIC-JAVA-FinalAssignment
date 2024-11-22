package system;

import utility.Character;
import utility.Fighter;
import utility.Runner;

import java.util.Scanner;

public class Battle {

    private Fighter participant1;
    private Fighter participant2;
    private int turnCount;
    private boolean battleOver;
    private Scanner scanner; // Scannerをフィールドとして宣言

    // コンストラクタ
    public Battle(Fighter participant1, Fighter participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
        this.turnCount = 0;
        this.battleOver = false;
        this.scanner = new Scanner(System.in); // Scannerを初期化
    }

    // バトルを開始する
    public void startBattle() {
        System.out.println(((Character) participant1).getName() + "と" +
                ((Character) participant2).getName() + "の戦いが始まった！");
        determineTurnOrder();
        while (!isBattleOver()) {
            executeTurn();
        }
        endBattle();
        scanner.close(); // バトル終了時にScannerを閉じる
    }

    // ターンを実行する
    public void executeTurn() {
        turnCount++;
        System.out.println("\nターン " + turnCount + ":");

        // ヒーローの行動をユーザーが選択
        if (participant1 instanceof characters.Hero && ((Character) participant1).isAlive()) {
            characters.Hero hero = (characters.Hero) participant1;
            if (!hero.isRunAway()) {
                System.out.println("あなたのターンです。行動を選択してください。");
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
                        hero.attack(participant2);
                        break;
                    case 2:
                        hero.runAway();
                        battleOver = true;
                        return;
                    case 3:
                        hero.sleep();
                        break;
                    default:
                        System.out.println("無効な選択です。攻撃します。");
                        hero.attack(participant2);
                }
            } else {
                System.out.println(hero.getName() + "は逃げ出した！");
                battleOver = true;
                return;
            }
        } else {
            participant1.attack(participant2);
        }

        if (!((Character) participant2).isAlive()) {
            battleOver = true;
            return;
        }

        // 敵のターン
        if (participant2 instanceof characters.Slime && ((Character) participant2).isAlive()) {
            characters.Slime slime = (characters.Slime) participant2;
            slime.attack(participant1);
        } else {
            participant2.attack(participant1);
        }

        if (!((Character) participant1).isAlive()) {
            battleOver = true;
        }
    }

    // バトルを終了する
    public void endBattle() {
        if (!((Character) participant1).isAlive()) {
            System.out.println("\n" + ((Character) participant1).getName() + "は倒れた。");
            System.out.println(((Character) participant2).getName() + "の勝利！");
        } else if (!((Character) participant2).isAlive()) {
            System.out.println("\n" + ((Character) participant2).getName() + "は倒れた。");
            System.out.println(((Character) participant1).getName() + "の勝利！");
        } else if (participant1 instanceof Runner && ((Runner) participant1).isRunAway()) {
            System.out.println("\n" + ((Character) participant1).getName() + "は逃げ出した。");
            System.out.println(((Character) participant2).getName() + "の勝利！");
        } else {
            System.out.println("\nバトルは引き分けに終わった。");
        }
    }

    // バトルが終了したかどうかを確認する
    public boolean isBattleOver() {
        return battleOver;
    }

    // ターン順を決定する
    public void determineTurnOrder() {
        // シンプルに、participant1が先攻とする
        System.out.println(((Character) participant1).getName() + "が先に攻撃する！");
    }
}