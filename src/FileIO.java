import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {
    
    public ArrayList<String> readGameData(String path) {
        ArrayList<String> data = new ArrayList<>();
        
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
        }
        
        return data;
    }
    
    public void writeGameData(String path, ArrayList<String> data) {
        try {
            FileWriter writer = new FileWriter(path);
            
            for (String line : data) {
                writer.write(line + "\n");
            }
            
            writer.close();
            System.out.println("Game data saved to " + path);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + path);
        }
    }
    
    public void saveGame(Game game, String path) {
        ArrayList<String> data = new ArrayList<>();
        
        data.add("PLAYERS");
        for (PlayerDeltager player : game.getPlayers()) {
            if (!player.isBankrupt()) {
                data.add(player.getName() + "," + player.getBalance() + "," + player.getPosition());
            }
        }
        
        data.add("PROPERTIES");
        for (int i = 0; i < game.getGameBoard().getFieldCount(); i++) {
            Field field = game.getGameBoard().getField(i);
            if (field instanceof Deed) {
                Deed deed = (Deed) field;
                if (deed.isOwned()) {
                    data.add(i + "," + deed.getOwner().getName());
                }
            }
        }
        
        writeGameData(path, data);
    }
    
    public Game loadGame(String path) {
        ArrayList<String> data = readGameData(path);
        Game game = new Game();
        
        if (data.isEmpty()) {
            return game;
        }
        
        boolean readingPlayers = false;
        boolean readingProperties = false;
        
        for (String line : data) {
            if (line.equals("PLAYERS")) {
                readingPlayers = true;
                readingProperties = false;
                continue;
            } else if (line.equals("PROPERTIES")) {
                readingPlayers = false;
                readingProperties = true;
                continue;
            }
            
            if (readingPlayers) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int balance = Integer.parseInt(parts[1]);
                    int position = Integer.parseInt(parts[2]);
                    
                    PlayerDeltager player = new PlayerDeltager(name, balance);
                    player.setPosition(position);
                    game.addPlayer(player);
                }
            } else if (readingProperties) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int fieldIndex = Integer.parseInt(parts[0]);
                    String ownerName = parts[1];
                    
                    Field field = game.getGameBoard().getField(fieldIndex);
                    if (field instanceof Deed) {
                        Deed deed = (Deed) field;
                        
                        for (PlayerDeltager player : game.getPlayers()) {
                            if (player.getName().equals(ownerName)) {
                                deed.setOwner(player);
                                player.addDeed(deed);
                                break;
                            }
                        }
                    }
                }
            }
        }
        
        return game;
    }
}

