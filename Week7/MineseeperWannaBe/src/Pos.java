class Pos{
    private int height;
    private int width;

    public Pos(int height,int width){
        this.height=height;
        this.width = width;
    }
    public void Add(Pos pos){
        this.height+=pos.height;
        this.width +=pos.width;
    }
    public boolean offBoard(int bHeight,int bWidth){
        return this.getWidth()>=bWidth ||
                this.getWidth() < 0 ||
                this.getHeight()>=bHeight ||
                this.getHeight()< 0;
    }

    public int getHeight(){
        return this.height;
    }
    public int getWidth(){
        return this.width;
    }

    public  static Pos Add(Pos one,Pos two){
        return new Pos(one.height+two.height,one.width +two.width);
    }
    public static boolean Equal(Pos one,Pos two){
        return one.height==two.height && one.width==two.width;
    }
}
