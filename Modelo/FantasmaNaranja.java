package Modelo;

import java.util.Random;

public class FantasmaNaranja extends Fantasma {

    private Random random = new Random();

    public FantasmaNaranja(int filaInicial, int columnaInicial, Laberinto laberinto) {
        super(filaInicial, columnaInicial, laberinto);
    }

    @Override
    public void mover() {
        int[][] direcciones = {{0,1},{0,-1},{1,0},{-1,0}};
        
         // intentar seguir una dirección de manera lineal
        int nuevaFila = fila + direccionFila;
        int nuevaColumna = columna + direccionColumna;

        if (!laberinto.esPared(nuevaFila, nuevaColumna)) {
            fila = nuevaFila;
            columna = nuevaColumna;
        } else {
            // al llegar a pared, escoger dirección aleatoria
            Random random = new Random();
            int[] dir = direcciones[random.nextInt(4)];
            direccionFila = dir[0];
            direccionColumna = dir[1];
        }
}
}
    
