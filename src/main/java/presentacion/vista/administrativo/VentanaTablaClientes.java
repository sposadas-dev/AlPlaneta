package presentacion.vista.administrativo;

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

public class VentanaTablaClientes extends JFrame {

	private static VentanaTablaClientes INSTANCE;
	private JPanel contentPane;
	private JTable tablaClientes;
	private DefaultTableModel modelClientes;
	private JButton btnConfirmar;
	private JButton btnAtras;
	private String[] nombreColumnasClientes = {"Nombre" , "Apellido", "DNI","Fecha de nacimiento", "Teléfono Fijo","Teléfono Celular","Email"};

	public static VentanaTablaClientes getInstance(){
		if(INSTANCE == null)
			return new VentanaTablaClientes();
		else
			return INSTANCE;
	}
	
	private VentanaTablaClientes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Selección de cliente");
		setBounds(250, 200, 975, 525);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 42, 928, 334);
		contentPane.add(spPasajeros);
		
		modelClientes = new DefaultTableModel(null,nombreColumnasClientes){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaClientes = new JTable(modelClientes);
		spPasajeros.setViewportView(tablaClientes);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(442, 395, 150, 49);
		contentPane.add(btnConfirmar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(249, 395, 150, 49);
		contentPane.add(btnAtras);
		
		this.setVisible(false);
	}

	public void mostrarVentana(boolean b) {
		this.setVisible(b);
	}

	public JTable getTablaClientes() {
		return tablaClientes;
	}

	public void setTablaClientes(JTable tablaCliente) {
		this.tablaClientes = tablaCliente;
	}

	public DefaultTableModel getModelClientes() {
		return modelClientes;
	}

	public void setModelClientes(DefaultTableModel modelClientes) {
		this.modelClientes = modelClientes;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JButton getBtnAtras() {
		return btnAtras;
	}
	
	public String[] getNombreColumnas() {
		return nombreColumnasClientes;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnasClientes = nombreColumnas;
	}
}