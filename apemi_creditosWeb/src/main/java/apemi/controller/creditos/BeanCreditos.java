package apemi.controller.creditos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import apemi.model.core.entities.CredParametro;
import apemi.model.creditos.managers.ManagerCreditos;
import apemi.model.creditos.managers.ManagerParametros;
import appemi.model.creditos.dtos.DTOAmortizacion;

@Named
@SessionScoped
public class BeanCreditos implements Serializable {
	@EJB
	private ManagerCreditos managerCreditos;
	@EJB
	private ManagerParametros managerParametros;

	private double monto;
	private double nroCuotas;


	List<DTOAmortizacion> listaAmortizacion = new ArrayList<DTOAmortizacion>();
	private static final long serialVersionUID = 1L;

	public BeanCreditos() {
		// TODO Auto-generated constructor stub
	}

	public void actionListenerMostarTablaDeAmortizacion() {
		List<CredParametro> parametros = managerParametros.findAllCredParametro();
		listaAmortizacion = managerCreditos.generarAmortizacion(monto, nroCuotas, parametros.get(0).getInteres().doubleValue());
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getNroCuotas() {
		return nroCuotas;
	}

	public void setNroCuotas(double nroCuotas) {
		this.nroCuotas = nroCuotas;
	}



	public List<DTOAmortizacion> getListaAmortizacion() {
		return listaAmortizacion;
	}

	public void setListaAmortizacion(List<DTOAmortizacion> listaAmortizacion) {
		this.listaAmortizacion = listaAmortizacion;
	}

}
