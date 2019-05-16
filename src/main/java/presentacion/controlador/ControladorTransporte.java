package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import dto.TransporteDTO;
import modelo.Transporte;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.administrador.VentanaAgregarTransporte;
import presentacion.vista.administrador.VentanaEditarTransporte;

public class ControladorTransporte implements ActionListener {

	private VentanaAgregarTransporte ventanaAgregarTransporte;
	private VentanaEditarTransporte ventanaEditarTransporte;
	private Transporte transporte;
	private List<TransporteDTO> transportes_en_tabla;
	
	public ControladorTransporte(){
		
		this.ventanaAgregarTransporte = VentanaAgregarTransporte.getInstance();
		this.ventanaEditarTransporte = VentanaEditarTransporte.getInstance();
			
		this.ventanaAgregarTransporte.getBtnAgregar().addActionListener(rc->agregarTransporte(rc));
//		this.ventanaEditarTransporte.getBtnEditar().addActionListener(ac->editarTransporte(ac));

		this.transporte = new Transporte(new DAOSQLFactory());
		transportes_en_tabla = transporte.obtenerTransportes();
	}

	public void inicializar() {
		this.ventanaAgregarTransporte.mostrarVentana();
	}
	
	public void editarTransporte() {
		this.ventanaEditarTransporte.mostrarVentana();
	}
	
	public void agregarTransporte(ActionEvent rc) {
		TransporteDTO nuevoTransporte = new TransporteDTO();
		nuevoTransporte.setNombre(this.ventanaAgregarTransporte.getTxtNombreTransporte().getText());
		transporte.agregarTransporte(nuevoTransporte);
		
		this.ventanaAgregarTransporte.limpiarCampos();
		this.ventanaAgregarTransporte.cerrarVentana();
	}
	
	public void editarTransporte(int filaSeleccionada){
		this.ventanaEditarTransporte.mostrarVentana();
		String nombreTransporte = this.ventanaEditarTransporte.getTxtNombreTransporte().getText();
//		if ((nombreLocalidad!=null) && (nombreLocalidad.length()>0)){
		
		this.transporte.editarTransporte(new TransporteDTO(transportes_en_tabla.get(filaSeleccionada).getIdTransporte(),nombreTransporte));
		

//					JOptionPane.showMessageDialog(null, "Localidad editada","Localidad", JOptionPane.INFORMATION_MESSAGE);
//				}		
//			}catch(Exception e){
//				JOptionPane.showMessageDialog(null, "La localidad no ha sido editada");
//			}
//		}
	}
	
	public void eliminarTransporte(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar el transporte?", 
				             "Eliminar localidad", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		this.transporte.borrarTransporte(transportes_en_tabla.get(filaSeleccionada));
	 }
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
}