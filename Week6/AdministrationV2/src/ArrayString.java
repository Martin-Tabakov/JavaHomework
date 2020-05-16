
public class ArrayString {
    String[] array;

    public ArrayString(int size) {
        array = new String[size];
    }

    public void setValues() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(String.format("Дума %d =", i+1));
            array[i] = Menu.getString();
        }
    }

    public void revertWords() {
        for (String word : array) {
            revertWord(word);
            System.out.println();
        }
    }

    private void revertWord(String word) {
        int sz = word.length();
        char[] wordArr = word.toCharArray();
        for (int i = 0; i < sz / 2; i++) {
            char temp = wordArr[i];
            wordArr[i] = wordArr[sz - i - 1];
            wordArr[sz - i - 1] = temp;
        }
        for (int i = 0; i < sz; i++) System.out.print(wordArr[i]);
    }

    public void getWordLen(){
        for (String s : array) {
            System.out.println(String.format("%s - брой символи: %d", s, s.length()));
        }
    }

    public void printRepeatingWords() {
        String[] lowerWords = new  String[array.length+1];
        for(int i=0;i<array.length;i++) lowerWords[i]=array[i].toLowerCase();
        lowerWords[array.length]="QQQQQQQQQQQQQQQQQQQQQQ";
        sort(lowerWords);

        String lastPrinted=lowerWords[0];
        int cnt=1;
        for(int i=1;i<lowerWords.length;i++){
            if(lowerWords[i].equals(lastPrinted)){
                cnt++;
            }
            else {
                System.out.println(String.format("%s брой повторения %d",lastPrinted,cnt));
                cnt=1;
                lastPrinted=lowerWords[i];
            }
        }
    }

    public void sort(String[] lowerWords) {
        String temp;
        for (int i = 0; i < lowerWords.length - 1; i++) {
            for (int j = 0; j < lowerWords.length - i - 1; j++) {
                if (lowerWords[j].compareTo(lowerWords[j + 1])<0 ) {
                    temp = lowerWords[j];
                    lowerWords[j] = lowerWords[j + 1];
                    lowerWords[j + 1] = temp;
                }
            }
        }
    }
}