import Vista.PanelJuego;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Pac-Man");
            PanelJuego panelJuego = new PanelJuego();

            ventana.add(panelJuego);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setResizable(false);
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
            panelJuego.requestFocusInWindow();
        });
    }
}
