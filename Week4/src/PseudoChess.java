import java.util.Scanner;
//To-Do
//Rewrite Dwarfs ,decodeCoordinates
//no string as Input
/**
 * Game of Pseudo Chess HomeWork 4
 * @author Martin Tabakov
 */
public class PseudoChess {

    public static void main(String[] args) {

        String[][] gameBoard = new String[6][6];
        boolean gameActive = true;
        int playerId = 1;

        initBoard(gameBoard);

        Scanner scanner = new Scanner(System.in);
        Format format = new Format();
        Game game = new Game(scanner, gameBoard);

        printInitialMessage();

        while (gameActive) {

            format.drawBoard(gameBoard);
            format.drawLine();

            playerId= switchPlayer(playerId);
            announceCurrentPlayer(getPlayerColor(playerId));

            game.getPawnPositions(playerId);
            game.makeMove();

            gameActive= !game.isKingDead();
        }

        format.drawBoard(gameBoard);
        printWinMessage(getPlayerColor(playerId));
    }

    /**
     * Changes the active player every turn
     * @param playerId current active player
     * @return next active player
     */
    public static int switchPlayer(int playerId){
        if(playerId==1) return 0;
        return 1;
    }

    /**
     * Prints the Game Over message
     * @param color The color of the victorious figures
     */
    public static void printWinMessage(String color){
        System.out.println("Game Over!");
        System.out.println("The player with "+ color+" figures wins!");
    }

    /**
     * Gets the player color based on his Id
     * @param playerId the id of the player
     * @return Player color
     */
    public static String getPlayerColor(int playerId){
        if (playerId==0) return "White";
        return "Black";
    }

    /**
     * Tells who the current Player is by his Id
     * @param color The Color of active player
     */
    public  static void announceCurrentPlayer(String color){
        System.out.println("Current turn for "+color+" figures.");
    }

    /**
     * Prints welcoming message. Shows how to input data.
     */
    public static void printInitialMessage(){
        System.out.println("A game of Pseudo Chess!");
        System.out.println("Moving figure is made as following:");
        System.out.println("Type Coordinates of the figure to move and coordinates of the destination");
        System.out.println("EXAMPLE: AD-BD (first char is the roll, the second- column, use any separator in-between)");
        System.out.println("Note! Dwarfs work with destination coordinates not pointing to allied pawn.");
        System.out.println("Good Luck!");
    }

    /**
     * Places every pawn at its place with the corresponding prefix, alongside the empty spaces.
     * @param board Game board that is played on.
     */
    public static void initBoard(String[][] board) {
        String[] pawns = new String[]{"Dw", "D ", "Q ", "K ", "M ", "Dw"};
        char prefixW = 'w';
        char prefixB = 'b';

        for (int i = 0; i < 6; i++) board[0][i] = prefixW + pawns[i];
        for (int i = 5; i >= 0; i--) board[5][i] = prefixB + pawns[5 - i];

        for (int i = 1; i < 5; i++)
            for (int j = 0; j < 6; j++) board[i][j] = "   ";
    }
}

/**
 * Handles all of the game logic, data input and pawn movement with all necessary validations.
 */
class Game {
    String[][] gameBoard;
    Coordinates[][] movements;
    Scanner scanner;
    int playerId;
    Coordinates pawnPosition;
    Coordinates moveToPosition;
    String pawnName;
    String moveToPosName;
    float turn=1;
    boolean[] dwarfRightDir;

    public Game(Scanner scanner, String[][] gameBoard) {
        this.scanner = scanner;
        this.gameBoard = gameBoard;
        initMovements();
        dwarfRightDir= new boolean[]{true,true};
    }

    /**
     * Gets the User input
     * @param playerId Current active player Id. Either 0 or 1.
     */
    public void getPawnPositions(int playerId) {
        this.playerId = playerId;
        System.out.println("Current turn: "+(int)Math.floor(turn));
        boolean offBoard;
        boolean wrongPositions=true;

        while (wrongPositions){
            printTurnMessage();
            offBoard= decodeCoordinates(getCoordinates());
            if (offBoard) continue;
            wrongPositions=!areCoordinatesCorrect() ;
        }
        turn+=0.5;
    }

    /**
     * Message to tell the user to insert data.
     * Shown also after wrongly user inserted data
     */
    private void printTurnMessage() {
        System.out.print("Insert Coordinates: ");
    }

    /**
     * Assigns coordinates and names of the Starting pawn and the move-to field.
     * @param rawInput User inserted string with pawn position and move-to place.
     * @return true if any pos off board
     */
    private boolean decodeCoordinates(String rawInput) {
        Coordinates pos= separateInput(0, rawInput);
        boolean offBoard = pawnOffBoard(pos);
        if(offBoard) return true;
        this.pawnPosition = pos;
        this.pawnName = gameBoard[pawnPosition.row][pawnPosition.column];

        pos= separateInput(rawInput.length() - 2, rawInput);
        offBoard = pawnOffBoard(pos);
        if(offBoard) return true;
        this.moveToPosition = pos;
        this.moveToPosName = gameBoard[moveToPosition.row][moveToPosition.column];
        return  false;
    }

    /**
     * Separates the user inserted string with data into coordinates.
     * @param startPosition Starting position of the interval with wanted position.
     * @param rawInput The String containing positions.
     * @return Decoded Coordinates.
     */
    private Coordinates separateInput(int startPosition, String rawInput) {
        String subInput = rawInput.substring(startPosition, startPosition + 2);
        Coordinates coordinates = new Coordinates();
        coordinates.row = getNumFromChar(subInput.charAt(0));
        coordinates.column = getNumFromChar(subInput.charAt(1));
        return coordinates;
    }

    /**
     *  Decodes single character to its corresponding numeric value.
     * @param encodedPos Encoded character.
     * @return Decoded value.
     */
    private int getNumFromChar(char encodedPos) {
        return (int) encodedPos - 'A';
    }

    /**
     * @return Pawn start position and move-to position.
     */
    private String getCoordinates() {
        return scanner.nextLine().toUpperCase();
    }

    /**
     * Checks whether both the Pawn coordinates and those of the move-to position
     * @return {@code true} if all coordinates are correct, else - {@code false}
     */
    private boolean areCoordinatesCorrect() {
        return isPawnPosCorrect() && isPawnDestinationCorrect();
    }

    /**
     * Validates the position of the pawn as usable
     * @return {@code true} if the  position is correct, else - {@code false}
     */
    private boolean isPawnPosCorrect() {
        if (canPlayDonkey()) return false;
        return getPawnPrefix(pawnName) == getPlayerPrefix();
    }

    /**
     * Validates the move-to position as of correct
     * @return {@code true} if possible, else - {@code false}
     */
    private boolean isPawnDestinationCorrect() {
        return !friendlyPawnLocated() && pawnMovementCorrect();
    }
    /**
     * Validates the usage of the Donkey pawn
     * @return {@code true} if can play the Donkey pawn, else - {@code false}
     */
    private boolean canPlayDonkey(){
        return (Math.floor(turn)%3!=0 && pawnName.endsWith("D "));
    }

    /**
     * Checks whether the coordinates are outside the border limits.
     * @param pawnCoordinates The coordinates of the pawn
     * @return {@code true} if the pawns coordinates is outside the board, else - {@code false}
     */
    private boolean pawnOffBoard(Coordinates pawnCoordinates) {
        return (pawnCoordinates.column > 5 || pawnCoordinates.column < 0) ||
                (pawnCoordinates.row > 5 || pawnCoordinates.row < 0);
    }

    /**
     * Gets the pawn prefix
     * @param pawn Pawn to get its prefix
     * @return The prefix of the selected pawn
     */
    private char getPawnPrefix(String pawn) {
        return pawn.charAt(0);
    }

    /**
     * Gets the active player prefix
     * @return The prefix of the active player
     */
    private char getPlayerPrefix() {
        if (playerId == 0) return 'w';
        return 'b';
    }

    /**
     * Checks whether the move-to spot is occupied by a friendly pawn
     * @return {@code true} if the move-to spot is taken by a friendly pawn, else - {@code false}
     */
    private boolean friendlyPawnLocated() {
        return getPawnPrefix(moveToPosName) == getPlayerPrefix();
    }

    /**
     * Checks whether the move the player is trying to make is supported by the pawn.
     * @return {@code true} if the movement is valid for the pawn, else - {@code false}
     */
    private boolean pawnMovementCorrect() {
        String noPrefixName = pawnName.substring(1);
        switch (noPrefixName){
            case "Dw":
                return movementDwarf();
                //return checkMovement(0);
            case "D ": return checkMovement(1);
            case "Q ": return checkMovement(2);
            case "K ": return checkMovement(3);
            case "M ": return checkMovement(4);
        }
        return false;
    }

    private boolean movementDwarf(){
        char prefix = getPawnPrefix(pawnName);
        switch (prefix){
            case 'w': moveDwarf(0,5,0,1,-1); break;
            case 'b': moveDwarf(1,0,5,-1,1);break;
        }
        return true;
    }

    private void moveDwarf(int id,int FRoll, int SRoll,int moveOk,int moveNotOk) {

        if (pawnPosition.row==FRoll) dwarfRightDir[id]=false;
        if (pawnPosition.row==SRoll) dwarfRightDir[id]=true;

        if (dwarfRightDir[id]) moveToPosition.row=pawnPosition.row+moveOk;
        if (!dwarfRightDir[id]) moveToPosition.row=pawnPosition.row+moveNotOk;
        moveToPosition.column=pawnPosition.column;
    }
    /**
     * Cycles through the array of supported movements for a pawn.
     * Works by adding the current pawn positions with the supported positions.
     * If the result is like the move-to position then the User has inserted supported movement for the pawn.
     * @param pawnId The pawn Id, used to select the correct array of supported movements
     * @return {@code true} if whether the move is supported, else - {@code false}
     */
    private boolean checkMovement(int pawnId){
        for (int i = 0; i < movements[pawnId].length; i++) {
            if(pawnPosition.row+movements[pawnId][i].row == moveToPosition.row &&
                    pawnPosition.column+movements[pawnId][i].column == moveToPosition.column
            ) return true;
        }
        return false;
    }

    /**
     * Initialisation of all the supported moves for the pawns.
     */
    private void initMovements() {
        this.movements = new Coordinates[][]
        {
            {
                new Coordinates(1, 0), new Coordinates(-1, 0)
            },
            {
                new Coordinates(2, 2), new Coordinates(-2, 2),
                new Coordinates(2, -2), new Coordinates(-2, -2),
                new Coordinates(2, 0), new Coordinates(-2, 0),
                new Coordinates(0, 2), new Coordinates(0, -2),
            },
            {
                new Coordinates(1, 1), new Coordinates(-1, 1),
                new Coordinates(1, -1), new Coordinates(-1, -1),
            },
            {
                new Coordinates(1, 1), new Coordinates(-1, 1),
                new Coordinates(1, -1), new Coordinates(-1, -1),
                new Coordinates(1, 0), new Coordinates(-1, 0),
                new Coordinates(0, 1), new Coordinates(0, -1),

            },
            {
                new Coordinates(1, 0), new Coordinates(-1, 0),
                new Coordinates(0, 1), new Coordinates(0, -1),
            }
        };
    }

    /**
     * Rewrites the position of the pawn and placing blank spot on the position, that the pawn was moved from
     */
    public void makeMove(){
        gameBoard[moveToPosition.row][moveToPosition.column]=
        gameBoard[pawnPosition.row][pawnPosition.column];
        gameBoard[pawnPosition.row][pawnPosition.column]="   ";
    }

    /**
     * Checks whether the move-to position contains the King, therefore deciding the winner.
     * @return {@code true} if the King is dead aka Game Over, else - {@code false}
     */
    public boolean isKingDead(){
        return moveToPosName.endsWith("K ");
    }

}

/**
 * Class for drawing UI elements.
 */
class Format {

    final int width = 25;
    final char borderChar = '-';

    public void drawLine() {
        for (int i = 0; i < width; i++) System.out.print(borderChar);
        System.out.println();
    }

    /**
     * Draws the game board with the pawns.
     * @param board  Takes a matrix, to be drawn as game board.
     */
    public void drawBoard(String[][] board) {
        drawLine();
        for (int row = 0; row < board.length; row++) {
            String[] strings = board[row];
            System.out.print("|");

            for (int column = 0; column < board.length; column++) System.out.print(strings[column] + "|");
            PrintSideLetters(row);
            drawLine();
        }
        PrintBottomNLetters();
    }

    private void PrintSideLetters(int row) {
        System.out.println(" " + (char) (row + 'A'));
    }

    private void PrintBottomNLetters() {
        for (char i = 'A'; i <= 'F'; i++)
            System.out.print("  " + i + " ");
        System.out.println();
    }
}

/**
 * Class for implementing easier positioning for the game,
 * by combining the column and row values of the pawn/move-to spot
 */
class Coordinates {
    public int row;
    public int column;

    public Coordinates() {

    }

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }
}