package ui;

import estructuras.ListaSimple;
import estructuras.NodoSimple;
import javax.swing.*;
import java.awt.*;

import modelos.Curso;
import modelos.Estudiante;
import persistencias.PersistenciaFachada;

/**
 * Clase que implementa un panel para rotar el líder/tutor de un curso
 * 
 * @author Paulina, Alec, Sarah
 */
public class PanelRotarTutor extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    private JComboBox<String> comboCursos;
    private JTextArea txtResultado;

    public PanelRotarTutor(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TÍTULO
        JLabel titulo = new JLabel("ROTAR ROL DEL ESTUDIANTE (Tutor / Líder)", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // PANEL SUPERIOR
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSuperior.setBackground(Color.WHITE);

        JLabel lblCurso = new JLabel("Curso:");
        lblCurso.setFont(new Font("Arial", Font.PLAIN, 18));

        comboCursos = new JComboBox<>();
        comboCursos.setPreferredSize(new Dimension(180, 30));

        JButton btnRotar = new JButton("Rotar");
        JButton btnRegresar = new JButton("Regresar");

        panelSuperior.add(lblCurso);
        panelSuperior.add(comboCursos);
        panelSuperior.add(btnRotar);
        panelSuperior.add(btnRegresar);

        add(panelSuperior, BorderLayout.CENTER);

        // PANEL RESULTADO 
        JPanel panelResultado = new JPanel(new BorderLayout());
        panelResultado.setBackground(Color.WHITE);
        panelResultado.setBorder(BorderFactory.createEmptyBorder(10, 50, 30, 50));

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Arial", Font.PLAIN, 18));

        panelResultado.add(new JScrollPane(txtResultado), BorderLayout.CENTER);
        add(panelResultado, BorderLayout.SOUTH);

        // Carga inicial
        cargarCursos();
        mostrarRolActual();

        // EVENTOS
        btnRotar.addActionListener(e -> rotarRol());
        comboCursos.addActionListener(e -> mostrarRolActual());
        btnRegresar.addActionListener(e ->
                frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );
    }


    private void cargarCursos() {
        comboCursos.removeAllItems();

        ListaSimple<Curso> lista = sistema.listarCursos();
        NodoSimple<Curso> nodo = lista.getInicio();

        while (nodo != null) {
            comboCursos.addItem(nodo.getDato().getClave());
            nodo = nodo.getSiguiente();
        }
    }


    private void mostrarRolActual() {
        String clave = (String) comboCursos.getSelectedItem();
        if (clave == null) {
            txtResultado.setText("No hay cursos registrados.");
            return;
        }

        Estudiante actual = sistema.obtenerRolActual(clave);

        if (actual == null) {
            txtResultado.setText("No hay un rol asignado actualmente.");
        } else {
            txtResultado.setText(
                    "Tutor/Líder actual:\n\n" +
                    "Matrícula: " + actual.getMatricula() + "\n" +
                    "Nombre: " + actual.getNombre()
            );
        }
    }


    private void rotarRol() {
        String clave = (String) comboCursos.getSelectedItem();
        if (clave == null) return;

        Estudiante nuevo = sistema.rotarRol(clave);

        if (nuevo == null) {
            txtResultado.setText("No hay estudiantes en la lista de roles.");
        } else {
            txtResultado.setText(
                    "Nuevo Tutor/Líder asignado:\n\n" +
                    "Matrícula: " + nuevo.getMatricula() + "\n" +
                    "Nombre: " + nuevo.getNombre()
            );
        }
    }
}