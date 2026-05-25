import Vista.PanelJuego;
import javax.swing.JFrame;

public class Main {
    public static void main (String[] args){
        JFrame ventana = new JFrame ("Pac-Man");
        PanelJuego panelJuego = new PanelJuego ();
        ventana.add (panelJuego);
        ventana.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        ventana.setResizable (false);
        ventana.pack ();
        ventana.setLocationRelativeTo(null); //Ventana centrada
        ventana.setVisible(true);
    }
}

