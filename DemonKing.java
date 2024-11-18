public class DemonKing extends Fighter {
    private int baseAttackPower;

    public DemonKing(String name, int maxHp, int baseAttackPower) {
        super(name, maxHp);
        this.baseAttackPower = baseAttackPower;
    }