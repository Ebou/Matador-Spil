import java.util.ArrayList;
import java.util.List;

public class PlayerDeltager {
    private String name;
    private int balance;
    private int position;
    private List<Card> cards;
    private List<Deed> deeds;
    private boolean inJail;
    private boolean bankrupt;

    public PlayerDeltager(String name, int initialBalance) {
        this.name = name;
        this.balance = initialBalance;
        this.position = 0;
        this.cards = new ArrayList<>();
        this.deeds = new ArrayList<>();
        this.inJail = false;
        this.bankrupt = false;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int amount) {
        this.balance += amount;
    }

    public void subtractBalance(int amount) {
        this.balance -= amount;
        if (balance < 0) {
            goBankrupt();
        }
    }

    public boolean canAfford(int amount) {
        return balance >= amount;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addDeed(Deed deed) {
        deeds.add(deed);
    }

    public List<Deed> getDeeds() {
        return deeds;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }
    
    public boolean isBankrupt() {
        return bankrupt;
    }

    public void goBankrupt() {
        this.bankrupt = true;
        this.name = this.name + " (OUT OF GAME)";
        System.out.println("GAME OVER: " + name + " has gone bankrupt and is out of the game!");

        for (Deed deed : new ArrayList<>(deeds)) {
            deed.setOwner(null);

        }
        deeds.clear();
    }
    
    public int calculateNetWorth() {
        int worth = balance;
        for (Deed deed : deeds) {
            worth += deed.getPrice();
            if (deed instanceof Plot) {
                Plot plot = (Plot) deed;
                worth += plot.getHouseCount() * plot.getHousePrice();
                if (plot.hasHotel()) {
                    worth += plot.getHousePrice() * 5;
                }
            }
        }
        return worth;
    }
}

