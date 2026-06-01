package Vista;

import Controlador.ControladorJuego;
import Modelo.Laberinto;
import Modelo.Pacman;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelJuego extends JPanel{

    private Laberinto laberinto;
    private Pacman pacman;
    private Image imgFantasmaNaranja;
    private Image imgFantasmaRosado;
    private Image imgFantasmaRojo;
    private Image imgPacmanAbajo;
    private Image imgPacmanArriba;
    private Image imgPacmanIzquierda;
    private Image imgPacmanDerecha;


    final int TAMANIO_CELDA = 35;
    final int COLUMNAS = 18;
    final int FILAS = 21;

   public PanelJuego(){
    laberinto = new Laberinto(1);
    pacman = new Pacman (1,1);
    addKeyListener(new ControladorJuego(null));
    setFocusable(true);
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(new Dimension(COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA)));
  
    imgFantasmaNaranja = new ImageIcon("Recursos/FantasmaNaranja.png").getImage();
    imgFantasmaRojo = new ImageIcon ("Recursos/FantasmaRojo.png").getImage();
    imgFantasmaRosado = new ImageIcon("Recursos/FantasmaRosado.png").getImage();
    imgPacmanAbajo = new ImageIcon("Recursos/pacmanAbajo.png").getImage();
    imgPacmanArriba = new ImageIcon("Recursos/pacmanArriba.png").getImage();
    imgPacmanIzquierda = new ImageIcon("Recursos/pacmanIzquierda").getImage();
    imgPacmanDerecha = new ImageIcon("Recursos/pacmanDerecha").getImage();
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
                g.fillOval(x + 12, y + 12 , 6, 6); // pellet centrado
           
            } else if (celda == Laberinto.POWERUP) {
                g.setColor(Color.BLACK);
                g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                g.setColor(Color.CYAN);
                g.fillOval(x + 6, y + 6, 15, 15); // Powerup
           
            } else {
                g.setColor(Color.BLACK);
                g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
            }
            
        }
    }

    //Dibujar Pacman
    int px = pacman.getColumna() * TAMANIO_CELDA;
    int py = pacman.getFila() * TAMANIO_CELDA;

    if (pacman.getDireccionFila() == 1){
        g.drawImage(imgPacmanAbajo, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
    } else if (pacman.getDireccionFila() == -1){
        g.drawImage(imgPacmanArriba, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
    } else if (pacman.getDireccionColumna() == 1){
        g.drawImage(imgPacmanDerecha, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
    } else if (pacman.getDireccionColumna() == -1) {
        g.drawImage(imgPacmanIzquierda, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
    } else {
        g.drawImage(imgPacmanDerecha, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null); //posición por defecto
    }
    }
    }

