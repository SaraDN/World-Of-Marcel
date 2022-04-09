import java.util.Random;

public class Enemy extends Entity implements CellElement{
    int dexterity;
    int charisma;
    int strenght;
    @Override
    public char returnElement() {
        return 'E';
    }

    @Override
    public void accept(Vizitor vizitor) {

    }

    public int recieveDamage(int spell){
        int probability = (int)(Math.random()*100);
        if(probability>50) {
            return spell;
        }
        return 0;
    }

    public int getDamage(int damage){
        int prob = (int)(Math.random()*100);
        if(prob>50)
            return 2*damage;
        return damage;
    }
}
