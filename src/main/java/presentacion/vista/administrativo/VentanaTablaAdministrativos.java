package presentacion.vista.administrativo;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VentanaTablaAdministrativos extends JFrame {
	private static final long serialVersionUID = 1L;
	private static VentanaTablaAdministrativos INSTANCE;
	private JPanel contentPane;
	private JTable tablaAdministrativos;
	private DefaultTableModel modelAdministrativos;
	private JButton btnConfirmar;
	private JButton btnAtras;
	private String[] nombreColumnasAdministrativos = {"Apellido" , "Nombre", "DNI","Mail"};

	public static VentanaTablaAdministrativos getInstance(){
		if(INSTANCE == null)
			return new VentanaTablaAdministrativos();
		else
			return INSTANCE;
	}
	
	private VentanaTablaAdministrativos() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Selección de Administrativo");
		setBounds(250, 200, 975, 525);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10, 42, 928, 334);
		contentPane.add(scroll);
		
		modelAdministrativos = new DefaultTableModel(null,nombreColumnasAdministrativos){
			private static final long serialVersionUID = 1L;
			@Override
				public boolean isCellEditable(int row, int column){ return false;}
		};
		tablaAdministrativos = new JTable(modelAdministrativos);
		scroll.setViewportView(tablaAdministrativos);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(442, 395, 150, 49);
		contentPane.add(btnConfirmar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(249, 395, 150, 49);
		contentPane.add(btnAtras);
		
		this.setVisible(false);
	}

	public void mostrarVentana(boolean b) {
		this.setVisible(b);
	}

	public JTable getTablaAdministrativos() {
		return tablaAdministrativos;
	}

	public void setTablaAdministrativos(JTable tablaAdministrativos) {
		this.tablaAdministrativos = tablaAdministrativos;
	}

	public DefaultTableModel getModelAdministrativos() {
		return modelAdministrativos;
	}

	public void setModelAdministrativos(DefaultTableModel modelAdministrativos) {
		this.modelAdministrativos = modelAdministrativos;
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public void setBtnConfirmar(JButton btnConfirmar) {
		this.btnConfirmar = btnConfirmar;
	}

	public JButton getBtnAtras() {
		return btnAtras;
	}
	
	public String[] getNombreColumnas() {
		return nombreColumnasAdministrativos;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnasAdministrativos = nombreColumnas;
	}
}