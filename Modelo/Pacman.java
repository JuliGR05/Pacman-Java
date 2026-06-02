package Modelo;

public class Pacman {
  
    private int fila;
    private int columna;

    private int direccionFila;
    private int direccionColumna;

    private int vidas;
    private int filaInicial;
    private int columnaInicial;

    public Pacman (int filaInicial, int columnaInicial){
        this.fila = filaInicial;
        this.columna = columnaInicial;
        this.vidas = 3;
        this.filaInicial = filaInicial;
        this.columnaInicial = columnaInicial;

        direccionFila = 0;
        direccionColumna = 0;
    }

    public void mover (Laberinto laberinto){
        int nuevaFila = fila + direccionFila;
        int nuevaColumna = columna + direccionColumna;

        if (!laberinto.esPared(nuevaFila,nuevaColumna)){
            fila = nuevaFila;
            columna = nuevaColumna;

            //cuando hay pellet o powerup se lo come
            int celda = laberinto.getCelda(fila, columna);
            if (celda == Laberinto.PELLET || celda == Laberinto.POWERUP){
                laberinto.setCelda(fila,columna,Laberinto.VACIO);
            }
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

    public int getDireccionFila() {
    return direccionFila;
    }

    public int getDireccionColumna() {
    return direccionColumna;
    }

public void perderVida() {
    vidas--;
}

public int getVidas() {
    return vidas;
}

public void reiniciarPosicion() {
    fila = filaInicial;
    columna = columnaInicial;

    direccionFila = 0;
    direccionColumna = 0;
}
}
