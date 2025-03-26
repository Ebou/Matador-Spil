import java.util.ArrayList;
import java.util.List;

public class MatadorGame {
    private Game game;
    private TextUI ui;
    private FileIO fileIO;

    public MatadorGame() {
        this.game = new Game();
        this.ui = new TextUI();
        this.fileIO = new FileIO();
    }
    
    public void startNewGame() {
        int playerCount = ui.getUserInputInt("Enter number of players (2-6):");
        while (playerCount < 2 || playerCount > 6) {
            playerCount = ui.getUserInputInt("Please enter a number between 2 and 6:");
        }
        
        for (int i = 1; i <= playerCount; i++) {
            String name = ui.getUserInput("Enter name for Player " + i + ":");
            game.addPlayer(new PlayerDeltager(name, 30000));
        }
        
        runGameLoop();
    }
    
    public void loadGame() {
        String path = ui.getUserInput("Enter save file path:");
        game = fileIO.loadGame(path);
        
        if (game.getPlayers().isEmpty()) {
            ui.displayMessage("Failed to load game or no players found. Starting new game.");
            startNewGame();
        } else {
            ui.displayMessage("Game loaded successfully!");
            runGameLoop();
        }
    }
    
    private void runGameLoop() {
        while (!game.isGameOver()) {
            PlayerDeltager currentPlayer = game.getCurrentPlayer();
            
            if (currentPlayer.isInJail()) {
                handleJailTurn(currentPlayer);
            } else {
                handleNormalTurn(currentPlayer);
            }
            
            if (!game.isGameOver()) {
                game.nextTurn();
            }
        }
    }
    
    private void handleJailTurn(PlayerDeltager player) {
        ui.displayMessage("\n" + player.getName() + "'s turn (IN JAIL)");
        ui.displayPlayerStatus(player);
        
        String[] options = {"Pay to get out", "Try to roll doubles", "Use get out of jail card"};
        int choice = ui.getMenuChoice(options);
        
        switch (choice) {
            case 1:
                if (player.canAfford(1000)) {
                    player.subtractBalance(1000);
                    player.setInJail(false);
                    ui.displayMessage(player.getName() + " paid 1000 to get out of jail.");
                    handleNormalTurn(player);
                } else {
                    // what happens when stuck in jail? later on mby bankrupt the player
                    ui.displayMessage("Not enough money to pay the jail fee!");
                }
                break;
            case 2:
                int roll1 = game.rollDice();
                int roll2 = game.rollDice();
                ui.displayMessage(player.getName() + " rolled " + roll1 + " and " + roll2);
                
                if (roll1 == roll2) {
                    player.setInJail(false);
                    ui.displayMessage(player.getName() + " rolled doubles and got out of jail!");
                    game.movePlayer(player, roll1 + roll2);
                } else {
                    ui.displayMessage(player.getName() + " failed to roll doubles and stays in jail.");
                }
                break;
            case 3:
                player.setInJail(false);
                ui.displayMessage(player.getName() + " force used a breakfree from jail card and got out of jail!");
                handleNormalTurn(player);
                break;
        }
    }
    
    private void handleNormalTurn(PlayerDeltager player) {
        ui.displayMessage("\n" + player.getName() + "'s turn");
        ui.displayPlayerStatus(player);
        
        String[] options = {"Roll dice", "Build house/hotel", "Save game", "Quit game"};
        int choice = ui.getMenuChoice(options);
        
        switch (choice) {
            case 1:
                int steps = game.rollDice();
                ui.displayMessage(player.getName() + " rolled a " + steps);
                game.movePlayer(player, steps);
                break;
            case 2:
                handleBuildingConstruction(player);
                break;
            case 3:
                String path = ui.getUserInput("Enter save file path:");
                fileIO.saveGame(game, path);
                break;
            case 4:
                if (ui.getUserInputBoolean("Are you sure you want to quit?")) {
                    game.endGame();
                }
                break;
        }
    }
    
    private void handleBuildingConstruction(PlayerDeltager player) {
        List<Plot> buildablePlots = new ArrayList<>();
        
        for (Deed deed : player.getDeeds()) {
            if (deed instanceof Plot) {
                buildablePlots.add((Plot) deed);
            }
        }
        
        if (buildablePlots.isEmpty()) {
            ui.displayMessage("You don't own any properties where you can build!");
            return;
        }
        
        ui.displayMessage("Your buildable properties:");
        for (int i = 0; i < buildablePlots.size(); i++) {
            Plot plot = buildablePlots.get(i);
            ui.displayMessage((i + 1) + ". " + plot.getName() + 
                             " (Houses: " + plot.getHouseCount() + 
                             ", Hotel: " + (plot.hasHotel() ? "Yes" : "No") + 
                             ", House price: " + plot.getHousePrice() + ")");
        }
        
        int plotIndex = ui.getUserInputInt("Select property (0 to cancel):") - 1;
        if (plotIndex >= 0 && plotIndex < buildablePlots.size()) {
            Plot selectedPlot = buildablePlots.get(plotIndex);
            
            if (selectedPlot.getHouseCount() < 4 && !selectedPlot.hasHotel()) {
                if (ui.getUserInputBoolean("Build a house for " + selectedPlot.getHousePrice() + "?")) {
                    selectedPlot.buildHouse(player);
                }
            } else if (selectedPlot.getHouseCount() == 4 && !selectedPlot.hasHotel()) {
                if (ui.getUserInputBoolean("Build a hotel for " + selectedPlot.getHousePrice() + "?")) {
                    selectedPlot.buildHotel(player);
                }
            } else {
                ui.displayMessage("This property already has a hotel!");
            }
        }
    }
    
    public static void main(String[] args) {
        MatadorGame matadorGame = new MatadorGame();
        
        TextUI ui = new TextUI();
        String[] options = {"New Game", "Load Game", "Exit"};
        int choice = ui.getMenuChoice(options);
        
        switch (choice) {
            case 1:
                matadorGame.startNewGame();
                break;
            case 2:
                matadorGame.loadGame();
                break;
            case 3:
                ui.displayMessage("Goodbye!");
                break;
        }
        
        ui.close();
    }
}

