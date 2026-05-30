package Controlador;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Modelo.Pacman;

public class ControladorJuego implements KeyListener {
    private Pacman pacman;

    public ControladorJuego(Pacman pacman){
        this.pacman = pacman;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        if (tecla == KeyEvent.VK_UP){
            pacman.setDireccion (-1,0);
        } else if (tecla == KeyEvent.VK_DOWN){
            pacman.setDireccion (1,0);
        } else if (tecla == KeyEvent.VK_LEFT){
            pacman.setDireccion (0, -1);
        } else if (tecla == KeyEvent.VK_RIGHT){
            pacman.setDireccion (0,1);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override 
    public void keyTyped(KeyEvent e){}

    
}
