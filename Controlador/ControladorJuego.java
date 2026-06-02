package Controlador;
import Modelo.EstadoJuego;
import Modelo.Pacman;
import Vista.PanelJuego;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControladorJuego implements KeyListener {
    private Pacman pacman;
    private PanelJuego panel;

    public ControladorJuego(Pacman pacman, PanelJuego panel){
        this.pacman = pacman;
        this.panel = panel;
    }

    @Override
public void keyPressed(KeyEvent e) {
    int tecla = e.getKeyCode();

    if (panel.getEstado() == EstadoJuego.INICIO) {
        if (tecla == KeyEvent.VK_ENTER) {
            panel.setEstado(EstadoJuego.JUGANDO);
        }
    } else if (panel.getEstado() == EstadoJuego.JUGANDO) {
        if (tecla == KeyEvent.VK_UP) {
            pacman.setDireccion(-1, 0);
        } else if (tecla == KeyEvent.VK_DOWN) {
            pacman.setDireccion(1, 0);
        } else if (tecla == KeyEvent.VK_LEFT) {
            pacman.setDireccion(0, -1);
        } else if (tecla == KeyEvent.VK_RIGHT) {
            pacman.setDireccion(0, 1);
        } else if (tecla == KeyEvent.VK_P) {
            panel.setEstado(EstadoJuego.PAUSA);
        }
    } else if (panel.getEstado() == EstadoJuego.PAUSA) {
        if (tecla == KeyEvent.VK_P) {
            panel.setEstado(EstadoJuego.JUGANDO);
        }
    } else if (panel.getEstado() == EstadoJuego.GAME_OVER) {
        if (tecla == KeyEvent.VK_ENTER) {
            panel.reiniciar();
        }
    }
} // cierra keyPressed

@Override
public void keyReleased(KeyEvent e) {}

@Override
public void keyTyped(KeyEvent e) {}

}