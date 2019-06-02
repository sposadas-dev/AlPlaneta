package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class VentanaVisualizarClientes extends JFrame {

	private static VentanaVisualizarClientes INSTANCE;

	private DefaultTableModel modelClientes;
	private String[] nombreColumnasClientes = {"Nombre" , "Apellido", "DNI","Fecha de nacimiento", "Teléfono Fijo","Teléfono Celular","Email"};
	private JTable tablaClientes;
	private JButton btnConfirmar;
	
	private JComboBox<String>comboBoxFiltro;
	private JTextField txtFiltro;
	private JButton btnAplicarFiltro;

	public static VentanaVisualizarClientes getInstance(){
		if(INSTANCE == null)
			return new VentanaVisualizarClientes();
		else
			return INSTANCE;
	}
	
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
			spPasajeros.setBounds(10, 163, 1109, 268);
			getContentPane().add(spPasajeros);
			tablaClientes = new JTable(modelClientes);
			spPasajeros.setViewportView(tablaClientes);
			
			btnConfirmar = new JButton("Confirmar");
			btnConfirmar.setBounds(485, 442, 136, 54);
			getContentPane().add(btnConfirmar);
			
			JLabel lblFiltro = new JLabel("Filtrar por:");
			lblFiltro.setBounds(203, 117, 98, 14);
			getContentPane().add(lblFiltro);
			
			comboBoxFiltro = new JComboBox<String>();
			comboBoxFiltro.setBounds(291, 114, 210, 20);
			comboBoxFiltro.addItem("Seleccione");
			comboBoxFiltro.addItem("DNI");
			getContentPane().add(comboBoxFiltro);
			
			txtFiltro = new JTextField();
			txtFiltro.setBounds(523, 114, 238, 20);
			getContentPane().add(txtFiltro);
			txtFiltro.setColumns(10);
			
			btnAplicarFiltro = new JButton("Aplicar filtro");
			btnAplicarFiltro.setBounds(811, 113, 158, 23);
			getContentPane().add(btnAplicarFiltro);

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

	public JComboBox<String> getComboBoxFiltro() {
		return comboBoxFiltro;
	}

	public JTextField getTxtFiltro() {
		return txtFiltro;
	}

	public JButton getBtnAplicarFiltro() {
		return btnAplicarFiltro;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
}