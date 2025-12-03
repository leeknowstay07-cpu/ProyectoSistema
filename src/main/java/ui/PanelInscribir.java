package ui;

import javax.swing.*;
import java.awt.*;
import modelos.Curso;
import modelos.Estudiante;
import persistencias.PersistenciaFachada;
import estructuras.ListaSimple;

/**
 * Panel para inscribir un estudiante a un curso
 */
public class PanelInscribir extends JPanel {

    private MainFrame frame;
    private PersistenciaFachada sistema;

    private JComboBox<Estudiante> cbEstudiantes;
    private JComboBox<Curso> cbCursos;

    public PanelInscribir(MainFrame frame, PersistenciaFachada sistema) {
        this.frame = frame;
        this.sistema = sistema;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TÍTULO
        JLabel titulo = new JLabel("INSCRIBIR ESTUDIANTE EN CURSO", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // FORMULARIO
        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));
        form.setBackground(Color.WHITE);

        cbEstudiantes = new JComboBox<>();
        cbCursos = new JComboBox<>();

        form.add(new JLabel("Estudiante:"));
        form.add(cbEstudiantes);

        form.add(new JLabel("Curso:"));
        form.add(cbCursos);

        add(form, BorderLayout.CENTER);

        // BOTONES
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);

        JButton btnInscribir = new JButton("Inscribir");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnInscribir);
        panelBotones.add(btnRegresar);

        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos en los combobox
        cargarEstudiantes();
        cargarCursos();

        // Acciones
        btnInscribir.addActionListener(e -> inscribirEstudiante());
        btnRegresar.addActionListener(e ->
                frame.cambiarPanel(new MenuPrincipal(frame, sistema))
        );
    }

    private void cargarEstudiantes() {
        cbEstudiantes.removeAllItems();
        ListaSimple<Estudiante> lista = sistema.obtenerTodosEstudiantes();
        for (int i = 0; i < lista.size(); i++) {
            cbEstudiantes.addItem(lista.obtener(i));
        }
    }

    private void cargarCursos() {
        cbCursos.removeAllItems();
        ListaSimple<Curso> lista = sistema.listarCursos();
        for (int i = 0; i < lista.size(); i++) {
            cbCursos.addItem(lista.obtener(i));
        }
    }

    private void inscribirEstudiante() {
        Estudiante estudiante = (Estudiante) cbEstudiantes.getSelectedItem();
        Curso curso = (Curso) cbCursos.getSelectedItem();

        if (estudiante == null || curso == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un estudiante y un curso.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean ok = sistema.inscribirEstudiante(curso.getClave(), estudiante);

        if (!ok) {
            // Ya estaba inscrito o en lista de espera
            JOptionPane.showMessageDialog(this,
                    "El estudiante ya está inscrito o en lista de espera de este curso.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            // Inscripción exitosa (aunque puede quedar en lista de espera si el curso está lleno)
            JOptionPane.showMessageDialog(this,
                    "Inscripción realizada con éxito.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}