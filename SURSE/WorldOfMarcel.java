import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class WorldOfMarcel extends JFrame {
    static int killedEnemies;
    static HashMap<Cell, JButton> cellJButtonHashMap;
    static HashMap<JButton, Cell> jButtonCellHashMap;
    static ArrayList<Shop> shopArrayList = new ArrayList<>();
    static ArrayList<Enemy> enemyArrayList = new ArrayList<>();
    static Character character;
    static int accountNumber;

    public WorldOfMarcel(int accountNumber, Character character) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.character=character;
        this.accountNumber=accountNumber;

    }

    public static ArrayList<JButton> buttonList = new ArrayList<JButton>();

    private static JButton getGridButton(int r, int c) {
        int index = r * 5 + c;
        return buttonList.get(index);
    }

    private JButton createGridButton() {
        return new JButton();
    }

    public static JLabel printStory(Cell cell) {
        JLabel story = new JLabel();
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
                story.setText(Game.getInstance().storyHashMap.get(type).get((int) (Math.random() * Game.getInstance().storyHashMap.get(type).size() - 1)));
                return story;
            }
        }
        return story;
    }



    public JPanel createBoardGame() {
        JPanel grid = new JPanel(new GridLayout(3, 5));
        //grid.setBorder(new EmptyBorder(10, 10, 10, 10));
        int numEnemy = 4;
        int numShops = 2;
        int fCell = 1;
        for (int i = 0; i < 3 * 5; i++) {
            int row = i / 5;
            int col = i % 5;
            JButton gb = createGridButton();
            Cell currentCell = new Cell();
            currentCell.vizited = false;
            currentCell.ox = col;
            currentCell.oy = row;
            Random rlen = new Random();
            int random = rlen.nextInt();
            if (i == 0) {
                currentCell.vizited = true;
                currentCell.cellElement = 'Y';
                currentCell.setCellTYpe(Cell.cellType.EMPTY);
                gb.setText("P");
                buttonList.add(gb);
                cellJButtonHashMap.put(currentCell, buttonList.get(row*5+col));
                jButtonCellHashMap.put(buttonList.get(row*5+col), currentCell);
                grid.add(gb);
                continue;
            }
            if (random % 3 == 0 && numEnemy > 0) {
                gb.setText("?");
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
                buttonList.add(gb);
                cellJButtonHashMap.put(currentCell,buttonList.get(row*5+col));
                jButtonCellHashMap.put(buttonList.get(row*5+col), currentCell);
                grid.add(gb);
                numEnemy--;
                continue;
            }
            if (random % 5 == 0 && fCell > 0) {
                gb.setText("?");
                currentCell.cellElement = 'F';
                Cell.cellType type = Cell.cellType.FINISH;
                currentCell.setCellTYpe(type);
                buttonList.add(gb);
                cellJButtonHashMap.put(currentCell, buttonList.get(row*5+col));
                jButtonCellHashMap.put(buttonList.get(row*5+col), currentCell);
                grid.add(gb);
                fCell--;
                continue;
            }
            if (random % 2 == 0 && random % 3 != 0 && numShops > 0) {
                gb.setText("?");
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
                buttonList.add(gb);

                cellJButtonHashMap.put(currentCell, buttonList.get(row*5+col));
                jButtonCellHashMap.put(buttonList.get(row*5+col), currentCell);
                grid.add(gb);
                numShops--;
                continue;
            }
            Cell.cellType type = Cell.cellType.EMPTY;
            currentCell.setCellTYpe(type);
            gb.setText("?");

            buttonList.add(gb);
            cellJButtonHashMap.put(currentCell, buttonList.get(row*5+col));
            jButtonCellHashMap.put(buttonList.get(row*5+col), currentCell);
            grid.add(gb);
        }
        return grid;
    }

    public void initialize() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        Color color = new Color(204, 204, 255);
        cellJButtonHashMap = new HashMap<>();
        jButtonCellHashMap = new HashMap<>();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();// get screen size
        setBounds(100, 100, 950, 800);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        JPanel WordOfMarcel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(WordOfMarcel, BoxLayout.Y_AXIS);
        WordOfMarcel.setLayout(boxlayout);
        JPanel BoardGame = createBoardGame();
        BoardGame.setBounds(300, 300, 600, 500);
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

        JTextField component = new JTextField(10);
        //component.addKeyListener(new keyListener.MyKeyListener());
        //component.getText();
        component.setMaximumSize( component.getPreferredSize() );

        JPanel infoPanel = new JPanel();
        JLabel label = new JLabel();
        label.setText("<html>Press n to go north, s to go south, e to go east, v to go west.<br> If you reach an enemy, you'll fight until one dies.<br>If you reach a shop, you can buy potions to help you win.<br></html>" +
                "Each empty cell gives you the chance to win extra coins.");
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        infoPanel.add(label);
        infoPanel.setBackground(color);
        WordOfMarcel.add(gameTitle);
        WordOfMarcel.add(Box.createRigidArea(new Dimension(5, 100)));
        WordOfMarcel.add(BoardGame);
        WordOfMarcel.add(Box.createRigidArea(new Dimension(5, 70)));
        WordOfMarcel.add(component);

        JButton button = new JButton("Go!");
        button.setMaximumSize(button.getPreferredSize());
        WordOfMarcel.add(button);
        WordOfMarcel.add(infoPanel);
        WordOfMarcel.add(characterCoins);
        WordOfMarcel.add(characterMana);
        WordOfMarcel.add(characterLevel);
        WordOfMarcel.add(charaterLife);
        WordOfMarcel.setBackground(color);
        setContentPane(WordOfMarcel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (component.getText().equalsIgnoreCase("s") || component.getText().equalsIgnoreCase("n") || component.getText().equalsIgnoreCase("e") || component.getText().equalsIgnoreCase("v")) {
                    String move = component.getText();
                    System.out.println(move);
                    playWordOfMarcel playWordOfMarcel = null;
                    try {
                        playWordOfMarcel = new playWordOfMarcel(accountNumber, character, move);
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
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/'MARIAN MEXICANU - HORA BUCURESTILOR' (Official - 2019).wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();

    }

}



