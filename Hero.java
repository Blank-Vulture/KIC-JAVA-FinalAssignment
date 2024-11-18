public class Hero extends Fighter {
    private int baseAttackPower;
    private boolean runAway;

    public Hero(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower;
        this.runAway = false;
    }

    public int getBaseAttackPower() {
        return baseAttackPower;
    }

}