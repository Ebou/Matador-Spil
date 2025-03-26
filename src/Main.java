public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        
        game.addPlayer(new PlayerDeltager("Andreas", 30000));
        game.addPlayer(new PlayerDeltager("Jonas", 30000));
        game.addPlayer(new PlayerDeltager("Ebou", 30000));

        while (!game.isGameOver()) {
            PlayerDeltager currentPlayer = game.getCurrentPlayer();
            System.out.println("\n" + currentPlayer.getName() + "'s turn");
            System.out.println("Balance: " + currentPlayer.getBalance());
            
            int steps = game.rollDice();
            System.out.println(currentPlayer.getName() + " rolled a " + steps);
            game.movePlayer(currentPlayer, steps);
            
            if (!game.isGameOver()) {
                game.nextTurn();
            }
        }
    }
}

