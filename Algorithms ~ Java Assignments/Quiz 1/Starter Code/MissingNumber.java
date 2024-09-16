import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class MissingNumber {

    public static void main(String[] args) throws FileNotFoundException {
        int n = Integer.parseInt(args[0]); 
        String filePath = args[1]; 

        int totalSum = n * (n + 1) / 2; 
        int actualSum = 0; 

        Scanner scanner = new Scanner(new File(filePath)) ;
        while (scanner.hasNextInt()) {
            actualSum += scanner.nextInt();
        }
        scanner.close();
         
        int missingNumber = totalSum - actualSum;
        System.out.println(missingNumber);
    }
}
