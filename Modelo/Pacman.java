package Modelo;

public class Pacman extends Personaje implements Runnable {
    
    private Laberinto laberinto;
    private ScoreModel scoreModel;
    private int vidas;
    private boolean vivo = true;
    private boolean invulnerable = false;
    private long tiempoFinInvulnerable = 0;

    //Constructor 
    public Pacman (int filaInicial, int columnaInicial){
        super(filaInicial, columnaInicial);
        vidas = 3;
    }

    //Métodos 
    public void mover (Laberinto laberinto, ScoreModel score){
        int nuevaFila = fila + direccionFila;
        int nuevaColumna = columna + direccionColumna;

        if (!laberinto.esPared(nuevaFila,nuevaColumna)){
            fila = nuevaFila;
            columna = nuevaColumna;

            //cuando hay pellet o powerup se lo come
            int celda = laberinto.getCelda(fila, columna);
            if (celda == Laberinto.PELLET ){
                laberinto.setCelda(fila,columna,Laberinto.VACIO);
                score.sumarPuntos(10); //10 puntos por cada pellet
            } else if (celda == Laberinto.CEREZA){
                laberinto.setCelda(fila, columna, Laberinto.VACIO);
                score.sumarPuntos(100);
            } else if (celda == Laberinto.NARANJA){
                laberinto.setCelda(fila, columna, Laberinto.VACIO);
                score.sumarPuntos(120);
            }
        }
    }

    public void setDireccion(int fila, int columna){
    direccionFila = fila;
    direccionColumna = columna;
}

    public void setContexto(Laberinto laberinto, ScoreModel scoreModel) {
        this.laberinto = laberinto;
        this.scoreModel = scoreModel;
    }

    public boolean isInvulnerable() {
    return invulnerable;
    }

    public void activarInvulnerabilidad() {
        invulnerable = true;
        tiempoFinInvulnerable =
                System.currentTimeMillis() + 2500;
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

    @Override
    public void run() {

        while (vivo) {

            if (laberinto != null && scoreModel != null) {
                mover(laberinto, scoreModel);
            }

            try {
                Thread.sleep(210);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        if (invulnerable && System.currentTimeMillis() > tiempoFinInvulnerable){
            invulnerable = false;
            }
        }
    }

    public void detener() {
    vivo = false;
}

}
