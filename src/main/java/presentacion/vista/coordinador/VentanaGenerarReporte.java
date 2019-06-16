package presentacion.vista.coordinador;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import presentacion.vista.administrativo.VistaAdministrativo;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;

public class VentanaGenerarReporte extends JFrame {

	private JPanel contentPane;
	private JButton btnGenerarReporteVentas;
	private JButton btnGenerarReportePasajes;
	
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
		
		JDateChooser dateDesdeChooser = new JDateChooser();
		dateDesdeChooser.setBounds(114, 52, 145, 20);
		contentPane.add(dateDesdeChooser);
		
		JDateChooser dateHastaChooser = new JDateChooser();
		dateHastaChooser.setBounds(371, 52, 145, 20);
		contentPane.add(dateHastaChooser);
		
		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(49, 52, 53, 14);
		contentPane.add(lblDesde);
		
		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(317, 52, 53, 14);
		contentPane.add(lblHasta);
		
		btnGenerarReportePasajes = new JButton("Generar reporte");
		btnGenerarReportePasajes.setBounds(211, 168, 145, 51);
		contentPane.add(btnGenerarReportePasajes);
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}
	
	public JButton getBtnGenerarReporte() {
		return btnGenerarReporteVentas;
	}
	
	public JButton getBtnGenerarReportePasajes() {
		return btnGenerarReportePasajes;
	}

}
