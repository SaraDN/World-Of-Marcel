import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChooseCharacter extends JFrame {
    private JPanel pane;
    private JCheckBox mageCheckBox;
    private JCheckBox warriorCheckBox;
    private JCheckBox rogueCheckBox;
    private JTextField chooseYourWarriorTextField;
    private JButton letSBeginTheButton;

//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    ChooseCharacter frame = new ChooseCharacter();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }


    public ChooseCharacter(int accountNumber) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // setIconImage(Toolkit.getDefaultToolkit().getImage(LoginSystem.class.getResource("/loginSystem/icon.jpg")));
        initialize(accountNumber);

    }
    public void initialize(int accountNumber) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();// get screen size
        setBounds(100, 100, 650, 500);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2); // center the
        // frame
        Color color = new Color(204, 204, 255);
        pane = new JPanel();
        pane.setBackground(color);
        pane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(pane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.setLayout(null);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/'MARIAN MEXICANU - HORA BUCURESTILOR' (Official - 2019).wav"));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        mageCheckBox = new JCheckBox("Mage " + addProperties(Game.getInstance().accountList, accountNumber, "Mage"));
        mageCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
        mageCheckBox.setBounds(30, 130, 624, 23);
        warriorCheckBox = new JCheckBox("Warrior "+addProperties(Game.getInstance().accountList, accountNumber, "Warrior"));
        warriorCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
        warriorCheckBox.setBounds(30, 180, 624, 23 );
        rogueCheckBox = new JCheckBox("Rogue "+addProperties(Game.getInstance().accountList, accountNumber, "Rogue"));
        rogueCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
        rogueCheckBox.setBounds(30, 230, 624, 23);
        letSBeginTheButton = new JButton("Let's begin!");
        letSBeginTheButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        letSBeginTheButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int check=0;
                int option=0;
                if(warriorCheckBox.isSelected())
                    check++;
                if(mageCheckBox.isSelected()) {
                    option=1;
                    check++;
                }
                if(rogueCheckBox.isSelected()) {
                    option=2;
                    check++;
                }
                if(check==0)
                    JOptionPane.showMessageDialog(null, "You must choose one!");
                else if(check>1){
                    JOptionPane.showMessageDialog(null, "You can only choose one!");
                }
                else{
                    setVisible(false);
                    WorldOfMarcel worldOfMarcel= null;
                    try {
                        worldOfMarcel = new WorldOfMarcel(accountNumber, selectCharacter(Game.getInstance().accountList, accountNumber, option));
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                    assert worldOfMarcel != null;
                    worldOfMarcel.setVisible(true);
                    try {
                        worldOfMarcel.initialize();
                    } catch (LineUnavailableException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (UnsupportedAudioFileException ex) {
                        ex.printStackTrace();
                    }
                    clip.stop();
                }
            }
        });
        letSBeginTheButton.setBounds(150, 392, 324, 23);
        pane.add(mageCheckBox);
        pane.add(warriorCheckBox);
        pane.add(rogueCheckBox);
        pane.add(letSBeginTheButton);

        JLabel lblLoginSystem = new JLabel("Choose your character");
        lblLoginSystem.setFont(new Font("Tahoma", Font.PLAIN, 27));
        lblLoginSystem.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginSystem.setBounds(160, 41, 313, 29);
        pane.add(lblLoginSystem);
    }

    private String addProperties(ArrayList<JSONObject> accountList, int accountNumber, String type){
        JSONArray charactersLIst = (JSONArray) accountList.get(accountNumber).get("characters");
        for (int i = 0; i < charactersLIst.size(); i++) {
            JSONObject characterProps = (JSONObject) charactersLIst.get(i);
            if(type.equals(characterProps.get("profession").toString()))
                return characterProps.toString();
        }
        return null;
    }
    private Character selectCharacter(ArrayList<JSONObject> accountList, int accountNumber, int option) {
        JSONArray charactersLIst = (JSONArray) accountList.get(accountNumber).get("characters");
        JSONObject characterProps = (JSONObject) charactersLIst.get(option);
        Character character = new Character() {

        };
        character.currentExperience = Integer.parseInt(characterProps.get("experience").toString());
        character.currentLevel = Integer.parseInt(characterProps.get("level").toString());
        character.currentLife = 100;
        character.currentMana = 75 / (option + 1);
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


        JOptionPane.showMessageDialog(null, character.name + " was chosen. Let's begin the adventure!");

        return character;

    }

}
