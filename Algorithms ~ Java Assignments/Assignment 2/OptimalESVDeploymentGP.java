import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP {
    private ArrayList<Integer> maintenanceTaskEnergyDemands;
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) {
        Collections.sort(maintenanceTaskEnergyDemands, Collections.reverseOrder());

        ArrayList<Integer> esvs = new ArrayList<>();
        for (int i = 0; i < maxNumberOfAvailableESVs; i++) {
            esvs.add(maxESVCapacity);
        }
        for (int task : maintenanceTaskEnergyDemands) {
            if(task > maxESVCapacity){
                System.out.println("Warning: Mission Eco-Maintenance Failed.");
                return -1;
            }
            for (int i = 0; i < maxNumberOfAvailableESVs; i++) {
                if(task > esvs.get(i)){
                    if(i == maxNumberOfAvailableESVs - 1){
                        System.out.println("Warning: Mission Eco-Maintenance Failed.");
                        return -1;
                    }
                    continue;
                }
                else{
                    if(maintenanceTasksAssignedToESVs.size() <= i){
                        maintenanceTasksAssignedToESVs.add(new ArrayList<>());
                    }
                    maintenanceTasksAssignedToESVs.get(i).add(task);
                    esvs.set(i, esvs.get(i) - task);
                    break;
                    
                }
            }
        }

        return maintenanceTasksAssignedToESVs.size();
    }
}
