public class Tax extends Field {
    private int taxAmount;

    public Tax(String name, int position, int taxAmount) {
        super(name, position);
        this.taxAmount = taxAmount;
    }

    @Override
    public void landOn(PlayerDeltager player) {
        player.subtractBalance(taxAmount);
        System.out.println(player.getName() + " paid " + taxAmount + " in tax");
    }

    public int getTaxAmount() {
        return taxAmount;
    }
}

