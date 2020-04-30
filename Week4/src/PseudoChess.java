import java.text.Normalizer;

public class PseudoChess {

    public static void main(String[] args) {
        int[][] boardB = new int [6][6];
        int[][] boardW = new int [6][6];
        int[][] mergedBoard;

        Format format = new Format();

        initBoards(boardB,boardW);
        boolean toRun = true;

        while (toRun)
        {
            mergedBoard= mergeBoard(boardB,boardW);
            format.drawBoard(mergedBoard);
            toRun=false;
        }

    }
    public static int[][] mergeBoard(int[][] boardB,int[][] boardW ){
        int[][] board = new int[6][6];
        for (int i=0;i<6;i++)
        {
            for (int j = 0; j < 6; j++) {
                if(boardB[i][j]!=0 && boardW[i][j]==0) board[i][j]=boardB[i][j];
                if(boardW[i][j]!=0 && boardB[i][j]==0) board[i][j]=boardW[i][j];
            }
        }
        return board;
    }

    public static void initBoards(int[][] boardB,int[][] boardW){
        for (int i =1; i<6;i++) boardB[0][i-1]=i;
        boardB[0][5]=1;
        for (int i =5; i>=0;i--) boardW[5][i]=6-i;
        boardW[5][0]=1;
    }
}

class Format{

    final int width = 25;
    final char borderChar = '-';

    public void drawLine(){
        for (int i=0;i<width;i++) System.out.print(borderChar);
        System.out.println();
    }
    public void drawBoard(int[][] board) {
        drawLine();
        for (int i = 0; i < board.length; i++) {
            int[] ints = board[i];
            System.out.print("|");
            for (int j = 0; j < board.length; j++)
                System.out.print(convertFigureChar(ints[j]) + "|");
            System.out.println(" "+(i + 1));
            drawLine();
        }
        PrintBoardNums();
    }
    private void PrintBoardNums(){
        for (int i=1;i<=6;i++)
        System.out.print("  "+i+" ");
        System.out.println();
    }
    private String convertFigureChar(int id){
        switch (id){
            case 1: return "Dw ";
            case 2: return " D ";
            case 3: return " Q ";
            case 4: return " K ";
            case 5: return " M ";
            default: return "   ";
        }
    }
}
