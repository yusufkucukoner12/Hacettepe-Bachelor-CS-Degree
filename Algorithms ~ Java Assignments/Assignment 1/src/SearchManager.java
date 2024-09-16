import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.function.BiFunction;

class SearchManager {
    public static double[] runSearchTests(Searchs searchs, BiFunction<int[], Integer, Integer> searchFunction, String dataDescription, String searchMethodName, int numRuns, Pickthedata pickthedata) throws IOException {
        int[] sizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000,250000};
        double[] averageDurations = new double[sizes.length];
        System.out.println(searchMethodName +" with " + dataDescription);
        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            long totalDuration = 0;
            

            for (int run = 0; run < numRuns; run++) {
                int[] data = pickthedata.returnTheData(size);
                Random random = new Random();
                int randomIndex = random.nextInt(size); 

                switch (dataDescription) {
                    case "Random":
                        break;
                    case "Sorted":
                        Arrays.sort(data);
                        break;
                    case "Reversely Sorted":
                        Arrays.sort(data);
                        reverseArray(data);
                        break;
                }

                long startTime = System.nanoTime();
                searchFunction.apply(data, data[randomIndex]); 
                long endTime = System.nanoTime();

                totalDuration += endTime - startTime;
            }

            averageDurations[i] = totalDuration / (double) numRuns;
            System.out.println("Size: " + size + ", Method: " + searchMethodName + ", Average Time: " + averageDurations[i] + "ns");
        }

        return averageDurations;
    }
    private static void reverseArray(int[] array) {
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }
    }
}
