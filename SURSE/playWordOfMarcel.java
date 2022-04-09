import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class playWordOfMarcel extends JFrame {
    Character character = WorldOfMarcel.character;
    String move;
    int enemiesKilled;

    public playWordOfMarcel(int accountNumber, Character character, String move) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.move = move;
        play(move);
    }


    public void play(String command) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        if (command.equals("n")) {
            if (goNorth(WorldOfMarcel.character) == 0) {
                JPanel pane = initialize(WorldOfMarcel.character);
                setVisible(false);
                setContentPane(pane);
                pane.setVisible(true);
            } else {
                new EndPage(character, WorldOfMarcel.accountNumber).setVisible(true);
                dispose();
            }
        } else if (command.equals("e")) {
            if (goEast(WorldOfMarcel.character) == 0) {
                JPanel pane = initialize(WorldOfMarcel.character);
                setVisible(false);
                setContentPane(pane);
                pane.setVisible(true);
            } else {
                new EndPage(character, WorldOfMarcel.accountNumber).setVisible(true);
                dispose();
            }
        } else if (command.equals("s")) {
            if (goSouth(WorldOfMarcel.character) == 0) {
                JPanel pane = initialize(WorldOfMarcel.character);
                setVisible(false);
                setContentPane(pane);
                pane.setVisible(true);
            } else {
                new EndPage(character, WorldOfMarcel.accountNumber).setVisible(true);
                dispose();
            }
        } else if (command.equals("v")) {
            if (goWest(WorldOfMarcel.character) == 0) {
                JPanel pane = initialize(WorldOfMarcel.character);
                setVisible(false);
                setContentPane(pane);
                pane.setVisible(true);
            } else {
                new EndPage(character, WorldOfMarcel.accountNumber).setVisible(true);
                dispose();
            }
        }


    }

    public JPanel generateGrid(Character character) throws IOException {
        int ok = 0;
        int viz = 0;
        JPanel board = new JPanel(new GridLayout(3, 5));
        for (int i = 0; i < 3 * 5; i++) {
            int row = i / 5;
            int col = i % 5;
            viz = 0;

            JButton currentButton = WorldOfMarcel.buttonList.get(i);
            Cell currentCell1 = WorldOfMarcel.jButtonCellHashMap.get(currentButton);
            if (WorldOfMarcel.character.ox == col && WorldOfMarcel.character.oy == row && ok == 0) {
                JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                button.setText("P");
                Image img = ImageIO.read(new File("src/main/resources/pacman.png"));
                button.setIcon(new ImageIcon(img));
                //System.out.print("P");
                currentCell1.vizited = true;
                viz++;
            }

            if (viz == 0) {
                if (currentCell1.getCellTYpe().equals("EMPTY") && !currentCell1.vizited) {
                    JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                    button.setText("?");
                    WorldOfMarcel.buttonList.set(row * 5 + col, button);
                } else if (currentCell1.getCellTYpe().equals("ENEMY") && currentCell1.vizited) {
                    JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                    button.setText("E");
                    Image img = ImageIO.read(new File("src/main/resources/ghost.png"));
                    button.setIcon(new ImageIcon(img));
                    WorldOfMarcel.buttonList.set(i, button);
                } else if (currentCell1.getCellTYpe().equals("SHOP") && currentCell1.vizited) {
                    JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                    button.setText("S");
                    Image img = ImageIO.read(new File("src/main/resources/shop.png"));
                    button.setIcon(new ImageIcon(img));
                    WorldOfMarcel.buttonList.set(i, button);
                } else if (currentCell1.getCellTYpe().equals("FINISH") && currentCell1.vizited) {
                    JOptionPane.showMessageDialog(null, "You wooon! :)");
                    setVisible(false);
                    new EndPage(character, WorldOfMarcel.accountNumber).setVisible(true);
                    JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                    button.setText("F");
                    Image img = ImageIO.read(new File("src/main/resources/marcel.jpg"));
                    button.setIcon(new ImageIcon(img));
                    WorldOfMarcel.buttonList.set(i, button);
                } else if (currentCell1.getCellTYpe().equals("EMPTY") && currentCell1.vizited) {
                    JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                    button.setText("N");
                    Image img = ImageIO.read(new File("src/main/resources/apple.png"));
                    button.setIcon(new ImageIcon(img));
                    currentCell1.cellElement = 'N';
                    WorldOfMarcel.buttonList.set(i, button);
                } else if (currentCell1.getCellTYpe().equals("SHOP") && !currentCell1.vizited) {
                    JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                    button.setText("?");
                    WorldOfMarcel.buttonList.set(i, button);
                } else if (currentCell1.getCellTYpe().equals("ENEMY") && !currentCell1.vizited) {
                    JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                    button.setText("?");
                    WorldOfMarcel.buttonList.set(i, button);
                } else if (currentCell1.getCellTYpe().equals("FINISH") && !currentCell1.vizited) {
                    JButton button = WorldOfMarcel.cellJButtonHashMap.get(currentCell1);
                    button.setText("?");
                    WorldOfMarcel.buttonList.set(i, button);
                }
            }
        }

        for (int i = 0; i < 15; i++)
            board.add(WorldOfMarcel.buttonList.get(i));
        return board;
    }


    public JPanel initialize(Character character) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Color color = new Color(204, 204, 255);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();// get screen size
        setBounds(100, 100, 950, 800);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        JLabel gameTitle = new JLabel("World of Marcel");
        gameTitle.setFont(new Font("Tahoma", Font.PLAIN, 29));
        gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel charaterLife = new JLabel(String.valueOf("Life: " + character.currentLife));
        charaterLife.setFont(new Font("Tahoma", Font.PLAIN, 19));
        JLabel characterCoins = new JLabel(String.valueOf("Coins: " + character.inventory.coins));
        characterCoins.setFont(new Font("Tahoma", Font.PLAIN, 19));
        JLabel characterMana = new JLabel(String.valueOf("Mana: " + character.currentMana));
        characterMana.setFont(new Font("Tahoma", Font.PLAIN, 19));
        JLabel characterLevel = new JLabel(String.valueOf("Level: " + character.currentLevel));
        characterLevel.setFont(new Font("Tahoma", Font.PLAIN, 19));
        JPanel WordOfMarcel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(WordOfMarcel, BoxLayout.Y_AXIS);
        WordOfMarcel.setLayout(boxlayout);
        WordOfMarcel.add(gameTitle);
        WordOfMarcel.setBackground(color);
        WordOfMarcel.add(Box.createRigidArea(new Dimension(5, 100)));
        JPanel BoardGame = generateGrid(character);
        WordOfMarcel.add(BoardGame);
        WordOfMarcel.add(Box.createRigidArea(new Dimension(5, 70)));
        JTextField component = new JTextField(10);
        JButton button = new JButton("Go!");
        button.setMaximumSize(button.getPreferredSize());
        WordOfMarcel.add(button);
        //component.setBounds(10, 10, 10, 10);
        //component.addKeyListener(new keyListener.MyKeyListener());
        component.setMaximumSize(component.getPreferredSize());
        JButton moves = new JButton("Go!");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (component.getText().equalsIgnoreCase("s") || component.getText().equalsIgnoreCase("n") || component.getText().equalsIgnoreCase("e") || component.getText().equalsIgnoreCase("v")) {
                    String move = component.getText();
                    System.out.println(move);
                    playWordOfMarcel playWordOfMarcel = null;
                    try {
                        playWordOfMarcel = new playWordOfMarcel(WorldOfMarcel.accountNumber, character, move);
                    } catch (UnsupportedAudioFileException ex) {
                        ex.printStackTrace();
                    } catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    setVisible(false);
                    playWordOfMarcel.setVisible(true);
                }
            }
        });
        WordOfMarcel.add(component);
        WordOfMarcel.add(characterCoins);
        WordOfMarcel.add(characterMana);
        WordOfMarcel.add(characterLevel);
        WordOfMarcel.add(charaterLife);

        return WordOfMarcel;
    }

    public int goNorth(Character character) {
        int fightResult;
        int coins = (int) (Math.random() * 100);
        if (character.oy == 0)
            System.out.println("It's impossible, sorry...");
        else {
            character.oy--;
            //System.out.println(character.oy * 3 + character.ox);
            JButton button = WorldOfMarcel.buttonList.get(character.oy * 5 + character.ox);
            Cell cell = WorldOfMarcel.jButtonCellHashMap.get(button);
            if (cell.getCellTYpe().equals("ENEMY")) {
                fightResult = fight();
                cell.vizited = true;
                return fightResult;
            } else if (cell.getCellTYpe().equals("SHOP")) {
                vizitShop();

            } else if (cell.getCellTYpe().equals("FINISH")) {
                //grid.printGrid();
                //printStory((Cell) grid.get(character.oy*3+character.ox));
                System.out.println("Well done!");
                return 1;
            } else if (cell.getCellTYpe().equals("EMPTY") && !cell.vizited) {
                if ((Math.random() * 100) < 20)
                    character.inventory.coins += coins;
            }
        }
        return 0;

    }

    public int goSouth(Character character) {
        int fightResult;
        int coins = (int) (30 + Math.random() * (100 - 30));
        JButton button = WorldOfMarcel.buttonList.get(character.oy * 5 + character.ox);
        Cell cell = WorldOfMarcel.jButtonCellHashMap.get(button);
        if (character.oy == 3 - 1)
            System.out.println("It's impossible, sorry...");
        else {
            character.oy++;
            if (cell.getCellTYpe().equals("ENEMY")) {
                fightResult = fight();
                cell.vizited = true;
                return fightResult;
            } else if (cell.getCellTYpe().equals("SHOP")) {
                vizitShop();

            } else if (cell.getCellTYpe().equals("FINISH")) {
                System.out.println("Well done!");
                return 1;

            } else if (cell.getCellTYpe().equals("EMPTY") && !cell.vizited) {
                if ((Math.random() * 100) < 20)
                    character.inventory.coins += coins;
            }
        }
        return 0;

    }

    public int goEast(Character character) {
        JButton button = WorldOfMarcel.buttonList.get(character.oy * 5 + character.ox);
        Cell cell = WorldOfMarcel.jButtonCellHashMap.get(button);
        int fightResult;
        int coins = (int) (Math.random() * 100);
        if (character.ox == 5 - 1)
            System.out.println("It's impossible, sorry...");
        else {
            character.ox++;
            if (cell.getCellTYpe().equals("ENEMY")) {
                fightResult = fight();
                cell.vizited = true;
                return fightResult;
            } else if (cell.getCellTYpe().equals("SHOP")) {
                vizitShop();

            } else if (cell.getCellTYpe().equals("FINISH")) {
                System.out.println("Well done!");
                return 1;

            } else if (cell.getCellTYpe().equals("EMPTY") && !cell.vizited) {
                if ((0 + (Math.random() * 100 - 0)) < 20)
                    character.inventory.coins += coins;
            }
        }
        return 0;

    }

    public int goWest(Character character) {
        int fightResult;
        int coins = (int) (Math.random() * 100);
        if (character.ox == 0)
            System.out.println("It's impossible, sorry...");
        else {
            character.ox--;
            JButton button = WorldOfMarcel.buttonList.get(character.oy * 5 + character.ox);
            Cell cell = WorldOfMarcel.jButtonCellHashMap.get(button);
            if (cell.getCellTYpe().equals("ENEMY") && !cell.vizited) {
                fightResult = fight();
                cell.vizited = true;
                return fightResult;
            } else if (cell.getCellTYpe().equals("SHOP")) {
                vizitShop();
            } else if (cell.getCellTYpe().equals("FINISH")) {
                System.out.println("Well done!");
                return 1;
            } else if (cell.getCellTYpe().equals("EMPTY") && !cell.vizited) {
                if ((Math.random() * 100) < 20)
                    character.inventory.coins += coins;
            }
        }
        return 0;

    }

    public static int fight() {
        JOptionPane.showMessageDialog(null, "You reached an Enemy! Fight until one of you guys die, in terminal. I'll watch :)");
        int enemyNumber = 0;
        for (int i = 0; i < WorldOfMarcel.character.oy * 5 + WorldOfMarcel.character.ox; i++) {
            JButton currentButton = WorldOfMarcel.buttonList.get(i);
            Cell cell1 = WorldOfMarcel.jButtonCellHashMap.get(currentButton);
            if (cell1.getCellTYpe().equals("ENEMY"))
                enemyNumber++;
        }
        Scanner sc = new Scanner(System.in);
        if (enemyNumber >= 3)
            enemyNumber = 2;
        Enemy enemy = WorldOfMarcel.enemyArrayList.get(enemyNumber);
        int turn = 0;
        while (true) {
            if (WorldOfMarcel.character.currentLife <= 0) {
                System.out.println("You lost");
                return 1;
            }
            if (enemy.currentLife <= 0) {
                System.out.println("Good job! Go ahead!");
                WorldOfMarcel.killedEnemies++;
                WorldOfMarcel.character.inventory.coins += 80;
                return 0;
            }
            if (turn % 2 == 0) {
                System.out.println("Choose a potion:");
                if (WorldOfMarcel.character.inventory.potions.size() == 0) {
                    System.out.println("Sorry you don't have potions left!");
                } else {
                    for (int i = 0; i < WorldOfMarcel.character.inventory.potions.size(); i++) {
                        System.out.println(i + " " + WorldOfMarcel.character.inventory.potions.get(i));
                    }
                    System.out.println("If you don't want a potion, press -1.");
                    int potionNo = sc.nextInt();
                    if (potionNo != -1) {
                        if (WorldOfMarcel.character.inventory.potions.get(potionNo) instanceof HealthPotion) {
                            WorldOfMarcel.character.regenerateLife(WorldOfMarcel.character.inventory.potions.get(potionNo).regenerateVal());
                            WorldOfMarcel.character.inventory.potions.remove(potionNo);
                        } else {
                            WorldOfMarcel.character.regenerateMana(WorldOfMarcel.character.inventory.potions.get(potionNo).regenerateVal());
                            WorldOfMarcel.character.inventory.potions.remove(potionNo);
                        }
                    }

                }
                System.out.println();
                System.out.println("If you want to use your charisma " + WorldOfMarcel.character.charisma + " press 0");
                System.out.println();
                System.out.println("If you want to use your strenght " + WorldOfMarcel.character.strenght + " press 1");
                System.out.println();
                System.out.println("If you want to use your dexterity " + WorldOfMarcel.character.dexterity + " press 2");
                System.out.println();
                System.out.println("If you want to use your spells, make sure you have enough mana: " + WorldOfMarcel.character.currentMana);
                System.out.println();
                int spellChoice;
                for (int i = 0; i < WorldOfMarcel.character.ablility.size(); i++) {
                    System.out.println("If you want this one press " + (i + WorldOfMarcel.character.ablility.size()) + " " + WorldOfMarcel.character.ablility.get(i) + " " + WorldOfMarcel.character.ablility.get(i).manaCost);
                    System.out.println();

                }
                System.out.println("If you believe you're too good, then press P.");
                String choice = sc.next();
                try {
                    if (!isNumeric(choice)) {
                        if (choice.equals("P"))
                            turn++;
                        else throw new InvalidCommandException(":(");
                        continue;
                    }
                } catch (InvalidCommandException e) {
                    System.out.println("Thsi command is not good");
                }
                try {
                    if (isNumeric(choice)) {

                        int finalChoice = Integer.parseInt(choice);
                        if (finalChoice < 6) {
                            spellChoice = finalChoice - 3;
                            if (spellChoice > 0) {
                                if (WorldOfMarcel.character.currentMana < WorldOfMarcel.character.ablility.get(spellChoice).manaCost) {
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
                                    enemy.currentLife -= WorldOfMarcel.character.charisma / 3;
                                    break;
                                case 1:
                                    enemy.currentLife -= WorldOfMarcel.character.strenght / 4;
                                    break;
                                case 2:
                                    enemy.currentLife -= WorldOfMarcel.character.dexterity / 2;
                                    break;
                                default:

                                    if (WorldOfMarcel.character.currentMana < WorldOfMarcel.character.ablility.get(spellChoice).manaCost) {
                                        System.out.println("You don't have enough coins for mana. I have already told you. Now let the enemy play.");
                                        throw new ArrayIndexOutOfBoundsException();
                                        //break;
                                    } else {
                                        enemy.currentLife -= enemy.recieveDamage(WorldOfMarcel.character.ablility.get(spellChoice).damage);
                                        WorldOfMarcel.character.currentMana -= WorldOfMarcel.character.ablility.get(spellChoice).manaCost;
                                        WorldOfMarcel.character.ablility.remove(enemy.ablility.get(spellChoice));
                                    }
                            }
                        } else throw new InvalidCommandException(":(");
                    }
                } catch (InvalidCommandException e) {
                    System.out.println("This command does not exist");
                }

                turn++;

            } else {
                System.out.println("This is your life before attack: " + WorldOfMarcel.character.currentLife);
                System.out.println("The enemy is thinking...");
                int rand = (int) (0 + (Math.random() * 2 - 0));
                int damage = WorldOfMarcel.character.recieveDamage(enemy.ablility.get(rand));
                WorldOfMarcel.character.currentLife -= damage;
                System.out.println(damage);
                System.out.println("This is your life after the attack: " + WorldOfMarcel.character.currentLife);
                turn++;
            }
        }
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

    public static int vizitShop() {
        JOptionPane.showMessageDialog(null, "You reached The Magic Shop! Select what do you want to buy from terminal.");
        int shopNumber = 0;
        for (int i = 0; i < WorldOfMarcel.character.oy * 5 + WorldOfMarcel.character.ox; i++) {
            JButton currentButton = WorldOfMarcel.buttonList.get(i);
            Cell cell1 = WorldOfMarcel.jButtonCellHashMap.get(currentButton);
            //Cell cell1 = (Cell) grid.get(i);
            if (cell1.getCellTYpe().equals("SHOP"))
                shopNumber++;
        }
        if (shopNumber >= 2)
            shopNumber = 0;
        if (WorldOfMarcel.shopArrayList.get(shopNumber).potions.size() == 0) {
            System.out.println("You bought everything from here!");
            return 0;
        }
        System.out.println("This is The Magic Shop! Buy potions as soon as you can :)");
        // System.out.println("These are your coins: " + character.inventory.coins);
        System.out.println("Select the number of the potion you would like to buy:");
        for (int i = 0; i < WorldOfMarcel.shopArrayList.get(shopNumber).potions.size(); i++) {
            System.out.println(i + " " + WorldOfMarcel.shopArrayList.get(shopNumber).getPotion(i).toString() + " Price: " + WorldOfMarcel.shopArrayList.get(shopNumber).getPotion(i).getPrice());
        }

        System.out.println("If nothing suits your taste, or you are finish shopping, type -1");
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is all you've got: " + WorldOfMarcel.character.inventory.coins);

        while (scanner.hasNext()) {
            try {
                String num = scanner.next();
                if (isNumeric(num)) {
                    int choice = Integer.parseInt(num);
                    if (choice == -1)
                        return 0;
                    if (choice < WorldOfMarcel.shopArrayList.get(shopNumber).potions.size()) {
                        WorldOfMarcel.character.buyPortion(WorldOfMarcel.character, WorldOfMarcel.shopArrayList, shopNumber, choice);
                        for (int i = 0; i < WorldOfMarcel.shopArrayList.get(shopNumber).potions.size(); i++) {
                            System.out.println(i + " " + WorldOfMarcel.shopArrayList.get(shopNumber).getPotion(i).toString() + " Price: " + WorldOfMarcel.shopArrayList.get(shopNumber).getPotion(i).getPrice());
                        }
                        System.out.println("This is all you've got: " + WorldOfMarcel.character.inventory.coins);
                    } else throw new InvalidCommandException(":(");
                } else throw new InvalidCommandException(":(");
            } catch (InvalidCommandException e) {
                System.out.println("This command does not exist!");
            }
        }

        return 0;
    }

}


