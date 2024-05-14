
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ACO {

    // Almacena la ruta del archivo ingresada para devolver la solución
    String rutaArchivo = "";
    private int[][] matrizAdyacencia;
    // Almacena el número de vértices del archivo ingresado para crear las matrices
    private int numVertices;

    public ACO(String rutaIngresada) {
        rutaArchivo = rutaIngresada;
        leeArchivo(rutaIngresada);
    }

    public void ejecutaACO() {
        // quizá borre toda la clase
    }

    /**
     * Lee el archivo .col ingresado y almacena la información en la matriz de
     * adyacencia.
     * 
     * @param rutaIngresada Ruta del archivo .col
     */
    private void leeArchivo(String rutaIngresada) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/output/graficas/" + rutaIngresada + ".col"))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                // Comentarios
                if (linea.startsWith("c")) {
                    continue;
                    // Número de vértices y aristas
                } else if (linea.startsWith("p edge")) {
                    String[] partes = linea.split(" ");
                    numVertices = Integer.parseInt(partes[2]);
                    // Creamos la matriz de adyacencia con el número de vértices
                    matrizAdyacencia = new int[numVertices][numVertices];
                    // Las adyacencias de los vértices
                } else if (linea.startsWith("e")) {
                    String[] partes = linea.split(" ");
                    // Le restamos uno a los vértices para que se representen correctamente en la
                    // matriz que comienza en 0
                    int v1 = Integer.parseInt(partes[1]) - 1;
                    int v2 = Integer.parseInt(partes[2]) - 1;
                    // Nos informa que son adyacentes, por lo que asignamos 1 en la matriz.
                    matrizAdyacencia[v1][v2] = 1;
                    matrizAdyacencia[v2][v1] = 1;
                } else {
                    throw new RuntimeException("Esquema de codificación incorrecto en el archivo .col ingresado");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No se ha podido leer el archivo ingresado.", e);
        }
    }

}
