import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
/**
 * The Main class represents the entry point for the Smart Home System.
 * It contains the main method and manages the execution of the program.
 */

public class Main {
    public static ArrayList<String> input;
    /**
     * The main method is the entry point for the Smart Home System.
     * It calls the I/O to take Input.
     * It calls MethodResolver classes to manage the execution of the program.
     * @param args command line arguments.
     * @throws IOException if an I/O error occurs while reading the input.
     * @throws ParseException if the input cannot be parsed.
     */
    public static void main(String[] args) throws IOException, ParseException {
        IO takeInputCreateOutputFile = new IO();
        input = takeInputCreateOutputFile.returnInput(args[0]);
        takeInputCreateOutputFile.nameOutputFile(args[1]);
        MethodResolver methodResolver = new MethodResolver();
        methodResolver.selectMethod(input);
    }
}