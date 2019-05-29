package presentacion.vista.administrativo;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class PanelEvento extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelEventos;
	private String[] nombreColumnasEventos = {"ID Evento","Fecha de Ingreso", "Fecha del Evento", "Hora del Evento", "Descripción" , "Apellido Cliente","Nombre Cliente", "Administrativo", "Estado del Evento"};
	private JTable tablaEventos;

	public PanelEvento() {
		
		modelEventos = new DefaultTableModel(null,nombreColumnasEventos){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
			
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 81, 1342, 529);
		add(spPasajeros);
		tablaEventos = new JTable(modelEventos);
		spPasajeros.setViewportView(tablaEventos);
		
		JPanel panelEventos = new JPanel();
		panelEventos.setBackground(Color.PINK);
		panelEventos.setBounds(10, 11, 1342, 69);
		add(panelEventos);
		panelEventos.setLayout(null);
		
		JLabel lblEventos = new JLabel("Eventos");
		lblEventos.setForeground(Color.WHITE);
		lblEventos.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblEventos.setBounds(589, 0, 219, 65);
		panelEventos.add(lblEventos);
	}
	
	public void mostrarPanelEvento(boolean visibilidad){
		this.setVisible(visibilidad);
	}

	public DefaultTableModel getModelEventos() {
		return modelEventos;
	}

	public void setModelEventos(DefaultTableModel modelEventos) {
		this.modelEventos = modelEventos;
	}

	public String[] getNombreColumnasEventos() {
		return nombreColumnasEventos;
	}

	public void setNombreColumnasEventos(String[] nombreColumnasEventos) {
		this.nombreColumnasEventos = nombreColumnasEventos;
	}

	public JTable getTablaEventos() {
		return tablaEventos;
	}

	public void setTablaEventos(JTable tablaEventos) {
		this.tablaEventos = tablaEventos;
	}

}