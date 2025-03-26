public class Jail extends Field {
    private static final int JAIL_FEE = 1000;

    public Jail(String name, int position) {
        super(name, position);
    }

    @Override
    public void landOn(PlayerDeltager player) {
        System.out.println(player.getName() + " is visiting jail");
    }

    public void imprison(PlayerDeltager player) {
        player.setInJail(true);
        System.out.println(player.getName() + " has been imprisoned");
    }

    public void release(PlayerDeltager player) {
        player.setInJail(false);
        player.subtractBalance(JAIL_FEE);
        System.out.println(player.getName() + " has been released from jail and paid " + JAIL_FEE);
    }
}

