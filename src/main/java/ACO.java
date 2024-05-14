import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ACO {
    private Graph graph;
    private int numAnts;
    private int maxIterations;
    private double alpha;
    private double beta;
    private double evaporation;
    private double[][] pheromone;
    private Random random;

    public ACO(Graph graph, int numAnts, int maxIterations, double alpha, double beta, double evaporation) {
        this.graph = graph;
        this.numAnts = numAnts;
        this.maxIterations = maxIterations;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporation = evaporation;
        this.pheromone = new double[graph.getV()][graph.getV()];
        this.random = new Random();
        initializePheromones();
    }

    private void initializePheromones() {
        for (int i = 0; i < graph.getV(); i++) {
            for (int j = 0; j < graph.getV(); j++) {
                pheromone[i][j] = 1.0;
            }
        }
    }

    public void optimize() {
        List<Ant> ants = new ArrayList<>();
        for (int i = 0; i < numAnts; i++) {
            ants.add(new Ant(graph));
        }

        Ant bestAnt = null;
        int bestEvaluation = Integer.MAX_VALUE;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (Ant ant : ants) {
                ant.clearFirefighters();
                ant.constructSolution(pheromone, alpha, beta);

                int evaluation = ant.evaluate();
                if (evaluation < bestEvaluation) {
                    bestEvaluation = evaluation;
                    bestAnt = new Ant(graph);
                    bestAnt.getFirefighters().addAll(ant.getFirefighters());
                }
            }

            updatePheromones(ants);
        }

        System.out.println("Best solution found with " + bestEvaluation + " firefighters.");
        System.out.println("Firefighters placed at nodes: " + bestAnt.getFirefighters());
    }

    private void updatePheromones(List<Ant> ants) {
        for (int i = 0; i < graph.getV(); i++) {
            for (int j = 0; j < graph.getV(); j++) {
                pheromone[i][j] *= (1 - evaporation);
            }
        }

        for (Ant ant : ants) {
            for (int node : ant.getFirefighters()) {
                for (int neighbor : graph.getNeighbors(node)) {
                    pheromone[node][neighbor] += 1.0 / ant.evaluate();
                }
            }
        }
    }
}
