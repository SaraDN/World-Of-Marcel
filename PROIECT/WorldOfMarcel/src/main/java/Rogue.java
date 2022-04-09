public class Rogue extends Character{
    int maxWeight = 200;
    boolean earth=true;
    int dexterity=100;
    int charisma=40;
    int strenght=50;


    public char returnElement() {
        return '0';
    }
    public int receiveDamage(){
        return (charisma+strenght)/4;
    }
    public int getDamage(int damage){
        if(damage<dexterity/3){
            damage+=dexterity/7;
        }

        if(strenght/2+damage<20){
            return damage*2;
        }
        return damage;
    }
}
