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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCargaPasajero extends JFrame {

	private static VentanaCargaPasajero INSTANCE;
	private JPanel contentPane;
	private JTable tablaPasajeros;
	private DefaultTableModel modelPasajeros;
	private JButton btnEliminar; 
	private JButton btnAgregarPasajero; 
	private JButton btnConfirmar;
	private  String[] nombreColumnas = {"Nombre" , "Apellido", "DNI","Fecha de nacimiento","Teléfono","Email"};

	public static VentanaCargaPasajero getInstance(){
		if(INSTANCE == null)
			return new VentanaCargaPasajero();
		else
			return INSTANCE;
	}
	
	private VentanaCargaPasajero() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Carga de pasajeros");
		setBounds(100, 100, 545, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAgregarPasajero = new JButton("Agregar");
		btnAgregarPasajero.setBounds(410, 85, 109, 37);
		contentPane.add(btnAgregarPasajero);
		
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
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(410, 159, 109, 37);
		contentPane.add(btnEliminar);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(278, 319, 109, 37);
		contentPane.add(btnConfirmar);
		
		JButton btnAtras = new JButton("Atrás");
		btnAtras.setBounds(111, 319, 109, 37);
		contentPane.add(btnAtras);
		
		this.setVisible(false);
	}
	
	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}

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

	public void mostrarVentana(boolean b) {
		this.setVisible(b);
	}

	public JButton getBtnAgregarPasajero() {
		return btnAgregarPasajero;
	}

	public void setBtnAgregarPasajero(JButton btnAgregarPasajero) {
		this.btnAgregarPasajero = btnAgregarPasajero;
	}

	public JTable getTablaPasajeros() {
		return tablaPasajeros;
	}

	public void setTablaPasajeros(JTable tablaPasajeros) {
		this.tablaPasajeros = tablaPasajeros;
	}

	public DefaultTableModel getModelPasajeros() {
		return modelPasajeros;
	}

	public void setModelPasajeros(DefaultTableModel modelPasajeros) {
		this.modelPasajeros = modelPasajeros;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}
}