package ui;

import javax.swing.*;
import java.awt.*;
import modelos.Curso;
import persistencias.PersistenciaFachada;

/**
 * Clase que implementa un panel para agregar un curso
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelAgregarCurso extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    private JTextField txtClave, txtNombre;

    public PanelAgregarCurso(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TÍTULO
        JLabel titulo = new JLabel("REGISTRAR CURSO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // FORMULARIO
        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));
        form.setBackground(Color.WHITE);

        // CAMPOS
        txtClave = new JTextField();
        txtNombre = new JTextField();

        form.add(new JLabel("Clave del curso:"));
        form.add(txtClave);

        form.add(new JLabel("Nombre del curso:"));
        form.add(txtNombre);

        add(form, BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.SOUTH);

        // Registrar curso
        btnRegistrar.addActionListener(e -> registrarCurso());

        // Regresar al menú
        btnRegresar.addActionListener(e ->
                frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );
    }

    private void registrarCurso() {

        String clave = txtClave.getText().trim();
        String nombre = txtNombre.getText().trim();

        if (clave.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear objeto Curso
        Curso curso = new Curso(clave, nombre);

        // Insertar usando la fachada
        boolean exito = sistema.agregarCurso(curso);

        if (!exito) {
            JOptionPane.showMessageDialog(this,
                    "Ya existe un curso con esa clave.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Curso registrado exitosamente.");
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtClave.setText("");
        txtNombre.setText("");
    }
}