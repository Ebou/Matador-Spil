import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game {
    private GameBoard gameBoard;
    private List<PlayerDeltager> players;
    private Dice dice;
    private int currentPlayerIndex;
    private boolean gameOver;

    public Game() {
        this.gameBoard = new GameBoard();
        this.players = new ArrayList<>();
        this.dice = new Dice();
        this.currentPlayerIndex = 0;
        this.gameOver = false;
    }

    public void addPlayer(PlayerDeltager player) {
        players.add(player);
    }

    public void nextTurn() {
        if (players.size() <= 1) {
            endGame();
            return;
        }
        
        do {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } while (players.get(currentPlayerIndex).isBankrupt());
    }

    public PlayerDeltager getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int rollDice() {
        return dice.roll();
    }

    public void movePlayer(PlayerDeltager player, int steps) {
        if (player.isBankrupt()) {
            return;
        }
        
        int currentPosition = player.getPosition();
        int newPosition = (currentPosition + steps) % gameBoard.getFieldCount();
        player.setPosition(newPosition);
        
        Field field = gameBoard.getField(newPosition);
        field.landOn(player);
        
        checkBankruptcy();
    }
    
    public void checkBankruptcy() {
        Iterator<PlayerDeltager> iterator = players.iterator();
        int activePlayers = 0;
        
        while (iterator.hasNext()) {
            PlayerDeltager player = iterator.next();
            if (!player.isBankrupt()) {
                activePlayers++;
            }
        }
        
        if (activePlayers <= 1) {
            endGame();
        }
    }
    
    public void endGame() {
        gameOver = true;
        
        System.out.println("\n===== GAME OVER =====");
        
        PlayerDeltager winner = null;
        int highestWorth = -1;
        
        for (PlayerDeltager player : players) {
            if (!player.isBankrupt()) {
                int netWorth = player.calculateNetWorth();
                System.out.println(player.getName() + " - Net worth: " + netWorth);
                
                if (netWorth > highestWorth) {
                    highestWorth = netWorth;
                    winner = player;
                }
            }
        }
        
        if (winner != null) {
            System.out.println("\nThe winner is " + winner.getName() + " with a net worth of " + highestWorth + "!");
        } else {
            System.out.println("\nAll players went bankrupt. No winner!");
        }
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public List<PlayerDeltager> getPlayers() {
        return players;
    }

    public Dice getDice() {
        return dice;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
}

