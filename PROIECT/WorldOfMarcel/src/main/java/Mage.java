public class Mage extends Character{
    int maxWeight= 100;
    boolean ice = true;
    int charisma = 100;
    int strenght=50;
    int dexterity=10;

    public char returnElement() {
        return 'o';
    }
    public int receiveDamage(){
        return (strenght+dexterity)/4;
    }
    public int getDamage(int damage) {
        if (damage < charisma / 3) {
            damage += charisma / 7;
        }

        if (charisma / 2 + damage < 20) {
            return damage * 2;
        }
        return damage;
    }

}
