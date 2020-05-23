import java.util.Random;
import java.util.Scanner;

public class Game {
    private int boardWidth;
    private int boardHeight;
    private int mines;
    private int probes;
    private int disposal;
    boolean isDead=false;
    boolean isAtFinish=false;

    private Tile[][] mineField;

    private final String[] menuOptions = new String[]{
            "1. Проба за мина",
            "2. Обезвреждане на мина",
            "3. (пре)Мини"
    };
    private final String[] posMessage = new String[]{
            "Въведи координати",
            "Формат за въвеждане - height:width",
    };
    Pos[] movements = new Pos[]{
            new Pos(1,0),
            new Pos(-1,0),
            new Pos(0,1),
            new Pos(0,-1)
    };
    Scanner scanner = new Scanner(System.in);
    Pos playerPos;

    public Game(){
        loadValues();
    }

    private void loadValues(){
        Reader reader = new Reader("configurations.txt");
        this.probes= reader.getValue("probes_number");
        this.disposal = reader.getValue("disposal_number");

        Reader reader1 = new Reader("enemy_terity.txt");
        this.boardWidth = reader1.getValue("width");
        this.boardHeight = reader1.getValue("height");
        this.mines = reader1.getValue("mines");
    }
    public boolean isDataCorrect(){
         return validateConfigValues() && validateTerityValues();
    }
    public boolean isDataIncorrect(){
        return !isDataCorrect();
    }
    private boolean validateConfigValues(){
        return probes>=0 && disposal>=0;
    }
    private boolean validateTerityValues(){
        return (boardWidth >=4 && boardHeight >=4) && (mines>=0 && mines<= boardWidth * boardHeight -2);
    }

    public void createMineField(){
        mineField = new Tile[boardHeight][boardWidth];
        assignStartFinishTiles();
        assignMines();
        fillMineField();
    }


    private void assignStartFinishTiles(){
        Random random = new Random();
        int side = random.nextInt(4);
        int h1=0,w1=0;

        switch (side){
            case 0: h1=0; w1=random.nextInt(boardWidth-2)+1; break;
            case 1: h1= random.nextInt(boardHeight-2)+1; w1= boardWidth -1; break;
            case 2: h1= boardHeight -1; w1=random.nextInt(boardWidth-2)+1; break;
            case 3: h1= random.nextInt(boardHeight-2)+1; w1= 0; break;
        }

        int side2= random.nextInt(4);
        while (side==side2) side2= random.nextInt(4);

        int h2=0,w2=0;

        switch (side2){
            case 0: h2=0; w2=random.nextInt(boardWidth-2)+1; break;
            case 1: h2= random.nextInt(boardHeight-2)+1; w2= boardWidth -1; break;
            case 2: h2= boardHeight -1; w2=random.nextInt(boardWidth-2)+1; break;
            case 3: h2= random.nextInt(boardHeight-2)+1; w2= 0; break;
        }

        mineField[h1][w1] = new Tile(true,false,false);
        playerPos = new Pos(h1,w1);
        mineField[h2][w2] = new Tile(false,true,false);
    }

    private void assignMines(){
        Random random = new Random();
        while (mines>0){
            int h = random.nextInt(boardHeight);
            int w = random.nextInt(boardWidth);
            if(mineField[h][w]==null){
                mineField[h][w] = new Tile(false,false,true);
                mines--;
            }
        }
    }
    private void fillMineField(){
        for(int i = 0; i< boardHeight; i++){
            for (int j = 0; j< boardWidth; j++)
                if(mineField[i][j]==null) mineField[i][j]= new Tile(false,false,false);
        }
    }
    public void printMineField(){
        for(int i = 0; i< boardHeight; i++){
            System.out.print(i+" ");
            for (int j = 0; j< boardWidth; j++) System.out.print(mineField[i][j].getChar()+" ");

            System.out.println();
        }
        System.out.print(" ");
        for (int i = 0; i< boardWidth; i++) System.out.print(" "+i);
        System.out.println();

    }
    public void printInventory(){
        System.out.printf("Брой проби: %d\nБрой обезвреждания: %d\n",this.probes,this.disposal);
    }

    public void startMenu(){
        Pos pos = getCoordinates();

        int option= getOption(menuOptions.length);
        switch (option){
            case 1: makeProbe(pos); break;
            case 2: defuseMine(pos); break;
            case 3: changePos(pos); break;
        }
    }
    private Pos getCoordinates(){
        int newH;
        int newW;
        Pos gotoPos;
        do{
            printLines(posMessage);
            String line = scanner.next();
            String[] parts = line.split(":");
            newH= Integer.parseInt(parts[0]);
            newW= Integer.parseInt(parts[1]);
            gotoPos = new Pos(newH,newW);
        }while (gotoPos.offBoard(boardHeight,boardWidth) && getPosId(gotoPos)!=-1);
        return gotoPos;
    }

    private void printLines(String[] lines){
        for (String line : lines) System.out.println(line);
    }

    private int getOption(int size){
        boolean isValid;
        int val;
        do {
            printLines(menuOptions);
            val=scanner.nextInt();
            isValid = val>=1 && val<=size;
        }while (!isValid);
        return val;
    }

    private void makeProbe(Pos pos){
        if(this.probes>0){
            this.probes--;
            if(this.playerPos.getHeight()!=pos.getHeight()) {
                scanVertical(pos);
                return;
            }
            if(this.playerPos.getWidth()!=pos.getWidth()) scanHorizontal(pos);
        }
    }

    private void scanHorizontal(Pos pos){
        int diff =pos.getWidth() - this.playerPos.getWidth();
        for(int i=0;i<=1;i++){
            for(int j = this.playerPos.getHeight()-1; j<=this.playerPos.getHeight()+1; j++){
                if(!new Pos(j, pos.getWidth() + i * diff).offBoard(boardHeight, boardWidth)){
                    mineField[j][pos.getWidth() + i * diff].isScanned=true;
                }
            }
        }
    }
    private void scanVertical(Pos pos){
        int diff =pos.getHeight() - this.playerPos.getHeight();
        for(int i=0;i<=1;i++){
            for(int j = this.playerPos.getWidth()-1; j<=this.playerPos.getWidth()+1; j++){
                if(!new Pos(pos.getHeight() + i * diff,j).offBoard(boardHeight, boardWidth)){
                    mineField[pos.getHeight() + i * diff][j].isScanned=true;
                }
            }
        }
    }

    private void defuseMine(Pos pos){
        if(mineField[pos.getHeight()][pos.getWidth()].hasBomb && this.disposal>0){
                mineField[pos.getHeight()][pos.getWidth()].defuseBomb();
                mineField[this.playerPos.getHeight()][this.playerPos.getWidth()].makeVisited();
                this.playerPos =pos;
                this.disposal--;
            }
            else this.isDead=true;
    }

    private void changePos(Pos newPos) {
        int id= getPosId(newPos);

            mineField[playerPos.getHeight()][playerPos.getWidth()].makeVisited();
            this.playerPos.Add(movements[id]);
            if(mineField[newPos.getHeight()][newPos.getWidth()].hasBomb) this.isDead=true;
            if(mineField[newPos.getHeight()][newPos.getWidth()].isFinish) this.isAtFinish=true;
            mineField[playerPos.getHeight()][playerPos.getWidth()].placePlayer();
        }


    public boolean isDead(){
        return isDead;
    }
    public boolean isAtFinish(){
        return isAtFinish;
    }

    private int getPosId(Pos newPos){
        for(int i=0;i<movements.length;i++){
            if( Pos.Equal(Pos.Add(this.playerPos, movements[i]),newPos)) return i;
        }
        return -1;
    }
}
