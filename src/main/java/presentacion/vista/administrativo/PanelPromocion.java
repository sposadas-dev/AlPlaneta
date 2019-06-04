package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class PanelPromocion extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelPromocion;
	private String[] nombreColumnasPromocion = {"Porcentaje", "Stock", "Fecha de Vencimiento","Origen del viaje","Destino del viaje","Fecha salida","Fecha llegada","Estado"};
	private JTable tablaPromociones;

	public PanelPromocion() {
		
		modelPromocion = new DefaultTableModel(null,nombreColumnasPromocion){
		private static final long serialVersionUID = 1L;

		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
			
		JScrollPane spPromocion = new JScrollPane();
		spPromocion.setBounds(10, 81, 1342, 529);
		add(spPromocion);
		tablaPromociones = new JTable(modelPromocion);
		spPromocion.setViewportView(tablaPromociones);
		
		JPanel panelPromocion = new JPanel();
		panelPromocion.setBackground(new Color(139, 219, 6));
		panelPromocion.setBounds(10, 11, 1342, 69);
		add(panelPromocion);
		panelPromocion.setLayout(null);
		
		JLabel lblPromocion = new JLabel("Promociones");
		lblPromocion.setHorizontalAlignment(SwingConstants.CENTER);
		lblPromocion.setForeground(Color.WHITE);
		lblPromocion.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblPromocion.setBounds(0, 0, 1342, 65);
		panelPromocion.add(lblPromocion);
	}
	
	public void mostrarPanelPromocion(boolean visibilidad){
		this.setVisible(visibilidad);
	}

	public DefaultTableModel getModelPromocion() {
		return modelPromocion;
	}

	public void setModelPromocion(DefaultTableModel modelPromocion) {
		this.modelPromocion = modelPromocion;
	}

	public String[] getNombreColumnasPromociones() {
		return nombreColumnasPromocion;
	}

	public void setNombreColumnasPromocion(String[] nombreColumnasPromocion) {
		this.nombreColumnasPromocion = nombreColumnasPromocion;
	}

	public JTable getTablaPromocion() {
		return tablaPromociones;
	}

	public void setTablaPromocion(JTable tablaPromocion) {
		this.tablaPromociones = tablaPromocion;
	}

}