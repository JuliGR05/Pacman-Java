package Vista;

import Modelo.Laberinto;
import java.awt.*;
import javax.swing.JPanel;

public class PanelJuego extends JPanel{

    private Laberinto laberinto;

    final int TAMANIO_CELDA = 32;
    final int COLUMNAS = 18;
    final int FILAS = 21;

   public PanelJuego(){
    laberinto = new Laberinto(1);
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(new Dimension(COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA)));
   } 



       @Override
    protected  void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int[][] mapa = laberinto.getMapa(); //Hacer mapa según nivel
        
        for (int fila = 0; fila < FILAS; fila++){

        for (int columna = 0; columna < COLUMNAS; columna ++){
            int celda = mapa [fila][columna];
            int x = columna * TAMANIO_CELDA;
            int y = fila * TAMANIO_CELDA;

            if (celda == Laberinto.PARED) {
                g.setColor(Color.BLUE);
                g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);

            } else if (celda == Laberinto.PELLET){
                g.setColor(Color.BLACK);
                g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                g.setColor(Color.WHITE);
                g.fillOval(x + 12, y + 12 , 8, 8); // pellet centrado
           
            } else if (celda == Laberinto.POWERUP) {
                g.setColor(Color.BLACK);
                g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                g.setColor(Color.CYAN);
                g.fillOval(x + 6, y + 6, 20, 20); // Powerup
           
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
            }
            
        }
    }
}
}