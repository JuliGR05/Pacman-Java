package Modelo;

public abstract class Personaje {

    protected int fila;
    protected int columna;

    protected int direccionFila;
    protected int direccionColumna;

    protected int filaInicial;
    protected int columnaInicial;

    public Personaje(int filaInicial, int columnaInicial) {

        this.fila = filaInicial;
        this.columna = columnaInicial;

        this.filaInicial = filaInicial;
        this.columnaInicial = columnaInicial;

        this.direccionFila = 0;
        this.direccionColumna = 0;
    }

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