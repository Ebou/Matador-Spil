public abstract class Property extends Deed {
    private int baseRent;

    public Property(String name, int position, int price, int baseRent) {
        super(name, position, price);
        this.baseRent = baseRent;
    }

    public int getBaseRent() {
        return baseRent;
    }

    @Override
    public int getRent() {
        return baseRent;
    }
}

