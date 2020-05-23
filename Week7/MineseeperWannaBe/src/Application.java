
public class Application {
    public static void main(String[] args) {
        Game game = new Game();
        if (game.isDataIncorrect()) return;
        game.createMineField();
        while (!game.isDead()) {
            game.printMineField();
            game.printInventory();
            game.startMenu();
            if(game.isAtFinish()){
                System.out.print("Печелиш!");
                return;
            }
        }
        System.out.println("Git gud N00b! You loose!");
    }
}
