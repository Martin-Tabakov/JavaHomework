package com.company;

public class Main {

    public static void main(String[] args) {
        String month="Май";
        PrintDaysInMonth(month);

    }
    static void PrintDaysInMonth(String month){
        if(month.equals("Януари")) System.out.println("31 дни.");
        else if(month.equals("Февруари")) System.out.println("29 дни.");
        else if(month.equals("Март")) System.out.println("31 дни.");
        else if(month.equals("Април")) System.out.println("30 дни.");
        else if(month.equals("Май")) System.out.println("31 дни.");
        else if(month.equals("Юни")) System.out.println("30 дни.");
        else if(month.equals("Юли")) System.out.println("31 дни.");
        else if(month.equals("Август")) System.out.println("31 дни.");
        else if(month.equals("Септември")) System.out.println("30 дни.");
        else if(month.equals("Октомври")) System.out.println("31 дни.");
        else if(month.equals("Ноември")) System.out.println("30 дни.");
        else if(month.equals("Декември")) System.out.println("31 дни.");
    }
}
