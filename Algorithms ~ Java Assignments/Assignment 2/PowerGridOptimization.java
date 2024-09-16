import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP() {
        int sol[] = new int[amountOfEnergyDemandsArrivingPerHour.size() + 1]; // Increase size by 1 to handle index 0
        sol[0] = 0;
    
        ArrayList<Integer>[] hours = new ArrayList[amountOfEnergyDemandsArrivingPerHour.size() + 1];
    
        hours[0] = new ArrayList<>();
    
        for (int i = 1; i <= amountOfEnergyDemandsArrivingPerHour.size(); i++) {
            sol[i] = Integer.MIN_VALUE;
            int cutHour = 0;
            hours[i] = new ArrayList<>(); 
            for (int j = 0; j < i; j++) {
                int max = sol[j] + Math.min(amountOfEnergyDemandsArrivingPerHour.get(i - 1), (i-j)*(i-j));
                if (max > sol[i]) {
                    cutHour = j;
                    sol[i] = max;
                }
            }
            hours[i].addAll(hours[cutHour]); 
            hours[i].add(i);
        }
    
        return new OptimalPowerGridSolution(sol[amountOfEnergyDemandsArrivingPerHour.size()], hours[amountOfEnergyDemandsArrivingPerHour.size()]);
    }    
}
