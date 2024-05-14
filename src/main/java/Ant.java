import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Ant {
    private Set<Integer> firefighters;
    private Graph graph;
    private boolean[] visited;
    private Random random;

    public Ant(Graph graph) {
        this.graph = graph;
        firefighters = new HashSet<>();
        visited = new boolean[graph.getV()];
        random = new Random();
    }

    public void placeFirefighter(int node) {
        firefighters.add(node);
        visited[node] = true;
    }

    public Set<Integer> getFirefighters() {
        return firefighters;
    }

    public void clearFirefighters() {
        firefighters.clear();
        for (int i = 0; i < visited.length; i++) {
            visited[i] = false;
        }
    }

    public int evaluate() {
        return firefighters.size();
    }

    public void constructSolution(double[][] pheromone, double alpha, double beta) {
        for (int node = 0; node < graph.getV(); node++) {
            if (random.nextDouble() < selectProbability(node, pheromone, alpha, beta)) {
                placeFirefighter(node);
            }
        }
    }

    private double selectProbability(int node, double[][] pheromone, double alpha, double beta) {
        double numerator = Math.pow(pheromone[node][node], alpha)
                * Math.pow(1.0 / graph.getNeighbors(node).size(), beta);
        double denominator = 0.0;
        for (int i = 0; i < graph.getV(); i++) {
            if (!visited[i]) {
                denominator += Math.pow(pheromone[i][i], alpha) * Math.pow(1.0 / graph.getNeighbors(i).size(), beta);
            }
        }
        return numerator / denominator;
    }
}
