package presentacion.vista.administrativo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class NotificacionEmergente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private JButton btnVerNotificacion;
	private JLabel lblMensajeEventoNuevo;
	
	private static NotificacionEmergente INSTANCE;
	
	public static NotificacionEmergente getInstance(){
		if(INSTANCE == null){	
			INSTANCE = new NotificacionEmergente();
			return INSTANCE;
		}else{
			return INSTANCE;
		}
	}
	public NotificacionEmergente() {
		setTitle("Notificacion");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 365, 209);
		setResizable(false);
		//setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(66, 99, 145));
		panel.setBounds(0, 0, 359, 174);
		contentPane.add(panel);
		
		lblMensajeEventoNuevo = new JLabel();
		lblMensajeEventoNuevo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeEventoNuevo.setForeground(Color.WHITE);
		lblMensajeEventoNuevo.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblMensajeEventoNuevo.setBounds(0, 0, 361, 83);
		panel.add(lblMensajeEventoNuevo);
		
		btnVerNotificacion = new JButton("Ver");
		btnVerNotificacion.setBounds(101, 66, 156, 31);
		btnVerNotificacion.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnVerNotificacion.setBackground(Color.WHITE);
		btnVerNotificacion.setForeground(Color.BLACK);
	}
	
	public JButton getBtnVerNotificacion() {
		return btnVerNotificacion;
	}
	
	public void setLblMensajeEventoNuevo(String s) {
		lblMensajeEventoNuevo.setText(s);;
	}
	
	public void mostrarBtnVerNotificacion() {
		panel.add(btnVerNotificacion);
	}
	
	public void mostrarVentana(boolean b){
		if (b) {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
	        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	        int x = (int) rect.getMaxX() - this.getWidth();
	        int y = (int) rect.getMaxY() - this.getHeight();
	        this.setLocation(x, y);
		}
		this.setVisible(b);
	}
}