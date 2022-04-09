import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Element{
    List<Spell> ablility;
    int currentLife;
    int maximumLife=100;
    int currentMana;
    int maximumMana=120;
    boolean fire;
    boolean ice;
    boolean earth;

    public Entity(){
        this.ablility=new ArrayList<>();
    }

    public void regenerateLife(int bonus){
        if(currentLife+bonus<=maximumLife)
            currentLife+=bonus;

        if(currentLife+bonus>maximumLife)
            currentLife=maximumLife;
    }

    public void regenerateMana(int bonus){
        if(currentMana+bonus<=maximumMana)
            currentMana+=bonus;

        if(currentMana+bonus>maximumMana)
            currentMana=maximumMana;
    }


}
