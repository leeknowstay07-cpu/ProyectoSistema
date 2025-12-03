package ui;

import estructuras.ListaSimple;
import estructuras.NodoSimple;
import javax.swing.*;
import java.awt.*;
import modelos.Curso;
import modelos.SolicitudCalificacion;
import modelos.SolicitudCalificacion.Tipo;
import modelos.Estudiante;
import persistencias.PersistenciaFachada;

/**
 * Clase que implementa un panel para enviar solicitud de calificación de un
 * estudiante
 *
 * @author Paulina, Alec, Sarah
 */
public class PanelEnviarSolicitudCalificacion extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    private JTextField txtMatricula, txtCalificacion, txtIndice;
    private JComboBox<String> cmbTipo, cmbCurso;

    public PanelEnviarSolicitudCalificacion(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("ENVIAR SOLICITUD DE CALIFICACIÓN", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 150, 20, 150));
        form.setBackground(Color.WHITE);

        txtMatricula = new JTextField();
        txtCalificacion = new JTextField();
        txtIndice = new JTextField();

        // Combo para tipo de solicitud
        cmbTipo = new JComboBox<>(new String[]{"NUEVA", "MODIFICAR"});

        // Llenar el JComboBox de cursos
        cmbCurso = new JComboBox<>();
        ListaSimple<Curso> cursos = sistema.listarCursos();
        NodoSimple actual = cursos.getInicio();
        while (actual != null) {
            Curso curso = (Curso) actual.getDato();
            cmbCurso.addItem(curso.getClave());
            actual = actual.getSiguiente();
        }

        form.add(new JLabel("Matrícula del estudiante:"));
        form.add(txtMatricula);

        form.add(new JLabel("Curso:"));
        form.add(cmbCurso);

        form.add(new JLabel("Tipo de solicitud:"));
        form.add(cmbTipo);

        form.add(new JLabel("Índice a modificar (solo MODIFICAR):"));
        form.add(txtIndice);

        form.add(new JLabel("Calificación:"));
        form.add(txtCalificacion);

        // Restringir txtCalificacion a solo números y punto decimal
        txtCalificacion.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();

                // Permitir borrar
                if (c == '\b') {
                    return;
                }

                // Solo permitir dígitos o un punto
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                    return;
                }

                // Solo un punto decimal permitido
                if (c == '.' && txtCalificacion.getText().contains(".")) {
                    e.consume();
                }
            }
        });

        add(form, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);

        JButton btnEnviar = new JButton("Enviar");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnEnviar);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.SOUTH);

        btnEnviar.addActionListener(e -> enviarSolicitud());
        btnRegresar.addActionListener(e -> frame.cambiarPanel(new MenuPrincipal(frame, sistema)));
    }

    private void enviarSolicitud() {
        String matricula = txtMatricula.getText().trim();
        String califStr = txtCalificacion.getText().trim();
        String tipoStr = (String) cmbTipo.getSelectedItem();
        String curso = (String) cmbCurso.getSelectedItem();

        if (matricula.isEmpty() || califStr.isEmpty() || curso == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double calif;
        try {
            calif = Double.parseDouble(califStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La calificación debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Estudiante est = sistema.buscarEstudiante(matricula);
        if (est == null) {
            JOptionPane.showMessageDialog(this, "Estudiante no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Tipo tipo = tipoStr.equals("NUEVA") ? Tipo.NUEVA : Tipo.MODIFICAR;
        int indice = -1;
        if (tipo == Tipo.MODIFICAR) {
            try {
                indice = Integer.parseInt(txtIndice.getText().trim());
                if (indice < 0 || indice >= est.getCalificaciones().tamanio()) {
                    JOptionPane.showMessageDialog(this, "Índice fuera de rango.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Índice debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        SolicitudCalificacion solicitud = new SolicitudCalificacion(matricula, curso, tipo, indice, calif);
        sistema.enviarSolicitudCalificacion(solicitud);

        JOptionPane.showMessageDialog(this, "Solicitud enviada correctamente.");
        txtMatricula.setText("");
        txtCalificacion.setText("");
        txtIndice.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbCurso.setSelectedIndex(0);
    }
}
