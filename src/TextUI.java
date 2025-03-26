import java.util.List;
import java.util.Scanner;

public class TextUI {
    private Scanner scanner;
    
    public TextUI() {
        scanner = new Scanner(System.in);
    }
    
    public String getUserInput(String message) {
        System.out.print(message + " ");
        return scanner.nextLine();
    }
    
    public int getUserInputInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(getUserInput(message));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    public boolean getUserInputBoolean(String message) {
        String input = getUserInput(message + " (y/n)").toLowerCase();
        return input.startsWith("y");
    }
    
    public void displayMessage(String message) {
        System.out.println(message);
    }
    
    public void displayGameBoard(GameBoard gameBoard, List<PlayerDeltager> players) {
        System.out.println("\n===== GAME BOARD =====");
        
        for (int i = 0; i < gameBoard.getFieldCount(); i++) {
            Field field = gameBoard.getField(i);
            System.out.print(field.getName());
            
            if (field instanceof Deed) {
                Deed deed = (Deed) field;
                if (deed.isOwned()) {
                    System.out.print(" (Owned by " + deed.getOwner().getName() + ")");
                } else {
                    System.out.print(" (Price: " + ((Deed) field).getPrice() + ")");
                }
            }
            
            System.out.print(" - Players: ");
            boolean playersOnField = false;
            
            for (PlayerDeltager player : players) {
                if (player.getPosition() == i && !player.isBankrupt()) {
                    System.out.print(player.getName() + " ");
                    playersOnField = true;
                }
            }
            
            if (!playersOnField) {
                System.out.print("None");
            }
            
            System.out.println();
        }
        
        System.out.println("=====================");
    }
    
    public void displayPlayerStatus(PlayerDeltager player) {
        System.out.println("\n===== " + player.getName() + " =====");
        System.out.println("Balance: " + player.getBalance());
        System.out.println("Position: " + player.getPosition());
        
        System.out.println("Properties owned:");
        if (player.getDeeds().isEmpty()) {
            System.out.println("None");
        } else {
            for (Deed deed : player.getDeeds()) {
                System.out.println("- " + deed.getName() + " (Rent: " + deed.getRent() + ")");
                
                if (deed instanceof Plot) {
                    Plot plot = (Plot) deed;
                    System.out.println("  Houses: " + plot.getHouseCount() + ", Hotel: " + (plot.hasHotel() ? "Yes" : "No"));
                }
            }
        }
        
        System.out.println("=====================");
    }
    
    public int getMenuChoice(String[] options) {
        System.out.println("\nMenu:");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        
        int choice;
        do {
            choice = getUserInputInt("Enter your choice (1-" + options.length + "):");
        } while (choice < 1 || choice > options.length);
        
        return choice;
    }
    
    public void close() {
        scanner.close();
    }
}

