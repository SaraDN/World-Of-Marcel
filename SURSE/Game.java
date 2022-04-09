import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Game {
    ArrayList<JSONObject> accountList = new ArrayList<>();
    List < String > story = new ArrayList<>();
    HashMap< String,  ArrayList<String> > storyHashMap = new HashMap<>();
    private static Game instance;
    Game() {
    }
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game(); // NOT THREAD-SAFE!
        }
        return instance;
    }

    public int verifyLogin(String email, String password){

        for(int i=0; i<this.accountList.size(); i++){
            JSONObject credentials = (JSONObject) this.accountList.get(i).get("credentials");
            String email1=(String) credentials.get("email");
            String password1=(String) credentials.get("password");
            if(email.equals(email1) && password.equals(password1))
                return i;
        }
        return -1;
    }

    public void parseStoryObject(JSONObject story) {
        JSONArray storyObject = (JSONArray) story.get("stories");
        int i=0;
        while(i<storyObject.size()-1) {
            JSONObject storyObject1=(JSONObject) storyObject.get(i);
            JSONObject storyObject2=(JSONObject) storyObject.get(i+1);
            ArrayList<String> captions = new ArrayList<>();
            while (storyObject1.get("type").equals(storyObject2.get("type")) && i<storyObject.size()-1) {
                i++;
                captions.add(storyObject1.get("value").toString());
                storyObject1 = storyObject2;
                storyObject2 = (JSONObject) storyObject.get(i);
            }
            i++;
            storyHashMap.put(storyObject1.get("type").toString(), captions);
        }

    }

    public void parseAccountObject(JSONObject account) {
        JSONArray accountObject = (JSONArray) account.get("accounts");
        for (int i = 0; i < accountObject.size(); i++) {
            JSONObject aobj = (JSONObject) accountObject.get(i);
            accountList.add(aobj);
        }
    }



    public void run() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the type of the game: (type 1 or 2)");
        System.out.println("1.Terminal");
        System.out.println("2.Board Game");
        int type=sc.nextInt();


        try (
                FileReader reader = new FileReader("src/main/resources/accounts.json")) {
            //Read JSON file
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONArray array = new JSONArray();
            array.add(obj);
            parseAccountObject((JSONObject) array.get(0));

        } catch (ParseException | IOException f) {
            f.printStackTrace();
        }

        try (
                FileReader reader = new FileReader("src/main/resources/stories.json")) {
            //Read JSON file
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONArray array = new JSONArray();
            array.add(obj);
            parseStoryObject((JSONObject) array.get(0));

        } catch (ParseException | IOException f) {
            f.printStackTrace();
        }

        if(type==1){
            PlayInTerminal playInTerminal= new PlayInTerminal();
            playInTerminal.play();
        }
        if(type==2){
            LoginSystem loginSystem = new LoginSystem();
            loginSystem.setVisible(true);
        }
    }

}
