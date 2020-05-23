public class Tile {

    boolean isStart;
    boolean isFinish;
    boolean hasBomb;
    boolean isVisited;
    boolean isPlayerHere;
    boolean isScanned;

    public Tile(boolean isStart,boolean isFinish,boolean hasBomb){
        this.isStart=isStart;
        this.isFinish=isFinish;
        this.hasBomb=hasBomb;
        this.isVisited=false;
        this.isPlayerHere=isStart;
        this.isScanned=false;
    }
    public char getChar(){
        if(isStart) return 'S';
        if(isFinish) return 'F';
        if(isPlayerHere) return '*';
        if(isVisited) return 'V';
        if(isScanned){
            if (hasBomb) return 'Y';
            else return 'N';
        }
        return 'X';
    }
    public void makeVisited(){
        this.isPlayerHere=false;
        this.hasBomb=false;
        this.isVisited=true;
    }
    public void placePlayer(){
        this.isPlayerHere=true;
        this.hasBomb=false;
        this.isVisited=false;
    }

    public void defuseBomb(){
        this.hasBomb=false;
        this.isPlayerHere=true;
        this.isVisited=false;
    }
}
