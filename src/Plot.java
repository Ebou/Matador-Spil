import java.util.ArrayList;
import java.util.List;

public class Plot extends Property {
    private List<Building> buildings;
    private int housePrice;
    private int[] rentWithHouses = {0, 0, 0, 0, 0, 0};

    public Plot(String name, int position, int price, int baseRent) {
        super(name, position, price, baseRent);
        this.buildings = new ArrayList<>();
        this.housePrice = price / 2;
        
        rentWithHouses[0] = baseRent;
        for (int i = 1; i < 6; i++) {
            rentWithHouses[i] = baseRent * (i + 1);
        }
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public int getHouseCount() {
        int count = 0;
        for (Building building : buildings) {
            if (building.getType().equals("House")) {
                count++;
            }
        }
        return count;
    }

    public boolean hasHotel() {
        for (Building building : buildings) {
            if (building.getType().equals("Hotel")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getRent() {
        if (hasHotel()) {
            return rentWithHouses[5];
        } else {
            return rentWithHouses[getHouseCount()];
        }
    }

    public int getHousePrice() {
        return housePrice;
    }

    public void buildHouse(PlayerDeltager player) {
        if (player == getOwner() && getHouseCount() < 4 && player.canAfford(housePrice)) {
            player.subtractBalance(housePrice);
            addBuilding(new Building("House", housePrice));
            System.out.println(player.getName() + " built a house on " + getName());
        }
    }

    public void buildHotel(PlayerDeltager player) {
        if (player == getOwner() && getHouseCount() == 4 && player.canAfford(housePrice)) {
            player.subtractBalance(housePrice);
            buildings.removeIf(building -> building.getType().equals("House"));
            addBuilding(new Building("Hotel", housePrice * 5));
            System.out.println(player.getName() + " built a hotel on " + getName());
        }
    }
}

