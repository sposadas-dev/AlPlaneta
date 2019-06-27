package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelFormaPago  extends JPanel {
	private static final long serialVersionUID = 1L;

	private DefaultTableModel modelFormaPago;
	private String[] nombreColumnasformaPago = {"Diferentes Formas de Pago"};
	private JTable tablaFormaPago;

	@SuppressWarnings("serial")
	public PanelFormaPago() {
		
		modelFormaPago = new DefaultTableModel(null,nombreColumnasformaPago){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);

		JScrollPane formaPago = new JScrollPane();
		formaPago.setBounds(32, 96, 1302, 483);
		add(formaPago);
		tablaFormaPago = new JTable(modelFormaPago);
		formaPago.setViewportView(tablaFormaPago);

		JPanel panelFomaPago = new JPanel();
		panelFomaPago.setBackground(new Color(96, 163, 188));
		panelFomaPago.setBounds(32, 32, 1302, 64);
		add(panelFomaPago);
		panelFomaPago.setLayout(null);
		
		JLabel lblFormaPago = new JLabel("Formas de pago");
		lblFormaPago.setBounds(453, 0, 301, 65);
		lblFormaPago.setForeground(Color.WHITE);
		lblFormaPago.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelFomaPago.add(lblFormaPago);
	}

	public void mostrarPanelFormaPago(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public DefaultTableModel getModelFormaPago() {
		return modelFormaPago;
	}

	public void setModelFormaPagp(DefaultTableModel modelFormaPago) {
		this.modelFormaPago = modelFormaPago;
	}

	public String[] getNombreColumnasFormaPago() {
		return nombreColumnasformaPago;
	}

	public void setNombreColumnasFormaPago(String[] nombreColumnasFormaPago) {
		this.nombreColumnasformaPago = nombreColumnasFormaPago;
	}

	public JTable getTablaFormaPago() {
		return tablaFormaPago;
	}

	public void setTablaFormaPago(JTable tablaFormaPago) {
		this.tablaFormaPago = tablaFormaPago;
	}
}