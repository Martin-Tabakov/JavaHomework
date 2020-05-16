
public class ArrayInt {
    int[] array;

    public ArrayInt(int size) {
        array = new int[size];
    }

    public void setValues() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(String.format("Елемент %d =", i+1));
            array[i] = Menu.getNumber(0, 10000);
        }
    }

    private boolean isPrime(int x) {
        int i = 2;
        if (x == 2) return true;
        while (i <= Math.sqrt(x)) {
            if (x % i == 0) return false;
            i++;
        }
        return true;
    }

    public void printPrimes() {
        for (int number : array) if (isPrime(number)) System.out.print(number + " ");
        System.out.println();
    }

    public void printSubArrayAcs() {
        int maxLeft = 0;
        int maxRight = 1;
        int left = 0;
        int right = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] >= array[i]) {
                int currentRange = right - left;

                if (currentRange > maxRight - maxLeft) {
                    maxLeft = left;
                    maxRight = right;
                }
                left = right;
            }
            right++;
        }
        for (int i = maxLeft; i < maxRight; i++) System.out.print(array[i] + " ");
        System.out.println();
    }

    public void printSubArrayDesc() {
        int maxLeft = 0;
        int maxRight = 1;
        int left = 0;
        int right = 1;
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] <= array[i]) {
                int currentRange = right - left;

                if (currentRange > maxRight - maxLeft) {
                    maxLeft = left;
                    maxRight = right;
                }
                left = right;
            }
            right++;
        }
        for (int i = maxLeft; i < maxRight; i++) System.out.print(array[i] + " ");
        System.out.println();
    }

    public void printSubArrayEq() {
        int maxLeft = 0;
        int maxRight = 0;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {

                if (array[j] == array[i]) {
                    if (j - i > maxRight - maxLeft) {
                        maxLeft = i;
                        maxRight = j;
                    }
                } else break;
            }
        }
        for (int i = maxLeft; i <= maxRight; i++) System.out.print(array[i] + " ");
        System.out.println();
    }

    public void getMostCommon() {
        int number=array[0];
        int occurrence = 1;
        int max = 1;
        for (int item : array) {
            for (int value : array) if (item == value) occurrence++;
            if (occurrence > max) {
                max = occurrence;
                number = item;
            }
            occurrence = 0;
        }

        System.out.println(number);
    }

}
