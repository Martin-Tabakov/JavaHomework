package com.company;

public class Main {

    public static void main(String[] args) {
        int floor =9;
        PrintGreets(floor);
    }
    public static void PrintGreets(int floor){
        switch (floor){
            case 9:
                System.out.println("Здрасти Мими");
                break;
            case 8:
                System.out.println("Здрасти Пепи");
                break;
            case 7:
                System.out.println("Здрасти Ваня");
                break;
            case 6:
                System.out.println("Здрасти Кати");
                break;
            case 5:
                System.out.println("Здрасти Малко коте");
                break;
            case 4:
                System.out.println("Здрасти Марийка");
                break;
            case 3:
                System.out.println("Здрасти Стефка");
                break;
            case 2:
                System.out.println("Здрасти Другата Стефка");
                break;
            case 1:
                System.out.println("Здрасти Таня");
                break;
            case 0:
                System.out.println("Ауч");
                break;
        }
         if(floor>0) PrintGreets(--floor);
    }
}
