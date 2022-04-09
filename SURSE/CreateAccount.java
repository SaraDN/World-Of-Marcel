import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class CreateAccount extends JFrame {
    private JPanel pane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField rogueField;
    private JTextField mageField;
    private JTextField countryField;
    private JTextField favGamesField;
    private JTextField warriorField;

    public CreateAccount(){
        initialize();
    }

    private void initialize() {
        Color color = new Color(204, 204, 255);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();// get screen size
        setBounds(100, 100, 450, 700);
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

        nameField = new JTextField();
        nameField.setBounds(137, 174, 201, 20);
        pane.add(nameField);
        nameField.setColumns(10);


        countryField = new JTextField();
        countryField.setBounds(137, 222, 201, 20);
        pane.add(countryField);
        countryField.setColumns(10);

        favGamesField = new JTextField();
        favGamesField.setBounds(137, 268, 201, 20);
        pane.add(favGamesField);
        favGamesField.setColumns(10);

        mageField = new JTextField();
        mageField.setBounds(137, 314, 201, 20);
        pane.add(mageField);
        mageField.setColumns(10);

        warriorField = new JTextField();
        warriorField.setBounds(137, 360, 201, 20);
        pane.add(warriorField);
        warriorField.setColumns(10);


        rogueField = new JTextField();
        rogueField.setBounds(137, 405, 201, 20);
        pane.add(rogueField);
        rogueField.setColumns(10);

        JButton btnResetButton_1 = new JButton("Cancel");
        btnResetButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                LoginSystem loginSystem=new LoginSystem();
                loginSystem.setVisible(true);
            }
        });
        btnResetButton_1.setBounds(281, 600, 117, 23);
        pane.add(btnResetButton_1);

        JLabel lblUsername = new JLabel("Email");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setBounds(38, 83, 89, 14);
        pane.add(lblUsername);


        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
        lblPassword.setBounds(38, 129, 89, 14);
        pane.add(lblPassword);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setBounds(38, 175, 89, 14);
        pane.add(lblName);

        JLabel lblCountry = new JLabel("Country");
        lblCountry.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblCountry.setHorizontalAlignment(SwingConstants.CENTER);
        lblCountry.setBounds(38, 221, 89, 14);
        pane.add(lblCountry);

        JLabel lblFavGames = new JLabel("Fav Games");
        lblFavGames.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblFavGames.setHorizontalAlignment(SwingConstants.CENTER);
        lblFavGames.setBounds(38, 267, 89, 14);
        pane.add(lblFavGames);

        JLabel lblmageName = new JLabel("Mage name");
        lblmageName.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblmageName.setHorizontalAlignment(SwingConstants.CENTER);
        lblmageName.setBounds(38, 313, 89, 14);
        pane.add(lblmageName);

        JLabel lblwarriorName = new JLabel("Warrior name");
        lblwarriorName.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblwarriorName.setHorizontalAlignment(SwingConstants.CENTER);
        lblwarriorName.setBounds(38, 359, 89, 14);
        pane.add(lblwarriorName);

        JLabel lblrogueName = new JLabel("Rogue name");
        lblrogueName.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblrogueName.setHorizontalAlignment(SwingConstants.CENTER);
        lblrogueName.setBounds(38, 405, 89, 14);
        pane.add(lblrogueName);


        JLabel lblLoginSystem = new JLabel("Create Account");
        lblLoginSystem.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblLoginSystem.setHorizontalAlignment(SwingConstants.CENTER);
        lblLoginSystem.setBounds(109, 10, 213, 29);
        pane.add(lblLoginSystem);

        JButton btnCreateAccount = new JButton("Create account");
        btnCreateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateAccount(usernameField.getText(), passwordField.getText(), mageField.getText(), rogueField.getText(), warriorField.getText(), countryField.getText(), favGamesField.getText(), nameField.getText())) {
                    createAccount(usernameField.getText(), passwordField.getText(), mageField.getText(), rogueField.getText(), warriorField.getText(), countryField.getText(), favGamesField.getText(), nameField.getText());
                    setVisible(false);
                    LoginSystem loginSystem=new LoginSystem();
                    loginSystem.setVisible(true);
                }
            }
        });
        btnCreateAccount.setBounds(137, 600, 134, 23);
        pane.add(btnCreateAccount);

        passwordField = new JPasswordField();
        passwordField.setBounds(137, 128, 201, 20);
        pane.add(passwordField);
        setVisible(true);
    }

    private boolean validateAccount(String email, String password, String mageName, String rogueName, String warriorName, String country, String fav_games, String name) {
        if (email.length() == 0 || password.length() == 0 || mageName.length() == 0 || rogueName.length() == 0 || country.length() == 0 || fav_games.length() == 0 || name.length() == 0) {
            JOptionPane.showMessageDialog(null, "Every filed is required");
            return false;
        }
        return  true;
    }

    public void createAccount(String email, String password, String mageName, String rogueName, String warriorName, String country, String fav_games, String name) {

        try {
            //Creating a JSONObject object
            JSONObject jsonObject = new JSONObject();
            //Inserting key-value pairs into the json object
            FileReader reader = new FileReader("src/main/resources/accounts.json");
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(reader);
            JSONArray array = new JSONArray();
            array.add(obj);
            JSONObject account = (JSONObject) array.get(0);
            JSONArray accountObj = (JSONArray) account.get("accounts");
            JSONArray fav_gamesArray=new JSONArray();
            fav_gamesArray.add(fav_games);
            //account.put("credentials");
            JSONObject credentials = new JSONObject();
            credentials.put("email", email );
            credentials.put("password", password);
            jsonObject.put("credentials", credentials);
            jsonObject.put("name", name);
            jsonObject.put("country", country);
            jsonObject.put("maps_completed", 0);
            jsonObject.put("favorite_games", fav_gamesArray);
            JSONArray characters = new JSONArray();
            JSONObject characterWarrior = new JSONObject();
            JSONObject characterMage = new JSONObject();
            JSONObject characterRogue = new JSONObject();
            characterMage.put("name", mageName);
            characterMage.put("profession", "Mage");
            characterMage.put("level", 0);
            characterMage.put("experience", 0);

            characterRogue.put("name", rogueName);
            characterRogue.put("profession", "Rogue");
            characterRogue.put("level", 0);
            characterRogue.put("experience", 0);

            characterWarrior.put("name", warriorName);
            characterWarrior.put("profession", "Warrior");
            characterWarrior.put("level", 0);
            characterWarrior.put("experience", 0);
            characters.add(characterMage);
            characters.add(characterRogue);
            characters.add(characterWarrior);
            jsonObject.put("characters", characters);
            accountObj.add(jsonObject);

            try {
                FileWriter file = new FileWriter("src/main/resources/accounts.json");
                file.write(account.toJSONString());
                //file.write(jsonObject.toJSONString());
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("JSON file created: "+jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
