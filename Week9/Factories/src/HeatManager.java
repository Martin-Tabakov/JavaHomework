public class HeatManager {
    Engine[] engines;
    int size;
    public HeatManager(){
        this.engines = new Engine[0];
        this.size=0;
    }
    public void add(Engine engine){
        if(size < engines.length){
            engines[size] = engine;
            size++;
        }
        else{
            grow();
            add(engine);
        }
    }
    private void grow(){
        Engine[] temp = new Engine[(int)Math.pow(2,size)];
        System.arraycopy(engines, 0, temp, 0, engines.length);
        engines=temp;
    }
    public void printPowers(){
        int i=0;
        while (engines[i]!=null){
            System.out.printf("Engine power: %d.\n",engines[i].getPower());
            i++;
        }
    }
}
