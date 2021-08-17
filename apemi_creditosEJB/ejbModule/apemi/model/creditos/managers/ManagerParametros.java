package apemi.model.creditos.managers;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import apemi.model.auditoria.managers.ManagerAuditoria;
import apemi.model.core.entities.CredParametro;
import apemi.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerParametros
 */
@Stateless
@LocalBean
public class ManagerParametros {

	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

	public ManagerParametros() {
	}

	public List<CredParametro> findAllCredParametro() {
		return mDAO.findAll(CredParametro.class);
	}

	public void insertarCredParametro(BigDecimal interes, BigDecimal montoMinimo, long plazoMaxMontoMin,
			BigDecimal seguroDesgravamen) throws Exception {
		CredParametro nuevo = new CredParametro();
		// nuevo.setIdParametroCredito(idParamCred);
		nuevo.setInteres(interes);
		nuevo.setMontoMinimo(montoMinimo);
		nuevo.setPlazoMaxMontoMin(plazoMaxMontoMin);
		nuevo.setSeguroDesgravamen(seguroDesgravamen);
		mDAO.insertar(nuevo);
		// return nuevo;
	}

	public void actualizarCredParametro(int idParamCredUpdate, BigDecimal interesUpdate, BigDecimal montoMinimoUpdate,
			long plazoMaxMontoMinUpdate, BigDecimal seguroDesgravamenUpdate) throws Exception {
		CredParametro credParametro = (CredParametro) mDAO.findById(CredParametro.class, idParamCredUpdate);
		credParametro.setInteres(interesUpdate);
		credParametro.setMontoMinimo(montoMinimoUpdate);
		credParametro.setPlazoMaxMontoMin(plazoMaxMontoMinUpdate);
		credParametro.setSeguroDesgravamen(seguroDesgravamenUpdate);

		mDAO.actualizar(credParametro);
	}

	public void eliminarCredParametro(int idParamCred) throws Exception {
		mDAO.eliminar(CredParametro.class, idParamCred);
	}
}
