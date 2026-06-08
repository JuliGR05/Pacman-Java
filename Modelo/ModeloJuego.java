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

    //Constructor
    public ModeloJuego() {
        scoreModel = new ScoreModel();
        estado = EstadoJuego.INICIO;
        inicializarNivel(1); // arranca nivel 1 
    }

    private void inicializarNivel(int nivel) {
        laberinto = new Laberinto(nivel);
        pacman = new Pacman(1, 1);
        pacman.setContexto(laberinto, scoreModel);

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
    }

    //Métodos 
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

    private void activarPowerUp() { //fantasmas se vuelven vulnerables 
        fantasmaRojo.setVulnerable(true);
        fantasmaNaranja.setVulnerable(true);
        fantasmaRosado.setVulnerable(true);

        new Thread(() -> {
            try {
            Thread.sleep (8000); //8 segundos que dura la vulnerabilidad   
            } catch (InterruptedException e) {
                return;
            }
        fantasmaRojo.setVulnerable(false);
        fantasmaNaranja.setVulnerable(false);
        fantasmaRosado.setVulnerable(false);
        }).start();
    }

    private void verificarColisiones() {
        verificarColisionFantasma(fantasmaRojo);
        verificarColisionFantasma(fantasmaNaranja);
        verificarColisionFantasma(fantasmaRosado);

       if (pacman.getVidas() <= 0) {
        PersistenciaPuntajes.guardarPuntaje(scoreModel.getPuntos()); 
        estado = EstadoJuego.GAME_OVER;
        }
    }

    private void verificarColisionFantasma(Fantasma fantasma) {
        boolean colision = Math.abs(pacman.getFila() - fantasma.getFila()) <= 0
        && Math.abs(pacman.getColumna() - fantasma.getColumna()) <= 0; 

        if (colision) {
            if (fantasma.isVulnerable()) {
                scoreModel.sumarPuntos(200);
                fantasma.setVulnerable(false);
                fantasma.respawnAleatorio(pacman);
            } else if (!pacman.isInvulnerable()) {
                pacman.perderVida();
                pacman.reiniciarPosicion();
                pacman.activarInvulnerabilidad();
            }
        }
    }

    private void subirNivel() {
        nivelCompletado = nivelActual;
        if (nivelActual == 3) {
            estado = EstadoJuego.VICTORIA;
            return;
        }
        estado = EstadoJuego.SIGUIENTE_NIVEL;
    }

    public void reiniciar() {
        detenerPersonajes();
        nivelActual = 1;
        nivelCompletado = 0;
        scoreModel.reiniciar();
        estado = EstadoJuego.INICIO;
        inicializarNivel(1);
    }

    private void detenerPersonajes() {
        pacman.detener();
        fantasmaRojo.detener();
        fantasmaNaranja.detener();
        fantasmaRosado.detener();

    try {
        if (hiloPacman != null) hiloPacman.join(500); //protegiendo los join
        if (hiloRojo != null) hiloRojo.join(500);
        if (hiloNaranja != null) hiloNaranja.join(500);
        if (hiloRosado != null) hiloRosado.join(500);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
}

    public void cargarSiguienteNivel() {
    detenerPersonajes();
    nivelActual++;
    inicializarNivel(nivelActual);
    estado = EstadoJuego.JUGANDO;
    }

    // Getters
    public Laberinto getLaberinto() { return laberinto; }
    public Pacman getPacman() { return pacman; }
    public FantasmaRojo getFantasmaRojo() { return fantasmaRojo; }
    public FantasmaNaranja getFantasmaNaranja() { return fantasmaNaranja; }
    public FantasmaRosado getFantasmaRosado() { return fantasmaRosado; }
    public ScoreModel getScoreModel() { return scoreModel; }
    public EstadoJuego getEstado() { return estado; }
    public void setEstado(EstadoJuego estado) { this.estado = estado; }

    public int getNivelActual(){
        return nivelActual;
    }
    public int getNivelCompletado() {
    return nivelCompletado;
}
}