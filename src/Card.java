public abstract class Card {
    private String description;

    public Card(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract void applyEffect(PlayerDeltager player);
}

