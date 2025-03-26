public class Parking extends Field {
    public Parking(String name, int position) {
        super(name, position);
    }

    @Override
    public void landOn(PlayerDeltager player) {
        System.out.println(player.getName() + " is parking for free");
    }
}

