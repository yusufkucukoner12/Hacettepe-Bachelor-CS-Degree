import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReaderExample {

    public static void main(String[] args) throws IOException {
        String csvFile = "TrafficFlowDataset.csv"; 
        int flowDurationIndex = 6; 
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            String flowDurationValue = values[flowDurationIndex];
            System.out.println("Flow Duration: " + flowDurationValue);
        }
        br.close(); 
    }
}
