import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    private Scanner scanner;
    public Reader(String fileName){
        try {
            File file = new File(fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            scanner = new Scanner(fileInputStream);
        }catch (FileNotFoundException ignored){

        }
    }

    public ArrayList<String> readPeople(){
        ArrayList<String> toReturn = new ArrayList<>();
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
             toReturn.add(Decoder.decode(line));
        }
        return toReturn;
    }

}
