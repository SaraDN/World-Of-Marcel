import java.util.ArrayList;

public abstract class Character extends Entity{
    String name;
    int ox;
    int oy;
    Inventory inventory;
    int currentExperience;
    int currentLevel;
    int strenght;
    int charisma;
    int dexterity;
    public Character(){
        inventory=new Inventory();
    }

    @Override
    public void accept(Vizitor vizitor) {

    }

    public boolean buyPortion(Character character, ArrayList<Shop> shopArrayList, int shopNumber, int choice) {
        if(shopArrayList.size()==0){
            System.out.println("You bought everything from here, get out! ;)");
            System.out.println("Press -1 to return to the game");
            return false;
        }
        int currentWeight = 0;
        for (int i = 0; i < character.inventory.potions.size(); i++) {
            currentWeight += character.inventory.potions.get(i).getPotionWeight();
        }
        if (currentWeight < character.inventory.maxWeight) {
            Potion potion = shopArrayList.get(shopNumber).getPotion(choice);
            if (currentWeight + potion.getPotionWeight() < character.inventory.maxWeight && character.inventory.coins >= potion.getPrice()) {
                character.inventory.potions.add(potion);
                character.inventory.coins -= potion.getPrice();
                shopArrayList.get(shopNumber).removePotion(choice);
                return true;
            } else if (currentWeight + potion.getPotionWeight() > character.inventory.maxWeight) {
                System.out.println("Too much weight, sorry!");

            } else if (character.inventory.coins <= potion.getPrice()) {
                System.out.println("You are too poor for this one.");

            }
        } else System.out.println("That's all you can carry, no more weight for you.");
        return false;
    }

    public int recieveDamage(Spell atribute){
        int probability = (int)(Math.random()*100);

        if(probability<50) {
            if (atribute instanceof Ice){
                return ((Ice) atribute).effect;
            }
            if (atribute instanceof Fire) {
                return ((Fire) atribute).effect;
            }
            if (atribute instanceof Earth) {
                return ((Earth) atribute).effect;
            }

        }
        return 0;
    }

    public void getDamage(){

    }
}
