package Vista;

import Controlador.ControladorJuego;
import Modelo.EstadoJuego;
import Modelo.Laberinto;
import Modelo.ModeloJuego;
import Modelo.Pacman;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelJuego extends JPanel {

    private ModeloJuego modelo;
    private Timer gameLoop;

    private Image imgFantasmaNaranja;
    private Image imgFantasmaRosado;
    private Image imgFantasmaRojo;
    private Image imgFantasmaVulnerable;
    private Image imgPacmanAbajo;
    private Image imgPacmanArriba;
    private Image imgPacmanIzquierda;
    private Image imgPacmanDerecha;
    private Image imgCereza;
    private Image imgNaranja;
    private boolean bocaAbierta = true;

    final int TAMANIO_CELDA = 35;
    final int COLUMNAS = 18;
    final int FILAS = 21;

    public PanelJuego() {
        modelo = new ModeloJuego();

        addKeyListener(new ControladorJuego(modelo, this));
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA));

        imgFantasmaNaranja = new ImageIcon("Recursos/FantasmaNaranja.png").getImage();
        imgFantasmaRojo = new ImageIcon("Recursos/FantasmaRojo.png").getImage();
        imgFantasmaRosado = new ImageIcon("Recursos/FantasmaRosado.png").getImage();
        imgFantasmaVulnerable = new ImageIcon("Recursos/fantasmaVulnerable.png").getImage();
        imgPacmanAbajo = new ImageIcon("Recursos/pacmanAbajo.png").getImage();
        imgPacmanArriba = new ImageIcon("Recursos/pacmanArriba.png").getImage();
        imgPacmanIzquierda = new ImageIcon("Recursos/pacmanIzquierda.png").getImage();
        imgPacmanDerecha = new ImageIcon("Recursos/pacmanDerecha.png").getImage();
        imgCereza = new ImageIcon ("Recursos/Cereza.png").getImage();
        imgNaranja = new ImageIcon ("Recursos/Naranja.png").getImage();

        gameLoop = new Timer(150, e -> {
            bocaAbierta = !bocaAbierta;
            modelo.actualizarJuego();
            repaint();
        });
        gameLoop.start();
    }

    public EstadoJuego getEstado() { return modelo.getEstado(); }
    public void setEstado(EstadoJuego estado) { modelo.setEstado(estado); }
    public void reiniciar() { modelo.reiniciar(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (modelo.getEstado() == EstadoJuego.INICIO) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA);
            g.setColor(new Color(255, 255, 102));
            g.setFont(new Font("Courier New", Font.BOLD, 48));
            FontMetrics fm = g.getFontMetrics();
            String titulo = "PAC-MAN";
            int xTitulo = (COLUMNAS * TAMANIO_CELDA - fm.stringWidth(titulo)) / 2;
            g.drawString(titulo, xTitulo, 220);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            fm = g.getFontMetrics();
            String sub = "PRESIONA ENTER PARA JUGAR";
            int xSub = (COLUMNAS * TAMANIO_CELDA - fm.stringWidth(sub)) / 2;
            g.drawString(sub, xSub, 300);

        } else if (modelo.getEstado() == EstadoJuego.JUGANDO) {
            int[][] mapa = modelo.getLaberinto().getMapa();

            for (int fila = 0; fila < FILAS; fila++) {
                for (int columna = 0; columna < COLUMNAS; columna++) {
                    int celda = mapa[fila][columna];
                    int x = columna * TAMANIO_CELDA;
                    int y = fila * TAMANIO_CELDA;

                    if (celda == modelo.getLaberinto().PARED) {
                        g.setColor(Color.BLUE);
                        g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                    } else if (celda == modelo.getLaberinto().PELLET) {
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                        g.setColor(Color.WHITE);
                        g.fillOval(x + 12, y + 12, 6, 6);
                    } else if (celda == modelo.getLaberinto().POWERUP) {
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                        g.setColor(Color.CYAN);
                        g.fillOval(x + 6, y + 6, 15, 15);
                    } else if (celda == Laberinto.CEREZA) {
                        g.drawImage (imgCereza, x, y, TAMANIO_CELDA, TAMANIO_CELDA, null);
                    } else if (celda == Laberinto.NARANJA) {
                        g.drawImage (imgNaranja, x, y, TAMANIO_CELDA, TAMANIO_CELDA, null);
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, TAMANIO_CELDA, TAMANIO_CELDA);
                    }
                }
            }

            
            // Dibujar Pac-Man
            int px = modelo.getPacman().getColumna() * TAMANIO_CELDA;
            int py = modelo.getPacman().getFila() * TAMANIO_CELDA;
            Pacman pacman = modelo.getPacman();
            boolean dibujar = true;

            if (pacman.isInvulnerable()) {
            g.setColor(new Color(255, 255, 255, 100));
            g.fillOval(px - 5, py - 5, TAMANIO_CELDA + 10, TAMANIO_CELDA + 10);
        }

            if (pacman.isInvulnerable()){
            dibujar = (System.currentTimeMillis() / 100) % 2 == 0; //divide el tiempo en bloques de 100 ms para alternar
        }
            if (dibujar) {
            if (!bocaAbierta) {
            g.setColor(Color.YELLOW);
            g.fillOval(px + 2, py + 2, TAMANIO_CELDA - 4, TAMANIO_CELDA - 4);
            } else {
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
    }
}

            // Dibujar fantasmas
            dibujarFantasma(g, modelo.getFantasmaRojo(), imgFantasmaRojo);
            dibujarFantasma(g, modelo.getFantasmaNaranja(), imgFantasmaNaranja);
            dibujarFantasma(g, modelo.getFantasmaRosado(), imgFantasmaRosado);

            // Mostrar puntaje
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 16));
            g.drawString("PUNTOS: " + modelo.getScoreModel().getPuntos(), 10, FILAS * TAMANIO_CELDA - 5);

            //Mostrar vidas
            for (int i = 0; i < pacman.getVidas(); i++){
                g.drawImage(imgPacmanDerecha, 10 +(i*35), 5, 30, 30, null);
            }
        } 
        else if (modelo.getEstado() == EstadoJuego.SIGUIENTE_NIVEL) {

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Courier New", Font.BOLD, 38));
        g.drawString("¡NIVEL COMPLETADO!", 110, 180);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.PLAIN, 22));
        g.drawString("Puntos: " + modelo.getScoreModel().getPuntos(), 170, 250);

        g.drawString("Has completado el nivel " + modelo.getNivelCompletado(), 140, 220);

        //Botones CONTINUAR y SALIR
        g.drawRect(150, 320, 320, 50);
        g.drawString("ENTER - Continuar", 195, 352);

        g.drawRect(150, 390, 320, 50);
        g.drawString("ESC - Salir", 235, 422);

       
    }

         else if (modelo.getEstado() == EstadoJuego.PAUSA) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Courier New", Font.BOLD, 36));
            g.drawString("PAUSA", 200, 300);

        } else if (modelo.getEstado() == EstadoJuego.GAME_OVER) {
            g.setColor(new Color (0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 48));
            FontMetrics fm = g.getFontMetrics();
            String texto = "GAME OVER";
            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            g.drawString(texto, x, 220);

            g.setColor(Color.WHITE);
            g.setFont(new Font ("Courier New", Font.PLAIN, 24));
            g.drawString ("Puntaje final: " + modelo.getScoreModel().getPuntos(), 180, 290);

            //Botones SALIR y REINICIAR
            g.drawString("Presiona R para reintentar", 150, 360);
            g.drawString("Presiona ESC para salir del juego", 105, 400);

        } else if (modelo.getEstado() == EstadoJuego.VICTORIA) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA);
            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 36));
            g.drawString("¡GANASTE!", 160, 300);

            g.setColor(Color.WHITE);
            g.setFont (new Font ("Courier New", Font.PLAIN, 24));
            g.drawString("Puntaje final: " + modelo.getScoreModel().getPuntos(), 170, 250);
            g.drawString("Gracias por jugar :) ", 190, 320);
            g.drawString("R - Jugar nuevamente", 160, 390);

        }
    }

    private void dibujarFantasma(Graphics g, Modelo.Fantasma fantasma, Image imgNormal) {
        int fx = fantasma.getColumna() * TAMANIO_CELDA;
        int fy = fantasma.getFila() * TAMANIO_CELDA;
        if (fantasma.isVulnerable()) {
            g.drawImage(imgFantasmaVulnerable, fx, fy, TAMANIO_CELDA, TAMANIO_CELDA, null);
        } else {
            g.drawImage(imgNormal, fx, fy, TAMANIO_CELDA, TAMANIO_CELDA, null);
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
