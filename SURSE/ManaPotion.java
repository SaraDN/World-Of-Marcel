public class ManaPotion implements Potion{
    @Override
    public int getPrice() {
        return 16;
    }

    @Override
    public int regenerateVal() {

        return 20;
    }

    @Override
    public int getPotionWeight() {
        return 75;
    }

    @Override
    public void usePotion() {

    }
}
