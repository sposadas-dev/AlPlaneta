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
	private JTextField textField;
	
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

	/**
	 * Create the frame.
	 */
	public VentanaTarjeta() {
		setTitle("Ingreso de tarjeta");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMonthChooser monthChooser = new JMonthChooser();
		monthChooser.getSpinner().setBounds(0, 0, 99, 20);
		monthChooser.setBounds(23, 82, 99, 20);
		contentPane.add(monthChooser);
		monthChooser.setLayout(null);
		
		JYearChooser yearChooser = new JYearChooser();
		yearChooser.setBounds(144, 82, 47, 20);
		contentPane.add(yearChooser);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 139, 139));
		panel.setBounds(0, 0, 444, 53);
		contentPane.add(panel);
		
		JLabel lblTarjeta = new JLabel("Tarjeta");
		lblTarjeta.setForeground(Color.WHITE);
		lblTarjeta.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTarjeta.setBounds(156, 0, 128, 53);
		panel.add(lblTarjeta);
		
		textField = new JTextField();
		textField.setBounds(23, 164, 227, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblIngreseNumeroDe = new JLabel("Ingrese numero de tarjeta:");
		lblIngreseNumeroDe.setBounds(23, 139, 202, 14);
		contentPane.add(lblIngreseNumeroDe);
		
		JButton btnCargarDatos = new JButton("Cargar datos");
		btnCargarDatos.setBounds(112, 211, 89, 23);
		contentPane.add(btnCargarDatos);
	}
}
