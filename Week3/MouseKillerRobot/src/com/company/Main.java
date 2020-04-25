package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean toRun = true;
        int batteryCharge = 4;

        while (toRun){

            ProcessObstacle();

            if(HasEnoughBattery(batteryCharge)){
                if(IsMouseDetected() && IsHitFurnitureSafe()){
                    System.out.println("Killed a mouse!");
                    batteryCharge--;
                }
                else{
                    System.out.println("Mouse not killed.");
                }
            }
            else{
                System.out.println("Battery Empty! Going For Recharge.");
                batteryCharge=ChargeBattery(batteryCharge);
            }
            toRun=!TurnOffRobot();
        }

        Communicate();

    }
    public static int ChargeBattery(int batteryCharge){
        while (batteryCharge==0){
            if(IsBatteryRecharged()){
                System.out.println("Recharge Successful!");
                batteryCharge=4;}
            else System.out.println("Failed to charge! Trying again.");
        }
        return batteryCharge;
    }

    public static void GetObstacle(){

        Scanner scanner = new Scanner(System.in);
        int obstacle = scanner.nextInt();

        switch (obstacle){
            case 1:
                System.out.println("Go Sideways.");
                break;
            case 2:
                System.out.println("Jump.");
                break;
            case 3:
                System.out.println("Forward.");
                break;
            default:
                System.out.println("Invalid Input.");
                GetObstacle();
        }
    }

    public static void ProcessObstacle(){
        PrintObstacleMenu();
        GetObstacle();
    }
    public static void  PrintObstacleMenu(){
        System.out.println("Object Codes:");
        System.out.println("Wall (1)");
        System.out.println("Chair (2)");
        System.out.println("Nothing (3)");
        System.out.print ("Insert Object Code: ");
    }

    public static int GetPixels(){
        System.out.print("Insert Pixel Count: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    public static boolean IsMouseDetected(){
        boolean isMouseDetected=GetPixels()%2==0;
        if (isMouseDetected) System.out.println("Mouse Detected!");
        else System.out.println("Mouse Not Detected!");
        return isMouseDetected;
    }
    public static boolean HasEnoughBattery(int batteryCharge){
        return batteryCharge>0;
    }

    public static boolean IsHitFurnitureSafe(){
        Random random = new Random();
        boolean res =(random.nextInt(9)+1)!=5;
        if (res) System.out.println("Hit Wont Affect Furniture!");
        else System.out.println("Hit Will Affect Furniture! Abort!");
        return res;
    }
    public static boolean IsBatteryRecharged(){
        Random random = new Random();
        int firstCharge = random.nextInt(999)+1;
        int secondCharge = random.nextInt(999)+1;
        return firstCharge> secondCharge;
    }
    public static void Communicate()
    {
        for (int i=10;i>1;i-=2) System.out.println("I am a Robotttttt "+ i);
    }
    public static boolean TurnOffRobot(){
        PrintTurnOffRobotMeu();
        Scanner scanner = new Scanner(System.in);
        int responseCode=scanner.nextInt();
        return responseCode==1;
    }
    public static void PrintTurnOffRobotMeu(){
        System.out.println("Cycle Completed!");
        System.out.println("Stop Robot?");
        System.out.println("Yes (1)");
        System.out.println("No (0)");
        System.out.print("Insert code: ");
    }
}
