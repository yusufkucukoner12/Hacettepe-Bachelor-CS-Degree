import java.io.*;
import java.util.ArrayList;

/**
 * The IO class provides methods for reading input from a file and writing output to a file.
 * It contains a static field myWriter that can be used to write output to a file.
 */
public class IO {

    static FileWriter myWriter; //static field used to write output to a file

    /**
     * Reads input from a file and returns a list of clean input.
     * @param nameOfTheFile name of the file to read input from
     * @return cleanInputList list of non-empty strings read from the file
     * @throws IOException if there is an error reading the file
     */
    public ArrayList<String> returnInput(String nameOfTheFile) throws IOException {
        ArrayList<String> inputList = new ArrayList<String>(); //list to store all strings read from file
        ArrayList<String> cleanInputList = new ArrayList<String>(); //list to store non-empty strings read from file
        File input = new File(nameOfTheFile);
        BufferedReader realInput = new BufferedReader(new FileReader(input));
        String inp;

        while ((inp = realInput.readLine()) != null) {
            inputList.add(inp);
        }
        //iterate over inputList and add non-empty strings to cleanInputList
        for(String element : inputList){
            if(!element.equals("")&&!element.equals(" ")){
                cleanInputList.add(element);
            }
        }
        return cleanInputList;
    }
    /**
     * Initialize a FileWriter object to write output to a file.
     * @param nameOfTheFile name of the file to write output to
     * @throws IOException if there is an error creating the FileWriter object
     */
    public void nameOutputFile(String nameOfTheFile) throws IOException {
        myWriter = new FileWriter(nameOfTheFile);
    }
}
