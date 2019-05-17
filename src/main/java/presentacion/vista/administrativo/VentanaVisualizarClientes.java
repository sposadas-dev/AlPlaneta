package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaVisualizarClientes extends JFrame {

	private static VentanaVisualizarClientes INSTANCE;

	private DefaultTableModel modelClientes;
	private String[] nombreColumnasClientes = {"Nombre" , "Apellido", "DNI","Fecha de nacimiento", "Teléfono Fijo","Teléfono Celular","Email"};
	private JTable tablaClientes;
	private JButton btnConfirmar;

	public VentanaVisualizarClientes() {
		setTitle("Agregar pasaje - Selección de cliente");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(180, 100, 1135, 555);
		setLocationRelativeTo(null);	
		setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panelPasaje = new JPanel();
		panelPasaje.setBackground(new Color(230, 126, 34));
		panelPasaje.setBounds(0, 0, 1308, 53);
		getContentPane().add(panelPasaje);
		panelPasaje.setLayout(null);
		
		JLabel lblAgregarReserva = new JLabel("Agregar pasaje");
		lblAgregarReserva.setForeground(Color.WHITE);
		lblAgregarReserva.setBounds(26, 0, 210, 53);
		lblAgregarReserva.setFont(new Font("Tahoma", Font.BOLD, 24));
		panelPasaje.add(lblAgregarReserva);
		
		JPanel panelSeleccioneCliente = new JPanel();
		panelSeleccioneCliente.setBackground(new Color(30, 144, 255));
		panelSeleccioneCliente.setBounds(0, 54, 1308, 37);
		getContentPane().add(panelSeleccioneCliente);
		panelSeleccioneCliente.setLayout(null);
		
		JLabel lblSeleccionarPasaje = new JLabel("Seleccione un cliente");
		lblSeleccionarPasaje.setForeground(Color.WHITE);
		lblSeleccionarPasaje.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSeleccionarPasaje.setBounds(488, 0, 296, 31);
		panelSeleccioneCliente.add(lblSeleccionarPasaje);
		
		modelClientes = new DefaultTableModel(null,nombreColumnasClientes){
			@Override
				public boolean isCellEditable(int row, int column){
					return false;
				}
			};
			getContentPane().setLayout(null);

			JScrollPane spPasajeros = new JScrollPane();
			spPasajeros.setBounds(10, 113, 1109, 318);
			getContentPane().add(spPasajeros);
			tablaClientes = new JTable(modelClientes);
			spPasajeros.setViewportView(tablaClientes);
			
			btnConfirmar = new JButton("Confirmar");
			btnConfirmar.setBounds(485, 442, 136, 54);
			getContentPane().add(btnConfirmar);

	}
	
	public DefaultTableModel getModelClientes() {
		return modelClientes;
	}

	public String[] getNombreColumnasClientes() {
		return nombreColumnasClientes;
	}

	public JTable getTablaClientes() {
		return tablaClientes;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	
	public static VentanaVisualizarClientes getInstance(){
		if(INSTANCE == null)
			return new VentanaVisualizarClientes();
		else
			return INSTANCE;
	}

}
