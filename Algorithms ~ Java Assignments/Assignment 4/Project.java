import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;
    private boolean marked[];
    List<Task> topoOrderedList = new ArrayList<>();


    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
        marked = new boolean[tasks.size()];
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int[] schedule = getEarliestSchedule();
        return tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1];
    }
    private void topoOrdered(){
        for(Task t : tasks){
            if(!marked[t.getTaskID()]){
                dfs(t);
            }
        }
    }
    private void dfs(Task t){
        marked[t.getTaskID()] = true;
        for(int t1 : t.getDependencies()){
            if(!marked[t1]){
                dfs(tasks.get(t1));
            }
        }
        topoOrderedList.add(t);
    }


    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {
        topoOrdered();
        int[] earliestTimes = new int[tasks.size()];
        for (Task task : topoOrderedList) {
            earliestTimes[task.getTaskID()] = 0;
            for (int dependency : task.getDependencies()) {
                earliestTimes[task.getTaskID()] = Math.max(earliestTimes[task.getTaskID()], earliestTimes[dependency] + tasks.get(dependency).getDuration());
            }
        }
        return earliestTimes;
    }
    


    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    /**
     * Some free code here. YAAAY! 
     */
    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s","Task ID","Description","Start","End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i]+t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1]));
        printlnDash(limit, symbol);        
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
