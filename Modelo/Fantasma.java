package Modelo;

public abstract class Fantasma implements Runnable {

    protected int fila;
    protected int columna;
    protected int direccionFila;
    protected int direccionColumna;
    protected Laberinto laberinto;
    protected boolean vivo;

    public Fantasma (int filaInicial, int columnaInicial, Laberinto laberinto){
        this.fila = filaInicial;
        this.columna = columnaInicial;
        this.laberinto = laberinto;
        this.vivo = true;
        this.direccionFila = 0;
        this.direccionColumna = 1;
    }

    public abstract void mover(); //fantasmas se mueven diferente

    @Override
    public void run(){
        while (vivo) {
            mover();
            try {
                Thread.sleep(300); //velocidad cada fantasma
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getFila() {return fila;}
    public int getColumna () {return columna;}
    public void detener () {vivo = false;}
}
