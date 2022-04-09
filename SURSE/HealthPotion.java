public class HealthPotion implements Potion{
    String name= "HealthPotion";
    @Override
    public int getPrice() {
        return 30;
    }

    @Override
    public int regenerateVal() {
        return 30;
    }

    @Override
    public int getPotionWeight() {
        return 120;
    }

    @Override
    public void usePotion() {

    }
}
