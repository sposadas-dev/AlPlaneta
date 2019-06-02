package presentacion.vista.administrativo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

public class PanelCliente extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnRecargarTabla;
	private DefaultTableModel modelClientes;
	private String[] nombreColumnasClientes = {"Nombre" , "Apellido", "DNI","Fecha de nacimiento", "Teléfono Fijo","Teléfono Celular","Email", "Estado"};
	private JTable tablaClientes;
	private JButton btnConfirmar;
	private JLabel lblClientes;

	
	public PanelCliente() {
		
		modelClientes = new DefaultTableModel(null,nombreColumnasClientes){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);

		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(32, 91, 1302, 393);
		add(spPasajeros);
		tablaClientes = new JTable(modelClientes);
		spPasajeros.setViewportView(tablaClientes);
		
//		btnRecargarTabla = new JButton("Recargar");
//		btnRecargarTabla.setBounds(421, 495, 165, 54);
//		add(btnRecargarTabla);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(661, 495, 136, 54);
		add(btnConfirmar);
		
		JPanel panelClientes = new JPanel();
		panelClientes.setBackground(new Color(39, 174, 96));
		panelClientes.setBounds(32, 32, 1302, 64);
		add(panelClientes);
		panelClientes.setLayout(null);
		
		lblClientes = new JLabel("Clientes");
		lblClientes.setBounds(549, 0, 219, 65);
		lblClientes.setForeground(Color.WHITE);
		lblClientes.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelClientes.add(lblClientes);
	
		btnConfirmar.setVisible(false);
//		btnRecargarTabla.setVisible(false);
	}

	public void mostrarPanelCliente(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
//	public JButton getBtnRecargarTabla() {
//		return btnRecargarTabla;
//	}

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
	
	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
}