package Modelo;

// Clase abstracta que representa cualquier personaje del juego
// Define los atributos y comportamientos comunes de Pacman y los fantasmas

public abstract class Personaje {

    protected volatile int fila; //volatile para leer el valor real de la memoria
    protected volatile int columna;

    protected volatile int direccionFila;
    protected volatile int direccionColumna;

    protected volatile int filaInicial;
    protected volatile int columnaInicial;


    //Constructor
    public Personaje(int filaInicial, int columnaInicial) {

        this.fila = filaInicial;
        this.columna = columnaInicial;

        this.filaInicial = filaInicial;
        this.columnaInicial = columnaInicial;

        this.direccionFila = 0;
        this.direccionColumna = 0;
    }

    public abstract void mover();

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void reiniciarPosicion() {
        fila = filaInicial;
        columna = columnaInicial;
    }
}