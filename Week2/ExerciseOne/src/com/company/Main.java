package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the two sides of the rectangle.");
        float a=scanner.nextFloat();
        float b=scanner.nextFloat();
        scanner.close();
        System.out.println("Area: "+PrintArea(a,b));
        System.out.println("Perimeter: "+PrintPerimeter(a,b));
    }
    public static float PrintArea(float x,float y){
        return x*y;
    }
    public  static float PrintPerimeter(float x, float y){
        return 2*(x+y);
    }
}
