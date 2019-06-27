package presentacion.vista.contador;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.SystemColor;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import dto.LocalDTO;
import dto.RolDTO;

public class VentanaAgregarServicio extends JFrame {

	private JPanel contentPane;
	private JTextField txtServicio;
	private JMonthChooser mesChooser;
	private JYearChooser anioChooser;
	private static VentanaAgregarServicio ventanaAgregarServicio;
	private JTextField txtMonto;
	private JButton btnConfirmar;
	private JComboBox<LocalDTO> comboBoxLocales;
	
	
	public static VentanaAgregarServicio getInstance(){
		if(ventanaAgregarServicio == null){	
			ventanaAgregarServicio = new VentanaAgregarServicio();
			return ventanaAgregarServicio;
		}else{
			return ventanaAgregarServicio;
		}
	}
	
	public VentanaAgregarServicio() {
		setTitle("Comprobantes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 522, 385);
		setLocationRelativeTo(null); 
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 0, 255));
		panel.setBounds(0, 0, 516, 53);
		contentPane.add(panel);
		
		JLabel lblSueldos = new JLabel("Agregar servicio");
		lblSueldos.setForeground(Color.WHITE);
		lblSueldos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblSueldos.setBounds(154, 0, 210, 53);
		panel.add(lblSueldos);
	
		JLabel lblNombreServicio = new JLabel("Nombre del servicio:");
		lblNombreServicio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombreServicio.setBounds(51, 78, 121, 14);
		contentPane.add(lblNombreServicio);
		
		txtServicio = new JTextField();
		txtServicio.setBackground(Color.WHITE);
		txtServicio.setBounds(208, 75, 185, 20);
		contentPane.add(txtServicio);
		txtServicio.setColumns(10);
		
		comboBoxLocales = new JComboBox<LocalDTO>();
		comboBoxLocales.setBounds(208, 177, 194, 20);
		contentPane.add(comboBoxLocales);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(171, 282, 178, 48);
		contentPane.add(btnConfirmar);
		
		JLabel lblMes = new JLabel("Mes:");
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMes.setBounds(53, 129, 53, 14);
		contentPane.add(lblMes);
		
		txtMonto = new JTextField();
		txtMonto.setBounds(208, 227, 185, 20);
		contentPane.add(txtMonto);
		txtMonto.setColumns(10);
		
		JLabel lblMonto = new JLabel("Monto $:");
		lblMonto.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMonto.setBounds(51, 230, 46, 14);
		contentPane.add(lblMonto);
		
		mesChooser = new JMonthChooser();
		mesChooser.setBounds(206, 129, 113, 27);
		contentPane.add(mesChooser);
		
		anioChooser = new JYearChooser();
		anioChooser.setMaximum(2015);
		anioChooser.setEndYear(2019);
		anioChooser.setBounds(322, 129, 71, 27);
		contentPane.add(anioChooser);
		
		JLabel lblLocal = new JLabel("Local:");
		lblLocal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblLocal.setBounds(51, 177, 46, 14);
		contentPane.add(lblLocal);
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}

	public JTextField getTxtServicio() {
		return txtServicio;
	}

	public JMonthChooser getMesChooser() {
		return mesChooser;
	}

	public JYearChooser getAnioChooser() {
		return anioChooser;
	}

	public JTextField getTxtMonto() {
		return txtMonto;
	}

	public JComboBox<LocalDTO> getComboBoxLocales() {
		return comboBoxLocales;
	}

	public void mostrarVentana(boolean visibilidad) {
		this.setVisible(visibilidad);
	}
	
	public void limpiarCampos(){
		this.txtMonto.setText("");
		this.txtServicio.setText("");
		this.comboBoxLocales.setSelectedIndex(0);
	}
}
