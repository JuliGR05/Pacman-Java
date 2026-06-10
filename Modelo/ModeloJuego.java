package Modelo;

public class ModeloJuego {

    private Laberinto laberinto;
    private Pacman pacman;
    private FantasmaRojo fantasmaRojo;
    private FantasmaNaranja fantasmaNaranja;
    private FantasmaRosado fantasmaRosado;
    private ScoreModel scoreModel;
    private EstadoJuego estado;
    private int nivelActual = 1;
    private int nivelCompletado;
    private Thread hiloRojo;
    private Thread hiloNaranja;
    private Thread hiloRosado;
    private Thread hiloPacman;
    private Thread hiloPowerUp;
    public static volatile boolean pausado = false;

    //Constructor
    public ModeloJuego() {
        scoreModel = new ScoreModel();
        estado = EstadoJuego.INICIO;
        inicializarNivel(1);
    }

    private void inicializarNivel(int nivel) {
        laberinto = new Laberinto(nivel);
        pacman = new Pacman(1, 1);
        pacman.setContexto(laberinto, scoreModel, this);

        fantasmaRojo = new FantasmaRojo(19, 9, laberinto, pacman);
        fantasmaNaranja = new FantasmaNaranja(1, 8, laberinto);
        fantasmaRosado = new FantasmaRosado(1, 14, laberinto);

        hiloRojo = new Thread(fantasmaRojo);
        hiloNaranja = new Thread(fantasmaNaranja);
        hiloRosado = new Thread(fantasmaRosado);
        hiloPacman = new Thread(pacman);

        hiloRojo.start();
        hiloNaranja.start();
        hiloRosado.start();
        hiloPacman.start();

        long velocidadFantasmas;
        long velocidadPacman;

        if (nivel == 1) {
            velocidadFantasmas = 250;
            velocidadPacman = 210;
        } else if (nivel == 2){
            velocidadFantasmas = 180;
            velocidadPacman = 160;
        } else {
            velocidadFantasmas = 120;
            velocidadPacman = 110;
        }

        fantasmaRojo.setVelocidad(velocidadFantasmas);
        fantasmaNaranja.setVelocidad(velocidadFantasmas);
        fantasmaRosado.setVelocidad(velocidadFantasmas);
        pacman.setVelocidad(velocidadPacman);
    }

    public void actualizarJuego() {
        if (estado != EstadoJuego.JUGANDO) return;

        int celdaNueva = laberinto.getCelda(pacman.getFila(), pacman.getColumna());
        if (celdaNueva == Laberinto.POWERUP) {
            laberinto.setCelda(pacman.getFila(), pacman.getColumna(), Laberinto.VACIO);
            activarPowerUp();
        }

        verificarColisiones();

        if (laberinto.contarPellets() == 0) {
            subirNivel();
        }
    }

    private void activarPowerUp() {
        if (hiloPowerUp != null && hiloPowerUp.isAlive()){
            hiloPowerUp.interrupt();
        }

        fantasmaRojo.setVulnerable(true);
        fantasmaNaranja.setVulnerable(true);
        fantasmaRosado.setVulnerable(true);

        // Sonido power-up, suena una sola vez
        Sonido.reproducir("/Recursos/powerup.wav");

        hiloPowerUp = new Thread(() -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                return;
            }
            fantasmaRojo.setVulnerable(false);
            fantasmaNaranja.setVulnerable(false);
            fantasmaRosado.setVulnerable(false);

            // Termina el powerup
            Sonido.detener("powerup");
        });
        hiloPowerUp.start();
    }

    private void verificarColisiones() {
        verificarColisionFantasma(fantasmaRojo);
        verificarColisionFantasma(fantasmaNaranja);
        verificarColisionFantasma(fantasmaRosado);

        if (pacman.getVidas() <= 0) {
            PersistenciaPuntajes.guardarPuntaje(scoreModel.getPuntos());
            estado = EstadoJuego.GAME_OVER;
            // Sonido game over
            Sonido.detenerTodo();
            Sonido.reproducir("/Recursos/gameover.wav");
        }
    }

    public void verificarColisionEnMovimiento(int filaPacman, int columnaPacman) {
        verificarColisionFantasma(fantasmaRojo);
        verificarColisionFantasma(fantasmaNaranja);
        verificarColisionFantasma(fantasmaRosado);
    }

    private void verificarColisionFantasma(Fantasma fantasma) {
        boolean colision = Math.abs(pacman.getFila() - fantasma.getFila()) <= 0
        && Math.abs(pacman.getColumna() - fantasma.getColumna()) <= 0;

        if (colision) {
            if (fantasma.isVulnerable()) {
                scoreModel.sumarPuntos(200);
                fantasma.setVulnerable(false);
                fantasma.respawnAleatorio(pacman);
                // Sonido comer fantasma (bajito, 20% de volumen)
                Sonido.reproducirConVolumen("/Recursos/eatghost.wav", 0.2f);
            } else if (!pacman.isInvulnerable()) {
                pacman.perderVida();
                pacman.reiniciarPosicion();
                pacman.activarInvulnerabilidad();
                // Sonido muerte / perder vida
                Sonido.detenerTodo();
                Sonido.reproducir("/Recursos/death.wav");

            }
        }
    }

    public boolean hayFantasmaEn(int fila, int columna) {
        return (fantasmaRojo.getFila() == fila && fantasmaRojo.getColumna() == columna)
            || (fantasmaNaranja.getFila() == fila && fantasmaNaranja.getColumna() == columna)
            || (fantasmaRosado.getFila() == fila && fantasmaRosado.getColumna() == columna);
    }

    private void subirNivel() {
        nivelCompletado = nivelActual;
        Sonido.detenerTodo();
        if (nivelActual == 3) {
            estado = EstadoJuego.VICTORIA;
            // Sonido victoria
            Sonido.reproducir("/Recursos/victory.wav");
            return;
        }
        estado = EstadoJuego.SIGUIENTE_NIVEL;
        // Sonido nivel completado
        Sonido.reproducir("/Recursos/levelup.wav");
    }

    public void reiniciar() {
        detenerPersonajes();
        Sonido.detenerTodo();
        nivelActual = 1;
        nivelCompletado = 0;
        scoreModel.reiniciar();
        estado = EstadoJuego.INICIO;
        inicializarNivel(1);
        // Volver al menú con música
        Sonido.reproducirLoop("menu", "/Recursos/menu.wav");
    }

    private void detenerPersonajes() {
        pacman.detener();
        fantasmaRojo.detener();
        fantasmaNaranja.detener();
        fantasmaRosado.detener();

        try {
            if (hiloPacman != null) hiloPacman.join(500);
            if (hiloRojo != null) hiloRojo.join(500);
            if (hiloNaranja != null) hiloNaranja.join(500);
            if (hiloRosado != null) hiloRosado.join(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void cargarSiguienteNivel() {
        detenerPersonajes();
        Sonido.detenerTodo();
        nivelActual++;
        inicializarNivel(nivelActual);
        estado = EstadoJuego.JUGANDO;

    }

    // Métodos para iniciar/detener sonidos de juego desde la vista
    public void iniciarSonidoJuego() {
        Sonido.detener("menu");
    }

    public void iniciarSonidoMenu() {
        Sonido.detenerTodo();
        Sonido.reproducirLoop("menu", "/Recursos/menu.wav");
    }

    // Getters
    public Laberinto getLaberinto() { return laberinto; }
    public Pacman getPacman() { return pacman; }
    public FantasmaRojo getFantasmaRojo() { return fantasmaRojo; }
    public FantasmaNaranja getFantasmaNaranja() { return fantasmaNaranja; }
    public FantasmaRosado getFantasmaRosado() { return fantasmaRosado; }
    public ScoreModel getScoreModel() { return scoreModel; }
    public EstadoJuego getEstado() { return estado; }
    public void setEstado(EstadoJuego estado) {
        this.estado = estado;
        if (estado == EstadoJuego.PAUSA) {
            pausado = true;
        } else if (estado == EstadoJuego.JUGANDO) {
            pausado = false;
        }
    }

    public int getNivelActual(){ return nivelActual; }
    public int getNivelCompletado() { return nivelCompletado; }
}
