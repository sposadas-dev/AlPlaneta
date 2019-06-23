package presentacion.vista.administrador;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
public class LoadingWorker extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JLabel progressImage;
	private JLabel lblPorFavorEspere;
	private Thread thread;
	private String imgPath;
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public LoadingWorker(Frame parent, String text, Thread thread, String imgPath) {
		super(parent, true);
		this.thread = thread;
		this.imgPath = imgPath;

		inicialize();
		lblPorFavorEspere.setText(text);
	}
	
	private void inicialize() {
		setBounds(100, 100, 450, 264);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		URL url = LoadingWorker.class.getResource(imgPath);
		ImageIcon imageIcon = new ImageIcon(url);
		progressImage = new JLabel();
		progressImage.setBounds(26, 64, 380, 139);
		Icon icono = new ImageIcon(imageIcon.getImage().getScaledInstance(progressImage.getWidth(), progressImage.getHeight(), Image.SCALE_DEFAULT));
		progressImage.setIcon(icono);

		contentPanel.setLayout(null);
		contentPanel.add(progressImage);

		lblPorFavorEspere = new JLabel();
		lblPorFavorEspere.setBounds(26, 28, 380, 14);
		contentPanel.add(lblPorFavorEspere);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

	}
	
	public void mostrar() {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws InterruptedException {
				thread.start();
				Thread.sleep(10000);     
				return null;
			}

			@Override
			protected void done() {
				 dispose();
			}
		};
		worker.execute();
		setVisible(true);
	}

	
	
}
