import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileIO {

    private static final String SECTION_SEPARATOR = "====================";
    private static final String HEADER_PREFIX = "## ";
    private static final String METADATA_PREFIX = "# ";
    private static final String PLAYERS_SECTION = "PLAYERS";
    private static final String PROPERTIES_SECTION = "PROPERTIES";

    public ArrayList<String> readGameData(String path) {
        ArrayList<String> data = new ArrayList<>();

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith(METADATA_PREFIX) && !line.equals(SECTION_SEPARATOR)) {
                    if (line.startsWith(HEADER_PREFIX)) {
                        line = line.substring(HEADER_PREFIX.length());
                    }
                    data.add(line);
                }
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

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            writer.write(METADATA_PREFIX + "Game Save File\n");
            writer.write(METADATA_PREFIX + "Created: " + now.format(formatter) + "\n");
            writer.write(METADATA_PREFIX + "Version: 1.0\n");
            writer.write(SECTION_SEPARATOR + "\n\n");

            for (String line : data) {
                if (line.equals(PLAYERS_SECTION) || line.equals(PROPERTIES_SECTION)) {
                    writer.write("\n" + SECTION_SEPARATOR + "\n");
                    writer.write(HEADER_PREFIX + line + "\n");
                    writer.write(SECTION_SEPARATOR + "\n\n");
                } else {
                    writer.write(line + "\n");
                }
            }

            writer.close();
            System.out.println("Game data saved to " + path);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + path);
        }
    }

    public void saveGame(Game game, String path) {
        ArrayList<String> data = new ArrayList<>();

        data.add(PLAYERS_SECTION);
        for (PlayerDeltager player : game.getPlayers()) {
            if (!player.isBankrupt()) {
                data.add(player.getName() + "," + player.getBalance() + "," + player.getPosition());
            }
        }

        data.add(PROPERTIES_SECTION);
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
            if (line.equals(PLAYERS_SECTION)) {
                readingPlayers = true;
                readingProperties = false;
                continue;
            } else if (line.equals(PROPERTIES_SECTION)) {
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