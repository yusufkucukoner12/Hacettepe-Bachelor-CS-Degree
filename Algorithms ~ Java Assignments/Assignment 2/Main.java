import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.*;
import java.io.*;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/
        // TODO: Your code goes here
        // You are expected to read the file given as the first command-line argument to read 
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        // PowerGridOptimization object. You need to call GetOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        String inputFile = args[0];
        FileReader reader = new FileReader(inputFile);
        ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour = new ArrayList<>();
        int total = 0;
        
        while(reader.hasNextInt()){
            int a = reader.readInt();
            amountOfEnergyDemandsArrivingPerHour.add(a);
            total += a; // total demanded energy
        }

        PowerGridOptimization powerGridOptimization = new PowerGridOptimization(amountOfEnergyDemandsArrivingPerHour);
        OptimalPowerGridSolution optimalPowerGridSolution =  powerGridOptimization.getOptimalPowerGridSolutionDP();

        System.out.println("The total number of demanded gigawatts: " + total);
        System.out.println("Maximum number of satisfied gigawatts: " + optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands());
        System.out.println("Hours at which the battery bank should be discharged: " + optimalPowerGridSolution.getHoursToDischargeBatteriesForMaxEfficiency().stream().map(Object::toString).collect(Collectors.joining(", ")));
        System.out.println("The number of unsatisfied gigawatts: " + (total - optimalPowerGridSolution.getmaxNumberOfSatisfiedDemands()));
            
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements 
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.

        String inputFile1 = args[1];
        FileReader reader1 = new FileReader(inputFile1);
        ArrayList<Integer> maintenanceTaskEnergyDemands = new ArrayList<>();
        int numberOfESV = reader1.readInt();
        int capacity = reader1.readInt();

        while(reader1.hasNextInt()){
            maintenanceTaskEnergyDemands.add(reader1.readInt());
        }
        OptimalESVDeploymentGP optimalESVDeploymentGP = new OptimalESVDeploymentGP(maintenanceTaskEnergyDemands);
        int a = optimalESVDeploymentGP.getMinNumESVsToDeploy(numberOfESV, capacity);
        
        if(a != -1){
            System.out.println("The minimum number of ESVs to deploy: " + a);
            int count = 1;
            for(int i  = 0 ; i<a; i++){
                System.out.println("ESV " + count++ + " tasks: " + optimalESVDeploymentGP.getMaintenanceTasksAssignedToESVs().get(i));
            }
            
        }   
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
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
    public boolean hasNextInt(){
        return scanner.hasNextInt();
    }    
}
