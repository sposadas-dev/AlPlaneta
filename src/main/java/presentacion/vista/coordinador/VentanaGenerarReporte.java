package presentacion.vista.coordinador;

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

public class VentanaGenerarReporte extends JFrame {

	private JPanel contentPane;
	private JDateChooser dateDesdeChooser;
	private JDateChooser dateHastaChooser;
	private JButton btnGenerarReporteVentas;
	private JButton btnGenerarReportePasajes;
	private JLabel lblFiltroPasajes;
	private JLabel lblDesde;
	private JLabel lblHasta;
	private JComboBox<String> comboBoxFiltro; 
	private static VentanaGenerarReporte ventanaGenerarReporte;

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
		setBounds(100, 100, 556, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnGenerarReporteVentas = new JButton("Generar reporte");
		btnGenerarReporteVentas.setBounds(211, 168, 145, 51);
		contentPane.add(btnGenerarReporteVentas);
		
		dateDesdeChooser = new JDateChooser();
		JTextFieldDateEditor editorDesde = (JTextFieldDateEditor) dateDesdeChooser.getDateEditor();
		editorDesde.setEditable(false);
		dateDesdeChooser.setBounds(114, 77, 145, 20);
		dateDesdeChooser.setVisible(false);
		contentPane.add(dateDesdeChooser);
		
		dateHastaChooser = new JDateChooser();
		JTextFieldDateEditor editorHasta = (JTextFieldDateEditor) dateHastaChooser.getDateEditor();
		editorHasta.setEditable(false);
		dateHastaChooser.setBounds(376, 77, 145, 20);
		dateHastaChooser.setVisible(false);
		contentPane.add(dateHastaChooser);
		
		lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(49, 83, 53, 14);
		lblDesde.setVisible(false);
		contentPane.add(lblDesde);
		
		lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(319, 83, 53, 14);
		lblHasta.setVisible(false);
		contentPane.add(lblHasta);
		
		btnGenerarReportePasajes = new JButton("Generar reporte");
		btnGenerarReportePasajes.setBounds(211, 168, 145, 51);
		contentPane.add(btnGenerarReportePasajes);
		
		lblFiltroPasajes = new JLabel("Filtrar pasajes:");
		lblFiltroPasajes.setBounds(52, 28, 103, 14);
		contentPane.add(lblFiltroPasajes);
		
		comboBoxFiltro = new JComboBox<String>();
		comboBoxFiltro.addItem("Seleccione");
		comboBoxFiltro.addItem("Todos");
		comboBoxFiltro.addItem("Reservados");
		comboBoxFiltro.addItem("Vendidos");
		comboBoxFiltro.setBounds(200, 25, 156, 20);
		contentPane.add(comboBoxFiltro);
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

	public JButton getBtnGenerarReporte() {
		return btnGenerarReporteVentas;
	}
	
	public JButton getBtnGenerarReportePasajes() {
		return btnGenerarReportePasajes;
	}
	
	public JLabel getLblFiltroPasajes() {
		return lblFiltroPasajes;
	}

	public JComboBox<String> getComboBoxFiltro() {
		return comboBoxFiltro;
	}
	
	public void limpiarCampos(){
		this.comboBoxFiltro.setToolTipText("Seleccione");
		this.dateDesdeChooser.setDate(null);
		this.dateHastaChooser.setDate(null);
	}
}