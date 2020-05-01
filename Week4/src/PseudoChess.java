import java.util.Scanner;

public class PseudoChess {

    public static void main(String[] args) {
        String[][] gameBoard = new String[6][6];
        boolean toRun = true;
        int playerId = 0;

        initBoard(gameBoard);

        Scanner scanner = new Scanner(System.in);
        Format format = new Format();
        Move move = new Move(scanner, gameBoard);

        while (toRun) {
            format.drawBoard(gameBoard);
            move.getPawnPositions(playerId);
            move.makeMove();
            toRun= !move.isKingDead();
        }
    }

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

class Move {
    String[][] gameBoard;
    Scanner scanner;
    int playerId;
    Coordinates pawnPosition;
    Coordinates newPawnPosition;
    String pawnName;
    String newPawnName;

    public Move(Scanner scanner, String[][] gameBoard) {
        this.scanner = scanner;
        this.gameBoard = gameBoard;
        initMovements();
    }

    public void getPawnPositions(int playerId) {
        this.playerId = playerId;

        do {
            printTurnMessage();
            decodeCoordinates(getCoordinates());

        } while (!areCoordinatesCorrect());

    }

    private String getCoordinates() {
        return scanner.next();
    }

    private boolean areCoordinatesCorrect() {
        return isFirstPawnCorrect() && isPawnDestinationCorrect();
    }

    private boolean isFirstPawnCorrect() {
        if (pawnOffBoard(pawnPosition)) return false;
        return getPawnPrefix(pawnName) == getPlayerPrefix();
    }

    private boolean pawnOffBoard(Coordinates pawnCoordinates) {
        return (pawnCoordinates.column > 5 || pawnCoordinates.column < 0) ||
                (pawnCoordinates.row > 5 || pawnCoordinates.row < 0);
    }

    private char getPawnPrefix(String pawn) {
        return pawn.charAt(0);
    }

    private char getPlayerPrefix() {
        if (playerId == 0) return 'w';
        return 'b';
    }

    private boolean isPawnDestinationCorrect() {
        return !pawnOffBoard(newPawnPosition) && (isLocationClear() || isEnemyLocated()) && pawnMovementCorrect();
    }

    private boolean isLocationClear() {
        return newPawnName.equals("   ");
    }

    private boolean isEnemyLocated() {
        return getPawnPrefix(newPawnName) != getPlayerPrefix();
    }

    private boolean pawnMovementCorrect() {
        String noPrefixName = pawnName.substring(1);
        switch (noPrefixName){
            case "Dw": return movementDwarf();
            case "D ": return movementDonkey();
            case "Q ": return movementQueen();
            case "K ": return movementKing();
            case "M": return movementMachineGun();
        }
        return false;
    }

    private boolean movementDwarf() {
        for (int i = 0; i < movements[0].length; i++) {
            if(pawnPosition.row+movements[0][i].row == newPawnPosition.row &&
               pawnPosition.column+movements[0][i].column == newPawnPosition.column
            ) return true;
        }
        return false;
    }
    private boolean movementDonkey() {
        for (int i = 0; i < movements[1].length; i++) {
            if(pawnPosition.row+movements[1][i].row == newPawnPosition.row &&
                    pawnPosition.column+movements[1][i].column == newPawnPosition.column
            ) return true;
        }
        return false;
    }
    private boolean movementQueen() {
        for (int i = 0; i < movements[2].length; i++) {
            if(pawnPosition.row+movements[2][i].row == newPawnPosition.row &&
                    pawnPosition.column+movements[2][i].column == newPawnPosition.column
            ) return true;
        }
        return false;
    }
    private boolean movementKing() {
        for (int i = 0; i < movements[3].length; i++) {
            if(pawnPosition.row+movements[3][i].row == newPawnPosition.row &&
                    pawnPosition.column+movements[3][i].column == newPawnPosition.column
            ) return true;
        }
        return false;
    }
    private boolean movementMachineGun() {
        for (int i = 0; i < movements[4].length; i++) {
            if(pawnPosition.row+movements[4][i].row == newPawnPosition.row &&
                    pawnPosition.column+movements[4][i].column == newPawnPosition.column
            ) return true;
        }
        return false;
    }

    private void decodeCoordinates(String rawInput) {
        //Possible ArrayIndexOutOfBound
        this.pawnPosition = separateInput(0, rawInput);
        this.pawnName = gameBoard[pawnPosition.row][pawnPosition.column];
        this.newPawnPosition = separateInput(2, rawInput);
        this.newPawnName = gameBoard[newPawnPosition.row][newPawnPosition.column];
    }

    private Coordinates separateInput(int startPosition, String rawInput) {
        String subInput = rawInput.substring(startPosition, startPosition + 2);
        Coordinates coordinates = new Coordinates();
        coordinates.row = getNumFromChar(subInput.charAt(0));
        coordinates.column = getNumFromChar(subInput.charAt(1));
        return coordinates;
    }

    private int getNumFromChar(char encodedPos) {
        return (int) encodedPos - 'A';
    }

    private void printTurnMessage() {
        System.out.println("Insert Coordinates:");
    }

    Coordinates[][] movements;

    private void initMovements() {
        this.movements = new Coordinates[][]
                {
                        {new Coordinates(1, 0), new Coordinates(-1, 0)},
                        {
                                new Coordinates(2, 2), new Coordinates(-2, 2),
                                new Coordinates(2, -2), new Coordinates(-2, -2),
                                new Coordinates(2, 0), new Coordinates(-2, 0),
                                new Coordinates(0, 2), new Coordinates(0, 2),
                        },
                        {
                                new Coordinates(1, 1), new Coordinates(-1, 1),
                                new Coordinates(1, -1), new Coordinates(-1, -1),
                        },
                        {
                                new Coordinates(1, 1), new Coordinates(-1, 1),
                                new Coordinates(1, -1), new Coordinates(-1, -1),
                                new Coordinates(1, 0), new Coordinates(-1, 0),
                                new Coordinates(0, 1), new Coordinates(0, 1),

                        },
                        {
                                new Coordinates(1, 0), new Coordinates(-1, 0),
                                new Coordinates(0, 1), new Coordinates(0, -1),
                        }
                };
    }

    //
    public void makeMove(){
        gameBoard[newPawnPosition.row][newPawnPosition.column]=
        gameBoard[pawnPosition.row][pawnPosition.column];
        gameBoard[pawnPosition.row][pawnPosition.column]="   ";
    }
    public boolean isKingDead(){
        return newPawnName.endsWith("K ");
    }


}

class Format {

    final int width = 25;
    final char borderChar = '-';

    private void drawLine() {
        for (int i = 0; i < width; i++) System.out.print(borderChar);
        System.out.println();
    }

    public void drawBoard(String[][] board) {
        drawLine();
        for (int row = 0; row < board.length; row++) {
            String[] strings = board[row];
            System.out.print("|");

            for (int column = 0; column < board.length; column++) System.out.print(strings[column] + "|");
            PrintSideNumbers(row);
            drawLine();
        }
        PrintBottomNumbers();
    }

    private void PrintSideNumbers(int row) {
        System.out.println(" " + (char) (row + 'A'));
    }

    private void PrintBottomNumbers() {
        for (char i = 'A'; i <= 'F'; i++)
            System.out.print("  " + i + " ");
        System.out.println();
    }
}

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


