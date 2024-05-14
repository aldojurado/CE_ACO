import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Coloracion {

    // Representación del problema con matriz de adyacencia
    private int[][] matrizAdyacencia;

    // Almacena el número de vértices del archivo ingresado para crear las matrices
    private int numVertices;

    // Almacena la ruta del archivo ingresada para devolver la solución
    String rutaArchivo = "";

    /**
     * Constructor de la clase Coloracion
     * Guarda la ruta ingresada en rutaArchivo y lee el archivo
     */
    public Coloracion(String rutaIngresada) {
        rutaArchivo = rutaIngresada;
        leeArchivo(rutaIngresada);
    }

    /**
     * Getter de la clase Coloracion
     * Devuelve la ruta del archivo ingresada
     */
    public String getRuta() {
        return rutaArchivo;
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

    /**
     * Escribe la solución en un archivo de salida.
     * 
     * @param solucion    Un arreglo de enteros que indican los colores de la
     *                    solución
     * @param iteraciones El número de iteraciones con mejora
     * @param tolerancia  El número de iteraciones sin mejora
     */
    private void escribeSolucion(int[] solucion, int iteraciones, int tolerancia) {
        // El nombre del archivo de salida es la ruta del archivo de entrada con un
        // identificador "_solucion" al final.
        String nombreArchivoSalida = "src/output/graficas/" + getRuta() + "_solucion.col";

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivoSalida))){
            int[] solucionRandom = solucionAleatoria();
            int[] solucionVecina = generarSolucionVecina(solucionRandom);
            writer.println("Primer solución aleatoria: " + java.util.Arrays.toString(solucionRandom) + "\n");
            writer.println("Evaluación de la primer solución aleatoria: " + evaluarSolucion(solucionRandom) + "\n");
            writer.println("Solución vecina generada: "
                    + java.util.Arrays.toString(solucionVecina) + "\n");
            writer.println("Evaluación de la solución vecina: " + evaluarSolucion(solucionVecina)
                    + "\n");
            writer.println("Solución más optimizada encontrada después de " + iteraciones + " iteraciones y "
                    + tolerancia + " iteraciones sin mejora.");

            for (int vertice = 0; vertice < solucion.length; vertice++) {
                writer.println("Vértice " + (vertice + 1) + ": Color " + solucion[vertice]);
            }
            writer.println("Total de colores utilizados: " + evaluarSolucion(solucion) + "\n");
            writer.println("Arreglo de valores discretos: " + java.util.Arrays.toString(solucion));

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    /**
     * Genera una solución aleatoria válida, asigna un color distinto a cada vértice
     * adyacente.
     * 
     * @return Un arreglo de enteros que representan colores con la solución
     *         aleatoria.
     */
    private int[] solucionAleatoria() {

        int[] solucion = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            solucion[i] = encontrarColorValido(i, solucion);
        }
        return solucion;
    }

    /**
     * Evalúa la solución ingresada, cuenta cuántos colores son utilizados para
     * colorear la gráfica
     * Mientras menos colores se utilicen la solución es mejor
     * 
     * @param solucion Un arreglo de enteros que representan colores con la solución
     * @return El número de colores utilizados
     */
    private int evaluarSolucion(int[] solucion) {
        boolean[] coloresUsados = new boolean[numVertices + 1];
        int totalColores = 0;
        // Itera sobre la solución, marca los colores ya utilizados y cuenta el número de apariciones
        for (int color : solucion) {
            if (!coloresUsados[color]) {
                coloresUsados[color] = true;
                totalColores++;
            }
        }
        return totalColores;
    }

    /**
     * Genera una solución vecina a partir de la solución actual
     * 
     * @param solucionActual Un arreglo de enteros que representan los colores de la
     *                       solución actual
     * @return Un arreglo de enteros que representan los colores de la solución
     *         vecina
     */
    private int[] generarSolucionVecina(int[] solucionActual) {
        Random rand = new Random();
        int[] solucionVecina = solucionActual.clone();
        // Seleccionamos un vértice aleatorio
        int vertice = rand.nextInt(numVertices);
    
        boolean[] coloresUsados = new boolean[numVertices + 1];
        for (int i = 0; i < numVertices; i++) {
            if (matrizAdyacencia[vertice][i] == 1) {
                coloresUsados[solucionActual[i]] = true;
            }
        }
    
        // Nueva estructura para almacenar la frecuencia de colores y el color
        int[][] frecuenciasColores = new int[numVertices + 1][2];
        for (int i = 1; i <= numVertices; i++) {
            frecuenciasColores[i][0] = i; // Color
            frecuenciasColores[i][1] = 0; // Frecuencia
        }
        
        // Contar la frecuencia de cada color en la solución actual
        for (int color : solucionActual) {
            if (color != 0) {
                frecuenciasColores[color][1]++;
            }
        }
    
        // Ordenar colores por su frecuencia de mayor a menor
        java.util.Arrays.sort(frecuenciasColores, (a, b) -> Integer.compare(b[1], a[1]));
    
        // Intentar asignar el color más frecuente, luego el segundo más frecuente, y así sucesivamente
        for (int i = 0; i < numVertices; i++) {
            int colorCandidato = frecuenciasColores[i][0];
            if (!coloresUsados[colorCandidato]) {
                solucionVecina[vertice] = colorCandidato;
                break;
            }
        }
    
        return solucionVecina;
    }
    

    /**
     * Utiliza búsqueda por escalada para encontrar la solución más óptima
     * 
     * @param toleranciaMaxima El número de iteraciones sin mejora que se permiten
     * @return Un arreglo de enteros que representan los colores de la solución más
     *         óptima
     */
    public int[] busquedaPorEscalada(int toleranciaMaxima) {
        // Partimos de una solcuón aleatoria
        int[] solucionActual = solucionAleatoria();
        // Evaluamos la solución
        int evaluacionActual = evaluarSolucion(solucionActual);

        // Inicializamos los contadores
        int tolerancia = 0;
        int iteracionesMejoran = 0;

        // Mientras no se alcance la tolerancia máxima
        while (tolerancia < toleranciaMaxima) {
            // Generamos una solución vecina
            int[] nuevaSolucion = generarSolucionVecina(solucionActual);
            int nuevaEvaluacion = evaluarSolucion(nuevaSolucion);
            // Si la solución vecina es mejor, la actualizamos
            if (nuevaEvaluacion < evaluacionActual) {
                solucionActual = nuevaSolucion;
                evaluacionActual = nuevaEvaluacion;
                tolerancia = 0;
                iteracionesMejoran++;
            } else {
                // No es mejor, entonces aumentamos la tolerancia
                tolerancia++;
            }
        }
        // Escribimos la solución en un archivo de salida
        escribeSolucion(solucionActual, iteracionesMejoran, tolerancia);

        return solucionActual;
    }
    

    /**
     * Utiliza búsqueda por escalada para encontrar la solución más óptima, en este caso se
     * requiere una solución inicial, puesto que no la genera aleatoriamente.
     * 
     * @param solucionInicial La solución inicial a partir de la cual se inicia la búsqueda
     * @param toleranciaMaxima El número de iteraciones sin mejora que se permiten
     * @return Un arreglo de enteros que representan los colores de la solución más
     *         óptima
     */
    public int[] busquedaPorEscaladaConInicial(int[] solucionInicial, int toleranciaMaxima) {
        // Clonamos la solución ingresada
        int[] solucionActual = solucionInicial.clone();
        int evaluacionActual = evaluarSolucion(solucionActual);
        
        int tolerancia = 0;
        // Realiza lo mismo que busquedaPorEscalada, pero con la solución inicial
        while (tolerancia < toleranciaMaxima){
            int[] nuevaSolucion = generarSolucionVecina(solucionActual);
            int nuevaEvaluacion = evaluarSolucion(nuevaSolucion);
            if (nuevaEvaluacion < evaluacionActual) {
                solucionActual = nuevaSolucion;
                evaluacionActual = nuevaEvaluacion;
                tolerancia = 0;
            } else {
                tolerancia++;
            }
        }
    
        return solucionActual;
    }

    
    /**
     * Utiliza búsqueda local iterada para encontrar la solución más óptima
     * @param numIteraciones Criterio para que se detenga la búsqueda local iterada
     * @param toleranciaMaxima Criterio para que se detenga la búsqueda por escalada
     * @return Un arreglo de enteros que representan los colores de la solución más
     *        óptima
     */
    public int[] busquedaLocalIterada(int numIteraciones, int toleranciaMaxima) {
        // Inicia con una solución aleatoria
        int[] mejorSolucion = solucionAleatoria();
        int mejorEvaluacion = evaluarSolucion(mejorSolucion);
    
        for (int i = 0; i < numIteraciones; i++) {
            // Perturbamos la mejor solución actual
            int[] solucionPerturbada = perturbarSolucion(mejorSolucion);
            
            // Realizamos la búsqueda por escalada (Búsqueda local) con la solución perturbada
            int[] solucionOptimizada = busquedaPorEscaladaConInicial(solucionPerturbada, toleranciaMaxima);
            int evaluacionOptimizada = evaluarSolucion(solucionOptimizada);
            
            // Compara y actualiza la mejor solución si se encuentra una mejor
            if (evaluacionOptimizada < mejorEvaluacion) {
                mejorSolucion = solucionOptimizada;
                mejorEvaluacion = evaluacionOptimizada;
            }
        }
    
        // Escribimos la mejor solución en un archivo de salida
        escribeSolucion(mejorSolucion, numIteraciones, toleranciaMaxima);
    
        return mejorSolucion;
    }
    
    
    /**
     * Perturba la solución actual cambiando de color un porcentaje de vértices
     * 
     * @param solucion La solución actual que se perturbará
     * @return Un arreglo de enteros que representa la solución perturbada
     */
    private int[] perturbarSolucion(int[] solucion) {
        // Comenzamos clonando la solución ingresada
        int[] solucionPerturbada = solucion.clone();
        Random rand = new Random();

        // Creamos una lista para registrar todos los vértices de la gráfica
        List<Integer> vertices = new ArrayList<>(solucionPerturbada.length);
        for (int i = 0; i < solucionPerturbada.length; i++) {
            vertices.add(i);
        }

        // Mezclamos aleatoriamente los vértices para seleccionarlos al azar
        Collections.shuffle(vertices, rand);

        // Seleccionamos el 30% de los vértices para cambiarles el color, asegurando que sean distintos
        for (int i = 0; i < solucionPerturbada.length / 30; i++) {
            int vertice = vertices.get(i);

            // Asignamos un nuevo color válido para el vértice seleccionado
            solucionPerturbada[vertice] = encontrarColorValido(vertice, solucionPerturbada);
        }

        return solucionPerturbada;
    }

    
    /**
     * Encuentra un color válido para un vértice, es decir, un color que no esté
     * siendo utilizado por sus vértices adyacentes
     * @param vertice El vértice para el cual se busca un color válido
     * @param solucion La solución actual
     * @return 
     */
    private int encontrarColorValido(int vertice, int[] solucion) {
        Random rand = new Random();
        // Creamos un arreglo de valores booleanos para registrar los colores utilizados
        boolean[] coloresInvalidos = new boolean[numVertices + 1];
        
        // Marcar los colores de los vértices adyacentes como no válidos
        for (int i = 0; i < numVertices; i++){
            // Si son adyacentes, el color no es válido
            if (matrizAdyacencia[vertice][i] == 1) { 
                coloresInvalidos[solucion[i]] = true;
            }
        }
    
        // Encontrar un color válido (no usado por ningún adyacente)
        int color;
        do {
            color = 1 + rand.nextInt(numVertices);
        } while (coloresInvalidos[color]);
    
        return color;
    }
    
    
}
