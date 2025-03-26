import java.util.Random;

public class Dice {
    private Random random;
    private int faceValue;

    public Dice() {
        random = new Random();
        faceValue = 1;
    }

    public int roll() {
        faceValue = random.nextInt(6) + 1;
        return faceValue;
    }

    public int getFaceValue() {
        return faceValue;
    }
}

