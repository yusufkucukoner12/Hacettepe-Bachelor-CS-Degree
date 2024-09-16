import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

class SortingManager {
    public static double[] runSortingTests(Sorts sorts, Consumer<int[]> sortingFunction, String dataDescription, String sortingMethodName, int numRuns, Pickthedata pickthedata) throws IOException {
        int[] sizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000,250000};
        double[] averageDurations = new double[sizes.length];
        
        System.out.println(sortingMethodName+" with "+dataDescription);

        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            long totalDuration = 0;

            for (int run = 0; run < numRuns; run++) {
                int[] data = null;
                long startTime, endTime, duration;

                data = pickthedata.returnTheData(size);

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

                startTime = System.nanoTime();
                sortingFunction.accept(data);
                endTime = System.nanoTime();
                duration = endTime - startTime;
                totalDuration += duration;
            }

            averageDurations[i] = (totalDuration / (double) numRuns)/1000000;
            System.out.println("Size: " + size + ", Method: " + sortingMethodName + ", Average Time: " + averageDurations[i] + "ms");
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
