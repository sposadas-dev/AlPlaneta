package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class VentanaCargaPasajero extends JFrame {

	private JPanel contentPane;
	private JTable tablaPasajeros;
	private DefaultTableModel modelPasajeros;
	private  String[] nombreColumnas = {"Nombre" , "Apellido","DNI"};

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCargaPasajero frame = new VentanaCargaPasajero();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaCargaPasajero() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Carga de pasajeros");
		setBounds(100, 100, 545, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton agregarPasajero = new JButton("Agregar");
		agregarPasajero.setBounds(410, 85, 109, 37);
		contentPane.add(agregarPasajero);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 42, 390, 258);
		contentPane.add(spPasajeros);
		
		modelPasajeros = new DefaultTableModel(null,nombreColumnas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaPasajeros = new JTable(modelPasajeros);
		spPasajeros.setViewportView(tablaPasajeros);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(410, 159, 109, 37);
		contentPane.add(btnEliminar);
	}
}