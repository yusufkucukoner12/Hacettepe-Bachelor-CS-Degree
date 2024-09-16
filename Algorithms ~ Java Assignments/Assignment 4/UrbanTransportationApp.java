import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;

    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();

        PriorityQueue<StationDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(sd -> sd.time));
        Map<Station, Double> distances = new HashMap<>();
        Map<Station, RouteDirection> edgeTo = new HashMap<>();
        Set<Station> visited = new HashSet<>();

        for (TrainLine line : network.lines) {
            for (Station station : line.trainLineStations) {
                distances.put(station, Double.POSITIVE_INFINITY);
            }
        }

        distances.put(network.startPoint, 0.0);
        pq.add(new StationDistance(network.startPoint, 0.0));

        while (!pq.isEmpty()) {
            StationDistance current = pq.poll();
            Station currentStation = current.station;

            if (!visited.add(currentStation)) {
                continue;
            }

            for (TrainLine line : network.lines) {
                int currentIndex = line.trainLineStations.indexOf(currentStation);

                if (currentIndex != -1) {
                    if (currentIndex > 0) {
                        Station edgeToStation = line.trainLineStations.get(currentIndex - 1);
                        double trainTime = calculateTrainTime(currentStation, edgeToStation, network.averageTrainSpeed);
                        if (distances.get(currentStation) + trainTime < distances.get(edgeToStation)) {
                            distances.put(edgeToStation, distances.get(currentStation) + trainTime);
                            pq.add(new StationDistance(edgeToStation, distances.get(edgeToStation)));
                            edgeTo.put(edgeToStation, new RouteDirection(currentStation.description, edgeToStation.description, trainTime, true));
                        }
                    }

                    if (currentIndex < line.trainLineStations.size() - 1) {
                        Station nextStation = line.trainLineStations.get(currentIndex + 1);
                        double trainTime = calculateTrainTime(currentStation, nextStation, network.averageTrainSpeed);
                        if (distances.get(currentStation) + trainTime < distances.get(nextStation)) {
                            distances.put(nextStation, distances.get(currentStation) + trainTime);
                            pq.add(new StationDistance(nextStation, distances.get(nextStation)));
                            edgeTo.put(nextStation, new RouteDirection(currentStation.description, nextStation.description, trainTime, true));
                        }
                    }
                }

                for (Station nextStation : line.trainLineStations) {
                    if (!visited.contains(nextStation) && !nextStation.equals(currentStation)) {
                        double walkingTime = calculateWalkingTime(currentStation, nextStation, network.averageWalkingSpeed);
                        if (distances.get(currentStation) + walkingTime < distances.get(nextStation)) {
                            distances.put(nextStation, distances.get(currentStation) + walkingTime);
                            pq.add(new StationDistance(nextStation, distances.get(nextStation)));
                            edgeTo.put(nextStation, new RouteDirection(currentStation.description, nextStation.description, walkingTime, false));
                        }
                    }
                }
            }
        }

        Station nearestStation = null;
        double minTimeToDestination = Double.POSITIVE_INFINITY;
        for (TrainLine line : network.lines) {
            for (Station station : line.trainLineStations) {
                double walkingTimeToDestination = calculateWalkingTime(station, network.destinationPoint, network.averageWalkingSpeed);
                double totalTime = distances.get(station) + walkingTimeToDestination;
                if (totalTime < minTimeToDestination) {
                    minTimeToDestination = totalTime;
                    nearestStation = station;
                }
            }
        }

        double directWalkingTime = calculateWalkingTime(network.startPoint, network.destinationPoint, network.averageWalkingSpeed);
        if (directWalkingTime < minTimeToDestination) {
            distances.put(network.destinationPoint, directWalkingTime);
            edgeTo.put(network.destinationPoint, new RouteDirection("Starting Point", "Final Destination", directWalkingTime, false));
        } else if (nearestStation != null) {
            double walkingTimeToDestination = calculateWalkingTime(nearestStation, network.destinationPoint, network.averageWalkingSpeed);
            distances.put(network.destinationPoint, minTimeToDestination);
            edgeTo.put(network.destinationPoint, new RouteDirection(nearestStation.description, "Final Destination", walkingTimeToDestination, false));
        }

        Station currentStation = network.destinationPoint;
        while (edgeTo.containsKey(currentStation)) {
            RouteDirection direction = edgeTo.get(currentStation);
            routeDirections.add(0, direction);
            currentStation = findStationByName(network, direction.startStationName);
        }

        return routeDirections;
    }

    private double calculateWalkingTime(Station start, Station end, double walkingSpeed) {
        double distance = calculateDistance(start.coordinates, end.coordinates);
        return distance / walkingSpeed;
    }

    private double calculateTrainTime(Station start, Station end, double trainSpeed) {
        double distance = calculateDistance(start.coordinates, end.coordinates);
        return distance / trainSpeed;
    }

    private double calculateDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    private Station findStationByName(HyperloopTrainNetwork network, String name) {
        for (TrainLine line : network.lines) {
            for (Station station : line.trainLineStations) {
                if (station.description.equals(name)) {
                    return station;
                }
            }
        }
        return null;
    }

    public void printRouteDirections(List<RouteDirection> directions) {
        DecimalFormat df = new DecimalFormat("#0.00");

        System.out.println("The fastest route takes " + df.format(getTotalDuration(directions)) + " minute(s).");
        System.out.println("Directions");
        System.out.println("----------");
        for (int i = 0; i < directions.size(); i++) {
            RouteDirection direction = directions.get(i);
            String type = direction.trainRide ? "Get on the train" : "Walk";
            System.out.println((i + 1) + ". " + type + " from \"" + direction.startStationName + "\" to \"" + direction.endStationName + "\" for " + df.format(direction.duration) + " minutes.");
        }
    }

    private double getTotalDuration(List<RouteDirection> directions) {
        return directions.stream().mapToDouble(d -> d.duration).sum();
    }
}

class StationDistance {
    Station station;
    double time;

    StationDistance(Station station, double time) {
        this.station = station;
        this.time = time;
    }
}
