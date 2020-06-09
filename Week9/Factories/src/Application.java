import java.util.Random;

public class Application {
    public static void main(String[] args) {
        printMachinesPower(initMachines(10));

        HeatManager heatManager = new HeatManager();
        Random random = new Random();
        for (int i=0;i<13;i++){
            if(i%2==0){
                HeatEngine engine = new HeatEngine();
                engine.setPower(random.nextInt(10));
                heatManager.add(engine);
            }
            else {
                WrapperEngine engine = new WrapperEngine();
                engine.setPower(random.nextInt(10));
                heatManager.add(engine);
            }
        }
        heatManager.printPowers();

    }
    private static Machine[] initMachines(int machineCnt){
        Random random = new Random();
        Machine[] machines = new Machine[machineCnt];

        for(int i=0;i<machineCnt;i++){
            machines[i] = new Machine();
            machines[i] = new Machine();
            machines[i].setPower(random.nextInt(100+10));
            machines[i].setColor("Gray");
            machines[i].setHeight(3.0);
        }
        return machines;
    }

    private static void printMachinesPower(Machine[] machines){
        int i=0;
        for(Machine m: machines){
            i++;
            System.out.printf("Machine %d has %d power.\n",i,m.getPower());
        }
    }
}
