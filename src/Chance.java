public class Chance extends Field {
    public Chance(String name, int position) {
        super(name, position);
    }

    @Override
    public void landOn(PlayerDeltager player) {
        System.out.println(player.getName() + " landed on Chance");
    }
}

