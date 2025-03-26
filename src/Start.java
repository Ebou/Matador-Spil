public class Start extends Field {
    private static final int PASSING_REWARD = 4000;

    public Start(String name, int position) {
        super(name, position);
    }

    @Override
    public void landOn(PlayerDeltager player) {
        player.addBalance(PASSING_REWARD);
        System.out.println(player.getName() + " landed on Start and received " + PASSING_REWARD);
    }

    public void passByStart(PlayerDeltager player) {
        player.addBalance(PASSING_REWARD);
        System.out.println(player.getName() + " passed Start and received " + PASSING_REWARD);
    }
}

