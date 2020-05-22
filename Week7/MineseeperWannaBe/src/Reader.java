import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {
    private File file;
    private FileInputStream fis;
    private Scanner scanner;

    public Reader(String fileName) {
        try {
            file = new File(fileName);
            fis = new FileInputStream(file);
            scanner = new Scanner(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public int getValue(String valueName){

        while (scanner.hasNextLine()){
            String line=scanner.nextLine();
            if(line.contains(valueName)) return extractValue(line);
        }
        return -1;
    }

    private int extractValue(String line) {
        String[] lineParts = line.split(":");
        return Integer.parseInt(lineParts[1]);
    }
}
