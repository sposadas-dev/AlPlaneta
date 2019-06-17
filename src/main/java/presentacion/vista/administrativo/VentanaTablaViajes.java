package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class VentanaTablaViajes extends JFrame {

	private static VentanaTablaViajes INSTANCE;
	private JPanel contentPane;
	private JTable tablaViajes;
	private DefaultTableModel modelViajes;
	private JButton btnConfirmar;
	private JButton btnAtras;
	JButton btnLimpiarFiltros;
	private  String[] nombreColumnas = {"Origen","Destino","Fecha de salida","Fecha de llegada","Hora de salida","Horas estimadas","Capacidad","Transporte","Precio"};
	private JTextField txtFiltro;
	private JLabel lblFiltro;
	private JDateChooser dateDesde;
	private JDateChooser dateHasta;
	private JLabel lblPresioneLaTecla;
	private Label label_1;
	private JLabel lblDesde;
	private JLabel lblHasta_1;
	private JComboBox<String> comboBoxPrecioDesde;

	private JComboBox<String> comboBoxPrecioHasta;

	public static VentanaTablaViajes getInstance(){
		if(INSTANCE == null)
			INSTANCE =  new VentanaTablaViajes();
		return INSTANCE;
	}
	
	private VentanaTablaViajes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Carga de viaje");
		setBounds(250, 200, 975, 525);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane spPasajeros = new JScrollPane();
		spPasajeros.setBounds(10, 149, 928, 227);
		contentPane.add(spPasajeros);
		
		modelViajes = new DefaultTableModel(null,nombreColumnas){
			@Override
				public boolean isCellEditable(int row, int column){
				return false;
				}
			};
		tablaViajes = new JTable(modelViajes);
		spPasajeros.setViewportView(tablaViajes);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(442, 395, 150, 49);
		contentPane.add(btnConfirmar);
		
		btnAtras = new JButton("Atrás");
		btnAtras.setBounds(249, 395, 150, 49);
		contentPane.add(btnAtras);
		
		txtFiltro = new JTextField();
		txtFiltro.setBounds(23, 106, 183, 19);
		contentPane.add(txtFiltro);
		txtFiltro.setColumns(10);
		
		lblFiltro = new JLabel("Filtro: Origen ó Destino");
		lblFiltro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblFiltro.setBounds(48, 83, 158, 19);
		contentPane.add(lblFiltro);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(60, 179, 113));
		panel.setBounds(0, 0, 969, 68);
		contentPane.add(panel);
		
		JLabel lblViaje = new JLabel("Viajes");
		lblViaje.setForeground(Color.WHITE);
		lblViaje.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblViaje.setBounds(425, 0, 271, 64);
		panel.add(lblViaje);
		
		dateDesde = new JDateChooser();
		dateDesde.setBounds(315, 103, 131, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateDesde.getDateEditor();
		editor.setEditable(false);
		getContentPane().add(dateDesde);
		
		JLabel lblFechaDelEvento = new JLabel("Fecha de Salida");
		lblFechaDelEvento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaDelEvento.setBounds(409, 85, 183, 14);
		getContentPane().add(lblFechaDelEvento);
		
		dateHasta = new JDateChooser();
		JTextFieldDateEditor editor2 = (JTextFieldDateEditor) dateHasta.getDateEditor();
		editor2.setEditable(false);
		dateHasta.setBounds(461, 103, 131, 20);
		getContentPane().add(dateHasta);
		
		JLabel lblHasta = new JLabel("Hasta");
		lblHasta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHasta.setBounds(501, 125, 78, 14);
		getContentPane().add(lblHasta);
		
		lblPresioneLaTecla = new JLabel("Consejo: Mantenga la tecla ctrl presionada para seleccionar más de un viaje.");
		lblPresioneLaTecla.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPresioneLaTecla.setBounds(12, 378, 704, 13);
		contentPane.add(lblPresioneLaTecla);
		
		Label label = new Label("Desde");
		label.setBounds(351, 125, 78, 18);
		contentPane.add(label);
		
		label_1 = new Label("Precio");
		label_1.setBounds(771, 75, 59, 21);
		contentPane.add(label_1);
		
		lblDesde = new JLabel("Desde");
		lblDesde.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDesde.setBounds(724, 123, 57, 19);
		contentPane.add(lblDesde);
		
		lblHasta_1 = new JLabel("Hasta");
		lblHasta_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblHasta_1.setBounds(824, 125, 57, 14);
		contentPane.add(lblHasta_1);
		
		btnLimpiarFiltros = new JButton("Listar Viajes sin filtro");
		btnLimpiarFiltros.setBounds(788, 386, 150, 19);
		contentPane.add(btnLimpiarFiltros);
		
		comboBoxPrecioDesde = new JComboBox<String>();
		comboBoxPrecioDesde.setBounds(702, 103, 81, 21);
		contentPane.add(comboBoxPrecioDesde);
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
		comboBoxPrecioHasta.setBounds(803, 103, 78, 21);
		contentPane.add(comboBoxPrecioHasta);
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
		
		lblPresioneLaTecla.setVisible(false);
		
		this.setVisible(false);
	}

	public JTextField getTxtFiltro() {
		return txtFiltro;
	}
	
	public JLabel getTxtMensajeCtrl() {
		return lblPresioneLaTecla;
	}
	public void mostrarVentana(boolean b) {
		this.setVisible(b);
	}

	public JTable getTablaViajes() {
		return tablaViajes;
	}

	public void setTablaViajes(JTable tablaViajes) {
		this.tablaViajes = tablaViajes;
	}

	public DefaultTableModel getModelViajes() {
		return modelViajes;
	}

	public void setModelViajes(DefaultTableModel modelViajes) {
		this.modelViajes = modelViajes;
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

	public JButton getBtnLimpiarFiltros() {
		return btnLimpiarFiltros;
	}
	
	public String[] getNombreColumnas() {
		return nombreColumnas;
	}

	public void setNombreColumnas(String[] nombreColumnas) {
		this.nombreColumnas = nombreColumnas;
	}
	
	public JDateChooser getFiltroDesde() {
		return dateDesde;	
	}
	
	public JDateChooser getFiltroHasta() {
		return dateHasta;	
	}
	
	public JComboBox<String> getComboBoxPrecioDesde() {
		return comboBoxPrecioDesde;
	}

	public JComboBox<String> getComboBoxPrecioHasta() {
		return comboBoxPrecioHasta;
	}
	
}