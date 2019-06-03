package presentacion.reportes;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.log4j.Logger;

import dto.Pagos_PasajeDTO;
import dto.PasajeDTO;

public class Reporte {
	
	private JasperReport reporte;
	private JasperViewer reporteViewer;
	private JasperPrint	reporteLleno;
	private Logger log = Logger.getLogger(Reporte.class);
	

    public void reporteReserva(Pagos_PasajeDTO pasaje){
    	Collection<Pagos_PasajeDTO> collection = new ArrayList<Pagos_PasajeDTO>();
		collection.add(pasaje);
    	//Hardcodeado
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));		
    	try	{
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile( "reportes" + File.separator + "ReporteReserva.jasper" );
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
					new JRBeanCollectionDataSource(collection));
    		log.info("Se cargó correctamente el reporte");
		}
		catch( JRException ex ) 
		{
			log.error("Ocurrió un error mientras se cargaba el archivo ReporteReserva.Jasper", ex);
		}
    }       
    
    public void reportePago(Pagos_PasajeDTO pago){
    	Collection<Pagos_PasajeDTO> collection = new ArrayList<Pagos_PasajeDTO>();
		collection.add(pago);
    	//Hardcodeado
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("Fecha", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));		
    	try	{
			this.reporte = (JasperReport) JRLoader.loadObjectFromFile( "reportes" + File.separator + "ReportePago.jasper" );
			this.reporteLleno = JasperFillManager.fillReport(this.reporte, parametersMap, 
					new JRBeanCollectionDataSource(collection));
    		log.info("Se cargó correctamente el reporte");
		}
		catch( JRException ex ) 
		{
			log.error("Ocurrió un error mientras se cargaba el archivo ReportePago.Jasper", ex);
		}
    }    
    
    public void mostrar(){
		this.reporteViewer = new JasperViewer(this.reporteLleno,false);
		this.reporteViewer.setVisible(true);
	}

}