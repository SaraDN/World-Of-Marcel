import java.util.ArrayList;

public abstract class Shop implements CellElement{
    ArrayList<Potion> potions;
    public Shop(){
        this.potions=new ArrayList<>();
    }
    public char returnElement(){
        return 'S';
    }

    public void addPotions(Potion potion) {
        potions.add(potion);
    }

    public Potion getPotion(int index){
        return potions.get(index);
    }
    public Potion removePotion(int index){
        Potion potion1 = potions.get(index);
        potions.remove(index);
        return potion1;
    }
}
