import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Reader reader = new Reader("Data.txt");
        ArrayList<String> allPeople =  reader.readPeople();
        //for(String person : allPeople) System.out.println(person);
        //testQueries(allPeople);
        makeQuery(allPeople);
    }

    /**
     * Logic for Finished App
     */
    public static void makeQuery(ArrayList<String> allPeople){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert Query:");
        String query = scanner.next();
        System.out.println("Result:");
        executeQuery(allPeople,query);
    }
    /**
     * Logic for testing purposes
     */
    public static void testQueries(ArrayList<String> allPeople){

        ArrayList<String> queries = new ArrayList<>();
        queries.add("SELECT{TYPE}=[R]");
        queries.add("SELECT{TYPE}=[R]@{SEX}=[F]");
        queries.add("SELECT{AGE}=[18]");
        queries.add("SELECT");
        queries.add("SELECT{FNAME}=[Pesho]");
        queries.add("SELECT{SPECIAL_PROPERTY}::{SALARY}=[420]");
        queries.add("SELECT{SPECIAL_PROPERTY}::{PENSION}=[1000]");
        queries.add("SELECT{SPECIAL_PROPERTY}::{KID}=[{KFNAME}=[Nikola]]");
        queries.add("SELECT{SPECIAL_PROPERTY}::{KID}=[{KLNAME}=[Nakov]]");

        for (String query : queries){
            System.out.println("=".repeat(50));
            executeQuery(allPeople,query);
        }
        System.out.println("=".repeat(50));
    }

    public static void executeQuery(ArrayList<String> allPeople,String query){
        String[] toSearch = Decoder.decodeQuery(query);
        boolean toPrint=true;
        for(int i=0;i<allPeople.size();i++){
            for(int j=0;j<toSearch.length;j++)
                if(!allPeople.get(i).contains(toSearch[j])) toPrint=false;

            if(toPrint) System.out.println(allPeople.get(i));
            toPrint=true;
        }
    }

}
