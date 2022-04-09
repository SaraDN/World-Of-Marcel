import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -5007909147223201510L;
    private JPanel pane;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginSystem() {
       // setIconImage(Toolkit.getDefaultToolkit().getImage(LoginSystem.class.getResource("/loginSystem/icon.jpg")));
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */

    private void initialize() {
        Color color = new Color(204, 204, 255);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();// get screen size
        setBounds(100, 100, 450, 300);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2); // center the
        // frame

        pane = new JPanel();
        pane.setBackground(color);
        pane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(pane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane.setLayout(null);

        usernameField = new JTextField();
        usernameField.setBounds(137, 81, 201, 20);
        pane.add(usernameField);
        usernameField.setColumns(10);

        JButton btnLoginButton = new JButton("Login");
        btnLoginButton.addActionListener(new ActionListener() {
           // @SuppressWarnings("deprecation")
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateAccount(usernameField.getText(), new String(passwordField.getText()))) {
                    int accountNumber = Game.getInstance().verifyLogin(usernameField.getText(), new String(passwordField.getText()));
                    if(accountNumber>-1){
                        createAccount(Game.getInstance().accountList, accountNumber);
                        ChooseCharacter chooseCharacter = null;
                        try {
                            chooseCharacter = new ChooseCharacter(accountNumber);
                        } catch (UnsupportedAudioFileException ex) {
                            ex.printStackTrace();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        setVisible(false);
                        chooseCharacter.setVisible(true);
                    }else JOptionPane.showMessageDialog(null, "Username/Password not found");
                }
            }
        });
        btnLoginButton.setBounds(23, 192, 104, 23);
        pane.add(btnLoginButton);

        JButton btnResetButton_1 = new JButton("Cancel");
        btnResetButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnResetButton_1.setBounds(281, 192, 117, 23);
        pane.add(btnResetButton_1);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setBounds(38, 83, 89, 14);
        pane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setBounds(38, 129, 89, 14);
        pane.add(lblPassword);

        JLabel lblLoginSystem = new JLabel("Login System");
        lblLoginSystem.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblLoginSystem.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginSystem.setBounds(109, 11, 213, 29);
        pane.add(lblLoginSystem);

        JButton btnCreateAccount = new JButton("Create account");
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CreateAccount().setVisible(true);
                setVisible(false);
            }
        });
        btnCreateAccount.setBounds(137, 192, 134, 23);
        pane.add(btnCreateAccount);

        passwordField = new JPasswordField();
        passwordField.setBounds(137, 127, 201, 20);
        pane.add(passwordField);
    }

    private boolean validateAccount(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            JOptionPane.showMessageDialog(null, "Username/Password required");
            return false;
        }
        return true;
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


