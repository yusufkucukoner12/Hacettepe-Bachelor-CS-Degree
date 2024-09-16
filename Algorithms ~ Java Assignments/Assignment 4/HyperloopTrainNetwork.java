import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperloopTrainNetwork implements Serializable {
    static final long serialVersionUID = 11L;
    public double averageTrainSpeed;
    public final double averageWalkingSpeed = 1000 / 6.0;;
    public int numTrainLines;
    public Station startPoint;
    public Station destinationPoint;
    public List<TrainLine> lines;

    /**
     * Method with a Regular Expression to extract integer numbers from the fileContent
     * @return the result as int
     */
    public int getIntVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Integer.parseInt(m.group(1));

    }

    /**
     * Write the necessary Regular Expression to extract string constants from the fileContent
     * @return the result as String
     */
    public String getStringVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]+)\"");
        Matcher m = p.matcher(fileContent);
        m.find();
        return m.group(1);
    }

    /**
     * Write the necessary Regular Expression to extract floating point numbers from the fileContent
     * Your regular expression should support floating point numbers with an arbitrary number of
     * decimals or without any (e.g. 5, 5.2, 5.02, 5.0002, etc.).
     * @return the result as Double
     */
    public Double getDoubleVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+(?:\\.[0-9]*)?)");
        Matcher m = p.matcher(fileContent);
        m.find();
        return Double.parseDouble(m.group(1));
    }

    /**
     * Write the necessary Regular Expression to extract a Point object from the fileContent
     * points are given as an x and y coordinate pair surrounded by parentheses and separated by a comma
     * @return the result as a Point object
     */
    public Point getPointVar(String varName, String fileContent) {
        Pattern p = Pattern.compile("[t ]*" + varName + "[t ]*=[t ]*(s*(-?d+)s*,s*(-?d+)s*)");
        Matcher m = p.matcher(fileContent);
        m.find();
        int x = Integer.parseInt(m.group(1));
        int y = Integer.parseInt(m.group(2));
        return new Point(x, y);
    }
     /**
     * Function to extract the train lines from the fileContent by reading train line names and their 
     * respective stations.
     * @return List of TrainLine instances
     */
    public List<TrainLine> getTrainLines(String fileContent) {
        List<TrainLine> trainLines = new ArrayList<>();

        Pattern linePattern = Pattern.compile("train_line_name\\s*=\\s*\"([^\"]+)\"\\s*train_line_stations\\s*=\\s*((?:\\(\\s*\\d+\\s*,\\s*\\d+\\s*\\)\\s*)+)");
        Matcher lineMatcher = linePattern.matcher(fileContent);

        while (lineMatcher.find()) {
            String lineName = lineMatcher.group(1);
            String stationsStr = lineMatcher.group(2);
            List<Station> stations = parseStations(stationsStr, lineName);
            trainLines.add(new TrainLine(lineName, stations));
        }

        return trainLines;
    }

    private List<Station> parseStations(String stationsLine, String lineName) {
        int count = 1;
        List<Station> stations = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)");
        Matcher matcher = pattern.matcher(stationsLine);
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            stations.add(new Station(new Point(x, y), lineName + " Line Station " + count++ ));
        }
        return stations;
    }

    /**
     * Function to populate the given instance variables of this class by calling the functions above.
     */
    public void readInput(String filename) {
        try {
            FileReader readFile = new FileReader(filename);
            numTrainLines = getIntVar("num_train_lines", readFile.readLine());
            startPoint = new Station(getPointVar("starting_point", readFile.readLine()), "Starting Point");
            destinationPoint = new Station(getPointVar("destination_point", readFile.readLine()), "Final Destination");
            averageTrainSpeed = getDoubleVar("average_train_speed", readFile.readLine()) * 1000/60;
            
            StringBuilder sbuilder = new StringBuilder();

            while (readFile.hasNextLine()) {
                sbuilder.append(readFile.readLine()).append("\n");
            }
            
            String fileContent = sbuilder.toString();
            lines = getTrainLines(fileContent);

        } catch (IOException e) {
            e.printStackTrace();
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
}

