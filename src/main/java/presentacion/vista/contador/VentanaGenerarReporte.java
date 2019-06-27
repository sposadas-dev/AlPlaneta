package presentacion.vista.contador;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import presentacion.vista.administrativo.VistaAdministrativo;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class VentanaGenerarReporte extends JFrame {

	private JPanel contentPane;
	private JDateChooser dateDesdeChooser;
	private JDateChooser dateHastaChooser;
	private JLabel lblFiltroPasajes;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JLabel lblSeleccioneOpcion;
	private JLabel lblLocal;
	private JComboBox<String> comboBoxFiltro; 
	private JComboBox<String> comboBoxOpciones;
	private JComboBox<String> comboBoxLocales;
	private static VentanaGenerarReporte ventanaGenerarReporte;
	private JButton btnGenerarReporte;

	public static VentanaGenerarReporte getInstance(){
		if(ventanaGenerarReporte == null){	
			ventanaGenerarReporte = new VentanaGenerarReporte();
			return ventanaGenerarReporte;
		}else{
			return ventanaGenerarReporte;
		}
	}
	
	public VentanaGenerarReporte() {
		super();
		setTitle("Generación de reporte");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 630, 320);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		dateDesdeChooser = new JDateChooser();
		JTextFieldDateEditor editorDesde = (JTextFieldDateEditor) dateDesdeChooser.getDateEditor();
		editorDesde.setEditable(false);
		dateDesdeChooser.setBounds(129, 156, 145, 20);
		dateDesdeChooser.setVisible(false);
		contentPane.add(dateDesdeChooser);
		
		dateHastaChooser = new JDateChooser();
		JTextFieldDateEditor editorHasta = (JTextFieldDateEditor) dateHastaChooser.getDateEditor();
		editorHasta.setEditable(false);
		dateHastaChooser.setBounds(415, 156, 145, 20);
		dateHastaChooser.setVisible(false);
		contentPane.add(dateHastaChooser);
		
		lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(53, 162, 53, 14);
		lblDesde.setVisible(false);
		contentPane.add(lblDesde);
		
		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(355, 162, 53, 14);
		lblHasta.setVisible(false);
		contentPane.add(lblHasta);
		
		lblFiltroPasajes = new JLabel("Filtrar por:");
		lblFiltroPasajes.setBounds(50, 56, 103, 14);
		contentPane.add(lblFiltroPasajes);
		
		comboBoxFiltro = new JComboBox<String>();
		comboBoxFiltro.addItem("Seleccione");
		comboBoxFiltro.addItem("Cliente");
		comboBoxFiltro.addItem("Destino");
		comboBoxFiltro.addItem("Vendedor");
		comboBoxFiltro.setBounds(146, 53, 156, 20);
		contentPane.add(comboBoxFiltro);
		
		btnGenerarReporte = new JButton("Generar reporte");
		btnGenerarReporte.setBounds(240, 205, 145, 44);
		contentPane.add(btnGenerarReporte);
		
		lblSeleccioneOpcion = new JLabel("Filtrar por:");
		lblSeleccioneOpcion.setBounds(50, 97, 78, 14);
		contentPane.add(lblSeleccioneOpcion);
		lblSeleccioneOpcion.setVisible(false);
		
		comboBoxOpciones = new JComboBox<String>();
		comboBoxOpciones.addItem("Seleccione");
		comboBoxOpciones.addItem("Local");
		comboBoxOpciones.addItem("General de la empresa");
		comboBoxOpciones.setBounds(146, 94, 156, 20);
		contentPane.add(comboBoxOpciones);
		comboBoxOpciones.setVisible(false);
		
		lblLocal = new JLabel("Local:");
		lblLocal.setBounds(355, 97, 46, 14);
		contentPane.add(lblLocal);
		lblLocal.setVisible(false);
		
		comboBoxLocales = new JComboBox<String>();
		comboBoxLocales.setBounds(411, 94, 189, 20);
		contentPane.add(comboBoxLocales);
		comboBoxLocales.setVisible(false);
	}


	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JLabel getLblDesde() {
		return lblDesde;
	}

	public JLabel getLblHasta() {
		return lblHasta;
	}

	public JDateChooser getDateDesdeChooser() {
		return dateDesdeChooser;
	}

	public JDateChooser getDateHastaChooser() {
		return dateHastaChooser;
	}
	
	public JLabel getLblFiltroPasajes() {
		return lblFiltroPasajes;
	}

	public JLabel getLblSeleccioneOpcion() {
		return lblSeleccioneOpcion;
	}

	public JLabel getLblLocal() {
		return lblLocal;
	}

	public JComboBox<String> getComboBoxFiltro() {
		return comboBoxFiltro;
	}
	
	public JComboBox<String> getComboBoxOpciones() {
		return comboBoxOpciones;
	}

	public JComboBox<String> getComboBoxLocales() {
		return comboBoxLocales;
	}

	public JButton getBtnGenerarReporte() {
		return btnGenerarReporte;
	}

	public void limpiarCampos(){
		this.comboBoxFiltro.setToolTipText("Seleccione");
		this.dateDesdeChooser.setDate(null);
		this.dateHastaChooser.setDate(null);
	}
}