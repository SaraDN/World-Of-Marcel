import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class EndPage extends JFrame {
    Character character;
    int accountNumber;
    public EndPage(Character character, int accountNumber){
        this.accountNumber=accountNumber;
        this.character=character;
        initialize();

    }
    public void initialize() {
        Color color = new Color(204, 204, 255);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();// get screen size
        setBounds(100, 100, 300, 200);
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        JPanel End = new JPanel(null);
        JLabel gameTitle = new JLabel("World of Marcel");
        gameTitle.setFont(new Font("Tahoma", Font.PLAIN, 29));
        gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel charaterLife = new JLabel(String.valueOf("Life: " + character.currentLife));
        charaterLife.setFont(new Font("Tahoma", Font.PLAIN, 19));
        JLabel characterCoins = new JLabel(String.valueOf("Coins: " + character.inventory.coins));
        characterCoins.setFont(new Font("Tahoma", Font.PLAIN, 19));
        JLabel characterMana = new JLabel(String.valueOf("Mana: " + character.currentMana));
        characterMana.setFont(new Font("Tahoma", Font.PLAIN, 19));
        JLabel characterLevel = new JLabel(String.valueOf("Level: " + character.currentLevel+1));
        characterLevel.setFont(new Font("Tahoma", Font.PLAIN, 19));
        BoxLayout boxlayout = new BoxLayout(End, BoxLayout.Y_AXIS);
        JLabel enemysKilled = new JLabel("Enemys killed: "+WorldOfMarcel.killedEnemies);
        JLabel congrats = new JLabel("Good job, "+Game.getInstance().accountList.get(accountNumber).get("name")+"!");
        congrats.setHorizontalAlignment(SwingConstants.CENTER);
        End.add(congrats);
        End.setLayout(boxlayout);
        End.add(characterCoins);
        End.add(characterLevel);
        End.add(charaterLife);
        congrats.setFont(new Font("Tahoma", Font.PLAIN, 22));
        congrats.setBounds(100, 100, 40, 80);
        enemysKilled.setFont(new Font("Tahoma", Font.PLAIN, 19));
        End.add(enemysKilled);
        End.setBackground(color);
        setContentPane(End);

    }
}
