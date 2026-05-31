package Modelo;
import java.util.Random;

public class FantasmaRojo {
    private Random random = new Random ();
    
    public FantasmaNaranja(int filaInicial, int columnaInicial, Laberinto laberinto) {
        super (filaInicial, columnaInicial, laberinto); //llama constructor clase padre
    }

    @Override
    public void mover() {
        int[][] direcciones = {{0,1},{0,-1},{1,0},{-1,0}};
        int [] dir = direcciones [random.nextInt(4)]; //escoge una dirección alaetoria del arreglo

        int nuevaFila = fila + dir[0];
        int nuevaColumna = columna + dir [1];

        if (!labeinto.esPared(nuevaFila, nuevaColumna)) { //verifica que no haya pared para moverse
            columna = nuevaColumna;
        }
    }
    
}
