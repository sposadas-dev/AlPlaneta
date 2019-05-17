package persistencia.dao.mysql;

import persistencia.dao.interfaz.AdministradorDAO;
import persistencia.dao.interfaz.AdministrativoDAO;
import persistencia.dao.interfaz.CiudadDAO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.CoordinadorDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.FormaPagoDAO;
import persistencia.dao.interfaz.LoginDAO;
import persistencia.dao.interfaz.MedioContactoDAO;
import persistencia.dao.interfaz.PagoDAO;
import persistencia.dao.interfaz.PaisDAO;
import persistencia.dao.interfaz.PasajeDAO;
import persistencia.dao.interfaz.PasajeroDAO;
import persistencia.dao.interfaz.ProvinciaDAO;
import persistencia.dao.interfaz.TransporteDAO;
import persistencia.dao.interfaz.ViajeDAO;


public class DAOSQLFactory implements DAOAbstractFactory {
	
	public LoginDAO createLoginDAO() {
		return new LoginDAOSQL();
	}
	
	public ClienteDAO createClienteDAO(){
		return new ClienteDAOSQL();
	}
	
	public AdministradorDAO createAdministradorDAO() {
		return new AdministradorDAOSQL();
	}
	
	public AdministrativoDAO createAdministrativoDAO() {
		return new AdministrativoDAOSQL();
	}
	

	public CoordinadorDAO createCoordinadorDAO() {
		return new CoordinadorDAOSQL();
	}

	@Override
	public PasajeDAO createPasajeDAO() {
		return new PasajeDAOSQL();
	}

	@Override
	public CiudadDAO createCiudadDAO() {
		return new CiudadDAOSQL();
	}

	@Override
	public PasajeroDAO createPasajeroDAO() {
		return new PasajeroDAOSQL();
	}

	@Override
	public MedioContactoDAO createMedioContactoDAO() {
		return new MedioContactoDAOSQL();
	}

	@Override
	public PagoDAO createPagoDAO() {
		return new PagoDAOSQL();
	}

	@Override
	public TransporteDAO createTransporteDAO() {
		return new TransporteDAOSQL();
	}

	@Override
	public ViajeDAO createViajeDAO() {
		return new ViajeDAOSQL();
	}

	@Override
	public ProvinciaDAO createProvinciaDAO() {
		return new ProvinciaDAOSQL();
	}

	@Override
	public PaisDAO createPaisDAO() {
		return new PaisDAOSQL();
	}
}