import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Pickthedata {
    String csvFile;
    Pickthedata(String csvFileName){
        csvFile = csvFileName;
    }
    public int[] returnTheData(int size) throws IOException{
        int flowDurationIndex = 6; 
        int index = 0;
        int[] array = new int[size];
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        String line = br.readLine();
        while ((line = br.readLine()) != null && index != size) {
            String[] values = line.split(",");
            int flowDurationValue = Integer.parseInt(values[flowDurationIndex]);
            array[index] = flowDurationValue;
            index++;
        }
        br.close();
        return array;
    }
}
