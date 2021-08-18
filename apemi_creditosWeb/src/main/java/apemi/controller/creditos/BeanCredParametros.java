package apemi.controller.creditos;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import apemi.controller.JSFUtil;
import apemi.model.core.entities.CredParametro;
import apemi.model.creditos.managers.ManagerParametros;
import jdk.internal.org.jline.terminal.spi.JansiSupport;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named
@SessionScoped
public class BeanCredParametros implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerParametros managerParametros;

	private int id_parametro_credito;
	private BigDecimal interes;
	private BigDecimal monto_minimo;
	private long plazo_max_monto_min;
	private BigDecimal seguro_desgravamen;
	private CredParametro edicionParametro;

	private List<CredParametro> listaCredParametro;
	private CredParametro nuevoParametro;

	public BeanCredParametros() {
	}

	@PostConstruct
	public void inicializar() {

	}

	public void actionListenerInsertarCredParametro() {

		try {
			if (listaCredParametro == null) {
				managerParametros.insertarCredParametro(interes, monto_minimo, plazo_max_monto_min, seguro_desgravamen);
				listaCredParametro = managerParametros.findAllCredParametro();
				nuevoParametro = new CredParametro();
				nuevoParametro.setInteres(interes);
				nuevoParametro.setMontoMinimo(monto_minimo);
				nuevoParametro.setPlazoMaxMontoMin(plazo_max_monto_min);
				nuevoParametro.setSeguroDesgravamen(seguro_desgravamen);

				JSFUtil.crearMensajeINFO("Parámetro ingresado");
			} else {
				JSFUtil.crearMensajeWARN("Ya existe un parámetro");
			}
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actionListenerEliminarParametro(int idParamCred) {
		try {
			managerParametros.eliminarCredParametro(idParamCred);
			listaCredParametro = managerParametros.findAllCredParametro();
			JSFUtil.crearMensajeINFO("Parámetro eliminado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actionListenerCargarParametro(CredParametro parametro) {
		edicionParametro = parametro;
	}

	public void actionListenerGuardarEdicionParametro() {
		try {
			managerParametros.actualizarCredParametro(edicionParametro);
			JSFUtil.crearMensajeINFO("Parámetro editado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<CredParametro> getListaCredParametro() {
		return listaCredParametro;
	}

	public void setListaCredParametro(List<CredParametro> listaCredParametro) {
		this.listaCredParametro = listaCredParametro;
	}

	public String actionListenerMostarCredParametros() {
		listaCredParametro = managerParametros.findAllCredParametro();
		return "parametros?faces-redirect=true";
	}

	public int getId_parametro_credito() {
		return id_parametro_credito;
	}

	public void setId_parametro_credito(int id_parametro_credito) {
		this.id_parametro_credito = id_parametro_credito;
	}

	public BigDecimal getInteres() {
		return interes;
	}

	public void setInteres(BigDecimal interes) {
		this.interes = interes;
	}

	public BigDecimal getMonto_minimo() {
		return monto_minimo;
	}

	public void setMonto_minimo(BigDecimal monto_minimo) {
		this.monto_minimo = monto_minimo;
	}

	public long getPlazo_max_monto_min() {
		return plazo_max_monto_min;
	}

	public void setPlazo_max_monto_min(long plazo_max_monto_min) {
		this.plazo_max_monto_min = plazo_max_monto_min;
	}

	public BigDecimal getSeguro_desgravamen() {
		return seguro_desgravamen;
	}

	public void setSeguro_desgravamen(BigDecimal seguro_desgravamen) {
		this.seguro_desgravamen = seguro_desgravamen;
	}

	public ManagerParametros getManagerParametros() {
		return managerParametros;
	}

	public void setManagerParametros(ManagerParametros managerParametros) {
		this.managerParametros = managerParametros;
	}

	public CredParametro getEdicionParametro() {
		return edicionParametro;
	}

	public void setEdicionParametro(CredParametro edicionParametro) {
		this.edicionParametro = edicionParametro;
	}

	public CredParametro getNuevoParametro() {
		return nuevoParametro;
	}

	public void setNuevoParametro(CredParametro nuevoParametro) {
		this.nuevoParametro = nuevoParametro;
	}
}
