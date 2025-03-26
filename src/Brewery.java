public class Brewery extends Company {
    public Brewery(String name, int position, int price, int baseRent) {
        super(name, position, price, baseRent);
    }

    @Override
    public int getRent() {
        int breweryCount = 0;
        
        if (getOwner() != null) {
            for (Deed deed : getOwner().getDeeds()) {
                if (deed instanceof Brewery) {
                    breweryCount++;
                }
            }
        }
        
        return getBaseRent() * breweryCount;
    }
}

