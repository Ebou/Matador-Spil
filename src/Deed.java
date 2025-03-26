public abstract class Deed extends Field {
    private int price;
    private PlayerDeltager owner;

    public Deed(String name, int position, int price) {
        super(name, position);
        this.price = price;
        this.owner = null;
    }

    public int getPrice() {
        return price;
    }

    public PlayerDeltager getOwner() {
        return owner;
    }

    public void setOwner(PlayerDeltager owner) {
        this.owner = owner;
    }

    public boolean isOwned() {
        return owner != null;
    }

    public abstract int getRent();

    @Override
    public void landOn(PlayerDeltager player) {
        if (!isOwned()) {
            if (player.canAfford(price)) {
                purchase(player);
            }
        } else if (player != owner && !owner.isBankrupt()) {
            payRent(player);
        }
    }

    public void purchase(PlayerDeltager player) {
        if (!isOwned() && player.canAfford(price)) {
            player.subtractBalance(price);
            setOwner(player);
            player.addDeed(this);
            System.out.println(player.getName() + " purchased " + getName() + " for " + price);
        }
    }

    public void payRent(PlayerDeltager player) {
        if (isOwned() && player != owner && !owner.isBankrupt()) {
            int rent = getRent();
            if (player.canAfford(rent)) {
                player.subtractBalance(rent);
                owner.addBalance(rent);
                System.out.println(player.getName() + " paid " + rent + " in rent to " + owner.getName() + " for " + getName());
            } else {
                owner.addBalance(player.getBalance());
                System.out.println(player.getName() + " couldn't afford " + rent + " rent and paid their remaining " + player.getBalance() + " to " + owner.getName());
                player.subtractBalance(player.getBalance());
            }
        }
    }
}

