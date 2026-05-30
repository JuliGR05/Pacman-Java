package Modelo;

public class Pacman {
  
    private int fila;
    private int columna;

    private int direccionFila;
    private int direccionColumna;

    private int vidas;

    public Pacman (int filaInicial, int columnaInicial){
        this.fila = filaInicial;
        this.columna = columnaInicial;
        this.vidas = 3;

        direccionFila = 0;
        direccionColumna = 0;
    }

    public void mover (Laberinto laberinto){
        int nuevaFila = fila + direccionFila;
        int nuevaColumna = columna + direccionColumna;

        if (!laberinto.esPared(nuevaFila,nuevaColumna)){
            fila = nuevaFila;
            columna = nuevaColumna;
        }
    }

    public void setDireccion(int fila, int columna){
    direccionFila = fila;
    direccionColumna = columna;
}

// Getters

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
}
}
