package Modelo;

import java.util.Random;

public class FantasmaRojo extends Fantasma {

    private Random random = new Random();

    public FantasmaRojo(int filaInicial, int columnaInicial, Laberinto laberinto) {
        super(filaInicial, columnaInicial, laberinto);
    }

    @Override
    public void mover() {

        int[][] direcciones = {
            {0,1},
            {0,-1},
            {1,0},
            {-1,0}
        };

        int[] dir = direcciones[random.nextInt(4)];

        int nuevaFila = fila + dir[0];
        int nuevaColumna = columna + dir[1];

        if (!laberinto.esPared(nuevaFila, nuevaColumna)) {

            fila = nuevaFila;
            columna = nuevaColumna;
        }
    }
}

