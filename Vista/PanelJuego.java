package Vista;

import java.awt.*;
import javax.swing.JPanel;

public class PanelJuego extends JPanel{
   public PanelJuego(){
    setBackground(Color.BLACK);
    setPreferredSize(new Dimension(600,600));
   } 
       @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
}
}