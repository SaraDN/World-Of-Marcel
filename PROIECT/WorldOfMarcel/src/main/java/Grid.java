import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Grid extends ArrayList {
    static int len;
    static int width;
    static Cell firstCell = new Cell();
    static ArrayList<Shop> shopArrayList = new ArrayList<>();
    static ArrayList<Enemy> enemyArrayList = new ArrayList<>();

    static Character character;
    private static Grid grid = new Grid(5, 5);

    private Grid(int len, int width) {
        this.len = len;
        this.width = width;
    }

    public void printStory(Cell cell) {
        System.out.println();
        char c = cell.cellElement;
        String type = null;
        if (c == 'S')
            type = "SHOP";
        else if (c == 'N' || c == 'Y')
            type = "EMPTY";
        else if (c == 'E')
            type = "ENEMY";
        else if (c == 'F')
            type = "FINISH";
        for (int j = 0; j < Game.getInstance().storyHashMap.size(); j++) {
            if (Game.getInstance().storyHashMap.containsKey(type)) {
                System.out.println(Game.getInstance().storyHashMap.get(type).get((int) (Math.random() * Game.getInstance().storyHashMap.get(type).size() - 1)));
                break;
            }
        }
    }

    public void printGrid() {
        int printStory = 0;
        int level = 0;
        int oxCounter = 0;
        int ok = 0;
        System.out.println(character.ox + " " + character.oy);
        if (character.ox == oxCounter && character.oy == level) {
            System.out.print("P");
            ok++;
        } else System.out.print("N ");
        for (int i = 1; i < len * width; i++) {
            Cell currentCell1 = (Cell) grid.get(i);
            oxCounter++;
            if (i == len * (level + 1)) {
                System.out.println();
                oxCounter = 0;
                level++;
            }
            if (character.ox == oxCounter && character.oy == level && ok == 0) {
                System.out.print("P");
                currentCell1.vizited = true;
            }
            if (currentCell1.getCellTYpe().equals("EMPTY") && !currentCell1.vizited)
                System.out.print("? ");
            else if (currentCell1.getCellTYpe().equals("ENEMY") && currentCell1.vizited) {
                System.out.print("E ");
            } else if (currentCell1.getCellTYpe().equals("SHOP") && currentCell1.vizited) {
                System.out.print("S ");
            } else if (currentCell1.getCellTYpe().equals("FINISH") && currentCell1.vizited) {
                printStory = 1;
                System.out.print("F ");
            } else if (currentCell1.getCellTYpe().equals("EMPTY") && currentCell1.vizited) {
                System.out.print("N ");
                currentCell1.cellElement = 'N';
            } else if (currentCell1.getCellTYpe().equals("SHOP") && !currentCell1.vizited) {
                System.out.print("? ");
            } else if (currentCell1.getCellTYpe().equals("ENEMY") && !currentCell1.vizited) {
                System.out.print("? ");
            } else if (currentCell1.getCellTYpe().equals("FINISH") && !currentCell1.vizited) {
                System.out.print("? ");
            }
        }
        if (printStory == 0)
            printStory((Cell) grid.get(character.oy * len + character.ox));

    }

    public static Grid generateGrid(Character character12) {
        firstCell.ox = 0;
        firstCell.oy = 0;
        firstCell.vizited = true;
        character = character12;
        int numEnemy = 4;
        int numShops = 2;
        int level = 0;
        int fCell = 1;
        int oxCounter = 0;
        System.out.print("P ");
        firstCell.setCellTYpe(Cell.cellType.EMPTY);
        firstCell.cellElement = 'Y';

        grid.add(0, firstCell);
        for (int i = 1; i < len * width; i++) {
            Cell currentCell = new Cell();
            oxCounter++;
            currentCell.ox = oxCounter;
            currentCell.oy = level;
            currentCell.vizited = false;
            if (i == len * (level + 1)) {
                level++;
                oxCounter = 0;
                currentCell.ox = 0;
                currentCell.oy = level;
                System.out.println();
            }

            Random rlen = new Random();
            int random = rlen.nextInt();
            if (random % 3 == 0 && numEnemy > 0) {
                System.out.print("? ");
                Cell.cellType type = Cell.cellType.ENEMY;
                currentCell.setCellTYpe(type);
                currentCell.cellElement = 'E';
                Enemy enemy = new Enemy();
                enemy.currentLife = 25 * numEnemy;
                enemy.currentMana = 30 * numEnemy;
                if (numEnemy % 2 == 0) {
                    enemy.ice = true;
                    enemy.ablility.add(new Ice());
                    enemy.ablility.add(null);
                    enemy.ablility.add(null);
                }
                if (numEnemy % 4 == 0) {
                    enemy.fire = true;
                    enemy.ablility.add(null);
                    enemy.ablility.add(null);
                    enemy.ablility.add(new Fire());
                }
                if (numEnemy % 3 == 0) {
                    enemy.earth = true;
                    enemy.ablility.add(null);
                    enemy.ablility.add(null);
                    enemy.ablility.add(new Earth());
                }
                if (numEnemy == 1) {
                    enemy.earth = true;
                    enemy.ice = true;
                    enemy.fire = true;
                    enemy.ablility.add(new Fire());
                    enemy.ablility.add(new Earth());
                    enemy.ablility.add(new Ice());

                }
                enemyArrayList.add(enemy);
                grid.add(i, currentCell);
                numEnemy--;
                continue;
            }
            if (random % 5 == 0 && fCell > 0) {
                System.out.print("? ");
                currentCell.cellElement = 'F';
                Cell.cellType type = Cell.cellType.FINISH;
                currentCell.setCellTYpe(type);
                grid.add(i, currentCell);
                fCell--;
                continue;
            }
            if (random % 2 == 0 && random % 3 != 0 && numShops > 0) {
                System.out.print("? ");
                Cell.cellType type = Cell.cellType.SHOP;
                currentCell.cellElement = 'S';
                currentCell.setCellTYpe(type);
                Shop shop = new Shop() {
                };
                if (numShops == 2) {
                    shop.addPotions(new ManaPotion());
                    shop.addPotions(new HealthPotion());
                    shop.addPotions(new HealthPotion());
                    shop.addPotions(new ManaPotion());
                }
                if (numShops == 1) {
                    shop.addPotions(new ManaPotion());
                    shop.addPotions(new HealthPotion());
                }
                shopArrayList.add(shop);

                grid.add(i, currentCell);
                numShops--;
                continue;
            }
            Cell.cellType type = Cell.cellType.EMPTY;
            currentCell.setCellTYpe(type);

            grid.add(i, currentCell);
            System.out.print("? ");
            currentCell.cellElement = '?';

        }

        //4 enemys, 2 shops
        System.out.println();

        return grid;

    }

    public static Grid getObject() {
        return grid;
    }

    public int goNorth() throws InvalidCommandException {
        int fightResult;
        int coins = (int) (Math.random() * 100);
        if (character.oy == 0)
            System.out.println("It's impossible, sorry...");
        else {
            character.oy--;
            Cell cell = (Cell) grid.get(character.oy * len + character.ox);
            if (cell.getCellTYpe().equals("ENEMY")) {
                fightResult = fight();
                cell.vizited = true;
                return fightResult;
            } else if (cell.getCellTYpe().equals("SHOP")) {
                vizitShop();

            } else if (cell.getCellTYpe().equals("FINISH")) {
                grid.printGrid();
                printStory((Cell) grid.get(character.oy * len + character.ox));
                System.out.println("Well done!");
                return 1;
            } else if (cell.getCellTYpe().equals("EMPTY") && !cell.vizited) {
                if ((Math.random() * 100) < 20)
                    character.inventory.coins += coins;
            }
        }
        return 0;

    }

    public int goSouth() throws InvalidCommandException {
        int fightResult;
        int coins = (int) (30 + Math.random() * (100 - 30));
        if (character.oy == width - 1)
            System.out.println("It's impossible, sorry...");
        else {
            character.oy++;
            Cell cell = (Cell) grid.get(character.oy * len + character.ox);
            if (cell.getCellTYpe().equals("ENEMY")) {
                fightResult = fight();
                cell.vizited = true;
                return fightResult;
            } else if (cell.getCellTYpe().equals("SHOP")) {
                vizitShop();

            } else if (cell.getCellTYpe().equals("FINISH")) {
                grid.printGrid();
                printStory((Cell) grid.get(character.oy * len + character.ox));
                System.out.println("Well done!");
                return 1;

            } else if (cell.getCellTYpe().equals("EMPTY") && !cell.vizited) {
                if ((Math.random() * 100) < 20)
                    character.inventory.coins += coins;
            }
        }
        return 0;

    }

    public int goEast() throws InvalidCommandException {
        int fightResult;
        int coins = (int) (Math.random() * 100);
        if (character.ox == len - 1)
            System.out.println("It's impossible, sorry...");
        else {
            character.ox++;
            Cell cell = (Cell) grid.get(character.oy * len + character.ox);
            if (cell.getCellTYpe().equals("ENEMY")) {
                fightResult = fight();
                cell.vizited = true;
                return fightResult;
            } else if (cell.getCellTYpe().equals("SHOP")) {
                vizitShop();

            } else if (cell.getCellTYpe().equals("FINISH")) {
                grid.printGrid();
                printStory((Cell) grid.get(character.oy * len + character.ox));
                System.out.println("Well done!");
                return 1;

            } else if (cell.getCellTYpe().equals("EMPTY") && !cell.vizited) {
                if ((0 + (Math.random() * 100 - 0)) < 20)
                    character.inventory.coins += coins;
            }
        }
        return 0;

    }

    public int goWest() throws InvalidCommandException {
        int fightResult;
        int coins = (int) (Math.random() * 100);
        if (character.ox == 0)
            System.out.println("It's impossible, sorry...");
        else {
            character.ox--;
            Cell cell = (Cell) grid.get(character.oy * len + character.ox);
            if (cell.getCellTYpe().equals("ENEMY") && !cell.vizited) {
                fightResult = fight();
                cell.vizited = true;
                return fightResult;
            } else if (cell.getCellTYpe().equals("SHOP")) {
                vizitShop();
            } else if (cell.getCellTYpe().equals("FINISH")) {
                grid.printGrid();
                printStory((Cell) grid.get(character.oy * len + character.ox));
                System.out.println("Well done!");
                return 1;
            } else if (cell.getCellTYpe().equals("EMPTY") && !cell.vizited) {
                if ((Math.random() * 100) < 20)
                    character.inventory.coins += coins;
            }
        }
        return 0;

    }

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

    public int fight() throws InvalidCommandException {
        int enemyNumber = 0;
        for (int i = 0; i < character.oy * len + character.ox; i++) {
            Cell cell1 = (Cell) grid.get(i);
            if (cell1.getCellTYpe().equals("ENEMY"))
                enemyNumber++;
        }
        Scanner sc = new Scanner(System.in);
        Enemy enemy = enemyArrayList.get(enemyNumber);
        int turn = 0;
        while (true) {
            if (character.currentLife <= 0) {
                System.out.println("You lost");
                return 1;
            }
            if (enemy.currentLife <= 0) {
                System.out.println("Good job! Go ahead!");
                character.inventory.coins += 80;
                return 0;
            }
            if (turn % 2 == 0) {
                System.out.println("Choose a potion:");
                if (character.inventory.potions.size() == 0) {
                    System.out.println("Sorry you don't have potions left!");
                } else {
                    for (int i = 0; i < character.inventory.potions.size(); i++) {
                        System.out.println(i + " " + character.inventory.potions.get(i));
                    }
                    System.out.println("If you don't want a potion, press -1.");
                    int potionNo = sc.nextInt();
                    if (potionNo != -1) {
                        if (character.inventory.potions.get(potionNo) instanceof HealthPotion) {
                            character.regenerateLife(character.inventory.potions.get(potionNo).regenerateVal());
                            character.inventory.potions.remove(potionNo);
                        } else {
                            character.regenerateMana(character.inventory.potions.get(potionNo).regenerateVal());
                            character.inventory.potions.remove(potionNo);
                        }
                    }


                }
                System.out.println();
                System.out.println("If you want to use your charisma " + character.charisma + " press 0");
                System.out.println();
                System.out.println("If you want to use your strenght " + character.strenght + " press 1");
                System.out.println();
                System.out.println("If you want to use your dexterity " + character.dexterity + " press 2");
                System.out.println();
                System.out.println("If you want to use your spells, make sure you have enough mana: " + character.currentMana);
                System.out.println();
                int spellChoice;
                for (int i = 0; i < character.ablility.size(); i++) {
                    System.out.println("If you want this one press " + (i + character.ablility.size()) + " " + character.ablility.get(i) + " " + character.ablility.get(i).manaCost);
                    System.out.println();

                }
                System.out.println("If you believe you're too good, then press P.");
                String choice = sc.next();
                try {
                    if (!isNumeric(choice)) {
                        if (choice.equals("P"))
                            turn++;
                        else throw new InvalidCommandException("Sorry");
                        continue;
                    }
                } catch (InvalidCommandException e) {
                    System.out.println("This command is not good.");
                }
                try {
                    if (isNumeric(choice)) {

                        int finalChoice = Integer.parseInt(choice);
                        if (finalChoice < 6) {
                            spellChoice = finalChoice - 3;
                            if (spellChoice > 0) {
                                if (character.currentMana < character.ablility.get(spellChoice).manaCost) {
                                    System.out.println("You don't have enough mana for spells.");
                                    System.out.println("Please select something else, or let the enemy play.");
                                    String lastChoice = sc.next();
                                    if (lastChoice.equals("P"))
                                        continue;
                                    ;
                                    finalChoice = Integer.parseInt(lastChoice);
                                }
                            }
                            switch (finalChoice) {
                                case -1:
                                    break;
                                case 0:
                                    enemy.currentLife -= character.charisma / 3;
                                    break;
                                case 1:
                                    enemy.currentLife -= character.strenght / 4;
                                    break;
                                case 2:
                                    enemy.currentLife -= character.dexterity / 2;
                                    break;
                                default:
                                    if (character.currentMana < character.ablility.get(spellChoice).manaCost) {
                                        System.out.println("You don't have enough coins for mana. I have already told you. Now let the enemy play.");
                                        break;
                                    } else {
                                        enemy.currentLife -= enemy.recieveDamage(character.ablility.get(spellChoice).damage);
                                        character.currentMana -= character.ablility.get(spellChoice).manaCost;
                                        character.ablility.remove(enemy.ablility.get(spellChoice));
                                    }
                            }
                        } else throw new InvalidCommandException(":P");
                    }
                } catch (InvalidCommandException e) {
                    System.out.println("This command does not exist");
                }
                turn++;
            } else {
                System.out.println("This is your life before attack: " + character.currentLife);
                System.out.println("The enemy is thinking...");
                int rand = (int) (0 + (Math.random() * 2 - 0));
                int damage = character.recieveDamage(enemy.ablility.get(rand));
                character.currentLife -= damage;
                System.out.println(damage);
                System.out.println("This is your life after the attack: " + character.currentLife);
                turn++;
            }
        }
    }

    public int vizitShop() {
        int shopNumber = 0;
        for (int i = 0; i < character.oy * len + character.ox; i++) {
            Cell cell1 = (Cell) grid.get(i);
            if (cell1.getCellTYpe().equals("SHOP"))
                shopNumber++;
        }
        if (shopArrayList.get(shopNumber).potions.size() == 0) {
            System.out.println("You bought everything from here!");
            return 0;
        }
        System.out.println("This is The Magic Shop! Buy potions as soon as you can :)");
        // System.out.println("These are your coins: " + character.inventory.coins);
        System.out.println("Select the number of the potion you would like to buy:");
        for (int i = 0; i < shopArrayList.get(shopNumber).potions.size(); i++) {
            System.out.println(i + " " + shopArrayList.get(shopNumber).getPotion(i).toString() + " Price: " + shopArrayList.get(shopNumber).getPotion(i).getPrice());
        }

        System.out.println("If nothing suits your taste, or you are finish shopping, type -1");
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is all you've got: " + character.inventory.coins);

        while (scanner.hasNext()) {
            try {
                String num = scanner.next();
                if (isNumeric(num)) {
                    int choice = Integer.parseInt(num);
                    if (choice == -1)
                        return 0;
                    if (choice < shopArrayList.get(shopNumber).potions.size()) {
                        character.buyPortion(character, shopArrayList, shopNumber, choice);
                        for (int i = 0; i < shopArrayList.get(shopNumber).potions.size(); i++) {
                            System.out.println(i + " " + shopArrayList.get(shopNumber).getPotion(i).toString() + " Price: " + shopArrayList.get(shopNumber).getPotion(i).getPrice());
                        }
                        System.out.println("This is all you've got: " + character.inventory.coins);
                    } else throw new InvalidCommandException(":(");
                } else throw new InvalidCommandException(":(");
            } catch (InvalidCommandException e) {
                System.out.println("This command does not exist");
            }

        }
        return 0;
    }

}
