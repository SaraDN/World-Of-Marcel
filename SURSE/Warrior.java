public class Warrior extends Character{
    int maxWeight = 300;
    int strenght=100;
    int charisma=20;
    int dexterity=80;
    boolean fire=true;
    public char returnElement() {
        return '0';
    }
    public int receiveDamage(){
        return (charisma+dexterity)/4;
    }
    public int getDamage(int damage){
        if(damage<strenght/3){
            damage+=strenght/7;
        }

        if(strenght/2+damage<20){
            return damage*2;
        }
        return damage;
    }
}
