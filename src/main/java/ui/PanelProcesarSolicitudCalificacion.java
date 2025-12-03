package ui;

import javax.swing.*;
import java.awt.*;
import persistencias.PersistenciaFachada;

/**
 * Clase que implementa un panel para procesar la solicitud de una calificación
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelProcesarSolicitudCalificacion extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    public PanelProcesarSolicitudCalificacion(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("PROCESAR SIGUIENTE SOLICITUD", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        JButton btnProcesar = new JButton("Procesar siguiente solicitud");
        JButton btnRegresar = new JButton("Regresar");

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);
        panelBotones.add(btnProcesar);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.CENTER);

        btnProcesar.addActionListener(e -> procesarSolicitud());
        btnRegresar.addActionListener(e -> frame.cambiarPanel(new MenuPrincipal(frame, sistema)));
    }

    private void procesarSolicitud() {
        boolean exito = sistema.procesarSiguienteSolicitud();
        if (exito) {
            JOptionPane.showMessageDialog(this, "Solicitud procesada correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No hay solicitudes pendientes.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
