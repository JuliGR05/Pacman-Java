package Modelo;

public class Pacman extends Personaje implements Runnable {
    
    private Laberinto laberinto;
    private ScoreModel scoreModel;
    private int vidas;
    private volatile boolean vivo = true;
    private volatile boolean invulnerable = false;
    private volatile long tiempoFinInvulnerable = 0;
    private ModeloJuego modelo;

    //Constructor 
    public Pacman (int filaInicial, int columnaInicial){
        super(filaInicial, columnaInicial);
        vidas = 3;
    }

    //Métodos 
    public synchronized void setDireccion(int fila, int columna){
    direccionFila = fila;
    direccionColumna = columna;
}

    public void setContexto(Laberinto laberinto, ScoreModel scoreModel, ModeloJuego modelo) {
        this.laberinto = laberinto;
        this.scoreModel = scoreModel;
        this.modelo = modelo;
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
    public synchronized int getFila() {
        return fila;
    }

    public synchronized int getColumna() {
        return columna;
    }

    public synchronized int getDireccionFila() {
    return direccionFila;
    }

    public synchronized int getDireccionColumna() {
    return direccionColumna;
    }

public synchronized void perderVida() {
    vidas--;
}

public synchronized int getVidas() {
    return vidas;
}

public synchronized void reiniciarPosicion() {
    fila = filaInicial;
    columna = columnaInicial;

    direccionFila = 0;
    direccionColumna = 0;
}

    @Override
    public synchronized void mover(){
        if (laberinto != null && scoreModel != null){
            int nuevaFila = fila + direccionFila;
            int nuevaColumna = columna + direccionColumna;

            if (!laberinto.esPared(nuevaFila, nuevaColumna)){
                fila = nuevaFila;
                columna = nuevaColumna;

                int celda = laberinto.getCelda(fila, columna);
                if (celda == Laberinto.PELLET){
                    laberinto.setCelda(fila, columna, Laberinto.VACIO);
                    scoreModel.sumarPuntos(10);
            } else if (celda == Laberinto.CEREZA) {
                laberinto.setCelda(fila, columna, Laberinto.VACIO);
                scoreModel.sumarPuntos(100);
            } else if (celda == Laberinto.NARANJA) {
                laberinto.setCelda(fila, columna, Laberinto.VACIO);
                scoreModel.sumarPuntos(120);
                }
            }
        }
    }

    private volatile long velocidad = 210; //valor por defecto de la velocidad
    public void setVelocidad(long velocidad){
        this.velocidad= velocidad;
    }

    @Override
    public void run() {

        while (vivo) {

            if (laberinto != null && scoreModel != null) {
                mover();
                if (modelo != null){
                    modelo.verificarColisionEnMovimiento(fila, columna);
                }
            }

            try {
                Thread.sleep(velocidad);
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
