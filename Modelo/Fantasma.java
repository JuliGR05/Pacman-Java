package Modelo;

public abstract class Fantasma implements Runnable {

    protected int fila;
    protected int columna;
    protected int direccionFila;
    protected int direccionColumna;
    protected Laberinto laberinto;
    protected boolean vivo;
    protected int filaInicial;
    protected int columnaInicial;

    public Fantasma (int filaInicial, int columnaInicial, Laberinto laberinto){
        this.fila = filaInicial;
        this.columna = columnaInicial;
        this.laberinto = laberinto;
        this.filaInicial = filaInicial;
        this.columnaInicial = columnaInicial;
        this.vivo = true;
        this.direccionFila = 0;
        this.direccionColumna = 1;
    }

    public abstract void mover(); //se mueven los fantasmas
        
       public void reiniciarPosicion(){
        fila = filaInicial;
        columna = columnaInicial;
       }
    

    @Override
    public void run(){
        while (vivo) {
            mover();
            try {
                Thread.sleep(150); //velocidad cada fantasma
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getFila() {return fila;}
    public int getColumna () {return columna;}
    public void detener () {vivo = false;}

    protected boolean vulnerable = false;
    public void setVulnerable(boolean v) {
        vulnerable = v;
    }

    public boolean isVulnerable() {
        return vulnerable;
    }
}
