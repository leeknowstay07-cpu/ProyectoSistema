package ui;

import modelos.Estudiante;
import persistencias.PersistenciaFachada;
import estructuras.VectorDinamico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Clase que implementa un panel para buscar un estudiante
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelBuscarEstudiante extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    private JTextField txtMatricula;
    private JTable tabla;
    private DefaultTableModel modelo;

    public PanelBuscarEstudiante(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BorderLayout());
        panelTop.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("BUSCAR ESTUDIANTE POR MATRÍCULA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panelTop.add(titulo, BorderLayout.NORTH);

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSuperior.setBackground(new Color(240, 240, 240));

        panelSuperior.add(new JLabel("Matrícula: "));
        txtMatricula = new JTextField(15);
        panelSuperior.add(txtMatricula);

        JButton btnBuscar = new JButton("Buscar");
        panelSuperior.add(btnBuscar);

        JButton btnRegresar = new JButton("Regresar");
        panelSuperior.add(btnRegresar);

        panelTop.add(panelSuperior, BorderLayout.CENTER);

        add(panelTop, BorderLayout.NORTH);

        // TABLA 
        String columnas[] = {"Matrícula", "Nombre", "Teléfono", "Correo", "Dirección", "Calificaciones"};

        modelo = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // EVENTOS
        btnBuscar.addActionListener(e -> buscarEstudiante());

        btnRegresar.addActionListener(e
                -> frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );
    }

    private void buscarEstudiante() {

        String matricula = txtMatricula.getText().trim();

        if (matricula.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debes ingresar una matrícula.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Estudiante est = sistema.buscarEstudiante(matricula);

        modelo.setRowCount(0); // limpia la tabla

        if (est == null) {
            JOptionPane.showMessageDialog(this,
                    "El estudiante con matrícula \"" + matricula + "\" no existe.",
                    "No encontrado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calificaciones como texto:
        VectorDinamico califs = est.getCalificaciones();
        StringBuilder sbCalif = new StringBuilder();

        if (califs != null && califs.tamanio() > 0) {
            for (int i = 0; i < califs.tamanio(); i++) {
                sbCalif.append(califs.obtener(i)).append(" ");
            }
        } else {
            sbCalif.append("(sin calificaciones)");
        }

        modelo.addRow(new Object[]{
            est.getMatricula(),
            est.getNombre(),
            est.getTelefono(),
            est.getCorreo(),
            est.getDireccion(),
            sbCalif.toString()
        });
    }
}