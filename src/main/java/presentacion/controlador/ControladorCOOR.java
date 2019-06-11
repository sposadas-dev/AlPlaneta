package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CoordinadorDTO;
import dto.RegimenPuntoDTO;
import modelo.Coordinador;
import modelo.modeloRegimenPunto;
import persistencia.dao.mysql.DAOSQLFactory;
import presentacion.vista.coordinador.VistaCoordinador;

public class ControladorCOOR {
	
	private VistaCoordinador vistaCoordinador;
	
	private List<RegimenPuntoDTO> puntos_en_tabla;
	
	private CoordinadorDTO coordinadorLogueado;
	private Coordinador coordinador;
	private modeloRegimenPunto punto;
	
	private ControladorRegimenPuntos controladorRegimenPuntos;
	
	public ControladorCOOR(VistaCoordinador vistaCoordinador,CoordinadorDTO coordinadorLogueado){

		this.vistaCoordinador = vistaCoordinador;

//INSTANCES
		

//MENU ITEMS		
		this.vistaCoordinador.getItemAgregarRegimenPuntos().addActionListener(a->agregarPanelRegimenPuntos(a));
		this.vistaCoordinador.getItemVisualizarRegimenPuntos().addActionListener(vRP->visualizarRegimenPuntos(vRP));
		this.vistaCoordinador.getItemEditarRegimenPuntos().addActionListener(eRP->editarRegimenPuntos(eRP));
		this.vistaCoordinador.getItemEliminarRegimenPuntos().addActionListener(dt->eliminarRegimenPuntos(dt));

//		this.vistaCoordinador.getPanelRegimenPuntos().getBtnRecargarTabla().addActionListener(r->recargarTabla(r));
	

		this.coordinador = new Coordinador(new DAOSQLFactory());
		this.punto = new modeloRegimenPunto(new DAOSQLFactory()); 
		this.coordinadorLogueado = coordinadorLogueado;
	
//CONTROLADORES		
		this.controladorRegimenPuntos = new ControladorRegimenPuntos();
	
	} 
	
	public void inicializar(){
		this.vistaCoordinador.mostrarVentana();
		this.llenarTablaRegimenPuntos();
		
	}
	
	//------------------------------REgimen Puntos-------------------------------------------------
	
	private void visualizarRegimenPuntos(ActionEvent vfp) {
		mostrarVentanaPuntosConValores();
		
	}
	private void mostrarVentanaPuntosConValores() {
		this.controladorRegimenPuntos.mostrarVentanaRegimenPuntos();
	}

	/*Agrega el panel de RegimenPuntos en la vistaPrinciapal del coordinador*/ 
	private void agregarPanelRegimenPuntos(ActionEvent afp) {
		this.vistaCoordinador.getPanelRegimenPuntos().mostrarPanelRegimenPuntos(true);
		controladorRegimenPuntos.mostrarVentanaRegistroRegimenPuntosConDarAlta();
	}
	
	private void editarRegimenPuntos(ActionEvent efp) {
		this.vistaCoordinador.getPanelRegimenPuntos().mostrarPanelRegimenPuntos(true);
		int filaSeleccionada = this.vistaCoordinador.getPanelRegimenPuntos().getTablaRegimenPuntos().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorRegimenPuntos.ModificarRegimenPuntos(filaSeleccionada);
			llenarTablaRegimenPuntos(); 

		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
		llenarTablaRegimenPuntos();
	} 
	
	private void eliminarRegimenPuntos(ActionEvent dt) {
		this.vistaCoordinador.getPanelRegimenPuntos().mostrarPanelRegimenPuntos(true);
		int filaSeleccionada = this.vistaCoordinador.getPanelRegimenPuntos().getTablaRegimenPuntos().getSelectedRow();
		if (filaSeleccionada != -1){
			controladorRegimenPuntos.eliminarPunto(filaSeleccionada);
			llenarTablaRegimenPuntos();
		
		}else{
			JOptionPane.showMessageDialog(null, "No ha seleccionado una fila", "Mensaje", JOptionPane.ERROR_MESSAGE);
		}
	}
	
//	public void recargarTablaRegimenPuntos(ActionEvent r){
//		llenarTablaFormaPago();
//	}
 
	public void llenarTablaRegimenPuntos(){
		this.vistaCoordinador.getPanelRegimenPuntos().getModelRegimenPuntos().setRowCount(0); //Para vaciar la tabla
		this.vistaCoordinador.getPanelRegimenPuntos().getModelRegimenPuntos().setColumnCount(0);
		this.vistaCoordinador.getPanelRegimenPuntos().getModelRegimenPuntos().setColumnIdentifiers(this.vistaCoordinador.getPanelRegimenPuntos().getNombreColumnasRegimenPuntos());
			
		this.puntos_en_tabla = punto.obtenerPunto();
			
		for (int i = 0; i < this.puntos_en_tabla.size(); i++){
			Object[] fila = {
					this.puntos_en_tabla.get(i).getPunto(),
					this.puntos_en_tabla.get(i).getARS(),
					this.puntos_en_tabla.get(i).getVencimiento()
					
			};
			this.vistaCoordinador.getPanelRegimenPuntos().getModelRegimenPuntos().addRow(fila);
		}		
	}

}
