package com.company;

import java.util.Random;
import java.util.Scanner;

/**
 * Class implementing the functions for the menu
 * @author Martin Tabakov
 */
public class MyArray {
    private final Scanner scanner= new Scanner(System.in);
    private int arraySize;
    private int[] array;
    private int minNum;
    private int maxNum;
    private long sum;
    private double avg;

    public MyArray() {
        this.sum = 0;
        this.maxNum = -1;
        this.minNum = -1;

    }

    /**
     * Sorts the array in ascending order
     */
    public void sortAsc() {
        int temp;
        for (int i = 0; i < arraySize - 1; i++) {
            for (int j = 0; j < arraySize - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Sorts the array in descending order
     */
    public void sortDesc() {
        int temp;
        for (int i = 0; i < arraySize - 1; i++) {
            for (int j = 0; j < arraySize - i - 1; j++) {
                if (array[j] < array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Searches for a number`s position withing the array
     * @param toSearch The number to be searched
     * @return The position where the number is located at. If the number is not found returns {@code -1}
     */
    public int getNPosNum(int toSearch) {
        sortAsc();
        return bSearch(0,arraySize-1,toSearch);
    }

    /**
     * Binary search
     * @param leftBound left bound of the interval
     * @param rightBound right bound of the interval
     * @param searchedNum searched number
     * @return The position where the number is located at. If the number is not found returns {@code -1}
     */
    private int bSearch(int leftBound,int rightBound,int searchedNum){

        if(leftBound>rightBound) return -1;
        int m=(leftBound+rightBound)/2;
        if(searchedNum==array[m]) return m;
        if(searchedNum>array[m]) bSearch(leftBound,m-1,searchedNum);
        return bSearch(m+1,rightBound,searchedNum);
    }

    /**
     * Shuffles the number values within the array
     */
    public void shuffle() {
        Random random = new Random();
        int temp;
        for (int i = 0; i < arraySize; i++) {
            int pos = random.nextInt(arraySize);
            temp = array[pos];
            array[pos] = array[i];
            array[i] = temp;
        }
    }

    public long getSum() {
        return sum;
    }

    public int getMax() {
        if (maxNum == -1) {
            for (int i = 0; i < arraySize; i++) maxNum = Math.max(maxNum, array[i]);
        }
        return maxNum;
    }

    public int getMin() {
        if (minNum == -1) {
            for (int i = 0; i < arraySize; i++) minNum = Math.max(minNum, array[i]);
        }
        return minNum;
    }

    public double getAvg() {
        return avg;
    }

    /**
     * Checks whether the array is symmetrical.
     * @return {@code true} if the array is symmetrical, otherwise {@code false}
     */
    public boolean isSymmetrical() {
        for (int i = 0; i <= arraySize / 2; i++) {
            if (array[i] != array[arraySize - i - 1]) return false;
        }
        return true;
    }

    /**
     * Reverses the numbers in the array
     */
    public void reverseValues() {
        for (int i = 0; i <= arraySize / 2; i++) {
            int temp = array[i];
            array[i] = array[arraySize - i - 1];
            array[arraySize - i - 1] = temp;
        }
    }

    /**
     * Prints to the console all of the array values
     */
    public void printValues() {
        for (int value : array) System.out.print(value + " ");
        System.out.println();
    }

    /**
     * Fills the values in the array and calculates their total and average value.
     */
    public void setNumbers() {
        int i = 0;
        boolean correctNum;
        while (i<arraySize){

            System.out.print( String.format("Въведи число в [%d] : ",i) );
            array[i] = scanner.nextInt();
            correctNum = validateNumber(array[i], 0, 101);

            if (correctNum) {
                sum += array[i];
                i++;
            }
        }
        calculateAverage();
    }

    private void calculateAverage(){
        avg = (double) sum / (double) arraySize;
    }

    /**
     * Checks whether a number is within certain range
     * @param number value to be checked
     * @param min the lowest value
     * @param max the highest value. If min == max then the number is checked for lowest value only
     * @return {@code true} if the number is within range, otherwise {@code false}
     */
    public static boolean validateNumber(int number, int min, int max) {
        if (min == max) return number >= min;
        return number >= min && number < max;
    }

    /**
     * Creates and array with a valid size
     */
    public void setArrSize() {
        System.out.print("Въведете размер на масива: ");
        int sz;
        do {
            sz = scanner.nextInt();
        } while (!validateNumber(sz, 1, 1));

        arraySize = sz;
        this.array = new int[arraySize];
    }
}
