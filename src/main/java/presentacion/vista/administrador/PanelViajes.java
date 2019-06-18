package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Label;
import javax.swing.JComboBox;

public class PanelViajes extends JPanel {

	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelViajes;
	private  String[] nombreColumnasViajes = {"Origen","Destino","Fecha de salida","Fecha de llegada","Hora de salida","Horas estimadas","Capacidad","Transporte","Precio","Estado"};
	private JTable tablaViajes;
	private JButton btnConfirmar;
	private JLabel lblEmpleados;
	private JCheckBox activos;
	private JCheckBox inactivos;
	private JCheckBox checkBoxAll;
	private JLabel label;
	private JTextField textFiltro;
	private JComboBox<String> comboBoxPrecioDesde; 
	private JComboBox<String> comboBoxPrecioHasta;
	private JButton btnLimpiarFiltros;
	private JDateChooser dateDesde;
	private JDateChooser dateHasta;
	
	@SuppressWarnings("serial")
	public PanelViajes() {
		
		modelViajes = new DefaultTableModel(null,nombreColumnasViajes){
		@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(32, 166, 1302, 413);
		add(spPasajeros);
		tablaViajes = new JTable(modelViajes);
		spPasajeros.setViewportView(tablaViajes);
		

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(640, 608, 136, 54);
		add(btnConfirmar);
		
		JPanel panelTransporte = new JPanel();
		panelTransporte.setBackground(new Color(87, 95, 207));
		panelTransporte.setBounds(20, 11, 1302, 64);
		add(panelTransporte);
		panelTransporte.setLayout(null);
		
		lblEmpleados = new JLabel("Viajes");
		lblEmpleados.setBounds(551, 0, 219, 65);
		lblEmpleados.setForeground(Color.WHITE);
		lblEmpleados.setFont(new Font("Tahoma", Font.BOLD, 36));
		panelTransporte.add(lblEmpleados);
		
		activos = new JCheckBox("Activos");
		activos.setBounds(32, 106, 95, 21);
		activos.setSelected(true);
		add(activos);
		
		inactivos = new JCheckBox("Inactivos");
		inactivos.setBounds(32, 130, 95, 21);
		add(inactivos);
		
		label = new JLabel("Filtro: Origen ó Destino");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label.setBounds(131, 86, 158, 19);
		add(label);
		
		textFiltro = new JTextField();
		textFiltro.setColumns(10);
		textFiltro.setBounds(133, 116, 158, 19);
		add(textFiltro);
		
		JLabel label_1 = new JLabel("Fecha de Salida");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setBounds(395, 86, 183, 14);
		add(label_1);
		
		checkBoxAll = new JCheckBox("Todos");
		checkBoxAll.setBounds(30, 82, 95, 21);
		add(checkBoxAll);
		
		dateDesde = new JDateChooser();
		dateDesde.setBounds(314, 115, 131, 20);
		add(dateDesde);
		
		dateHasta = new JDateChooser();
		dateHasta.setBounds(461, 115, 131, 20);
		add(dateHasta);
		
		Label label_2 = new Label("Desde");
		label_2.setBounds(336, 141, 78, 18);
		add(label_2);
		
		JLabel label_3 = new JLabel("Hasta");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(484, 141, 78, 14);
		add(label_3);
		
		Label label_4 = new Label("Precio");
		label_4.setBounds(732, 81, 59, 21);
		add(label_4);
		
		
		comboBoxPrecioDesde = new JComboBox<String>();
		comboBoxPrecioDesde.setBounds(650, 116, 81, 21);
		add(comboBoxPrecioDesde);
		
		comboBoxPrecioDesde.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0)
					if(comboBoxPrecioDesde.getSelectedIndex() < comboBoxPrecioDesde.getItemCount()-1) 
						comboBoxPrecioDesde.setSelectedIndex(comboBoxPrecioDesde.getSelectedIndex()+1);
				if(e.getWheelRotation()<0)
					if(comboBoxPrecioDesde.getSelectedIndex() > 0)
						comboBoxPrecioDesde.setSelectedIndex(comboBoxPrecioDesde.getSelectedIndex()-1);
			}
		});
		
		comboBoxPrecioHasta = new JComboBox<String>();
		comboBoxPrecioHasta.setBounds(765, 116, 78, 21);
		add(comboBoxPrecioHasta);
		
		comboBoxPrecioHasta.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0)
					if(comboBoxPrecioHasta.getSelectedIndex() < comboBoxPrecioHasta.getItemCount()-1) 
						comboBoxPrecioHasta.setSelectedIndex(comboBoxPrecioHasta.getSelectedIndex()+1);
				if(e.getWheelRotation()<0)
					if(comboBoxPrecioHasta.getSelectedIndex() > 0)
						comboBoxPrecioHasta.setSelectedIndex(comboBoxPrecioHasta.getSelectedIndex()-1);
			}
		});

		
		JLabel label_5 = new JLabel("Desde");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_5.setBounds(660, 136, 57, 19);
		add(label_5);
		
		JLabel label_6 = new JLabel("Hasta");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_6.setBounds(775, 141, 57, 14);
		add(label_6);
		
		btnLimpiarFiltros = new JButton("Listar Viajes sin filtro");
		btnLimpiarFiltros.setBounds(873, 105, 150, 19);
		add(btnLimpiarFiltros);
	
		btnConfirmar.setVisible(false);
	
	}

	public void mostrarPanelViajes(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JCheckBox getActivos() {
		return activos;
	}
	
	public JCheckBox getInactivos() {
		return inactivos;
	}

	public DefaultTableModel getModelViajes() {
		return modelViajes;
	}

	public String[] getNombreColumnasViajes() {
		return nombreColumnasViajes;
	}

	public JTable getTablaViajes() {
		return tablaViajes;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public JCheckBox getCheckBoxAll() {
		return checkBoxAll;
	}

	public void setCheckBoxAll(JCheckBox checkBoxAll) {
		this.checkBoxAll = checkBoxAll;
	}

	public Window getBtnAtras() {
		// TODO Auto-generated method stub
		return null;
	}

	public JLabel getLblEmpleados() {
		return lblEmpleados;
	}

	public void setLblEmpleados(JLabel lblEmpleados) {
		this.lblEmpleados = lblEmpleados;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JTextField getTxtFiltro() {
		return textFiltro;
	}

	public void setTextFiltro(JTextField textFiltro) {
		this.textFiltro = textFiltro;
	}

	public JComboBox<String> getComboBoxPrecioDesde() {
		return comboBoxPrecioDesde;
	}

	public void setComboBoxPrecioDesde(JComboBox<String> comboBoxPrecioDesde) {
		this.comboBoxPrecioDesde = comboBoxPrecioDesde;
	}

	public JComboBox<String> getComboBoxPrecioHasta() {
		return comboBoxPrecioHasta;
	}

	public void setComboBoxPrecioHasta(JComboBox<String> comboBoxPrecioHasta) {
		this.comboBoxPrecioHasta = comboBoxPrecioHasta;
	}

	public JButton getBtnLimpiarFiltros() {
		return btnLimpiarFiltros;
	}

	public void setBtnLimpiarFiltros(JButton btnLimpiarFiltros) {
		this.btnLimpiarFiltros = btnLimpiarFiltros;
	}

	public JDateChooser getFiltroDesde() {
		return dateDesde;
	}

	public void setDateDesde(JDateChooser dateDesde) {
		this.dateDesde = dateDesde;
	}

	public JDateChooser getFiltroHasta() {
		return dateHasta;
	}

	public void setDateHasta(JDateChooser dateHasta) {
		this.dateHasta = dateHasta;
	}

	public void setModelViajes(DefaultTableModel modelViajes) {
		this.modelViajes = modelViajes;
	}

	public void setNombreColumnasViajes(String[] nombreColumnasViajes) {
		this.nombreColumnasViajes = nombreColumnasViajes;
	}

	public void setTablaViajes(JTable tablaViajes) {
		this.tablaViajes = tablaViajes;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public void setActivos(JCheckBox activos) {
		this.activos = activos;
	}

	public void setInactivos(JCheckBox inactivos) {
		this.inactivos = inactivos;
	}
	
	
}