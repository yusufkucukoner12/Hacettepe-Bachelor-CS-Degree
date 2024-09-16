import java.io.*;
import java.util.*;

public class Quiz1 {
    public static void main(String[] args) throws IOException {
        String inputFile = args[0];
        FileReader reader = new FileReader(inputFile);
        String line;
        while (reader.hasNextLine()) {
            line = reader.readLine();
            if (line.equals("...")) {
                readingWordsToIgnore = false;
                continue;
            }
            if (readingWordsToIgnore) {
                wordsToIgnore.add(line);
            } else {
                titles.add(line);
            }
        }


        Set<String> ignoreSet = new HashSet<>(wordsToIgnore);
        ArrayList<String> keyWordArrayList = new ArrayList<>();

            for (int i = 0; i<titles.size(); i++) {
                String[] words = titles.get(i).split("\\s+");
                for (String word : words) {
                    if (!ignoreSet.contains(word.toLowerCase(Locale.ENGLISH)) && !keyWordArrayList.contains(word.toLowerCase(Locale.ENGLISH))) {
                        keyWordArrayList.add(word.toLowerCase(Locale.ENGLISH));
                        
                        
                    }
                }
        }
        insertionSort(keyWordArrayList);

        printKeywordInContextIndex(keyWordArrayList, titles);
    }

    public static void insertionSort(ArrayList<String> arr) {
        for (int i = 1; i < arr.size(); i++) {
            String key = arr.get(i);
            int j = i - 1;

            while (j >= 0 && arr.get(j).compareTo(key) > 0) {
                arr.set(j + 1, arr.get(j));
                j = j - 1;
            }
            arr.set(j + 1, key);
        }
    }
    public static void printKeywordInContextIndex(ArrayList<String> keywords, List<String> titles) {
        for (String keyword : keywords) {
            for (String title : titles) {
                if (title.toLowerCase(Locale.ENGLISH).contains(keyword)) {
                        int index = titles.indexOf(title);
                        capitalizeKeywordInTitle(titles.get(index), keyword);
                }
            }
        }
    }

    public static void capitalizeKeywordInTitle(String title, String keyword) {
        String[] words = title.split("\\s+");
        StringBuilder indexedTitle = new StringBuilder();
        int count = 0;
        while (true){
            int x = 0;
            boolean found = false;
            for (String word : words) {
                if (word.equalsIgnoreCase(keyword)) {
                    if (x == count) {
                        indexedTitle.append(word.toUpperCase(Locale.ENGLISH)).append(" ");
                        found = true;
                    } else {
                        indexedTitle.append(word.toLowerCase(Locale.ENGLISH)).append(" ");
                    }
                    x++;
                } else {
                    indexedTitle.append(word.toLowerCase(Locale.ENGLISH)).append(" ");
                }
            }
            count++;
            if ( found == false) {
                break;
            }
            System.out.println(indexedTitle.toString().trim());
            indexedTitle.setLength(0);
        }
    }
}
class FileReader {
    private Scanner scanner;
    public FileReader(String filename) throws IOException {
        scanner = new Scanner(new File(filename));
    }
    public String readLine() {
        return scanner.nextLine();
    }
    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }
}