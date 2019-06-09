package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import javax.swing.JScrollPane;

public class VentanaEditarEvento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JDateChooser dateFechaEvento;
	private JComboBox<String> comboHora;
	private JComboBox<String> comboMinutos;
	private JComboBox<String> comboEstado;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnMotivos;
	private JEditorPane txtDescripcion;
	private JEditorPane txtReprogramacion;
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
		setBounds(400, 150, 597, 572);
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
		lblFechaEvento.setBounds(35, 75, 87, 14);
		contentPane.add(lblFechaEvento);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(35, 114, 72, 31);
		contentPane.add(lblDescripcion);
		
		dateFechaEvento = new JDateChooser();
		dateFechaEvento.setBounds(120, 69, 131, 20);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateFechaEvento.getDateEditor();
		editor.setEditable(false);
		contentPane.add(dateFechaEvento);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(119, 114, 435, 94);
		contentPane.add(scrollPane_1);
		
		txtDescripcion = new JEditorPane();
		scrollPane_1.setViewportView(txtDescripcion);
		txtDescripcion.setEditable(false);
		//txtDescripcion.setColumns(10);
		
		JSeparator separadorCliente = new JSeparator();
		separadorCliente.setBounds(35, 248, 519, 8);
		contentPane.add(separadorCliente);
		
		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEditar.setBackground(new Color(52, 152, 219));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setBounds(133, 482, 131, 42);
		contentPane.add(btnEditar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(new Color(192, 57, 43));
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnCancelar.setBounds(306, 482, 131, 42);
		contentPane.add(btnCancelar);			
		
		
		JLabel lblHoraEvento = new JLabel("Hora evento:");
		lblHoraEvento.setBounds(354, 71, 85, 14);
		contentPane.add(lblHoraEvento);
		
		JLabel lblContrasenia = new JLabel("Estado del evento:");
		lblContrasenia.setBounds(35, 221, 123, 14);
		contentPane.add(lblContrasenia);
		
		comboHora = new JComboBox<String>();
		comboHora.setBounds(432, 66, 40, 22);
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
		comboMinutos.setBounds(484, 66, 40, 22);
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
		comboEstado.setBounds(145, 216, 131, 22);
		contentPane.add(comboEstado);
		
		txtDni = new JTextField();
		txtDni.setEditable(false);
		txtDni.setColumns(10);
		txtDni.setBounds(70, 425, 116, 20);
		contentPane.add(txtDni);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setBounds(35, 420, 72, 31);
		contentPane.add(lblDni);
		
		JLabel lblMotivoDeReprogramacion = new JLabel("Motivo de reprogramación:");
		lblMotivoDeReprogramacion.setBounds(35, 269, 164, 31);
		contentPane.add(lblMotivoDeReprogramacion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(195, 274, 359, 97);
		contentPane.add(scrollPane);
		
		txtReprogramacion = new JEditorPane();
		scrollPane.setViewportView(txtReprogramacion);
		
		JLabel lblDatosCliente = new JLabel("Datos cliente");
		lblDatosCliente.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDatosCliente.setBounds(35, 394, 98, 31);
		contentPane.add(lblDatosCliente);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(35, 384, 519, 8);
		contentPane.add(separator);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(198, 420, 72, 31);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setEditable(false);
		txtApellido.setColumns(10);
		txtApellido.setBounds(250, 425, 116, 20);
		contentPane.add(txtApellido);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(385, 420, 72, 31);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setEditable(false);
		txtNombre.setColumns(10);
		txtNombre.setBounds(438, 424, 116, 20);
		contentPane.add(txtNombre);
		
		JLabel label = new JLabel(":");
		label.setBounds(475, 73, 46, 14);
		contentPane.add(label);
		
		btnMotivos = new JButton("Motivos");
		btnMotivos.setForeground(Color.WHITE);
		btnMotivos.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnMotivos.setBackground(Color.GRAY);
		btnMotivos.setBounds(45, 313, 131, 42);
		contentPane.add(btnMotivos);
	}
	
	public void mostrarVentana(boolean b){
		this.setVisible(b);
	}
	public JDateChooser getDateFechaEvento() {
		return dateFechaEvento;
	}
	
	public JEditorPane getTxtDescripcion() {
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
	
	public JButton getBtnMotivos() {
		return btnMotivos;
	}


	public static VentanaEditarEvento getVentanaCliente() {
		return ventanaCliente;
	}

	public JEditorPane getTxtReprogramacion() {
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