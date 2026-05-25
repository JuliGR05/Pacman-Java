import javax.swing.JFrame;
import vista.PanelJuego;

public class Main {
    public static void main (String[] args){
        JFrame ventana = newJFrame ("Pac-Man");
        PanelJuego panelJuego = new PanelJuego ();
        window.add (panelJuego);
        window.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        window.setResizable (false);
        window.pack ();
        window.setLocationRelativeTo(null); //Ventana centrada
        window.setVisible(true);
    }
}

