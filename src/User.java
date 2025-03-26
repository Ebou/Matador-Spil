public class User {
    private String name;
    private Dice dice;
    private Card currentCard;

    public User(String name) {
        this.name = name;
        this.dice = new Dice();
    }

    public String getName() {
        return name;
    }

    public int rollDice() {
        return dice.roll();
    }

    public void drawCard(Card card) {
        this.currentCard = card;
    }

    public Card getCurrentCard() {
        return currentCard;
    }
}

