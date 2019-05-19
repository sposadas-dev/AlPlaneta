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

public class TableroDeProvincias{
	private JFrame frmProvincia;
	private JTable tablaProvincia;
	private JButton btnAgregar;
	private JButton btnBorrar;
	private JButton btnReporte;
	private JButton btnEditar ;
	private DefaultTableModel modelProvincia;
	private JButton btnConexion;
	private static TableroDeProvincias INSTANCE;
	private  String[] nombreColumnas = {" Pais"," Provincia"};
	
	
	public static TableroDeProvincias getInstance(){
		if(INSTANCE == null)
			return new TableroDeProvincias();
		else
			return INSTANCE;
	}

	private TableroDeProvincias(){
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
		frmProvincia = new JFrame();
		frmProvincia.setResizable(false);
		frmProvincia.setTitle("Provincias");
		frmProvincia.setBounds(100, 100, 657, 366);
		frmProvincia.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProvincia.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 0, 627, 306);
		frmProvincia.getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane spPersonas = new JScrollPane();
		spPersonas.setEnabled(false);
		spPersonas.setBounds(0, 11, 621, 183);
		panel.add(spPersonas);
		
		modelProvincia = new DefaultTableModel(null,nombreColumnas) {
			private static final long serialVersionUID = 1L;
			@Override
             public boolean isCellEditable(int row, int col) {
                 return false;
             }	
		};
		tablaProvincia = new JTable(modelProvincia);
		adjustColumnPreferredWidths(tablaProvincia);
		spPersonas.setViewportView(tablaProvincia);
		
		btnAgregar = new JButton("AGREGAR PROVINCIA");
		btnAgregar.setBounds(9, 207, 181, 23);
		panel.add(btnAgregar);
		
		btnEditar = new JButton("EDITAR PROVINCIA");
		btnEditar.setBounds(200, 207, 181, 23);
		panel.add(btnEditar);
		
		btnBorrar = new JButton("BORRAR PROVINCIA");
		btnBorrar.setBounds(391, 205, 181, 23);
		panel.add(btnBorrar);
	}
	
	public void show(){
		this.frmProvincia.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frmProvincia.addWindowListener(new WindowAdapter(){
			@Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, "Estas seguro que quieres salir de la vista de provincia!?", 
		             "Confirmacion", JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		        	Conexion.getConexion().cerrarConexion();
		           System.exit(0);
		        }
		    }
		});
		this.frmProvincia.setVisible(true);
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
	
	public DefaultTableModel getModelProvincias() {
		return modelProvincia;
	}
	
	public JTable getTablaProvincias(){
		return tablaProvincia;
	}

	public String[] getNombreColumnas() {
		return nombreColumnas;
	}
	
	public void setTablaProvincias(JTable tablaPaises) {
		this.tablaProvincia = tablaPaises;
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