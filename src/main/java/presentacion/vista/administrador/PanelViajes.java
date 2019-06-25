package presentacion.vista.administrador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

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
	private JLabel lblOrigenDestino;
	private JButton btnLimpiarFiltros;
	private JDateChooser dateDesde;
	private JDateChooser dateHasta;
	private JTextField txtFiltro;
	private JTextField txtPrecioDesde;
	private JTextField txtPrecioHasta;
	
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

		lblOrigenDestino = new JLabel("Origen / Destino");
		lblOrigenDestino.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOrigenDestino.setBounds(161, 86, 158, 19);
		add(lblOrigenDestino);

		
		JLabel label_1 = new JLabel("Fecha de Salida");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setBounds(469, 86, 183, 21);
		add(label_1);
		
		checkBoxAll = new JCheckBox("Todos");
		checkBoxAll.setBounds(30, 82, 95, 21);
		add(checkBoxAll);
		
		dateDesde = new JDateChooser();
		dateDesde.setBounds(398, 115, 131, 20);
		add(dateDesde);
		
		dateHasta = new JDateChooser();
		dateHasta.setBounds(545, 115, 131, 20);
		add(dateHasta);
		
		Label label_2 = new Label("Desde");
		label_2.setBounds(430, 141, 78, 14);
		add(label_2);
		
		JLabel label_3 = new JLabel("Hasta");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(586, 142, 78, 14);
		add(label_3);
		
		Label label_4 = new Label("Precio");
		label_4.setBounds(846, 86, 59, 21);
		add(label_4);

		
		JLabel label_5 = new JLabel("Desde");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_5.setBounds(805, 136, 57, 14);
		add(label_5);
		
		JLabel label_6 = new JLabel("Hasta");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_6.setBounds(887, 136, 57, 14);
		add(label_6);
		
		btnLimpiarFiltros = new JButton("Listar Viajes sin filtro");
		btnLimpiarFiltros.setBounds(1032, 106, 150, 29);
		add(btnLimpiarFiltros);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(161, 113, 141, 20);
		add(txtFiltro);
		txtFiltro.setColumns(10);
		
		txtPrecioDesde = new JTextField();
		txtPrecioDesde.setBounds(780, 113, 78, 20);
		add(txtPrecioDesde);
		txtPrecioDesde.setColumns(10);
		
		txtPrecioHasta = new JTextField();
		txtPrecioHasta.setColumns(10);
		txtPrecioHasta.setBounds(869, 113, 78, 20);
		add(txtPrecioHasta);
	
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
		return lblOrigenDestino;
	}

	public void setLabel(JLabel label) {
		this.lblOrigenDestino = label;
	}

	public JTextField getTxtFiltro() {
		return txtFiltro;
	}

	public void setTextFiltro(JTextField textFiltro) {
		this.txtFiltro = textFiltro;
	}
	
	public JTextField getTxtPrecioDesde() {
		return txtPrecioDesde;
	}

	public void setTextPrecioDesde(JTextField txtPrecioDesde) {
		this.txtPrecioDesde = txtPrecioDesde;
	}

	public JTextField getTextPrecioHasta() {
		return txtPrecioHasta;
	}

	public void setTxtPrecioHasta(JTextField txtPrecioHasta) {
		this.txtPrecioHasta = txtPrecioHasta;
	}

	public JButton getBtnLimpiarFiltros() {
		return btnLimpiarFiltros;
	}

	public void setBtnLimpiarFiltros(JButton btnLimpiarFiltros) {
		this.btnLimpiarFiltros = btnLimpiarFiltros;
	}

	public JDateChooser getFechaDesde() {
		return dateDesde;
	}

	public void setDateDesde(JDateChooser dateDesde) {
		this.dateDesde = dateDesde;
	}

	public JDateChooser getFechaHasta() {
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
	
	public void limpiarFiltrosFechas() {
		((JTextField)dateDesde.getDateEditor().getUiComponent()).setText("");
		((JTextField)dateHasta.getDateEditor().getUiComponent()).setText("");
	}
}