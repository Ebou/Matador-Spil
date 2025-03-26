import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private List<Field> fields;

    public GameBoard() {
        fields = new ArrayList<>();
        initializeFields();
    }

    private void initializeFields() {
        fields.add(new Start("Start", 0));
        fields.add(new Plot("Rødovrevej", 1, 1200, 50));
        fields.add(new Chance("Prøv lykken", 2));
        fields.add(new Plot("Hvidovrevej", 3, 1200, 50));
        fields.add(new Tax("Indkomstskat", 4, 4000));
        fields.add(new Shipping("Øresund", 5, 4000, 500));
        fields.add(new Plot("Roskildevej", 6, 2000, 100));
        fields.add(new Chance("Prøv lykken", 7));
        fields.add(new Plot("Valby Langgade", 8, 2000, 100));
        fields.add(new Plot("Allégade", 9, 2400, 150));
        fields.add(new Jail("Fængsel", 10));
        fields.add(new Brewery("Tuborg", 11, 3000, 100));
        fields.add(new Plot("Frederiksberg Allé", 12, 2800, 200));
        fields.add(new Shipping("D.F.D.S.", 13, 4000, 500));
        fields.add(new Plot("Gl. Kongevej", 14, 3200, 250));
        fields.add(new Parking("Parkering", 15));
        fields.add(new Plot("Trianglen", 16, 3600, 300));
        fields.add(new Chance("Prøv lykken", 17));
        fields.add(new Plot("Østerbrogade", 18, 3600, 300));
        fields.add(new Plot("Grønningen", 19, 4000, 350));
        fields.add(new Shipping("Ø.S.", 20, 4000, 500));
        fields.add(new Plot("Bredgade", 21, 4400, 350));
        fields.add(new Chance("Prøv lykken", 22));
        fields.add(new Plot("Kgs. Nytorv", 23, 4400, 350));
        fields.add(new Plot("Østergade", 24, 4800, 400));
        fields.add(new Shipping("D.F.D.S.", 25, 4000, 500));
        fields.add(new Plot("Amagertorv", 26, 5200, 450));
        fields.add(new Plot("Vimmelskaftet", 27, 5200, 450));
        fields.add(new Brewery("Carlsberg", 28, 3000, 100));
        fields.add(new Plot("Nygade", 29, 5600, 500));
        fields.add(new Jail("Gå i fængsel", 30));
        fields.add(new Plot("Frederiksberg Allé", 31, 6000, 550));
        fields.add(new Plot("Rådhuspladsen", 32, 8000, 600));
        fields.add(new Tax("Ekstraordinær statsskat", 33, 2000));
        fields.add(new Chance("Prøv lykken", 34));
        fields.add(new Plot("Strøget", 35, 7000, 550));
    }

    public Field getField(int position) {
        return fields.get(position);
    }

    public int getFieldCount() {
        return fields.size();
    }
}

