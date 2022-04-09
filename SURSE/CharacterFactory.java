public class CharacterFactory {
    public Character getCharacter(String characterType) {
        if (characterType == null) {
            return null;
        }
        if (characterType.equalsIgnoreCase("MAGE")) {
            return new Mage();
        } else if (characterType.equalsIgnoreCase("ROGUE")) {
            return new Rogue();
        } else if (characterType.equalsIgnoreCase("WARRIOR")) {
            return new Warrior();
        }
        return null;
    }
}
