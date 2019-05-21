package presentacion.vista.administrador;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import persistencia.conexion.Conexion;

public class TableroDePaises{
	private JFrame frmPais;
	private JTable tablaPaises;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnReporte;
	private JButton btnEditar ;
	private DefaultTableModel modelPaises;
	private JButton btnConexion;
	private static TableroDePaises INSTANCE;
	private  String[] nombreColumnas = {"Nombre"};
	
	
	public static TableroDePaises getInstance(){
		if(INSTANCE == null)
			return new TableroDePaises();
		else
			return INSTANCE;
	}

	private TableroDePaises(){
		super();
		initialize();
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}

	public static void adjustColumnPreferredWidths(JTable table) { 
		TableColumnModel columnModel = table.getColumnModel();
		for (int col=0; col<table.getColumnCount(); col++) {
			int maxwidth = 0; 
			for (int row=0; row<table.getRowCount(); row++) {
				TableCellRenderer rend = table.getCellRenderer(row, col); 
				Object value = table.getValueAt (row, col); 
				Component comp = rend.getTableCellRendererComponent (table, value, false, false, row, col);
				maxwidth = Math.max (comp.getPreferredSize().width, maxwidth); 
			}
			TableColumn column = columnModel.getColumn (col); 
			column.setPreferredWidth (maxwidth); 
		}
	}
	
	private void initialize() {
		frmPais = new JFrame();
		frmPais.setResizable(false);
		frmPais.setTitle("Paises");
		frmPais.setBounds(100, 100, 657, 366);
		frmPais.setLocationRelativeTo(null);
		frmPais.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmPais.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 0, 627, 306);
		frmPais.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane spPersonas = new JScrollPane();
		spPersonas.setEnabled(false);
		spPersonas.setBounds(0, 11, 621, 183);
		panel.add(spPersonas);
		
		modelPaises = new DefaultTableModel(null,nombreColumnas) {
			private static final long serialVersionUID = 1L;
			@Override
             public boolean isCellEditable(int row, int col) {
                 return false;
             }	
		};
		tablaPaises = new JTable(modelPaises);
		adjustColumnPreferredWidths(tablaPaises);
		spPersonas.setViewportView(tablaPaises);
		
		btnAgregar = new JButton("Agregar pais");
		btnAgregar.setBounds(9, 207, 181, 23);
		panel.add(btnAgregar);
		
		btnEditar = new JButton("Editar pais");
		btnEditar.setBounds(200, 207, 181, 23);
		panel.add(btnEditar);
		
		btnBorrar = new JButton("Borrar pais");
		btnBorrar.setBounds(391, 205, 181, 23);
		panel.add(btnBorrar);
	}
	
	public void show(){
		this.frmPais.setVisible(true);
	}
	
	public JButton getBtnAgregar(){
		return btnAgregar;
	}

	public JButton getBtnBorrar() {
		return btnBorrar;
	}
	
	public JButton getBtnReporte(){
		return btnReporte;
	}
	
	public DefaultTableModel getModelPaises() {
		return modelPaises;
	}
	
	public JTable getTablaPaises(){
		return tablaPaises;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}
	
	public void setTablaPaises(JTable tablaPaises) {
		this.tablaPaises = tablaPaises;
	}

	public void setBtnAgregar(JButton btnAgregar) {
		this.btnAgregar = btnAgregar;
	}

	public void setBtnBorrar(JButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public JButton getBtnConexion() {
		return btnConexion;
	}
	

}