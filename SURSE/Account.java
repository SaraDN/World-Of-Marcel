import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Account {
    Information information;
    ArrayList<Character> characterList = new ArrayList();
    int numberOfGamesPlayed;
}

class Information {
    private final Credentials credentials;
    private final Collection<String> favourite_games;
    private final String name;
    private final String country;


    public Information(Credentials credentials, ArrayList<String> favouriteGames, String name, String country) {

        this.credentials = credentials;
        this.favourite_games = favouriteGames;
        this.name = name;
        this.country = country;
    }

    public static class InformationBuilder {
        private Credentials credentials;
        private ArrayList<String> favouriteGames;
        private String name;
        private String country;

        public InformationBuilder setCredentials(Credentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public InformationBuilder setFavouriteGames(ArrayList<String> favourite_games) {
            this.favouriteGames = favourite_games;
            //Collections.sort(favourite_games);
            return this;
        }

        public InformationBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public InformationBuilder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Information build() {
            return new Information(credentials, favouriteGames, name, country);
        }
    }
}


