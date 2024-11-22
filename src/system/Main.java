package system;

/**
 * メインクラス
 */
public class Main {
    public static void main(String[] args) {
        GameScenario scenario = new GameScenario();
        // 最初に王様に会いにいく。使命を与えられる。「魔王を倒してお姫様を助け出すこと」
        scenario.startScenario();
        
        // まずはゴブリン、スライム、ウェアウルフとランダム順で戦闘。戦闘終了の度に魔法使いの加入判定をかける。魔法使いが勇者のパーティに加入する確率は1/2。
        scenario.part1();
        
        // ゴブリン、スライム、ウェアウルフを全て倒し魔法使いと仲間になっている場合、勇者はスーパー勇者になる。スーパー勇者は飛ぶ能力を持つ。
        scenario.part2();
        
        // スーパー勇者になったあと、ゴブリン、スライム、ウェアウルフ、魔王のいずれか敵にあう。どの敵に会うかはランダム。
        // 魔王と戦っている間はお姫様がスーパー勇者を応援する。スーパー勇者の攻撃力が応援で一時的に上昇する。
        scenario.part3();
        
        //使命は完了してゲームは終了する。
        scenario.endScenario();
    }
}