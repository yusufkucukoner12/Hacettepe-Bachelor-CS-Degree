import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class SmallestCharacter {    
    public static int f(String s) {
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); ++i) {
            //char returns a specific ASCII value but since we only deal with lowercase chars and they are aligned in ASCII table
            //so if the ascii value of a is: x then the last lowercase character is x+25.
            ++cnt[s.charAt(i) - 'a']; 
        }
        int max = 0;
        for (int x : cnt) {
            if (x > 0) {
                max = x;
            }
        }
        return max;
    }

    public static int[] countSmallerFrequencies(String[] words, String[] queries) {
        int[] result = new int[queries.length];
        
        for (int i = 0; i < queries.length; i++) {
            int queryFrequency = f(queries[i]);
            for (String word : words) {
                if (f(word) > queryFrequency) {
                    result[i]++;
                }
            }
        }
        
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
    
        String filePath = args[0]; 
        Scanner scanner = new Scanner(new File(filePath));

        String[] queries = scanner.nextLine().trim().split("\\s+");
        String[] words = scanner.nextLine().trim().split("\\s+");

      
            
        int[] answers = countSmallerFrequencies(words, queries);
        
        System.out.print(Arrays.toString(answers));
       
    }
}