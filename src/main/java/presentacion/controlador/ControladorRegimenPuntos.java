package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import dto.RegimenPuntoDTO;
import modelo.modeloRegimenPunto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.coordinador.VentanaModificarRegimenPuntos;
import presentacion.vista.coordinador.VentanaMostrarRegimenPuntos;
import presentacion.vista.coordinador.VentanaRegistroRegimenPuntos;
public class ControladorRegimenPuntos implements ActionListener {
	
	private VentanaRegistroRegimenPuntos ventanaRegistroRegimenPuntos;
	private VentanaModificarRegimenPuntos ventanaModificarRegimenPuntos;
	private VentanaMostrarRegimenPuntos ventanaMostrarRegimenPuntos;
	private modeloRegimenPunto modeloRegimenPunto;
	private List<RegimenPuntoDTO> puntos_en_tabla;
	private int filaSeleccionada;
	
	public ControladorRegimenPuntos(){
		this.ventanaRegistroRegimenPuntos = VentanaRegistroRegimenPuntos.getInstance();
		this.ventanaModificarRegimenPuntos = VentanaModificarRegimenPuntos.getInstance();
		this.ventanaMostrarRegimenPuntos = VentanaMostrarRegimenPuntos.getInstance();
		
		this.ventanaModificarRegimenPuntos.getBtnModificar().addActionListener(ac->ModificarRegimenPuntos(ac));
		
		
		
		this.ventanaRegistroRegimenPuntos.getBtnAgregar().addActionListener(rc->RegistroRegimenPuntos(rc));
		this.ventanaRegistroRegimenPuntos.getBtnCancelar().addActionListener(c->cancelarVentanaRegistroRegimenPuntos(c));
		this.ventanaModificarRegimenPuntos.getBtnModificar().addActionListener(ac->ModificarRegimenPuntos(ac));
		this.ventanaModificarRegimenPuntos.getBtnCancelar().addActionListener(c->cancelarVentanaModificarRegimenPuntos(c));

		this.modeloRegimenPunto = new modeloRegimenPunto(new DAOSQLFactory());
		puntos_en_tabla = modeloRegimenPunto.obtenerPunto();
	}
	
	public void mostrarVentanaRegimenPuntos(){
		RegimenPuntoDTO punto = this.modeloRegimenPunto.obtenerPunto().get(0);
		this.ventanaMostrarRegimenPuntos.getTextARS().setText(String.valueOf(punto.getARS()));
		this.ventanaMostrarRegimenPuntos.getVencimiento().setText(String.valueOf(punto.getVencimiento()));
		this.ventanaMostrarRegimenPuntos.getTxtCantPuntos().setText(String.valueOf(punto.getPunto()));
		this.ventanaMostrarRegimenPuntos.setVisible(true);
	}

	public void mostrarVentanaRegistroRegimenPuntosConDarAlta() {
		this.ventanaRegistroRegimenPuntos.limpiarCampos();
		this.ventanaRegistroRegimenPuntos.mostrarVentana();
	}
	
	public void ModificarRegimenPuntos() {
		this.ventanaModificarRegimenPuntos.limpiarCampos();
		this.ventanaModificarRegimenPuntos.mostrarVentana();
	}
	
	public void RegistroRegimenPuntos(ActionEvent rc) {
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres agregar el Punto?", 
			             "Agregar Punto", JOptionPane.YES_NO_OPTION,
			             JOptionPane.INFORMATION_MESSAGE, null, null, null);
		if (confirm == 0){		
			RegimenPuntoDTO nuevoPunto = new RegimenPuntoDTO();
			nuevoPunto.setPunto(Integer.parseInt(this.ventanaRegistroRegimenPuntos.getTxtCantPuntos().getText()));
			nuevoPunto.setARS(Integer.parseInt(this.ventanaRegistroRegimenPuntos.getTextARS().getText()));
			nuevoPunto.setVencimiento(Integer.parseInt(this.ventanaRegistroRegimenPuntos.getVencimiento().getText()));
			modeloRegimenPunto.agregarPunto(nuevoPunto);
	
			this.ventanaRegistroRegimenPuntos.limpiarCampos();
			this.ventanaRegistroRegimenPuntos.cerrarVentana();
			
		}
	}
	
	private void cancelarVentanaRegistroRegimenPuntos(ActionEvent c) {
		this.ventanaRegistroRegimenPuntos.limpiarCampos();
		this.ventanaRegistroRegimenPuntos.cerrarVentana();
	}
	public void ModificarRegimenPuntos(int filaSeleccionada){
		this.filaSeleccionada = filaSeleccionada;
		this.ventanaModificarRegimenPuntos.mostrarVentana();
		System.out.println(puntos_en_tabla.size()+ "--" + filaSeleccionada);
		this.ventanaModificarRegimenPuntos.getTxtCantPuntos().setText(Integer.toString(this.puntos_en_tabla.get(this.filaSeleccionada).getPunto()));
		this.ventanaModificarRegimenPuntos.getTextARS().setText(Integer.toString(this.puntos_en_tabla.get(this.filaSeleccionada).getARS()));
		this.ventanaModificarRegimenPuntos.getVencimiento().setText(Integer.toString(this.puntos_en_tabla.get(this.filaSeleccionada).getVencimiento()));
	}
	
    public void ModificarRegimenPuntos(ActionEvent ac) { 
		int confirm = JOptionPane.showOptionDialog(
	            null,"¿Estás seguro que quieres editar el regimen puntos?", 
			             "Editar puntos", JOptionPane.YES_NO_OPTION,
			             JOptionPane.WARNING_MESSAGE, null, null, null);
		if (confirm == 0){
			this.modeloRegimenPunto.editarPunto(new RegimenPuntoDTO(puntos_en_tabla.get(this.filaSeleccionada).getIdPunto(),
												Integer.parseInt(this.ventanaModificarRegimenPuntos.getTxtCantPuntos().getText()),
												Integer.parseInt(this.ventanaModificarRegimenPuntos.getTextARS().getText()),
												Integer.parseInt(this.ventanaModificarRegimenPuntos.getVencimiento().getText()))
								);
			ventanaModificarRegimenPuntos.limpiarCampos();
			ventanaModificarRegimenPuntos.dispose(); 
		}  
	}  
		
	private void cancelarVentanaModificarRegimenPuntos(ActionEvent c) {
		this.ventanaModificarRegimenPuntos.limpiarCampos();
		this.ventanaModificarRegimenPuntos.cerrarVentana();
	}
	
	public void eliminarPunto(int filaSeleccionada){
		int confirm = JOptionPane.showOptionDialog(
		            null,"¿Estás seguro que quieres eliminar el regimen de puntos?", 
				             "Eliminar puntos", JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE, null, null, null);
	 if (confirm == 0){
		this.modeloRegimenPunto.borrarPunto(puntos_en_tabla.get(filaSeleccionada));
	 }
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {	
	}
	


}
