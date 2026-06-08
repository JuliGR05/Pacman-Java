package Modelo;

public class ScoreModel {
    private int puntos;

    //Constructor
    public ScoreModel() {
        puntos = 0;
    }

    //Métodos
    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }

    public int getPuntos() {
        return puntos;
    }

    public void reiniciar() {
        puntos = 0;
    }
}
