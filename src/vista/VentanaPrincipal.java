package vista;

import modelo.Biblioteca;
import modelo.Libro;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private Biblioteca biblioteca = new Biblioteca();
    private JTextField txtIsbn = new JTextField(), txtTitulo = new JTextField(),
            txtAutor = new JTextField(), txtAnio = new JTextField(),
            txtCopias = new JTextField(), txtBuscar = new JTextField(10);
    private JComboBox<String> cbGenero = new JComboBox<>(Biblioteca.GENEROS_PREDEFINIDOS);
    private DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"ISBN", "Título", "Autor", "Género", "Año", "Copias"}, 0);
    private JTable tabla = new JTable(modeloTabla);

    public VentanaPrincipal() {
        setTitle("Biblioteca San Rafael");
        setSize(750, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pnlForm = new JPanel(new GridLayout(4, 4, 5, 5));
        pnlForm.add(new JLabel("ISBN:")); pnlForm.add(txtIsbn);
        pnlForm.add(new JLabel("Título:")); pnlForm.add(txtTitulo);
        pnlForm.add(new JLabel("Autor:")); pnlForm.add(txtAutor);
        pnlForm.add(new JLabel("Género:")); pnlForm.add(cbGenero);
        pnlForm.add(new JLabel("Año:")); pnlForm.add(txtAnio);
        pnlForm.add(new JLabel("Copias:")); pnlForm.add(txtCopias);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardar());
        pnlForm.add(btnGuardar);
        add(pnlForm, BorderLayout.NORTH);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel pnlInf = new JPanel();
        JButton btnBuscar = new JButton("Filtrar Autor"), btnTodos = new JButton("Ver Todos"), btnEliminar = new JButton("Eliminar");
        btnBuscar.addActionListener(e -> cargarTabla(biblioteca.filtrarPorAutor(txtBuscar.getText())));
        btnTodos.addActionListener(e -> cargarTabla(biblioteca.obtenerTodos()));
        btnEliminar.addActionListener(e -> eliminar());

        pnlInf.add(new JLabel("Autor:")); pnlInf.add(txtBuscar);
        pnlInf.add(btnBuscar); pnlInf.add(btnTodos); pnlInf.add(btnEliminar);
        add(pnlInf, BorderLayout.SOUTH);
    }

    private void guardar() {
        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            int copias = Integer.parseInt(txtCopias.getText().trim());
            Libro l = new Libro(txtTitulo.getText(), txtAutor.getText(), txtIsbn.getText(), (String)cbGenero.getSelectedItem(), anio, copias);
            if (biblioteca.agregarLibro(l)) cargarTabla(biblioteca.obtenerTodos());
            else JOptionPane.showMessageDialog(this, "ISBN duplicado o error.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Revisa los campos numéricos.");
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0 && JOptionPane.showConfirmDialog(this, "¿Eliminar?") == 0) {
            biblioteca.eliminarLibro((String) modeloTabla.getValueAt(fila, 0));
            cargarTabla(biblioteca.obtenerTodos());
        }
    }

    private void cargarTabla(java.util.List<Libro> lista) {
        modeloTabla.setRowCount(0);
        for (Libro l : lista) {
            modeloTabla.addRow(new Object[]{l.getIsbn(), l.getTitulo(), l.getAutor(), l.getGenero(), l.getAnioPublicacion(), l.getCopiasDisponibles()});
        }
    }
}
