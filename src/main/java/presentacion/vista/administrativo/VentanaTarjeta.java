package presentacion.vista.administrativo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class VentanaTarjeta extends JFrame {

	private JPanel contentPane;
	private static VentanaTarjeta INSTANCE;
	private JTextField txtNumeroTarjeta;
	private JMonthChooser mesChooser; 
	private JYearChooser anioChooser;
	
	public static VentanaTarjeta getInstance(){
		if(INSTANCE == null){	
			INSTANCE = new VentanaTarjeta();
			return INSTANCE;
		}else{
			return INSTANCE;
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaTarjeta frame = new VentanaTarjeta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaTarjeta() {
		setTitle("Ingreso de tarjeta");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 397, 326);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 139, 139));
		panel.setBounds(0, 0, 444, 53);
		contentPane.add(panel);
		
		JLabel lblTarjeta = new JLabel("Tarjeta");
		lblTarjeta.setForeground(Color.WHITE);
		lblTarjeta.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTarjeta.setBounds(137, 0, 128, 53);
		panel.add(lblTarjeta);
		
		txtNumeroTarjeta = new JTextField();
		txtNumeroTarjeta.setBounds(23, 99, 227, 20);
		contentPane.add(txtNumeroTarjeta);
		txtNumeroTarjeta.setColumns(10);
		
		JLabel lblIngreseNumeroDe = new JLabel("Ingrese número de tarjeta:");
		lblIngreseNumeroDe.setBounds(23, 74, 202, 14);
		contentPane.add(lblIngreseNumeroDe);
		
		JButton btnCargarDatos = new JButton("Cargar datos");
		btnCargarDatos.setBounds(98, 233, 190, 34);
		contentPane.add(btnCargarDatos);
		
		mesChooser = new JMonthChooser();
		mesChooser.setBounds(33, 160, 135, 25);
		contentPane.add(mesChooser);
		
		JLabel lblFechaVencimiento = new JLabel("Fecha de vencimiento:");
		lblFechaVencimiento.setBounds(23, 141, 202, 14);
		contentPane.add(lblFechaVencimiento);
		
		anioChooser = new JYearChooser();
		anioChooser.setStartYear(2019);
		anioChooser.setEndYear(2039);
		anioChooser.setBounds(178, 160, 47, 25);
		contentPane.add(anioChooser);
	}
	
	public JTextField getTxtNumeroTarjeta() {
		return txtNumeroTarjeta;
	}

	public JMonthChooser getMesChooser() {
		return mesChooser;
	}

	public JYearChooser getAnioChooser() {
		return anioChooser;
	}
}