import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayInTerminal {
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void play() throws InvalidCommandException {
        System.out.println("Login:");
        System.out.println("Email: ");
        Scanner sc = new Scanner(System.in);
        String email = sc.next();
        System.out.println("Password: ");
        String password = sc.next();
        int accountNumber = Game.getInstance().verifyLogin(email, password);
        if (accountNumber > -1) {
            System.out.println("Autentificare reusita!");

            createAccount(Game.getInstance().accountList, accountNumber);
            Character character = selectCharacter(Game.getInstance().accountList, accountNumber);
            printInfoGame();
            Grid grid = Grid.getObject();
            //System.out.println("oy fa" + character.oy);
            Grid.generateGrid(character);
            int letsPlay = 0;

                while (letsPlay == 0) {
                    try {
                    String num = sc.next();
                    if (isNumeric(num)) {
                        int move = Integer.parseInt(num);
                        switch (move) {
                            case 1:
                                letsPlay = grid.goNorth();
                                if (letsPlay != 1)
                                    grid.printGrid();
                                break;
                            case 2:
                                letsPlay = grid.goSouth();
                                if (letsPlay != 1) {
                                    grid.printGrid();
                                }
                                break;
                            case 3:
                                letsPlay = grid.goEast();
                                if (letsPlay != 1)
                                    grid.printGrid();
                                break;
                            case 4:
                                letsPlay = grid.goWest();
                                if (letsPlay != 1)
                                    grid.printGrid();
                                break;
                            default:
                                try {
                                    throw new InvalidCommandException("Nu este in regula comanda.");
                                } catch (InvalidCommandException e) {
                                    System.out.println("This move does not exist.");
                                }
                        }
                    } else throw new InvalidCommandException("Nope");

                } catch(InvalidCommandException e){
                    System.out.println("This move does not exist");
                }
            }
        } else System.out.println("Credentiale incorecte");
    }


    public void printInfoGame() {
        System.out.println("Press 1 to go north, 2 to go south, 3 to go east, 4 to go west.");
        System.out.println("If you reach an enemy, you'll fight until one dies.");
        System.out.println("If you reach a shop, you can buy potions to help you win.");
        System.out.println("Each empty cell gives you the chance to win extra life.");
        System.out.println("Reach the 'F' cell to win!");
    }

    private Character selectCharacter(ArrayList<JSONObject> accountList, int accountNumber) {
        JSONArray charactersLIst = (JSONArray) accountList.get(accountNumber).get("characters");
        System.out.println("Select character: ");
        for (int i = 0; i < charactersLIst.size(); i++) {
            System.out.println(i + 1 + ". " + charactersLIst.get(i).toString());
        }
        Scanner sc = new Scanner(System.in);
        int numberOfCharacter = sc.nextInt() - 1;
        JSONObject characterProps = (JSONObject) charactersLIst.get(numberOfCharacter);
        Character character = new Character() {

        };
        character.currentExperience = Integer.parseInt(characterProps.get("experience").toString());
        character.currentLevel = Integer.parseInt(characterProps.get("level").toString());
        character.currentLife = 100;
        character.currentMana = 75 / (numberOfCharacter + 1);
        character.name = characterProps.get("name").toString();
        character.ox = 0;
        character.oy = 0;
        CharacterFactory characterFactory = new CharacterFactory();
        String type = characterProps.get("profession").toString();
        Object unknown = characterFactory.getCharacter(type);
        if (unknown instanceof Mage) {
            Mage mage = new Mage();
            character.dexterity = mage.dexterity;
            character.charisma = mage.charisma;
            //System.out.println("Carisma " + character.charisma);
            character.strenght = mage.strenght;
            character.ablility.add(new Ice());
            character.ablility.add(new Earth());
            character.ablility.add(new Fire());
            character.ice = mage.ice;
            character.earth = mage.earth;
            character.fire = mage.fire;
            character.inventory.maxWeight = mage.maxWeight;
        }
        if (unknown instanceof Warrior) {
            Warrior warrior = new Warrior();
            character.dexterity = warrior.dexterity;
            character.charisma = warrior.charisma;
            // System.out.println("Carisma " + character.charisma);
            character.ablility.add(new Ice());
            character.ablility.add(new Earth());
            character.ablility.add(new Fire());
            character.strenght = warrior.strenght;
            character.ice = warrior.ice;
            character.earth = warrior.earth;
            character.fire = warrior.fire;
            character.inventory.maxWeight = warrior.maxWeight;
        }
        if (unknown instanceof Rogue) {
            Rogue rogue = new Rogue();
            character.dexterity = rogue.dexterity;
            character.charisma = rogue.charisma;
            // System.out.println("Carisma " + character.charisma);
            character.ablility.add(new Ice());
            character.ablility.add(new Earth());
            character.ablility.add(new Fire());
            character.strenght = rogue.strenght;
            character.ice = rogue.ice;
            character.earth = rogue.earth;
            character.fire = rogue.fire;
        }


        System.out.println(character.name + " was chosen. Let's begin the adventure!");

        return character;

    }

    public void createAccount(ArrayList<JSONObject> accountList, int accountNumber) {
        Account account = new Account();
        String name = accountList.get(accountNumber).get("name").toString();
        String country = accountList.get(accountNumber).get("country").toString();
        JSONObject credentials = (JSONObject) accountList.get(accountNumber).get("credentials");
        String email1 = (String) credentials.get("email");
        String password1 = (String) credentials.get("password");
        Credentials credentials1 = new Credentials(email1, password1);
        JSONArray favouriteGames = (JSONArray) accountList.get(accountNumber).get("favorite_games");
        ArrayList<String> favourite_games = new ArrayList<String>();
        for (Object favouriteGame : favouriteGames) {
            favourite_games.add(favouriteGame.toString());
        }
        JSONArray characterList = (JSONArray) accountList.get(accountNumber).get("characters");
        ArrayList<Character> character_list = new ArrayList<Character>();
        for (int i = 0; i < characterList.size(); i++) {
            Character character = new Character() {
                public char returnElement() {
                    return '0';
                }
            };
            JSONObject curCh = (JSONObject) characterList.get(i);
            character.name = curCh.get("name").toString();
            String type = curCh.get("profession").toString();
            character.currentExperience = Integer.parseInt(curCh.get("experience").toString());
            character.currentLevel = Integer.parseInt(curCh.get("level").toString());
            character.currentLife = 100;
            character.currentMana = 75 / (i + 1);
            character.ox = 0;
            character.oy = 0;
            CharacterFactory characterFactory = new CharacterFactory();
            Object unknown = characterFactory.getCharacter(type);
            if (unknown instanceof Mage) {
                Mage mage = new Mage();
                character.dexterity = mage.dexterity;
                character.charisma = mage.charisma;
                //System.out.println("Carisma " + character.charisma);
                character.strenght = mage.strenght;
                character.ablility.add(new Ice());
                character.ablility.add(new Earth());
                character.ablility.add(new Fire());
                character.ice = mage.ice;
                character.earth = mage.earth;
                character.fire = mage.fire;
                character.inventory.maxWeight = mage.maxWeight;
            }
            if (unknown instanceof Warrior) {
                Warrior warrior = new Warrior();
                character.dexterity = warrior.dexterity;
                character.charisma = warrior.charisma;
                // System.out.println("Carisma " + character.charisma);
                character.ablility.add(new Ice());
                character.ablility.add(new Earth());
                character.ablility.add(new Fire());
                character.strenght = warrior.strenght;
                character.ice = warrior.ice;
                character.earth = warrior.earth;
                character.fire = warrior.fire;
                character.inventory.maxWeight = warrior.maxWeight;
            }
            if (unknown instanceof Rogue) {
                Rogue rogue = new Rogue();
                character.dexterity = rogue.dexterity;
                character.charisma = rogue.charisma;
                // System.out.println("Carisma " + character.charisma);
                character.ablility.add(new Ice());
                character.ablility.add(new Earth());
                character.ablility.add(new Fire());
                character.strenght = rogue.strenght;
                character.ice = rogue.ice;
                character.earth = rogue.earth;
                character.fire = rogue.fire;
            }

            character_list.add(character);

        }

        int numberOfGamesPlayed = Integer.parseInt(accountList.get(accountNumber).get("maps_completed").toString());
        Information.InformationBuilder informationBuilder = new Information.InformationBuilder();
        informationBuilder
                .setCredentials(credentials1)
                .setFavouriteGames(favourite_games)
                .setCountry(country)
                .setName(name);
        account.information = informationBuilder.build();
        account.characterList = character_list;
        account.numberOfGamesPlayed = numberOfGamesPlayed;
    }
}
