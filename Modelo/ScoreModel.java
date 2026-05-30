javapackage Modelo;

public class ScoreModel {
    private int puntos;

    public ScoreModel() {
        puntos = 0;
    }

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
