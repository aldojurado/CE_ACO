import java.util.ArrayList;
import java.util.List;

class Graph {
    private int V; // Número de nodos
    private List<List<Integer>> adj; // Lista de adyacencia

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u); // Grafo no dirigido
    }

    public List<Integer> getNeighbors(int u) {
        return adj.get(u);
    }

    public int getV() {
        return V;
    }
}
