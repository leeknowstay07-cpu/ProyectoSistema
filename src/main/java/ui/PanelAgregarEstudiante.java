package ui;

import javax.swing.*;
import java.awt.*;
import modelos.Estudiante;
import persistencias.PersistenciaFachada;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Clase que implementa un panel para agregar un estudiante
 *
 * @author Paulina, Alec, Sarah
 */
public class PanelAgregarEstudiante extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;
    private JTextField txtMatricula, txtNombre, txtTelefono, txtCorreo, txtDireccion;

    public PanelAgregarEstudiante(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TÍTULO
        JLabel titulo = new JLabel("REGISTRAR ESTUDIANTE", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // FORMULARIO
        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));
        form.setBackground(Color.WHITE);

        // CAMPOS
        txtMatricula = new JTextField();
        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtDireccion = new JTextField();

        ((AbstractDocument) txtTelefono.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) {
                    return;
                }
                if (string.matches("\\d+")) { // solo números
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) {
                    return;
                }
                if (text.isEmpty() || text.matches("\\d+")) { // permite borrado y solo números
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); // permite eliminar sin restricciones
            }
        });

        form.add(new JLabel("Matrícula:"));
        form.add(txtMatricula);

        form.add(new JLabel("Nombre completo:"));
        form.add(txtNombre);

        form.add(new JLabel("Teléfono:"));
        form.add(txtTelefono);

        form.add(new JLabel("Correo electrónico:"));
        form.add(txtCorreo);

        form.add(new JLabel("Dirección postal:"));
        form.add(txtDireccion);

        add(form, BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.SOUTH);

        // Registrar estudiante
        btnRegistrar.addActionListener(e -> registrarEstudiante());

        // Regresar al menú
        btnRegresar.addActionListener(e
                -> frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );
    }

    private void registrarEstudiante() {

        String matricula = txtMatricula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (matricula.isEmpty() || nombre.isEmpty() || telefono.isEmpty()
                || correo.isEmpty() || direccion.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el objeto Estudiante
        Estudiante est = new Estudiante(
                matricula,
                nombre,
                telefono,
                correo,
                direccion
        );

        // Insertarlo en el BST con la persistencia
        boolean exito = sistema.agregarEstudiante(est);

        if (!exito) {
            JOptionPane.showMessageDialog(this,
                    "Ya existe un estudiante con esa matrícula.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Estudiante registrado exitosamente.");
            limpiarCampos();
        }
    }

    private void limpiarCampos() {
        txtMatricula.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
    }
}
