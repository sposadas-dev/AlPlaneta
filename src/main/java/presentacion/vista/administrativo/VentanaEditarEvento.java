package presentacion.vista.administrativo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;

public class VentanaEditarEvento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser dateFechaEvento;
	private JComboBox<String> comboHora;
	private JComboBox<String> comboMinutos;
	private JComboBox<String> comboEstado;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JTextField txtDescripcion;
	private JTextField txtReprogramacion;
	private JTextField txtDni;
	private JTextField txtApellido;
	private JTextField txtNombre;	

	private static VentanaEditarEvento ventanaCliente;
	
	public static VentanaEditarEvento getInstance(){
		if(ventanaCliente == null){	
			ventanaCliente = new VentanaEditarEvento();
			return ventanaCliente;
		}else{
			return ventanaCliente;
		}
	}
	
	public VentanaEditarEvento() {
		setResizable(false);
		setTitle("Editar evento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(400, 150, 597, 441);
		setLocationRelativeTo(null); // centrado en pantalla

		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelCliente = new JPanel();
		panelCliente.setBackground(new Color(66, 99, 145));
		panelCliente.setBounds(0, 0, 591, 53);
		contentPane.add(panelCliente);
		panelCliente.setLayout(null);
		
		JLabel lblEditarEvento = new JLabel("Editar evento");
		lblEditarEvento.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditarEvento.setForeground(Color.WHITE);
		lblEditarEvento.setBounds(0, 0, 591, 53);
		panelCliente.add(lblEditarEvento);
		lblEditarEvento.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblFechaEvento = new JLabel("Fecha evento:\t");
		lblFechaEvento.setBounds(35, 92, 87, 14);
		contentPane.add(lblFechaEvento);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(35, 114, 72, 31);
		contentPane.add(lblDescripcion);
		
		dateFechaEvento = new JDateChooser();
		dateFechaEvento.setBounds(120, 86, 131, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateFechaEvento.getDateEditor();
		editor.setEditable(false);
		contentPane.add(dateFechaEvento);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setEditable(false);
		txtDescripcion.setBounds(119, 119, 435, 20);
		contentPane.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		JSeparator separadorCliente = new JSeparator();
		separadorCliente.setBounds(35, 182, 519, 8);
		contentPane.add(separadorCliente);
		
		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEditar.setBackground(new Color(52, 152, 219));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setBounds(133, 345, 131, 42);
		contentPane.add(btnEditar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBounds(306, 345, 131, 42);
		contentPane.add(btnCancelar);			
		
		JLabel lblHoraEvento = new JLabel("Hora evento:");
		lblHoraEvento.setBounds(338, 88, 85, 14);
		contentPane.add(lblHoraEvento);
		
		JLabel lblContrasenia = new JLabel("Estado del evento:");
		lblContrasenia.setBounds(35, 155, 123, 14);
		contentPane.add(lblContrasenia);
		
		comboHora = new JComboBox<String>();
		comboHora.setBounds(416, 83, 40, 22);
		contentPane.add(comboHora);
		comboHora.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0)
					if(comboHora.getSelectedIndex() < comboHora.getItemCount()-1) 
						comboHora.setSelectedIndex(comboHora.getSelectedIndex()+1);
				if(e.getWheelRotation()<0)
					if(comboHora.getSelectedIndex() > 0)
						comboHora.setSelectedIndex(comboHora.getSelectedIndex()-1);
			}
		});
		
		comboMinutos = new JComboBox<String>();
		comboMinutos.setBounds(468, 83, 40, 22);
		contentPane.add(comboMinutos);
		comboMinutos.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation()>0)
					if(comboMinutos.getSelectedIndex() < comboMinutos.getItemCount()-1) 
						comboMinutos.setSelectedIndex(comboMinutos.getSelectedIndex()+1);
				if(e.getWheelRotation()<0)
					if(comboMinutos.getSelectedIndex() > 0)
						comboMinutos.setSelectedIndex(comboMinutos.getSelectedIndex()-1);
			}
		});
		
		comboEstado = new JComboBox<String>();
		comboEstado.setBounds(145, 150, 131, 22);
		contentPane.add(comboEstado);
		
		txtDni = new JTextField();
		txtDni.setEditable(false);
		txtDni.setColumns(10);
		txtDni.setBounds(70, 288, 116, 20);
		contentPane.add(txtDni);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(35, 283, 72, 31);
		contentPane.add(lblDni);
		
		JLabel lblMotivoDeReprogramacion = new JLabel("Motivo de reprogramación:");
		lblMotivoDeReprogramacion.setBounds(35, 203, 164, 31);
		contentPane.add(lblMotivoDeReprogramacion);
		
		txtReprogramacion = new JTextField();
		txtReprogramacion.setColumns(10);
		txtReprogramacion.setBounds(195, 208, 359, 20);
		contentPane.add(txtReprogramacion);
		
		JLabel lblDatosCliente = new JLabel("Datos cliente");
		lblDatosCliente.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDatosCliente.setBounds(35, 257, 98, 31);
		contentPane.add(lblDatosCliente);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 247, 519, 8);
		contentPane.add(separator);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(198, 283, 72, 31);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setEditable(false);
		txtApellido.setColumns(10);
		txtApellido.setBounds(250, 288, 116, 20);
		contentPane.add(txtApellido);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(385, 283, 72, 31);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setColumns(10);
		txtNombre.setBounds(438, 287, 116, 20);
		contentPane.add(txtNombre);
		
		JLabel label = new JLabel(":");
		label.setBounds(459, 90, 79, 14);
		contentPane.add(label);
	}
	
	public void mostrarVentana(boolean b){
		this.setVisible(b);
	}
	public JDateChooser getDateFechaEvento() {
		return dateFechaEvento;
	}
	
	public JTextField getTxtDescripcion() {
		return txtDescripcion;
	}
	
	public JTextField getTxtDni() {
		return txtDni;
	}

	public JComboBox<String> getComboEstadoEvento() {
		return comboEstado;
	}
	
	public JComboBox<String> getComboHora() {
		return comboHora;
	}
	
	public JComboBox<String> getComboMinutos() {
		return comboMinutos;
	}
	
	public JButton getBtnEditar() {
		return btnEditar;
	}
	
	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public static VentanaEditarEvento getVentanaCliente() {
		return ventanaCliente;
	}

	public JTextField getTxtReprogramacion() {
		return txtReprogramacion;
	}

	public JTextField getTxtApellido() {
		return txtApellido;
	}

	public JTextField getTxtNombre() {
		return txtNombre;
	}

	public void cerrarVentana(){
		this.dispose();
	}
	
	public void limpiarCampos(){
		this.dateFechaEvento.setDate(null);
		this.txtReprogramacion.setText(null);
	}
}