package Vista;

import Controlador.ControladorJuego;
import Modelo.EstadoJuego;
import Modelo.Laberinto;
import Modelo.ModeloJuego;
import Modelo.Pacman;
import Modelo.PersistenciaPuntajes;
import java.awt.*;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    
    private JButton btnIniciar;
    private JButton btnSalir;
    private JButton btnContinuarNivel;
    private JButton btnSalirNivel;
    private JButton btnReintentar;
    private JButton btnSalirGameOver;
    private JButton btnContinuarPausa;
    private JButton btnSalirPausa;
    private JButton btnSalirVictoria;

    final int TAMANIO_CELDA = 35;
    final int COLUMNAS = 18;
    final int FILAS = 21;

    //CONSTRUCTOR
    public PanelJuego() {
        modelo = new ModeloJuego();

        addKeyListener(new ControladorJuego(modelo, this));
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA));

        imgFantasmaNaranja    = cargarImagen("/Recursos/FantasmaNaranja.png");
        imgFantasmaRojo       = cargarImagen("/Recursos/FantasmaRojo.png");
        imgFantasmaRosado     = cargarImagen("/Recursos/FantasmaRosado.png");
        imgFantasmaVulnerable = cargarImagen("/Recursos/FantasmaVulnerable.png");
        imgPacmanAbajo        = cargarImagen("/Recursos/pacmanAbajo.png");
        imgPacmanArriba       = cargarImagen("/Recursos/pacmanArriba.png");
        imgPacmanIzquierda    = cargarImagen("/Recursos/pacmanIzquierda.png");
        imgPacmanDerecha      = cargarImagen("/Recursos/pacmanDerecha.png");
        imgCereza             = cargarImagen("/Recursos/Cereza.png");
        imgNaranja            = cargarImagen("/Recursos/Naranja.png");

    gameLoop = new Timer(150, e -> {
            bocaAbierta = !bocaAbierta;
            modelo.actualizarJuego();
            repaint();
        });
        gameLoop.start();

        configurarMenuInicio();
        configurarBotonesNivel();
        configurarBotonesGameOver();
        configurarBotonesPausa();
    } 

    //Métodos
    private Image cargarImagen(String ruta) { //Método para cargar imágenes desde la raíz del classpath
    java.net.URL url = getClass().getResource(ruta);
    if (url == null) {
        System.err.println("Advertencia: no se encontró la imagen " + ruta);
        return null;
    }
    return new ImageIcon(url).getImage();
    }

    private void configurarMenuInicio() {
    setLayout(null); // posicionamiento manual

    btnIniciar = new JButton("INICIAR PARTIDA");
    btnIniciar.setBounds(180, 320, 270, 45);
    btnIniciar.setBackground(new Color(255, 200, 0));
    btnIniciar.setForeground(Color.BLACK);
    btnIniciar.setFont(new Font("Courier New", Font.BOLD, 16));
    btnIniciar.setFocusPainted(false);
    btnIniciar.addActionListener(e -> {
        modelo.setEstado(EstadoJuego.JUGANDO);
        btnIniciar.setVisible(false);
        btnSalir.setVisible(false);
        requestFocusInWindow();
    });

    btnSalir = new JButton("SALIR");
    btnSalir.setBounds(180, 380, 270, 45);
    btnSalir.setBackground(new Color(220, 50, 50));
    btnSalir.setForeground(Color.WHITE);
    btnSalir.setFont(new Font("Courier New", Font.BOLD, 16));
    btnSalir.setFocusPainted(false);

    btnSalir.addActionListener(e -> {
        System.exit(0);
    });

    add(btnIniciar);
    add(btnSalir);
}

    private void configurarBotonesNivel() {

    btnContinuarNivel = new JButton("CONTINUAR");
    btnContinuarNivel.setBounds(180, 320, 250, 45);

    btnContinuarNivel.addActionListener(e -> {
        modelo.cargarSiguienteNivel();
        requestFocusInWindow();
    });

    add(btnContinuarNivel);
    btnSalirNivel = new JButton("SALIR");
    btnSalirNivel.setBounds(180, 390, 250, 45);
    btnSalirNivel.addActionListener(e -> {
        System.exit(0);
    });
    add(btnSalirNivel);
    btnContinuarNivel.setVisible(false);
    btnSalirNivel.setVisible(false);
}

    private void configurarBotonesGameOver() {

    btnReintentar = new JButton("REINTENTAR");
    btnReintentar.setBounds(180, 340, 270, 45);
    btnReintentar.setBackground(new Color(255, 200, 0));
    btnReintentar.setForeground(Color.BLACK);
    btnReintentar.setFont(new Font("Courier New", Font.BOLD, 16));
    btnReintentar.setFocusPainted(false);

    btnReintentar.addActionListener(e -> {
        modelo.reiniciar();
        btnReintentar.setVisible(false);
        btnSalirGameOver.setVisible(false);
        requestFocusInWindow();
    });

    btnSalirGameOver = new JButton("SALIR");
    btnSalirGameOver.setBounds(180, 400, 270, 45);
    btnSalirGameOver.setBackground(new Color(220, 50, 50));
    btnSalirGameOver.setForeground(Color.WHITE);
    btnSalirGameOver.setFont(new Font("Courier New", Font.BOLD, 16));
    btnSalirGameOver.setFocusPainted(false);

    btnSalirGameOver.addActionListener(e -> {
        System.exit(0);
    });

    btnReintentar.setVisible(false);
    btnSalirGameOver.setVisible(false);

    add(btnReintentar);
    add(btnSalirGameOver);
}

    private void configurarBotonesPausa() {

    btnContinuarPausa = new JButton("CONTINUAR");
    btnContinuarPausa.setBounds(180, 320, 270, 45);
    btnContinuarPausa.setBackground(new Color(255, 200, 0));
    btnContinuarPausa.setForeground(Color.BLACK);
    btnContinuarPausa.setFont(new Font("Courier New", Font.BOLD, 16));
    btnContinuarPausa.setFocusPainted(false);

    btnContinuarPausa.addActionListener(e -> {
        modelo.setEstado(EstadoJuego.JUGANDO);
        btnContinuarPausa.setVisible(false);
        btnSalirPausa.setVisible(false);
        requestFocusInWindow();
    });

    btnSalirPausa = new JButton("SALIR");
    btnSalirPausa.setBounds(180, 380, 270, 45);
    btnSalirPausa.setBackground(new Color(220, 50, 50));
    btnSalirPausa.setForeground(Color.WHITE);
    btnSalirPausa.setFont(new Font("Courier New", Font.BOLD, 16));
    btnSalirPausa.setFocusPainted(false);

    btnSalirPausa.addActionListener(e -> {
        System.exit(0);
    });

    btnContinuarPausa.setVisible(false);
    btnSalirPausa.setVisible(false);

    add(btnContinuarPausa);
    add(btnSalirPausa);
}


    public EstadoJuego getEstado() { return modelo.getEstado(); }
    public void setEstado(EstadoJuego estado) { modelo.setEstado(estado); }
    public void reiniciar() { modelo.reiniciar(); }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

       // Ocultar todos los botones
        btnIniciar.setVisible(false);
        btnSalir.setVisible(false);

        btnContinuarNivel.setVisible(false);
        btnSalirNivel.setVisible(false);

        btnReintentar.setVisible(false);
        btnSalirGameOver.setVisible(false);

        btnContinuarPausa.setVisible(false);
        btnSalirPausa.setVisible(false); 

    if (modelo.getEstado() == EstadoJuego.INICIO) {
        btnIniciar.setVisible(true);
        btnSalir.setVisible(true);

        // Fondo
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA);

        // Título
        g.setColor(new Color(255, 255, 102));
        g.setFont(new Font("Courier New", Font.BOLD, 52));
        FontMetrics fm = g.getFontMetrics();
        String titulo = "PAC-MAN";
        g.drawString(titulo, (COLUMNAS * TAMANIO_CELDA - fm.stringWidth(titulo)) / 2, 140);

        // Top 5 puntajes
        String top = "— TOP 5 —";
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier New", Font.BOLD, 15));
        FontMetrics fmTop = g.getFontMetrics();
        int anchoPanel = getWidth();
        int xTop = (anchoPanel - fmTop.stringWidth(top)) / 2;
        g.drawString(top, xTop, 210);

        List<String[]> puntajes = PersistenciaPuntajes.cargarPuntajes();
        g.setFont(new Font("Courier New", Font.PLAIN, 13));
        if (puntajes.isEmpty()) {
            g.setColor(Color.GRAY);
            g.drawString("Aún no hay partidas jugadas", 170, 240);
        } else {
            for (int i = 0; i < puntajes.size(); i++) {
                g.setColor(new Color(255, 200, 0));
                g.drawString((i + 1) + ".  " + puntajes.get(i)[0] + " pts   " + puntajes.get(i)[1], 160, 235 + i * 20);
            }
        }
    } else {
        btnIniciar.setVisible(false);
        btnSalir.setVisible(false);
    } 
    if (modelo.getEstado() == EstadoJuego.JUGANDO) {
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

            btnContinuarNivel.setVisible(true);
            btnSalirNivel.setVisible(true);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, COLUMNAS * TAMANIO_CELDA, FILAS * TAMANIO_CELDA);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Courier New", Font.BOLD, 38));
            g.drawString("¡NIVEL COMPLETADO!", 110, 180);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 22));
            g.drawString(
                "Has completado el nivel " + modelo.getNivelCompletado(), 140, 220);
            g.drawString("Puntos: " + modelo.getScoreModel().getPuntos(), 170, 260);
        }

         else if (modelo.getEstado() == EstadoJuego.PAUSA) {

            btnContinuarPausa.setVisible(true);
            btnSalirPausa.setVisible(true);
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Courier New", Font.BOLD, 42));
            String texto = "PAUSA";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            g.drawString(texto, x, 180);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 20));
            g.drawString("Juego pausado", (getWidth() - 150) / 2, 240);

        } else if (modelo.getEstado() == EstadoJuego.GAME_OVER) {

            btnReintentar.setVisible(true);
            btnSalirGameOver.setVisible(true);
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 48));
            String texto = "GAME OVER";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            g.drawString(texto, x, 220);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 24));
            g.drawString("Puntaje final: " + modelo.getScoreModel().getPuntos(), 150, 290);

        } else if (modelo.getEstado() == EstadoJuego.VICTORIA) {
            btnSalirVictoria.setVisible(true);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.GREEN);
            g.setFont(new Font("Courier New", Font.BOLD, 42));
            String texto = "¡GANASTE!";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(texto)) / 2;
            g.drawString(texto, x, 180);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 24));
            g.drawString(
                "Puntaje final: " + modelo.getScoreModel().getPuntos(), 160, 250);
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
