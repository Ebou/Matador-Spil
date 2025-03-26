public class Shipping extends Company {
    public Shipping(String name, int position, int price, int baseRent) {
        super(name, position, price, baseRent);
    }

    @Override
    public int getRent() {
        int shippingCount = 0;
        
        if (getOwner() != null) {
            for (Deed deed : getOwner().getDeeds()) {
                if (deed instanceof Shipping) {
                    shippingCount++;
                }
            }
        }
        
        return getBaseRent() * (int)Math.pow(2, shippingCount - 1);
    }
}

