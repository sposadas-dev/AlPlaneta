package persistencia.dao.mysql;

import persistencia.dao.interfaz.AdministradorDAO;
import persistencia.dao.interfaz.AdministrativoDAO;
import persistencia.dao.interfaz.CiudadDAO;
import persistencia.dao.interfaz.ClienteDAO;
import persistencia.dao.interfaz.CondicionDeCancelacionDAO;
import persistencia.dao.interfaz.ContadorDAO;
import persistencia.dao.interfaz.CoordinadorDAO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.EstadoEventoDAO;
import persistencia.dao.interfaz.EstadoPasajeDAO;
import persistencia.dao.interfaz.EventoDAO;
import persistencia.dao.interfaz.FormaPagoDAO;
import persistencia.dao.interfaz.LocalDAO;
import persistencia.dao.interfaz.LoginDAO;
import persistencia.dao.interfaz.MedioContactoDAO;
import persistencia.dao.interfaz.PagoDAO;
import persistencia.dao.interfaz.Pagos_PasajeDAO;
import persistencia.dao.interfaz.PaisDAO;
import persistencia.dao.interfaz.PasajeDAO;
import persistencia.dao.interfaz.Pasaje_PasajerosDAO;
import persistencia.dao.interfaz.PasajeroDAO;
import persistencia.dao.interfaz.PromocionDAO;
import persistencia.dao.interfaz.ProvinciaDAO;
import persistencia.dao.interfaz.PuntoDAO;
import persistencia.dao.interfaz.RegimenPuntoDAO;
import persistencia.dao.interfaz.RolDAO;
import persistencia.dao.interfaz.TransporteDAO;
import persistencia.dao.interfaz.ViajeDAO;
import persistencia.dao.interfaz.Viaje_PromocionDAO;

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
	
	public CondicionDeCancelacionDAO createCondicionDeCancelacionDAO(){
		return new CondicionDeCancelacionDAOSQL();
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
	public FormaPagoDAO createFormaPagoDAO() {
		return new FormaPagoDAOSQL();
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

	@Override
	public RolDAO createRolDAO() {
		return new RolDAOSQL();
	}

	@Override
	public Pasaje_PasajerosDAO createPasaje_PasajerosDAO() {
		return new Pasaje_PasajerosDAOSQL();
	}

	@Override
	public EstadoPasajeDAO createEstadoPasajeDAO() {
		return new EstadoPasajeDAOSQL();
	}
	@Override
	public RegimenPuntoDAO createRegimenPuntoDAO() {
		return new RegimenPuntoDAOSQL();
	}

	@Override
	public Pagos_PasajeDAO createPagos_PasajeDAO() {
		return new Pagos_PasajeDAOSQL();
	}
	
	@Override
	public EstadoEventoDAO createEstadoEventoDAO() {
		return new EstadoEventoDAOSQL();
	}
	
	@Override
	public EventoDAO createEventoDAO() {
		return new EventoDAOSQL();
	}
	
	@Override
	public PromocionDAO createPromocionDAO() {
		return new PromocionDAOSQL();
	}

	@Override
	public PuntoDAO createPuntoDAO() {
		return new PuntoDAOSQL();
	}

	@Override
	public Viaje_PromocionDAO createViaje_PromocionDAO() {
		return new Viaje_PromocionDAOSQL();
	}

	@Override
	public LocalDAO createLocalDAO() {
		return new LocalDAOSQL();
	}

	@Override
	public ContadorDAO createContadorDAO() {
		return new ContadorDAOSQL();
	}

}