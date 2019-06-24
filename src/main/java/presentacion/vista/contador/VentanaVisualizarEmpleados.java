package presentacion.vista.contador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;

import dto.RolDTO;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import com.toedter.calendar.JMonthChooser;

public class VentanaVisualizarEmpleados extends JFrame {

	private JPanel contentPane;
	private static VentanaVisualizarEmpleados ventanaVisualizarEmpleados;
	private JComboBox<RolDTO> comboBoxRoles;
	private String[] nombreColumnas = {"Nombre", "Apellido","DNI", "Rol","Local"};
	private JMonthChooser mesChooser;
	private JTextField txtSueldo;
	private JButton btnConfirmar;

	public static VentanaVisualizarEmpleados getInstance(){
		if(ventanaVisualizarEmpleados == null){	
			ventanaVisualizarEmpleados = new VentanaVisualizarEmpleados();
			return ventanaVisualizarEmpleados;
		}else{
			return ventanaVisualizarEmpleados;
		}
	}
	
	public VentanaVisualizarEmpleados() {
		super();
		setTitle("Agregar sueldo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 537, 387);
		setLocationRelativeTo(null);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(0, 128, 0));
		panel.setBounds(0, 0, 531, 53);
		contentPane.add(panel);
		
		comboBoxRoles = new JComboBox<RolDTO>();
		comboBoxRoles.setBounds(212, 80, 194, 20);
		contentPane.add(comboBoxRoles);
		
		JLabel lblSueldo = new JLabel("Sueldo $:");
		lblSueldo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSueldo.setBounds(78, 208, 122, 14);
		contentPane.add(lblSueldo);
		
		JLabel lblCargaSueldo = new JLabel("Carga de sueldo");
		lblCargaSueldo.setForeground(Color.WHITE);
		lblCargaSueldo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCargaSueldo.setBounds(153, 0, 210, 53);
		panel.add(lblCargaSueldo);
		
		JLabel lblFiltro = new JLabel("Filtrar por:");
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFiltro.setBounds(78, 83, 80, 14);
		contentPane.add(lblFiltro);
		
		JLabel lblMes = new JLabel("Mes:");
		lblMes.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMes.setBounds(78, 141, 53, 14);
		contentPane.add(lblMes);
		
		txtSueldo = new JTextField();
		txtSueldo.setColumns(10);
		txtSueldo.setBackground(Color.WHITE);
		txtSueldo.setBounds(212, 205, 194, 20);
		contentPane.add(txtSueldo);
		
		mesChooser = new JMonthChooser();
		mesChooser.setBounds(212, 141, 111, 27);
		contentPane.add(mesChooser);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(188, 282, 178, 48);
		contentPane.add(btnConfirmar);
	}

	public JComboBox<RolDTO> getComboBoxRoles() {
		return comboBoxRoles;
	}
	
	public JMonthChooser getMesChooser() {
		return mesChooser;
	}

	public JTextField getTxtSueldo() {
		return txtSueldo;
	}

	public void mostrarVentana(boolean visibilidad){
		this.setVisible(visibilidad);
	}

	public JButton getBtnConfirmar() {
		return btnConfirmar;
	}
	
	private class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = 7596593350901299254L;
		private String datePattern = "dd/MM/yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}
	}
}