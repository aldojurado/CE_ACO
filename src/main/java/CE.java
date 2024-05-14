import java.util.Scanner;

public class CE {
    final static String GREEN = "\u001B[32m";
    final static String BLANCO = "\u001B[0m";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        imprimeMenu(1);
        int opcion = escaneaNum(2);
        if (opcion == 1) {
            System.out.println("Ingrese el nombre del archivo: ");
            String rutaIngresada = scanner.next();
            Coloracion coloracion = new Coloracion(rutaIngresada);
            // Es posible cambiar el critero de término de ambas búsquedas, en este caso BLI
            // 100 iteraciones y BE 50 iteraciones.
            coloracion.busquedaLocalIterada(100, 50);
        } else {
            System.out.println("Opción inválida");
        }
    }

    public static int escaneaNum(int max) {
        int res = Integer.MAX_VALUE;

        while (res == Integer.MAX_VALUE) {
            Scanner scanner = new Scanner(System.in);
            try {
                int num = scanner.nextInt();
                if (num < 1 || num > max) {
                    res = Integer.MAX_VALUE;
                    System.out.println("Ingrese un índice válido:");
                } else {
                    res = num;
                }
            } catch (Exception e) {
                System.out.println("Se debe ingresar un número entero");
                res = Integer.MAX_VALUE;
                System.out.println("Ingrese un entero:");
            }

        }
        return res;
    }

    private static void imprimeMenu(int opcion) {
        if (opcion == 1) {
            System.out.println(GREEN + "Seleccione una opción:" + BLANCO);

            System.out.println("1. Problema de coloración BLI");
            System.out.println("2. Problema de optimización continua");
        } else if (opcion == 2) {
            System.out.println(GREEN + "Seleccione el tipo de optimización:" + BLANCO);
            System.out.println("1. Recocido simulado");
            System.out.println("2. Búsqueda aleatoria");
        } else if (opcion == 3) {
            System.out.println(GREEN + "Seleccione la función a evaluar:" + BLANCO);
            System.out.println("1. Sphere Function");
            System.out.println("2. Ackley Function");
            System.out.println("3. Griewank Function");
            System.out.println("4. Rastrigin Function");
            System.out.println("5. Rosenbrock Function");
            System.out.println("6. Sum Square Function");
            System.out.println("7. Styblinski-Tang Function");
            System.out.println("8. Dixon-Price Function");
        }
    }

}
