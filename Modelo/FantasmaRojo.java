package Modelo;

import java.util.Random;

public class FantasmaRojo extends Fantasma {

    private Random random = new Random();
    private Pacman pacman;

    public FantasmaRojo(int filaInicial, int columnaInicial, Laberinto laberinto, Pacman pacman) {
        super(filaInicial, columnaInicial, laberinto);
        this.pacman = pacman;
    }

    @Override
    public void mover() {
        int targetFila = pacman.getFila();
        int targetColumna = pacman.getColumna();

        int dFila = 0, dColumna = 0;

        if (Math.abs(targetFila - fila) > Math.abs(targetColumna - columna)) {
            dFila = (targetFila > fila) ? 1 : -1;
        } else {
            dColumna = (targetColumna > columna) ? 1 : -1;
        }

        if (!laberinto.esPared(fila + dFila, columna + dColumna)) {
            fila += dFila;
            columna += dColumna;
        } else {
            // si hay pared intenta la otra dirección
            int[][] direcciones = {{0,1},{0,-1},{1,0},{-1,0}};
            Random random = new Random();
            int[] dir = direcciones[random.nextInt(4)];
            if (!laberinto.esPared(fila + dir[0], columna + dir[1])) {
                fila += dir[0];
                columna += dir[1];
            }
        }
    }
}

