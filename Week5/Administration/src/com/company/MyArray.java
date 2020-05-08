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

    public int getNPosNum(int toSearch) {
        sortAsc();
        return bSearch(0,arraySize-1,toSearch);
    }

    /**
     * Binary search with recursion
     * @param leftBound left bound
     * @param rightBound right bound
     * @param searchedNum searched number
     * @return the number found or -1 if not found
     */
    private int bSearch(int leftBound,int rightBound,int searchedNum){

        if(leftBound>rightBound) return -1;
        int m=(leftBound+rightBound)/2;
        if(searchedNum==array[m]) return m;
        if(searchedNum>array[m]) bSearch(leftBound,m-1,searchedNum);
        return bSearch(m+1,rightBound,searchedNum);
    }

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

    public boolean isSymmetrical() {
        for (int i = 0; i <= arraySize / 2; i++) {
            if (array[i] != array[arraySize - i - 1]) return false;
        }
        return true;
    }

    public void reverse() {
        for (int i = 0; i <= arraySize / 2; i++) {
            int temp = array[i];
            array[i] = array[arraySize - i - 1];
            array[arraySize - i - 1] = temp;
        }
    }

    public void print() {
        for (int value : array) System.out.print(value + " ");
        System.out.println();
    }

    public void setNumbers() {
        int i = 0;
        boolean correctNum;
        while (i<arraySize){

            System.out.print("Insert number[" + (i+1) + "] : ");
            array[i] = scanner.nextInt();
            correctNum = validateNumber(array[i], 0, 101);

            if (correctNum) {
                sum += array[i];
                i++;
            }
        }

        avg = (double) sum / (double) arraySize;
    }

    public static boolean validateNumber(int number, int min, int max) {
        if (min == max) return number > min;
        return number >= min && number < max;
    }

    public void setArrSize() {
        System.out.print("Insert array size: ");
        int sz;
        do {
            sz = scanner.nextInt();
        } while (!validateNumber(sz, 1, 1));

        arraySize = sz;
        this.array = new int[arraySize];
    }
}
