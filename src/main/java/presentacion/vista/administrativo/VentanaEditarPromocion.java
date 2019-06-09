package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class VentanaEditarPromocion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser dateFechaVencimiento;
	private JComboBox<String> comboPorcentaje;
	private JTextField txtStock;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnAsociarViaje;

	private static VentanaEditarPromocion ventanaCliente;
	private JLabel label;
	
	public static VentanaEditarPromocion getInstance(){
		if(ventanaCliente == null){	
			ventanaCliente = new VentanaEditarPromocion();
			return ventanaCliente;
		}else{
			return ventanaCliente;
		}
	}
	
	public VentanaEditarPromocion() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 361, 449);
		setLocationRelativeTo(null); // centrado en pantalla

		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBackground(new Color(139, 219, 6));
		panelCliente.setBounds(0, 0, 355, 53);
		contentPane.add(panelCliente);
		panelCliente.setLayout(null);
		
		JLabel lblRegistrarPromocion = new JLabel("Editar promoción");
		lblRegistrarPromocion.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrarPromocion.setForeground(Color.WHITE);
		lblRegistrarPromocion.setBounds(0, 0, 355, 53);
		panelCliente.add(lblRegistrarPromocion);
		lblRegistrarPromocion.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblFechaVencimiento = new JLabel("Fecha de vencimiento:");
		lblFechaVencimiento.setBounds(35, 179, 141, 31);
		contentPane.add(lblFechaVencimiento);
		
		JLabel lblPorcentaje = new JLabel("Porcentaje:");
		lblPorcentaje.setBounds(35, 92, 72, 31);
		contentPane.add(lblPorcentaje);
		
		dateFechaVencimiento = new JDateChooser();
		dateFechaVencimiento.setBounds(178, 183, 131, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateFechaVencimiento.getDateEditor();
		editor.setEditable(false);
		contentPane.add(dateFechaVencimiento);
		
		txtStock = new JTextField();
		txtStock.setBounds(81, 141, 86, 20);
		contentPane.add(txtStock);
		txtStock.setColumns(10);
		
		JSeparator separadorCliente = new JSeparator();
		separadorCliente.setBounds(35, 327, 284, 2);
		contentPane.add(separadorCliente);
		
		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEditar.setBackground(new Color(52, 152, 219));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setBounds(35, 342, 131, 42);
		contentPane.add(btnEditar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBounds(191, 342, 131, 42);
		contentPane.add(btnCancelar);
		
		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(35, 136, 72, 31);
		contentPane.add(lblStock);
		
		comboPorcentaje = new JComboBox<String>();
		comboPorcentaje.setBounds(104, 96, 52, 22);
		contentPane.add(comboPorcentaje);
		
		btnAsociarViaje = new JButton("Ver viajes");
		btnAsociarViaje.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAsociarViaje.setBounds(104, 283, 152, 31);
		contentPane.add(btnAsociarViaje);
		
		label = new JLabel("%");
		label.setBounds(164, 92, 72, 31);
		contentPane.add(label);
	}	
	
	public void mostrarVentana(){
		this.setVisible(true);
	}
	public JDateChooser getDateFechaVencimiento() {
		return dateFechaVencimiento;
	}
	
	public JTextField getTxtStock() {
		return txtStock;
	}

	public JComboBox<String> getComboPorcentaje() {
		return comboPorcentaje;
	}
	
	public JButton getBtnEditar() {
		return btnEditar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}
	
	public JButton getBtnAsociarViaje() {
		return btnAsociarViaje;
	}
	
	public void cerrarVentana(){
		this.dispose();
	}
	
	public void limpiarCampos(){
		this.comboPorcentaje.setSelectedIndex(0);
		this.dateFechaVencimiento.setDate(null);
		this.txtStock.setText("");
	}
}