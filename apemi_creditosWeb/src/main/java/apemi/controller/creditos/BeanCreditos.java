package apemi.controller.creditos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import apemi.model.asociados.managers.ManagerAsociados;
import apemi.model.core.entities.CredGarante;
import apemi.model.core.entities.CredParametro;
import apemi.model.core.entities.SegUsuario;
import apemi.model.creditos.managers.ManagerCreditos;
import apemi.model.creditos.managers.ManagerParametros;
import apemi.model.garante.managers.ManagerGarante;
import appemi.model.creditos.dtos.DTOAmortizacion;

@Named
@SessionScoped
public class BeanCreditos implements Serializable {
	@EJB
	private ManagerCreditos managerCreditos;
	@EJB
	private ManagerParametros managerParametros;
	@EJB
	private ManagerAsociados managerAsociados;
	@EJB
	private ManagerGarante managerGarantes;

	private double monto;
	private double nroCuotas;
	private List<SegUsuario> listaAsociados;
	private List<CredGarante> listaGarantes;


	List<DTOAmortizacion> listaAmortizacion = new ArrayList<DTOAmortizacion>();
	private static final long serialVersionUID = 1L;

	public BeanCreditos() {
		// TODO Auto-generated constructor stub
	}
	
	public String actionMenuCreditos() {
        listaGarantes = managerGarantes.findAllGarantes();
		listaAsociados = managerAsociados.findAllAsociados();
		return "nuevoCredito";
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

	public List<SegUsuario> getListaAsociados() {
		return listaAsociados;
	}

	public void setListaAsociados(List<SegUsuario> listaAsociados) {
		this.listaAsociados = listaAsociados;
	}

	public List<CredGarante> getListaGarantes() {
		return listaGarantes;
	}

	public void setListaGarantes(List<CredGarante> listaGarantes) {
		this.listaGarantes = listaGarantes;
	}
	
	

}
