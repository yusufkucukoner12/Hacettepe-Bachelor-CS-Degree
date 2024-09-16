import java.util.*;
import java.io.*;

public class Quiz2 {
    public static void main(String[] args) throws IOException {
        String inputFile = args[0];
        FileReader reader = new FileReader(inputFile);
        int capacity = reader.readInt();
        int n = reader.readInt();

        int[] weights = new int[n];

        for (int i = 0; i < n; i++) {
            weights[i] = reader.readInt();
        }

        boolean[][] dp = new boolean[capacity + 1][n + 1];

        for (int m = 0; m <= capacity; m++){
            for (int i = 0; i <= n; i++) {
                if (i == 0 || m == 0) 
                    if (m == 0) 
                        dp[m][i] = true;
                    else 
                        dp[m][i] = false;
                else if (weights[i - 1] > m) 
                    dp[m][i] = dp[m][i - 1]; 
                else 
                    dp[m][i] = dp[m][i - 1] || dp[m - weights[i - 1]][i - 1]; 
            }
        }

        int max = 0;
        for (int m = 0; m <= capacity; m++) {
            if (dp[m][n]) {
                max = m;
            }
        }

        System.out.println(max);

        for (int m = 0; m <= capacity; m++) {
            for (int i = 0; i <= n; i++) {
                if(dp[m][i])
                    System.out.print("1");
                else
                    System.out.print("0");
            }
            System.out.println();
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
    public int readInt(){
        return scanner.nextInt();
    }
}