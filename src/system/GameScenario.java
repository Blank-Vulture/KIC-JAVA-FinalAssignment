package system;

import characters.*;
import utility.Character;
import utility.Runner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * ゲームシナリオクラス
 */
public class GameScenario {
    private Hero hero;
    private SuperHero superHero;
    private King king;
    private Wizard wizard;
    private Princess princess;
    private List<Character> enemies; // 敵のリスト
    private boolean wizardJoined;
    private Scanner scanner;

    public GameScenario(Scanner scanner) {
        this.scanner = scanner; // Scannerを受け取る
        wizardJoined = false;
    }

    // 最初に王様に会いにいく。使命を与えられる。
    public void startScenario() {
        // 勇者の名前を入力
        System.out.print("勇者の名前を入力してください: ");
        String heroName = scanner.nextLine();
        hero = new Hero(heroName, 100, 20);

        // 王様に会う
        king = new King("王様", 9999);
        System.out.println("\n=== 王様との会話 ===");
        king.talk("ようこそ、" + hero.getName() + "よ。");
        king.talk("お姫様が魔王にさらわれてしまった。");
        king.talk("魔王を倒して、お姫様を助け出してくれないか。");
        hero.talk("はい、必ずお姫様を救い出します！");
        System.out.println("==================\n");
    }

    // パート1の実装
    public void part1() {
        System.out.println("=== パート1：冒険の始まり ===");

        // 敵のリストを初期化
        enemies = new ArrayList<>();
        enemies.add(new Goblin("ゴブリン", 50, hero.getBaseAttackPower()));
        enemies.add(new Slime("スライム", 30, hero.getBaseAttackPower()));
        enemies.add(new Werewolf("ウェアウルフ", 80, hero.getBaseAttackPower()));

        Random random = new Random();

        while (!enemies.isEmpty()) {
            // ランダムに敵を選択
            Character enemy = enemies.get(random.nextInt(enemies.size()));

            // 戦闘開始
            Battle battle;
            if (wizardJoined) {
                battle = new Battle(hero, enemy, wizard, null, scanner);
            } else {
                battle = new Battle(hero, enemy, null, null, scanner);
            }
            battle.startBattle();

            // 勇者が倒れた場合、ゲームオーバー
            if (!hero.isAlive()) {
                System.out.println("\n=== ゲームオーバー ===");
                System.exit(0);
            }

            // 敵を倒した場合、リストから削除
            if (!enemy.isAlive()) {
                System.out.println(enemy.getName() + "を倒した！");
                enemies.remove(enemy);
            } else if (hero.isRunAway()) {
                // 勇者が逃げた場合、冒険を続けるか確認
                System.out.println("\n戦闘から逃げ出した。");
                hero.resetRunAway(); // 逃走状態をリセット
                System.out.println("冒険を続けますか？（1: はい、2: いいえ）");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    choice = 2;
                }
                if (choice == 2) {
                    System.out.println("ゲームを終了します。");
                    System.exit(0);
                }
            }

            // 魔法使いの加入判定
            if (!wizardJoined) {
                if (random.nextBoolean()) { // 1/2の確率で仲間になる
                    System.out.println("\n誰かが近づいてくる...");
                    wizard = new Wizard("魔法使い", 70, hero.getBaseAttackPower());
                    wizard.talk("私は旅の魔法使い。あなたの旅に同行しましょうか？");

                    System.out.println("魔法使いが仲間になることを誘っています。仲間にしますか？（1: はい、2: いいえ）");
                    int choice;
                    try {
                        choice = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        choice = 2;
                    }

                    if (choice == 1) {
                        wizardJoined = true;
                        wizard.talk("よろしくお願いします。");
                    } else {
                        wizard.talk("そうですか、残念です。");
                    }
                }
            }
        }

        // 全ての敵を倒した場合
        if (enemies.isEmpty()) {
            System.out.println("\n全ての敵を倒した！");
        } else {
            System.out.println("\n冒険を続けます。");
            part1(); // 再度挑戦
        }
    }

    // パート2の実装
    public void part2() {
        if (enemies.isEmpty() && wizardJoined) {
            System.out.println("\n=== スーパー勇者にクラスチェンジ！ ===");
            superHero = new SuperHero(hero.getName(), hero.getMaxHp(), hero.getBaseAttackPower());
            System.out.println(hero.getName() + "はスーパー勇者になった！");
            System.out.println("飛ぶ能力を手に入れた！");
            System.out.println("============================\n");
        } else {
            System.out.println("\n条件を満たしていないため、先に進めません。ゲームオーバーです。");
            System.exit(0);
        }
    }

    // パート3の実装
    public void part3() {
        System.out.println("=== パート3：最後の戦い ===");

        // 敵のリストを再初期化（ゴブリン、スライム、ウェアウルフ、魔王）
        enemies = new ArrayList<>();
        enemies.add(new Goblin("ゴブリン", 50, superHero.getBaseAttackPower()));
        enemies.add(new Slime("スライム", 30, superHero.getBaseAttackPower()));
        enemies.add(new Werewolf("ウェアウルフ", 80, superHero.getBaseAttackPower()));
        enemies.add(new DemonKing("魔王", 200, superHero.getBaseAttackPower()));

        Random random = new Random();

        boolean missionComplete = false;

        while (!missionComplete) {
            // ランダムに敵を選択
            Character enemy = enemies.get(random.nextInt(enemies.size()));

            // 魔王との戦闘ではお姫様が応援
            if (enemy instanceof DemonKing) {
                princess = new Princess("お姫様", 9999);
                Battle battle = new Battle(superHero, enemy, wizardJoined ? wizard : null, princess, scanner);
                battle.startBattle();
            } else {
                // 通常の戦闘
                Battle battle = new Battle(superHero, enemy, wizardJoined ? wizard : null, null, scanner);
                battle.startBattle();
            }

            // スーパー勇者が倒れた場合、ゲームオーバー
            if (!superHero.isAlive()) {
                System.out.println("\n=== ゲームオーバー ===");
                System.exit(0);
            }

            // 敵を倒した場合、リストから削除
            if (!enemy.isAlive()) {
                System.out.println("\n" + enemy.getName() + "を倒した！");
                enemies.remove(enemy);
            } else if (superHero.isRunAway()) {
                // スーパー勇者が逃げた場合、冒険を続けるか確認
                System.out.println("\n戦闘から逃げ出した。");
                superHero.resetRunAway(); // 逃走状態をリセット
                System.out.println("冒険を続けますか？（1: はい、2: いいえ）");
                int choice;
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    choice = 2;
                }
                if (choice == 2) {
                    System.out.println("ゲームを終了します。");
                    System.exit(0);
                }
            }

            // 魔王を倒した場合、使命完了
            if (enemy instanceof DemonKing && (!enemy.isAlive() || (enemy instanceof Runner && ((Runner) enemy).isRunAway()))) {
                System.out.println("\n" + superHero.getName() + "は魔王を倒した！");
                missionComplete = true;
                enemies.remove(enemy);
            }
        }
    }

    // エンディング
    public void endScenario() {
            System.out.println("\n=== エンディング ===");
            princess.talk("ありがとうございます！" + superHero.getName() + "！");
            king.talk("よくぞ魔王を倒してくれた。お姫様を救い出してくれて感謝する。");
            System.out.println("\n=== ゲームクリア！ ===");
        System.exit(0);
    }
}