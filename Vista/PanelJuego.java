package Vista;

import Controlador.ControladorJuego;
import Modelo.EstadoJuego;
import Modelo.FantasmaNaranja;
import Modelo.FantasmaRojo;
import Modelo.FantasmaRosado;
import Modelo.Laberinto;
import Modelo.Pacman;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

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
    private FantasmaRojo fantasmaRojo;
    private FantasmaNaranja fantasmaNaranja;
    private FantasmaRosado fantasmaRosado;
    
    private EstadoJuego estado;
    private Timer gameLoop;

    final int TAMANIO_CELDA = 35;
    final int COLUMNAS = 18;
    final int FILAS = 21;

    //Constructor
    public PanelJuego(){ 

    estado = EstadoJuego.INICIO; //Inicializar estado de juego    
        
    laberinto = new Laberinto(1);
    pacman = new Pacman (1,1);
    fantasmaRojo = new FantasmaRojo(1, 2, laberinto);
    fantasmaNaranja = new FantasmaNaranja(1, 8, laberinto);
    fantasmaRosado = new FantasmaRosado(1, 14, laberinto);
    Thread hiloRojo = new Thread(fantasmaRojo);
    Thread hiloNaranja = new Thread(fantasmaNaranja);
    Thread hiloRosado = new Thread(fantasmaRosado);

    hiloRojo.start();
    hiloNaranja.start();
    hiloRosado.start();
    addKeyListener(new ControladorJuego(pacman, this));
    setFocusable(true);
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(new Dimension(COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA)));
  
    imgFantasmaNaranja = new ImageIcon("Recursos/FantasmaNaranja.png").getImage();
    imgFantasmaRojo = new ImageIcon ("Recursos/FantasmaRojo.png").getImage();
    imgFantasmaRosado = new ImageIcon("Recursos/FantasmaRosado.png").getImage();
    imgPacmanAbajo = new ImageIcon("Recursos/pacmanAbajo.png").getImage();
    imgPacmanArriba = new ImageIcon("Recursos/pacmanArriba.png").getImage();
    imgPacmanIzquierda = new ImageIcon("Recursos/pacmanIzquierda.png").getImage();
    imgPacmanDerecha = new ImageIcon("Recursos/pacmanDerecha.png").getImage();

    gameLoop = new Timer(200, e ->{
        if (estado == EstadoJuego.JUGANDO){
            pacman.mover(laberinto);
            verificarColisiones();
            if (laberinto.contarPellets() == 0) {
                subirNivel();
            }
            repaint();
        }
    });
    gameLoop.start();
    requestFocusInWindow();
} 

//Getter y setter del estado del juego
    public EstadoJuego getEstado (){
        return estado;
    }

    public void setEstado(EstadoJuego estado){
        this.estado = estado;
        repaint();
    }

    public void reiniciar() {
    laberinto = new Laberinto(1);
    pacman = new Pacman(1, 1);
    estado = EstadoJuego.INICIO;
    repaint();
    }

    private int nivelActual = 1;

    public void subirNivel() {
        nivelActual++;
        if (nivelActual > 3) {
            estado = EstadoJuego.VICTORIA;
        } else {
            laberinto = new Laberinto(nivelActual);
            pacman = new Pacman(1, 1);
            repaint();
        }
    }

   private void verificarColisiones() {
    if (pacman.getFila() == fantasmaRojo.getFila()
            && pacman.getColumna() == fantasmaRojo.getColumna()) {
        pacman.perderVida();
        pacman.reiniciarPosicion();
    }

    if (pacman.getFila() == fantasmaNaranja.getFila()
            && pacman.getColumna() == fantasmaNaranja.getColumna()) {
        pacman.perderVida();
        pacman.reiniciarPosicion();
    }

    if (pacman.getFila() == fantasmaRosado.getFila()
            && pacman.getColumna() == fantasmaRosado.getColumna()) {
        pacman.perderVida();
        pacman.reiniciarPosicion();
    }

    // revisar vidas
    if (pacman.getVidas() <= 0) {
        estado = EstadoJuego.GAME_OVER;
        gameLoop.stop(); // detener el juego
    
    }
}

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (estado == EstadoJuego.INICIO) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA);

        //Título Pantalla Principal
        g.setColor(new Color(255, 255, 102));
        g.setFont(new Font("Courier New", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String titulo = "PAC-MAN";
        int xTitulo = (COLUMNAS * TAMANIO_CELDA - fm.stringWidth(titulo)) / 2; //Para centrar el texto
        g.drawString(titulo, xTitulo, 220);

        //Subtítulo
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.PLAIN, 20));
        fm = g.getFontMetrics();
        String sub = "PRESIONA ENTER PARA JUGAR";
        int xSub = (COLUMNAS * TAMANIO_CELDA -fm.stringWidth(sub)) /2; //centrado
        g.drawString(sub, xSub, 300);

    } else if (estado == EstadoJuego.JUGANDO) {

        //Dibujo mapa
        int[][] mapa = laberinto.getMapa();

        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                int celda = mapa[fila][columna];
                int x = columna * TAMANIO_CELDA;
                int y = fila * TAMANIO_CELDA;

                if (celda == Laberinto.PARED) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                } else if (celda == Laberinto.PELLET) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                    g.setColor(Color.WHITE);
                    g.fillOval(x + 12, y + 12, 6, 6);
                } else if (celda == Laberinto.POWERUP) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                    g.setColor(Color.CYAN);
                    g.fillOval(x + 6, y + 6, 15, 15);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                }
            }
        } 

        // Dibujar Pac-Man
        int px = pacman.getColumna() * TAMANIO_CELDA;
        int py = pacman.getFila() * TAMANIO_CELDA;

        if (pacman.getDireccionFila() == 1) {
            g.drawImage(imgPacmanAbajo, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
        } else if (pacman.getDireccionFila() == -1) {
            g.drawImage(imgPacmanArriba, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
        } else if (pacman.getDireccionColumna() == 1) {
            g.drawImage(imgPacmanDerecha, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
        } else if (pacman.getDireccionColumna() == -1) {
            g.drawImage(imgPacmanIzquierda, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
        } else {
            g.drawImage(imgPacmanDerecha, px, py, TAMANIO_CELDA, TAMANIO_CELDA, null);
        }

        // Dibujar fantasmas
        g.drawImage(imgFantasmaRojo, fantasmaRojo.getColumna() * TAMANIO_CELDA, fantasmaRojo.getFila() * TAMANIO_CELDA, TAMANIO_CELDA, TAMANIO_CELDA, null);
        g.drawImage(imgFantasmaNaranja, fantasmaNaranja.getColumna() * TAMANIO_CELDA, fantasmaNaranja.getFila() * TAMANIO_CELDA, TAMANIO_CELDA, TAMANIO_CELDA, null);
        g.drawImage(imgFantasmaRosado, fantasmaRosado.getColumna() * TAMANIO_CELDA, fantasmaRosado.getFila() * TAMANIO_CELDA, TAMANIO_CELDA, TAMANIO_CELDA, null);

        verificarColisiones();

    } else if (estado == EstadoJuego.PAUSA) {
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("PAUSA", 200, 300);

    } else if (estado == EstadoJuego.GAME_OVER) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("GAME OVER", 150, 300);

    } else if (estado == EstadoJuego.VICTORIA) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA);
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("¡GANASTE!", 160, 300);
    }
}

@Override
public void addNotify() {
    super.addNotify();
    requestFocusInWindow();
}

}