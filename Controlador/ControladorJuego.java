package Controlador;

import Modelo.EstadoJuego;
import Modelo.ModeloJuego;
import Vista.PanelJuego;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControladorJuego implements KeyListener {
    private ModeloJuego modelo;
    private PanelJuego panel;

    //Constructor
    public ControladorJuego(ModeloJuego modelo, PanelJuego panel){
        this.modelo = modelo;
        this.panel = panel;
    }

    //Métodos
    @Override
    public void keyPressed(KeyEvent e){
    int tecla = e.getKeyCode();

        if (modelo.getEstado() == EstadoJuego.INICIO) {
            if (tecla == KeyEvent.VK_ENTER) {
                modelo.setEstado(EstadoJuego.JUGANDO);
            }
        } else if (modelo.getEstado() == EstadoJuego.JUGANDO) {
            if (tecla == KeyEvent.VK_UP) {
                modelo.getPacman().setDireccion(-1, 0);
            } else if (tecla == KeyEvent.VK_DOWN) {
                modelo.getPacman().setDireccion(1, 0);
            } else if (tecla == KeyEvent.VK_LEFT) {
                modelo.getPacman().setDireccion(0, -1);
            } else if (tecla == KeyEvent.VK_RIGHT) {
                modelo.getPacman().setDireccion(0, 1);
            } else if (tecla == KeyEvent.VK_P) {
                modelo.setEstado(EstadoJuego.PAUSA);
            }
        } else if (modelo.getEstado() == EstadoJuego.PAUSA) {
            if (tecla == KeyEvent.VK_P) {
                modelo.setEstado(EstadoJuego.JUGANDO);
            }
        } else if (modelo.getEstado() == EstadoJuego.SIGUIENTE_NIVEL){
            if (tecla == KeyEvent.VK_ENTER){
                modelo.cargarSiguienteNivel();
            } else if (tecla == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        } else if (modelo.getEstado() == EstadoJuego.GAME_OVER) {
            if (tecla == KeyEvent.VK_R) {
                modelo.reiniciar();
            } else if (tecla == KeyEvent.VK_ESCAPE){
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
