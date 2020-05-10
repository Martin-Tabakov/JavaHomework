package com.company;

import java.util.Scanner;

/**
 * @author Martin Tabakov
 * Homework 5 Administration
 */
public class Main {

    public static void main(String[] args) {
        MyArray array = new MyArray();
        array.setArrSize();
        array.setNumbers();
        menu(array);
    }

    public static void printMenu() {
        System.out.println("1. Сортиране на числата във възходящ ред");
        System.out.println("2. Сортиране на числата във низходящ ред");
        System.out.println("3. Търсене на позиция на конкретно число");
        System.out.println("4. Разбъркване на числата");
        System.out.println("5. Изчисляване на сбора на всички числа");
        System.out.println("6. Намиране на най-голямото число");
        System.out.println("7. Намиране на най-малкото число");
        System.out.println("8. Намиране на средно-аритметично число");
        System.out.println("9. Проверка за симетричност на числата");
        System.out.println("10. Обръщане на масива от числа");
        System.out.println("11. Визуализиране на числата");
        System.out.println("12. Изход");
    }

    /**
     * Gets a correct menu option
     * @param scanner Scanner for data input
     * @return Valid menu option
     */
    public static int getMenuOption(Scanner scanner) {
        System.out.println("Въведи номер на опция от менюто");
        int menuOption;
        do {
            menuOption = scanner.nextInt();
        } while (!MyArray.validateNumber(menuOption,1,13));
        return menuOption;
    }

    /**
     *  Executes a menu option with formatted output
     * @param optionNum Menu option
     * @param array The working array
     * @param scanner Scanner for data input
     * @return {@code false} if selected option 12 to exit the app, otherwise {@code true}
     */
    public static boolean selectOption(int optionNum, MyArray array,Scanner  scanner) {
        switch (optionNum) {
            case 1:
                array.sortAsc();
                break;
            case 2:
                array.sortDesc();
                break;
            case 3:
                getNumberPosition(scanner,array);
                break;
            case 4:
                array.shuffle();
                break;
            case 5:
                System.out.println(array.getSum());
                break;
            case 6:
                System.out.println("Максимално число: " + array.getMax());
                break;
            case 7:
                System.out.println("Минимално число: " + array.getMin());
                break;
            case 8:
                System.out.println("Средноаритметично: " + array.getAvg());
                break;
            case 9:
                if (array.isSymmetrical()) System.out.println("Масивът е симетричен");
                else System.out.println("Масивът не е симетричен");
                break;
            case 10:
                array.reverseValues();
                break;
            case 11:
                array.printValues();
                break;
            case 12:
                return false;
        }
        return true;
    }

    public static void getNumberPosition(Scanner scanner,MyArray array){
        System.out.print("Въведи число:");
        int num = scanner.nextInt();
        int pos = array.getNPosNum(num);
        if (pos == -1) System.out.println("Числото не е намерено");
        else System.out.println("Числото е намерено на позиция " + pos);
    }

    /**
     * Contains the main loop of the menu
     * @param array The array containing data for manipulation
     */
    public static void menu(MyArray array) {
        Scanner scanner = new Scanner(System.in);
        boolean runApp = true;
        while (runApp) {
            printMenu();
            int menuOption = getMenuOption(scanner);
            runApp = selectOption(menuOption, array,scanner);
        }
    }
}
