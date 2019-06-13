package presentacion.vista.administrativo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import javax.swing.SwingConstants;


public class PanelCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelClientes;
	private String[] nombreColumnasClientes = {"Nombre" , "Apellido", "DNI","Fecha de nacimiento", "Teléfono Fijo","Teléfono Celular","Email", "Estado"};
	private JTable tablaClientes;
	private JButton btnConfirmar;
	private JLabel lblClientes;
	private JButton btnAgregar;
	private JButton btnEditar;
	private JCheckBox activos;
	private JCheckBox inactivos;
	private JTextField txtFiltro;
	
	public PanelCliente() {
		
		modelClientes = new DefaultTableModel(null,nombreColumnasClientes){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);

		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 127, 1337, 527);
		add(spPasajeros);
		tablaClientes = new JTable(modelClientes);
		spPasajeros.setViewportView(tablaClientes);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(661, 495, 136, 54);
		add(btnConfirmar);
		
		JPanel panelClientes = new JPanel();
		panelClientes.setBackground(new Color(39, 174, 96));
		panelClientes.setBounds(10, 0, 1353, 61);
		add(panelClientes);
		panelClientes.setLayout(null);
		
		lblClientes = new JLabel("Clientes");
		lblClientes.setBounds(549, 0, 219, 65);
		lblClientes.setForeground(Color.WHITE);
		lblClientes.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelClientes.add(lblClientes);
		
		activos = new JCheckBox("Activos");
		activos.setBounds(1142, 83, 95, 21);
		activos.setSelected(true);
		add(activos);
		
		inactivos = new JCheckBox("Inactivos");
		inactivos.setBounds(1239, 82, 95, 21);
		add(inactivos);
		
		btnAgregar = new JButton("");
		btnAgregar.setVerticalAlignment(SwingConstants.TOP);
		btnAgregar.setToolTipText("Registrar cliente");
		btnAgregar.setIcon(new ImageIcon(PanelCliente.class.getResource("/recursos/agregarCliente.png")));
		btnAgregar.setBounds(10, 61, 68, 54);
		add(btnAgregar);
		
		
	    btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(PanelCliente.class.getResource("/recursos/editarCliente.png")));
		btnEditar.setBounds(77, 61, 68, 54);
		add(btnEditar);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(553, 83, 266, 20);
		add(txtFiltro);
		txtFiltro.setColumns(10);
		
		JLabel lblFiltro = new JLabel("");
		lblFiltro.setIcon(new ImageIcon(PanelCliente.class.getResource("/recursos/filtroCliente.png")));
		lblFiltro.setBounds(489, 61, 75, 54);
		add(lblFiltro);
	
		btnConfirmar.setVisible(false);
	}

	public void mostrarPanelCliente(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JCheckBox getActivos() {
		return activos;
	}
	
	public JCheckBox getInactivos() {
		return inactivos;
	}

	public DefaultTableModel getModelClientes() {
		return modelClientes;
	}

	public void setModelClientes(DefaultTableModel modelClientes) {
		this.modelClientes = modelClientes;
	}

	public String[] getNombreColumnasClientes() {
		return nombreColumnasClientes;
	}

	public void setNombreColumnasClientes(String[] nombreColumnasClientes) {
		this.nombreColumnasClientes = nombreColumnasClientes;
	}

	public JTable getTablaClientes() {
		return tablaClientes;
	}

	public void setTablaClientes(JTable tablaClientes) {
		this.tablaClientes = tablaClientes;
	}
	
	public JButton getBtnAgregar() {
		return btnAgregar;
	}
	
	public JButton getBtnEditar() {
		return btnEditar;
	}

	public JTextField getTxtFiltro() {
		return txtFiltro;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}