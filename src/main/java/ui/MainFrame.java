package ui;

import javax.swing.*;
import java.awt.*;
import persistencias.PersistenciaFachada;

/**
 * Clase que implementa un frame en donde se mostrarán todos los paneles
 * 
 * @author Paulina, Alec, Sarah
 */
public class MainFrame extends JFrame {
    private JPanel contenedor;
    private PersistenciaFachada sistema;

    public MainFrame() {
        super("Sistema Escolar");
        sistema = new PersistenciaFachada();
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        contenedor = new JPanel(new BorderLayout());
        setContentPane(contenedor);

        // iniciar con MenuPrincipal
        MenuPrincipal menu = new MenuPrincipal(this, sistema);
        cambiarPanel(menu);
    }

    public void cambiarPanel(JPanel panel) {
        contenedor.removeAll();
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
    }

    public PersistenciaFachada getSistema() {
        return sistema; 
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new MainFrame().setVisible(true);
//        });
//    }
}