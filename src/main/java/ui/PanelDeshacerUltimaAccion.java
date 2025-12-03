package ui;

import javax.swing.*;
import java.awt.*;
import persistencias.Accion;
import persistencias.PersistenciaFachada;

/**
 * Clase que implementa un panel para deshacer la última acción realizada
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelDeshacerUltimaAccion extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    public PanelDeshacerUltimaAccion(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("DESHACER ÚLTIMA ACCIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        JButton btnDeshacer = new JButton("Deshacer última acción");
        JButton btnRegresar = new JButton("Regresar");

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnDeshacer);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.CENTER);

        btnDeshacer.addActionListener(e -> deshacerAccion());
        btnRegresar.addActionListener(e -> frame.cambiarPanel(new MenuPrincipal(frame, sistema)));
    }

    private void deshacerAccion() {
        Accion ultimaAccion = sistema.obtenerUltimaAccion();
        if (ultimaAccion == null) {
            JOptionPane.showMessageDialog(this, "No hay acciones para deshacer.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Mostramos un JOptionPane de confirmación
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea deshacer la siguiente acción?\n" + ultimaAccion.descripcion(),
                "Confirmar deshacer",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        // Si el usuario confirma
        if (opcion == JOptionPane.YES_OPTION) {
            boolean exito = sistema.deshacerUltimaAccion();
            if (exito) {
                JOptionPane.showMessageDialog(this, "Última acción deshecha correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo deshacer la acción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}