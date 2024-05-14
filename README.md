# Tarea 03: Metaheurísticas de Trayectoria

## Integrantes del equipo:

- **Ángeles Sánchez Aldo Javier 320286144**
- **Jurado Guadalupe Aldo Emilio 320025255**

## Requisitos

Este proyecto usa maven. <br>
**Instalación desde terminal:**

### En fedora:

```bash
sudo dnf install maven
```

### En ubuntu:

```bash
sudo apt install maven
```

Adicionalmente ya se debe tener java con su **jdk**.
De no tenerlo, en fedora se instala de la siguiente forma:

```bash
sudo dnf install java-devel

```

## Compilar y Ejecutar

Para compilar de forma limpia:

```bash
mvn clean install
```

Para ejecutar el programa (después de compilar):

```bash
java -jar target/ejecuta.jar
```

## Limpieza

Adicionalmente para limpiar los archivos generados por la compilación:

```bash
mvn clean
```

## Comentarios

# Sobre la interfaz 

El programa cuenta con una pequeña interfaz de texto para ir seleccionando
las distintas opciones para la ejecución de los algoritmos.
Los parámetros se pasan después de la ejecución.

Para realizar la ejecución del recocido simulado y búsqueda aleatoria:
En los menús primero se debe selecciónar:
 Problema de optimización contínua índice (2)
    Luego se selecciona recocido simulado o búsqueda aleatoria (1 o 2 respectivamente) 
    luego se selecciona la función de evaluación (1, ... , 8)
    y por último se escribe como parámetro la dimensión deseada

Se imprimirán 10 soluciones generadas por el algoritmo seleccionado (en dicha dimensión) y se guardarán en archivos de texto.

Los métodos para generar de forma masiva las soluciones se encuentran comentados en OptimizacionCombinatoria.java, debido a que estos solo
fueron usados para generar los archivos de soluciones.

