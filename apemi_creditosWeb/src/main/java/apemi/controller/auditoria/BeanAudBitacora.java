package apemi.controller.auditoria;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import apemi.controller.JSFUtil;
import apemi.model.auditoria.managers.ManagerAuditoria;
import apemi.model.core.entities.AudBitacora;
import apemi.model.core.utils.ModelUtil;



@Named
@SessionScoped
public class BeanAudBitacora implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerAuditoria managerAuditoria;
	private List<AudBitacora> listaBitacora;
	private Date fechaInicio;
	private Date fechaFin;

	public BeanAudBitacora() {

	}

	@PostConstruct
	public void inicializacion() {

	}

	public String actionCargarMenuBitacora() {
		// obtener la fecha de ayer:
		fechaInicio = ModelUtil.addDays(new Date(), -1);
		// obtener la fecha de hoy:
		fechaFin = new Date();
		listaBitacora = managerAuditoria.findBitacoraByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacora.size());
		return "bitacora";
	}

	public void actionListenerConsultarBitacora() {
		listaBitacora = managerAuditoria.findBitacoraByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacora.size());
	}

	public List<AudBitacora> getListaBitacora() {
		return listaBitacora;
	}

	public void setListaBitacora(List<AudBitacora> listaBitacora) {
		this.listaBitacora = listaBitacora;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

}
