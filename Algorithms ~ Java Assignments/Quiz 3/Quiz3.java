import java.io.*;
import java.util.*;

public class Quiz3 {
    public static void main(String[] args) throws IOException {
        String inputFile = args[0];
        FileReader reader = new FileReader(inputFile);
        
        int numCases = reader.readInt(); 
        
        for (int i = 0; i < numCases; i++) {
            int S = reader.readInt(); 
            int P = reader.readInt(); 
            
            List<int[]> stations = new ArrayList<>();
            for (int j = 0; j < P; j++) {
                int[] station = new int[2];
                station[0] = reader.readInt();
                station[1] = reader.readInt(); 
                stations.add(station);
            }
            
            double threshold = primMST(stations, S);
            
            System.out.printf("%.2f\n", threshold); 
        }        
    }
    
    private static double distance(int[] station1, int[] station2) {
        int dx = station1[0] - station2[0];
        int dy = station1[1] - station2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    private static double primMST(List<int[]> stations, int S) {
        int N = stations.size();
        boolean[] visited = new boolean[N];
        ArrayList<Double> distances = new ArrayList<Double>();
    
        PriorityQueue<double[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        
        pq.offer(new double[]{0, 0});
        int count = 0;
        
        while (!pq.isEmpty()) {
            double[] cur = pq.poll();
            int u = (int) cur[0];
            double dist = cur[1];
            
            if (visited[u]) continue;
            visited[u] = true;
    
            distances.add(dist);
            count++;
            
            if (count == N) break;
            
            for (int v = 0; v < N; v++) {
                if (!visited[v]) {
                    double d = distance(stations.get(u), stations.get(v));
                    pq.offer(new double[]{v, d}); 
                }
            }
        }
    
        Collections.sort(distances);
            
        return distances.get(N-S);
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

