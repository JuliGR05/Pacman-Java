package Modelo;
import java.util.Random;

public abstract class Fantasma extends Personaje implements Runnable {

    protected Laberinto laberinto;
    protected volatile boolean vivo;

    //Constructor
    public Fantasma (int filaInicial, int columnaInicial, Laberinto laberinto){
        super(filaInicial, columnaInicial);
        this.laberinto = laberinto;
        this.vivo = true;
        direccionFila = 0;
        direccionColumna = 1;
    }

    public abstract void mover(); //se mueven los fantasmas

    //Para que los fantasmas reaparezcan aleatoriamente después de mor
    public synchronized void respawnAleatorio(Pacman pacman) {
    Random random = new Random();
    int nuevaFila;
    int nuevaColumna;

    do {
        nuevaFila = random.nextInt(21);
        nuevaColumna = random.nextInt(18);
    } while (laberinto.esPared(nuevaFila, nuevaColumna)
    || Math.abs (nuevaFila - pacman.getFila()) < 3
    && Math.abs(nuevaColumna - pacman.getColumna()) < 3);

    fila = nuevaFila;
    columna = nuevaColumna;
    }


    protected long velocidad = 250; //valor por defecto de la velocidad
    @Override
    public void run(){
        while (vivo) {
            if (ModeloJuego.pausado) {
                try { Thread.sleep(50); } catch (InterruptedException e) { break; }
                continue;
            }
            mover();
            try {
                Thread.sleep(velocidad); //velocidad cada fantasma
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setVelocidad(long velocidad){
        this.velocidad = velocidad;
    }

    public synchronized  int getFila() {return fila;}
    public synchronized  int getColumna () {return columna;}
    public void detener () {vivo = false;}

    protected volatile boolean vulnerable = false;
    public void setVulnerable(boolean v) {
        vulnerable = v;
    }

    public boolean isVulnerable() {
        return vulnerable;
    }
}
